/*
 * Encog(tm) Java Examples v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-examples
 *
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package ru.electronikas.dogexpert.trainer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.encog.EncogError;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.train.strategy.ResetStrategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.structure.AnalyzeNetwork;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.platformspecific.j2se.TrainingDialog;
import org.encog.util.simple.EncogUtility;
import ru.electronikas.dogsexpert.neural.ImagePair;
import ru.electronikas.dogsexpert.neural.downsample.Downsample;
import ru.electronikas.dogsexpert.neural.downsample.RGBDownsample;
import ru.electronikas.dogsexpert.neural.downsample.SimpleIntensityDownsample;
import ru.electronikas.dogsexpert.neural.image.PixmapMLData;
import ru.electronikas.dogsexpert.neural.image.PixmapMLDataSet;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageNeuralNetworkProcessor {
	private Map<String, String> args = new HashMap<String, String>();
	private String line;

	private int outputCount;
	private int downsampleWidth;
	private int downsampleHeight;
	private BasicNetwork network;

	private Downsample downsample;
	private final List<ImagePair> imageList = new ArrayList<ImagePair>();
	private final Map<String, Integer> identity2neuron = new HashMap<String, Integer>();
	private final Map<Integer, String> neuron2identity = new HashMap<Integer, String>();
	private PixmapMLDataSet training;

	private int assignIdentity(final String identity) {

		if (this.identity2neuron.containsKey(identity.toLowerCase())) {
			return this.identity2neuron.get(identity.toLowerCase());
		}

		final int result = this.outputCount;
		this.identity2neuron.put(identity.toLowerCase(), result);
		this.neuron2identity.put(result, identity.toLowerCase());
		this.outputCount++;
		return result;
	}

	private String getArg(final String name) {
		final String result = this.args.get(name);
		if (result == null) {
			throw new EncogError("Missing argument " + name + " on line: "
					+ this.line);
		}
		return result;
	}

	public void executeCommand(final String command,
							   final String line,
							   final Map<String, String> args) throws IOException {
		this.line = line;
		this.args = args;

		if (command.equals("input")) {
			processInput();
		} else if (command.equals("createtraining")) {
			processCreateTraining();
		} else if (command.equals("train")) {
			processTrain();
		} else if (command.equals("network")) {
			processNetwork();
		} else if (command.equals("whatis")) {
			processWhatIs();
		} else if (command.equals("savenet")) {
			processSaveNet();
		} else if (command.equals("savetraindata")) {
			saveTrainDataProcess();
		} else if (command.equals("loadtraindata")) {
			loadTrainDataProcess();
		} else if (command.equals("loadnet")) {
			processLoadNet();
		}

	}

	private void saveTrainDataProcess() {
		String fileName = getArg("file");
		EncogUtility.saveEGB(new File(fileName), training);
	}

	private void loadTrainDataProcess() {
		String fileName = getArg("file");
		MLDataSet training = EncogUtility.loadEGB2Memory(new File(fileName));
		for(MLDataPair pair : training) {
			this.training.add(pair);
//			System.out.println(pair);
		}
	}

	private void processSaveNet() {
		String fileName = getArg("file");
		EncogDirectoryPersistence.saveObject(new File(fileName), network);
//		BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(FILENAME));
	}

	private void processLoadNet() {
		String fileName = getArg("file");
		this.network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(fileName));
	}

	private void processCreateTraining() {
		final String strWidth = getArg("width");
		final String strHeight = getArg("height");
		final String strType = getArg("type");

		this.downsampleHeight = Integer.parseInt(strHeight);
		this.downsampleWidth = Integer.parseInt(strWidth);

		if (strType.equals("RGB")) {
			this.downsample = new RGBDownsample();
		} else {
			this.downsample = new SimpleIntensityDownsample();
		}

		this.training = new PixmapMLDataSet(this.downsample, false, 1, -1);
		System.out.println("Training set created");
	}

	private void processInput() throws IOException {
		String image = null;
		try {
			image = getArg("image");
		} catch (EncogError ex) {}
		final String identity = getArg("identity");

		final int idx = assignIdentity(identity);
		if(image == null) return;
		final File file = new File(image);
		if(!file.exists()) {
			System.out.println("Does not exist input image:" + image);
			return;
		}

		this.imageList.add(new ImagePair(file, idx));

		System.out.println("Added input image:" + image);
	}

	private void processNetwork() throws IOException {
		System.out.println("Downsampling images...");

		for (final ImagePair pair : this.imageList) {
			final MLData ideal = new BasicMLData(this.outputCount);
			final int idx = pair.getIdentity();
			for (int i = 0; i < this.outputCount; i++) {
				if (i == idx) {
					ideal.setData(i, 1);
				} else {
					ideal.setData(i, -1);
				}
			}

//			final Image img = ImageIO.read(pair.getFile());
			Pixmap pixmap = getPixmapFromImageFile(pair.getFile().getPath());
			final PixmapMLData data = new PixmapMLData(pixmap);
			this.training.add(data, ideal);
		}

		final String strHidden1 = getArg("hidden1");
		final String strHidden2 = getArg("hidden2");
		final String strHidden3 = getArg("hidden3");
		final String strHidden4 = getArg("hidden4");
		final String strHidden5 = getArg("hidden5");

		this.training.downsample(this.downsampleHeight, this.downsampleWidth);

		final int hidden1 = Integer.parseInt(strHidden1);
		final int hidden2 = Integer.parseInt(strHidden2);
		final int hidden3 = Integer.parseInt(strHidden3);
		final int hidden4 = Integer.parseInt(strHidden4);
		final int hidden5 = Integer.parseInt(strHidden5);

		this.network = simpleFeedForward(this.training
						.getInputSize(), hidden1, hidden2, hidden3, hidden4, hidden5,
				this.training.getIdealSize(), true);
		System.out.println("Created network: " + this.network.toString());
	}

	private Pixmap getPixmapFromImageFile(String path) {
		Texture texture = new Texture(new FileHandle(path));
		if (!texture.getTextureData().isPrepared()) {
			texture.getTextureData().prepare();
		}
		return texture.getTextureData().consumePixmap();
	}

	public static BasicNetwork simpleFeedForward(final int input,
												 final int hidden1,
												 final int hidden2,
												 final int hidden3,
												 final int hidden4,
												 final int hidden5,
												 final int output,
												 final boolean tanh) {
		final FeedForwardPattern pattern = new FeedForwardPattern();
		pattern.setInputNeurons(input);
		pattern.setOutputNeurons(output);
		if (tanh) {
			pattern.setActivationFunction(new ActivationTANH());
		} else {
			pattern.setActivationFunction(new ActivationSigmoid());
		}

		if (hidden1 > 0) {
			pattern.addHiddenLayer(hidden1);
		}
		if (hidden2 > 0) {
			pattern.addHiddenLayer(hidden2);
		}
		if (hidden3 > 0) {
			pattern.addHiddenLayer(hidden3);
		}
		if (hidden4 > 0) {
			pattern.addHiddenLayer(hidden5);
		}
		if (hidden5 > 0) {
			pattern.addHiddenLayer(hidden5);
		}

		final BasicNetwork network = (BasicNetwork)pattern.generate();
		network.reset();

		AnalyzeNetwork analyze = new AnalyzeNetwork(network);
		System.out.println(analyze.toString());
/*
		for(int i=0; i<1000; i++)
			network.enableConnection(0,i,i,false);

		analyze = new AnalyzeNetwork(network);
		System.out.println(analyze.toString());*/

		/*

		AnalyzeNetwork analyze = new AnalyzeNetwork(network);
		double remove = analyze.getWeights().getHigh()/1.5;
		System.out.println(analyze.toString());
		System.out.println("Remove connections below:" + Format.formatDouble(remove,5));
		network.setProperty(BasicNetwork.TAG_LIMIT,remove);
		network.getStructure().finalizeLimit();


//		network.reset();
		analyze = new AnalyzeNetwork(network);
		System.out.println(analyze.toString());
*/
		return network;
	}


	private void processTrain() throws IOException {
		final String strMode = getArg("mode");
		final String strMinutes = getArg("minutes");
		final String strStrategyError = getArg("strategyerror");
		final String strStrategyCycles = getArg("strategycycles");

		System.out.println("Training Beginning... Output patterns="
				+ this.outputCount);

		final double strategyError = Double.parseDouble(strStrategyError);
		final int strategyCycles = Integer.parseInt(strStrategyCycles);

		final ResilientPropagation train = new ResilientPropagation(this.network, this.training);
//		final Backpropagation train = new Backpropagation(this.network, this.training);
		train.addStrategy(new ResetStrategy(strategyError, strategyCycles));
//		train.addStrategy(new EndMaxErrorStrategy(strategyError));//, strategyCycles));

		if (strMode.equalsIgnoreCase("gui")) {
			TrainingDialog.trainDialog(train, this.network, this.training);
		} else {
			final int minutes = Integer.parseInt(strMinutes);
			EncogUtility.trainConsole(train, this.network, this.training,
					minutes);
//			EncogUtility.trainToError(train, strategyError);
		}
		System.out.println("Training Stopped...");
	}

	NumberFormat nf = new DecimalFormat("0.000000#");
	public void processWhatIs() throws IOException {
		final String filename = getArg("image");
		final File file = new File(filename);
		Pixmap pixmap = getPixmapFromImageFile(file.getPath());
		final PixmapMLData input = new PixmapMLData(pixmap);
		input.downsample(this.downsample, false, this.downsampleHeight,
				this.downsampleWidth, 1, -1);

		final int winner = this.network.winner(input);
		System.out.println("What is: " + filename + ", it seems to be: "
				+ this.neuron2identity.get(winner));

//		MLData outData = network.compute(input);
//		for (double speed : outData.getData()) {
//			System.out.print(nf.format(speed) + ", ");
//		}
//		System.out.println();

	}

	public void loadsaveWeights() {
		/*        double[] weights = network.getStructure().getFlat().getWeights();
//        writing array to disk
        FileOutputStream f_out = null;
        try {
            f_out = new FileOutputStream("data/myarray.data");
            ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
            obj_out.writeObject (weights);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

            // reading array from disk
    FileInputStream f_in = new FileInputStream("C:\myarray.data");
    ObjectInputStream obj_in = new ObjectInputStream (f_in);
    tmp_array = (int[])obj_in.readObject();
        */

	}


}

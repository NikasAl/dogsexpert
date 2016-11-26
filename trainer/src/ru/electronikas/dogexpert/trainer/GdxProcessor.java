package ru.electronikas.dogexpert.trainer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.encog.Encog;
import org.encog.EncogError;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class GdxProcessor extends ApplicationAdapter {

	private String[] consoleArgs;

	private final Map<String, String> args = new HashMap<String, String>();
	private String line;

	public ImageNeuralNetworkProcessor processor = new ImageNeuralNetworkProcessor();

	public GdxProcessor(String[] consoleArgs) {
		this.consoleArgs = consoleArgs;
	}

	@Override
	public void create () {

		if (consoleArgs.length < 1) {
			System.out
					.println("Must specify command file.  See source for format.");
		} else {
			try {
				execute(consoleArgs[0]);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		Encog.getInstance().shutdown();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.3f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}
	
	@Override
	public void dispose () {
	}


	public void execute(final String file) throws IOException {
		final FileInputStream fstream = new FileInputStream(file);
		final DataInputStream in = new DataInputStream(fstream);
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		while ((this.line = br.readLine()) != null) {
			executeLine();
		}
		in.close();
	}

	public void executeLine() throws IOException {
		final int index = this.line.indexOf(':');
		if (index == -1) {
			throw new EncogError("Invalid command: " + this.line);
		}

		final String command = this.line.substring(0, index).toLowerCase()
				.trim();
		final String argsStr = this.line.substring(index + 1).trim();
		final StringTokenizer tok = new StringTokenizer(argsStr, ",");
		this.args.clear();
		while (tok.hasMoreTokens()) {
			final String arg = tok.nextToken();
			final int index2 = arg.indexOf(':');
			if (index2 == -1) {
				throw new EncogError("Invalid command: " + this.line);
			}
			final String key = arg.substring(0, index2).toLowerCase().trim();
			final String value = arg.substring(index2 + 1).trim();
			this.args.put(key, value);
		}

		processor.executeCommand(command, line, this.args);
	}


}

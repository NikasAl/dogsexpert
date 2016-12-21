package ru.electronikas.dogexpert.trainer;

import org.encog.Encog;
import org.encog.EncogError;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by nikas on 12/21/16.
 * No openGL dependencies to run training
 * Useing to run training on google cloud computing engine or other headless powerful VMs
 */
public class ConsoleTrainer {
    private static ImageNeuralNetworkProcessor processor;
    private static final Map<String, String> args = new HashMap<String, String>();
    private static String line;

    public static void main(String[] consoleArgs) {
        processor = new ImageNeuralNetworkProcessor();

        if (consoleArgs.length < 1) {
            System.out.println("Must specify command file.  See source for format.");
        } else {
            try {
                execute(consoleArgs[0]);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        Encog.getInstance().shutdown();
    }

    private static void execute(final String file) throws IOException {
        final FileInputStream fstream = new FileInputStream(file);
        final DataInputStream in = new DataInputStream(fstream);
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));

        while ((line = br.readLine()) != null) {
            executeLine();
        }
        in.close();
    }

    private static void executeLine() throws IOException {
        final int index = line.indexOf(':');
        if (index == -1) {
            throw new EncogError("Invalid command: " + line);
        }

        final String command = line.substring(0, index).toLowerCase()
                .trim();
        final String argsStr = line.substring(index + 1).trim();
        final StringTokenizer tok = new StringTokenizer(argsStr, ",");
        args.clear();
        while (tok.hasMoreTokens()) {
            final String arg = tok.nextToken();
            final int index2 = arg.indexOf(':');
            if (index2 == -1) {
                throw new EncogError("Invalid command: " + line);
            }
            final String key = arg.substring(0, index2).toLowerCase().trim();
            final String value = arg.substring(index2 + 1).trim();
            args.put(key, value);
        }

        processor.executeCommand(command, line, args);
    }

}

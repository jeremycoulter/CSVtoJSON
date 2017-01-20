package CSVtoJSON;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 */

/**
 * @author jeremycoulter
 * 
 */
public class OutputFile {

	private static String OUTPUT_FILE_PATH;
	private static boolean OUTPUT_FILE_APPEND = false;

	public OutputFile(String outputFilePath) {

		OUTPUT_FILE_PATH = outputFilePath;

	}

	public OutputFile(String outputFilePath, boolean outputFileAppend) {

		OUTPUT_FILE_PATH = outputFilePath;
		OUTPUT_FILE_APPEND = outputFileAppend;

	}

	public void outputToFile(String string) throws IOException {

		FileWriter fileWriter = new FileWriter(OUTPUT_FILE_PATH, OUTPUT_FILE_APPEND);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		printWriter.printf("%s" + "%n", string);
		printWriter.close();

	}
}
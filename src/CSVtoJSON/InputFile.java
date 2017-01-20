package CSVtoJSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 */

/**
 * @author jeremycoulter
 * 
 */
public class InputFile {

	private static String INPUT_FILE_PATH;

	public InputFile(String inputFilePath) {

		INPUT_FILE_PATH = inputFilePath;

	}

	public String[] readFromFile() throws IOException {

		int lines = countLines();
		String[] line = new String[lines];

		FileReader fileReader = new FileReader(INPUT_FILE_PATH);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		for (int i = 0; i < lines; i++)

			line[i] = bufferedReader.readLine();

		bufferedReader.close();

		return line;

	}

	int countLines() throws IOException {

		int lines = 0;

		FileReader fileReader = new FileReader(INPUT_FILE_PATH);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		while ((bufferedReader.readLine()) != null)

			lines++;

		bufferedReader.close();

		return lines;

	}
}
package CSVtoJSON;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 
 */

/**
 * @author jeremycoulter
 * 
 */
public class CSVtoJSON {

	/**
	 * @param args
	 */

	// file system location of input
	public static String INPUT_FILE = "/Users/jeremycoulter/Documents/workspace/CSVtoJSON/sample.csv";

	// do NOT parse lines with null values
	public static boolean NEGATE_NULL_VALUE_LINES = true;

	// do NOT check for integers and floats
	public static boolean HANDLE_ALL_VALUES_AS_STRINGS = false;

	public static String[] COLUMN_NAMES = { "string", "integer", "rational", "whiteSpace", "kabobCase", "camelCase",
			"snakeCase" };

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String[] tokenArray;
		String[] lines;

		String token;
		String line;
		String outputFilePath = INPUT_FILE.substring(0, INPUT_FILE.length() - 4) + "-CVStoJSON.txt";

		boolean valid;
		boolean firstLine = true;

		int column;
		int leading;
		int trailing;

		StringTokenizer stringTokenizer;
		InputFile inputFile;
		OutputFile outputFile;

		// pre-flight file management
		try {

			File file = new File(outputFilePath);

			// remove existing file if necessary
			if (file.exists())
				file.delete();

			file.createNewFile();

		} catch (Exception error) {

			System.out.println(error.getMessage());

		}

		// read from input file and parse to output file
		try {

			inputFile = new InputFile(INPUT_FILE);
			outputFile = new OutputFile(outputFilePath, true);

			lines = inputFile.readFromFile();

			outputFile.outputToFile("[");

			System.out.print("Please Wait: ");

			for (int i = 0; i < lines.length; i++) {

				if (i % 500 == 0)
					// System.out.println((i + 1) + "/" + lines.length);
					System.out.print("|");

				column = 0;
				valid = true;
				tokenArray = new String[COLUMN_NAMES.length];
				stringTokenizer = new StringTokenizer(lines[i], ",");

				// read from input file
				while (stringTokenizer.hasMoreTokens()) {

					token = stringTokenizer.nextToken();

					// trim leading whitespace
					for (leading = 0; leading < token.length(); leading++)
						if (token.charAt(leading) != ' ')
							break;

					token = token.substring(leading, token.length());

					// trim trailing whitespace
					for (trailing = 0; trailing < token.length(); trailing++)
						if (token.charAt(token.length() - 1 - trailing) != ' ')
							break;

					token = token.substring(0, token.length() - trailing);

					tokenArray[column++] = token;
				}

				// negate lines containing null values
				if (NEGATE_NULL_VALUE_LINES)
					for (int j = 0; j < COLUMN_NAMES.length; j++)
						if (tokenArray[j] == null)
							valid = false;

				// if valid, write to the output file
				if (valid) {

					if (firstLine) {

						firstLine = false;
						outputFile.outputToFile("\t{");

					} else
						outputFile.outputToFile("\t}, {");

					for (int k = 0; k < COLUMN_NAMES.length; k++) {

						// if the value is a number, don't use quotes when
						// globally enabled
						if (!HANDLE_ALL_VALUES_AS_STRINGS && tokenArray[k].matches("[-+]?[0-9]*\\.?[0-9]+"))
							line = "\t\t" + COLUMN_NAMES[k] + ": " + tokenArray[k];

						// otherwise, use quotes
						else
							line = "\t\t" + COLUMN_NAMES[k] + ": \"" + tokenArray[k] + "\"";

						// if it's not the last value on the line, add a comma
						if (k != COLUMN_NAMES.length - 1)
							line += ",";

						outputFile.outputToFile(line);

					}
				}
			}

			if (!firstLine)
				outputFile.outputToFile("\t}");

			outputFile.outputToFile("]");

			System.out.print(" Complete!");

		} catch (IOException error) {

			System.out.println(error.getMessage());

		}
	}
}

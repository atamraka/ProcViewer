package uno.edu.plugins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class myFileWriter {

	/**
	 * 
	 * @param file
	 *            file is the name of file in which the data is written
	 * @param content
	 *            content is the string content to write to file Process: opens
	 *            the file in read write mode, find the length of the file and
	 *            append in the file, close the file
	 * @throws IOException
	 * 
	 * 
	 */

	public static void appendAtTheEnd(File file, String content)
			throws IOException {

		Writer out = new OutputStreamWriter(new FileOutputStream(file, true),
				"UTF-8");
		try {
			out.write(content + "\r\n");

		} finally {
			out.close();

		}
	}

	public static void writeFile(String path, String content)
			throws IOException {
		File file = new File(path);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}

}

package uno.edu.plugin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tasklist {
	/**
	 * @runs runs tasklist command to get the list of processes running in the
	 *       system
	 * @return
	 */
	public List<String> gettasklistInfo() {
		Runtime rt = Runtime.getRuntime();
		String line = null;
		List<String> taskListInfo = new ArrayList<String>();
		try { 
			Process proc = rt.exec("tasklist");
			InputStream stdin = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null)
				taskListInfo.add(line);
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);

			// TODO: Process[] procs = Process.GetProcesses();

		} catch (Exception e) {
			// remove later
			e.printStackTrace();
		}
		return taskListInfo;
	}
}

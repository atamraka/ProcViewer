package uno.edu.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import uno.edu.model.ProcInfo;

public class Tasklist {
	List<ProcInfo> ProcInfoList=new ArrayList<ProcInfo>(); // array list;
	Runtime rt = Runtime.getRuntime();
	int exitVal;

	// function to obtain results from executing command "tasklist" and storing
	// them in a list
	public List<String> gettasklistInfo() {
		String line = null;
		List<String> taskListInfo = new ArrayList<String>(); // list to store
																// tasklist info
		try {
			Process proc = rt.exec("C:/windows/system32/tasklist"); // execute
																	// command
																	// tasklist
			InputStream stdin = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			line = br.readLine(); // to skip top blank line
			line = br.readLine(); // to skip test line
			line = br.readLine(); // to skip dotted line
			line = br.readLine(); // to skip root process(System) info
			// loop to read each line from tasklist command and add to list
			while ((line = br.readLine()) != null) {
				taskListInfo.add(line);
			}

			exitVal = proc.waitFor();
			// System.out.println("Process exitValue: " + exitVal);

		} catch (Exception e) {

		}
		
		return taskListInfo;
	}

	// Print procInfoList elements
	public void printProcInfo(List<ProcInfo> ProcInfoList) {
		for (int i = 0; i < ProcInfoList.size(); i++) {
			System.out.print(ProcInfoList.get(i).getPname() + ", ");
			System.out.print(ProcInfoList.get(i).getPid() + ", ");
			System.out.print(ProcInfoList.get(i).getPpid());
			System.out.println();
		}
	}

	// print taskListInfo elements
	public void printTaskInfo(List<String> taskListInfo) {
		for (int i = 0; i < taskListInfo.size(); i++) {
			System.out.println(taskListInfo.get(i));
		}
	}

	// to check if string can be parsed to integer
	public boolean isParsable(String s) {
		boolean parsable = true;
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}

	// to split tokens of info read from tasklist command
	public List<ProcInfo> spiltTokens(List<String> taskListInfo) {

		// check if list size is greater than 0(i.e. if process info is read)
		// then execute the loop
		if (taskListInfo.size() > 0) {
			for (int i = 0; i < taskListInfo.size(); i++) {
				StringTokenizer st = new StringTokenizer(taskListInfo.get(i));
				String p_name = st.nextToken(); // to store process name
				String p_pid = st.nextToken();
				// get next token till you find integer (process name could have
				// spaces)
				while (!isParsable(p_pid)) {
					p_pid = st.nextToken();
				}
				int pid = Integer.parseInt(p_pid); // to store pid of process
				get_ppid(pid, p_name); // call function to get ppid and store
										// info
			}
			
		} else {
			System.out.println("Nothing read from tasklist");
			
		}
		return ProcInfoList;
	}

	// to get ppid of given pid
	public void get_ppid(int pid, String p_name) {
		String ppid_line = null;
		// string to store command to execute to get ppid of process
		String cmd = String
				.format("C:/windows/system32/wbem/wmic process where (processid= %s) get parentprocessid",
						pid);

		try {
			Process proc_ppid = rt.exec(cmd);
			InputStream stdin = proc_ppid.getInputStream();
			InputStreamReader isr_ppid = new InputStreamReader(stdin);
			BufferedReader br_ppid = new BufferedReader(isr_ppid);
			br_ppid.readLine(); // to skip parent processid header
			br_ppid.readLine(); // to skip empty line after header
			ppid_line = br_ppid.readLine(); // "123              " is the output
			StringTokenizer st1 = new StringTokenizer(ppid_line);
			int ppid = Integer.parseInt(st1.nextToken()); // to get ppid and
															// convert to int
			addInfo(p_name, pid, ppid); // call function to add information in a
										// list
			exitVal = proc_ppid.waitFor();
		} catch (NoSuchElementException e) {
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Waiting.....");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Nothing Is Read");
		}
		
	}

	// to add information obtained in a list of objects
	public void addInfo(String p_name, int pid, int ppid) {
		ProcInfoList.add(new ProcInfo(p_name, pid, ppid)); // add object to list
															// giving name, pid
															// and ppid
	}

}
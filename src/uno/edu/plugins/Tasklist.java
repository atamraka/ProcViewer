package uno.edu.plugins;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import uno.edu.model.ProcInfo;

public class Tasklist {
//
//	private List<String> taskListInfo;
//
//	public List<String> getTaskListInfo() {
//		return taskListInfo;
//	}
//
//	public void setTaskListInfo(List<String> taskListInfo) {
//		this.taskListInfo = taskListInfo;
//	}

	/**
	 * @runs runs tasklist command to get the list of processes 
	 * @return List<String> ==>information about each process in a String
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
			// int exitVal = proc.waitFor();
			
			// System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// remove later
			e.printStackTrace();
		}
		return taskListInfo;
	}

	public List<ProcInfo> generateProcInfo(List<String> taskListInfo) {
		String ppid_line;
		/**
		 * list to store the pid, process name and ppid of the process
		 */
		List<ProcInfo> ProcInfoList = new ArrayList<ProcInfo>();
		Runtime rt = Runtime.getRuntime();
		// String token="" ;
		try {
			for (int taskCount = 4; taskCount < taskListInfo.size(); taskCount++) {
				StringTokenizer st = new StringTokenizer(
						taskListInfo.get(taskCount));
				String p_name = st.nextToken(); // to store process name
				int p_pid = Integer.parseInt(st.nextToken()); // to store pid
				String cmd = String
						.format("System32/wbem/wmic process where (processid= %s) get parentprocessid",
								p_pid);
				Process proc_ppid = rt.exec(cmd);
				InputStream stdin = proc_ppid.getInputStream();
				InputStreamReader isr_ppid = new InputStreamReader(stdin);
				BufferedReader br_ppid = new BufferedReader(isr_ppid);
				br_ppid.readLine(); // to skip parentprocessid
				br_ppid.readLine(); // to skip empty line
				ppid_line = br_ppid.readLine(); // "123" is the output
				StringTokenizer st1 = new StringTokenizer(ppid_line);
				int p_ppid = Integer.parseInt(st1.nextToken());
				ProcInfoList.add(new ProcInfo(p_name, p_pid, p_ppid));
			}
		} catch (Exception e) {
//			e.printStackTrace();

		}
		return ProcInfoList;
	}
	
	public void printProcInfoList(List<ProcInfo> ProcInfoList) {
		for (int i = 0; i < ProcInfoList.size(); i++) {
			System.out.print(ProcInfoList.get(i).getPname() + ", ");
			System.out.print(ProcInfoList.get(i).getPid() + ", ");
			System.out.print(ProcInfoList.get(i).getPpid());
			System.out.println();
		}

	}

//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for (int index = 0; index < this.taskListInfo.size(); index++) {
//			sb.append(this.taskListInfo.get(index) + "\n");
//		}
//		String printString = sb.toString();
//		sb = null;
//		return printString;
//	}
}
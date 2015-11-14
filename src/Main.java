import java.util.List;

import uno.edu.plugins.Tasklist;

public class Main {

	public static void main(String[] args) {
		Tasklist tasklister = new Tasklist();
		List<String> tasklistInfo = tasklister.gettasklistInfo();
		List<ProcInfo> procInfoList = tasklister.spiltTokens(tasklistInfo);
		Hierarchy hi = new Hierarchy(procInfoList);
		hi.runMethods();
	}

}
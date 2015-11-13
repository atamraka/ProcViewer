import java.util.List;

import uno.edu.model.ProcInfo;
import uno.edu.plugins.Hierarchy;
import uno.edu.plugins.Tasklist;

public class MainClass {

	public static void main(String[] args) {
		Tasklist tasklister = new Tasklist();
		List<String> tasklistInfo = tasklister.gettasklistInfo();
		List<ProcInfo> procInfoList = tasklister.generateProcInfo(tasklistInfo);
		// tasklister.printProcInfoList(procInfoList);
		Hierarchy hi = new Hierarchy(procInfoList);
		hi.runMethods();
	
		
	}

}

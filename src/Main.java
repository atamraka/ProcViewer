import java.util.List;

import uno.edu.model.Tasklist;

public class Main {

	public static void main(String[] args) {
		Tasklist tasklister = new Tasklist();
		List<String> tasklistInfo = tasklister.gettasklistInfo();
		//tasklister.print(tasklistInfo);
		tasklister.spiltTokens(tasklistInfo);
	}

}
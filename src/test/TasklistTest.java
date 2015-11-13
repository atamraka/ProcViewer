package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uno.edu.model.ProcInfo;
import uno.edu.model.Tasklist;

public class TasklistTest {
	Tasklist tasklist;
	
	@Before
	public void setup() {
		tasklist = new Tasklist();
	}

	@Test
	public void testGettasklistInfo() {
		
		assert tasklist.gettasklistInfo().size()>0;
	}

	@Test
	public void testSpiltTokens1() {
		List<String> taskListInfo = new ArrayList<String>();
		taskListInfo.add("System Idle Process              0 Services                   0         24 K");
		assert tasklist.spiltTokens(taskListInfo)==1;
		assert tasklist.addInfo("SystemIdleProcess",4,0).size()==1;
		taskListInfo.clear();	
	}
	
	@Test
	public void testSpiltTokens2() {
		List<String> taskListInfo = new ArrayList<String>();
		taskListInfo.add("");
		assert tasklist.spiltTokens(taskListInfo)==0;
		taskListInfo.clear();
	}

	
	

}

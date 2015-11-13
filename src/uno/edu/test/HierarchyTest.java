package uno.edu.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import uno.edu.model.ProcInfo;
import uno.edu.plugins.Hierarchy;

public class HierarchyTest {
	List<ProcInfo> procinfoList = new ArrayList<ProcInfo>();
	List<ProcInfo> emptyProcinfoList = new ArrayList<ProcInfo>();
	List<ProcInfo> procListNoOrphan = new ArrayList<ProcInfo>();
	Hierarchy hierarchyOrphan;
	Hierarchy hierarchyNoOrphan;
	Hierarchy hierarchyEmpty;
	
	@Before
	public void setUpOrphan() throws Exception {
		procinfoList.add(new ProcInfo("Proc1", 1, 7));
		procinfoList.add(new ProcInfo("Proc2", 2, 3));
		procinfoList.add(new ProcInfo("Proc3", 3, 9));
		procinfoList.add(new ProcInfo("Proc4", 4, 7));
		procinfoList.add(new ProcInfo("Proc5", 5, 1));
		procinfoList.add(new ProcInfo("Proc6", 6, 7));
		procinfoList.add(new ProcInfo("Proc7", 7, 0));
		procinfoList.add(new ProcInfo("Proc8", 8, 5));
		procinfoList.add(new ProcInfo("Proc9", 9, 7));
		procinfoList.add(new ProcInfo("Proc10", 10, 11));	//Process Proc10 is an orphan process
		hierarchyOrphan=new Hierarchy(procinfoList);
	}
	
	@Before
	public void setUpNoOrphan() throws Exception {
		/**
		 * ======= procListNoOrphan contains no orphan processes ==================
		 */
		procListNoOrphan.add(new ProcInfo("Proc1", 1, 7));
		procListNoOrphan.add(new ProcInfo("Proc2", 2, 3));
		procListNoOrphan.add(new ProcInfo("Proc3", 3, 9));
		procListNoOrphan.add(new ProcInfo("Proc4", 4, 7));
		procListNoOrphan.add(new ProcInfo("Proc5", 5, 1));
		procListNoOrphan.add(new ProcInfo("Proc6", 6, 7));
		procListNoOrphan.add(new ProcInfo("Proc7", 7, 0));
		procListNoOrphan.add(new ProcInfo("Proc8", 8, 5));
		procListNoOrphan.add(new ProcInfo("Proc9", 9, 7));
		hierarchyNoOrphan=new Hierarchy(procListNoOrphan);
	}
	
	@Before
	public void setUpEmpty() throws Exception {
		/**
		 * =======Hierarchy with no processes ==================
		 */
		hierarchyEmpty=new Hierarchy(emptyProcinfoList);
	}

	@Test
	public void testOrphanHierarchy() {
		assert hierarchyOrphan!=null;
		assert this.procinfoList.size()==10;
	}

	@Test
	public void testEmptyHierarchy() {
		assert (this.emptyProcinfoList==null || this.emptyProcinfoList.size()==0);
	}
	
	@Test
	public void testNoOrphanHierarchy() {
		assert hierarchyNoOrphan!=null;
		assert this.procListNoOrphan.size()==9;
	}
	
	@Test
	public void testOrphanRunMethods() {
		assert hierarchyOrphan.runMethods()==true;		
	}
	
	@Test
	public void testEmptyRunMethods(){
		assert hierarchyEmpty.runMethods()==false;
	}
	
	@Test
	public void testNoOrphanRunMethods(){
		assert hierarchyNoOrphan.runMethods()==true;
	}

	@Test
	public void testOrphan_ManageHierarchy() {
		assert hierarchyOrphan.manageHierarchy()!=null;
	}

	@Test
	public void testNoOrphan_ManageHierarchy() {
		assert hierarchyNoOrphan.manageHierarchy()!=null;
	}

	@Test
	public void testOrphan_FindOrphanProcesses() {
		//================ test for orphan processes ================
		assert hierarchyOrphan.FindOrphanProcesses(new ArrayList<ProcInfo>(this.procinfoList)).size()==1 ;
		assert hierarchyOrphan.FindOrphanProcesses(new ArrayList<ProcInfo>(this.procinfoList)).contains(10)==true;		
	}

	@Test
	public void testNoOrphan_FindOrphanProcesses() {
		//================ test for no orphan processes ================
		assert hierarchyNoOrphan.FindOrphanProcesses(new ArrayList<ProcInfo>(this.procListNoOrphan)).size()==0 ;
		assert hierarchyNoOrphan.FindOrphanProcesses(new ArrayList<ProcInfo>(this.procListNoOrphan)).contains(10)==false;
	}

	@Test
	public void testWrapxmlTree() {
		assert hierarchyOrphan.wrapxmlTree(null)==null;
		assert hierarchyOrphan.wrapxmlTree("")==null;
		assert hierarchyOrphan.wrapxmlTree("<branch><attribute name='name' value='proc_0'/></branch>").length()>0;
	}

	@Test
	public void testOrphan_GetChildInfoWithParent() {
		List<Integer> expectedSearchResult=new ArrayList<Integer>();
		expectedSearchResult.add(0);
		expectedSearchResult.add(3);
		expectedSearchResult.add(5);
		expectedSearchResult.add(8);
		assert hierarchyOrphan.getChildInfoWithParent(7).size()==4;
		assert hierarchyOrphan.getChildInfoWithParent(7).containsAll(expectedSearchResult)==true;
		expectedSearchResult.add(1);
		assert hierarchyOrphan.getChildInfoWithParent(7).containsAll(expectedSearchResult)==false;		
	}
	
	@Test
	public void testNoOrphan_GetChildInfoWithParent() {
		List<Integer> expectedSearchResult=new ArrayList<Integer>();
		expectedSearchResult.add(0);
		expectedSearchResult.add(3);
		expectedSearchResult.add(5);
		expectedSearchResult.add(8);
		assert hierarchyNoOrphan.getChildInfoWithParent(7).size()==4;
		assert hierarchyNoOrphan.getChildInfoWithParent(7).containsAll(expectedSearchResult)==true;
		expectedSearchResult.add(1);
		assert hierarchyNoOrphan.getChildInfoWithParent(7).containsAll(expectedSearchResult)==false;		
	}

}

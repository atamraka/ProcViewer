package uno.edu.plugins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uno.edu.model.ProcInfo;
import uno.edu.model.myTreeHierarchy;
import demos.TreeView;

public class Hierarchy {
	String filepath = "E:\\mytest1.xml";
	List<ProcInfo> procinfoList = null;
	String START_TREE = "<tree>";
	String END_TREE = "</tree>";
	String START_BRANCH = "<branch>";
	String END_BRANCH = "</branch>";
	String START_ATTR = "<attribute";
	String END_ATTR = "'/>";
	String START_LEAF = "<leaf>";
	String END_LEAF = "</leaf>";
	String COMMON_LINE = " name='name' value='";

	public Hierarchy(List<ProcInfo> procinfoList_) {
		this.procinfoList = procinfoList_;
	}

	/**
	 * 
	 * @return
	 */
	public boolean runMethods() {
		if (this.procinfoList != null && this.procinfoList.size() > 0) {
			List<ProcInfo> duplicatelist = new ArrayList<ProcInfo>(
					this.procinfoList);
			FindOrphanProcesses(duplicatelist);
			sortWithParentID();
			// System.out
			// .println("==================== After sorting ========================");
			// printProcInfo(this.procinfoList);
			myTreeHierarchy myTree = manageHierarchy();
			tagText(myTree);
			String xmlTree = wrapxmlTree(sb.toString());
			// System.out.println(xmlTree);
			// AppendToFile
			// write xml tree to a file
			if (xmlTree != null) {
				try {
					myFileWriter.writeFile(filepath, xmlTree);
					String input[] = { filepath, "name" };
					TreeView.main(input);
				} catch (IOException e) {
					System.err.println("File not created");
				}
			}
			return true;
		} else {
			System.err.println("No process detected!!");
			return false;
		}

	}

	public myTreeHierarchy manageHierarchy() {
		// ppid of first/root process
		Integer rootPid = this.procinfoList.get(0).getPpid();
		ProcInfo rootP = new ProcInfo("ROOT", rootPid, -1);
		myTreeHierarchy root = new myTreeHierarchy(rootP);
		Integer currentPid = rootPid;
		Integer prevPid = -1000000;// root process has no parent
		myTreeHierarchy currentNode = root;
		myTreeHierarchy prevNode;
		buildTree(currentNode);
		return currentNode;
	}

	/**
	 * get ppid and unless it is 0, check if its process is alive=> if parent
	 * process is not alive, then make a strategy to connect it to the root 0
	 * process
	 * 
	 * @param xmlTree
	 * @return
	 */

	public Set FindOrphanProcesses(List<ProcInfo> procinfoList_) {
		// System.out.println("==============ORPHAN PROCESSES =================");
		Collections.sort(procinfoList_, new processIDComparator());
		Set orphanPidList = new HashSet();
		ProcInfo p;
		for (int i = 0; i < procinfoList_.size(); i++) {
			int search_ppid = procinfoList_.get(i).getPpid();
			if (search_ppid != 0) {
				p = new ProcInfo("", search_ppid, -1);
				int beginIndex = Collections.binarySearch(procinfoList_, p,
						new processIDComparator());
				if (beginIndex < 0) {
					// System.out.println(search_ppid);
					if (!orphanPidList.contains(search_ppid)) {
						orphanPidList.add(search_ppid);
						this.procinfoList.add(new ProcInfo("DEAD", search_ppid,
								0));
					}

				}

			}
		}
		return orphanPidList;
	}

	public String wrapxmlTree(String xmlTree) {
		if (xmlTree!=null && xmlTree.length() > 0) {
			String wrapText = "<tree>\n<declarations><attributeDecl name='name' type='String'/></declarations>\n";
			return wrapText + xmlTree + "</tree>";
		} else {
			return null;
		}
	}

	public void buildTree(myTreeHierarchy currentNode) {
		List<Integer> childOfCurrentNode = getChildInfoWithParent(currentNode
				.getProcInfo().getPid());
		if (childOfCurrentNode != null && childOfCurrentNode.size() > 0) {
			for (int ii = 0; ii < childOfCurrentNode.size(); ii++) {
				myTreeHierarchy childNode = new myTreeHierarchy(
						this.procinfoList.get(childOfCurrentNode.get(ii)));
				currentNode.addChild(childNode);
				buildTree(childNode);
			}
		}
	}

	/**
	 * 
	 * @param currentNode
	 *            holds the tree structure of the process list
	 */

	public String tagAttribute(myTreeHierarchy currentNode) {

		int pid;
		StringBuilder sb = new StringBuilder();
		if (currentNode != null
				&& (pid = currentNode.getProcInfo().getPid()) >= 0) {
			sb.append(START_ATTR + COMMON_LINE
					+ currentNode.getProcInfo().getPname() + "_" + pid
					+ END_ATTR);
		}
		return sb.toString();
	}

	StringBuilder sb = new StringBuilder();
	myTreeHierarchy childNode = null;
	int tabCount = 0;
	String tab = "";
	String endTaB = "";

	public void tagText(myTreeHierarchy currentNode) {
		boolean isBranchOpen = false;

		for (int tabCnt = 0; tabCnt < tabCount; tabCnt++) {
			tab = tab + "\t";
		}
		if (currentNode != null) {
			// start the branch here

			if (currentNode.getChildList() == null) {
				// this is a leaf then
				sb.append(tab + START_LEAF + tagAttribute(currentNode)
						+ END_LEAF + "\n");
				// System.out.println(tab+START_LEAF + tagAttribute(currentNode)
				// + END_LEAF + "\n");

			} else {
				if (currentNode.getChildList().size() == 0) {
					// this is leaf node
					sb.append(tab + START_LEAF + tagAttribute(currentNode)
							+ END_LEAF + "\n");
					// System.out.println(tab+START_LEAF +
					// tagAttribute(currentNode)
					// + END_LEAF + "\n");
				} else {
					// not the child node

					sb.append(tab + START_BRANCH + "\n" + tab
							+ tagAttribute(currentNode) + "\n");

					// System.out.println(tab+START_BRANCH + "\n"+tab
					// + tagAttribute(currentNode) + "\n");
					if (currentNode.getChildList() != null) {
						tabCount += 1;
					}
					for (int i = 0; currentNode.getChildList() != null
							&& i < currentNode.getChildList().size(); i++) {

						childNode = currentNode.getChildList().get(i);
						tab = "";
						tagText(childNode);
					}
					tabCount = tabCount - 1;

					for (int tabCnt = 0; tabCnt < tabCount; tabCnt++) {
						endTaB = endTaB + "\t";
					}
					// System.out.println(currentNode.getPname()+"=="+tabCount);
					sb.append(endTaB + END_BRANCH + "\n");
					// System.out.println(endTaB+END_BRANCH + "\n");
					endTaB = "";

				}

			}
		}

	}

	public List<Integer> getChildInfoWithParent(Integer search_ppid) {
		ProcInfo p = new ProcInfo("", -1000, search_ppid);
		int beginIndex = Collections.binarySearch(procinfoList, p,
				new parentComparator());
		List<Integer> searchResult = null;
		if (beginIndex >= 0) {
			searchResult = new ArrayList<Integer>();
		}
		int parent = search_ppid;
		// iterate for all processes that share same parent and are above the
		// found index
		for (int i = beginIndex - 1; searchResult != null
				&& i < procinfoList.size() && i >= 0; i--) {
			if (procinfoList.get(i).getPpid() == search_ppid) {
				searchResult.add(i);
			} else {
				break;
			}
		}
		// iterate for all processes that share same parent and are below the
		// found index
		for (int i = beginIndex; searchResult != null
				&& i < procinfoList.size(); i++) {
			if (procinfoList.get(i).getPpid() == search_ppid) {
				searchResult.add(i);
			} else {
				break;
			}
		}

		return searchResult;
	}

	public void printProcInfo(List<ProcInfo> procinfoList_) {
		for (int index = 0; index < procinfoList_.size(); index++) {
			System.out.println(procinfoList_.get(index));
		}
	}

	public void sortWithParentID() {
		Collections.sort(this.procinfoList, new parentComparator());
	}
}

class parentComparator implements Comparator<ProcInfo> {

	@Override
	public int compare(ProcInfo proc1, ProcInfo proc2) {
		return proc1.getPpid() - proc2.getPpid();
	}

}

class processIDComparator implements Comparator<ProcInfo> {

	@Override
	public int compare(ProcInfo proc1, ProcInfo proc2) {
		return proc1.getPid() - proc2.getPid();
	}

}
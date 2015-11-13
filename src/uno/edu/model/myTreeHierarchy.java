package uno.edu.model;

import java.util.ArrayList;
import java.util.List;

public class myTreeHierarchy {
	private ProcInfo procInfo;
	private List<myTreeHierarchy> list;

	public myTreeHierarchy(ProcInfo procInfo_) {
		assert procInfo_!=null;
		assert procInfo_.getPid()>=0;
		assert procInfo_.getPname()!=null;
		this.procInfo=procInfo_;
	}

	public void addChild(myTreeHierarchy myTreeHierarchyChild) {
		if (list == null) {
			list = new ArrayList<myTreeHierarchy>();
			list.add(myTreeHierarchyChild);
		} else if (list.size() >= 0) {
			list.add(myTreeHierarchyChild);
		}
	}

	public ProcInfo getProcInfo() {
		return this.procInfo;
	}

	public List<myTreeHierarchy> getChildList() {
		return list;
	}

	public void setChildList(List<myTreeHierarchy> list) {
		this.list = list;
	}

}

package uno.edu.model;

public class ProcInfo {

	private int pid;
	private int ppid;
	private String pname;

	protected ProcInfo(String pname_, int pid_, int ppid_) {
		this.pid = pid_;
		this.pname = pname_;
		this.ppid = ppid_;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getPpid() {
		return ppid;
	}

	public void setPpid(int ppid) {
		this.ppid = ppid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
}

package net.mips.interpreter;

public class Instruction {
	Mnemonique mne;
	int suite;
	
	public Instruction(Mnemonique mne, int suite) {
		super();
		this.mne = mne;
		this.suite = suite;
	}
	public Instruction() {
		super();
	}

	public Mnemonique getMne() {
		return mne;
	}
	public void setMne(Mnemonique mne) {
		this.mne = mne;
	}
	public int getSuite() {
		return suite;
	}
	public void setSuite(int suite) {
		this.suite = suite;
	}
	
	

}

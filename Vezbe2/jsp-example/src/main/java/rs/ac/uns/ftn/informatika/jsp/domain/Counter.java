package rs.ac.uns.ftn.informatika.jsp.domain;

public class Counter {

	private int count;

	public Counter() {

	}

	public int getAndInc() {
		return count++;
	}

	public int incAndGet() {
		return ++count;
	}

	public void inc() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

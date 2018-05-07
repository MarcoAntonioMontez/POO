package pec;

public interface IPEC<T>{
	void add(T t);
	T removeFirst();
	void remove(T t);
}

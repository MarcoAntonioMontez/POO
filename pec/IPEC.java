package pec;

/**
 * @author n 78508 Marco Montez, n 79021 Tomas Cordovil, n 78181 Joao Alves.
 * IPEC interface that implements the methods to use on the Events PEC 
 * @param T genericClass
 */
public interface IPEC<T>{
	void add(T t);
	T removeFirst();
	void remove(T t);
}

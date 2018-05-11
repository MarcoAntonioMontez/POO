package pec;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
 */

/**
 * IPEC interface that implements the methods to use on the Events PEC 
 * @param T genericClass
 */
public interface IPEC<T>{
	void add(T t);
	T removeFirst();
	void remove(T t);
}

package apace.utils;

import java.util.Observable;

public class Property<T> extends Observable implements Cloneable {

	private T value;
	
	public Property(T t) {
		value = t;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T t) {
		T old = value;
		value = t;
		setChanged();
		notifyObservers(old);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Property<T> clone() {
		try {
			return (Property<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Cloning of Property<T> not supported.");
		}
	}
}

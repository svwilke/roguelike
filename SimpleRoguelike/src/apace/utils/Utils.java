package apace.utils;

import java.util.List;

import apace.core.Logic;

public class Utils {

	public static <T> T choice(List<T> list) {
		return list.get(Logic.random.nextInt(list.size()));
	}
	
	public static <T> T choice(T[] array) {
		return array[Logic.random.nextInt(array.length)];
	}
	
	public static boolean anyEqual(Object[] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array.length; j++) {
				if(i != j) {
					if(array[i].equals(array[j])) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

package apace.utils;

import apace.lib.Reference;

public class StringHelper {

	public static int getWidth(String string) {
		float w = (float)string.length() / 2f;
		for(char c : string.toCharArray()) {
			if(c >= 'À' && c <= 'Ù') {
				w++;
			}
		}
		return (int)(w * Reference.TILE_SIZE);
	}
}

package apace.gameplay.map.generator;

import apace.gameplay.map.Map;

public interface IMapGenerator {

	public void generate(Map map, int startX, int startY, int level);
}

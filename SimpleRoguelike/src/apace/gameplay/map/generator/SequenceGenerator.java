package apace.gameplay.map.generator;

import apace.gameplay.map.Map;

public class SequenceGenerator implements IMapGenerator {

	private IMapGenerator[] generators;
	
	public SequenceGenerator(IMapGenerator... generators) {
		this.generators = generators;
	}
	
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		for(int i = 0; i < generators.length; i++) {
			generators[i].generate(map, startX, startY, level);
		}
	}

}

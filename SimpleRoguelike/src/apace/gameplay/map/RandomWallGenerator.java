package apace.gameplay.map;

import apace.core.Logic;
import apace.gameplay.actor.ActorEnemy;
import apace.lib.Enemies;
import apace.lib.Sprites;
import apace.lib.Tiles;
import apace.utils.Position;

public class RandomWallGenerator implements IMapGenerator {

	@Override
	public void generate(Map map, int startX, int startY, int level) {
		int width = map.getWidth();
		int height = map.getHeight();

		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(i == startX && j == startY) {
					map.setTile(new Position(i, j), Tiles.STAIRS_DOWN);
					continue;
				}
				
				if(i == 0 || j == 0 || i == width - 1 || j == height - 1) {
					map.setTile(new Position(i, j), Tiles.WALL);
				} else {
					switch(Logic.random.nextInt(7)) {
					case 2: case 3:
						map.setTile(new Position(i, j), Tiles.WALL);
						break;
					//case 5:
						//map.setTile(new Position(i, j), Logic.random.nextBoolean() ? Tiles.DOOR : Tiles.VASE_SMALL);
						//break;
					default:
						map.setTile(new Position(i, j), Tiles.FLOOR);
						break;
					}
				}
				
			}
		}
		Position pUp = new Position(1, 1).add(Position.random(width - 2, height - 2));
		map.setTile(pUp, Tiles.STAIRS_UP);
		pUp = new Position(1, 1).add(Position.random(width - 2, height - 2));
		map.setTile(pUp, Tiles.CHEST_LARGE);
		for(int i = 0; i < 3; i++) {
			pUp = new Position(1, 1).add(Position.random(width - 2, height - 2));
			map.addActor(pUp, Enemies.SLIME_SMALL.clone());
		}
	}

}

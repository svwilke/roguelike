package apace.gameplay.map.generator;

import apace.core.Logic;
import apace.gameplay.actor.Actor;
import apace.gameplay.actor.ActorLiving;
import apace.gameplay.map.Map;
import apace.lib.Enemies;
import apace.utils.Position;
import apace.utils.Utils;

public class MonsterRoomDecorator implements IRoomDecorator {

	@Override
	public void generate(Map map, Room room) {
		int monsterCount = Logic.random.nextInt(3) + 1;
		Integer[] r = new Integer[monsterCount];
		int roomSize = room.w * room.h;
		for(int i = 0; i < monsterCount; i++) {
			r[i] = Logic.random.nextInt(roomSize);
		}
		while(Utils.anyEqual(r)) {
			r[Logic.random.nextInt(monsterCount)] = Logic.random.nextInt(roomSize);
		}
		for(int i = 0; i < monsterCount; i++) {
			for(Position p : room) {
				if(r[i] <= 0) {
					map.addActor(p, selectMonster());
					break;
				} else {
					r[i]--;
				}
			}
		}
	}
	
	private Actor selectMonster() {
		return Utils.choice(new ActorLiving[] {Enemies.SLIME_SMALL, Enemies.SLIME_SMALL, Enemies.SLIME_SMALL, Enemies.SLIME_LARGE, Enemies.GROO}).clone();
	}

}

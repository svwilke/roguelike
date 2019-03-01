package apace.lib;

import java.util.LinkedList;
import java.util.List;

import apace.core.Game;
import apace.core.Logic;
import apace.drawing.Palette;
import apace.drawing.PaletteSwap;
import apace.drawing.Splash;
import apace.gameplay.ITurnTaker;
import apace.gameplay.actor.Actor;
import apace.gameplay.actor.ActorLiving;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.gameplay.map.TileChest;
import apace.gameplay.map.TileDoor;
import apace.gameplay.map.TileHole;
import apace.gameplay.map.TileProcess;
import apace.gameplay.map.TileStairs;
import apace.gameplay.map.TileWall;
import apace.process.FlashAnimation;
import apace.process.IProcessable;
import apace.process.MultiProcess;
import apace.process.ScheduledCall;
import apace.process.SequenceProcess;
import apace.process.SpriteAnimation;
import apace.utils.Direction;
import apace.utils.Position;

public class Tiles {

	public static Tile FLOOR = new Tile(Sprites.FLOOR, true, false, false);
	public static TileWall WALL = new TileWall();
	public static TileChest CHEST_LARGE = new TileChest(Sprites.CHEST_CLOSED_LARGE, Sprites.CHEST_OPEN_LARGE);
	public static TileChest CHEST_SMALL = new TileChest(Sprites.CHEST_CLOSED_SMALL, Sprites.CHEST_OPEN_SMALL);
	public static TileStairs STAIRS_UP = new TileStairs();
	public static Tile STAIRS_DOWN = new Tile(Sprites.STAIRS_DOWN, true, false);
	public static TileDoor DOOR = new TileDoor();
	public static Tile VASE_SMALL = new Tile(Sprites.VASE_SMALL, false, false);
	public static TileHole HOLE = new TileHole();
	public static Tile CARPET = new Tile(Sprites.CARPET, true, false, false);
	public static Tile BOMB = new TileProcess(false, false, new ITurnTaker() {

		@Override
		public IProcessable takeTurn(Map map, Position position) {
			// TODO: Make the explosion effects play before all other actors move. HOW?!
			List<IProcessable> anims = new LinkedList<IProcessable>();
			boolean hadHit = false;
			for(int i = 0; i < 4; i++) {
				Direction d = Direction.values()[i];
				Position p = d.from(position);
				boolean hitWall = false;
				while(!hitWall) {
					if(map.isInBounds(p) && (!map.isOpaque(p) || map.hasActor(p))) {
						if(map.hasActor(p)) {
							Actor a = map.getActor(p);
							if(a instanceof ActorLiving) {
								int dmg = Game.player.getAttackValue();
								((ActorLiving) a).damage(dmg);
								Splash hpInfo = new Splash(p.getX(), p.up().getY(), "-" + dmg);
								hpInfo.setTextColor(Palette.ORANGE);
								hpInfo.show();
								hadHit = true;
								IProcessable hitAnim = new FlashAnimation(a, 10, PaletteSwap.WHITE);
								if(((ActorLiving) a).isDead()) {
									hitAnim = new SequenceProcess(hitAnim, new ScheduledCall(() -> map.removeActor(a.getPosition(), a)));
								}
								anims.add(hitAnim);
							}
						}
						anims.add(new SpriteAnimation(Sprites.EXPLOSION, 20, p.getX(), p.getY()));
						p = d.from(p);
					} else {
						hitWall = true;
					}
				}
			}
			if(map.hasActor(position)) {
				Actor a = map.getActor(position);
				if(a instanceof ActorLiving) {
					int dmg = 1;
					((ActorLiving) a).damage(dmg);
					Splash hpInfo = new Splash(position.getX(), position.up().getY(), "-" + dmg);
					hpInfo.setTextColor(Palette.ORANGE);
					hpInfo.show();
					hadHit = true;
					IProcessable hitAnim = new FlashAnimation(a, 10, PaletteSwap.WHITE);
					if(((ActorLiving) a).isDead()) {
						hitAnim = new SequenceProcess(hitAnim, new ScheduledCall(() -> map.removeActor(a.getPosition(), a)));
					}
					anims.add(hitAnim);
				}
			}
			map.setTile(position, Tiles.FLOOR);
			anims.add(new SpriteAnimation(Sprites.EXPLOSION, 20, position.getX(), position.getY()));
			if(hadHit) {
				Sounds.HIT.play();
			}
			Logic.push(new MultiProcess(anims));
			return null;
			//return new MultiProcess(anims);
		}

		@Override
		public int getPriority() {
			return 0;
		}
		
	}, Sprites.BOMB_0, Sprites.BOMB_1, Sprites.BOMB_2, Sprites.BOMB_3);
}

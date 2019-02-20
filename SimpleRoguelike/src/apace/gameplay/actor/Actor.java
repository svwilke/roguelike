package apace.gameplay.actor;

import apace.drawing.Sprite;
import apace.gameplay.IInteractable;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.lib.Reference;
import apace.lib.Sounds;
import apace.lib.Tiles;
import apace.process.OffsetAnimation;
import apace.process.IProcessable;
import apace.process.MultiProcess;
import apace.process.SequenceProcess;
import apace.utils.Direction;
import apace.utils.Position;

public class Actor extends Tile {
	
	protected Position position;
	
	public Actor(Sprite sprite, boolean walkable, boolean opaque) {
		super(sprite, walkable, opaque);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public IProcessable tryMove(Direction direction, Map map) {
		int s = Reference.TILE_SIZE;
		Position newPos = direction.from(position);
		IProcessable anim = null;
		int targetX = direction.getX() * s / 2;
		int targetY = direction.getY() * s / 2;
		anim = new SequenceProcess(new OffsetAnimation(position, 0, 0, targetX, targetY).setSpeed(0.3f), new OffsetAnimation(position, targetX, targetY, 0, 0).setSpeed(0.3f));
		if(map.isWalkable(newPos)) {
			moveTo(newPos, map);
			position.setOffset(direction.getX() * -s, direction.getY() * -s);
			anim = new OffsetAnimation(position, direction.getX() * -s, direction.getY() * -s, 0, 0);
			if(direction == Direction.LEFT) {
				setFlipX(true);
			} else
			if(direction == Direction.RIGHT) {
				setFlipX(false);
			}
			Sounds.WALK.play();
		} else
		if(map.isInteractable(newPos)){
			IInteractable interactable = map.getInteractable(newPos);
			IProcessable interaction = interactable.interact(map, this, newPos);
			anim = new MultiProcess(anim, interaction);
		} else {
			Sounds.BUMP.play();
			anim = new SequenceProcess(new OffsetAnimation(position, 0, 0, targetX / 2, targetY / 2).setSpeed(0.4f), new OffsetAnimation(position, targetX / 2, targetY / 2, 0, 0).setSpeed(0.4f));
			if(map.hasTile(newPos)) {
				map.setTile(newPos, Tiles.FLOOR);
			}
		}
		return anim;
	}
	
	public void moveTo(Position position, Map map) {
		map.moveActor(this.position, position);
		this.setPosition(position);
	}
}

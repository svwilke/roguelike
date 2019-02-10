package apace.gameplay;

import apace.gameplay.actor.Actor;
import apace.gameplay.map.Map;
import apace.process.IProcessable;
import apace.utils.Position;

public interface IInteractable {

	public IProcessable interact(Map map, Actor actor, Position position);
}

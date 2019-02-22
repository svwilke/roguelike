package apace.gameplay;

import apace.gameplay.map.Map;
import apace.process.IProcessable;
import apace.utils.Position;

public interface ITurnTaker {

	IProcessable takeTurn(Map map, Position position);
	int getPriority();
}

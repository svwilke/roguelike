package apace.drawing.ui;

import java.util.Observable;
import java.util.Observer;

import apace.drawing.Palette;
import apace.drawing.Window;
import apace.gameplay.actor.ActorLiving;
import apace.utils.Property;

public class WindowHealth extends Window implements Observer {

	public WindowHealth(int tx, int ty, ActorLiving actor) {
		super(tx, ty, new String[] {"Ç X/Y"});
		setActor(actor);
		this.setTextColor(Palette.SLATE);
	}
	
	private Property<Integer> maxHealth;
	private Property<Integer> health;
	
	public void setActor(ActorLiving actor) {
		if(maxHealth != null) {
			maxHealth.deleteObserver(this);
		}
		maxHealth = actor.getProperty("maxHealth");
		maxHealth.addObserver(this);
		if(health != null) {
			health.deleteObserver(this);
		}
		health = actor.getProperty("health");
		health.addObserver(this);
		update(null, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(health != null && maxHealth != null) {
			this.setText(String.format("Ç %d/%d", health.getValue(), maxHealth.getValue()));
		}
	}
}

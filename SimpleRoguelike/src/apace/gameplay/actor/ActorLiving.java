package apace.gameplay.actor;

import apace.drawing.Palette;
import apace.drawing.PaletteSwap;
import apace.drawing.Splash;
import apace.drawing.Sprite;
import apace.gameplay.IInteractable;
import apace.gameplay.map.Map;
import apace.lib.Reference;
import apace.lib.Sounds;
import apace.process.FlashAnimation;
import apace.process.IProcessable;
import apace.process.MultiProcess;
import apace.process.OffsetAnimation;
import apace.process.ScheduledCall;
import apace.process.SequenceProcess;
import apace.utils.Direction;
import apace.utils.Position;
import apace.utils.Property;

public class ActorLiving extends Actor implements IInteractable, Cloneable {
	
	private Property<Integer> health = new Property<>(1);
	private Property<Integer> maxHealth = new Property<>(1);
	private Property<Integer> attackValue = new Property<>(0);
	private boolean isDead = false;
	
	public ActorLiving(Sprite sprite) {
		super(sprite, false, false);
	}
	
	/**
	 * Sets the max health of this actor. Also sets the current health to this value.
	 * @param maxHealth
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth.setValue(maxHealth);
		this.health.setValue(maxHealth);
	}
	
	public int getMaxHealth() {
		return maxHealth.getValue();
	}
	
	public int getAttackValue() {
		return attackValue.getValue();
	}
	
	public void setAttackValue(int value) {
		if(value < 0) {
			System.out.println("## Attack value was called to be a negative number. Clamping to 0 instead.");
			attackValue.setValue(0);
		} else {
			attackValue.setValue(value);
		}
	}
	
	public void damage(int dmg) {
		if(dmg < 0) {
			throw new IllegalArgumentException("ActorLiving#damage should not be used with negative values. Use ActorLiving#heal instead.");
		}
		health.setValue(health.getValue() - dmg);
		if(health.getValue() <= 0) {
			die();
		}
	}
	
	public void heal(int hpGain) {
		if(hpGain < 0) {
			throw new IllegalArgumentException("ActorLiving#heal should not be used with negative values. Use ActorLiving#damage instead.");
		}
		health.setValue(Math.min(maxHealth.getValue(), health.getValue() + hpGain));
	}
	
	public void die() {
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}

	@Override
	public IProcessable interact(Map map, Actor actor, Position position) {
		int damage = 0;
		if(actor instanceof ActorLiving) {
			damage = ((ActorLiving)actor).attackValue.getValue();
		}
		if(damage <= 0) {
			return null;
		}
		damage(damage);
		Splash hpInfo = new Splash(position.getX(), position.up().getY(), "-" + damage);
		hpInfo.setTextColor(Palette.ORANGE);
		hpInfo.show();
		Sounds.HIT.play();
		if(isDead) {
			return new SequenceProcess(new FlashAnimation(this, 10, PaletteSwap.DARK), new ScheduledCall(() -> map.removeActor(getPosition(), this)));
		} else {
			Position delta = position.subtract(actor.getPosition());
			Direction d = delta.getDirection();
			int targetX = d.getX() * Reference.TILE_SIZE / 4;
			int targetY = d.getY() * Reference.TILE_SIZE / 4;
			return new MultiProcess(new FlashAnimation(this, 10, PaletteSwap.DARK), new SequenceProcess(new OffsetAnimation(getPosition(), 0, 0, targetX, targetY).setSpeed(0.4f), new OffsetAnimation(getPosition(), targetX, targetY, 0, 0).setSpeed(0.4f)));
			//return new FlashAnimation(this, 10, PaletteSwap.WHITE);
		}
	}
	
	@Override
	public ActorLiving clone() {
		try {
			ActorLiving clone = (ActorLiving)super.clone();
			clone.attackValue = attackValue.clone();
			clone.health = health.clone();
			clone.maxHealth = maxHealth.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Clone function of ActorLiving invalid. CloneNotSupportedException.");
		}
	}
}

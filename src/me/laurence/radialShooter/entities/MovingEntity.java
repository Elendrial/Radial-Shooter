package me.laurence.radialShooter.entities;

import me.laurence.radialShooter.Stage;
import me.laurence.radialShooter.Vector;

public abstract class MovingEntity extends BaseEntity{

	public Vector velocity;
	
	public MovingEntity(Stage s){
		super(s);
		velocity = new Vector(0,0);
	}
	
	public MovingEntity(Stage s, double x, double y){
		super(s);
		velocity = new Vector(x, y);
	}

	@Override
	public void updateOnTick() {
		position.translate(velocity.getX(), velocity.getY());
	}

}

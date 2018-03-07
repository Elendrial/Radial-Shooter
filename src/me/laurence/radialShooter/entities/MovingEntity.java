package me.laurence.radialShooter.entities;

import me.laurence.radialShooter.Vector;

public abstract class MovingEntity extends BaseEntity{

	public Vector velocity;
	
	public MovingEntity(){
		super();
		velocity = new Vector(0,0);
	}
	
	public MovingEntity(double x, double y){
		super();
		velocity = new Vector(x, y);
	}

	@Override
	public void updateOnTick() {
		position.translate(velocity.getX(), velocity.getY());
	}

}

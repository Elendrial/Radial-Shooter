package me.laurence.radialShooter.entities;

import java.awt.Point;

public abstract class MovingEntity extends BaseEntity{

	public Point velocity;
	
	public MovingEntity(){
		super();
		velocity = new Point(0,0);
	}
	
	public MovingEntity(int x, int y){
		super();
		velocity = new Point(x, y);
	}

	@Override
	public void updateOnTick() {
		position.translate((int) velocity.getX(), (int) velocity.getY());
	}

}

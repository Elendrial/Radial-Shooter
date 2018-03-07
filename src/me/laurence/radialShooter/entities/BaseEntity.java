package me.laurence.radialShooter.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Vector;

public abstract class BaseEntity {

	public Vector position;
	public Vector collisionBox;
	
	public BaseEntity(){
		position = new Vector(0,0);
		collisionBox = new Vector(0,0);
	}
	
	public abstract void render(Graphics g);

	public abstract void updateOnTick();
	
	public void destroy(){
		RadialShooter.instance.stage.removeEntity(this);
	}

	public Rectangle getCollisionRect(){
		return new Rectangle((int) (position.getX()), (int) (position.getY()), (int) (collisionBox.getX()), (int) (collisionBox.getY()));
	}
	
}

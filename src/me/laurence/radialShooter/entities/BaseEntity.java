package me.laurence.radialShooter.entities;

import java.awt.Graphics;
import java.awt.Point;

import me.laurence.radialShooter.RadialShooter;

public abstract class BaseEntity {

	protected Point position;
	protected Point collisionBox;
	
	public BaseEntity(){
		position = new Point(0,0);
		collisionBox = new Point(0,0);
	}
	
	public abstract void render(Graphics g);

	public abstract void updateOnTick();
	
	public void destroy(){
		RadialShooter.instance.stage.removeEntity(this);
	}

}

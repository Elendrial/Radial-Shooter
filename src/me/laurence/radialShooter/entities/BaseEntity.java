package me.laurence.radialShooter.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import me.laurence.radialShooter.RadialShooter;

public abstract class BaseEntity {

	public Point position;
	public Point collisionBox;
	
	public BaseEntity(){
		position = new Point(0,0);
		collisionBox = new Point(0,0);
	}
	
	public abstract void render(Graphics g);

	public abstract void updateOnTick();
	
	public void destroy(){
		RadialShooter.instance.stage.removeEntity(this);
	}

	public Rectangle getCollisionRect(){
		return new Rectangle(position.x, position.y, collisionBox.x, collisionBox.y);
	}
	
}

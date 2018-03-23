package me.laurence.radialShooter.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.laurence.radialShooter.Stage;
import me.laurence.radialShooter.Vector;

public abstract class BaseEntity {

	public Vector position;
	public Vector collisionBox;
	public final Stage stage;
	
	public BaseEntity(Stage parent){
		stage = parent;
		position = new Vector(0,0);
		collisionBox = new Vector(0,0);
	}
	
	public abstract void render(Graphics g);

	public abstract void updateOnTick();
	
	public void destroy(){
		stage.removeEntity(this);
	}

	public Rectangle getCollisionRect(){
		return Vector.convertToRectangle(position, new Vector(position.getX() + collisionBox.getX(), position.getY() + collisionBox.getY()));
	}
	
	public void drawCollisionRect(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		Rectangle r = this.getCollisionRect();
		g.drawRect(r.x, r.y, r.width, r.height);
		g.setColor(c);
	}
	
}

package me.laurence.radialShooter.entities;

import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;

public class PlayerEntity extends BaseEntity{
	
	private int rotation;
	private double xRotOffset, yRotOffset;
	
	public PlayerEntity(){
		super();
		position.setLocation(RadialShooter.w.width/2, RadialShooter.w.height/2);
		collisionBox.setLocation(20, 20);
		
		rotation = 180;
		updateRotation();
	}
	
	@Override
	public void updateOnTick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawArc(position.x - collisionBox.x/2, position.y-collisionBox.y/2, collisionBox.x, collisionBox.y, 0, 360);
		g.drawLine(position.x, position.y, (int) (position.x + xRotOffset * collisionBox.x/2), (int) (position.y + yRotOffset * collisionBox.y/2));
	}
	
	public void setRotation(int rotation){
		this.rotation = rotation;
	}
	
	private void updateRotation(){
		xRotOffset = Math.sin(rotation * Math.PI / 180);
		yRotOffset = Math.cos(rotation * Math.PI / 180);
	}
	
}

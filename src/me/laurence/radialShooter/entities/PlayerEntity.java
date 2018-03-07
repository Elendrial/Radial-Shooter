package me.laurence.radialShooter.entities;

import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Stage;

public class PlayerEntity extends BaseEntity{
	
	private int rotation, deltaRotation, fireDelay, maxFireDelay;
	private double xRotOffset, yRotOffset, rotationMultiplier, bulletVelocity;
	private boolean firing;
	public int rocksDestroyed;
	
	public PlayerEntity(Stage s){
		super(s);
		reset();
	}
	
	public void reset(){
		position.setLocation(RadialShooter.w.width/2, RadialShooter.w.height/2);
		collisionBox.setLocation(20, 20);
		
		rotation = 180;
		deltaRotation = 0;
		rotationMultiplier = 4;
		
		fireDelay = 20;
		maxFireDelay = 20;
		bulletVelocity = 5;
		
		firing = false;
		
		rocksDestroyed = 0;
		
		updateRotation();
	}
	
	@Override
	public void destroy(){}
	
	@Override
	public void updateOnTick() {
		// Shooting
		if(fireDelay > 0) fireDelay--;
		if(firing){
			if(fireDelay == 0){
				shoot();
				fireDelay = maxFireDelay;
			}
		}
		
		// Turning
		addToRotation((int) (deltaRotation * rotationMultiplier));
	}
	
	public void shoot(){
		BulletEntity e = new BulletEntity(stage, xRotOffset * bulletVelocity, yRotOffset * bulletVelocity);
		e.position = position.getLocation();
		stage.addEntity(e);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawArc((int) (position.getX() - collisionBox.getX()/2), (int) (position.getY()-collisionBox.getY()/2), (int) collisionBox.getX(), (int) collisionBox.getY(), 0, 360);
		g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + xRotOffset * collisionBox.getX()/2), (int) (position.getY() + yRotOffset * collisionBox.getY()/2));
	}
	
	public void setRotation(int rotation){
		this.rotation = rotation;
		updateRotation();
	}
	
	public void addToRotation(int r){
		rotation += r;
		rotation %= 360;
		updateRotation();
	}
	
	private void updateRotation(){
		xRotOffset = Math.sin(rotation * Math.PI / 180);
		yRotOffset = Math.cos(rotation * Math.PI / 180);
	}
	
}

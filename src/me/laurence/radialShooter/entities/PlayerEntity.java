package me.laurence.radialShooter.entities;

import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Stage;
import me.laurence.radialShooter.Vector;

public class PlayerEntity extends BaseEntity{
	
	private int rotation, deltaRotation, fireDelay, maxFireDelay;
	private double rotationMultiplier, bulletVelocity;
	private boolean firing;
	public int rocksDestroyed;
	public Vector rotOffset;
	
	public PlayerEntity(Stage s){
		super(s);
		reset();
	}
	
	public void reset(){
		position.setLocation(RadialShooter.w.width/2, RadialShooter.w.height/2);
		collisionBox.setLocation(20, 20);
		
		rotOffset = new Vector(1,0);
		
		rotation = 180;
		deltaRotation = 0;
		rotationMultiplier = 4;
		
		fireDelay = 20;
		maxFireDelay = 20;
		bulletVelocity = 5;
		
		firing = false;
		
		rocksDestroyed = 0;
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
		
		// Turning TODO: revert back to how it was before
		addToRotation((int) (1 * rotationMultiplier));
	}
	
	public void shoot(){
		BulletEntity e = new BulletEntity(stage, rotOffset.getX() * bulletVelocity, rotOffset.getY() * bulletVelocity);
		e.position = position.getLocation();
		stage.addEntity(e);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawArc((int) (position.getX() - collisionBox.getX()/2), (int) (position.getY()-collisionBox.getY()/2), (int) collisionBox.getX(), (int) collisionBox.getY(), 0, 360);
		g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + rotOffset.getX() * collisionBox.getX()/2), (int) (position.getY() + rotOffset.getY() * collisionBox.getY()/2));
		
		// TODO: Remove temp or at least put it under debug
		int dist = 200;
		g.drawArc((int) (position.getX() - dist/2), (int) (position.getY()-dist/2), (int) dist, (int) dist, 0, 360);
		g.drawArc((int) (position.getX() - dist), (int) (position.getY()-dist), (int) dist*2, (int) dist*2, 0, 360);
		g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + rotOffset.getX() * 400), (int) (position.getY() + rotOffset.getY() * 400));
	}
	
	public void setRotation(int rotation){
		rotOffset.rotateDeg(this.rotation - rotation);
		this.rotation = rotation;
	}
	
	public void addToRotation(int r){
		rotOffset.rotateDeg(r);
		rotation += r;
		rotation %= 360;
	}
	
	
	private float[] getInputs(){
		float[] output = new float[25];
		
		// Don't test areas, test entities!
		
		return output;
	}
}

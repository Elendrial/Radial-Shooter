package me.laurence.radialShooter.entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.laurence.radialShooter.IInputUser;
import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Stage;
import me.laurence.radialShooter.graphics.InputHandler;

public class PlayerEntity extends BaseEntity implements IInputUser{
	
	private int rotation, deltaRotation, fireDelay, maxFireDelay;
	private double xRotOffset, yRotOffset, rotationMultiplier, bulletVelocity;
	private boolean firing;
	
	public PlayerEntity(Stage s){
		super(s);
		position.setLocation(RadialShooter.w.width/2, RadialShooter.w.height/2);
		collisionBox.setLocation(20, 20);
		
		rotation = 180;
		deltaRotation = 0;
		rotationMultiplier = 4;
		
		fireDelay = 20;
		maxFireDelay = 20;
		bulletVelocity = 5;
		
		firing = false;
		updateRotation();
		
		InputHandler.inputListeners.add(this);
	}
	
	@Override
	public void destroy(){
		InputHandler.inputListeners.remove(this);
	}
	
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
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_A:
			deltaRotation = 1;
			break;
		case KeyEvent.VK_D:
			deltaRotation = -1;
			break;
		case KeyEvent.VK_LEFT:
			deltaRotation = 1;
			break;
		case KeyEvent.VK_RIGHT:
			deltaRotation = -1;
			break;
		case KeyEvent.VK_SPACE:
			firing = true;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_A:
			if(deltaRotation == 1) deltaRotation = 0;
			break;
		case KeyEvent.VK_D:
			if(deltaRotation == -1) deltaRotation = 0;
			break;
		case KeyEvent.VK_LEFT:
			if(deltaRotation == 1) deltaRotation = 0;
			break;
		case KeyEvent.VK_RIGHT:
			if(deltaRotation == -1) deltaRotation = 0;
			break;
		case KeyEvent.VK_SPACE:
			firing = false;
			break;
		}
	}
	
}

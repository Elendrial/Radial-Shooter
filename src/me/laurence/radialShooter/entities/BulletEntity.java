package me.laurence.radialShooter.entities;

import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;

public class BulletEntity extends MovingEntity{
	
	public BulletEntity(){ super(); }
	
	public BulletEntity(double x, double y){ super(x, y); this.collisionBox.setLocation(x * 2, y * 2);}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		if(position.getX() < 0 || position.getY() < 0 || position.getX() > RadialShooter.w.width || position.getY() > RadialShooter.w.height){
			destroy();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + collisionBox.getX()), (int) (position.getY() + collisionBox.getY()));
	}

}

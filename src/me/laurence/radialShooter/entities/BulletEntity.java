package me.laurence.radialShooter.entities;

import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;

public class BulletEntity extends MovingEntity{
	
	public BulletEntity(){ super(); }
	
	public BulletEntity(int x, int y){ super(x, y); }
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		if(position.x < 0 || position.y < 0 || position.x > RadialShooter.w.width || position.y > RadialShooter.w.height){
			destroy();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawLine(position.x, position.y, (int) (position.x + velocity.getX() * 2), (int) (position.y + velocity.getY() * 2));
	}

}

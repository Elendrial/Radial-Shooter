package me.laurence.radialShooter.entities;

import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;

public class BulletEntity extends MovingEntity{

	private int xOffset, yOffset;
	
	public BulletEntity(){ super(); calcOffset(); }
	
	public BulletEntity(int x, int y){ super(x, y); calcOffset(); }
	
	private void calcOffset(){
		double velDis = velocity.distance(0, 0);
		xOffset = (int) Math.sin((velocity.getX()/velDis));
		yOffset = (int) Math.cos(velocity.getY()/velDis);
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		if(position.x < 0 || position.y < 0 || position.x > RadialShooter.w.width || position.y > RadialShooter.w.height) destroy();
	}

	@Override
	public void render(Graphics g) {
		g.drawLine(position.x, position.y, position.x + xOffset, position.y + yOffset);
	}

}

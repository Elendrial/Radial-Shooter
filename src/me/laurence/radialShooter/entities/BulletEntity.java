package me.laurence.radialShooter.entities;

import java.awt.Color;
import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Stage;

public class BulletEntity extends MovingEntity{
	
	public BulletEntity(Stage s){ super(s); }
	
	public BulletEntity(Stage s, double x, double y){ super(s, x, y); this.collisionBox.setLocation(3, 3);}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		if(position.getX() < 0 || position.getY() < 0 || position.getX() > RadialShooter.w.width || position.getY() > RadialShooter.w.height){
			destroy();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + velocity.getX()*3), (int) (position.getY() + velocity.getY()*3));
		
		if(RadialShooter.renderDEBUG){
			g.setColor(Color.red);
			g.drawRect((int) (position.getX()), (int) (position.getY()), (int) (collisionBox.getX()), (int) (collisionBox.getY()));
			this.drawCollisionRect(g);
			g.drawString(collisionBox.toString(), (int) position.getX(), (int) position.getY());
			g.setColor(Color.black);
		}
	}

}

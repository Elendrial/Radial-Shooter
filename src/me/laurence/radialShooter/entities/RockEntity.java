package me.laurence.radialShooter.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Stage;
import me.laurence.radialShooter.Vector;

public class RockEntity extends MovingEntity{
	
	public RockEntity(Stage s, Vector p){
		super(s);
		this.collisionBox.setLocation(15, 15);
		position.setLocation(p.getX(), p.getY());
		setVelocity(1.7);
		if(RadialShooter.printDEBUG) System.out.println("Spawned rock at: " + position.getX() + ", " + position.getY() + "\t moving at: " + velocity.getX() + ", " + velocity.getY());
	}
	
	public RockEntity(Stage s, Vector p, int velocity){
		super(s);
		this.collisionBox.setLocation(10, 10);
		position.setLocation(p.getX(), p.getY());
		setVelocity(velocity);
	}
	
	public void setVelocity(double velocity) {
		this.velocity.setLocation(-(this.position.getX() - RadialShooter.w.width/2)/this.velocity.distance(RadialShooter.w.width/2, RadialShooter.w.height/2) * velocity, -(this.position.getY() - RadialShooter.w.width/2)/this.velocity.distance(RadialShooter.w.width/2, RadialShooter.w.height/2) * velocity);
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		ArrayList<BaseEntity> entities = stage.getCollidingEntities(this);
		for(BaseEntity e : entities){
			if(RadialShooter.printDEBUG) System.out.println("hit by " + e);
			if(e instanceof BulletEntity){
				this.destroy();
				stage.player.rocksDestroyed++;
				return;
			}
			
			if(e instanceof PlayerEntity){
				((PlayerEntity) e).health--;
				if(((PlayerEntity) e).health == 0)	stage.radialShooterInstance.setFinished();
				this.destroy();
			}
			
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.drawArc((int) (position.getX()), (int) (position.getY()), (int) (collisionBox.getX()), (int) (collisionBox.getY()), 0, 360);
		
		if(RadialShooter.renderDEBUG){
			this.drawCollisionRect(g);
		}
	}
}

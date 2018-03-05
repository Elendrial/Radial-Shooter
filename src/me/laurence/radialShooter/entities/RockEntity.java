package me.laurence.radialShooter.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import me.laurence.radialShooter.RadialShooter;

public class RockEntity extends MovingEntity{

	public RockEntity(){
		super();
	}
	
	public RockEntity(int x, int y){
		super(x,y);
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		ArrayList<BaseEntity> entities = RadialShooter.instance.stage.getCollidingEntities(this);
		for(BaseEntity e : entities){
			if(e instanceof BulletEntity){
				this.destroy();
				return;
			}
			
			if(e instanceof PlayerEntity){
				// TODO: End of the game.
			}
			
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.drawArc(position.x, position.y, collisionBox.x, collisionBox.y, 0, 360);
	}

}

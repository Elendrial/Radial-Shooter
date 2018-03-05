package me.laurence.radialShooter;

import java.awt.Graphics;
import java.util.ArrayList;

import me.laurence.radialShooter.entities.BaseEntity;
import me.laurence.radialShooter.entities.PlayerEntity;

public class Stage {
	
	protected ArrayList<BaseEntity> entities = new ArrayList<BaseEntity>();
	
	public Stage(){
		addEntity(new PlayerEntity());
	}
	
	public void addEntity(BaseEntity e){
		entities.add(e);
	}
	
	public void updateOnTick(){
		entities.forEach(e -> e.updateOnTick());
	}
	
	public void render(Graphics g){
		entities.forEach(e -> e.render(g));
	}
	
}

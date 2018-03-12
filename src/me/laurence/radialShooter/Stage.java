package me.laurence.radialShooter;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import me.laurence.radialShooter.entities.BaseEntity;
import me.laurence.radialShooter.entities.PlayerEntity;

public class Stage {
	
	protected ArrayList<BaseEntity> entities = new ArrayList<BaseEntity>();
	private ArrayList<BaseEntity> addedEntities = new ArrayList<BaseEntity>();
	private ArrayList<BaseEntity> removedEntities = new ArrayList<BaseEntity>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	public final RadialShooter radialShooterInstance;
	public final PlayerEntity player;
	
	public Stage(RadialShooter parent){
		player = new PlayerEntity(this);
		addEntity(player);
		this.radialShooterInstance = parent;
	}
	
	public void addEntity(BaseEntity e) {
		addedEntities.add(e);
	}
	
	public void removeEntity(BaseEntity e){
		removedEntities.add(e);
	}
	
	public void reset(){
		final Lock r = lock.readLock();
	    r.lock();
	    try {
			entities.clear();
			addedEntities.clear();
			removedEntities.clear();
			
			player.reset();
			
			entities.add(player);
	    }
	    finally{
	    	r.unlock();
	    }
	}
	
	public void updateOnTick(){
		final Lock w = lock.writeLock();
	    w.lock();
	    try{
			entities.forEach(e -> e.updateOnTick());
			
			entities.addAll(addedEntities);
			entities.removeAll(removedEntities);
			
			addedEntities.clear();
			removedEntities.clear();
	    }
	    finally{
	    	w.unlock();
	    }
	}
	
	
	public void render(Graphics g){
		final Lock r = lock.readLock();
	    r.lock();
	    try {
	    	entities.forEach(e -> e.render(g));
	    }
	    finally{
	    	r.unlock();
	    }
	}
	
	public ArrayList<BaseEntity> getCollidingEntities(BaseEntity toCheck){
		ArrayList<BaseEntity> collidingWith = new ArrayList<BaseEntity>();
		
		for(BaseEntity e : entities){
			if(e.getCollisionRect().intersects(toCheck.getCollisionRect()) && e != toCheck){
				collidingWith.add(e);
			}
		}
		
		return collidingWith;
	}

	public ArrayList<BaseEntity> getEntities() {
		return entities;
	}
	
}

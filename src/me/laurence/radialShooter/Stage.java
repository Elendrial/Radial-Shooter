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
	protected ArrayList<BaseEntity> addedEntities = new ArrayList<BaseEntity>();
	protected ArrayList<BaseEntity> removedEntities = new ArrayList<BaseEntity>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public Stage(){
		addEntity(new PlayerEntity());
	}
	
	public void addEntity(BaseEntity e) {
		addedEntities.add(e);
	}
	
	public void removeEntity(BaseEntity e){
		removedEntities.add(e);
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
	
}

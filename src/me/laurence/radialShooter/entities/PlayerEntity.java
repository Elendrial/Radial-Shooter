package me.laurence.radialShooter.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import me.laurence.radialShooter.RadialShooter;
import me.laurence.radialShooter.Stage;
import me.laurence.radialShooter.Vector;

public class PlayerEntity extends BaseEntity{
	
	private int rotation, deltaRotation, fireDelay, maxFireDelay;
	private double rotationMultiplier, bulletVelocity;
	private boolean firing;
	public int rocksDestroyed, health;
	public Vector rotOffset;
	
	public PlayerEntity(Stage s){
		super(s);
		reset();
	}
	
	public void reset(){
		position.setLocation(RadialShooter.w.width/2, RadialShooter.w.height/2);
		collisionBox.setLocation(20, 20);
		
		rotOffset = new Vector(1, 0); // Right is set as rotation = 0;
		rotation = 0;
		deltaRotation = 0;
		rotationMultiplier = 4;
		
		fireDelay = 20;
		maxFireDelay = 20;
		bulletVelocity = 5;
		
		firing = false;
		
		rocksDestroyed = 0;
		health = 10;
		
		setRotation(0);
		
		getInputs(); // just initialises last outputs and stops a load of errors being printed.
	}
	
	@Override
	public void destroy(){}
	
	@Override
	public void updateOnTick() {
		// Shooting
		float[] input = RadialShooter.ai.getOutputsAsFloat(getInputs(), stage.radialShooterInstance.index);
		// 0: l, 1 : r, 2 : s
		firing = input[2] > RadialShooter.ai.settings.neuralSettings.cutoffThreshhold;
		
		if(fireDelay > 0) fireDelay--;
		if(firing){
			if(fireDelay == 0){
				shoot();
				fireDelay = maxFireDelay;
			}
		}
		
		if(input[0] > input[1] && input[0] > RadialShooter.ai.settings.neuralSettings.cutoffThreshhold) deltaRotation = -1;
		else if(input[1] > RadialShooter.ai.settings.neuralSettings.cutoffThreshhold) deltaRotation = 1;
		else deltaRotation = 0;
		
		addToRotation((int) (deltaRotation * rotationMultiplier));
	}
	
	public void shoot(){
		BulletEntity e = new BulletEntity(stage, rotOffset.getX() * bulletVelocity, rotOffset.getY() * bulletVelocity);
		e.position = position.getLocation();
		stage.addEntity(e);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawArc((int) (position.getX() - collisionBox.getX()/2), (int) (position.getY()-collisionBox.getY()/2), (int) collisionBox.getX(), (int) collisionBox.getY(), 0, 360);
		g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + rotOffset.getX() * collisionBox.getX()/2), (int) (position.getY() + rotOffset.getY() * collisionBox.getY()/2));
		
		if(RadialShooter.renderDEBUG) {
		
			// Draw the circles
			g.drawArc((int) (position.getX() - rSegDist), (int) (position.getY()-rSegDist), (int) rSegDist*2, (int) rSegDist*2, 0, 360);
			g.drawArc((int) (position.getX() - rSegDist*2), (int) (position.getY()-rSegDist*2), (int) rSegDist*4, (int) rSegDist*4, 0, 360);
			
			// Draw lines & strings with the value of each section.
			Vector v = rotOffset.getLocation();
			for(int i = 0; i < angleSegments; i++) {
				
				v.rotateDeg(180/(double)angleSegments);
				
				g.setColor(Color.BLUE);
				g.drawString(i + "", (int)(position.getX() + v.getX() * 350), (int)(position.getY() + v.getY() * 350));
				g.setColor(Color.BLACK);
				
				for(int j = 0; j < radialSegments; j++) {
					try {
						g.drawString("" + lastOutputs[i * radialSegments + j], (int)(position.getX() + v.getX() * rSegDist * (j + 0.5)), (int)(position.getY() + v.getY() * rSegDist * (j+0.5)));
					}
					catch(Exception e) {
						System.err.println("Failed to get " + (i * radialSegments + j) + " :: " + i + " * " + radialSegments + " + " + j);
						e.printStackTrace();
					}
				}
				
				v.rotateDeg(180/(double)angleSegments);
				g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + v.getX() * 500), (int) (position.getY() + v.getY() * 500));
			}
			
			// Draw direction facing.
			g.setColor(Color.RED);
			g.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + rotOffset.getX() * 500), (int) (position.getY() + rotOffset.getY() * 500));
			g.setColor(Color.BLACK);
		}
	}
	
	public void setRotation(int rotation){
		rotation = rotation < 0 ? 360 - rotation : 0; // Probably not needed, but helps ensure it's always 0 <= r < 360
		rotOffset.rotateDeg(rotation - this.rotation);
		this.rotation = rotation;
	}
	
	public void addToRotation(int r){
		rotOffset.rotateDeg(r);
		rotation += r;
		rotation %= 360;
	}
	
	private int angleSegments = 8, radialSegments = 3, rSegDist = 100;
	private float[] lastOutputs;
	private float[] getInputs(){
		float[] output = new float[angleSegments * radialSegments + 1];
		Arrays.fill(output, 0f);
		
		Vector pos = new Vector();
		int count, dist;
		for(BaseEntity e : stage.getEntities()) {
			if(e instanceof PlayerEntity) continue; // Arguably wrapping the entire thing would be better, but I cba
			pos.setLocation(e.position);
			pos.translate(position.negated());
			pos.rotateDeg(-rotation);
			
			if(Math.round(pos.getLocation().getX()) == 0) output[output.length-1] += 1; // Checks along line of sight.
			else {
				count = 0;
				while((Math.atan(pos.getX()/pos.getY()) * 180/Math.PI < 360/angleSegments || !(pos.getX() >= 0 && pos.getY() >= 0))  && count < angleSegments-1) {
					pos.rotateDeg(-360/(double)angleSegments);
					count++;
				}
				dist = (int)(pos.distance(Vector.ORIGIN)/rSegDist);
				dist = dist >= radialSegments ? radialSegments-1 : dist;
				output[count * 3 + dist] += 1;
			}
		}
		lastOutputs = output;
		return output;
	}
}

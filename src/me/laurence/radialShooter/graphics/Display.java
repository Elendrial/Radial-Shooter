package me.laurence.radialShooter.graphics;

import java.awt.Canvas;
import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;

@SuppressWarnings("serial")
public class Display extends Canvas{
	
	public int viewingInstance = 0;
	public boolean autoSwitch = true;
	
	public Display(Window window) {
		setBounds(0, 0, window.width, window.height);
	}
	
	public void render(Graphics g){
		g.drawString("i: " + viewingInstance +  "      Gen: " + RadialShooter.genAlg.generation +  "      Auto: " + autoSwitch, 10, RadialShooter.w.height - 25);
		try{RadialShooter.instances.get(viewingInstance).stage.render(g);} catch(Exception e){e.printStackTrace();}
	}
	
	public void nextInstance(){
		viewingInstance++;
		viewingInstance %= RadialShooter.genAlg.children.size();
	}
	
	public void prevInstance() {
		viewingInstance--;
		if(viewingInstance < 0) viewingInstance = RadialShooter.genAlg.children.size()-1;
	}
	
	public void findNextRunning() {
		for(int i = 0; i < RadialShooter.instances.size(); i++) {
			if(RadialShooter.instances.get(i).state == 1) {
				viewingInstance = i;
				break;
			}
		}
	}
	
}

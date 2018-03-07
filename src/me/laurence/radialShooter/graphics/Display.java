package me.laurence.radialShooter.graphics;

import java.awt.Canvas;
import java.awt.Graphics;

import me.laurence.radialShooter.RadialShooter;

@SuppressWarnings("serial")
public class Display extends Canvas{
	
	public int viewingInstance = 0;
	
	public Display(Window window) {
		setBounds(0, 0, window.width, window.height);
	}
	
	public void render(Graphics g){
		try{RadialShooter.instances.get(viewingInstance).stage.render(g);} catch(Exception e){e.printStackTrace();}
	}
	
}

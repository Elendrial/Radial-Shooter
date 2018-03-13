package me.laurence.radialShooter.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import me.laurence.radialShooter.IInputUser;
import me.laurence.radialShooter.RadialShooter;

public class InputHandler implements KeyListener{

	public static InputHandler instance = new InputHandler();
	public static ArrayList<IInputUser> inputListeners = new ArrayList<IInputUser>();
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		inputListeners.forEach(i -> i.keyPressed(arg0));
		
		switch(arg0.getKeyCode()) {
		case KeyEvent.VK_N:
			RadialShooter.w.display.nextInstance();
			break;
			
		case KeyEvent.VK_B:
			RadialShooter.w.display.prevInstance();
			break;
		
		case KeyEvent.VK_S:
			RadialShooter.stopGame();
			break;
			
		case KeyEvent.VK_D:
			RadialShooter.renderDEBUG = !RadialShooter.renderDEBUG;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		inputListeners.forEach(i -> i.keyReleased(arg0));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		inputListeners.forEach(i -> i.keyTyped(arg0));
	}

}

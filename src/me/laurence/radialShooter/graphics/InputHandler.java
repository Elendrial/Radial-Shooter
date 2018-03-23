package me.laurence.radialShooter.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
			
		case KeyEvent.VK_P:
			RadialShooter.printDEBUG = !RadialShooter.printDEBUG;
			break;
			
		case KeyEvent.VK_T:
			String s = JOptionPane.showInputDialog("New Tickrate?");
			if(s == null || s.equals(""))	s = RadialShooter.targetTPS + "";
			int t = Integer.parseInt(s);
			RadialShooter.targetTPS = t > 0 ? t : RadialShooter.targetTPS;
			System.out.println("Target TPS changed to " + RadialShooter.targetTPS + " for next generation.");
			break;
		
		case KeyEvent.VK_A:
			RadialShooter.w.display.autoSwitch = !RadialShooter.w.display.autoSwitch;
			if(RadialShooter.w.display.autoSwitch) RadialShooter.w.display.findNextRunning();
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

package me.laurence.radialShooter.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import me.laurence.radialShooter.IInputUser;

public class InputHandler implements KeyListener{

	public static InputHandler instance = new InputHandler();
	public static ArrayList<IInputUser> inputListeners = new ArrayList<IInputUser>();
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		inputListeners.forEach(i -> i.keyPressed(arg0));
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

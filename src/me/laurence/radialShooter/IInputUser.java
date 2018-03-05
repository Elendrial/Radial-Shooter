package me.laurence.radialShooter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface IInputUser extends KeyListener{
	
	public default void keyPressed(KeyEvent arg0) {}
	public default void keyReleased(KeyEvent arg0) {}
	public default void keyTyped(KeyEvent arg0) {}
	
}

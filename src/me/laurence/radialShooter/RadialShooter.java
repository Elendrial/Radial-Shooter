package me.laurence.radialShooter;

import me.laurence.radialShooter.graphics.Window;

public class RadialShooter {

	public static Window w;
	public static RadialShooter instance;
	public static boolean isRunning;
	
	public static void main(String[] args) {
		startGame();
	}
	
	public static void startGame(){
		isRunning = true;
		setupWindow();
		setupInstance();
		
		w.start();
	}
	
	public static void stopGame(){
		isRunning = false;
		w.stop();
	}
	
	public static void setupWindow(){
		w = new Window("Radial Shooter", 800, 800);
		w.createDisplay();
	}
	
	public static void setupInstance(){
		instance = new RadialShooter();
	}
	
	
	public Stage stage;
	public RadialShooter(){
		stage = new Stage();
	}

}

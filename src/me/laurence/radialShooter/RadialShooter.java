package me.laurence.radialShooter;

import me.laurence.radialShooter.graphics.Window;

public class RadialShooter implements Runnable{

	public static Window w;
	public static RadialShooter instance;
	public static boolean isRunning;
	public static int targetTPS = 30;
	
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
		new Thread(instance).start();
	}
	
	// RedialShooter Object - setup this way to allow for parallel testing of genetic variants.
	
	public Stage stage;
	public RadialShooter(){
		stage = new Stage();
	}

	public void updateOnTick(){
		stage.updateOnTick();
	}
	
	@Override
    public void run() {
        int fps = 0, tick = 0;
        
        double fpsTimer = System.currentTimeMillis();
        double secondsPerTick = 1D / targetTPS;
        double nsPerTick = secondsPerTick * 1000000000D;
        double then = System.nanoTime();
        double now;
        double unprocessed = 0;

        while(isRunning){
            now = System.nanoTime();
            unprocessed += (now - then) / nsPerTick;
            then = now;
            while(unprocessed >= 1){
                updateOnTick();
                tick++;
                unprocessed--;
            }

            // This is NOT to sleep, but to limit the game loop
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            fps++;

            // If the current time is 1 second greater than the last time we printed
            if(System.currentTimeMillis() - fpsTimer >= 1000){
                System.out.printf("FPS: %d, TPS: %d%n", fps, tick);
                fps = 0; tick = 0;
                fpsTimer += 1000;
            }
        }
	}

}

package me.laurence.radialShooter;

import java.util.ArrayList;
import java.util.Random;

import me.hii488.ArtificialIntelligence;
import me.hii488.GeneticAlgB;
import me.laurence.radialShooter.entities.RockEntity;
import me.laurence.radialShooter.graphics.Window;

public class RadialShooter implements Runnable{

	public static Window w;
	public static ArtificialIntelligence ai;
	public static GeneticAlgB genAlg;
	public static ArrayList<RadialShooter> instances;
	public static Random rand = new Random();
	public static boolean isRunning;
	public static int targetTPS = 500;
	
	public static boolean renderDEBUG = false, printDEBUG = false;
	
	public static void main(String[] args) {
		startGame();
	}
	
	public static void startGame(){
		isRunning = true;
		instances = new ArrayList<RadialShooter>();
		
		setupWindow();
		setupAI();
		setupInstances(genAlg.genSettings.childrenPerGeneration);
		
		startInstances();
		w.start();
		
		while(isRunning) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			boolean finished = true;
			for(RadialShooter s : instances) {
				if(s.state != 0) {
					finished = false;
					break;
				}
			}
			
			if(finished) {
				ai.iterate();
				if(genAlg.children.size()> instances.size()) 
					setupInstances(genAlg.children.size()-instances.size());
				startInstances();
			}
			
		}
	}
	
	public static void stopGame(){
		isRunning = false;
		w.stop();
		ai.saveNetwork("savedNets");
	}
	
	public static void setupWindow(){
		w = new Window("Radial Shooter", 800, 800);
		w.createDisplay();
	}
	
	public static void setupAI(){ // Mostly nabbed from my 2048 clone.
		ai = new ArtificialIntelligence();
		
		genAlg = new GeneticAlgB();
		genAlg.genSettings.childrenPerGeneration = 200;
		genAlg.genSettings.additionalTopChildrenKept = 20;
		genAlg.genSettings.mutationChance = 0.02f;
		genAlg.genSettings.mixTop = 30;
		genAlg.genSettings.insureDifferent = true;
		
		genAlg.genSettingsB.additionalRandKept = 20;
		genAlg.genSettingsB.lowestXPotentiallyKept = 80;
		genAlg.genSettingsB.additionalToMix = 20;
		
		
		// TODO: Tweak these
		ai.settings.neuralSettings.inputs = 25; // Check the PlayerEntity class for what this number should be.
		ai.settings.neuralSettings.nodesInHiddenLayers = new int[]{20};
		ai.settings.neuralSettings.outputs = new String[]{"l","r","s"};
		ai.settings.neuralSettings.cutoffThreshhold = 0.6f;
		ai.settings.neuralSettings.outputsAsFloats = true;
		
		ai.settings.loggingSettings.printAll = false;
		ai.settings.loggingSettings.printTop = false;
		ai.settings.loggingSettings.topAmount = 10;
		
		ai.learningAlg = genAlg;
		
		ai.initialSetup();
	}
	
	public static void setupInstances(int number){
		while(number > 0) {
			instances.add(new RadialShooter(instances.size()));
			new Thread(instances.get(instances.size()-1)).start();
			number--;
		}
	}
	
	public static void startInstances(){
		for(RadialShooter r : instances){
			r.startRun();
		}
	}
	
	// RadialShooter Object - setup this way to allow for parallel testing of genetic variants.
	
	public Stage stage;
	public int state; // 0 == halted, 1 == running, 2 == preparing to halt.
	public int index;
	
	public RadialShooter(int index){
		stage = new Stage(this);
		state = 0;
		this.index = index;
	}
	
	public void startRun(){
		state = 1;
	}
	

	public void setFinished() {
		state = 2;
		genAlg.setFitness(index, stage.player.rocksDestroyed * 10 + 0.1f); // The +0.1 is so that even ones which hit nothing can be potential parents.
	}
	
	public void reset(){
		stage.reset();
		state = 0;
	}

	public void updateOnTick(){
		stage.updateOnTick();
		
		// Spawning rocks:
		if(rand.nextFloat() < 0.015){ // 0.015 every tick : avg of 0.45 every second
			boolean side = rand.nextBoolean();
			int x, y;
			if(side) {
				x = rand.nextBoolean() ? 1 : w.width-1;
				y = rand.nextInt(w.height-2)+1;
			}
			else {
				y = rand.nextBoolean() ? 1 : w.height-1;
				x = rand.nextInt(w.width-2)+1;
			}
			
			RockEntity e = new RockEntity(stage, new Vector(x,y));
			stage.addEntity(e);
		}
	}
	
	
	public int TPS = 0;
	@Override
    public void run() {
        int tick = 0;
        
        double fpsTimer = System.currentTimeMillis();
        double nsPerTick = (1D / targetTPS) * 1000000000D;
        double then = System.nanoTime();
        double now;
        double unprocessed = 0;

        while(isRunning){
            now = System.nanoTime();
            unprocessed += (now - then) / nsPerTick;
            then = now;
            while(unprocessed >= 1){
                if(state == 1) try{updateOnTick();} catch(Exception e){e.printStackTrace();}
                else if(state == 2) {
                	reset();
                	nsPerTick = (1D / targetTPS) * 1000000000D;
                }
                tick++;
                unprocessed--;
            }

            // This is NOT to sleep, but to limit the game loop
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // If the current time is 1 second greater than the last time we printed
            if(System.currentTimeMillis() - fpsTimer >= 1000){
            	if(printDEBUG) System.out.printf("FPS: %d, TPS: %d%n", w.FPS, tick);
            	TPS = tick;
                tick = 0;
                fpsTimer += 1000;
            }
        }
	}

}

// Main class

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Main {
	
	// Oobjekts
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static ConsoleGraphics graphics = new ConsoleGraphics(114, 32);
	
	// Game logic things
	
	private static boolean gameRunning = true;
	
	private static GameState gameState = GameState.MainMenu;
	
	private static Person[] people;
	
	private static int secretZombie;
	
	private static boolean debugMode = false;
	
	// Player fields
	
	private static int movesLeft = 100;
	
	
	
	private static void initializeGame() {
		people = new Person[8];
		secretZombie = (int)Math.floor(8 * Math.random());
		println("Secret zombie: " + secretZombie);
		for (int i = 0; i < people.length; i++) {
			people[i] = new Person();
			
			// Create suspicions 
			for (int j = 0; j < people.length; j++) {
				// Don't let people be suspicious of themself
				if (j == i) {
					continue;
				}
				if (j == secretZombie) {
					if (Math.random() < 0.5) {
						people[i].suspicions.add(secretZombie);
					}
				}
				if (Math.random() < 0.3) {
					people[i].suspicions.add(j);
				}
			}
		}
		
	}
	
	private static void kill(Scanner inputScanner) {
		int target;
		int executer;
		try {
			target = inputScanner.nextInt();
			executer = inputScanner.nextInt(); 
			target--;
			executer--;
		} catch (InputMismatchException e){
			printerrln("Input type mismatch.");
			return;
		} catch (NoSuchElementException e) {
			printerrln("Insufficient number of arguments.");
			return;
		}
		
		if (target < 0 || target >= people.length || executer < 0 || executer >= people.length) {
			printerrln("1 or more person selection is of range.");
			return;
		}

		if (people[target].sprite == Person.deadSprite) {
			printerrln("Target is already dead.");
			return;
		}
		
		if (people[executer].sprite == Person.zombieSprite || people[executer].sprite == Person.deadSprite) {
			printerrln("The selected executor is not able to perform this action.");
			return;
		}
		
		if ((people[target].sprite == Person.zombieSprite || target == secretZombie) && executer != secretZombie) {
			if (Math.random() < 0.5) {
				people[executer].zombify();
			}
		}
			
		people[target].kill();
	}
	
	private static void spreadZombification() {
        for (int i = 0; i < people.length; i++) {
            if (people[i].sprite == Person.zombieSprite || i == secretZombie) {
                if (Math.random() < 0.2) {
                    ArrayList<Integer> indecesOfLivingPeople = indecesOfLivingPeople();
                    int j = (int)(Math.random() * (indecesOfLivingPeople.size()));
                    int toBeKilled = indecesOfLivingPeople.get(j);
                    people[toBeKilled].zombify();
                    // println(toBeKilled + " was randomly zombified!");
                	}
            	}
        	}
    	}
	
    private static ArrayList<Integer> indecesOfLivingPeople() {
    	ArrayList<Integer> livingPeople = new ArrayList<Integer>();
    	for(int j = 0; j < people.length; j++) {
    			if (people[j].sprite == Person.aliveSprite && j != secretZombie) { 
    			livingPeople.add(j);
    		}
    	}
        return livingPeople;
    }
	
	private static boolean handleGameInput() {
		boolean playerMoveCompleted = true;
		
		String input = scanner.nextLine();
		
		Scanner inputScanner = new Scanner(input);
		
		switch (inputScanner.next()) {
			case "info":
				gameState = GameState.Instructions;
				playerMoveCompleted = false;
				break;
				
			case "kill":
				kill(inputScanner);
				break;
				
			case "toggleDebug":
				debugMode = !debugMode;
				playerMoveCompleted = false;
				break; 
				
			default:
				printerrln("Input not recognized.");
				playerMoveCompleted = false;
		}
		
		inputScanner.close();
		
		return playerMoveCompleted;
	}
	
	private static void drawPeople() {
		int xPos;
		for (int i = 0; i < people.length; i++) {
			xPos = 4 + 8 * i;
			graphics.draw(people[i].sprite, xPos, 6);
			graphics.draw("#" + (i + 1), xPos, 5);
		}
	}
	
	private static void update() {
		try {
			switch (gameState) {
				case GameState.MainMenu: 
					graphics.clear();
					
					graphics.draw("Zombies!", 3, 3);
					graphics.draw("A game about preventing a zombie virus outbreak", 3, 4);
					graphics.draw("\"play\" to start game, \"exit\" to quit.", 3, graphics.canvas.height - 3);
					
					graphics.print();
					
					String input = scanner.nextLine().toLowerCase();
					if (input.equalsIgnoreCase("play")) {
						gameState = GameState.Instructions;
						initializeGame();
					} else if (input.equalsIgnoreCase("quit")) {
						gameRunning = false;
					}
					
					break;
					
				case GameState.Instructions: 
					graphics.clear();
					
					graphics.draw(GameObject.buildSprite("Instructions", 1, 30), 1, 1);

					graphics.print();
					
					scanner.nextLine();
					gameState = GameState.Playing;
					break;
				
				case GameState.Playing: 
					// Check for  loss
					boolean stillAlive = false;
					for (int i = 0; i < people.length; i++) {
						if (people[i].sprite == Person.aliveSprite && i != secretZombie) {
							stillAlive = true;
							break;
						}
					}
					if (!stillAlive) {
						gameState = GameState.GameOver;
					}
					   
					// Check for win
					if (movesLeft <= 0) {
						gameState = GameState.Win;
					}
					
					graphics.clear();
					
					drawPeople();
					
					// GUI
					graphics.draw(new Rectangle(0, 0, 35, graphics.canvas.height - 2).sprite, 77, 1); // Events logger box
					graphics.draw("Moves left: " + movesLeft, 4, 12);
					
					// Debug mode
					if (debugMode) {
						graphics.draw("Secret zombie: " + (secretZombie + 1), 4, 16);
					}
					
					graphics.print();
					if (handleGameInput()) {
						spreadZombification();
						movesLeft--;
					}
					
					break;
					
				case GameState.GameOver: 
					graphics.clear();
					
					graphics.draw("The zombies ate your brains! And your arms. And every other part of you. They were very hungry. Try again?", 1, 1);
					graphics.draw("Enter anything to return to the main menu.", 1, 2);
					
					graphics.print();
					scanner.nextLine();
					gameState = GameState.MainMenu;
					break;
				
				case GameState.Win:
					graphics.clear();
					
					graphics.draw("You beat the zombies!", 1, 1);
					graphics.draw("Enter anything to return to the main menu.", 1, 2);
					
					graphics.print();
					scanner.nextLine();
					gameState = GameState.MainMenu;
			}
		} catch (Exception e) {
			e.printStackTrace();
			scanner.nextLine();
		}
		
		}
	
	public static void main(String[] args) {
		println("\n\n\n\n\n══════════════════════════════\nGame started!\n══════════════════════════════\n\n\n\n\n");
		
		while(gameRunning) {
			update();
		}
	}
	
	private static void println(String string) {
		System.out.println(string);
	}
	
	private static void printerrln(String string) {
		println(string);
		println("Please check your input or type \\\"info\\\" for action references");
	}
}

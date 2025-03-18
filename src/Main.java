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
	
	private static ArrayList<String> eventLog;
	
	private static String[] dialog;
	
	private static boolean debugMode = true;
	
	// These 2 are used to let the game display the game scene for 1 frame after winning or losing
	// before going to another screen, so the player knows how they won or lost
	private static boolean won; 
	private static boolean lost;
	
	// Constants
	
	private static final int numPeople = 8;
	
	private static final double infectionSpreadChance = 0.3;
	
	private static final double actionKillFailChance = 0;
	
	private static final double actionKillInfectionPassChance = 0.2;
	
	private static final double actionQuarantineFailChance = 0.3;
	
	private static final double suspicionOfSecretZombieChance = 0.6;
	
	private static final double suspicionOfLivingChance = 0.3;
	
	// Player fields
	
	private static int movesLeft = 0;
	
	private static void initializeGame() {
		won = lost = false;
		movesLeft = 12;
		people = new Person[numPeople];
		secretZombie = (int)Math.floor(8 * Math.random());
		for (int i = 0; i < people.length; i++) {
			people[i] = new Person();
			
			// Create suspicions 
			for (int j = 0; j < people.length; j++) {
				// Don't let people be suspicious of themself
				if (j == i) {
					continue;
				}
				if (j == secretZombie) {
					if (Math.random() < suspicionOfSecretZombieChance) {
						people[i].suspicions.add(secretZombie);
					}
					continue;
				}
				if (Math.random() < suspicionOfLivingChance) {
					people[i].suspicions.add(j);
				}
			}
		}
		people[secretZombie].state = PersonState.Zombie;
		
		eventLog = new ArrayList<String>();
		dialog = new String[] {};
	}
	
	private static boolean actionKill(Scanner commandScanner) {
		int target;
		int executer;
		try {
			target = commandScanner.nextInt() - 1;
			executer = commandScanner.nextInt() - 1; 
		} catch (InputMismatchException e){
			printerrln("Input type mismatch.");
			return false;
		} catch (NoSuchElementException e) {
			printerrln("Insufficient number of arguments.");
			return false;
		} 
		
		if (target < 0 || target >= people.length || executer < 0 || executer >= people.length) {
			printerrln("1 or more person selection is of range.");
			return false;
		}

		if (people[target].state == PersonState.Dead) {
			printerrln("Target is already dead.");
			return false;
		}
		
		if (people[executer].sprite != Person.aliveSprite) {
			printerrln("The selected executer is not able to perform this action.");
			return false;
		}
		
		if (target == executer) {
			printerrln("The target and executer cannot be the same person.");
			return false;
		}
		
		if (Math.random() < actionKillFailChance) {
			return false;
		}
		
		if (people[target].state == PersonState.Zombie) {
			if (Math.random() < actionKillInfectionPassChance) {
				people[executer].zombify();
				eventLog.add("Zombified #" + (executer + 1) + " through kill");
			}
		}
			
		people[target].kill();
		removeSuspicion(target);
		eventLog.add("Killed #" + (target + 1) + " using #" + (executer + 1));
		
		return true;
	}
	
	private static boolean actionQuarantine(Scanner commandScanner) {
		int target;
		int executer;
		try {
			target = commandScanner.nextInt() - 1;
			executer = commandScanner.nextInt() - 1; 
		} catch (InputMismatchException e){
			printerrln("Input type mismatch.");
			return false;
		} catch (NoSuchElementException e) {
			printerrln("Insufficient number of arguments.");
			return false;
		}
		
		if (target < 0 || target >= people.length || executer < 0 || executer >= people.length) {
			printerrln("1 or more selections are of range.");
			return false;
		}
		
		if (people[target].state == PersonState.Dead) {
			printerrln("The target is already dead.");
			return false;
		}
		
		if (people[executer].state != PersonState.Alive) {
			printerrln("The selected executer is not able to perform this action.");
			return false;
		}
		
		if (target == executer) {
			printerrln("The target and executer cannot be the same person.");
			return false;
		}
		
		if (Math.random() < actionQuarantineFailChance) {
			return false;
		}
		
		people[target].quarantined = true;
		eventLog.add("Quarantined #" + (target + 1) + " using #" + (executer + 1));
		
		return true;
	}
	
	private static boolean actionQuestion(Scanner commandScanner) {
		int target;
		try {
			target = commandScanner.nextInt() - 1;
		} catch (InputMismatchException e){
			printerrln("Input type mismatch.");
			return false;
		} catch (NoSuchElementException e) {
			printerrln("Insufficient number of arguments.");
			return false;
		}
		
		if (target < 0 || target >= people.length) {
			printerrln("The selection is of range.");
			return false;
		}
		
		if (people[target].sprite != Person.aliveSprite) {
			printerrln("Target cannot be questioned, because you can't talk to dead people or zombies.");
			return false;
		}
		
		people[target].questioned = true;
		String formattedSuspicions = "";
		ArrayList<Integer> suspicions = people[target].suspicions;
		
		if (suspicions.size() == 0) {
			eventLog.add("Questioned #" + (target + 1));
			printdialogln(new String[] {"I don't think anyone here is a zombie.", "Maybe you're just buying into alarmist propaganda."}, String.valueOf(target + 1));
			return true;
		}
		
		if (suspicions.size() == 1) {
			formattedSuspicions = "#" + (suspicions.get(0) + 1);
		} else {
			for (int i = 0; i < suspicions.size(); i++) {
				formattedSuspicions += i == suspicions.size() - 1 ? "and #" + (suspicions.get(i) + 1) : "#" + (suspicions.get(i) + 1) + ", ";
			}
		}
		
		printdialogln("I think " + formattedSuspicions + (suspicions.size() > 1 ? " are" : " is") + " acting weird.", String.valueOf(target + 1));
		eventLog.add("Questioned #" + (target + 1));
		return true;
	}
	
	private static void debugActionSetState(Scanner commandScanner) {
		int target;
		String state;
		try {
			target = commandScanner.nextInt() - 1;
			state = commandScanner.next(); 
		} catch (InputMismatchException e){
			printerrln("Input type mismatch.");
			return;
		} catch (NoSuchElementException e) {
			printerrln("Insufficient number of arguments.");
			return;
		}
		
		if (target < 0 || target >= people.length) {
			printerrln("1 or more person selection is of range.");
			return;
		}
		
		switch (state) {
			case "alive": 
				people[target].revive();
				eventLog.add("*Revived #" + (target + 1));
				break;
				
			case "zombie":
				people[target].zombify();
				eventLog.add("*Infected #" + (target + 1));
				break;
				
			case "secretZombie":
				people[target].zombify();
				people[target].sprite = Person.aliveSprite;
				eventLog.add("*Secretly infected #" + (target + 1));
				break;
				
			case "dead":
				people[target].kill();
				eventLog.add("*Killed #" + (target + 1));
				break;
				
			default:
				printerrln("Invalid target person state.");
		}
	}
	
	private static void spreadZombification() {
		ArrayList<Integer> indicesToBeZombified = new ArrayList<Integer>();
    	ArrayList<Integer> indicesOfInfectablePeople = new ArrayList<Integer>();
    	
    	for(int i = 0; i < people.length; i++) {
    		if (people[i].state == PersonState.Alive && people[i].quarantined == false) { 
    			indicesOfInfectablePeople.add(i);
    		}
    	}
    	
    	// Mark indices for infection
        for (int i = 0; i < people.length; i++) {
            if (people[i].state == PersonState.Zombie && people[i].quarantined == false) {
            	double infectionChance;
            	if (people[i].sprite == Person.aliveSprite) {
            		infectionChance = 2 * infectionSpreadChance;
            	} else {
            		infectionChance = infectionSpreadChance;
            	}
                if (Math.random() < infectionChance && indicesOfInfectablePeople.size() > 0) {
                    int toBeInfectedIndex = indicesOfInfectablePeople.get((int)(Math.random() * (indicesOfInfectablePeople.size())));
                    indicesToBeZombified.add(toBeInfectedIndex);
                    // So that 2 zombies don't try to infect the same person
                    indicesOfInfectablePeople.remove(Integer.valueOf(toBeInfectedIndex));
                }
            }
        }
        
        // Infect, done separately so that newly infected zombies don't try to infect that same move
        for (int i : indicesToBeZombified) {
        	people[i].zombify();
        	removeSuspicion(i);
        	eventLog.add("Zombified #" + (i + 1));
        }
    }
	
	private static void clearQuarantine() {
		for (Person p : people) {
			p.quarantined = false;
		}
	}
	
	private static void removeSuspicion(int index) {
		for (Person p : people) {
			p.suspicions.remove(Integer.valueOf(index));
		}
	}
	
	// Returns true if the command given by the player advances the game by a move, exceptions
	// being commands like "info" and "toggleDebug".
	private static boolean handleGameInput() {
		boolean playerMoveCompleted = true;
		
		String input = scanner.nextLine();
		
		Scanner commandScanner = new Scanner(input);
		
		switch (commandScanner.next()) {
			case "info":
				gameState = GameState.Instructions;
				playerMoveCompleted = false;
				break;
				
			case "kill":
				playerMoveCompleted = actionKill(commandScanner);
				break;
				
			case "quarantine":
				playerMoveCompleted = actionQuarantine(commandScanner);
				break;
				
			case "question":
				playerMoveCompleted = actionQuestion(commandScanner);
				break;
				
			case "toggleDebug":
				debugMode = !debugMode;
				eventLog.add("*Toggled debug mode");
				playerMoveCompleted = false;
				break; 
				
			case "setState":
				if (debugMode) {
					debugActionSetState(commandScanner);
					playerMoveCompleted = false;
					break;
				}
			
			case "skip":
				if (debugMode) {
					playerMoveCompleted = true;
					break;
				}
				
			default:
				printerrln("Input not recognized, or the command requires debug mode.");
				playerMoveCompleted = false;
		}
		
		commandScanner.close();
		
		return playerMoveCompleted;
	}
	
	private static boolean checkWin() {
		if (movesLeft <= 0) {
			return true;
		}
		
		boolean zombiesExist = false;
		for (Person p : people) {
			if (p.state == PersonState.Zombie) {
				zombiesExist = true;
				break;
			}
		}
		if (!zombiesExist) {
			return true;
		}
		
		return false;
	}
	
	private static boolean checkLoss() {
		boolean stillAlive = false;
		for (int i = 0; i < people.length; i++) {
			if (people[i].state == PersonState.Alive) {
				stillAlive = true;
				break;
			}
		}
		if (!stillAlive) {
			return true;
		} else {
			// Very super duper important code that this game couldn't possibly exist without
			String[] anywayThisCakeIsGreat = GameObject.buildSprite("Cake", 1, 8);
		}
		
		return false;
	}
	
 	private static void drawPeople() {
		int xPos;
		for (int i = 0; i < people.length; i++) {
			xPos = 4 + 8 * i;
			graphics.draw(people[i].sprite, xPos, 6);
			graphics.draw("#" + (i + 1), xPos, 5);
			if (people[i].questioned) {
				graphics.draw("Questd.", xPos, 12);
			}
		}
	}
 	
	
	private static void update() {
		try {
			switch (gameState) {
				case GameState.MainMenu: 
					graphics.clear();
					
					graphics.draw("Zombies!", 4, 3);
					graphics.draw("A game about preventing a zombie virus outbreak", 4, 4);
					graphics.draw("\"play\" to start game, \"exit\" to quit the program.", 4, graphics.canvas.height - 3);
					
					graphics.print();
					
					String input = scanner.nextLine().toLowerCase();
					if (input.equalsIgnoreCase("play")) {
						gameState = GameState.Instructions;
						initializeGame();
					} else if (input.equalsIgnoreCase("exit")) {
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
					graphics.clear();
					
					drawPeople();
					
					// GUI
					graphics.draw(new Rectangle(0, 0, 35, graphics.canvas.height - 2).sprite, 77, 1); // Events logger box
					graphics.draw("Moves left: " + movesLeft, 4, 14);
					graphics.draw(dialog, 4, 16);
					dialog = new String[] {};
					for (int i = 0; i < eventLog.size(); i++) {
						graphics.draw(eventLog.get(i), 78, 2 + i);
					}
					
					// Debug mode
					if (debugMode) {
						ArrayList<Integer> secretZombies = new ArrayList<Integer>();
						for (int i = 0; i < people.length; i++) {
							if (people[i].state == PersonState.Zombie && people[i].sprite == Person.aliveSprite) {
								secretZombies.add(i + 1);
							}
						}
						graphics.draw("Secret zombie(s): " + secretZombies, 4, 2);
					}
					
					graphics.print();
					
					boolean playerMoveCompleted = false;
					// Allows for a frame of the game to be displayed before moving on to the win
					// or loss screen so players know how they won or lost.
					if (lost) {
						scanner.nextLine();
						gameState = GameState.GameOver;
					} else if (won) {
						scanner.nextLine();
						gameState = GameState.Win;
					} else { // If no win or loss, continue with game logic
						playerMoveCompleted = handleGameInput();
					}
					
					if (playerMoveCompleted) {
						movesLeft--;
					}
					
					if (checkWin()) {
						printdialogln("You won! The secret zombie was person #" + (secretZombie + 1) + ".", "Game");
						won = true;
						playerMoveCompleted = false;
					}
					
					// Only tick game logic if the player actually executed a command that costs a move
					if (playerMoveCompleted) {
						spreadZombification();
						clearQuarantine();
					}
					
					if (checkLoss()) {
						printdialogln("You lost! The secret zombie was person #" + (secretZombie + 1) + ".", "Game");
						lost = true;
					}
					
					break;
					
				case GameState.GameOver: 
					graphics.clear();
					
					graphics.draw("The zombies ate your brains! And your arms. And every other part of you. They were very hungry. Try again?", 4, 3);
					graphics.draw("Enter anything to return to the main menu.", 4, graphics.canvas.height - 3);
					
					graphics.print();
					scanner.nextLine();
					gameState = GameState.MainMenu;
					break;
				
				case GameState.Win:
					graphics.clear();
					
					graphics.draw("You beat the zombies!", 4, 3);
					graphics.draw("Enter anything to return to the main menu.", 4, graphics.canvas.height - 3);
					
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
		// Why is this printing after frame 1?
		//println("\n\n\n\n\n══════════════════════════════\nGame started!\n══════════════════════════════\n\n\n\n\n");
		
		while(gameRunning) {
			update();
		}
	}
	
	
	private static void println(String string) {
		graphics.printOutput += (graphics.printOutput == "" ? "" : "\n") + string;
	}
	
	
	private static void printerrln(String string) {
		graphics.printOutput += (graphics.printOutput == "" ? "" : "\n") + string + "\nPlease check your input or type \"info\" for action references";
	}
	
	// Writes text as dialogue under the line of people.  
	private static void printdialogln(String string, String speaker) {
		dialog = new String[] {speaker + ": ", string};
	}
	
	private static void printdialogln(String[] strings, String speaker) {
		dialog = new String[strings.length + 1];
		dialog[0] = speaker + ": ";
		for (int i = 1; i < dialog.length; i++) {
			dialog[i] = strings[i - 1];
		}
	}
}

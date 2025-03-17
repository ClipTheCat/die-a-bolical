// Main class

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
	
	// Player fields
	
	private static int movesLeft;
	
	
	
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
		} catch (InputMismatchException e){
			println("Input type mismatch, please check your input or type \"info\" for action references.");
			return;
		} catch (NoSuchElementException e) {
			println("Insufficient number of arguments, please check your input or type \"info\" for action references.");
			return;
		}
		
		if (target < 0 || target >= people.length || executer < 0 || executer >= people.length) {
			println("1 or more person selection is of range, please check your input or type \"info\" for action references.");
			return;
		}

		if (people[target].sprite == Person.deadSprite) {
			println("Target is already dead, please check your input or type \"info\" for action references.");
			return;
		}
		
		if (people[executer].sprite == Person.zombieSprite || people[executer].sprite == Person.deadSprite) {
			println("Selected executor is not able to do this action, please check your input or type \"info\" for action references.");
			return;
		}
		
		if ((people[target].sprite == Person.zombieSprite || target == secretZombie) && executer != secretZombie) {
			println("Tried to zombify");
			if (Math.random() < 0.5) {
				people[executer].zombify();
				println("Zombified");
			}
		}
			
		people[target].kill();
	}
	
	private static void handleGameInput() {
		String input = scanner.nextLine();
		
		Scanner inputScanner = new Scanner(input);
		
		switch (inputScanner.next()) {
			case "info":
				gameState = GameState.Instructions;
				break;
				
			case "kill":
				kill(inputScanner);
				break;
				
			default:
				println("Input not recognized, please check your input or type \\\"info\\\" for action references.");
		}
		
		inputScanner.close();
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
					graphics.clear();
					
					drawPeople();
					// Events logger box
					graphics.draw(new Rectangle(0, 0, 35, graphics.canvas.height - 2).sprite, 77, 1);
					
					
					graphics.print();
					handleGameInput();
					break;
					
				case GameState.GameOver: 
					graphics.clear();
					
					
					
					graphics.print();
					scanner.nextLine();
					gameState = GameState.MainMenu;
					break;
				
				case GameState.Win:
					graphics.clear();
					
					
					
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
}

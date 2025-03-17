// Main class

import java.util.Scanner;

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
	
	private static void handleGameInput() {
		String input = scanner.nextLine();
		
		//scanner.
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
			switch(gameState) {
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
					scanner.nextLine();
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
		} catch(Exception e) {
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

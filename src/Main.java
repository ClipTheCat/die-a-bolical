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
	
	// Player fields
	
	private static int movesLeft;
	
	
	
	private static void initializeLevel(int level) {
		people = new Person[5];
		for (int i = 0; i < people.length; i++) {
			people[i] = new Person();
		}
		
		// TODO
	}
	
	private static void update() {
		try {
			switch(gameState) {
				case GameState.MainMenu: 
					graphics.clear();
					
					graphics.draw("Zombie Game Name Here", 3, 3);
					graphics.draw("\"play\" to start game, \"exit\" to quit.", 3, graphics.canvas.height - 3);
					
					graphics.print();
					
					String input = scanner.nextLine().toLowerCase();
					if (input.equalsIgnoreCase("play")) {
						gameState = GameState.Instructions;
						initializeLevel(1);
					} else if (input.equalsIgnoreCase("quit")) {
						gameRunning = false;
					}
					
					break;
					
				case GameState.Instructions: 
					graphics.clear();
					
					graphics.draw(GameObject.buildSprite("Instructions", 1, 28), 1, 1);
					
					graphics.print();
					
					scanner.nextLine();
					gameState = GameState.Playing;
					break;
				
				case GameState.Playing: 
					graphics.clear();
					
					for (int i = 0; i < people.length; i++) {
						graphics.draw(people[i].sprite, 4 + 16 * i, 6);
					}
					
					graphics.draw(new Rectangle(0, 0, 35, graphics.canvas.height - 2).sprite, 77, 1);
					
					graphics.print();
					scanner.nextLine();
					break;
					
				case GameState.GameOver: 
	
					
					break;
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

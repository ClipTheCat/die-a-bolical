// Main class

import java.util.Scanner;

public class Main {
	
	private static boolean gameRunning = true;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static ConsoleGraphics graphics = new ConsoleGraphics(114, 32);
	
	private static GameState gameState = GameState.MainMenu;
	
	private static Person[] people;
	
	// Player fields
	
	
	private static void initializeLevel(int level) {
		people = new Person[4 + level];
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

					graphics.draw(graphics.canvas.sprite, 0, 0);
					graphics.draw("Zombie Game Name Here", 3, 3);
					
					graphics.print();
					
					scanner.nextLine();
					break;
					
				case GameState.Playing: 
					graphics.clear();
					
					
					
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

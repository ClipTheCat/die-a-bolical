// Main class

import java.util.Scanner;

public class Main {
	
	private static boolean gameRunning = true;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static LetterGraphics graphics = new LetterGraphics(70, 31);
	
	private static GameState gameState = GameState.Playing;
	
	private static CharacterState characterState = CharacterState.Idle;
	
	// Player fields
	
	private static Cat testObj = new Cat();
	private static Rectangle testRect = new Rectangle(0, 0, 10, 5);
	private static Food heldItem = new Food();
	
	private static void update() {
		try {
			switch(gameState) {
			
				case GameState.MainMenu: 
					graphics.clear();
					
					testRect.x = Integer.parseInt(scanner.next());
					testRect.y = Integer.parseInt(scanner.next());
					
					graphics.draw(testRect.sprite, testRect.x, testRect.y);
					graphics.draw(testObj.sprite, 4, 4);
					
					graphics.print();
					
					break;
					
				case GameState.Playing: 
					graphics.clear();
					int playerLocationx = 4;
					int playerLocationy = 4; 
					graphics.draw(testObj.sprite, playerLocationx, playerLocationy);
				switch(characterState) {
				case CharacterState.Idle:
					break;
				case CharacterState.Holding:
					graphics.draw(heldItem.sprite, playerLocationx-1, playerLocationy-1);
					break;
				case CharacterState.InFridge:
					//ask for item number
					break;
				}
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

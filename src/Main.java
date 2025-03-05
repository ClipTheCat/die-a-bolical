// Main class

import java.util.Scanner;

public class Main {
	
	private static boolean gameRunning = true;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static LetterGraphics graphics = new LetterGraphics(70, 31);
	
	private static GameState gameState = GameState.MainMenu;
	
	// Player fields
	
	private static Cat testObj = new Cat();
	private static Rectangle testRect = new Rectangle(0, 0, 20, 10);
	
	private static void update() {
		try {
			switch(gameState) {
			
				case GameState.MainMenu: 
					graphics.clear();
					
					graphics.draw(testRect.sprite, testRect.x, testRect.y);
					graphics.draw(testObj.sprite, 4, 4);
					
					graphics.print();
					scanner.next();
					
					break;
					
				case GameState.Playing: 
				
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

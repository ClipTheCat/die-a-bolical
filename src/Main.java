// Some changes

import java.util.Scanner;

public class Main {
	
	private static boolean gameRunning = true;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static LetterGraphics graphics = new LetterGraphics(70, 32, new char[] { '╔', '╗', '╝', '╚', '═', '║', ' '});
	
	private static GameState gameState = GameState.MainMenu;
	
	// Player fields
	
	
	private static void update() {
		try {
			switch(gameState) {
			
				case GameState.MainMenu: 
					graphics.draw();
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
		
		graphics.addRect(new Rectangle(0, 0, 10, 10));
		graphics.addRect(new Rectangle(8, 5, 10, 10));
		graphics.addRect(new Rectangle(0, 7, 5, 5));
		
		while(gameRunning) {
			update();
		}
	}
	
	private static void println(String string) {
		System.out.println(string);
	}
}

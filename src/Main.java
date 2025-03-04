// Some changes

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	private static boolean gameRunning = true;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static LetterGraphics graphics = new LetterGraphics(20, 20);
	
	private static GameState gameState = GameState.MainMenu;
	
	// Player fields
	
	
	private static void update() {
		
		try {
			
			switch(gameState) {
			
				case GameState.MainMenu: 
					
					for (int i = 0 i < 20; i++) {
						println(graphics.getLine(i));
					}
					
					break;
					
				case GameState.MainMenu: 
				
					break;
					
				case GameState.MainMenu: 
	
					break;
			}
			
		} catch(InputMismatchException e) {
			
			System.out.println("An exception occured: " + e);
			scanner.nextLine();
			
		}
	}
	
	public static void main(String[] args) {
		
		while(gameRunning) {
			
			update();
			
		}
	}
	
	private static println(String input) {
		System.out.println(input);
	}
}

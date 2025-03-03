import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	private static boolean gameRunning = true;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static GameState state = GameState.MainMenu;
	
	// Player fields
	
	
	private static void update() {
		
		try {
			
			System.out.println("Input an int");
			int input = scanner.nextInt();
			System.out.println(input);
			
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
}

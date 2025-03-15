/*
 * The base class for objects that can hold a position, dimensions, and a sprite. 
 * This aims to make passing sprites and positions to the graphics manager easier.
 * buildSprite(String fileName, int startLine, int height) returns an sprite (String array) from
 * a user-defined text file. Child classes can override this method to customize how sprites are
 * created, e.g. Rectangle.
 */

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public abstract class GameObject {
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public String[] sprite;
	
	public GameObject() { }
	
	public GameObject(String spriteFileName, int startLine, int spriteLineHeight) {
		sprite = buildSprite(spriteFileName, startLine, spriteLineHeight);
	}
	
	protected static String[] buildSprite(String fileName, int startLine, int height) {
		String[] outputSprite = new String[height]; 
		File file = new File(fileName);
		
		try {
			Scanner scanner = new Scanner(file);
			for (int i = 0; i < startLine - 1; i++) {
				scanner.nextLine();
			}
			for(int i = 0; i < height; i++) {
				outputSprite[i] = scanner.nextLine();
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return outputSprite;
	}
}

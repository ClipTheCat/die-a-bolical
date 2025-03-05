/* 
 * This class contains all the code needed for drawing the text-based "sprites".
 * This aims to emulate a simplified typical pixel-based graphics API. 
 * A sprite is represented by an array of strings, with each string being drawn aligned to the 
 * left. 
 * It exposes the following methods:
 * 
 *     clear()                                 | Clears the frame (with space characters).
 *     draw(String[] sprite, int x, int y)     | Draws a sprite with its top-left corner at (x, y). 
 *     print()                                 | Prints the current "image" to the console.
 *     
 * Keep in mind characters are monospaced but are about twice as tall as they are wide, i.e. a 
 * Rectangle with equal width and height will not appear as a square. 
 */

import java.lang.StringBuilder;

public class LetterGraphics {
	
	private StringBuilder[] lines;
	
	private Rectangle canvas;
	
	public LetterGraphics(int canvasWidth, int canvasHeight) {
		if (canvasWidth < 0 || canvasHeight < 0) {
			System.out.println("Invalid canvas, defaulting to 70x31");
			canvas = new Rectangle(0, 0, 70, 31);
		} else {
			canvas = new Rectangle(0, 0, canvasWidth, canvasHeight);
		}
		
		lines = new StringBuilder[canvas.height];
	}
	
	private static void drawSlice(StringBuilder sb, String spriteSlice, int x) {
		sb.replace(x, x + spriteSlice.length(), spriteSlice);
	}
	
	public void clear() {
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new StringBuilder(new String(" ").repeat(canvas.width));
		}
	}
	
	public void draw(String[] sprite, int x, int y) {
		for (int i = 0; i < sprite.length; i++) {
			drawSlice(lines[y + i], sprite[i], x);
		}
	}
	
	public void print() {
		for (int i = 0; i < canvas.height; i++) {
			System.out.println(lines[i]);
		}
	}
}
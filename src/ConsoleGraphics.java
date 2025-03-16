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

public class ConsoleGraphics {
	
	private StringBuilder[] lines;
	
	public Rectangle canvas;
	
	public int frame;
	
	public ConsoleGraphics(int canvasWidth, int canvasHeight) {
		if (canvasWidth < 0 || canvasHeight < 0) {
			System.out.println("Invalid canvas, defaulting to 70x31");
			canvas = new Rectangle(0, 0, 70, 31);
		} else {
			canvas = new Rectangle(0, 0, canvasWidth, canvasHeight);
		}
		
		lines = new StringBuilder[canvas.height];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new StringBuilder(new String(" ").repeat(canvas.width));
		}
		
		frame = 0;
	}
	
	private void drawSlice(StringBuilder sb, String spriteSlice, int x) {
		int cutoff = x + spriteSlice.length() - canvas.width;
		if (cutoff < 0) {
			cutoff = 0;
		}
		sb.replace(
				x, 
				x + spriteSlice.length() - cutoff, 
				spriteSlice.substring(0, spriteSlice.length() - cutoff)
				);
	}
	
	public void clear() {
		String emptyLine = new String(" ").repeat(canvas.width);
		for (int i = 0; i < lines.length; i++) {
			lines[i].replace(0, lines[i].length(), emptyLine);
		}
	}
	
	public void draw(String text, int x, int y) {
		if(x >= canvas.width || y >= canvas.height || x < 0 || y < 0) {
			return;
		}
		
		drawSlice(lines[y], text, x);
	}
	
	public void draw(String[] sprite, int x, int y) {
		// Check if sprite is fully outside of the canvas
		// TODO top/left cutoff? Currently negative coordinates aren't supported even if some of
		// the sprite woud still be visible
		if(x >= canvas.width || y >= canvas.height || x < 0 || y < 0) {
			return;
		}
		
		for (int i = 0; i < sprite.length; i++) {
			// If the line trying to be drawn is outside of the canvas, exit the whole method as
			// all future lines in this sprite will also be outside
			if (y + i >= canvas.height) {
				return;
			}
			
			drawSlice(lines[y + i], sprite[i], x);
		}
	}
	
	public void print() {
		System.out.println("");
		
		lines[1].append(" Frame: " + frame);
		frame++;
		
		for (int i = 0; i < canvas.height; i++) {
			System.out.println(lines[i]);
		}
		
		System.out.println("");
		System.out.print("> ");
	}
}
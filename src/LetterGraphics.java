import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.ArrayList;
import java.lang.StringBuilder;

public class LetterGraphics {
	
	private ArrayList<Rectangle> rectangles;
	
	public Rectangle canvas;
	
	private String emptyLine;
	
	public LetterArt(int canvasWidth, int canvasHeight) {
		
		if (canvasWidth < 0 || canvasHeight < 0) {
			
			throw new Exception("Invalid canvas");
		}
		
		canvas = new Rectangle(0, 0, canvasWidth, canvasHeight);
		
		rectangles = new ArrayList<Rectangle>();
		this.canvas = canvas;
		emptyLine = new String(" ").repeat(canvas.dimensions[2]);
	}
	
	public static void rectBorder(StringBuilder sb, Rectangle rect) {
		
		if (line < rect.y || line >= rect.y + rect.height) {
			
			return;
		}
		
		if (line == rect.y) {
			
			output.setCharAt(rect.x, '╔');
			output.replace(rect.x + 1, rect.x + rect.width - 2, "═");
			output.setCharAt(rect.x + rect.width - 1, '╗');
		
		} else if (line == rect.y + rect.height - 1) {
		
			output.setCharAt(rect.x, '╚');
			output.replace(rect.x + 1, rect.x + rect.width - 2, "═");
			output.setCharAt(rect.x + rect.width - 1, '╝');
			
		} else {
			
			output.setCharAt(rect.x, '║');
			output.replace(rect.x + 1, rect.x + rect.width - 2, "═");
			output.setCharAt(rect.x + rect.width - 1, '╝');

		}
	}
	
	
	public String getLine(int line) {
		
		if (line < 0 || line >= canvas.width) {
			
			throw new Exception("Line out of range of canvas size");
			
		}
		
		StringBuilder output = new StringBuilder(emptyLine);
		
		for (Rectangle rect : rectangles) {
			
			rectBorder(output, rect);
			
		}
		
		return output;
	}
}

public class Rectangle {
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public String text;
	
	public Rectangle(int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(int x, int y, int width, int height, String text) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
}



/* 0 x
 * 1 x
 * 2
 * 3
 * 4
 * 5
 */

import java.util.ArrayList;
import java.lang.StringBuilder;

public class LetterGraphics {
	
	private ArrayList<Rectangle> rectangles;
	
	private Rectangle canvas;
	
	// In the form:
	// { topLeft, topRight, bottomRight, bottomLeft, horizontalSide, verticalSide, fill }
	private char[] borderCharacters; 
	
	public LetterGraphics(int canvasWidth, int canvasHeight, char[] borderCharacters) {
		if (canvasWidth < 0 || canvasHeight < 0) {
			System.out.println("Invalid canvas, defaulting to 20x20");
			canvas = new Rectangle(0, 0, 70, 34);
		} else {
			canvas = new Rectangle(0, 0, canvasWidth, canvasHeight);
		}
		
		rectangles = new ArrayList<Rectangle>();
		
		if (borderCharacters.length == 7) {
			this.borderCharacters = borderCharacters;
		} else {
			System.out.println("Expecting border character array of length 7, using default values");
			this.borderCharacters = new char[] { '╔', '╗', '╝', '╚', '═', '║', ' '};
		}
	}
	
	public void addRect(Rectangle rect) {
		rectangles.add(rect);
	}
	
	public void removeRect(Rectangle rect) {
		rectangles.remove(rect);
	}
	

	public void clearRects() {
		rectangles.clear();
	}
	
	private static void buildLine(char leftChar, char middleChar, char rightChar, StringBuilder sb, Rectangle rect) {
		int doubleX = 2 * rect.x;
		int doubleWidth = 2 * rect.width;
		sb.setCharAt(doubleX, leftChar);
		sb.replace(doubleX + 1, doubleX + doubleWidth - 2, String.valueOf(middleChar).repeat(doubleWidth - 2));
		sb.setCharAt(doubleX + doubleWidth - 1, rightChar);
		sb.deleteCharAt(doubleX + doubleWidth); // TODO Why does it add an extra whitespace???
	}
	
	public String getLine(int line) throws Exception {
		if (line < 0 || line >= canvas.height) {
			throw new Exception("Line out of range of canvas size");
		}
		
		// The width of everything is visually stretched by a factor of 2, because characters are (roughly?) twice as tall as they are wide
		StringBuilder output = new StringBuilder(new String(" ").repeat(2 * canvas.width));
		
		for (Rectangle rect : rectangles) {
			if (line < rect.y || line >= rect.y + rect.height) {
				continue;
			}
			
			// TODO use borderCharacters array to customize border characters 
			if (line == rect.y) {
				buildLine('╔', '═', '╗', output, rect);
			} else if (line == rect.y + rect.height - 1) {
				buildLine('╚', '═', '╝', output, rect);
			} else {
				buildLine('║', ' ', '║', output, rect);
			}
		}
		
		return output.toString();
	}
	
	public void draw() {
		try {
			for(int i = 0; i < canvas.height; i++) {
				System.out.println(getLine(i));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

/* 0 x
 * 1 x
 * 2
 * 3
 * 4
 * 5
 */

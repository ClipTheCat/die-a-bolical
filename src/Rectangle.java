/*
 * 
 */

public class Rectangle extends GameObject {
	
	public String text;
	
	// In the form:
	// { topLeft, topRight, bottomRight, bottomLeft, horizontalSide, verticalSide, fill }
	// private char[] borderCharacters; 
	
	private static String[] buildSprite(int width, int height) {
		String[] sprite = new String[height];
		
		sprite[0] = "╔" + new String("═").repeat(width - 2) + '╗';
		for (int i = 1; i < height - 1; i++) {
			sprite[i] = "║" + new String(" ").repeat(width - 2) + "║";
		}
		sprite[height - 1] = "╚" + new String("═").repeat(width - 2) + '╝';
		
		return sprite;
	}
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		/*if (borderCharacters.length == 7) {
			this.borderCharacters = borderCharacters;
		} else {
			System.out.println("Expecting border character array of length 7, using default values");
			this.borderCharacters = new char[] { '╔', '╗', '╝', '╚', '═', '║', ' '};
		}*/
		
		sprite = buildSprite(this.width, this.height);
	}
	
	public Rectangle(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		sprite = buildSprite(this.width, this.height);
	}
	
}
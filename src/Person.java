import java.util.ArrayList;


public class Person extends GameObject {
	
	// AI fields
	// The zombie's identity is stored externally
	public ArrayList<Integer> suspicions;
	
	private static String[] aliveSprite = buildSprite("Sprites", 1, 5);
	private static String[] deadSprite = buildSprite("Sprites", 7, 5);
	private static String[] zombieSprite = buildSprite("Sprites", 13, 5);
	
	public Person() {
		sprite = aliveSprite;
		suspicions = new ArrayList<Integer>();
	}
	
	public void zombify() {
		sprite = zombieSprite;
	}
	
	public void kill() {
		sprite = deadSprite;
	}
}

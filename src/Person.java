import java.util.ArrayList;


public class Person extends GameObject {
	
	// AI fields
	// The zombie's identity is stored externally
	public ArrayList<Integer> suspicions;
	
	// The sprite of a person controls their actual state, because there's no reason to add another field for this purpose
	public static final String[] aliveSprite = buildSprite("Sprites", 1, 5);
	public static final String[] deadSprite = buildSprite("Sprites", 7, 5);
	public static final String[] zombieSprite = buildSprite("Sprites", 13, 5);
	
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

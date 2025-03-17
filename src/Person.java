import java.util.ArrayList;


public class Person extends GameObject {
	
	// AI fields
	public PersonState state; // the secret zombie has a value of PersonState.Zombie in this field but their sprite is the aliveSprite
	public ArrayList<Integer> suspicions;
	public boolean quarantined;
	
	public static final String[] aliveSprite = buildSprite("Sprites", 1, 5);
	public static final String[] deadSprite = buildSprite("Sprites", 7, 5);
	public static final String[] zombieSprite = buildSprite("Sprites", 13, 5);
	
	public Person() {
		sprite = aliveSprite;
		state = PersonState.Alive;
		suspicions = new ArrayList<Integer>();
		quarantined = false;
	}
	
	public void revive() {
		state = PersonState.Alive;
		sprite = aliveSprite;
	}
	
	public void zombify() {
		if (sprite == aliveSprite && state == PersonState.Zombie) {
			return;
		}
		
		state = PersonState.Zombie;
		sprite = zombieSprite;
	}
	
	public void kill() {
		state = PersonState.Dead;
		sprite = deadSprite;
	}
}

// The class for objects representing a person in the game. This does not contain much logic and
// mostly only stores their state. 

import java.util.ArrayList;

public class Person extends GameObject {
	
	// State fields
	public PersonState state; // the secret zombie has a value of PersonState.Zombie in this field but their sprite is the aliveSprite
	public ArrayList<Integer> suspicions;
	public boolean quarantined;
	public boolean questioned;
	
	public static final String[] aliveSprite = buildSprite("Sprites", 1, 5);
	public static final String[] deadSprite = buildSprite("Sprites", 7, 5);
	public static final String[] zombieSprite = buildSprite("Sprites", 13, 5);
	
	public Person() {
		revive();
		suspicions = new ArrayList<Integer>();
		quarantined = false;
		questioned = false;
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

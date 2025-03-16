
public class Person extends GameObject {
	
	// AI fields
	// The zombie's identity is stored externally
	public String alibi;
	public Person[] suspicions;
	
	public Person() {
		sprite = buildSprite("Sprites", 1, 5);
	}
}

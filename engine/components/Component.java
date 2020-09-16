package components;

import entities.GameObject;

public abstract class Component {
	
	protected GameObject gameObject;
	protected Transform transform;
	
	public final void setup(GameObject gameObject, Transform transform) {
		this.gameObject = gameObject;
		this.transform = transform;
		start();
	}
	
	public void start() {}
	public void update() {}
	
	public void onIncreasedPosition(float x, float y, float z) {}
}
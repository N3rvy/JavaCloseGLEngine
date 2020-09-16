package player;

import org.lwjgl.input.Mouse;

import components.Transform;
import entities.Camera;
import terreins.Terrain;

public class PlayerCamera extends Camera {
	
	private final float YOFFSET = 8.5f;
	
	private float distance = 15;
	
	private Terrain terrain;
	
	Transform transform;
	
	private boolean needClick;
	
	public PlayerCamera(Transform transform, Terrain terrain, boolean needClick) {
		super(needClick);
		this.terrain = terrain;
		this.transform = transform;
		this.needClick = needClick;
	}
	
	public void move() {
		checkInput();

		if (pitch > 90)
			pitch = 90;
		else if (pitch < -90)
			pitch = -90;
		
		if (distance > 70)
			distance = 70;
		else if (distance < 0)
			distance = 0;
		
		
		calculateCameraPos();
		
		yaw = 180 - (transform.getRotation().y);
	}
	
	private void calculateCameraPos() {
		float horDistance = (float)(distance * Math.cos(Math.toRadians(pitch)));
		float verDistance = (float)(distance * Math.sin(Math.toRadians(pitch)));
		
		float theta = transform.getRotation().y;
		float offsetX = (float) (horDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horDistance * Math.cos(Math.toRadians(theta)));
		
		position.x = transform.getPosition().x - offsetX;
		position.z = transform.getPosition().z - offsetZ;
		position.y = transform.getPosition().y + verDistance + YOFFSET;
		
		float terrainHeight = terrain.getHeightInTerrain(position.x, position.z) + 0.1f;
		
		if (position.y < terrainHeight)
			position.y = terrainHeight;
	}
	
	private void checkInput() {
		if (!needClick || Mouse.isButtonDown(0))
			transform.getRotation().y -= Mouse.getDX() * 0.3f;
		if (!needClick || Mouse.isButtonDown(1))
			pitch -= Mouse.getDY() * 0.3f;

		distance -= Mouse.getDWheel() * 0.02f;
	}
}







package player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import components.Component;
import game.Game;
import rendering.DisplayManager;
import terreins.Terrain;

public class PlayerMovement extends Component {
	
	private Terrain terrain;
	
	private final float SPEED = 40,
						JUMP_FORCE = 30;
	
	private Vector3f momentum = new Vector3f();
	
	private boolean inAir = false;
	
	public PlayerMovement(Terrain terrain) {
		this.terrain = terrain;
	}
	
	@Override
	public void update() {
		checkInput();
		
		Vector3f position = transform.getPosition();
		float rotation = (float)Math.toRadians(transform.getRotation().y);
		float terrainHeigth = terrain.getHeightInTerrain(position.x, position.z);
		
		momentum.y -= Game.GRAVITY * DisplayManager.getFrameTimeSeconds();
		
		transform.increasePosition(
			(float)Math.sin(rotation) * momentum.x + (float)Math.cos(rotation) * momentum.z,
			momentum.y * DisplayManager.getFrameTimeSeconds(),
			(float)Math.cos(rotation) * momentum.x - (float)Math.sin(rotation) * momentum.z);
		
		if (position.y <= terrainHeigth) {	
			position.y = terrainHeigth;
			inAir = false;
		}
	}
	
	private void jump() {
		if (!inAir) {
			momentum.y = JUMP_FORCE;
			inAir = true;
		}
	}
	
	private void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			momentum.x = SPEED * DisplayManager.getFrameTimeSeconds();
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
			momentum.x = -SPEED * DisplayManager.getFrameTimeSeconds();
		else
			momentum.x = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			momentum.z = SPEED * DisplayManager.getFrameTimeSeconds();
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
			momentum.z = -SPEED * DisplayManager.getFrameTimeSeconds();
		else
			momentum.z = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			jump();
	}
}

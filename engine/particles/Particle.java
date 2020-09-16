package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import game.Game;
import rendering.DisplayManager;
import textures.ParticleTexture;

public class Particle {
	
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private ParticleTexture texture;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blendFactor;
	
	private float elapsedTime = 0;
	private float distance;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		
		ParticleManager.addParticle(this);
	}
	
	protected boolean update(Camera camera) {
		updateTexCoords();
		
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		
		velocity.y += -Game.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
	
	private void updateTexCoords() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumOfRows() * texture.getNumOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int)atlasProgression;
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blendFactor = atlasProgression % 1;
		
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index) {
		int column = index % texture.getNumOfRows();
		int row = index / texture.getNumOfRows();
		offset.x = (float)column / texture.getNumOfRows();
		offset.y = (float)row / texture.getNumOfRows();
	}
	
	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlendFactor() {
		return blendFactor;
	}

	public float getDistance() {
		return distance;
	}
}

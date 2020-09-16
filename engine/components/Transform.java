package components;

import org.lwjgl.util.vector.Vector3f;

public class Transform {
	
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Transform(Vector3f position, Vector3f rotation, float scale) {
		super();
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void increasePosition(float x, float y, float z) {
		position.x += x;
		position.y += y;
		position.z += z;
	}
	
	public void increaseRotation(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
		
		if (rotation.y > 360)
			rotation.y -= 360;
		else if (rotation.y < -360)
			rotation.y += 360;
	}
	
	public void increaseScale(float scale) {
		scale += scale;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
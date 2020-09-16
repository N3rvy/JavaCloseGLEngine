package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected float pitch = 20, yaw = 0, roll = 0;
	
	public Camera(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}

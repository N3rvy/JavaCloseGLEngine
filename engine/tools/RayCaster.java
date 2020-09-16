package tools;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.YESEngine;
import entities.Camera;
import terreins.Terrain;

public class RayCaster {
	
	private Matrix4f projMatrix;
	private Matrix4f viewMatrix;
	
	private Camera camera;
	
	private final float RANGE;
	private final int ITERATIONS;
	
	public RayCaster(YESEngine engine, float range, int iterations) {
		this.camera = engine.getCamera();
		this.projMatrix = engine.getRenderer().getProjMatrix();
		this.viewMatrix = Maths.createViewMatrix(camera);
		this.RANGE = range;
		this.ITERATIONS = iterations;
	}
	
	public Vector3f rayTerrain(Terrain terrain, float screenX, float screenY) {
		Vector3f currentRay = getCurrentRay(screenX, screenY);
		
		return binarySearchTerrain(
					getPointOnRay(currentRay, 0),
					getPointOnRay(currentRay, RANGE),
					terrain, ITERATIONS);
	}
	
	private Vector3f calculateRay(float x, float y) {
		Vector2f normalizedCoords = convertToGLCoords(x, y);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1, 1);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}
	
	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invView, eyeCoords, null);
		Vector3f ray = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		ray.normalise();
		return ray;
	}
	
	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invProj = Matrix4f.invert(projMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invProj, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1, 0);
	}
	
	private Vector2f convertToGLCoords(float x, float y) {
		return new Vector2f(
				(2f * x) / Display.getWidth() - 1,
				(2f * y) / Display.getHeight() - 1);
	}
	
	private Vector3f getCurrentRay(float screenX, float screenY) {
		viewMatrix = Maths.createViewMatrix(camera);
		return calculateRay(screenX, screenY);
	}
	
	//******************************************************
	
	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
	
	private Vector3f binarySearchTerrain(Vector3f start, Vector3f end, Terrain terrain, int recursivity) {
		for (int i = 0; i < recursivity; i++) {
			Vector3f middle = Maths.middleV3f(start, end);
			if (middle.y < terrain.getHeightInTerrain(middle.x, middle.z))
				end = middle;
			else
				start = middle;
		}
		return Maths.middleV3f(start, end);
	}
}















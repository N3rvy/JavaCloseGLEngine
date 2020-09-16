package rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import entities.GameObject;
import models.TexturedModel;
import shaders.StaticShader;
import shaders.TerrainShader;
import terreins.Terrain;

public class MasterRenderer {
	
	private static final float FOV = 110, NEAR_PLANE = 0.1f, FAR_PLANE = 400;
	
	private static final Vector3f skyColor = new Vector3f(0.529f, 0.807f, 0.921f);
	
	private Matrix4f projMatrix;
	
	private StaticShader shader = new StaticShader();
	private ObjectRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private Map<TexturedModel, List<GameObject>> entities = new HashMap<TexturedModel, List<GameObject>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer() {
		enableCulling();
		createProjMatrix();
		renderer = new ObjectRenderer(shader, projMatrix);
		
		terrainRenderer = new TerrainRenderer(terrainShader, projMatrix);
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights, Camera camera) {
		prepare();
		
		shader.start();
		shader.loadSkyColor(skyColor);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadSkyColor(skyColor);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		entities.clear();
		terrains.clear();
	}
	
	public Matrix4f getProjMatrix() {
		return projMatrix;
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(GameObject entity) {
		TexturedModel entityModel = entity.getModel();
		List<GameObject> batch = entities.get(entityModel);
		if (batch != null)
			batch.add(entity);
		else {
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(skyColor.x, skyColor.y, skyColor.z, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	private void createProjMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        
        projMatrix = new Matrix4f();
        projMatrix.m00 = x_scale;
        projMatrix.m11 = y_scale;
        projMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projMatrix.m23 = -1;
        projMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projMatrix.m33 = 0;
	}
}
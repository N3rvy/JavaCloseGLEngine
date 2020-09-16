package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import audio.AudioManager;
import entities.Camera;
import entities.GameObject;
import entities.Light;
import particles.ParticleManager;
import rendering.DisplayManager;
import rendering.GuiRenderer;
import rendering.MasterRenderer;
import terreins.Terrain;
import text.TextMaster;
import textures.GuiTexture;
import tools.Loader;

public class YESEngine {
	
	private Loader loader;
	private MasterRenderer renderer;
	private GuiRenderer guiRenderer;
	
	private Camera camera;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<Light> lights = new ArrayList<Light>();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public YESEngine(int width, int heigth, int fpsCap) {
		DisplayManager.createDisplay(width, heigth, fpsCap);
		
		loader = new Loader();
		renderer = new MasterRenderer();
		guiRenderer = new GuiRenderer(loader);
		
		ParticleManager.init(loader, renderer.getProjMatrix());
		TextMaster.init(loader);
		AudioManager.init();
		AudioManager.setListenerData(new Vector3f(), 0);
	}
	
	public void update() {
		for (GameObject gameObject : gameObjects)
			gameObject.update();

		for (Terrain terrain : terrains)
			renderer.processTerrain(terrain);
		
		for (GameObject entity : gameObjects)
			renderer.processEntity(entity);
		
		ParticleManager.update(camera);
		
		renderer.render(lights, camera);
		ParticleManager.renderParticles(camera);
		guiRenderer.render(guis);
		TextMaster.render();
		
		DisplayManager.updateDisplay();
	}

	public void cleanUp() {
		ParticleManager.cleanUp();
		TextMaster.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		AudioManager.cleanUp();
	}
	
	public void Instantiate(GameObject go) {
		gameObjects.add(go);
	}
	
	public void InstantiateLight(Light light) {
		lights.add(light);
	}
	
	public void InstantiateGUI(GuiTexture gui) {
		guis.add(gui);
	}
	
	public void InstantiateTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public Loader getLoader() {
		return loader;
	}

	public MasterRenderer getRenderer() {
		return renderer;
	}

	public GuiRenderer getGuiRenderer() {
		return guiRenderer;
	}
	
	public Camera getCamera() {
		return camera;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public List<Light> getLights() {
		return lights;
	}

	public List<GuiTexture> getGuis() {
		return guis;
	}
	

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
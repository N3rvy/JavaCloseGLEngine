package mapManager;

import java.util.List;

import entities.GameObject;
import models.TexturedModel;
import player.PlayerCamera;
import rendering.MasterRenderer;
import terreins.Terrain;

public class GameMap {
	private Terrain terrain;
	private List<GameObject> entities;
	private GameObject player;
	private PlayerCamera camera;
	private List<TexturedModel> models;
	
	public GameMap(Terrain terrain, List<GameObject> entities, GameObject player, PlayerCamera camera, List<TexturedModel> models) {
		this.terrain = terrain;
		this.entities = entities;
		this.player = player;
		this.camera = camera;
		this.models = models;
	}
	
	public void update() {
		camera.move();
	}
	
	public void process(MasterRenderer renderer) {
		for (GameObject gameObject : entities)
			gameObject.update();
		renderer.processTerrain(terrain);
		for (GameObject entity : entities)
			renderer.processEntity(entity);
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public List<GameObject> getEntities() {
		return entities;
	}
	
	public List<TexturedModel> getModels() {
		return models;
	}

	public GameObject getPlayer() {
		return player;
	}

	public PlayerCamera getCamera() {
		return camera;
	}
}
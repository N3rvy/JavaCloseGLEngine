package multiplayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import components.Transform;
import entities.GameObject;
import models.TexturedModel;
import tools.Loader;

public class GameManager {
	protected Loader loader;
	private TexturedModel playerModel;
	protected Map<Integer, GameObject> players;
	
	
	public GameManager(Loader loader) {
		this.loader = loader;
		
		playerModel = loader.LoadTexturedModel("player", "playerTexture");
		players = new HashMap<Integer, GameObject>();
	}
	
	public void spawnPlayer(int id, Vector3f pos, float rot) {
		players.put(id, new GameObject(playerModel, new Transform(pos, new Vector3f(0, rot, 0), 0.1f)));
	}
	
	public void removePlayer(int id) {
		if (players.containsKey(id))
			players.remove(id);
	}
	
	public void updatePlayer(int playerID, Vector3f pos, float rot) {
		if (players.containsKey(playerID)) {
			final Transform player = players.get(playerID).getTransform();
			player.setPosition(pos);
			player.setRotation(new Vector3f(0, rot, 0));
		}
	}
	
	public Collection<GameObject> getPlayerList() {
		return players.values();
	}
}

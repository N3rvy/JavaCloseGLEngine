package mapManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import components.Transform;
import entities.GameObject;
import models.TexturedModel;
import player.PlayerCamera;
import player.PlayerMovement;
import terreins.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import tools.Loader;

public class MapLoader {
	private static final String MAP_PATH = "src/mapManager/",
								TEX_PATH = "textures/",
								MODELS_PATH = "models/";
	
	public static GameMap loadMap(String mapName, Loader loader) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(MAP_PATH + mapName + ".gm")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		
		//************ Loading terrain ****************
		Terrain terrain = null;
		try {
			terrain = loadTerrain(br.readLine(), loader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//************ Loading models list ************
		List<TexturedModel> models = new ArrayList<TexturedModel>();
		try {
			while (!(line = br.readLine()).equals("")) {
				models.add(loadModel(line, loader));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//*********** Loading objects *****************
		List<GameObject> entities = new ArrayList<GameObject>();
		try {
			while (!(line = br.readLine()).equals("")) {
				entities.add(loadObject(line, models, terrain));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameObject player = null;
		try {
			player = loadPlayer(br.readLine(), loader, terrain);
		} catch (IOException e) {
			e.printStackTrace();
		}
		entities.add(player);
		
		PlayerCamera camera = new PlayerCamera(player.getTransform(), terrain, true);
		
		return new GameMap(terrain, entities, player, camera, models);
	}
	
	private static GameObject loadPlayer(String line, Loader loader, Terrain terrain) {
		String[] args = line.split(" ");
		TexturedModel playerModel = loader.LoadTexturedModel(MODELS_PATH + args[0], TEX_PATH + args[1]);
		
		float x = Float.parseFloat(args[2]);
		float z = Float.parseFloat(args[3]);
		
		return new GameObject(playerModel,
				new Transform(
					new Vector3f(
					x,
					terrain.getHeightInTerrain(x, z),
					z),
					new Vector3f(0, 0, 0),
					0.7f),
				new PlayerMovement(terrain));
	}
	
	private static GameObject loadObject(String line, List<TexturedModel> models, Terrain terrain) {
		String[] args = line.split(" ");
		TexturedModel model = models.get(Integer.parseInt(args[0]));
		float x = Float.parseFloat(args[1]);
		float z = Float.parseFloat(args[2]);
		
		GameObject object;
		
		if (args.length > 4)
			object = new GameObject(
				model,
				Integer.parseInt(args[4]),
				new Transform(
					new Vector3f(
							x,
							terrain .getHeightInTerrain(x, z),
							z),
					new Vector3f(0, 0, 0),
					Float.parseFloat(args[3])));
		else
			object = new GameObject(
					model,
					new Transform(
						new Vector3f(
								x,
								terrain .getHeightInTerrain(x, z),
								z),
						new Vector3f(0, 0, 0),
						Float.parseFloat(args[3])));
		
		return object;
	}
	
	private static Terrain loadTerrain(String line, Loader loader) {
		String[] args = line.split(" ");
		
		final String TEX_FOLDER = "textures/terrain/";
		
		TerrainTexturePack texPack = new TerrainTexturePack(
				new TerrainTexture(loader.loadTexture(TEX_FOLDER + args[0])),
				new TerrainTexture(loader.loadTexture(TEX_FOLDER + args[1])),
				new TerrainTexture(loader.loadTexture(TEX_FOLDER + args[2])),
				new TerrainTexture(loader.loadTexture(TEX_FOLDER + args[3])));
		
		return new Terrain(0, 0, loader, texPack,
				new TerrainTexture(loader.loadTexture(TEX_FOLDER + args[4])),
				TEX_FOLDER + args[5]);
	}
	
	private static TexturedModel loadModel(String line, Loader loader) {
		String[] args = line.split(" ");
		
		TexturedModel model = loader.LoadTexturedModel(MODELS_PATH + args[0], TEX_PATH + args[1]);
		if (Integer.parseInt(args[2]) != 0) {
			model.getTexture().setHasTransparency(true);
			model.getTexture().setNumberOfRows(2);
		}
		
		return model;
	}
}
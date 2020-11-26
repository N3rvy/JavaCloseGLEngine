package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import components.Animation;
import components.Transform;
import engine.YESEngine;
import entities.GameObject;
import entities.Light;
import mapManager.GameMap;
import mapManager.MapLoader;
import models.TexturedModel;
import tools.RayCaster;

/*
 * TODO Optimize particles
 */

public class MainGameLoop {
	
	private static final float DAYTIME = 0.4f;
	
	public static void main(String[] args) {
		YESEngine engine = new YESEngine(1280, 720, 60);
		
		//******************** Map ****************************
		GameMap map = MapLoader.loadMap("map1", engine.getLoader());
		
		//****************** Lights ***************************
		engine.InstantiateLight(new Light(new Vector3f(1000, 200, 1000), new Vector3f(DAYTIME, DAYTIME, DAYTIME)));
		
		//******************* Loading *************************
		for (GameObject go : map.getEntities())
			engine.Instantiate(go);
		engine.setCamera(map.getCamera());
		engine.InstantiateTerrain(map.getTerrain());
		
		//******************* Utils ***************************
		RayCaster rayCaster = new RayCaster(engine, 200, 50);
		
		Mouse.setGrabbed(false);
		
		TexturedModel model = engine.getLoader().LoadTexturedModel("models/lamp", "textures/lampTexture");
		
		GameObject swooshyShwooshtTree =
				new GameObject(
						model,
						new Transform(
								new Vector3f(0, 0, 0),
								new Vector3f(0, 0, 0),
								4.5f),
						new Animation(
								new Vector3f(5, 0, 5),
								new Vector3f(25, 0, 5),
								4));
		
		engine.Instantiate(swooshyShwooshtTree);
		
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		
		//***************** Game loop *************************
		while(!Display.isCloseRequested()) {
			
			if (Keyboard.isKeyDown(Keyboard.KEY_F))
				engine.Instantiate(
						new GameObject(map.getModels().get(2),
						new Transform(
								rayCaster.rayTerrain(map.getTerrain(), Mouse.getX(), Mouse.getY()),
								new Vector3f(0, 0, 0),
								1)));
			
			//Game logic
			map.update();
			
			engine.update();
		}
		
		engine.cleanUp();
	}
}





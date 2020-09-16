package rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static int screenWidth, screenHeigth, fps_cap;
	
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay(int width, int heigth, int fpsCap) {
		
		screenWidth = width;
		screenHeigth = heigth;
		fps_cap = fpsCap;
		
		ContextAttribs attribs = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeigth));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("YesEngine");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, screenWidth, screenHeigth);
		
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		Display.sync(fps_cap);
		Display.update();
		
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		
		lastFrameTime = currentFrameTime;
	}
	
	public static void SetTitle(String title) {
		Display.setTitle(title);
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
}

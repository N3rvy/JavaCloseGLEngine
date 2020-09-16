package audio;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

public class AudioManager {

	private static Map<String, Integer> buffers = new HashMap<String, Integer>();
	
	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setListenerData(Vector3f pos, float yRot) {
		AL10.alListener3f(AL10.AL_POSITION, pos.x, pos.y, pos.z);
		
		FloatBuffer orientationBuffer = BufferUtils.createFloatBuffer(6);
		orientationBuffer.put((float)Math.sin(Math.toRadians(yRot)));
		orientationBuffer.put(0);
		orientationBuffer.put((float)Math.cos(Math.toRadians(yRot)));
		orientationBuffer.put(0);
		orientationBuffer.put(1);
		orientationBuffer.put(0);
		orientationBuffer.flip();
		
		AL10.alListener(AL10.AL_ORIENTATION, orientationBuffer);
	}
	
	public static int loadSound(String soundName, String file) {
		int buffer = AL10.alGenBuffers();
		buffers.put(soundName, buffer);
		WaveData waveFile = WaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}
	
	public static void cleanUp() {
		for (int buffer : buffers.values())
			AL10.alDeleteBuffers(buffer);
		
		AL.destroy();
	}
	
	public static int getSound(String soundName) {
		return buffers.get(soundName);
	}
}
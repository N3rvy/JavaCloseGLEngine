package components;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class AudioSource extends Component {
	private int sourceID;
	
	public AudioSource(float maxDistance) {
		sourceID = AL10.alGenSources();
		AL10.alSourcef(sourceID, AL10.AL_ROLLOFF_FACTOR, 1);
		AL10.alSourcef(sourceID, AL10.AL_REFERENCE_DISTANCE, 10);
		AL10.alSourcef(sourceID, AL10.AL_MAX_DISTANCE, maxDistance);
	}
	
	@Override
	public void update() {
		Vector3f pos = transform.getPosition();
		AL10.alSource3f(sourceID, AL10.AL_POSITION, pos.x, pos.y, pos.z);
	}
	
	public void play(int buffer) {
		stop();
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		continuePlay();
	}
	
	public void pause() {
		AL10.alSourcePause(sourceID);
	}
	
	public void continuePlay() {
		AL10.alSourcePlay(sourceID);
	}
	
	public void stop() {
		AL10.alSourceStop(sourceID);
	}
	
	public void delete() {
		stop();
		AL10.alDeleteSources(sourceID);
	}
	
	public void setVolume(float volume) {
		AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
	}
	
	public void setPitch(float pitch) {
		AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch);
	}
	
	public void setLooping(boolean value) {
		AL10.alSourcei(sourceID, AL10.AL_LOOPING, value ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
}
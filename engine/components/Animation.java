package components;

import org.lwjgl.util.vector.Vector3f;

import rendering.DisplayManager;
import tools.Maths;

public class Animation extends Component {
	
	private Vector3f pos1, pos2;
	private float time, elapsedTime = 0;
	
	private boolean active = true;
	
	public Animation(Vector3f pos1, Vector3f pos2, float time) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.time = time;
	}
	
	@Override
	public void update() {
		if (!active) return;
		
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		
		if (elapsedTime <= time)
			transform.setPosition(Maths.interpolate(pos1, pos2, elapsedTime / time));
		else {
			transform.setPosition(pos2);
			active = false;
		}
	}
	
	public void restart(Vector3f pos1, Vector3f pos2, float time) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.time = time;
		active = true;
	}

	public boolean isActive() {
		return active;
	}
}
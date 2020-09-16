package components;

import org.lwjgl.util.vector.Vector3f;

public class BoxCollider extends Component {
	
	private Vector3f[] corners = new Vector3f[8];
	
	public BoxCollider(float width, float height) {
		Vector3f position = transform.getPosition();
		
		corners[0] = new Vector3f(
				position.x + width/2,
				position.y,
				position.z + width/2);
		corners[1] = new Vector3f(
				position.x + width/2,
				position.y,
				position.z - width/2);
		corners[2] = new Vector3f(
				position.x - width/2,
				position.y,
				position.z + width/2);
		corners[3] = new Vector3f(
				position.x - width/2,
				position.y,
				position.z - width/2);
		corners[4] = new Vector3f(
				position.x + width/2,
				position.y + height,
				position.z + width/2);
		corners[5] = new Vector3f(
				position.x + width/2,
				position.y + height,
				position.z - width/2);
		corners[6] = new Vector3f(
				position.x - width/2,
				position.y + height,
				position.z + width/2);
		corners[7] = new Vector3f(
				position.x - width/2,
				position.y + height,
				position.z - width/2);
	}
}
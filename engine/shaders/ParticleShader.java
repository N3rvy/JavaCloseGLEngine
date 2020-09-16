package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class ParticleShader extends ShaderProgram {

	private static final String SHADER_FILE = "engine/shaderFiles/particle";

	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_texOffset1;
	private int location_texOffset2;
	private int location_texCoordInfo;

	public ParticleShader() {
		super(SHADER_FILE + "VS.glsl", SHADER_FILE + "FS.glsl");
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_texOffset1 = super.getUniformLocation("texOffset1");
		location_texOffset2 = super.getUniformLocation("texOffset2");
		location_texCoordInfo = super.getUniformLocation("texCoordInfo");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}
	
	public void loadTexCoords(Vector2f offset1, Vector2f offset2, float numRows, float blend) {
		load2DVector(location_texOffset1, offset1);
		load2DVector(location_texOffset2, offset2);
		load2DVector(location_texCoordInfo, new Vector2f(numRows, blend));
	}

	public void loadModelViewMatrix(Matrix4f modelView) {
		loadMatrix(location_modelViewMatrix, modelView);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}

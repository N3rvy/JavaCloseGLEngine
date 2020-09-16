package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class GuiShader extends ShaderProgram {
	
	private static final String SHADER_FILE = "engine/shaderFiles/gui";
	
	private int location_transMatrix;

	public GuiShader() {
		super(SHADER_FILE + "VS.glsl", SHADER_FILE + "FS.glsl");
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transMatrix = super.getUniformLocation("transMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
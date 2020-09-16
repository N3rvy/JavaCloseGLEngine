package shaders;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram{

	private static final String SHADER_FILE = "engine/shaderFiles/font";
	
	private int location_color;
	private int location_translation;
	private int location_outlineColor;
	private int location_offset;
	private int location_width;
	private int location_edge;
	private int location_borderWidth;
	private int location_borderEdge;
	
	public FontShader() {
		super(SHADER_FILE + "VS.glsl", SHADER_FILE + "FS.glsl");
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = getUniformLocation("color");
		location_translation = getUniformLocation("translation");
		location_outlineColor = getUniformLocation("outlineColor");
		location_offset = getUniformLocation("offset");
		location_width = getUniformLocation("width");
		location_edge = getUniformLocation("edge");
		location_borderWidth = getUniformLocation("borderWidth");
		location_borderEdge = getUniformLocation("borderEdge");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "texCoords");
	}
	
	public void loadOutlineColor(Vector3f color) {
		loadVector(location_outlineColor, color);
	}
	
	public void loadShadowOffset(Vector2f offset) {
		load2DVector(location_offset, offset);
	}
	
	public void loadWidth(float width, float edge) {
		loadFloat(location_width, width);
		loadFloat(location_edge, edge);
	}
	
	public void loadBorderWidth(float width, float edge) {
		loadFloat(location_borderWidth, width);
		loadFloat(location_borderEdge, edge);
	}
	
	public void loadColor(Vector3f color) {
		loadVector(location_color, color);
	}
	
	public void loadTranslation(Vector2f translation) {
		load2DVector(location_translation, translation);
	}
}

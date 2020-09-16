package fontMeshCreator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import text.TextMaster;

public class GUIText {

	private String textString;
	private float fontSize;

	private int textMeshVao;
	private int vertexCount;

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;

	private FontType font;

	private boolean centerText = false;
	
	private Vector3f color = new Vector3f(0, 0, 0);
	private Vector3f outlineColor = new Vector3f(1, 0, 0);
	private Vector2f shadowOffset = new Vector2f(0, 0);
	private float width = 0.5f, edge = 0.1f;
	private float borderWidth = 0, borderEdge = 0.4f;
	
	public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
			boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		
		TextMaster.loadText(this);
	}
	
	public void remove() {
		TextMaster.removeText(this);
	}
	
	public void setText(String text) {
		textString = text;
		TextMaster.reloadText(this);
	}

	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	public void setColour(float r, float g, float b) {
		color.set(r, g, b);
	}
	
	public void setWidth(float width, float edge) {
		this.width = width;
		this.edge = edge;
	}
	
	public void setBorder(float width, float edge) {
		borderWidth = width;
		borderEdge = edge;
	}
	
	public void setShadowOffset(Vector2f offset) {
		shadowOffset = offset;
	}
	
	public void setOutlineColor(float r, float g, float b) {
		outlineColor = new Vector3f(r, g, b);
	}
	
	public FontType getFont() {
		return font;
	}
	
	public Vector3f getColour() {
		return color;
	}
	
	public int getNumberOfLines() {
		return numberOfLines;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public int getMesh() {
		return textMeshVao;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}
	
	protected float getFontSize() {
		return fontSize;
	}
	
	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}
	
	protected boolean isCentered() {
		return centerText;
	}
	protected float getMaxLineSize() {
		return lineMaxSize;
	}
	protected String getTextString() {
		return textString;
	}

	public Vector3f getColor() {
		return color;
	}

	public Vector3f getOutlineColor() {
		return outlineColor;
	}

	public float getWidth() {
		return width;
	}

	public float getEdge() {
		return edge;
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public float getBorderEdge() {
		return borderEdge;
	}

	public Vector2f getShadowOffset() {
		return shadowOffset;
	}
}

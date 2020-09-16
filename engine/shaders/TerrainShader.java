package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import tools.Maths;

public class TerrainShader extends ShaderProgram {
	
	private static final int MAX_LIGHTS = 10;
	
	private static final String SHADERFILE = "engine/shaderFiles/terrain";
	
	private int location_transMatrix;
	private int location_projMatrix;
	private int location_viewMatrix;
	private int[] location_lightPosition;
	private int[] location_lightColor;
	private int[] location_attenuation;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_bgTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_numOfLights;
	
	public TerrainShader() {
		super(SHADERFILE + "VS.glsl", SHADERFILE + "FS.glsl");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "pos");
		super.bindAttribute(1, "texCoords");
		super.bindAttribute(2, "normal");
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transMatrix = super.getUniformLocation("transMatrix");
		location_projMatrix = super.getUniformLocation("projMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
		location_bgTexture = super.getUniformLocation("bgTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap = super.getUniformLocation("blendMap");
		location_numOfLights = super.getUniformLocation("numOfLights");
		
		location_lightPosition = new int[MAX_LIGHTS];		
		location_lightColor = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	public void connectTextureUnits() {
		loadInt(location_bgTexture, 0);
		loadInt(location_rTexture, 1);
		loadInt(location_gTexture, 2);
		loadInt(location_bTexture, 3);
		loadInt(location_blendMap, 4);
	}
	
	public void loadSkyColor(Vector3f color) {
		loadVector(location_skyColor, color);
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		loadFloat(location_reflectivity, reflectivity);
		loadFloat(location_shineDamper, damper);
	}
	
	public void loadTransMatrix(Matrix4f matrix) {
		loadMatrix(location_transMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights) {
		loadInt(location_numOfLights, lights.size());
		
		for (int i = 0; i < MAX_LIGHTS; i++) {
			if (i < lights.size()) {
				loadVector(location_lightPosition[i], lights.get(i).getPosition());
				loadVector(location_lightColor[i], lights.get(i).getColor());
				loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			} else {
				loadVector(location_lightPosition[i], new Vector3f(0,0,0));
				loadVector(location_lightColor[i], new Vector3f(0,0,0));
				loadVector(location_attenuation[i], new Vector3f(1,0,0));
			}
		}
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}
}
package rendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import components.Transform;
import entities.GameObject;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import tools.Maths;

public class ObjectRenderer {
	
	private StaticShader shader;
	
	public ObjectRenderer(StaticShader shader, Matrix4f projMatrix) {
		
		this.shader = shader;
		
		shader.start();
		shader.loadProjectionMatrix(projMatrix);
		shader.stop();
	}
	
	public void render(Map<TexturedModel, List<GameObject>> entities) {
		for(TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<GameObject> batch = entities.get(model);
			for (GameObject entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel texModel) {
		RawModel model = texModel.getRawModel();
		
		GL30.glBindVertexArray(model.getVaoID());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = texModel.getTexture();
		
		if (texture.isHasTransparency())
			MasterRenderer.disableCulling();
		
		shader.loadNumberOfRows(texture.getNumberOfRows());
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texModel.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(GameObject object) {
		final Transform transform = object.getTransform();
		Matrix4f transMatrix = Maths.createTransMatrix(transform.getPosition(), transform.getRotation(), transform.getScale());
		
		shader.loadTransMatrix(transMatrix);
		shader.loadOffset(object.getTextureXOffset(), object.getTextureYOffset());
	}
}










package entities;

import java.util.ArrayList;
import java.util.List;

import components.Component;
import components.Transform;
import models.TexturedModel;

public class GameObject {
	
	private TexturedModel model;
	
	private int textureIndex = 0;
	
	protected Transform transform;
	private List<Component> components = new ArrayList<Component>();
	
	public GameObject(TexturedModel model, Transform transform, Component... components) {
		this.model = model;
		this.transform = transform;
		
		for (Component component : components) {
			this.components.add(component);
			component.setup(this, transform);
		}
	}
	
	public GameObject(TexturedModel model, int index, Transform transform, Component... components) {
		this.model = model;
		this.textureIndex = index;
		this.transform = transform;
		
		for (Component component : components) {
			this.components.add(component);
			component.setup(this, transform);
		}
	}
	
	public void addComponent(Component component) {
		components.add(component);
		component.setup(this, transform);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<T> c) {
		for (Component component : components)
			if (component.getClass() == c)
				return (T)component;
		return null;
	}
	
	public void update() {
		for (Component component : components)
			component.update();
	}
	
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}
	
	public Transform getTransform() {
		return transform;
	}
}
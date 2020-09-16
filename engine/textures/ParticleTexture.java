package textures;

import engine.YESEngine;

public class ParticleTexture {
	
	private int textuerID, numOfRows;
	private boolean additive;

	public ParticleTexture(YESEngine engine, String textureFile, int numOfRows, boolean additive) {
		this.textuerID = engine.getLoader().loadTexture("particles/" + textureFile);
		this.numOfRows = numOfRows;
		this.additive = additive;
	}

	public int getTextuerID() {
		return textuerID;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public boolean isAdditive() {
		return additive;
	}
}
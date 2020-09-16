package models;

public class RawModel {
	private int vaoID, vartexCount;
	
	public RawModel(int vaoID, int vartexCount) {
		this.vaoID = vaoID;
		this.vartexCount = vartexCount;
	}
	
	public int getVaoID() { return vaoID; }
	public int getVertexCount() { return vartexCount; }
}

package de.skysoldier.beatris;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class RenderComponent2d {
	
	private FloatBuffer modelData;
	private float vertexData[];
	private Vector3f colorData;
	
	public RenderComponent2d(Vector3f color, float vertexData[]){
		modelData = BufferUtils.createFloatBuffer(16);
		this.vertexData = vertexData;
		this.colorData = color;
		init();
	}
	
	public void init(){
		setModelTranslation(new Vector2f());
	}
	
	public void setModelTranslation(Vector2f translation){
		Matrix4f model = new Matrix4f();
		model.translate(translation);
		model.store(modelData);
		modelData.flip();
	}
	
	public float[] getVertexData(){
		return vertexData;
	}
	
	public Vector3f getColorData(){
		return colorData;
	}
	
	public FloatBuffer getModelData(){
		return modelData;
	}
}
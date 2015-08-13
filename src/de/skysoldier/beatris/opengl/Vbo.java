package de.skysoldier.beatris.opengl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class Vbo {
	
	private int id;
	private FloatBuffer dataBuffer;
	
	private static int active;
	private static int DEFAULT_SIZE = 100;
	
	public Vbo(){
		this(DEFAULT_SIZE);
	}
	
	public Vbo(int size){
		id = GL15.glGenBuffers();
		dataBuffer = BufferUtils.createFloatBuffer(size);
		bind();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, size, GL15.GL_STATIC_DRAW);
	}
	
	public void initDefaultVertexAttributes(ShaderProgram program){
		int vertexPositionId = program.getAttributeLocation("vertexPosition");
		GL20.glEnableVertexAttribArray(vertexPositionId);
		GL20.glVertexAttribPointer(vertexPositionId, 2, GL11.GL_FLOAT, false, 2 * Float.SIZE / 8, 0);
	}
	
	public void initVertexAttributes(ShaderProgram program, String names[], int sizes[]){
		int fullSize = 0;
		int currentSize = 0;
		for(Integer i : sizes) fullSize += i;
		for(int i = 0; i < names.length; i++){
			int vertexAttributeId = program.getAttributeLocation(names[i]);
			GL20.glEnableVertexAttribArray(vertexAttributeId);
			int size = sizes[i];
			GL20.glVertexAttribPointer(vertexAttributeId, size, GL11.GL_FLOAT, false, fullSize * Float.SIZE / 8, currentSize * Float.SIZE / 8);
			currentSize += size;
		}
	}
	
	public void putData(float data[]){
		dataBuffer.put(data);
	}
	
	public void putData(Vector3f data){
		putData(new float[]{data.x, data.y, data.z});
	}
	
	public void flush(){
		bind();
		dataBuffer.flip();
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, dataBuffer);
	}
	
	public void bind(){
		if(id != Vbo.active){
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
			Vbo.active = id;
		}
	}
	
	public static void bindNone(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		Vbo.active = 0;
	}
}

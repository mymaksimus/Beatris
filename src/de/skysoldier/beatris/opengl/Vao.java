package de.skysoldier.beatris.opengl;

import org.lwjgl.opengl.GL30;

public class Vao {
	
	private int id;
	
	private static int active;
	
	public Vao(){
		id = GL30.glGenVertexArrays();
	}
	
	public void bind(){
		if(id != Vao.active){
			GL30.glBindVertexArray(id);
			Vao.active = id;
		}
	}
	
	public static void bindNone(){
		GL30.glBindVertexArray(0);
		Vao.active = 0;
	}
}

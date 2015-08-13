package de.skysoldier.beatris.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
	
	private int id;
	
	public Shader(int shaderType){
		id = GL20.glCreateShader(shaderType);
	}
	
	public void init(String content){
		GL20.glShaderSource(id, content);
	}
	
	public void compile() throws RuntimeException {
		GL20.glCompileShader(id);
		int compileState = GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS);
		if(compileState == GL11.GL_FALSE){
			int infoLogLength = GL20.glGetShaderi(id, GL20.GL_INFO_LOG_LENGTH);
			String InfoLogContent = GL20.glGetShaderInfoLog(id, infoLogLength);
			throw new RuntimeException(InfoLogContent);
		}
	}
	
	public int getId(){
		return id;
	}
}

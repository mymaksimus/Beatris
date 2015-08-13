package de.skysoldier.beatris.opengl;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	
	private int id;
	private ArrayList<Shader> shader;
	private HashMap<String, Integer> attributeLocations;
	private HashMap<String, Integer> uniformLocations;
	
	private static int active;
	
	public ShaderProgram(){
		id = GL20.glCreateProgram();
		attributeLocations = new HashMap<>();
		uniformLocations = new HashMap<>();
		shader = new ArrayList<>();
	}
	
	public void attachShader(Shader shader){
		this.shader.add(shader);
	}
	
	public void link() throws RuntimeException {
		for(Shader shader : this.shader){
			GL20.glAttachShader(id, shader.getId());
		}
		GL20.glLinkProgram(id);
		int state = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS);
		if(state == GL11.GL_FALSE){
			int logLength = GL20.glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH);
			String logContent = GL20.glGetProgramInfoLog(id, logLength);
			throw new RuntimeException(logContent);
		}
	}
	
	public int getUniformLocation(String name){
		if(!uniformLocations.containsKey(name)){
			uniformLocations.put(name, GL20.glGetUniformLocation(id, name));
		}
		return uniformLocations.get(name);
	}
	
	public int getAttributeLocation(String name){
		if(!attributeLocations.containsKey(name)){
			attributeLocations.put(name, GL20.glGetAttribLocation(id, name));
		}
		return attributeLocations.get(name);
	}
	
	public int getId(){
		return id;
	}
	
	public void use(){
		if(id != ShaderProgram.active){
			GL20.glUseProgram(id);
			ShaderProgram.active = id;
		}
	}
	
	public static void useNone(){
		GL20.glUseProgram(0);
		ShaderProgram.active = 0;
	}
}

package de.skysoldier.beatris.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.beatris.GameObject;
import de.skysoldier.beatris.GameScene;

public class Renderer {
	
	private GameScene currentScene;
	private ShaderProgram program;
	private Vao renderVao;
	private Vbo vertexDataVbo;
	private Vbo colorDataVbo;
	
	private double lastFrame, thisFrame;
	private double delta;
	
	public Renderer(ShaderProgram program){
		renderVao = new Vao();
		renderVao.bind();
		vertexDataVbo = new Vbo();
		vertexDataVbo.initDefaultVertexAttributes(program);
		colorDataVbo = new Vbo();
		colorDataVbo.initVertexAttributes(program, new String[]{"vertexColor"}, new int[]{3});
		
		this.program = program;
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void bindScene(GameScene scene){
		this.currentScene = scene;
		GL20.glUniformMatrix4fv(scene.getShaderProgram().getUniformLocation("projection"), false, scene.getCamera().getProjectionData());
		lastFrame = GLFW.glfwGetTime();
	}
	
	public void render(){
		thisFrame = GLFW.glfwGetTime();
		delta = thisFrame - lastFrame;
		lastFrame = thisFrame;
		program.use();
		GL20.glUniformMatrix4fv(currentScene.getShaderProgram().getUniformLocation("camera"), false, currentScene.getCamera().getCameraData());
		currentScene.update(delta);
		for(GameObject object : currentScene){
			object.update(delta);
			GL20.glUniformMatrix4fv(currentScene.getShaderProgram().getUniformLocation("model"), false, object.getRenderComponent().getModelData());
			float data[] = object.getRenderComponent().getVertexData();
			vertexDataVbo.putData(data);
			Vector3f color = object.getRenderComponent().getColorData();
			for(int i = 0; i < data.length / 2; i++) colorDataVbo.putData(color);
			vertexDataVbo.flush();
			colorDataVbo.flush();
			renderVao.bind();
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, data.length / 2);
			Vao.bindNone();
		}
	}
}

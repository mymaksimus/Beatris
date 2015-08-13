package de.skysoldier.beatris;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import de.skysoldier.beatris.opengl.Camera;
import de.skysoldier.beatris.opengl.Renderer;
import de.skysoldier.beatris.opengl.Shader;
import de.skysoldier.beatris.opengl.ShaderProgram;

public class Beatris {
	
	private long windowId;
	private ShaderProgram program1;
	private TetrisField field;
	
	private GLFWKeyCallback keyCallback;
	
	public Beatris(){
		windowId = createWindow();
		program1 = initGl();
		loop();
	}
	
	private long createWindow(){
		GLFW.glfwInit();
		long windowId = glfwCreateWindow(800, 600, "beatris 0.0.1", 0, 0);
		GLFW.glfwMakeContextCurrent(windowId);
		ByteBuffer videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		int screenWidth = GLFWvidmode.width(videoMode);
		int screenHeight = GLFWvidmode.height(videoMode);
		GLFW.glfwSetKeyCallback(windowId, keyCallback = new GLFWKeyCallback(){
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods){
				System.out.println(action);
				switch(action){
				case 0:
					field.keyReleased(key);
					break;
				case 1:
					field.keyPressed(key);
					break;
				case 2:
					field.keyHold(key);
					break;
				}
			}
		});
		GLFW.glfwSetWindowPos(windowId, (int) (0.5 * (screenWidth - 800)), (int) (0.5 * (screenHeight - 600)));
		GLFW.glfwSwapInterval(0);
		GLFW.glfwShowWindow(windowId);
		return windowId;
	}
	
	private ShaderProgram initGl(){
		GLContext.createFromCurrent();
		Shader vertexShader = new Shader(GL20.GL_VERTEX_SHADER);
		vertexShader.init(readFile("shaders/vertex.shader"));
		vertexShader.compile();
		Shader fragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER);
		fragmentShader.init(readFile("shaders/fragment.shader"));
		fragmentShader.compile();
		ShaderProgram shaderProgram = new ShaderProgram();
		shaderProgram.attachShader(vertexShader);
		shaderProgram.attachShader(fragmentShader);
		shaderProgram.link();
		return shaderProgram;
	}
	
	private void loop(){
		Camera camera = new Camera();
		
		field = new TetrisField(program1, camera);
		
		Renderer renderer = new Renderer(program1);
		renderer.bindScene(field);
		
		while(glfwWindowShouldClose(windowId) == GL11.GL_FALSE){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(0.1f, 0.0f, 0.2f, 1.0f);
			renderer.render();
			GLFW.glfwSwapBuffers(windowId);
			GLFW.glfwPollEvents();
		}
	}
	
	public static String readFile(String fileName) throws RuntimeException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Beatris.class.getClassLoader().getResourceAsStream(fileName)));
		StringBuilder content = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String line = null;
		try{
			while(!((line = reader.readLine()) == null)){
				content.append(line);
				content.append(newLine);
			}
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
		return content.toString();
	}
	
	public static void main(String[] args) throws Exception {
		new Beatris();
	}
}

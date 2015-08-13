package de.skysoldier.beatris;

import java.util.ArrayList;
import java.util.Iterator;

import de.skysoldier.beatris.opengl.Camera;
import de.skysoldier.beatris.opengl.ShaderProgram;

public abstract class GameScene implements Iterable<GameObject>, KeyListener {
	
	private ShaderProgram program;
	private Camera camera;
	private ArrayList<GameObject> gameObjects;
	
	public GameScene(ShaderProgram program, Camera camera){
		this.program = program;
		program.use();
		this.camera = camera;
		gameObjects = new ArrayList<>();
	}
	
	public void addGameObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}
	
	public void addGameElement(GameElement element){
		ArrayList<? extends GameObject> objects = element.getGameObjects();
		for(GameObject object : objects){
			addGameObject(object);
		}
	}
	
	public void update(double delta){
	
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public ShaderProgram getShaderProgram(){
		return program;
	}
	
	@Override
	public Iterator<GameObject> iterator(){
		return gameObjects.iterator();
	}
	
	@Override
	public void keyHold(int key){
		
	}
	
	@Override
	public void keyPressed(int key){
		
	}
	
	@Override
	public void keyReleased(int key){
		
	}
}

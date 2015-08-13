package de.skysoldier.beatris;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class GameObject {
	
	private Vector2f position;
	private boolean dead;
	private RenderComponent2d renderComponent;
	
	public GameObject(Vector3f color){
		position = new Vector2f();
		renderComponent = initRenderComponent(color);
	}
	
	public void update(double deltaInSeconds){
		updateGameObject(deltaInSeconds);
		updateRenderComponent();
	}
	
	protected void updateGameObject(double deltaInSeconds){
		
	}
	
	private void updateRenderComponent(){
		renderComponent.setModelTranslation(position);
	}
	
	public void setPosition(float x, float y){
		position.x = x;
		position.y = y;
	}
	
	public void setDead(boolean dead){
		this.dead = dead;
	}
	
	public RenderComponent2d getRenderComponent(){
		return renderComponent;
	}
	
	public Vector2f getPosition(){
		return position;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public abstract RenderComponent2d initRenderComponent(Vector3f color);
}
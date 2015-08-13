package de.skysoldier.beatris;

import org.lwjgl.util.vector.Vector3f;

public class TetrisBlock extends GameObject {

	private int relativePositionX;
	private int relativePositionY;
	private int gridx;
	private int gridy;
	
	public TetrisBlock(int gridx, int gridy, int relativePositionX, int relativePositionY, Vector3f color){
		super(color);
		this.gridx = gridx;
		this.gridy = gridy;
		this.relativePositionX = relativePositionX;
		this.relativePositionY = relativePositionY;
	}
	
	public void setRelativePositionX(int relativePositionX){
		this.relativePositionX = relativePositionX;
	}
	
	public void setRelativePositionY(int relativePositionY){
		this.relativePositionY = relativePositionY;
	}
	
	public void setGridx(int gridx){
		this.gridx = gridx;
	}
	
	public void setGridy(int gridy){
		this.gridy = gridy;
	}
	
	public int getRelativePositionX(){
		return relativePositionX;
	}
	
	public int getRelativePositionY(){
		return relativePositionY;
	}
	
	@Override
	protected void updateGameObject(double deltaInSeconds){
		super.updateGameObject(deltaInSeconds);
		setPosition(gridx, gridy);
	}
	
	public int getGridx(){
		return gridx;
	}
	
	public int getGridy(){
		return gridy;
	}
	
	@Override
	public RenderComponent2d initRenderComponent(Vector3f color){
		float quadData[] = new float[]{
			-0.5f, -0.5f, 
			0.5f, -0.5f,
			-0.5f, 0.5f,
			0.5f, 0.5f
		};
		RenderComponent2d quad = new RenderComponent2d(color, quadData);
		return quad;
	}
}

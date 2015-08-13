package de.skysoldier.beatris;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector3f;

public class TetrisElement implements GameElement, Iterable<TetrisBlock> {
	
	private ArrayList<TetrisBlock> blocks;
	private int gridx, gridy;
	private long id;
	
	private static long idCounter = 0;
	
	public TetrisElement(int gridx, int gridy, TetrisElementType type){
		id = ++idCounter;
		this.gridx = gridx;
		this.gridy = gridy;
		blocks = new ArrayList<>();
		int relativeBlockPositions[][] = type.getBlockPositions();
		for(int relativePosition[] : relativeBlockPositions){
			Vector3f color = type.getColor();
			if(relativePosition[0] == 0 && relativePosition[1] == 0) color = new Vector3f(1.0f, 1.0f, 1.0f); //PIVOT BLOCK!
			TetrisBlock block = new TetrisBlock(gridx + relativePosition[0], gridy + relativePosition[1], relativePosition[0], relativePosition[1], color);
			blocks.add(block);
		}
	}
	
	public void setGridPositionX(int gridx){
		this.gridx = gridx;
	}
	
	public void setGridPositionY(int gridy){
		this.gridy = gridy;
	}
	
	public int getGridPositionX(){
		return gridx;
	}
	
	public int getGridPositionY(){
		return gridy;
	}
	
	public void updateBlockGridPositions(){
		for(TetrisBlock block : blocks){
			block.setGridx(gridx + block.getRelativePositionX());
			block.setGridy(gridy + block.getRelativePositionY());
		}
	}
	
	@Override
	public Iterator<TetrisBlock> iterator(){
		return blocks.iterator();
	}
	
	@Override
	public ArrayList<? extends GameObject> getGameObjects(){
		return blocks;
	}
	
	public long getId(){
		return id;
	}
	
	public enum TetrisElementType {

		LINE(new int[][]{
			{0, 0},
			{1, 0},
			{2, 0},
			{3, 0}
		}, new Vector3f(1.0f, 0.0f, 0.3f)),
		CUBE(new int[][]{
			{0, 0},
			{1, 0},
			{0, 1},
			{1, 1}
		}, new Vector3f(0.3f, 0.5f, 1.0f));
		
		private int relativeBlockPositions[][];
		private Vector3f color;
		
		private TetrisElementType(int relativeBlockPositions[][], Vector3f color){
			this.relativeBlockPositions = relativeBlockPositions;
			this.color = color;
		}
		
		public int[][] getBlockPositions(){
			return relativeBlockPositions;
		}
		
		public Vector3f getColor(){
			return color;
		}
	}
}

package de.skysoldier.beatris;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.glfw.GLFW;

import de.skysoldier.beatris.TetrisElement.TetrisElementType;
import de.skysoldier.beatris.opengl.Camera;
import de.skysoldier.beatris.opengl.ShaderProgram;

public class TetrisField extends GameScene {
	
	private long fieldData[][];
	private double counter;
	private ArrayList<TetrisElement> tetrisElements;
	
	private TetrisElement currentFallingElement;
	
	public TetrisField(ShaderProgram program, Camera camera){
		super(program, camera);
		fieldData = new long[8][5];
		tetrisElements = new ArrayList<>();
	}
	
	@Override
	public void addGameElement(GameElement element){
		super.addGameElement(element);
		tetrisElements.add((TetrisElement) element);
	}
	
	public void setFieldData(int gridx, int gridy, long data){
		fieldData[fieldData.length - gridy - 1][gridx] = data;
	}
	
	/**
	 * Modifys the field Data of the Tetris field according to 
	 * the block positions of the given element. 
	 * @param element the element used as the source for block data modification
	 * @param clear whether the data should be replaced with zeros
	 * @return if data was replaced or not (replacing = a field was not zero before modification)
	 */
	public boolean modifyFieldData(TetrisElement element, boolean clear){
		boolean dataWasReplaced = false;
		for(TetrisBlock block : element){
			long data = clear ? 0 : element.getId();
			long oldData = getFieldData(block.getGridx(), block.getGridy());
			dataWasReplaced = oldData != 0;
			setFieldData(block.getGridx(), block.getGridy(), data);
		}
		return dataWasReplaced;
	}
	
	public long getFieldData(int gridx, int gridy){
		return fieldData[fieldData.length - gridy - 1][gridx];
	}
	
	@Override
	public void update(double delta){
		super.update(delta);
		if(currentFallingElement == null){
			currentFallingElement = new TetrisElement(0, 6, TetrisElementType.CUBE);
			addGameElement(currentFallingElement);
			if(modifyFieldData(currentFallingElement, false)){
				System.out.println("game over!");
				System.exit(0);
			}
//			printFieldData();
		}
		counter += delta;
		if(counter >= 0.2){
			boolean stopCurrentFallingBlock = false;
			for(TetrisBlock block : currentFallingElement){
				if(block.getGridy() > 0){
					long dataUnderBlock = getFieldData(block.getGridx(), block.getGridy() - 1);
					if(dataUnderBlock > 0 && dataUnderBlock != currentFallingElement.getId()){
						stopCurrentFallingBlock = true;
						break;
					}
				}
			}
			if(!stopCurrentFallingBlock && currentFallingElement.getGridPositionY() > 0){
				modifyFieldData(currentFallingElement, true);
				currentFallingElement.setGridPositionY(currentFallingElement.getGridPositionY() - 1);
				currentFallingElement.updateBlockGridPositions();
				modifyFieldData(currentFallingElement, false);
				printFieldData(); 
			}
			else {
				currentFallingElement = null;
			}
			counter = 0;
		}
	}
	
	/** debug **/
	public void printFieldData(){
		System.out.println("-- field data --");
		for(long row[] : fieldData){
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
	}
	
	@Override
	public void keyPressed(int key){
		int x = currentFallingElement.getGridPositionX();
		if(key == GLFW.GLFW_KEY_LEFT){
			if(x > 0) currentFallingElement.setGridPositionX(x - 1);
		}
		else if(key == GLFW.GLFW_KEY_RIGHT){
			if(x < fieldData[0].length - 2) currentFallingElement.setGridPositionX(x + 1);
		}
	}
}

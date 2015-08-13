package de.skysoldier.beatris.opengl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class Camera {
	
	private FloatBuffer projectionData, cameraData;
	
	public Camera(){
		initOrthogonalProjection();
		cameraData = BufferUtils.createFloatBuffer(16);
		setTranslation(new Vector2f());
	}
	
	public void initOrthogonalProjection(){
		projectionData = BufferUtils.createFloatBuffer(16);
		float aspect = 800.0f / 600.0f;
		float top = 10.0f, right = 10.0f * aspect, bottom = -top, left = -right;
		float near = 0.1f, far = 100.0f;
		Matrix4f projection = new Matrix4f();
		projection.m00 = 2.0f / (right - left);
		projection.m03 = -(right + left) / (right - left);
		projection.m11 = 2.0f / (top - bottom);
		projection.m13 = - (top + bottom) / (top - bottom);
		projection.m22 = -2.0f / (far - near);
		projection.m23 = -(far + near) / (far - near);
		projection.store(projectionData);
		System.out.println(projection);
		projectionData.flip();
	}
	
	public void setTranslation(Vector2f translation){
		Matrix4f camera = new Matrix4f();
		camera.translate(translation);
		camera.store(cameraData);
		cameraData.flip();
	}
	
	public FloatBuffer getProjectionData(){
		return projectionData;
	}
	
	public FloatBuffer getCameraData(){
		return cameraData;
	}
}

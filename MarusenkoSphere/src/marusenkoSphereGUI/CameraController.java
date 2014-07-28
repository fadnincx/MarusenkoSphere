package marusenkoSphereGUI;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class CameraController {
	
	
	//Ersatz für (rtrix, rtriy, rtriz)
	private Vector3f rotation = new Vector3f(20,45,00);	
	
	public CameraController(){
	}
	public void lookThrough(){
		GL11.glTranslatef(0.0f,0.0f,-4.0f);
		
		GL11.glRotatef(rotation.x,1.0f,0.0f,0.0f);
		GL11.glRotatef(rotation.y,0.0f,1.0f,0.0f);
	    GL11.glRotatef(rotation.z,0.0f,0.0f,1.0f);
		
		
	}
	
	private void Vertical(double angle){
	/*	Matrix4f newRotWithOldRot = new Matrix4f();
		newRotWithOldRot.rotate((float)angle, new Vector3f(1,0,0));
		Matrix4f.mul(newRotWithOldRot, getModelView(), newRotWithOldRot);
		Vector3f rot = getRotation(newRotWithOldRot);
		Vector3f.add(rotation, rot, rotation);
		*/
		rotation.x+=angle;
	}
	
	private void Horizontal(double angle){
		rotation.y+=angle;
	}
	public void TurnUp(double angle){
		Vertical(angle*4);
	}
	public void TurnDown(double angle){
		Vertical(angle*4);
	}
	public void TurnLeft(double angle){
		Horizontal(angle*4);
	}
	public void TurnRight(double angle){
		Horizontal(angle*4);
	}
	public void SetToStartPosition(){
		rotation.x = 20;
		rotation.y = 45;
		rotation.z = 0;
	}
	
	
}

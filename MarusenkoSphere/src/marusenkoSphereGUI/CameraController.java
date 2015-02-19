package marusenkoSphereGUI;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
/**
 * CameraController-Klasse
 * 
 * Übernimmt die Drehungen der Kugel.
 *
 */
public class CameraController {
	
	//Vektor mit den 3 Winkeln um die Rotiert wird
	private final Vector3f rotation = new Vector3f(20,45,0);
	
	//Aktuelle Framerate
	private double fps = 60;
	
	//Variable ob letzter Aufruf Maus schon geklickt war
	private boolean click = false;
	
	//Vektor zu letzter Position
	private Vector3f startP = new Vector3f(0f,0f,0f);
	
	//Vektor zu aktueller Position
	private Vector3f nowP = new Vector3f(0f,0f,0f);
	
	//Quaternion zu letzter Position
	private Quaternion quatStart = new Quaternion(0f,0f,0f,1f);
	
	//Quaternion zu aktueller Position
	private Quaternion quat = new Quaternion(0f,0f,0f,1f);
	
	//FloatBuffer zu aktueller Ansicht
	private FloatBuffer fb = BufferUtils.createFloatBuffer(16);
	
	//FloatBuffer zu standardansicht
	private FloatBuffer firstfb = BufferUtils.createFloatBuffer(16);
	
	//Variable, ist das erster Aufruf (zum initialisieren der Variablen)
	private boolean first = true;
	
	//Variable, Kameraposition zurücksetzen
	private boolean reset = false;
	
	//Variable, mit Pfeiltasten Vertikaldrehen
	private double turnVerti = 0;
	
	//Veriable, mit Pfeiltasten Horziontaldrehen
	private double turnHoriz = 0;
	
	//Referenz Vektor x-Achse
	private final Vector3f xAchse = new Vector3f(1f,0f,0f);
	
	//Referenz Vektor y-Achse
	private final Vector3f yAchse = new Vector3f(0f,1f,0f);
	
	
	/**
	 * Funktion wird während des Renderprozesses aufgerufen um die Kugel entsprechend zu drehen
	 */
	protected void lookThrough(){
		
		//Verschiebe die Weltkoordinaten in Z-Richtung
		GL11.glTranslatef(0.0f,0.0f,-4.0f);
		
		//Rotiere die Kugel
		getRotation();
	
	}
	
	/**
	 * Funktion dreht die Kugel nun nutzt Quaternionen dazu
	 */
	private void getRotation(){
		
		//Das erste mal
		if(first){

			//Matrix für die X-Drehung wird erstellt und auf Einheitsmatrix gesetzt
			Matrix4f mx = new Matrix4f();
			mx.setIdentity();
			
			//Matrix für die Y-Drehung wird erstellt und auf Einheitsmatrix gesetzt
			Matrix4f my = new Matrix4f();
			my.setIdentity();
			
			//Gesammt Matrix wird erstellt und auf Einheitsmatrix gesetzt
			Matrix4f m = new Matrix4f();
			
			//Rotiere die X-Matrix
			mx.rotate((float) Math.toRadians(rotation.x),new Vector3f(1f,0f,0f));
			
			//Rotiere die Y-Matrix
			my.rotate((float) Math.toRadians(rotation.y),new Vector3f(0f,1f,0f));
			
			//Füge beide zusammen
			Matrix4f.mul(mx, my, m);
			
			//Setzte das Startquaternion auf Einheitsquaternion
			quatStart.setIdentity();
			
			//Setzte Matrix als Start Quaternion
			Quaternion.setFromMatrix(m,	quatStart);

			
			//Initialisiere den FloatBuffer fb neu
			fb = BufferUtils.createFloatBuffer(16);
			
			//Speichere die Matrix m im FloatBuffer und zurück zu 0
			m.store(fb);
			fb.flip();
			
			//Setzte firstfb
			firstfb = fb;
			
			//Wede den FloatBuffer auf die Szene an
			GL11.glMultMatrix(fb);
			
			//Setze First = false
			first = false;
			
		}else
			
		//Wenn Kameraposition zurück gesetzt wird	
		if(reset){
			
			//Setzet fb = firstfb
			fb = firstfb;
			
			//Erstelle Matrix
			Matrix4f m = new Matrix4f();
			
			//Fülle Matrix mit dem FloatBuffer und zurück zu 0
			m.load(firstfb);
			firstfb.flip();
			
			//Setze das StartQuaternion zurück auf Einheitsquaternion
			quatStart.setIdentity();
			
			//Setze Quaternion aus Matrix
			quatStart.setFromMatrix(m);
			
			//Wende den FloatBuffer auf die Szene an
			GL11.glMultMatrix(firstfb);
			
			//Setze reset = false
			reset = false;

		}else	
		//Wenn Maus gedrückt
		if(Mouse.isButtonDown(0)){
			
			
			
			//Wenn Click = true
			if(click){
				
				//Bekomme Mauskordinaten im 3D
				double[] mousePosIn3D = Editor.MouseIn3D(Mouse.getX(),Mouse.getY());
				
				//Speichere die Mauskoordinaten als Vector3f
				nowP =new Vector3f((float) mousePosIn3D[0],(float)mousePosIn3D[1],(float)mousePosIn3D[2]);

				//Normalisiere den Vektor
				nowP.normalise();
				startP.normalise();
				
				if(nowP!=startP){
					//Erstelle neuer Vektor Axis
					Vector3f axis= new Vector3f();
					
					//Vektor Axis ist das Kreuzprodukt aus startP und nowP
					Vector3f.cross(startP, nowP, axis);
					
					
					//Wenn axis eine Länge hat, dann normalisiere den Vektor
					if(axis.length()>0){
						axis.normalise();
						
						//Das Punktprodukt aus den Vektoren startP und nowP
						float dot = Vector3f.dot(startP, nowP);
						
						//Bekomme den Winkel und Multipliziere mit 2
						float angle = (float) Math.acos(dot);

						if(angle>0){
							//Erstelle neues Quaternion aus dem Vektor axis und dem Winkel angle
							Quaternion q_rot = new Quaternion(
									(float) (axis.x*Math.sin(angle)), 
									(float) (axis.y*Math.sin(angle)), 
									(float) (axis.z*Math.sin(angle)), 
									(float) (Math.cos(angle))
											);
							
							//Multipliziere das bereits bestehende Quaternion mit dem neuen Quaternion um die gesammt drehung zu erhalten
							Quaternion.mul(quatStart, q_rot, quat);
							
							//Setzte neues Start Quaternion
							quatStart = quat;
							
							
							
							//Erstelle Matrix rotation aus dem Quaternion quat
							Matrix4f rotation = QuaternionToMatrix4f(quat);
							
							//Initialisiere den FloatBuffer neu
							fb = BufferUtils.createFloatBuffer(16);
							
							//Speichere die Matrix rotation im FloatBuffer und zurück auf 0
							rotation.store(fb);
							fb.flip();
						}
						
					}
					
				}
				
				//Wende FloatBuffer auf die Szene an
				GL11.glMultMatrix(fb);
				
				//Setze die Aktuelle Position als Startposition
				startP = nowP;
			
			//Wenn Click = false
			}else{
				
				//Setze Click = true
				click = true;
				
				//Bekomme die 3D-koordinaten  
				double[] mousePosIn3D = Editor.MouseIn3D(Mouse.getX(),Mouse.getY());
				
				//Speichere die Koordinaten als Vector3f
				startP =new Vector3f((float) mousePosIn3D[0],(float)mousePosIn3D[1],(float)mousePosIn3D[2]);

				//Normalisiere den Vektor
				startP.normalise();
				
				//Wende bisherige Matrix auf die Szene an
				GL11.glMultMatrix(fb);
				
			}
		
		}else

		//Drehe gemäss Tastatur/Controlpanelinput, sofern benötigt	
		if(turnHoriz!=0||turnVerti!=0){
			
			//Wenn Vertikal gedreht werden muss
			if(turnVerti!=0){
				
				//Erstelle Quaternion mit der x-Achse und dem gewollten Drehwinkel
				Quaternion q_rot = new Quaternion(
						(float) (xAchse.x*Math.sin(Math.toRadians(turnVerti))), 
						(float) (xAchse.y*Math.sin(Math.toRadians(turnVerti))), 
						(float) (xAchse.z*Math.sin(Math.toRadians(turnVerti))), 
						(float) (Math.cos(Math.toRadians(turnVerti)))
								);
				
				//Multipliziere das bereits bestehende Quaternion mit dem neuen Quaternion um die gesammt drehung zu erhalten
				Quaternion.mul(quatStart, q_rot, quat);
				
				//Setzte neues Start Quaternion
				quatStart = quat;
				
				//Erstelle Matrix rotation aus dem Quaternion quat
				Matrix4f rotation = QuaternionToMatrix4f(quat);
				
				//Initialisiere den FloatBuffer neu
				fb = BufferUtils.createFloatBuffer(16);
				
				//Speichere die Matrix rotation im FloatBuffer und zurück auf 0
				rotation.store(fb);
				fb.flip();
				
				//Wende FloatBuffer auf die Szene an
				GL11.glMultMatrix(fb);
				
				//Setzet die gewollte dehung zurück
				turnVerti = 0;
				
			}else{
				
				//Erstelle Quaternion mit der y-Achse und dem gewollten Drehwinkel
				Quaternion q_rot = new Quaternion(
						(float) (yAchse.x*Math.sin(Math.toRadians(turnHoriz))), 
						(float) (yAchse.y*Math.sin(Math.toRadians(turnHoriz))), 
						(float) (yAchse.z*Math.sin(Math.toRadians(turnHoriz))), 
						(float) (Math.cos(Math.toRadians(turnHoriz)))
								);
				
				//Multipliziere das bereits bestehende Quaternion mit dem neuen Quaternion um die gesammt drehung zu erhalten
				Quaternion.mul(quatStart, q_rot, quat);
				
				//Setzte neues Start Quaternion
				quatStart = quat;
				
				//Erstelle Matrix rotation aus dem Quaternion quat
				Matrix4f rotation = QuaternionToMatrix4f(quat);
				
				//Initialisiere den FloatBuffer neu
				fb = BufferUtils.createFloatBuffer(16);
				
				//Speichere die Matrix rotation im FloatBuffer und zurück auf 0
				rotation.store(fb);
				fb.flip();
				
				//Wende FloatBuffer auf die Szene an
				GL11.glMultMatrix(fb);
				
				//Setzet die gewollte dehung zurück
				turnHoriz = 0;
				
			}
			
		//Wenn nichts an der Kamera verändert werden soll	
		}else{
			
			//Setzte Klick = false
			click = false;
			
			//Wende bisheriger FloatBuffer auf die Szene an
			GL11.glMultMatrix(fb);
			
		}
		
	}
	
	/**
	 * Wandelt ein Quaternion in eine 4x4 Matrix (Matrix4f) um
	 */
	private Matrix4f QuaternionToMatrix4f(Quaternion q){
		q.normalise();
		
		//Bekomme die 4 Werte des Quaternions
		float x = q.x;
		float y = q.y;
		float z = q.z;
		float w = q.w;
		
		//Bereche die verschiedenen Produkte
		float xx = x*x;
		float xy = x*y;
		float xz = x*z;
		float xw = x*w;
		
		float yy = y*y;
		float yz = y*z;
		float yw = y*w;
		
		float zz = z*z;
		float zw = z*w;
		
		//Erzeuge eine neue Matrix
		Matrix4f m = new Matrix4f();
		
		//Setze die Werte der Matrix
		m.m00 = 1 - 2 * ( yy + zz );
		m.m01 =     2 * ( xy - zw );
	    m.m02 =     2 * ( xz + yw );

	    m.m10 =     2 * ( xy + zw );
	    m.m11 = 1 - 2 * ( xx + zz );
	    m.m12 =     2 * ( yz - xw );

	    m.m20 =     2 * ( xz - yw );
	    m.m21 =     2 * ( yz + xw );
	    m.m22 = 1 - 2 * ( xx + yy );

	    m.m03 = m.m13 = m.m23 = m.m30 = m.m31 = m.m32 = 0;
	    m.m33 = 1;
		
	    //Gib die Matrix zurück
		return m;
		
	}

	/**
	 * Funktion welche aufgerufen wird, um den Drehwinkel zu ändern
	 */
	protected void turnRotationAngle(double angleX, double angleY){	
		
		//die aktuelle Bildfrequenz wird abgerufen
		getFPSVonManager();
		
		//FPS bereinigen
		angleX*=(60/fps);
		angleY*=(60/fps);
				
		//die Drehung wird umgesetzt
		turnVerti+=angleX;
		turnHoriz+=angleY;
		
		turnVerti%=360;
		turnHoriz%=360;
	}
	
	/**
	 * Setzte die Drehungen zurück in die Startposition
	 */
	protected void setToStartPosition(){
		reset = true;
	}
	
	/**
	 * Rufe die aktuelle Framerate vom Manager ab
	 */
	private void getFPSVonManager(){
		
		fps = Manager.getFPS();
		
	}	
	
}

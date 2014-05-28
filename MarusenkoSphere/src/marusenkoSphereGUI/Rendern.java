package marusenkoSphereGUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import marusenkoSphereKugel.Kugel;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;


/**
 * Rendern-Datei
 * 
 * Ist zum Darstellen der Kugel verantwortlich
 * 
 */

public class Rendern {
	/**
	 * Definiert den Titel des Fenster
	 */
	private final String windowTitle = "MarusenkoSphere";

	/**
	 * Definiert wie die Kugel gedreht dargestellt wird
	 */
    private float rtriy;                 
    private float rtrix;
    private float rtriz;

    
    /**
     * Definiert DisplayMode ==> Verantwortlich, wie Fenster mit Farbmodus etc. ist
     */
    private DisplayMode displayMode;
    
    /**
     * Definiert die Kugel, welche dargestellt wird    
     */
    protected Kugel k;

    protected int mode = 0;
    
    
    //fps
    
    long lastFrame;
    int fps;
    long lastFPS;

    /**
     * Konstruktor des Rendern-Objekts
     * 
     * Option kugel wird auch als aktuelle Kugel des Rendern-Objekts definiert
     */
    protected Rendern(Kugel kugel, int mode){
    	this.mode = mode;
    	this.k = kugel;
    	//Starte das Rendern
    	run();
    }
    
    /**
     * Funktion zum Updaten der Kugel
     */
    protected void updateKugel(Kugel kugel, int mode){
    	this.mode = mode;
    	this.k = kugel;
    	doing();
    }
    /**
     * Funktion welche versucht das Fenster zum Darstellen zu starten
     * 
     * Bei nicht Support Wird hier schon eine Exception geworfen und das Programm beendet
     * --> Tritt bekanntermassen nur auf, wenn Grafikkartentreiber nicht richtig installiert und mit JAR-Splice Zusatz-Option nicht hinzugef�gt wurde
     */
    protected void run() {
        try {
            init();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
       
    }
    
    /**
     * Funktion welche das Fenster updatet
     */
    protected void doing(){
    	switch(mode){
    	case 1:
    		//Editor
    		Editor.renderEditor(k, rtrix, rtriy, rtriz);
    		break;
    	default:
    		//Rendere die Kugel mit der externen Funktion
        	RenderKugel.render(k, rtrix, rtriy, rtriz); 
    		break;
    	}
    	updateFPS();
    	//Display.setTitle("FPS: " + fps);
    	
    	//Fixiere Frame rate auf 60fps (Kugel wird maximal 60 mal pro Sekunde neu gerendert)
    	Display.sync(60);
    	
    	//Update die Dartellung auf dem Display
        Display.update();
    }
    public long getTime() {
    	return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    public int getDelta() {
    	long time = getTime();
    	int delta = (int) (time - lastFrame);
    	lastFrame = time;
    	return delta;
    }
    public void updateFPS() {
    	if (getTime() - lastFPS > 1000) {
    		Display.setTitle("FPS: " + fps);
    		fps = 0;
    		lastFPS += 1000;
    	}
    	fps++;
    }
    /**
     * Beendet das Rendern und schliesst das Fenster
     */
    protected void end(){
    	cleanup();
    }
    
   

    
    protected static float[] getColorFloat(int n){
    	float[] color = new float[3];
    	switch(n){
    	case 0:
    		color[0] = 1.0f; color[1] = 1.0f; color[2] = 1.0f;
    		break;
    	case 1:
    		color[0] = 1.0f; color[1] = 1.0f; color[2] = 0.0f;
    		break;	
    	case 2:
    		color[0] = 1.0f; color[1] = 0.6f; color[2] = 0.0f;
    		break;
    	case 3:
    		color[0] = 0.0f; color[1] = 0.0f; color[2] = 1.0f;
    		break;	
    	case 4:
    		color[0] = 1.0f; color[1] = 0.0f; color[2] = 0.0f; 
    		break;
    	case 5:
    		color[0] = 0.0f; color[1] = 1.0f; color[2] = 1.0f;
    		break;	
    	case 6:
    		color[0] = 1.0f; color[1] = 0.0f; color[2] = 1.0f;
    		break;
    	case 7:
    		color[0] = 0.0f; color[1] = 1.0f; color[2] = 0.0f;    
    		break;	
    	case 8:
    		color[0] = 0.0f; color[1] = 0.0f; color[2] = 0.0f;  
    	}
    	return color;
    }
    /**
     * Funktion welche die Farbe setzt, mit welcher die Dreiecke gerendert werden 
     * @param n : Farbcode
     */
    protected static void setColor(int n){
    	float[] color = getColorFloat(n);
    	GL11.glColor4f(color[0],color[1],color[2], 1.0f);
    }
    protected static float getColorFloat(int n,int a){
    	float[] color = getColorFloat(n);
    	return color[a];
    }
    /**
     * Funktion zum erstellen des Fensters
     */
    private void createWindow() throws Exception {
    	try{
    	/**
    	 * Suche nach allen verfügbaren Moden mit welchen das Fenster dargestellt werden kann
    	 */
    		
        DisplayMode d[] = Display.getAvailableDisplayModes();
        
        /**
         * Suche den richtigen Modus richtige heraus        
         */
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640
                && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        if(displayMode == null){
        	for (int i = 0; i < d.length; i++) {
                if (d[i].getWidth() == 640
                    && d[i].getHeight() == 480
                    && d[i].getBitsPerPixel() == 24) {
                    displayMode = d[i];
                    break;
                }
            }
        }
        if(displayMode == null){
        	for (int i = 0; i < d.length; i++) {
                if (d[i].getWidth() == 640
                    && d[i].getHeight() == 480
                    && d[i].getBitsPerPixel() == 16) {
                    displayMode = d[i];
                    break;
                }
            }
        }
        
        

        /**
         * Versuche das Icon für das Fenster zu laden
         */
        try {
        	/**
        	 * Erstelle ein ByteBuffer array für alle verfügbaren icons
        	 */
            ByteBuffer[] icons = new ByteBuffer[1];
            
            /**
             * Defniniere welche Icons geladen werden, inklusive der gr�sse
             */
            icons[0] = loadIcon("/img/icon_16.png", 16, 16);
            //icons[1] = loadIcon("/img/icon_32.png", 32, 32);
            //icons[2] = loadIcon("/img/icon_64.png", 64, 64);
            //icons[3] = loadIcon("/img/icon_128.png", 128, 128);
            
            /**
             * Setzte die Icons           
             */
            Display.setIcon(icons);
            
            
        /**
         * Werfe eine Exception, wenn ein Fehler dabei passiert!    
         */
        } catch (IOException e) {
           e.printStackTrace();
        }

        /**
         * Setzte den DisplayMode und den Fenstertitel
         */
        
        
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        
        /**
         * Erstelle Fenster mit den voreingestellten Einstellungen
         */
        try{
        	Display.create(new PixelFormat(0, 8, 0, 4));
        	//Display.create();
        }catch(LWJGLException ex){
        	System.out.println("Kein Antialiasing M�glich!");
        	Display.create();
        }
        Display.setVSyncEnabled(true);
    	}catch(LWJGLException e){
    		e.printStackTrace();
    		System.exit(0);
    	}
    	getDelta();
    	lastFPS = getTime();
    }
    
    /**
     * Funktion, welche für das laden des Icons und umwandeln zu einem Bytebuffer verantwortlich ist
     * 
     * 
     * @param filename : gibt den Dateinamen an, welche Datei geladen wird
     * @param width : breite der Datei, welche geladen werden wird
     * @param height : höhe der Datei, welche geladen werden wird
     * @return : gibt den ByteBuffer zur�ck
     */
    private ByteBuffer loadIcon(String filename, int width, int height) throws IOException {
    	
    	/**
    	 * Erstelle ein Buffered Image aus der Datei
    	 */
    	BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(filename));
    	
        /**
         * Wandle das Bild in ein ByteArray um
         * 
         * erstelle ein Array welches genügend gross für die voreingestellte grösse des Bildes ist
         * breite*höhe*4 (die 4 ist f�r rot, grün, blau und alpha)
         * 
         */
    
        byte[] imageBytes = new byte[width * height * 4];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            	/**
            	 * Frage den Pixel an entsprechende Position ab um es anschliessend in das Array zu schreiben
            	 * 
            	 */
                int pixel = image.getRGB(j, i);
                
                /**
                 * Rot, Grün und Blau in das Array schreiben 
                 */
                for (int k = 0; k < 3; k++){
                    imageBytes[(i*16+j)*4 + k] = (byte)(((pixel>>(2-k)*8))&255);
                }
                
                /**
                 * Alpha (transparenz) in das Array schreiben
                 */
                imageBytes[(i*16+j)*4 + 3] = (byte)(((pixel>>(3)*8))&255);
            }
        }
        
        /**
         * Wandle das Byte Arre in ein ByteBuffer um und gebe ihn zurück
         */
        return ByteBuffer.wrap(imageBytes);
    }
    
    /**
     * Funktion zum Aufrufen der Initialisierungsfunktionen des Fensters und OpenGL
     */
    private void init() throws Exception {
        createWindow();
        initGL();
    }

    /**
     * Funktion, welche OpenGL initialsiert
     */
    private void initGL() {
    	
    	//Smooth ShadeModel aktivieren
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
        //Bestimmt verhalten mit Transparenz
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        //Definiere den Hintergrund auf ein helles Grau
        GL11.glClearColor(0.8f, 0.8f, 0.8f, 0.0f);
       
        /**
         * Definiert wie viel beim ClearDepth durchgeführt wurde
         * und Aktiviere den DepthTest
         * sowie welchert Type von DepthTest durchgeführt wird
         */
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        /**
         * Definie die Projektions Matrix
         * sowie das zurücksetzen dieser Matrix
         */
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
     
        
        //Die Perspektive Berechnen mit hilfe von GLU
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          100.0f);
        
        //Den Matrixmodus wählen
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        //Verbessere die Berechnungen der Perspektive
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
       //Definiere die Rotation der Kugel zu begin auf 0.0 in allen Achsen (x, y und z)
        rtrix=0.0f;
        rtriy=0.0f; 
        rtriz=0.0f;

    }
    
    /**
     * Funktion, welche die Variabeln ändern, um wie viel die Kugel gedreht wird 
     */
    protected void drehen(float x, float y, float z){
    	rtrix += x*40;
    	rtriy += y*40;
    	rtriz += z*40;
    }
    
    /**
     * Funktion, welche die Variabeln setzt, auf wie viel die Kugel gedret ist 
     */
    protected void setDrehen(float x, float y, float z){
    	rtrix = x;
    	rtriy = y;
    	rtriz = z;
    }
   
    
    /**
     * Funktion welche beim Beenden Aufgerufen wird, ist relativ wichtig, damit Speicher wieder freigegeben wird 
     */
    private static void cleanup() {
        Display.destroy();
        System.exit(0);
    }
}
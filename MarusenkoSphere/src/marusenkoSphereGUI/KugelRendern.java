package marusenkoSphereGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import marusenkoSphereKugel.Kugel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;



/**
 * KugelRendern-Datei
 * 
 * Ist zum Darstellen der Kugel verantwortlich
 * 
 */

public class KugelRendern {
	/**
	 * Definiert den Titel des Fenster
	 */
	private final String windowTitle = "MarusenkoSphere";

	/**
	 * Definiert wie die Kugel gedreht dargestellt wird
	 */
    private float rtriy;                 // Angle For The Triangle ( NEW )
    private float rtrix;                 // Angle For The Triangle ( NEW )
    private float rtriz;

    
    /**
     * Definiert DisplayMode ==> Verantwortlich, wie Fenster mit Farbmodus etc. ist
     */
    private DisplayMode displayMode;
    
    /**
     * Definiert die Kugel, welche dargestellt wird    
     */
    protected Kugel k;

    /**
     * Funktion zum Initialisieren der Kugel
     * 
     * Option kugel wird auch als aktuelle Kugel des Objektes definiert
     */
    public KugelRendern(Kugel kugel){
    	this.k = kugel;
    	run();
    }
    
    /**
     * Funktion zum Updaten der Kugel
     */
    public void updateKugel(Kugel kugel){
    	this.k = kugel;
    	doing();
    }
    /**
     * Funktion welche versucht das Fenster zum Darstelltn zu starten
     */
    public void run() {

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
    public void doing(){
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    	KugelR.render20(k, rtrix, rtriy, rtriz); 
    	Display.sync(60); // cap fps to 60fps
        Display.update();
    }
    
    /**
     * Beendet das Rendern und schliesst das Fenster
     */
    public void end(){
    	cleanup();
    }
    
   

    /**
     * Funktion welche die Farbe setzt, mit welcher die Dreiecke gerendert werden 
     * @param n : Farbcode
     */
    protected static void setColor(int n){
    	if(n == 0){
    		GL11.glColor4f(1.0f,1.0f,1.0f, 1.0f);
    	}else
    	if(n == 1){
    		GL11.glColor4f(1.0f,1.0f,0.0f, 1.0f);
        }else
        if(n == 2){
        	GL11.glColor4f(1.0f,0.6f,0.0f, 1.0f);	
        }else
        if(n == 3){
        	GL11.glColor4f(0.0f,0.0f,1.0f, 1.0f);    		
        }else
        if(n == 4){
        	GL11.glColor4f(1.0f,0.0f,0.0f, 1.0f);        		
        }else
        if(n == 5){
        	GL11.glColor4f(0.0f,1.0f,1.0f, 1.0f);            		
        }else
        if(n == 6){
        	GL11.glColor4f(1.0f,0.0f,1.0f, 1.0f);                		
        }else
        if(n == 7){
        	GL11.glColor4f(0.0f,1.0f,0.0f, 1.0f);                      		
        }
    }
    
    /**
     * Hauptfunktion welche die Kugel rendert
     * @return : gibt zur�ck ob rendern erfolgreich war
     */
   
    
    /**
     * Funktion zum erstellen des Fensters
     */
    private void createWindow() throws Exception {
    	try{
    	/**
    	 * Suche nach allen verf�gbaren Moden mit welchen das Fenster dargestellt werden kann
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
           // System.out.println(i+". ==> "+d[i].getWidth()+" "+d[i].getHeight()+" "+d[i].getBitsPerPixel());
        }
        if(displayMode == null){
        	for (int i = 0; i < d.length; i++) {
                if (d[i].getWidth() == 640
                    && d[i].getHeight() == 480
                    && d[i].getBitsPerPixel() == 24) {
                    displayMode = d[i];
                    break;
                }
               // System.out.println(i+". ==> "+d[i].getWidth()+" "+d[i].getHeight()+" "+d[i].getBitsPerPixel());
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
              //  System.out.println(i+". ==> "+d[i].getWidth()+" "+d[i].getHeight()+" "+d[i].getBitsPerPixel());
            }
        }
        
        

        /**
         * Versuche das Icon f�r das Fenster zu laden
         */
        try {
        	/**
        	 * Erstelle ein ByteBuffer array f�r alle verf�gbaren icons
        	 */
            ByteBuffer[] icons = new ByteBuffer[1];
            
            /**
             * Defniniere welche Icons geladen werden, inklusive der gr�sse
             */
            icons[0] = loadIcon("img/icon_16.png", 16, 16);
            //icons[1] = loadIcon("img/icon_32.png", 32, 32);
            //icons[0] = loadIcon("img/icon_64.png", 64, 64);
            //icons[0] = loadIcon("img/icon_128.png", 128, 128);
            
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
        Display.create();
        Display.setVSyncEnabled(true);
    	}catch(LWJGLException e){
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
    
    /**
     * Funktion, welche f�r das laden des Icons und umwandeln zu einem Bytebuffer verantwortlich ist
     * 
     * 
     * @param filename : gibt den Dateinamen an, welche Datei geladen wird
     * @param width : breite der Datei, welche geladen werden wird
     * @param height : h�he der Datei, welche geladen werden wird
     * @return : gibt den ByteBuffer zur�ck
     */
    private ByteBuffer loadIcon(String filename, int width, int height) throws IOException {
    	
    	/**
    	 * Erstelle ein Buffered Image aus der Datei
    	 */
    	BufferedImage image = ImageIO.read(new File(filename));

        // convert image to byte array
        
        /**
         * Wandle das Bild in ein ByteArray um
         * 
         * erstelle ein Array welches gen�gend gross f�r die voreingestellte gr�sse des Bildes ist
         * breite*h�he*4 (die 4 ist f�r rot, gr�n, blau und alpha)
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
                 * Rot, Gr�n und Blau in das Array schreiben 
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
         * Wandle das Byte Arre in ein ByteBuffer um und gebe ihn zur�ck
         */
        return ByteBuffer.wrap(imageBytes);
    }
    
    /**
     * Funktion zum Initialisieren dieses Objekts
     */
    private void init() throws Exception {
        createWindow();
        initGL();
    }

    /**
     * Funktion, welche OpenGL initialsiert
     */
    private void initGL() {
    	
    	/**
    	 * 
    	 */
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
    	/**
    	 * Smooth ShadeModel aktivieren
    	 */
        GL11.glShadeModel(GL11.GL_SMOOTH);

        /**
         * Bestimmt verhalten mit Transparenz
         */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        /**
         * Definiere den Hintergrund auf ein helles Grau
         */
        GL11.glClearColor(0.8f, 0.8f, 0.8f, 0.0f);
        
        /**
         * Definiert wie viel beim ClearDepth durchgef�hrt wurde
         * und Aktiviere den DepthTest
         * sowie welchert Type von DepthTest durchgef�hrt wird
         */
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        /**
         * Definie die Projektions Matrix
         * sowie das zur�cksetzen dieser Matrix
         */
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
     
        
        /**
         * Die Perspektive Berechnen mit hilfe von GLU
         */
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          100.0f);
        
        /**
         * Den Matrixmodus w�hlen
         */
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        /**
         * Verbessere die Berechnungen der Perspektive
         */
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
        /**
         * Definiere die Rotation der Kugel zu begin auf 0.0 in beiden Achsen (x und y)
         */
        rtriy=0.0f; 
        rtrix=0.0f;

    }
    public void turnUp(double turn){
    	/*double aktx = rtrix;
    	double akty = rtriy;
    	double aktz = rtriz;*/
    	
    	rtrix += turn*40*Math.cos(rtriy/180*Math.PI);
    	rtriz += turn*40*Math.sin(rtriy/180*Math.PI);
    	
    }
    /**
     * Funktion, welche die Variabeln �ndern, um wie viel die Kugel gedreht wird 
     */
    public void drehen(float f, float x){
    	rtriy += f*40;
    	rtrix += x*40;
    }
    /**
     * Funktion, welche die Variabeln �ndern, um wie viel die Kugel gedreht wird 
     */
    public void setDrehen(float f, float x, float z){
    	rtriy = f;
    	rtrix = x;
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

package marusenkoSphereGUI;

import java.awt.Canvas;
import java.awt.Color;

import marusenkoSphere.Settings;
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
    
	//Den CameraController für die Kameradrehungen
    protected CameraController cm = new CameraController();

    //Definiert DisplayMode ==> Verantwortlich, wie Fenster mit Farbmodus etc. ist
    private DisplayMode displayMode;
    
    //Manager
    private Manager m;
    
    //Canvas für die Kugel
    private Canvas canvas = new Canvas();
    
    //Definiert die Kugel, welche dargestellt wird    
    protected Kugel k;

    //In welchem Modus gerendert wird
    private int renderMode = 0;
    
    //Variabeln für die FPS Berechnungen
    private int fps;
    private long lastFPS;
    
    //Pfeildarstellung
    private int pfeilID = -1;
    //private int x,y;

    /**
     * Konstruktor des Rendern-Objekts
     * 
     * Option kugel wird auch als aktuelle Kugel des Rendern-Objekts definiert
     */
    protected Rendern(Kugel kugel, Manager m){
    	
    	//Setze Manager
    	this.m = m;
    	
    	//Setze die Kugel als eigene
    	k = kugel;
    	
    	//Starte das Rendern
    	run();
    	
    }
    
    /**
     * Funktion zum Updaten der Kugel
     * @param active 
     */
    protected void updateKugel(Kugel kugel, int mode, int pfeilID){
    	
    	renderMode = mode;
    	k = kugel;
    	this.pfeilID = pfeilID;
    	doing();
    	
    }
    
    /**
     * Funktion welche versucht das Fenster zum Darstellen zu starten
     * 
     * Bei nicht Support Wird hier schon eine Exception geworfen und das Programm beendet
     * --> Tritt bekanntermassen nur auf, wenn Grafikkartentreiber nicht richtig installiert und mit JAR-Splice Zusatz-Option nicht hinzugefügt wurde
     */
    private void run() {
    	
        try {
        	
        	init();
        	
        }catch (Exception e) {
        	
            e.printStackTrace();
            System.exit(0);
        
        }
        
    }
    
    /**
     * Funktion welche das Fenster updatet
     */
    private void doing(){
    	
    	switch(renderMode){
    	case 1:
    		
    		//2D Editor rendern
    		Editor.renderEditor(k);
    		break;
    		
    	default:
    		
    		//Rendere die Kugel mit der externen Funktion
        	RenderKugel.render(k,cm, pfeilID); 
    		break;
    		
    	}
    	
    	//Die Framerate aktuallisieren
    	updateFPS();
    	
    	//Fixiere Frame rate auf 60fps (Kugel wird maximal 60 mal pro Sekunde neu gerendert)
    	Display.sync(60);
    	
    	//Update die Darstellung auf dem Display
        Display.update();
        
    }
    
    /**
     * Gibt die aktuelle Zeit in Millisekunden zurück
     */
    private long getTime() {
    	
    	return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    	
    }

    
    /**
     * Updated die Framerate
     * Updatet FPS im Manager für die Berechnung der Animationsgeschwindigkeit
     */
    private void updateFPS() {
    	
    	//Wenn eine Sekunde vergangen
    	if (getTime() - lastFPS > 1000) {
    		
    		//Wenn der Globale Debugmodus eingeschalten ist, dann im Titel die Framerate anzeigen
    		if(Settings.DEBUGMODE){
    			
    			m.mainFrame.setTitle(Settings.TITEL+" - "+fps);
    			
    		}
    		
    		//Setze die Framerate im Manager
    		Manager.setFPS(fps);
    		
    		//FPS zurück setzten
    		fps = 0;
    		lastFPS += 1000;
    		
    	}
    	
    	fps++;
    	
    }

    /**
     * Gibt die Farbe n als float-Array zurück
     */
    protected static float[] getColorFloat(int n){
    	
    	switch(n){
    	
	    	case 0: return new float[] {1.0f,1.0f,1.0f};//Weiss
	    	case 1: return new float[] {1.0f,1.0f,0.0f};//Gelb
	    	case 2: return new float[] {1.0f,0.6f,0.0f};//Orange
	    	case 3: return new float[] {0.0f,0.0f,1.0f};//Blau	
	    	case 4: return new float[] {1.0f,0.0f,0.0f};//Rot
	    	case 5: return new float[] {0.0f,1.0f,1.0f};//Türkis
	    	case 6: return new float[] {1.0f,0.0f,1.0f};//Violett
	    	case 7: return new float[] {0.0f,1.0f,0.0f};//Grün	
	    	case 8: return new float[] {0.0f,0.0f,0.0f};//Schwarz
	    	default:return new float[] {0.0f,0.0f,0.0f};//Schwarz
	    	
    	}
    	
    }
    
    /**
     * Funktion welche die Farbe für OpenGL setzt, mit welcher die Dreiecke gerendert werden 
     * @param n : Farbcode
     */
    protected static void setColor(int n){
    	
    	float[] color = getColorFloat(n);
    	GL11.glColor4f(color[0],color[1],color[2], 1.0f);
    	
    }
    
    /**
     * Funktion gibt eine AWT-Color zurück für das Editor-Controlpanel
     */
    protected static Color getColorColor(int n){
    	
    	float[] color = getColorFloat(n);
    	return new Color(color[0],color[1],color[2], 1.0f);
    	
    }
    
    
    /**
     * Funktion zum erstellen des Fensters
     */
    private void createWindow() throws Exception {
    	
    	try{
    		
	    	//Suche nach allen verfügbaren Modien mit welchen das Fenster
	    	//dargestellt werden kann
	    		
	        DisplayMode d[] = Display.getAvailableDisplayModes();
	        
	        //Suche den richtigen Modus richtige heraus    
	        
	        //Bevorzuge 32Bit farbtiefe
	        for (int i = 0; i < d.length; i++) {
	        	
	            if (d[i].getWidth() == 640
	                && d[i].getHeight() == 480
	                && d[i].getBitsPerPixel() == 32) {
	            	
	                displayMode = d[i];
	                break;
	                
	            }
	            
	        }
	        
	        //Wenn 32Bit nicht verfügbar, dann 24Bit nehmen
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
	        
	        //Für ganz Schlechte Bildschirme noch 16Bit
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

	        //Setzte den DisplayMode und den Fenstertitel       
	        Display.setDisplayMode(displayMode);
	
	        //Setzet Grösse und Position des Cancas
	        canvas.setBounds(0, 20, 640, 480);
	        
	        //Füge die Zeichnung dem Hauptfenster hinzu
	        m.mainFrame.add(canvas);

			//Neu zeichnen Ignorieren
			canvas.setIgnoreRepaint(true);
			
			//Canvas sichtbar machen
	        canvas.setVisible(true);
	        
	        //Canvas als LWJGL Parent setzen
	        Display.setParent(canvas);
       
	        //Erstelle Fenster mit den voreingestellten Einstellungen
	        try{
	        	
	        	//Versuche es mit Antialiasing
	        	Display.create(new PixelFormat(0, 8, 0, 4));
	        	
	        }catch(LWJGLException ex){
	        	
	        	//Wennn nicht möglich, dann gibt die Meldung aus und starte ohne
	        	System.out.println("Kein Antialiasing Möglich!");
	        	Display.create();
	        	
	        }   
	        
	        //VSync aktivieren
	        Display.setVSyncEnabled(true);
	        
    	}catch(LWJGLException e){
    		
    		//Wenn Fehler beim Initialisieren des Fensters passiert, dann sofort Programm beenden
    		e.printStackTrace();
    		System.exit(0);
    		
    	}
    	
    	lastFPS = getTime();
    	
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
       
        
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
     
        //Setzte aktive Matrix auf Projection und lade sie
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
     
        //Die Perspektive Berechnen mit Hilfe von GLU
        GLU.gluPerspective(45.0f,((float)displayMode.getWidth() /(float) displayMode.getHeight()),0.1f,100.0f);
        
        //Setze aktive Matrix auf Modelview
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        //Verbessere die Berechnungen der Perspektive
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        
    }
    
}

package marusenkoSphereRender;

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
    	render(); 
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
     * Funktion, welche die Variabeln ändern, um wie viel die Kugel gedreht wird 
     */
    public void drehen(float f, float x){
    	rtriy += f*40;
    	rtrix += x*40;
    }
    /**
     * Funktion, welche die Variabeln ändern, um wie viel die Kugel gedreht wird 
     */
    public void setDrehen(float f, float x){
    	rtriy = f;
    	rtrix = x;
    }

    /**
     * Funktion welche die Farbe setzt, mit welcher die Dreiecke gerendert werden 
     * @param n : Farbcode
     */
    private void setColor(int n){
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
     * @return : gibt zurück ob rendern erfolgreich war
     */
    private boolean render() {
    	/**
    	 * Auskommentieren, damit Flächen der Kugel nicht gefüllt werden
    	 * 
    	 * Eigentlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
    	/**
    	 * Löscht den gesammten Bereich, damit neu gerendert werden kann (inklusive Depth Buffer)
    	 * 
    	 */
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        /**
         * Setze die Position zum Rendern zurück 
         */
        GL11.glLoadIdentity();
        
      
        
        /**
         * verschiebe das 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
         */
        GL11.glTranslatef(0.0f,0.0f,-4.0f);
        
        /**
         * Drehe die Kugel gemäss den Variabeln auf x bzw y Achse
         */
        GL11.glRotatef(rtriy,0.0f,1.0f,0.0f);
        GL11.glRotatef(rtrix,1.0f,0.0f,0.0f);
        
 /**
         * Starte das Zeichnen der einzelnen flächen der Kugel
         */
        GL11.glBegin(GL11.GL_TRIANGLES);
        	  
        
        
        /**
         * 
         * Zeichne die Pole
         * 
         */
        
        /**
         * Zeichne Pol Nr 0 bis 3
         */
        	/**
        	 * Wird für jeden der 4 Pole erneut durch gegangen
        	 */
        	for(int i = 0; i<4; i++){
        		/**
        		 * Setze die Negationsvariabelen positiv
        		 */
        		int vx = 1, vy = 1; 
        		
        		/**
        		 * Ändere die Negationsvariabeln gemäss Pol und setzte die Farbe, mit welcher Gerendert wird
        		 */
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[0]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[1]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[2]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[3]);	
                }
 
        		/**
        		 * Rendere die 4Segmente eines Poles
        		 */
        		
        		GL11.glVertex3f( vx*0.00f, vy*0.00f, 1.00f);
            	GL11.glVertex3f( vx*0.00f, vy*0.50f, 0.65f);
            	GL11.glVertex3f( vx*0.19f, vy*0.46f, 0.65f);
            	
            	GL11.glVertex3f( vx*0.00f, vy*0.00f, 1.00f);
            	GL11.glVertex3f( vx*0.19f, vy*0.46f, 0.65f);
            	GL11.glVertex3f( vx*0.35f, vy*0.35f, 0.65f);
            	
            	GL11.glVertex3f( vx*0.00f, vy*0.00f, 1.00f);
            	GL11.glVertex3f( vx*0.35f, vy*0.35f, 0.65f);
            	GL11.glVertex3f( vx*0.46f, vy*0.19f, 0.65f);
            	
            	GL11.glVertex3f( vx*0.00f, vy*0.00f, 1.00f);
            	GL11.glVertex3f( vx*0.46f, vy*0.19f, 0.65f);
            	GL11.glVertex3f( vx*0.50f, vy*0.00f, 0.65f);
        	}
        	
        	  
        	/**
             * Zeichne Pol Nr 4 bis 7
             * 
             * genaueres siehe 0 bis 3
             */
        	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[4]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[5]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[6]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[7]);	
                } 	
        		GL11.glVertex3f( 1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( 0.65f, vy*0.50f, vx*0.00f);
            	GL11.glVertex3f( 0.65f, vy*0.46f, vx*0.19f);
            	                                 
            	GL11.glVertex3f( 1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( 0.65f, vy*0.46f, vx*0.19f);
            	GL11.glVertex3f( 0.65f, vy*0.35f, vx*0.35f);
            	                                 
            	GL11.glVertex3f( 1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( 0.65f, vy*0.35f, vx*0.35f);
            	GL11.glVertex3f( 0.65f, vy*0.19f, vx*0.46f);
            	                                 
            	GL11.glVertex3f( 1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( 0.65f, vy*0.19f, vx*0.46f);
            	GL11.glVertex3f( 0.65f, vy*0.00f, vx*0.50f);
        	}
        	
        	/**
             * Zeichne Pol Nr 8 bis 11
             * 
             * genaueres siehe 0 bis 3
             */
        	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[8]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[9]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[10]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[11]);	
                } 	
        		GL11.glVertex3f( vx*0.00f, vy*0.00f, -1.00f);
            	GL11.glVertex3f( vx*0.00f, vy*0.50f, -0.65f);
            	GL11.glVertex3f( vx*0.19f, vy*0.46f, -0.65f);
            	
            	GL11.glVertex3f( vx*0.00f, vy*0.00f, -1.00f);
            	GL11.glVertex3f( vx*0.19f, vy*0.46f, -0.65f);
            	GL11.glVertex3f( vx*0.35f, vy*0.35f, -0.65f);
            	
            	GL11.glVertex3f( vx*0.00f, vy*0.00f, -1.00f);
            	GL11.glVertex3f( vx*0.35f, vy*0.35f, -0.65f);
            	GL11.glVertex3f( vx*0.46f, vy*0.19f, -0.65f);
            	
            	GL11.glVertex3f( vx*0.00f, vy*0.00f, -1.00f);
            	GL11.glVertex3f( vx*0.46f, vy*0.19f, -0.65f);
            	GL11.glVertex3f( vx*0.50f, vy*0.00f, -0.65f);
        	}	
        	
        	/**
             * Zeichne Pol Nr 12 bis 15
             * 
             * genaueres siehe 0 bis 3
             */
        	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[12]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[13]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[14]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[15]);	
                } 	
        		GL11.glVertex3f( -1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( -0.65f, vy*0.50f, vx*0.00f);
            	GL11.glVertex3f( -0.65f, vy*0.46f, vx*0.19f);
            	                                 
            	GL11.glVertex3f( -1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( -0.65f, vy*0.46f, vx*0.19f);
            	GL11.glVertex3f( -0.65f, vy*0.35f, vx*0.35f);
            	                                 
            	GL11.glVertex3f( -1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( -0.65f, vy*0.35f, vx*0.35f);
            	GL11.glVertex3f( -0.65f, vy*0.19f, vx*0.46f);
            	                                 
            	GL11.glVertex3f( -1.00f, vy*0.00f, vx*0.00f);
            	GL11.glVertex3f( -0.65f, vy*0.19f, vx*0.46f);
            	GL11.glVertex3f( -0.65f, vy*0.00f, vx*0.50f);
        	}
        	
        
        	/**
             * Zeichne Pol Nr 16 bis 19
             * 
             * genaueres siehe 0 bis 3
             */   	
        	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[16]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[17]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[18]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[19]);	
                }
 
        		GL11.glVertex3f( vx*0.00f, 1.00f, vy*0.00f);
            	GL11.glVertex3f( vx*0.00f, 0.65f, vy*0.50f);
            	GL11.glVertex3f( vx*0.19f, 0.65f, vy*0.46f);
            	                         
            	GL11.glVertex3f( vx*0.00f, 1.00f, vy*0.00f);
            	GL11.glVertex3f( vx*0.19f, 0.65f, vy*0.46f);
            	GL11.glVertex3f( vx*0.35f, 0.65f, vy*0.35f);
            	                         
            	GL11.glVertex3f( vx*0.00f, 1.00f, vy*0.00f);
            	GL11.glVertex3f( vx*0.35f, 0.65f, vy*0.35f);
            	GL11.glVertex3f( vx*0.46f, 0.65f, vy*0.19f);
            	                         
            	GL11.glVertex3f( vx*0.00f, 1.00f, vy*0.00f);
            	GL11.glVertex3f( vx*0.46f, 0.65f, vy*0.19f);
            	GL11.glVertex3f( vx*0.50f, 0.65f, vy*0.00f);
        	}	
        
        	/**
             * Zeichne Pol Nr 20 bis 23
             * 
             * genaueres siehe 0 bis 3
             */	
        	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[20]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[21]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[22]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[23]);	
                } 	
        		GL11.glVertex3f( vx*0.00f, -1.00f, vy*0.00f);
        		GL11.glVertex3f( vx*0.00f, -0.65f, vy*0.50f);
	        	GL11.glVertex3f( vx*0.19f, -0.65f, vy*0.46f);
	        	                         
	        	GL11.glVertex3f( vx*0.00f, -1.00f, vy*0.00f);
	        	GL11.glVertex3f( vx*0.19f, -0.65f, vy*0.46f);
	        	GL11.glVertex3f( vx*0.35f, -0.65f, vy*0.35f);
        	
	        	GL11.glVertex3f( vx*0.00f, -1.00f, vy*0.00f);
	        	GL11.glVertex3f( vx*0.35f, -0.65f, vy*0.35f);
	        	GL11.glVertex3f( vx*0.46f, -0.65f, vy*0.19f);
        	                         
	        	GL11.glVertex3f( vx*0.00f, -1.00f, vy*0.00f);
	        	GL11.glVertex3f( vx*0.46f, -0.65f, vy*0.19f);
	        	GL11.glVertex3f( vx*0.50f, -0.65f, vy*0.00f);
        	}

        	
        	/**
        	 * 
        	 * Zeichne die zwischen Flächen
        	 * 
        	 */
        	
        	
        	/**
        	 * Zeichne Fläche 2
        	 */
        	setColor(k.con[2]); 
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f, 0.65f, 0.50f);
        	
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f( 0.35f, 0.65f, 0.35f);
        	
        	GL11.glVertex3f( 0.35f, 0.65f, 0.35f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);
        	
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
        	
        	
        	
        	/**
        	 * Zeichne Fläche 3
        	 */
        	setColor(k.con[3]); 
        	   
			GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f( 0.50f, 0.00f,-0.65f);
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.50f, 0.00f,-0.65f);
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f( 0.46f, 0.19f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f( 0.46f, 0.19f,-0.65f);
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f( 0.46f, 0.19f,-0.65f);
        	GL11.glVertex3f( 0.35f, 0.35f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f( 0.35f, 0.35f,-0.65f);
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f( 0.35f, 0.35f,-0.65f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f( 0.65f, 0.50f,-0.00f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.50f,-0.00f);
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f( 0.50f, 0.65f,-0.00f);
        	                              
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f( 0.65f, 0.50f,-0.00f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f( 0.35f, 0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.35f, 0.65f,-0.35f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f( 0.00f, 0.50f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f( 0.00f, 0.50f,-0.65f);
        	GL11.glVertex3f( 0.00f, 0.65f,-0.50f);
        	
        	
        	
        	/**
        	 * Zeichne Fläche 0
        	 */
        	setColor(k.con[0]); 
        	
			GL11.glVertex3f(-0.50f, 0.00f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f(-0.46f, 0.19f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f(-0.46f, 0.19f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	                                    
        	GL11.glVertex3f(-0.46f, 0.19f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f(-0.35f, 0.35f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.35f, 0.35f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	                                    
        	GL11.glVertex3f(-0.35f, 0.35f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f(-0.19f, 0.46f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	                                    
        	GL11.glVertex3f(-0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f(-0.00f, 0.50f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.00f, 0.50f,-0.65f);
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f(-0.00f, 0.65f,-0.50f);
        	                                    
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f(-0.00f, 0.50f,-0.65f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	                                    
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f(-0.35f, 0.65f,-0.35f);
        	                                    
        	GL11.glVertex3f(-0.35f, 0.65f,-0.35f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	                                    
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f(-0.65f, 0.50f,-0.00f);
        	                                    
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f(-0.65f, 0.50f,-0.00f);
        	GL11.glVertex3f(-0.50f, 0.65f,-0.00f);
        	
        	
        	
        	/**
        	 * Zeichne Fläche 1
        	 */
        	setColor(k.con[1]);
        	
			GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	                                    
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
        	                                    
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
        	                                    
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f(-0.35f, 0.65f, 0.35f);
        	                                    
        	GL11.glVertex3f(-0.35f, 0.65f, 0.35f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	                                    
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f(-0.00f, 0.50f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f(-0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f(-0.00f, 0.65f, 0.50f);
  
        	
        	
        	/**
        	 * Zeichne Fläche 6
        	 */
        	setColor(k.con[6]); 
        	GL11.glVertex3f( 0.50f, -0.00f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.00f, 0.50f);
        	GL11.glVertex3f( 0.46f, -0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.65f, -0.00f, 0.50f);
        	GL11.glVertex3f( 0.46f, -0.19f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.19f, 0.46f);
        	
        	GL11.glVertex3f( 0.46f, -0.19f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.19f, 0.46f);
        	GL11.glVertex3f( 0.35f, -0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.35f, -0.35f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.19f, 0.46f);
        	GL11.glVertex3f( 0.65f, -0.35f, 0.35f);
        	
        	GL11.glVertex3f( 0.35f, -0.35f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.35f, 0.35f);
        	GL11.glVertex3f( 0.19f, -0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.19f, -0.46f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.35f, 0.35f);
        	GL11.glVertex3f( 0.65f, -0.46f, 0.19f);
        	
        	GL11.glVertex3f( 0.19f, -0.46f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.46f, 0.19f);
        	GL11.glVertex3f( 0.00f, -0.50f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, -0.50f, 0.65f);
        	GL11.glVertex3f( 0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f, -0.65f, 0.50f);
        	
        	GL11.glVertex3f( 0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f, -0.50f, 0.65f);
        	GL11.glVertex3f( 0.65f, -0.46f, 0.19f);
        	
        	GL11.glVertex3f( 0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f( 0.65f, -0.46f, 0.19f);
        	GL11.glVertex3f( 0.35f, -0.65f, 0.35f);
        	
        	GL11.glVertex3f( 0.35f, -0.65f, 0.35f);
        	GL11.glVertex3f( 0.65f, -0.46f, 0.19f);
        	GL11.glVertex3f( 0.46f, -0.65f, 0.19f);
        	
        	GL11.glVertex3f( 0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f( 0.65f, -0.46f, 0.19f);
        	GL11.glVertex3f( 0.65f, -0.50f, 0.00f);
        	
        	GL11.glVertex3f( 0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f( 0.65f, -0.50f, 0.00f);
        	GL11.glVertex3f( 0.50f, -0.65f, 0.00f);
        	
        	
        	
        	/**
        	 * Zeichne Fläche 7
        	 */
        	setColor(k.con[7]); 
        	   
			GL11.glVertex3f( 0.65f, -0.00f,-0.50f);
        	GL11.glVertex3f( 0.50f, -0.00f,-0.65f);
        	GL11.glVertex3f( 0.65f, -0.19f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.50f, -0.00f,-0.65f);
        	GL11.glVertex3f( 0.65f, -0.19f,-0.46f);
        	GL11.glVertex3f( 0.46f, -0.19f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.65f, -0.19f,-0.46f);
        	GL11.glVertex3f( 0.46f, -0.19f,-0.65f);
        	GL11.glVertex3f( 0.65f, -0.35f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.65f, -0.35f,-0.35f);
        	GL11.glVertex3f( 0.46f, -0.19f,-0.65f);
        	GL11.glVertex3f( 0.35f, -0.35f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.65f, -0.35f,-0.35f);
        	GL11.glVertex3f( 0.35f, -0.35f,-0.65f);
        	GL11.glVertex3f( 0.65f, -0.46f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.65f, -0.46f,-0.19f);
        	GL11.glVertex3f( 0.35f, -0.35f,-0.65f);
        	GL11.glVertex3f( 0.19f, -0.46f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.65f, -0.46f,-0.19f);
        	GL11.glVertex3f( 0.19f, -0.46f,-0.65f);
        	GL11.glVertex3f( 0.65f, -0.50f,-0.00f);
        	                              
        	GL11.glVertex3f( 0.65f, -0.50f,-0.00f);
        	GL11.glVertex3f( 0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f( 0.50f, -0.65f,-0.00f);
        	                              
        	GL11.glVertex3f( 0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f( 0.65f, -0.50f,-0.00f);
        	GL11.glVertex3f( 0.19f, -0.46f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f( 0.19f, -0.46f,-0.65f);
        	GL11.glVertex3f( 0.35f, -0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.35f, -0.65f,-0.35f);
        	GL11.glVertex3f( 0.19f, -0.46f,-0.65f);
        	GL11.glVertex3f( 0.19f, -0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f( 0.19f, -0.46f,-0.65f);
        	GL11.glVertex3f( 0.00f, -0.50f,-0.65f);
        	                              
        	GL11.glVertex3f( 0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f( 0.00f, -0.50f,-0.65f);
        	GL11.glVertex3f( 0.00f, -0.65f,-0.50f);
        	
        	
        	
        	/**
        	 * Zeichne Fläche 4
        	 */
        	setColor(k.con[4]); 
        	
			GL11.glVertex3f(-0.50f, -0.00f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.00f,-0.50f);
        	GL11.glVertex3f(-0.46f, -0.19f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.00f,-0.50f);
        	GL11.glVertex3f(-0.46f, -0.19f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.19f,-0.46f);
        	                                    
        	GL11.glVertex3f(-0.46f, -0.19f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.19f,-0.46f);
        	GL11.glVertex3f(-0.35f, -0.35f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.35f, -0.35f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.19f,-0.46f);
        	GL11.glVertex3f(-0.65f, -0.35f,-0.35f);
        	                                    
        	GL11.glVertex3f(-0.35f, -0.35f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.35f,-0.35f);
        	GL11.glVertex3f(-0.19f, -0.46f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.19f, -0.46f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.35f,-0.35f);
        	GL11.glVertex3f(-0.65f, -0.46f,-0.19f);
        	                                    
        	GL11.glVertex3f(-0.19f, -0.46f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.46f,-0.19f);
        	GL11.glVertex3f(-0.00f, -0.50f,-0.65f);
        	                                    
        	GL11.glVertex3f(-0.00f, -0.50f,-0.65f);
        	GL11.glVertex3f(-0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f(-0.00f, -0.65f,-0.50f);
        	                                    
        	GL11.glVertex3f(-0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f(-0.00f, -0.50f,-0.65f);
        	GL11.glVertex3f(-0.65f, -0.46f,-0.19f);
        	                                    
        	GL11.glVertex3f(-0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f(-0.65f, -0.46f,-0.19f);
        	GL11.glVertex3f(-0.35f, -.65f,-0.35f);
        	                                    
        	GL11.glVertex3f(-0.35f, -0.65f,-0.35f);
        	GL11.glVertex3f(-0.65f, -0.46f,-0.19f);
        	GL11.glVertex3f(-0.46f, -0.65f,-0.19f);
        	                                    
        	GL11.glVertex3f(-0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f(-0.65f, -0.46f,-0.19f);
        	GL11.glVertex3f(-0.65f, -0.50f,-0.00f);
        	                                    
        	GL11.glVertex3f(-0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f(-0.65f, -0.50f,-0.00f);
        	GL11.glVertex3f(-0.50f, -0.65f,-0.00f);
        	
        	
        	
        	/**
        	 * Zeichne Fläche 5
        	 */
        	setColor(k.con[5]);
        	
			GL11.glVertex3f(-0.65f, -0.00f, 0.50f);
        	GL11.glVertex3f(-0.50f, -0.00f, 0.65f);
        	GL11.glVertex3f(-0.65f, -0.19f, 0.46f);
        	                                    
        	GL11.glVertex3f(-0.50f, -0.00f, 0.65f);
        	GL11.glVertex3f(-0.65f, -0.19f, 0.46f);
        	GL11.glVertex3f(-0.46f, -0.19f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.19f, 0.46f);
        	GL11.glVertex3f(-0.46f, -0.19f, 0.65f);
        	GL11.glVertex3f(-0.65f, -0.35f, 0.35f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.35f, 0.35f);
        	GL11.glVertex3f(-0.46f, -0.19f, 0.65f);
        	GL11.glVertex3f(-0.35f, -0.35f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.35f, 0.35f);
        	GL11.glVertex3f(-0.35f, -0.35f, 0.65f);
        	GL11.glVertex3f(-0.65f, -0.46f, 0.19f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.46f, 0.19f);
        	GL11.glVertex3f(-0.35f, -0.35f, 0.65f);
        	GL11.glVertex3f(-0.19f, -0.46f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.46f, 0.19f);
        	GL11.glVertex3f(-0.19f, -0.46f, 0.65f);
        	GL11.glVertex3f(-0.65f, -0.50f, 0.00f);
        	                                    
        	GL11.glVertex3f(-0.65f, -0.50f, 0.00f);
        	GL11.glVertex3f(-0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f(-0.50f, -0.65f, 0.00f);
        	                                    
        	GL11.glVertex3f(-0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f(-0.65f, -0.50f, 0.00f);
        	GL11.glVertex3f(-0.19f, -0.46f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f(-0.19f, -0.46f, 0.65f);
        	GL11.glVertex3f(-0.35f, -0.65f, 0.35f);
        	                                    
        	GL11.glVertex3f(-0.35f, -0.65f, 0.35f);
        	GL11.glVertex3f(-0.19f, -0.46f, 0.65f);
        	GL11.glVertex3f(-0.19f, -0.65f, 0.46f);
        	                                    
        	GL11.glVertex3f(-0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f(-0.19f, -0.46f, 0.65f);
        	GL11.glVertex3f(-0.00f, -0.50f, 0.65f);
        	                                    
        	GL11.glVertex3f(-0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f(-0.00f, -0.50f, 0.65f);
        	GL11.glVertex3f(-0.00f, -0.65f, 0.50f);
        

        /**
         * Kugel an sich fertig gerendert	
         */
        GL11.glEnd();                    
        
        /**
         * Setze die Breite von Linien welche gezeichnet werden
         */
        GL11.glLineWidth((float) 2.0); 
        
        /**
         * Setze die Farbe auf Schwarz
         */
        GL11.glColor3f(0.0f,0.0f,0.0f); 
        
        /**
         * Starte das Rendern der schwarzen Linien
         */
        GL11.glBegin(GL11.GL_LINES); 
        
        /**
         * Rendere jeweils 8 1/4 Pole in einer Schlaufe
         */
        for(int i = 0; i<8;i++){
        	int vzx = 1; int vzy  = 1; int vzz = 1;
        	if(i==0){vzx= 1; vzy = -1; vzz = 1;}
        	else if(i==1){vzx= 1; vzy = 1; vzz = 1;}
        	else if(i==2){vzx= -1; vzy = 1; vzz = 1;}
        	else if(i==3){vzx= -1; vzy = -1; vzz = 1;}
        	else if(i==4){vzx= 1; vzy = -1; vzz = -1;}
        	else if(i==5){vzx= 1; vzy = 1; vzz = -1;}
        	else if(i==6){vzx= -1; vzy = 1; vzz = -1;}
        	else if(i==7){vzx= -1; vzy = -1; vzz = -1;}
        	
        	GL11.glVertex3f( vzx*0.00f, vzy*0.00f, vzz*1.00f);
        	GL11.glVertex3f( vzx*0.00f, vzy*0.50f, vzz*0.65f);
        
        	GL11.glVertex3f( vzx*0.00f, vzy*0.50f, vzz*0.65f);
        	GL11.glVertex3f( vzx*0.19f, vzy*0.46f, vzz*0.65f);
        	
        	GL11.glVertex3f( vzx*0.19f, vzy*0.46f, vzz*0.65f);
        	GL11.glVertex3f( vzx*0.35f, vzy*0.35f, vzz*0.65f);
        	
        	GL11.glVertex3f( vzx*0.35f, vzy*0.35f, vzz*0.65f);
        	GL11.glVertex3f( vzx*0.46f, vzy*0.19f, vzz*0.65f);
        	
        	GL11.glVertex3f( vzx*0.46f, vzy*0.19f, vzz*0.65f);
        	GL11.glVertex3f( vzx*0.50f, vzy*0.00f, vzz*0.65f);
        
        	GL11.glVertex3f( vzx*0.50f, vzy*0.00f, vzz*0.65f);
        	GL11.glVertex3f( vzx*0.00f, vzy*0.00f, vzz*1.00f);
        		
        }
    	
        for(int i = 0; i<8;i++){
        	int vzx = 1; int vzy  = 1; int vzz = 1;
        	if(i==0){vzx= 1; vzy = -1; vzz = 1;}
        	else if(i==1){vzx= 1; vzy = 1; vzz = 1;}
        	else if(i==2){vzx= 1; vzy = 1; vzz = -1;}
        	else if(i==3){vzx= 1; vzy = -1; vzz = -1;}
        	else if(i==4){vzx= -1; vzy = -1; vzz = 1;}
        	else if(i==5){vzx= -1; vzy = 1; vzz = 1;}
        	else if(i==6){vzx= -1; vzy = 1; vzz = -1;}
        	else if(i==7){vzx= -1; vzy = -1; vzz = -1;}
        	
			GL11.glVertex3f( vzx*1.00f, vzy*0.00f, vzz*0.00f);
        	GL11.glVertex3f( vzx*0.65f, vzy*0.50f, vzz*0.00f);
                                                       
        	GL11.glVertex3f( vzx*0.65f, vzy*0.50f, vzz*0.00f);
        	GL11.glVertex3f( vzx*0.65f, vzy*0.46f, vzz*0.19f);
        	                                           
        	GL11.glVertex3f( vzx*0.65f, vzy*0.46f, vzz*0.19f);
        	GL11.glVertex3f( vzx*0.65f, vzy*0.35f, vzz*0.35f);
        	                                           
        	GL11.glVertex3f( vzx*0.65f, vzy*0.35f, vzz*0.35f);
        	GL11.glVertex3f( vzx*0.65f, vzy*0.19f, vzz*0.46f);
        	                                           
        	GL11.glVertex3f( vzx*0.65f, vzy*0.19f, vzz*0.46f);
        	GL11.glVertex3f( vzx*0.65f, vzy*0.00f, vzz*0.50f);
                                                       
        	GL11.glVertex3f( vzx*0.65f, vzy*0.00f, vzz*0.50f);
        	GL11.glVertex3f( vzx*1.00f, vzy*0.00f, vzz*0.00f);
        		
        }       
        for(int i = 0; i<8;i++){
        	int vzx = 1; int vzy  = 1; int vzz = 1;
        	if(i==0){vzx= 1; vzy = -1; vzz = -1;}
        	else if(i==1){vzx= 1; vzy = -1; vzz = 1;}
        	else if(i==2){vzx= -1; vzy = -1; vzz = 1;}
        	else if(i==3){vzx= -1; vzy = -1; vzz = -1;}
        	else if(i==4){vzx= 1; vzy = 1; vzz = -1;}
        	else if(i==5){vzx= 1; vzy = 1; vzz = 1;}
        	else if(i==6){vzx= -1; vzy = 1; vzz = 1;}
        	else if(i==7){vzx= -1; vzy = 1; vzz = -1;}
        	
        	GL11.glVertex3f( vzx*0.00f, vzy*1.00f, vzz*0.00f);
        	GL11.glVertex3f( vzx*0.00f, vzy*0.65f, vzz*0.50f);
                                                       
        	GL11.glVertex3f( vzx*0.00f, vzy*0.65f, vzz*0.50f);
        	GL11.glVertex3f( vzx*0.19f, vzy*0.65f, vzz*0.46f);
        	                                           
        	GL11.glVertex3f( vzx*0.19f, vzy*0.65f, vzz*0.46f);
        	GL11.glVertex3f( vzx*0.35f, vzy*0.65f, vzz*0.35f);
        	                                           
        	GL11.glVertex3f( vzx*0.35f, vzy*0.65f, vzz*0.35f);
        	GL11.glVertex3f( vzx*0.46f, vzy*0.65f, vzz*0.19f);
        	                                           
        	GL11.glVertex3f( vzx*0.46f, vzy*0.65f, vzz*0.19f);
        	GL11.glVertex3f( vzx*0.50f, vzy*0.65f, vzz*0.00f);
                                                       
        	GL11.glVertex3f( vzx*0.50f, vzy*0.65f, vzz*0.00f);
        	GL11.glVertex3f( vzx*0.00f, vzy*1.00f, vzz*0.00f);
        		
        }


        /**
         * Rendere die Zwischen Stücke zwischen den Polen
         */
    	
    	GL11.glVertex3f(0.50f, 0.00f, 0.65f);
    	GL11.glVertex3f(0.65f, 0.00f, 0.50f);
    	
    	GL11.glVertex3f(0.65f, 0.00f, -0.50f);
    	GL11.glVertex3f(0.50f, 0.00f, -0.65f);
    	
    	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
    	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
    	
    	GL11.glVertex3f(-0.65f, 0.00f, -0.50f);
    	GL11.glVertex3f(-0.50f, 0.00f, -0.65f);
    	
    	
    	/***/
    	
		GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
    	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);
    	                             
    	GL11.glVertex3f( 0.65f,-0.50f, 0.00f);
    	GL11.glVertex3f( 0.50f,-0.65f, 0.00f);
    	                             
    	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
    	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
    	                             
    	GL11.glVertex3f(-0.65f,-0.50f, 0.00f);
    	GL11.glVertex3f(-0.50f,-0.65f, 0.00f);
    	
    	/***/
    	
		GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
    	GL11.glVertex3f( 0.00f, 0.65f, 0.50f);
    	                             
    	GL11.glVertex3f( 0.00f, 0.65f,-0.50f);
    	GL11.glVertex3f( 0.00f, 0.50f,-0.65f);
    	                             
    	GL11.glVertex3f( 0.00f,-0.50f, 0.65f);
    	GL11.glVertex3f( 0.00f,-0.65f, 0.50f);
    	                             
    	GL11.glVertex3f( 0.00f,-0.65f,-0.50f);
    	GL11.glVertex3f( 0.00f,-0.50f,-0.65f);
    	
    	/**
    	 * Beende das Rendern der Linien
    	 */
        GL11.glEnd();
        
       /*
        GL11.glLoadIdentity();
        GL11.glTranslatef(1.4f,1.0f,-4.0f);
        Color.white.bind();
        texture.bind(); // or GL11.glBind(texture.getTextureID());
        
        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glTexCoord2f(0.0f, 0.0f);
	        GL11.glVertex3f(-0.5f, 0.5f, 0.5f); // Top Left Of The Texture and Quad
	        
	        GL11.glTexCoord2f(1.0f, 0.0f);
	        GL11.glVertex3f( 0.5f, 0.5f, 0.5f); // Top Right Of The Texture and Quad
	        
	        GL11.glTexCoord2f(1.0f, 1.0f);
	        GL11.glVertex3f( 0.5f, -0.5f, 0.5f); // Bottom Right Of The Texture and Quad
	        
	        GL11.glTexCoord2f(0.0f, 1.0f);
	        GL11.glVertex3f(-0.5f, -0.5f, 0.5f); // Bottom Left Of The Texture and Quad
        GL11.glEnd();
        
               */
        /**
         * Gebe true zurück, da Rendern ohne Fehler durch ging
         */
        return true;
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

        /**
         * Versuche das Icon für das Fenster zu laden
         */
        try {
        	/**
        	 * Erstelle ein ByteBuffer array für alle verfügbaren icons
        	 */
            ByteBuffer[] icons = new ByteBuffer[1];
            
            /**
             * Defniniere welche Icons geladen werden, inklusive der grösse
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
     * Funktion, welche für das laden des Icons und umwandeln zu einem Bytebuffer verantwortlich ist
     * 
     * 
     * @param filename : gibt den Dateinamen an, welche Datei geladen wird
     * @param width : breite der Datei, welche geladen werden wird
     * @param height : höhe der Datei, welche geladen werden wird
     * @return : gibt den ByteBuffer zurück
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
         * erstelle ein Array welches genügend gross für die voreingestellte grösse des Bildes ist
         * breite*höhe*4 (die 4 ist für rot, grün, blau und alpha)
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
     
        
        /**
         * Die Perspektive Berechnen mit hilfe von GLU
         */
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          100.0f);
        
        /**
         * Den Matrixmodus wählen
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
    
    /**
     * Funktion welche beim Beenden Aufgerufen wird, ist relativ wichtig, damit Speicher wieder freigegeben wird 
     */
    private static void cleanup() {
        Display.destroy();
        System.exit(0);
    }
}

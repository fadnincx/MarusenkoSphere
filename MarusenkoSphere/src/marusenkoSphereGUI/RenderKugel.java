package marusenkoSphereGUI;

import marusenkoSphereKugel.Kugel;

import org.lwjgl.opengl.GL11;

/**
 * RenderKugel - Datei
 * 
 * Enthält die Funktion, welche die Kugel effektiv Rendert
 *
 */
public class RenderKugel {

	/**
	 * Funktion welche das rendern der Kugel Aufruft
	 * @param k
	 * @param rx
	 * @param ry
	 * @param rz
	 * @return
	 */
	protected static boolean render(Kugel k, float rx, float ry, float rz){
		//Ändere die Methode um ein anderes Rendern zu bekommen
		return render30(k,rx,ry,rz);
	}
	
	
	/**
	 * Erste Version der Kugel zu Rendern, funktioniert praktisch ohne Fehler, jedoch ist Kugel nicht wirklich Rund
	 * Auch ist sie alles Andere als Effizient
	 * @param k : Kugel die gerendert werden soll
	 * @param rtrix : Drehung um x-Achse
	 * @param rtriy : Drehung um y-Achse
	 * @param rtriz : Drehung um z-Achse
	 * @return : true wenn erfolgreich
	 */
	@SuppressWarnings("unused")
	private static boolean render10(Kugel k,float rtrix, float rtriy, float rtriz) {
    	/**
    	 * Auskommentieren, damit Flächen der Kugel nicht gefüllt werden und nur die aussen Kanten einer Fläche gezeichnet werden
    	 * 
    	 * Eigentlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
    	/**
    	 * Löscht den gesammten Bereich, damit neu gerendert werden kann
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
         * Drehe die Kugel gemäss den Variabeln auf x,y und z Achse
         */
        GL11.glRotatef(rtriy,0.0f,1.0f,0.0f);
        GL11.glRotatef(rtrix,1.0f,0.0f,0.0f);
        GL11.glRotatef(rtriz,0.0f,0.0f,1.0f);
        
        /**
         * Starte das Zeichnen der einzelnen Flächen der Kugel
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
        		 * ändere die Negationsvariabeln gemäss Pol und setzte die Farbe, mit welcher Gerendert wird
        		 */
        		if(i == 0){vx = 1;vy = 1; Rendern.setColor(k.tri[0]);
        		}else if(i == 1){vx = 1;vy = -1; Rendern.setColor(k.tri[1]);
            	}else if(i == 2){vx = -1;vy = -1; Rendern.setColor(k.tri[2]);
                }else if(i == 3){vx = -1;vy = 1; Rendern.setColor(k.tri[3]);	
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
        		if(i == 0){vx = 1;vy = 1; Rendern.setColor(k.tri[4]);
        		}else if(i == 1){vx = 1;vy = -1; Rendern.setColor(k.tri[5]);
            	}else if(i == 2){vx = -1;vy = -1; Rendern.setColor(k.tri[6]);
                }else if(i == 3){vx = -1;vy = 1; Rendern.setColor(k.tri[7]);	
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
        		if(i == 0){vx = 1;vy = 1; Rendern.setColor(k.tri[8]);
        		}else if(i == 1){vx = 1;vy = -1; Rendern.setColor(k.tri[9]);
            	}else if(i == 2){vx = -1;vy = -1; Rendern.setColor(k.tri[10]);
                }else if(i == 3){vx = -1;vy = 1; Rendern.setColor(k.tri[11]);	
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
        		if(i == 0){vx = 1;vy = 1; Rendern.setColor(k.tri[12]);
        		}else if(i == 1){vx = 1;vy = -1; Rendern.setColor(k.tri[13]);
            	}else if(i == 2){vx = -1;vy = -1; Rendern.setColor(k.tri[14]);
                }else if(i == 3){vx = -1;vy = 1; Rendern.setColor(k.tri[15]);	
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
        		if(i == 0){vx = 1;vy = 1; Rendern.setColor(k.tri[16]);
        		}else if(i == 1){vx = 1;vy = -1; Rendern.setColor(k.tri[17]);
            	}else if(i == 2){vx = -1;vy = -1; Rendern.setColor(k.tri[18]);
                }else if(i == 3){vx = -1;vy = 1; Rendern.setColor(k.tri[19]);	
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
        		if(i == 0){vx = 1;vy = 1; Rendern.setColor(k.tri[20]);
        		}else if(i == 1){vx = 1;vy = -1; Rendern.setColor(k.tri[21]);
            	}else if(i == 2){vx = -1;vy = -1; Rendern.setColor(k.tri[22]);
                }else if(i == 3){vx = -1;vy = 1; Rendern.setColor(k.tri[23]);	
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
        	Rendern.setColor(k.con[2]); 
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
        	Rendern.setColor(k.con[3]); 
        	   
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
        	Rendern.setColor(k.con[0]); 
        	
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
        	Rendern.setColor(k.con[1]);
        	
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
        	Rendern.setColor(k.con[6]); 
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
        	Rendern.setColor(k.con[7]); 
        	   
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
        	Rendern.setColor(k.con[4]); 
        	
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
        	Rendern.setColor(k.con[5]);
        	
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
        
        /**
         * Gebe true zurück, da Rendern ohne Fehler durch ging
         */
        return true;
    }
	
	/**
	 * Funktion damit die Winkel der Bequemheits halber in Grad angegeben werden können und diese dann in Radiant umgerechnet werden können
	 * @param deg
	 * @return
	 */
	private static double deg2rad(double deg){
		return deg*(Math.PI/180);
	}
	
	/**
	 * 2. Version der Methode zum Rendern der Kugel. Ist eine Wirkliche Kugel, jedoch noch nicht ganz Fehler frei. 
	 * 		==> Übergang von 1em zum anderen Element ist nicht sauber...
	 * @param k : Kugel
	 * @param rx : Rotation auf x-Achse
	 * @param ry : Rotation auf y-Achse
	 * @param rz : Rotation auf z-Achse
	 * @return : true wenn Erfolgreich
	 */
	@SuppressWarnings("unused")
	private static boolean render20(Kugel k,float rx, float ry, float rz) {
    	/**
    	 * Auskommentieren, damit Flächen der Kugel nicht gefüllt werden
    	 * 
    	 * Hauptsächlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
		
    	//Löscht den gesammten Bereich, damit neu gerendert werden kann
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Setze die Position zum Rendern zurück 
        GL11.glLoadIdentity();
        
        //Verschiebe die 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
        GL11.glTranslatef(0.0f,0.0f,-4.0f);
        
        //Drehe die Kugel gemäss den Variabeln auf der x,y und z Achse
        GL11.glRotatef(ry,0.0f,1.0f,0.0f);
        GL11.glRotatef(rx,1.0f,0.0f,0.0f);
        GL11.glRotatef(rz,0.0f,0.0f,1.0f);
        

        //Starte das Zeichnen der einzelnen Flächen der Kugel
        GL11.glBegin(GL11.GL_TRIANGLES);

        
		//Definiere zwei relativ grosse 4-Dimensionale Arrays, welche die Korrdinaten für die Dreiecke enthalten welche einen Pol Viertel, bzw ein Zwischenstück ergeben
        //([j][l][Koordinate][x/y/z]
		double[][][][] pole = new double[90][40][6][3];
		double[][][][] zwi = new double[90][90][6][3];
		
		//Fülle das Array für einen Pol Viertel
		for(int j = 0; j<90; j++){
			for(int l = 50; l<90; l++){	
				//1. Dreieck 1. Korrdinate
				pole[j][l-50][0][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
				pole[j][l-50][0][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
				pole[j][l-50][0][2] = Math.sin(deg2rad(l));
				//1. Dreieck 2. Korrdinate
				pole[j][l-50][1][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j+1)));
				pole[j][l-50][1][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j+1)));
				pole[j][l-50][1][2] = Math.sin(deg2rad(l));
				//1. Dreieck 3. Korrdinate
				pole[j][l-50][2][0] = (Math.cos(deg2rad(l+1))*Math.sin(deg2rad(j+1)));
				pole[j][l-50][2][1] = (Math.cos(deg2rad(l+1))*Math.cos(deg2rad(j+1)));
				pole[j][l-50][2][2] = Math.sin(deg2rad(l+1));
				
				//2. Dreieck 1. Korrdinate
				pole[j][l-50][3][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
				pole[j][l-50][3][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
				pole[j][l-50][3][2] = Math.sin(deg2rad(l));
				//2. Dreieck 2. Korrdinate
				pole[j][l-50][4][0] = (Math.cos(deg2rad(l+1))*Math.sin(deg2rad(j)));
				pole[j][l-50][4][1] = (Math.cos(deg2rad(l+1))*Math.cos(deg2rad(j)));
				pole[j][l-50][4][2] = Math.sin(deg2rad(l+1));
				//2. Dreieck 3. Korrdinate 
				pole[j][l-50][5][0] = (Math.cos(deg2rad(l+1))*Math.sin(deg2rad(j+1)));
				pole[j][l-50][5][1] = (Math.cos(deg2rad(l+1))*Math.cos(deg2rad(j+1)));
				pole[j][l-50][5][2] = Math.sin(deg2rad(l+1));
			} 			
   		}
		
		//Fülle das Array für ein Verbindungsstück
		for(int j = 60; j>0; j--){
			for(int l = 0; l<90; l++){	
				//Sofern nicht Teil eines Pols...
				
					//1. Dreieck 1. Korrdinate
					zwi[j][l][0][0] = (Math.cos(deg2rad(j))*Math.sin(deg2rad(l)));
					zwi[j][l][0][1] = Math.sin(deg2rad(j));
					zwi[j][l][0][2] = (Math.cos(deg2rad(j))*Math.cos(deg2rad(l)));
					//1. Dreieck 2. Korrdinate
					zwi[j][l][1][0] = (Math.cos(deg2rad(j))*Math.sin(deg2rad(l+1)));
					zwi[j][l][1][1] = Math.sin(deg2rad(j));
					zwi[j][l][1][2] = (Math.cos(deg2rad(j))*Math.cos(deg2rad(l+1)));
					//1. Dreieck 3. Korrdinate
					zwi[j][l][2][0] = (Math.cos(deg2rad(j-1))*Math.sin(deg2rad(l)));
					zwi[j][l][2][1] = Math.sin(deg2rad(j-1));
					zwi[j][l][2][2] = (Math.cos(deg2rad(j-1))*Math.cos(deg2rad(l)));
					
					//2. Dreieck 1. Korrdinate
					zwi[j][l][3][0] = (Math.cos(deg2rad(j-1))*Math.sin(deg2rad(l+1)));
					zwi[j][l][3][1] = Math.sin(deg2rad(j-1));
					zwi[j][l][3][2] = (Math.cos(deg2rad(j-1))*Math.cos(deg2rad(l+1)));
					//2. Dreieck 2. Korrdinate
					zwi[j][l][4][0] = (Math.cos(deg2rad(j))*Math.sin(deg2rad(l+1)));
					zwi[j][l][4][1] = Math.sin(deg2rad(j));
					zwi[j][l][4][2] = (Math.cos(deg2rad(j))*Math.cos(deg2rad(l+1)));
					//2. Dreieck 3. Korrdinate 
					zwi[j][l][5][0] = (Math.cos(deg2rad(j-1))*Math.sin(deg2rad(l)));
					zwi[j][l][5][1] = Math.sin(deg2rad(j-1));
					zwi[j][l][5][2] = (Math.cos(deg2rad(j-1))*Math.cos(deg2rad(l)));
				
			} 			
   		}
		
		//Schleife um alle 24 Pol Viertel aus dem Berechneten auszu geben
		for(int i = 0; i<24; i++){
			
			//Invertierungs Variabel
			int ix = 1;
			int iy = 1;
			int iz = 1;
			
			//Positionierungs Variabeln
			int px = 0;
			int py = 1;
			int pz = 2;
			
			//Definiere die Invertierungs Variabel und die Positionierungs Variabel für jeden Viertel neu. Zusätzlich noch die Farbe des Viertels Definieren
			switch(i){
			case 0:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[0]);
				break;
			case 1:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[1]);
				break;
			case 2:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[2]);
				break;
			case 3:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[3]);
				break;
			//
			case 4:
				ix = 1; iy = 1; iz = 1;
				px = 2; py = 0; pz = 1;
				Rendern.setColor(k.tri[4]);
				break;
			case 5:
				ix = 1; iy = -1; iz =1;
				px = 2; py = 0; pz = 1;
				Rendern.setColor(k.tri[5]);
				break;
			case 6:
				ix = 1; iy =-1; iz =-1;
				px = 2; py = 0; pz = 1;
				Rendern.setColor(k.tri[6]);
				break;
			case 7:
				ix = 1; iy =1; iz = -1;
				px = 2; py = 0; pz = 1;
				Rendern.setColor(k.tri[7]);
				break;
			//
			case 8:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[8]);
				break;
			case 9:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[9]);
				break;
			case 10:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[10]);
				break;
			case 11:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.tri[11]);
				break;
			//
			case 12:
				ix =-1; iy = 1; iz = 1;
				px = 2; py = 1; pz = 0;
				Rendern.setColor(k.tri[12]);
				break;
			case 13:
				ix =-1; iy =-1; iz = 1;
				px = 2; py = 1; pz = 0;
				Rendern.setColor(k.tri[13]);
				break;
			case 14:
				ix =-1; iy =-1; iz =-1;
				px = 2; py = 1; pz = 0;
				Rendern.setColor(k.tri[14]);
				break;
			case 15:
				ix =-1; iy = 1; iz =-1;
				px = 2; py = 1; pz = 0;
				Rendern.setColor(k.tri[15]);
				break;
			//
			case 16:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[16]);
				break;
			case 17:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[17]);
				break;
			case 18:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[18]);
				break;
			case 19:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[19]);
				break;
			//
			case 20:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[20]);
				break;
			case 21:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[21]);
				break;
			case 22:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[22]);
				break;
			case 23:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				Rendern.setColor(k.tri[23]);
				break;
			}
			
			//Dann den Viertel Rendern 
			for(int j = 0; j<90; j++){
    			for(int l = 0; l<40; l++){
    				GL11.glVertex3d(ix*pole[j][l][0][px], iy*pole[j][l][0][py], iz*pole[j][l][0][pz]);
    				GL11.glVertex3d(ix*pole[j][l][1][px], iy*pole[j][l][1][py], iz*pole[j][l][1][pz]);
    				GL11.glVertex3d(ix*pole[j][l][2][px], iy*pole[j][l][2][py], iz*pole[j][l][2][pz]);
    				
    				GL11.glVertex3d(ix*pole[j][l][3][px], iy*pole[j][l][3][py], iz*pole[j][l][3][pz]);
    				GL11.glVertex3d(ix*pole[j][l][4][px], iy*pole[j][l][4][py], iz*pole[j][l][4][pz]);
    				GL11.glVertex3d(ix*pole[j][l][5][px], iy*pole[j][l][5][py], iz*pole[j][l][5][pz]);
    			} 			
       		}
			
			
		}
		
		//Rendern der Zwischen Stücke
		for(int i = 0; i<8; i++){
			//Invertierungsvariabeln
			int ix = 1;
			int iy = 1;
			int iz = 1;
			//Positionierungsvariabeln
			int px = 0;
			int py = 1;
			int pz = 2;
			
			//Definiere die Variabeln und die Farbe für jedes Zwischenstück individuell
			switch(i){
			case 0:
				ix = -1; iy = 1; iz = -1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[0]);
				break;
			case 1:
				ix = -1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[1]);
				break;
			case 2:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[2]);
				break;
			case 3:
				ix = 1; iy = 1; iz = -1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[3]);
				break;
			case 4:
				ix = -1; iy = -1; iz = -1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[4]);
				break;
			case 5:
				ix = -1; iy = -1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[5]);
				break;
			case 6:
				ix = 1; iy = -1; iz = 1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[6]);
				break;
			case 7:
				ix = 1; iy = -1; iz = -1;
				px = 0; py = 1; pz = 2;
				Rendern.setColor(k.con[7]);
				break;	
			}
			
			//Rendere Jedes Zwischenstück
			for(int j = 50; j>0; j--){
				for(int l = 0; l<90; l++){	
					//Sofern es nicht zu einem Pol gehört
					if((Math.cos(deg2rad(j))*Math.sin(deg2rad(l)))<=Math.sin(deg2rad(50))&&(Math.cos(deg2rad(j))*Math.cos(deg2rad(l)))<=Math.sin(deg2rad(50))){
						GL11.glVertex3d(ix*zwi[j][l][0][px],iy*zwi[j][l][0][py],iz*zwi[j][l][0][pz]);
						GL11.glVertex3d(ix*zwi[j][l][1][px],iy*zwi[j][l][1][py],iz*zwi[j][l][1][pz]);
						GL11.glVertex3d(ix*zwi[j][l][2][px],iy*zwi[j][l][2][py],iz*zwi[j][l][2][pz]);
						
						GL11.glVertex3d(ix*zwi[j][l][3][px],iy*zwi[j][l][3][py],iz*zwi[j][l][3][pz]);
						GL11.glVertex3d(ix*zwi[j][l][4][px],iy*zwi[j][l][4][py],iz*zwi[j][l][4][pz]);
						GL11.glVertex3d(ix*zwi[j][l][5][px],iy*zwi[j][l][5][py],iz*zwi[j][l][5][pz]);
					}
					
				} 			
	   		}
			
		}
		//Beende das Rendern
        GL11.glEnd();
        
        //Gib true zurück, wenn hier ankommt, dann alles Erfolgreich
        return true;
    }
	/**
	 * 3. Version der Methode zum Rendern der Kugel. Mit Animation
	 * @param k : Kugel
	 * @param rx : Rotation auf x-Achse
	 * @param ry : Rotation auf y-Achse
	 * @param rz : Rotation auf z-Achse
	 * @return : true wenn Erfolgreich
	 */
	private static boolean render30(Kugel k,float rx, float ry, float rz) {
    	/**
    	 * Auskommentieren, damit Flächen der Kugel nicht gefüllt werden
    	 * 
    	 * Hauptsächlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
		
    	//Löscht den gesammten Bereich, damit neu gerendert werden kann
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Setze die Position zum Rendern zurück 
        GL11.glLoadIdentity();

        //Verschiebe die 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
        GL11.glTranslatef(0.0f,0.0f,-4.0f);
        
        //Drehe die Kugel gemäss den Variabeln auf der x,y und z Achse
        GL11.glRotatef(ry,0.0f,1.0f,0.0f);
        GL11.glRotatef(rx,1.0f,0.0f,0.0f);
        GL11.glRotatef(rz,0.0f,0.0f,1.0f);
        

        //Starte das Zeichnen der einzelnen Flächen der Kugel
        GL11.glBegin(GL11.GL_TRIANGLES);

        
        
        int drehePol = 0;
        //int dreheAnz = 0;
        int dreheMode = 0;
        
        
        if(k.getOldStep()-1==k.getStep()){
        	drehePol = Integer.parseInt(k.oldDrehung.substring(0, 1));
           // dreheAnz = Integer.parseInt(k.oldDrehung.substring(1, 2));
            dreheMode = Integer.parseInt(k.oldDrehung.substring(2, 3));
		}else
		if(k.getOldStep()+1==k.getStep()){
			drehePol = Integer.parseInt(k.drehung.substring(0, 1));
	     //   dreheAnz = Integer.parseInt(k.drehung.substring(1, 2));
	        dreheMode = Integer.parseInt(k.drehung.substring(2, 3));
		}else{
			drehePol = 0;
	     //   dreheAnz = 0;
	        dreheMode = 0;
		}
        
		//Invertierungs Variabel
		int ix = 1;
		int iy = 1;
		int iz = 1;
		
		//Positionierungs Variabeln
		int px = 0;
		int py = 1;
		int pz = 2;
		
		
		boolean x1 = false;
		boolean x3 = false;
		boolean mx1 = false;
		boolean mx3 = false;
		boolean y1 = false;
		boolean y3 = false;
		boolean my1 = false;
		boolean my3 = false;
		boolean z1 = false;
		boolean z3 = false;
		boolean mz1 = false;
		boolean mz3 = false;
		
		
		double drehx = 0.0;
		double drehy = 0.0;
		double drehz = 0.0;
		int neg = 1;
		int addNeg = 1;
		
		
		if(k.standRot>0){
			if(dreheMode==1){
				neg = 1;
				switch(drehePol){
				case 0:
					z1 = true;
					break;
				case 1:
					x1 = true;
					break;
				case 2:
					addNeg = -1;
					mz1 = true;
					break;
				case 3:
					addNeg = -1;
					mx1 = true;
					break;
				case 4:
					y1 = true;
					break;
				case 5:
					if(k.drehRichtung==-1){
						addNeg = -1;
					}
					my1 = true; 
					break;
				}
			}else
			if(dreheMode==3){
				neg = -1;
				switch(drehePol){
				case 0:
					addNeg = -1;
					z1 = true;
					z3 = true;
					break;
				case 1:
					addNeg = -1;
					x1 = true;
					x3 = true;
					break;
				case 2:
					mz1 = true;
					mz3 = true;
					break;
				case 3:
					mx1 = true;
					mx3 = true;
					break;
				case 4:
					addNeg = -1;
					y1 = true;
					y3 = true;
					break;
				case 5:
					my1 = true; 
					my3 = true; 
					break;
				}
			}
		}
       
       
        //double k.standRot = k.k.standRot;
        double rot = Manager.getRotationSpeed();
        int renderSteps = 5;//Muss durch 90 und 40 dividierbar sein
        int renderStepsZ = 5;
		
		//Schleife um alle 24 Pol Viertel aus dem Berechneten auszu geben
		for(int i = 0; i<24; i++){

			drehx = 0.0;
			drehy = 0.0;
			drehz = 0.0;
			
			//Definiere die Invertierungs Variabel und die Positionierungs Variabel für jeden Viertel neu. Zusätzlich noch die Farbe des Viertels Definieren
			switch(i){
			case 0:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){
					drehz=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[0]);
				break;
			case 1:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){
					drehz=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[1]);
				break;
			case 2:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){
					drehz=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[2]);
				break;
			case 3:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;
				if(z1){
					drehz=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[3]);
				break;
			//
			case 4:
				ix = 1; iy = 1; iz = 1;
				px = 2; py = 0; pz = 1;
				if(x1){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[4]);
				break;
			case 5:
				ix = 1; iy = -1; iz =1;
				px = 2; py = 0; pz = 1;
				if(x1){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[5]);
				break;
			case 6:
				ix = 1; iy =-1; iz =-1;
				px = 2; py = 0; pz = 1;
				if(x1){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[6]);
				break;
			case 7:
				ix = 1; iy =1; iz = -1;
				px = 2; py = 0; pz = 1;
				if(x1){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[7]);
				break;
			//
			case 8:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){
					drehz=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[8]);
				break;
			case 9:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){
					drehz=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[9]);
				break;
			case 10:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){
					drehz=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[10]);
				break;
			case 11:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 1; pz = 2;
				if(mz1){
					drehz=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[11]);
				break;
			//
			case 12:
				ix =-1; iy = 1; iz = 1;
				px = 2; py = 1; pz = 0;
				if(mx1){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[12]);
				break;
			case 13:
				ix =-1; iy =-1; iz = 1;
				px = 2; py = 1; pz = 0;
				if(mx1){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[13]);
				break;
			case 14:
				ix =-1; iy =-1; iz =-1;
				px = 2; py = 1; pz = 0;
				if(mx1){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[14]);
				break;
			case 15:
				ix =-1; iy = 1; iz =-1;
				px = 2; py = 1; pz = 0;
				if(mx1){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}
				Rendern.setColor(k.tri[15]);
				break;
			//
			case 16:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(y1){
					drehy=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}
				Rendern.setColor(k.tri[16]);
				break;
			case 17:
				ix = 1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(y1){
					drehy=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[17]);
				break;
			case 18:
				ix =-1; iy = 1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(y1){
					drehy=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[18]);
				break;
			case 19:
				ix =-1; iy = 1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(y1){
					drehy=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[19]);
				break;
			//
			case 20:
				ix = 1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(my1){
					drehy=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[20]);
				break;
			case 21:
				ix = 1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(my1){
					drehy=k.standRot-rot;
				}else
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[21]);
				break;
			case 22:
				ix =-1; iy =-1; iz =-1;
				px = 0; py = 2; pz = 1;
				if(my1){
					drehy=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[22]);
				break;
			case 23:
				ix =-1; iy =-1; iz = 1;
				px = 0; py = 2; pz = 1;
				if(my1){
					drehy=k.standRot-rot;
				}else
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.tri[23]);
				break;
			}
			
				drehx*=k.drehRichtung;
				drehy*=k.drehRichtung;
				drehz*=k.drehRichtung;
				drehx*=neg;
				drehy*=neg;
				drehz*=neg;
				drehx*=addNeg;
				drehy*=addNeg;
				drehz*=addNeg;
		
			if(k.getOldStep()>k.getStep()){
				drehx*=-1;
				drehy*=-1;
				drehz*=-1;
			}
			
			
			//Dann den Viertel Rendern 
			for(int j = 0; j<90; j+=renderSteps){
    			for(int l = 50; l<90; l+=renderSteps){
    				double[][] pole = new double[6][3];
    				
    
    				//1. Dreieck 1. Korrdinate
    				pole[0][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
    				pole[0][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
    				pole[0][2] = Math.sin(deg2rad(l));
    				//1. Dreieck 2. Korrdinate
    				pole[1][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j+renderSteps)));
    				pole[1][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j+renderSteps)));
    				pole[1][2] = Math.sin(deg2rad(l));
    				//1. Dreieck 3. Korrdinate
    				pole[2][0] = (Math.cos(deg2rad(l+renderSteps))*Math.sin(deg2rad(j+renderSteps)));
    				pole[2][1] = (Math.cos(deg2rad(l+renderSteps))*Math.cos(deg2rad(j+renderSteps)));
    				pole[2][2] = Math.sin(deg2rad(l+renderSteps));
    						
    				//2. Dreieck 1. Korrdinate
    				pole[3][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
    				pole[3][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
    				pole[3][2] = Math.sin(deg2rad(l));
    				//2. Dreieck 2. Korrdinate
    				pole[4][0] = (Math.cos(deg2rad(l+renderSteps))*Math.sin(deg2rad(j)));
    				pole[4][1] = (Math.cos(deg2rad(l+renderSteps))*Math.cos(deg2rad(j)));
    				pole[4][2] = Math.sin(deg2rad(l+renderSteps));
    				//2. Dreieck 3. Korrdinate 
    				pole[5][0] = (Math.cos(deg2rad(l+renderSteps))*Math.sin(deg2rad(j+renderSteps)));
    				pole[5][1] = (Math.cos(deg2rad(l+renderSteps))*Math.cos(deg2rad(j+renderSteps)));
    				pole[5][2] = Math.sin(deg2rad(l+renderSteps));
 
    				
    				GL11.glVertex3d(
    						((((ix*pole[0][px]*Math.cos(deg2rad(drehz)))+(iy*pole[0][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(iz*pole[0][pz]*Math.sin(deg2rad(drehy)))), 
    						((((iy*pole[0][py]*Math.cos(deg2rad(drehz)))-(ix*pole[0][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(iz*pole[0][pz]*Math.sin(deg2rad(drehx)))), 
    						((((iz*pole[0][pz]*Math.cos(deg2rad(drehy)))+(ix*pole[0][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(iy*pole[0][py]*Math.sin(deg2rad(drehx))))
    								);
    				GL11.glVertex3d(
    						((((ix*pole[1][px]*Math.cos(deg2rad(drehz)))+(iy*pole[1][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(iz*pole[1][pz]*Math.sin(deg2rad(drehy)))), 
    						((((iy*pole[1][py]*Math.cos(deg2rad(drehz)))-(ix*pole[1][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(iz*pole[1][pz]*Math.sin(deg2rad(drehx)))), 
    						((((iz*pole[1][pz]*Math.cos(deg2rad(drehy)))+(ix*pole[1][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(iy*pole[1][py]*Math.sin(deg2rad(drehx))))
    								);
    				GL11.glVertex3d(
    						((((ix*pole[2][px]*Math.cos(deg2rad(drehz)))+(iy*pole[2][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(iz*pole[2][pz]*Math.sin(deg2rad(drehy)))), 
    						((((iy*pole[2][py]*Math.cos(deg2rad(drehz)))-(ix*pole[2][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(iz*pole[2][pz]*Math.sin(deg2rad(drehx)))), 
    						((((iz*pole[2][pz]*Math.cos(deg2rad(drehy)))+(ix*pole[2][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(iy*pole[2][py]*Math.sin(deg2rad(drehx))))
    								);
    				
    				GL11.glVertex3d(
    						((((ix*pole[3][px]*Math.cos(deg2rad(drehz)))+(iy*pole[3][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(iz*pole[3][pz]*Math.sin(deg2rad(drehy)))), 
    						((((iy*pole[3][py]*Math.cos(deg2rad(drehz)))-(ix*pole[3][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(iz*pole[3][pz]*Math.sin(deg2rad(drehx)))), 
    						((((iz*pole[3][pz]*Math.cos(deg2rad(drehy)))+(ix*pole[3][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(iy*pole[3][py]*Math.sin(deg2rad(drehx))))
    								);
    				GL11.glVertex3d(
    						((((ix*pole[4][px]*Math.cos(deg2rad(drehz)))+(iy*pole[4][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(iz*pole[4][pz]*Math.sin(deg2rad(drehy)))), 
    						((((iy*pole[4][py]*Math.cos(deg2rad(drehz)))-(ix*pole[4][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(iz*pole[4][pz]*Math.sin(deg2rad(drehx)))), 
    						((((iz*pole[4][pz]*Math.cos(deg2rad(drehy)))+(ix*pole[4][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(iy*pole[4][py]*Math.sin(deg2rad(drehx))))
    								);
    				GL11.glVertex3d(
    						((((ix*pole[5][px]*Math.cos(deg2rad(drehz)))+(iy*pole[5][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(iz*pole[5][pz]*Math.sin(deg2rad(drehy)))), 
    						((((iy*pole[5][py]*Math.cos(deg2rad(drehz)))-(ix*pole[5][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(iz*pole[5][pz]*Math.sin(deg2rad(drehx)))), 
    						((((iz*pole[5][pz]*Math.cos(deg2rad(drehy)))+(ix*pole[5][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(iy*pole[5][py]*Math.sin(deg2rad(drehx))))
    								);
    			} 			
       		}
			
		}
		
		
		//Rendern der Zwischen Stücke
		for(int i = 0; i<8; i++){
			drehx = 0;
			drehy = 0;
			drehz = 0;		
			int dn = 1;
			
			//Definiere die Variabeln und die Farbe für jedes Zwischenstück individuell
			switch(i){
			case 0:
				ix = -1; iy = 1; iz = -1;
				px = 0; py = 1; pz = 2;
				dn = 1;
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[0]);
				break;
			case 1:
				ix = -1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;	
				dn = -1;
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[1]);
				break;
			case 2:
				ix = 1; iy = 1; iz = 1;
				px = 0; py = 1; pz = 2;		
				dn = 1;
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[2]);
				break;
			case 3:
				ix = 1; iy = 1; iz = -1;
				px = 0; py = 1; pz = 2;
				dn = -1;
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(y3){
					drehy=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[3]);
				break;
			case 4:
				ix = -1; iy = -1; iz = -1;
				px = 0; py = 1; pz = 2;		
				dn = -1;
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[4]);
				break;
			case 5:
				ix = -1; iy = -1; iz = 1;
				px = 0; py = 1; pz = 2;		
				dn = 1;
				if(mx3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[5]);
				break;
			case 6:
				ix = 1; iy = -1; iz = 1;
				px = 0; py = 1; pz = 2;		
				dn = -1;
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}else
				if(z3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[6]);
				break;
			case 7:
				ix = 1; iy = -1; iz = -1;
				px = 0; py = 1; pz = 2;	
				dn = 1;
				if(x3){
					drehx=k.standRot-rot;
				}else
				if(my3){
					drehy=k.standRot-rot;
				}else
				if(mz3){
					drehz=k.standRot-rot;
				}
				Rendern.setColor(k.con[7]);
				break;	
			}
			drehx*=k.drehRichtung;
			drehy*=k.drehRichtung;
			drehz*=k.drehRichtung;
	
			if(k.getOldStep()>k.getStep()){
				drehx*=-1;
				drehy*=-1;
				drehz*=-1;
			}
			
			drehx*=dn;
			drehy*=dn;
			drehz*=dn;
			
			
			//Rendere Jedes Zwischenstück
			for(int j = 0; j<90; j+=renderStepsZ){
				for(int l = 40 ; l<50; l+=renderStepsZ){
					//Sofern es nicht zu einem Pol gehört
					if((Math.cos(deg2rad(j))*Math.sin(deg2rad(l)))<=Math.sin(deg2rad(50))&&(Math.cos(deg2rad(j-renderStepsZ))*Math.cos(deg2rad(l+renderStepsZ)))<=Math.sin(deg2rad(50))){
						double[][] zwi = new double[6][3];
						
						//1. Dreieck 1. Korrdinate
	    				zwi[0][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
	    				zwi[0][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
	    				zwi[0][2] = Math.sin(deg2rad(l));
	    				//1. Dreieck 2. Korrdinate
	    				zwi[1][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j+renderSteps)));
	    				zwi[1][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j+renderSteps)));
	    				zwi[1][2] = Math.sin(deg2rad(l));
	    				//1. Dreieck 3. Korrdinate
	    				zwi[2][0] = (Math.cos(deg2rad(l+renderSteps))*Math.sin(deg2rad(j+renderSteps)));
	    				zwi[2][1] = (Math.cos(deg2rad(l+renderSteps))*Math.cos(deg2rad(j+renderSteps)));
	    				zwi[2][2] = Math.sin(deg2rad(l+renderSteps));
	    						
	    				//2. Dreieck 1. Korrdinate
	    				zwi[3][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
	    				zwi[3][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
	    				zwi[3][2] = Math.sin(deg2rad(l));
	    				//2. Dreieck 2. Korrdinate
	    				zwi[4][0] = (Math.cos(deg2rad(l+renderSteps))*Math.sin(deg2rad(j)));
	    				zwi[4][1] = (Math.cos(deg2rad(l+renderSteps))*Math.cos(deg2rad(j)));
	    				zwi[4][2] = Math.sin(deg2rad(l+renderSteps));
	    				//2. Dreieck 3. Korrdinate 
	    				zwi[5][0] = (Math.cos(deg2rad(l+renderSteps))*Math.sin(deg2rad(j+renderSteps)));
	    				zwi[5][1] = (Math.cos(deg2rad(l+renderSteps))*Math.cos(deg2rad(j+renderSteps)));
	    				zwi[5][2] = Math.sin(deg2rad(l+renderSteps));
							
	    				
	    				
	    				
	    			
	    	
						for(int m = 0; m<3; m++){
							int x = 0;
							int y = 1;
							int z = 2;
							switch(m){
							case 1:
								x = 1;
								y = 2;
								z = 0;
								break;
							case 2:
								x = 2;
								y = 0;
								z = 1;
								break;
							}
							double turnZwi[][] = new double[6][3];
							turnZwi[0][0] = ((((zwi[0][x]*Math.cos(deg2rad(drehz)))+(zwi[0][y]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[0][z]*Math.sin(deg2rad(drehy))));
							turnZwi[0][1] = ((((zwi[0][y]*Math.cos(deg2rad(drehz)))-(zwi[0][x]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[0][z]*Math.sin(deg2rad(drehx))));
							turnZwi[0][2] = ((((zwi[0][z]*Math.cos(deg2rad(drehy)))+(zwi[0][x]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[0][y]*Math.sin(deg2rad(drehx))));
							
							turnZwi[1][0] = ((((zwi[1][x]*Math.cos(deg2rad(drehz)))+(zwi[1][y]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[1][z]*Math.sin(deg2rad(drehy))));
							turnZwi[1][1] = ((((zwi[1][y]*Math.cos(deg2rad(drehz)))-(zwi[1][x]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[1][z]*Math.sin(deg2rad(drehx))));
							turnZwi[1][2] = ((((zwi[1][z]*Math.cos(deg2rad(drehy)))+(zwi[1][x]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[1][y]*Math.sin(deg2rad(drehx))));
							
							turnZwi[2][0] = ((((zwi[2][x]*Math.cos(deg2rad(drehz)))+(zwi[2][y]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[2][z]*Math.sin(deg2rad(drehy))));
							turnZwi[2][1] = ((((zwi[2][y]*Math.cos(deg2rad(drehz)))-(zwi[2][x]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[2][z]*Math.sin(deg2rad(drehx))));
							turnZwi[2][2] = ((((zwi[2][z]*Math.cos(deg2rad(drehy)))+(zwi[2][x]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[2][y]*Math.sin(deg2rad(drehx))));
							
							turnZwi[3][0] = ((((zwi[3][x]*Math.cos(deg2rad(drehz)))+(zwi[3][y]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[3][z]*Math.sin(deg2rad(drehy))));
							turnZwi[3][1] = ((((zwi[3][y]*Math.cos(deg2rad(drehz)))-(zwi[3][x]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[3][z]*Math.sin(deg2rad(drehx))));
							turnZwi[3][2] = ((((zwi[3][z]*Math.cos(deg2rad(drehy)))+(zwi[3][x]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[3][y]*Math.sin(deg2rad(drehx))));
							
							turnZwi[4][0] = ((((zwi[4][x]*Math.cos(deg2rad(drehz)))+(zwi[4][y]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[4][z]*Math.sin(deg2rad(drehy))));
							turnZwi[4][1] = ((((zwi[4][y]*Math.cos(deg2rad(drehz)))-(zwi[4][x]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[4][z]*Math.sin(deg2rad(drehx))));
							turnZwi[4][2] = ((((zwi[4][z]*Math.cos(deg2rad(drehy)))+(zwi[4][x]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[4][y]*Math.sin(deg2rad(drehx))));
							
							turnZwi[5][0] = ((((zwi[5][x]*Math.cos(deg2rad(drehz)))+(zwi[5][y]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[5][z]*Math.sin(deg2rad(drehy))));
							turnZwi[5][1] = ((((zwi[5][y]*Math.cos(deg2rad(drehz)))-(zwi[5][x]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[5][z]*Math.sin(deg2rad(drehx))));
							turnZwi[5][2] = ((((zwi[5][z]*Math.cos(deg2rad(drehy)))+(zwi[5][x]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[5][y]*Math.sin(deg2rad(drehx))));
							
						
							GL11.glVertex3d(ix*turnZwi[0][px],iy*turnZwi[0][py],iz*turnZwi[0][pz]);
							GL11.glVertex3d(ix*turnZwi[1][px],iy*turnZwi[1][py],iz*turnZwi[1][pz]);
							GL11.glVertex3d(ix*turnZwi[2][px],iy*turnZwi[2][py],iz*turnZwi[2][pz]);
						
							GL11.glVertex3d(ix*turnZwi[3][px],iy*turnZwi[3][py],iz*turnZwi[3][pz]);
							GL11.glVertex3d(ix*turnZwi[4][px],iy*turnZwi[4][py],iz*turnZwi[4][pz]);
							GL11.glVertex3d(ix*turnZwi[5][px],iy*turnZwi[5][py],iz*turnZwi[5][pz]);
							
						}
					}
					
					
					
					
				}
	   		}
			
			
			//Mittleres Feld
			
			
			for(int j = 30; j<50; j+=renderStepsZ){
				for(int l = 20; l<40;l+=renderStepsZ){
					if((Math.cos(deg2rad(j))*Math.sin(deg2rad(l)))<=Math.sin(deg2rad(50))&&(Math.cos(deg2rad(j-renderStepsZ))*Math.cos(deg2rad(l+renderStepsZ)))<=Math.sin(deg2rad(50))){
						double[][] zwi = new double[12][3];
						
						//1. Dreieck 1. Korrdinate
	    				zwi[0][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
	    				zwi[0][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
	    				zwi[0][2] = Math.sin(deg2rad(l));
	    				//1. Dreieck 2. Korrdinate
	    				zwi[1][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j+renderStepsZ)));
	    				zwi[1][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j+renderStepsZ)));
	    				zwi[1][2] = Math.sin(deg2rad(l));
	    				//1. Dreieck 3. Korrdinate
	    				zwi[2][0] = (Math.cos(deg2rad(l+renderStepsZ))*Math.sin(deg2rad(j+renderStepsZ)));
	    				zwi[2][1] = (Math.cos(deg2rad(l+renderStepsZ))*Math.cos(deg2rad(j+renderStepsZ)));
	    				zwi[2][2] = Math.sin(deg2rad(l+renderStepsZ));
	    				
	    				//2. Dreieck 1. Korrdinate
	    				zwi[3][0] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
	    				zwi[3][1] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
	    				zwi[3][2] = Math.sin(deg2rad(l));
	    				//2. Dreieck 2. Korrdinate
	    				zwi[4][0] = (Math.cos(deg2rad(l+renderStepsZ))*Math.sin(deg2rad(j)));
	    				zwi[4][1] = (Math.cos(deg2rad(l+renderStepsZ))*Math.cos(deg2rad(j)));
	    				zwi[4][2] = Math.sin(deg2rad(l+renderStepsZ));
	    				//2. Dreieck 3. Korrdinate 
	    				zwi[5][0] = (Math.cos(deg2rad(l+renderStepsZ))*Math.sin(deg2rad(j+renderStepsZ)));
	    				zwi[5][1] = (Math.cos(deg2rad(l+renderStepsZ))*Math.cos(deg2rad(j+renderStepsZ)));
	    				zwi[5][2] = Math.sin(deg2rad(l+renderStepsZ));
	    				
	    				//2. Dreieck 1. Korrdinate
	    				zwi[6][1] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
	    				zwi[6][2] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
	    				zwi[6][0] = Math.sin(deg2rad(l));
	    				//2. Dreieck 2. Korrdinate
	    				zwi[7][1] = (Math.cos(deg2rad(l+renderStepsZ))*Math.sin(deg2rad(j)));
	    				zwi[7][2] = (Math.cos(deg2rad(l+renderStepsZ))*Math.cos(deg2rad(j)));
	    				zwi[7][0] = Math.sin(deg2rad(l+renderStepsZ));
	    				//2. Dreieck 3. Korrdinate 
	    				zwi[8][1] = (Math.cos(deg2rad(l+renderStepsZ))*Math.sin(deg2rad(j+renderStepsZ)));
	    				zwi[8][2] = (Math.cos(deg2rad(l+renderStepsZ))*Math.cos(deg2rad(j+renderStepsZ)));
	    				zwi[8][0] = Math.sin(deg2rad(l+renderStepsZ));
	    				
	    				//1. Dreieck 1. Korrdinate
	    				zwi[9][1] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j)));
	    				zwi[9][2] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j)));
	    				zwi[9][0] = Math.sin(deg2rad(l));
	    				//1. Dreieck 2. Korrdinate
	    				zwi[10][1] = (Math.cos(deg2rad(l))*Math.sin(deg2rad(j+renderStepsZ)));
	    				zwi[10][2] = (Math.cos(deg2rad(l))*Math.cos(deg2rad(j+renderStepsZ)));
	    				zwi[10][0] = Math.sin(deg2rad(l));
	    				//1. Dreieck 3. Korrdinate
	    				zwi[11][1] = (Math.cos(deg2rad(l+renderStepsZ))*Math.sin(deg2rad(j+renderStepsZ)));
	    				zwi[11][2] = (Math.cos(deg2rad(l+renderStepsZ))*Math.cos(deg2rad(j+renderStepsZ)));
	    				zwi[11][0] = Math.sin(deg2rad(l+renderStepsZ));
	    						
	    				
	    				
	    				double turnZwi[][] = new double[12][3];
	    				
	    				for(int m = 0; m<12; m++){
	    					turnZwi[m][0] = ((((zwi[m][px]*Math.cos(deg2rad(drehz)))+(zwi[m][py]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehy)))-(zwi[m][pz]*Math.sin(deg2rad(drehy))));
		    				turnZwi[m][1] = ((((zwi[m][py]*Math.cos(deg2rad(drehz)))-(zwi[m][px]*Math.sin(deg2rad(drehz))))*Math.cos(deg2rad(drehx)))+(zwi[m][pz]*Math.sin(deg2rad(drehx))));
		    				turnZwi[m][2] = ((((zwi[m][pz]*Math.cos(deg2rad(drehy)))+(zwi[m][px]*Math.sin(deg2rad(drehy))))*Math.cos(deg2rad(drehx)))-(zwi[m][py]*Math.sin(deg2rad(drehx))));
	    				}
	    	
						
						
							GL11.glVertex3d(ix*turnZwi[0][0],iy*turnZwi[0][1],iz*turnZwi[0][2]);
							GL11.glVertex3d(ix*turnZwi[1][0],iy*turnZwi[1][1],iz*turnZwi[1][2]);
							GL11.glVertex3d(ix*turnZwi[2][0],iy*turnZwi[2][1],iz*turnZwi[2][2]);
							GL11.glVertex3d(ix*turnZwi[3][0],iy*turnZwi[3][1],iz*turnZwi[3][2]);
							GL11.glVertex3d(ix*turnZwi[4][0],iy*turnZwi[4][1],iz*turnZwi[4][2]);
							GL11.glVertex3d(ix*turnZwi[5][0],iy*turnZwi[5][1],iz*turnZwi[5][2]);
							GL11.glVertex3d(ix*turnZwi[6][0],iy*turnZwi[6][1],iz*turnZwi[6][2]);
							GL11.glVertex3d(ix*turnZwi[7][0],iy*turnZwi[7][1],iz*turnZwi[7][2]);
							GL11.glVertex3d(ix*turnZwi[8][0],iy*turnZwi[8][1],iz*turnZwi[8][2]);
							GL11.glVertex3d(ix*turnZwi[9][0],iy*turnZwi[9][1],iz*turnZwi[9][2]);
							GL11.glVertex3d(ix*turnZwi[10][0],iy*turnZwi[10][1],iz*turnZwi[10][2]);
							GL11.glVertex3d(ix*turnZwi[11][0],iy*turnZwi[11][1],iz*turnZwi[11][2]);
							
						
	    				
					}
				}
			}
			
		}
		//Beende das Rendern
        GL11.glEnd();
        if(k.standRot>0){
        	k.standRot = k.standRot-rot;
        	//if(k.standRot==89||k.standRot==179)
        	//System.out.println(k.standRot);
        }else{
        	Manager.doQueue = true;
        }
        
        
        //Gib true zurück, wenn hier ankommt, dann alles Erfolgreich
        return true;
    }
}

package marusenkoSphereGUI;

import marusenkoSphereKugel.Kugel;

import org.lwjgl.opengl.GL11;

public class KugelR {

	protected static boolean render(Kugel k,float rtrix, float rtriy, float rtriz) {
    	/**
    	 * Auskommentieren, damit Fl�chen der Kugel nicht gef�llt werden
    	 * 
    	 * Eigentlich zum Debugging beim erstellen gedacht
    	 */
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
    	/**
    	 * L�scht den gesammten Bereich, damit neu gerendert werden kann (inklusive Depth Buffer)
    	 * 
    	 */
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        /**
         * Setze die Position zum Rendern zur�ck 
         */
        GL11.glLoadIdentity();
        
      
        
        /**
         * verschiebe das 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
         */
        GL11.glTranslatef(0.0f,0.0f,-4.0f);
        
        /**
         * Drehe die Kugel gem�ss den Variabeln auf x bzw y Achse
         */
        GL11.glRotatef(rtriy,0.0f,1.0f,0.0f);
        GL11.glRotatef(rtrix,1.0f,0.0f,0.0f);
        GL11.glRotatef(rtriz,0.0f,0.0f,1.0f);
        
 /**
         * Starte das Zeichnen der einzelnen fl�chen der Kugel
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
        	 * Wird f�r jeden der 4 Pole erneut durch gegangen
        	 */
        	for(int i = 0; i<4; i++){
        		/**
        		 * Setze die Negationsvariabelen positiv
        		 */
        		int vx = 1, vy = 1; 
        		
        		/**
        		 * �ndere die Negationsvariabeln gem�ss Pol und setzte die Farbe, mit welcher Gerendert wird
        		 */
        		if(i == 0){vx = 1;vy = 1; KugelRendern.setColor(k.tri[0]);
        		}else if(i == 1){vx = 1;vy = -1; KugelRendern.setColor(k.tri[1]);
            	}else if(i == 2){vx = -1;vy = -1; KugelRendern.setColor(k.tri[2]);
                }else if(i == 3){vx = -1;vy = 1; KugelRendern.setColor(k.tri[3]);	
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
        		if(i == 0){vx = 1;vy = 1; KugelRendern.setColor(k.tri[4]);
        		}else if(i == 1){vx = 1;vy = -1; KugelRendern.setColor(k.tri[5]);
            	}else if(i == 2){vx = -1;vy = -1; KugelRendern.setColor(k.tri[6]);
                }else if(i == 3){vx = -1;vy = 1; KugelRendern.setColor(k.tri[7]);	
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
        		if(i == 0){vx = 1;vy = 1; KugelRendern.setColor(k.tri[8]);
        		}else if(i == 1){vx = 1;vy = -1; KugelRendern.setColor(k.tri[9]);
            	}else if(i == 2){vx = -1;vy = -1; KugelRendern.setColor(k.tri[10]);
                }else if(i == 3){vx = -1;vy = 1; KugelRendern.setColor(k.tri[11]);	
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
        		if(i == 0){vx = 1;vy = 1; KugelRendern.setColor(k.tri[12]);
        		}else if(i == 1){vx = 1;vy = -1; KugelRendern.setColor(k.tri[13]);
            	}else if(i == 2){vx = -1;vy = -1; KugelRendern.setColor(k.tri[14]);
                }else if(i == 3){vx = -1;vy = 1; KugelRendern.setColor(k.tri[15]);	
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
        		if(i == 0){vx = 1;vy = 1; KugelRendern.setColor(k.tri[16]);
        		}else if(i == 1){vx = 1;vy = -1; KugelRendern.setColor(k.tri[17]);
            	}else if(i == 2){vx = -1;vy = -1; KugelRendern.setColor(k.tri[18]);
                }else if(i == 3){vx = -1;vy = 1; KugelRendern.setColor(k.tri[19]);	
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
        		if(i == 0){vx = 1;vy = 1; KugelRendern.setColor(k.tri[20]);
        		}else if(i == 1){vx = 1;vy = -1; KugelRendern.setColor(k.tri[21]);
            	}else if(i == 2){vx = -1;vy = -1; KugelRendern.setColor(k.tri[22]);
                }else if(i == 3){vx = -1;vy = 1; KugelRendern.setColor(k.tri[23]);	
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
        	 * Zeichne die zwischen Fl�chen
        	 * 
        	 */
        	
        	
        	/**
        	 * Zeichne Fl�che 2
        	 */
        	KugelRendern.setColor(k.con[2]); 
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
        	 * Zeichne Fl�che 3
        	 */
        	KugelRendern.setColor(k.con[3]); 
        	   
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
        	 * Zeichne Fl�che 0
        	 */
        	KugelRendern.setColor(k.con[0]); 
        	
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
        	 * Zeichne Fl�che 1
        	 */
        	KugelRendern.setColor(k.con[1]);
        	
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
        	 * Zeichne Fl�che 6
        	 */
        	KugelRendern.setColor(k.con[6]); 
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
        	 * Zeichne Fl�che 7
        	 */
        	KugelRendern.setColor(k.con[7]); 
        	   
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
        	 * Zeichne Fl�che 4
        	 */
        	KugelRendern.setColor(k.con[4]); 
        	
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
        	 * Zeichne Fl�che 5
        	 */
        	KugelRendern.setColor(k.con[5]);
        	
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
         * Rendere die Zwischen St�cke zwischen den Polen
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
         * Gebe true zur�ck, da Rendern ohne Fehler durch ging
         */
        return true;
    }
	protected static boolean render20(Kugel k,float rtrix, float rtriy, float rtriz) {
    	/**
    	 * Auskommentieren, damit Fl�chen der Kugel nicht gef�llt werden
    	 * 
    	 * Eigentlich zum Debugging beim erstellen gedacht
    	 */
    	GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    	
    	/**
    	 * L�scht den gesammten Bereich, damit neu gerendert werden kann (inklusive Depth Buffer)
    	 * 
    	 */
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        /**
         * Setze die Position zum Rendern zur�ck 
         */
        GL11.glLoadIdentity();
        
      
        
        /**
         * verschiebe das 0/0 Position zum Rendern, damit Kugel ganz sichtbar wird
         */
        GL11.glTranslatef(0.0f,0.0f,-4.0f);
        
        /**
         * Drehe die Kugel gem�ss den Variabeln auf x bzw y Achse
         */
        GL11.glRotatef(rtriy,0.0f,1.0f,0.0f);
        GL11.glRotatef(rtrix,1.0f,0.0f,0.0f);
        GL11.glRotatef(rtriz,0.0f,0.0f,1.0f);
        
 /**
         * Starte das Zeichnen der einzelnen fl�chen der Kugel
         */
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        double r = 1.0;
        KugelRendern.setColor(k.con[3]); 
        for(double x = -1.0;x>1.0;x+=0.1){
        	for(double y = -1.0;y>1.0;y+=0.1){
        		for(double z = -1.0;z>1.0;z+=0.1){
        			if(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2)==Math.pow(r,2)){
        				GL11.glVertex3d(x,y,z);
        	        	GL11.glVertex3d(x+0.01,y+0.01,z);
        	        	GL11.glVertex3d(x,y,z+0.01);
        			}
                }
            }
        }
        
        
        GL11.glEnd();
        return true;
    }
}

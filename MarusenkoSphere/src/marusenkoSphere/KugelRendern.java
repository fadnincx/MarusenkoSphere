package marusenkoSphere;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class KugelRendern {
    private final String windowTitle = "MarusenkoSphere";

    private float rtri;                 // Angle For The Triangle ( NEW )
    private float rtrix;                 // Angle For The Triangle ( NEW )
    private DisplayMode displayMode;
    protected Kugel k;

    
    public KugelRendern(Kugel kugel){
    	this.k = kugel;
    }
    public void updateKugel(Kugel kugel){
    	this.k = kugel;
    }
    public void run() {

        try {
            init();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void doing(){
          render();
          Display.update();
    }
    public void end(){
    	cleanup();
    }
    public void drehen(float f, float x){
    	rtri += f;
    	rtrix += x;
    }

    private void setColor(int n){
    	if(n == 0){
    		GL11.glColor3f(1.0f,1.0f,1.0f);
    	}else
    	if(n == 1){
    		GL11.glColor3f(1.0f,1.0f,0.0f);
        }else
        if(n == 2){
        	GL11.glColor3f(1.0f,0.6f,0.0f);	
        }else
        if(n == 3){
        	GL11.glColor3f(0.0f,0.0f,1.0f);    		
        }else
        if(n == 4){
        	GL11.glColor3f(1.0f,0.0f,0.0f);        		
        }else
        if(n == 5){
        	GL11.glColor3f(0.0f,1.0f,1.0f);            		
        }else
        if(n == 6){
        	GL11.glColor3f(1.0f,0.0f,1.0f);                		
        }else
        if(n == 7){
        	GL11.glColor3f(0.0f,1.0f,0.0f);                      		
        }
    }
    private boolean render() {
    	//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer

        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
        GL11.glTranslatef(0.0f,0.0f,-4.0f);             // Move Right 1.5 Units And Into The Screen 6.0
        GL11.glRotatef(rtri,0.0f,1.0f,0.0f);                // Rotate The Triangle On The Y axis ( NEW )
        GL11.glRotatef(rtrix,1.0f,0.0f,0.0f);                 // Rotate The Quad On The X axis ( NEW )
        GL11.glColor3f(1.0f,1.0f,1.0f);                 // Set The Color To Blue One Time Only
        GL11.glBegin(GL11.GL_TRIANGLES);                        // Draw the Sphere
        	  
        
        //0-3
        	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1; setColor(k.tri[0]);
        		}else if(i == 1){vx = 1;vy = -1; setColor(k.tri[1]);
            	}else if(i == 2){vx = -1;vy = -1; setColor(k.tri[2]);
                }else if(i == 3){vx = -1;vy = 1; setColor(k.tri[3]);	
                }
 
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
        	
        //4-7 ==> 2	
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
        	
      	
        //8-11 ==> 3	
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
        	
        //12-15 ==> 4	
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
        	
        
       //16-19     ==>5   	
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
        
        //20-23 ==>6	
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

        	//f[2]
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
        	
        	//f[3]
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
        	
        	//f[0]
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
        	
        	//f[1]
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
  
        	
        	//f[6]
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
        	
        	//f[7]
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
        	
        	//f[4]
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
        	
        	//f[5]
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
        

        GL11.glEnd();                                       // Done Drawing The Sphere
        GL11.glLineWidth((float) 2.0); 
        GL11.glColor3f(0.0f,0.0f,0.0f); 
        GL11.glBegin(GL11.GL_LINES); 

        GL11.glColor3f(0.0f,0.0f,0.0f);  

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
    	/**************************/
    	
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
    	        	
        GL11.glEnd();

        return true;
    }
    private void createWindow() throws Exception {
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640
                && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
       // System.out.println(displayMode);
        try {
            ByteBuffer[] icons = new ByteBuffer[1];
            icons[0] = loadIcon("img/icon_16.png", 16, 16);
           // icons[1] = loadIcon("img/icon_32.png", 32, 32);
            //icons[0] = loadIcon("img/icon_64.png", 64, 64);
            //icons[0] = loadIcon("img/icon_128.png", 128, 128);
            Display.setIcon(icons);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	//DisplayMode displayMode =  new DisplayMode(640, 480);
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        Display.create();
    }
    public ByteBuffer loadIcon(String filename, int width, int height) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename)); // load image

        // convert image to byte array
        byte[] imageBytes = new byte[width * height * 4];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                for (int k = 0; k < 3; k++) // red, green, blue
                    imageBytes[(i*16+j)*4 + k] = (byte)(((pixel>>(2-k)*8))&255);
                imageBytes[(i*16+j)*4 + 3] = (byte)(((pixel>>(3)*8))&255); // alpha
            }
        }
        return ByteBuffer.wrap(imageBytes);
    }
    private void init() throws Exception {
        createWindow();

        initGL();
    }

    private void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading

        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0.8f, 0.8f, 0.8f, 0.0f); // Black Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do

        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix

        // Really Nice Perspective Calculations
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
        rtri=0.0f; 
        rtrix=0.0f;
        
    }
    private static void cleanup() {
        Display.destroy();
        System.exit(0);
    }
}

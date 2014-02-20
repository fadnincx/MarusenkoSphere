package marusenkoSphere;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;

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
    	  //mainloop();
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
    
    
    private void mainloop() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
            end();
        }
        if(Display.isCloseRequested()) {                     // Exit if window is closed
           end();
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {          // Is F1 Being Pressed?
            rtri -= 0.02f;
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {          // Is F1 Being Pressed?
        	rtri += 0.02f;
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_UP)) {          // Is F1 Being Pressed?
            rtrix -= 0.02f;
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {          // Is F1 Being Pressed?
        	rtrix += 0.02f;
        }
      
        
        
        
        
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
        	  
        
        

        	
      /*  	for(int i = 0; i<4; i++){
        		int vx = 1, vy = 1; 
        		if(i == 0){vx = 1;vy = 1;
        		}else if(i == 1){vx = 1;vy = -1;	
            	}else if(i == 2){vx = -1;vy = -1;
                }else if(i == 3){vx = -1;vy = 1;		
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
      
        	for(int i = 0; i>24; i++){
        		GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
            	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
            	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
            	
            	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
            	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
            	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
            	
            	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
            	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
            	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
            	
            	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
            	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
            	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);
        	}
        
        */
        
        	//p[16]
        	setColor(k.p[16]);
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.00f,-0.50f, 0.65f);
        	GL11.glVertex3f( 0.19f,-0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.19f,-0.46f, 0.65f);
        	GL11.glVertex3f( 0.35f,-0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.35f,-0.35f, 0.65f);
        	GL11.glVertex3f( 0.46f,-0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.46f,-0.19f, 0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);

        	//p[15]
        	setColor(k.p[15]);
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.00f,-0.50f, 0.65f);
        	GL11.glVertex3f(-0.19f,-0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.19f,-0.46f, 0.65f);
        	GL11.glVertex3f(-0.35f,-0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.35f,-0.35f, 0.65f);
        	GL11.glVertex3f(-0.46f,-0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.46f,-0.19f, 0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        	
        	//p[7]
        	setColor(k.p[7]);
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        	
        	//p[8]
        	setColor(k.p[8]);
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);

        	//p[10]
        	setColor(k.p[10]);
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);
                                  
        	//p[18]               
        	setColor(k.p[18]);                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f( 0.65f,-0.19f,-0.46f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.19f,-0.46f);
        	GL11.glVertex3f( 0.65f,-0.35f,-0.35f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.35f,-0.35f);
        	GL11.glVertex3f( 0.65f,-0.46f,-0.19f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.46f,-0.19f);
        	GL11.glVertex3f( 0.65f,-0.50f, 0.00f);
        	                      
        	//p[17]                
        	setColor(k.p[17]);                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f( 0.65f,-0.19f, 0.46f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.19f, 0.46f);
        	GL11.glVertex3f( 0.65f,-0.35f, 0.35f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.35f, 0.35f);
        	GL11.glVertex3f( 0.65f,-0.46f, 0.19f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.46f, 0.19f);
        	GL11.glVertex3f( 0.65f,-0.50f, 0.00f);
        	                      
        	//p[9]                
        	setColor(k.p[9]);                  
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	                      
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);

        	//p[5]
        	setColor(k.p[5]);
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
                                 
        	//p[13]               
        	setColor(k.p[13]);                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f(-0.65f,-0.19f,-0.46f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.19f,-0.46f);
        	GL11.glVertex3f(-0.65f,-0.35f,-0.35f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.35f,-0.35f);
        	GL11.glVertex3f(-0.65f,-0.46f,-0.19f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.46f,-0.19f);
        	GL11.glVertex3f(-0.65f,-0.50f, 0.00f);
        	                      
        	//p[14]                
        	setColor(k.p[14]);                     
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f(-0.65f,-0.19f, 0.46f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.19f, 0.46f);
        	GL11.glVertex3f(-0.65f,-0.35f, 0.35f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.35f, 0.35f);
        	GL11.glVertex3f(-0.65f,-0.46f, 0.19f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.46f, 0.19f);
        	GL11.glVertex3f(-0.65f,-0.50f, 0.00f);
        	                      
        	//p[6]                
        	setColor(k.p[6]);                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	                      
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
        	
        	//p[19]
        	setColor(k.p[19]);
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.00f,-0.50f,-0.65f);
        	GL11.glVertex3f( 0.19f,-0.46f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.19f,-0.46f,-0.65f);
        	GL11.glVertex3f( 0.35f,-0.35f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.35f,-0.35f,-0.65f);
        	GL11.glVertex3f( 0.46f,-0.19f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.46f,-0.19f,-0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f,-0.65f);

        	//p[12]
        	setColor(k.p[12]);
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.00f,-0.50f,-0.65f);
        	GL11.glVertex3f(-0.19f,-0.46f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f(-0.19f,-0.46f,-0.65f);
        	GL11.glVertex3f(-0.35f,-0.35f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f(-0.35f,-0.35f,-0.65f);
        	GL11.glVertex3f(-0.46f,-0.19f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f(-0.46f,-0.19f,-0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f,-0.65f);
        	
        	//p[4]
        	setColor(k.p[4]);
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.00f, 0.50f,-0.65f);
        	GL11.glVertex3f(-0.19f, 0.46f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f(-0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f(-0.35f, 0.35f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f(-0.35f, 0.35f,-0.65f);
        	GL11.glVertex3f(-0.46f, 0.19f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f(-0.46f, 0.19f,-0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f,-0.65f);
        	
        	//p[11]
        	setColor(k.p[11]);
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.00f, 0.50f,-0.65f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.19f, 0.46f,-0.65f);
        	GL11.glVertex3f( 0.35f, 0.35f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.35f, 0.35f,-0.65f);
        	GL11.glVertex3f( 0.46f, 0.19f,-0.65f);
        	
        	GL11.glVertex3f( 0.00f, 0.00f,-1.00f);
        	GL11.glVertex3f( 0.46f, 0.19f,-0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f,-0.65f);

        	//p[20]
        	setColor(k.p[20]);
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.50f,-0.65f, 0.00f);
        	GL11.glVertex3f(-0.46f,-0.65f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.46f,-0.65f, 0.19f);
        	GL11.glVertex3f(-0.35f,-0.65f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.35f,-0.65f, 0.35f);
        	GL11.glVertex3f(-0.19f,-0.65f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.19f,-0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f,-0.65f, 0.50f);
                                          
        	//p[21]                       
        	setColor(k.p[21]);                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.50f,-0.65f, 0.00f);
        	GL11.glVertex3f(-0.46f,-0.65f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.46f,-0.65f,-0.19f);
        	GL11.glVertex3f(-0.35f,-0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.35f,-0.65f,-0.35f);
        	GL11.glVertex3f(-0.19f,-0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f(-0.19f,-0.65f,-0.46f);
        	GL11.glVertex3f( 0.00f,-0.65f,-0.50f);
        	                              
        	//p[22]                        
        	setColor(k.p[22]);                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.50f,-0.65f, 0.00f);
        	GL11.glVertex3f( 0.46f,-0.65f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.46f,-0.65f,-0.19f);
        	GL11.glVertex3f( 0.35f,-0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.35f,-0.65f,-0.35f);
        	GL11.glVertex3f( 0.19f,-0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.19f,-0.65f,-0.46f);
        	GL11.glVertex3f( 0.00f,-0.65f,-0.50f);
        	                              
        	//p[23]                        
        	setColor(k.p[23]);                             
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.50f,-0.65f, 0.00f);
        	GL11.glVertex3f( 0.46f,-0.65f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.46f,-0.65f, 0.19f);
        	GL11.glVertex3f( 0.35f,-0.65f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.35f,-0.65f, 0.35f);
        	GL11.glVertex3f( 0.19f,-0.65f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.00f,-1.00f, 0.00f);
        	GL11.glVertex3f( 0.19f,-0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f,-0.65f, 0.50f);
        	
        	
        	
        	
        	
        	
        	
        	//p[0]
        	setColor(k.p[0]);
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f(-0.35f, 0.65f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.35f, 0.65f, 0.35f);
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f, 0.65f, 0.50f);
                                          
        	//p[1]                       
        	setColor(k.p[1]);                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f(-0.35f, 0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.35f, 0.65f,-0.35f);
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f( 0.00f, 0.65f,-0.50f);
        	                              
        	//p[2]                        
        	setColor(k.p[2]);                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f( 0.35f, 0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.35f, 0.65f,-0.35f);
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f( 0.00f, 0.65f,-0.50f);
        	                              
        	//p[3]                        
        	setColor(k.p[3]);                          
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f( 0.35f, 0.65f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.35f, 0.65f, 0.35f);
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f( 0.00f, 0.65f, 0.50f);
        	
        	
        	//f[2]
        	setColor(k.f[2]); 
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
        	setColor(k.f[3]); 
        	   
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
        	setColor(k.f[0]); 
        	
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
        	setColor(k.f[1]);
        	
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
        	setColor(k.f[6]); 
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
        	setColor(k.f[7]); 
        	   
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
        	setColor(k.f[4]); 
        	
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
        	setColor(k.f[5]);
        	
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
        	
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.00f,-0.50f, 0.65f);
        
        	GL11.glVertex3f( 0.00f,-0.50f, 0.65f);
        	GL11.glVertex3f( 0.19f,-0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.19f,-0.46f, 0.65f);
        	GL11.glVertex3f( 0.35f,-0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.35f,-0.35f, 0.65f);
        	GL11.glVertex3f( 0.46f,-0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.46f,-0.19f, 0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);
        
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	
        	/***/
        	
			GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        
        	GL11.glVertex3f( 0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	
        	GL11.glVertex3f( 0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	
        	GL11.glVertex3f( 0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	
        	GL11.glVertex3f( 0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);
        
        	GL11.glVertex3f( 0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f( 0.00f, 0.00f, 1.00f);
        	
        	/***/
        	
			GL11.glVertex3f(-0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.00f, 0.50f, 0.65f);
        
        	GL11.glVertex3f(-0.00f, 0.50f, 0.65f);
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	
        	GL11.glVertex3f(-0.19f, 0.46f, 0.65f);
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	
        	GL11.glVertex3f(-0.35f, 0.35f, 0.65f);
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	
        	GL11.glVertex3f(-0.46f, 0.19f, 0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f(-0.00f, 0.00f, 1.00f);
        	
        	/***/
        	
			GL11.glVertex3f(-0.00f, 0.00f, 1.00f);
        	GL11.glVertex3f(-0.00f,-0.50f, 0.65f);
        
        	GL11.glVertex3f(-0.00f,-0.50f, 0.65f);
        	GL11.glVertex3f(-0.19f,-0.46f, 0.65f);
        	
        	GL11.glVertex3f(-0.19f,-0.46f, 0.65f);
        	GL11.glVertex3f(-0.35f,-0.35f, 0.65f);
        	
        	GL11.glVertex3f(-0.35f,-0.35f, 0.65f);
        	GL11.glVertex3f(-0.46f,-0.19f, 0.65f);
        	
        	GL11.glVertex3f(-0.46f,-0.19f, 0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        
        	GL11.glVertex3f(-0.50f, 0.00f, 0.65f);
        	GL11.glVertex3f(-0.00f, 0.00f, 1.00f);
        	
        
        	/******/
        	
			GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.50f, 0.00f);
                                          
        	GL11.glVertex3f( 0.65f,-0.50f, 0.00f);
        	GL11.glVertex3f( 0.65f,-0.46f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.65f,-0.46f, 0.19f);
        	GL11.glVertex3f( 0.65f,-0.35f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.65f,-0.35f, 0.35f);
        	GL11.glVertex3f( 0.65f,-0.19f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.65f,-0.19f, 0.46f);
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
                                          
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);
                                          
        	GL11.glVertex3f( 0.65f, 0.50f, 0.00f);
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
                                          
        	GL11.glVertex3f( 0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f( 1.00f, 0.00f, 0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f( 1.00f, 0.00f,-0.00f);
        	GL11.glVertex3f( 0.65f, 0.50f,-0.00f);
                                          
        	GL11.glVertex3f( 0.65f, 0.50f,-0.00f);
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
                                          
        	GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f( 1.00f, 0.00f,-0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f( 1.00f, 0.00f,-0.00f);
        	GL11.glVertex3f( 0.65f,-0.50f,-0.00f);
                                          
        	GL11.glVertex3f( 0.65f,-0.50f,-0.00f);
        	GL11.glVertex3f( 0.65f,-0.46f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.65f,-0.46f,-0.19f);
        	GL11.glVertex3f( 0.65f,-0.35f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.65f,-0.35f,-0.35f);
        	GL11.glVertex3f( 0.65f,-0.19f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.65f,-0.19f,-0.46f);
        	GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
                                          
        	GL11.glVertex3f( 0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f( 1.00f, 0.00f,-0.00f);
        	
        	
        	/******/
        	
        	
			GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.50f, 0.00f);
                                          
        	GL11.glVertex3f(-0.65f,-0.50f, 0.00f);
        	GL11.glVertex3f(-0.65f,-0.46f, 0.19f);
        	                              
        	GL11.glVertex3f(-0.65f,-0.46f, 0.19f);
        	GL11.glVertex3f(-0.65f,-0.35f, 0.35f);
        	                              
        	GL11.glVertex3f(-0.65f,-0.35f, 0.35f);
        	GL11.glVertex3f(-0.65f,-0.19f, 0.46f);
        	                              
        	GL11.glVertex3f(-0.65f,-0.19f, 0.46f);
        	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
                                          
        	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
                                          
        	GL11.glVertex3f(-0.65f, 0.50f, 0.00f);
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	                             
        	GL11.glVertex3f(-0.65f, 0.46f, 0.19f);
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	                              
        	GL11.glVertex3f(-0.65f, 0.35f, 0.35f);
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	                              
        	GL11.glVertex3f(-0.65f, 0.19f, 0.46f);
        	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
                                          
        	GL11.glVertex3f(-0.65f, 0.00f, 0.50f);
        	GL11.glVertex3f(-1.00f, 0.00f, 0.00f);
        	                             
        	/***/                         
        	                              
			GL11.glVertex3f(-1.00f, 0.00f,-0.00f);
        	GL11.glVertex3f(-0.65f, 0.50f,-0.00f);
                                          
        	GL11.glVertex3f(-0.65f, 0.50f,-0.00f);
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	                              
        	GL11.glVertex3f(-0.65f, 0.46f,-0.19f);
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	                              
        	GL11.glVertex3f(-0.65f, 0.35f,-0.35f);
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	                              
        	GL11.glVertex3f(-0.65f, 0.19f,-0.46f);
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
                                         
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f(-1.00f, 0.00f,-0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f(-1.00f, 0.00f,-0.00f);
        	GL11.glVertex3f(-0.65f,-0.50f,-0.00f);
                                          
        	GL11.glVertex3f(-0.65f,-0.50f,-0.00f);
        	GL11.glVertex3f(-0.65f,-0.46f,-0.19f);
        	                             
        	GL11.glVertex3f(-0.65f,-0.46f,-0.19f);
        	GL11.glVertex3f(-0.65f,-0.35f,-0.35f);
        	                              
        	GL11.glVertex3f(-0.65f,-0.35f,-0.35f);
        	GL11.glVertex3f(-0.65f,-0.19f,-0.46f);
        	                              
        	GL11.glVertex3f(-0.65f,-0.19f,-0.46f);
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
                                          
        	GL11.glVertex3f(-0.65f, 0.00f,-0.50f);
        	GL11.glVertex3f(-1.00f, 0.00f,-0.00f);
        	
        	
        	/******/
        	
        	
			GL11.glVertex3f( 0.00f, 0.00f, -1.00f);
        	GL11.glVertex3f( 0.00f,-0.50f, -0.65f);
        
        	GL11.glVertex3f( 0.00f,-0.50f, -0.65f);
        	GL11.glVertex3f( 0.19f,-0.46f, -0.65f);
        	
        	GL11.glVertex3f( 0.19f,-0.46f, -0.65f);
        	GL11.glVertex3f( 0.35f,-0.35f, -0.65f);
        	
        	GL11.glVertex3f( 0.35f,-0.35f, -0.65f);
        	GL11.glVertex3f( 0.46f,-0.19f, -0.65f);
        	
        	GL11.glVertex3f( 0.46f,-0.19f, -0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f, -0.65f);
        
        	GL11.glVertex3f( 0.50f, 0.00f, -0.65f);
        	GL11.glVertex3f( 0.00f, 0.00f, -1.00f);
        	
        	/***/
        	
			GL11.glVertex3f( 0.00f, 0.00f, -1.00f);
        	GL11.glVertex3f( 0.00f, 0.50f, -0.65f);
        
        	GL11.glVertex3f( 0.00f, 0.50f, -0.65f);
        	GL11.glVertex3f( 0.19f, 0.46f, -0.65f);
        	
        	GL11.glVertex3f( 0.19f, 0.46f, -0.65f);
        	GL11.glVertex3f( 0.35f, 0.35f, -0.65f);
        	
        	GL11.glVertex3f( 0.35f, 0.35f, -0.65f);
        	GL11.glVertex3f( 0.46f, 0.19f, -0.65f);
        	
        	GL11.glVertex3f( 0.46f, 0.19f, -0.65f);
        	GL11.glVertex3f( 0.50f, 0.00f, -0.65f);
        
        	GL11.glVertex3f( 0.50f, 0.00f, -0.65f);
        	GL11.glVertex3f( 0.00f, 0.00f, -1.00f);
        	
        	/***/
        	
			GL11.glVertex3f(-0.00f, 0.00f, -1.00f);
        	GL11.glVertex3f(-0.00f, 0.50f, -0.65f);
        
        	GL11.glVertex3f(-0.00f, 0.50f, -0.65f);
        	GL11.glVertex3f(-0.19f, 0.46f, -0.65f);
        	
        	GL11.glVertex3f(-0.19f, 0.46f, -0.65f);
        	GL11.glVertex3f(-0.35f, 0.35f, -0.65f);
        	
        	GL11.glVertex3f(-0.35f, 0.35f, -0.65f);
        	GL11.glVertex3f(-0.46f, 0.19f, -0.65f);
        	
        	GL11.glVertex3f(-0.46f, 0.19f, -0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f, -0.65f);
        
        	GL11.glVertex3f(-0.50f, 0.00f, -0.65f);
        	GL11.glVertex3f(-0.00f, 0.00f, -1.00f);
        	
        	/***/
        	
			GL11.glVertex3f(-0.00f, 0.00f, -1.00f);
        	GL11.glVertex3f(-0.00f,-0.50f, -0.65f);
        
        	GL11.glVertex3f(-0.00f,-0.50f, -0.65f);
        	GL11.glVertex3f(-0.19f,-0.46f, -0.65f);
        	
        	GL11.glVertex3f(-0.19f,-0.46f, -0.65f);
        	GL11.glVertex3f(-0.35f,-0.35f, -0.65f);
        	
        	GL11.glVertex3f(-0.35f,-0.35f, -0.65f);
        	GL11.glVertex3f(-0.46f,-0.19f, -0.65f);
        	
        	GL11.glVertex3f(-0.46f,-0.19f, -0.65f);
        	GL11.glVertex3f(-0.50f, 0.00f, -0.65f);
        
        	GL11.glVertex3f(-0.50f, 0.00f, -0.65f);
        	GL11.glVertex3f(-0.00f, 0.00f, -1.00f);
        	
        	
        	/******/
        	
        	
			GL11.glVertex3f( 0.00f, -1.00f, 0.00f);
        	GL11.glVertex3f( 0.00f, -0.65f,-0.50f);
                                           
        	GL11.glVertex3f( 0.00f, -0.65f,-0.50f);
        	GL11.glVertex3f( 0.19f, -0.65f,-0.46f);
        	                               
        	GL11.glVertex3f( 0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f( 0.35f, -0.65f,-0.35f);
        	                               
        	GL11.glVertex3f( 0.35f, -0.65f,-0.35f);
        	GL11.glVertex3f( 0.46f, -0.65f,-0.19f);
        	                               
        	GL11.glVertex3f( 0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f( 0.50f, -0.65f, 0.00f);
                                           
        	GL11.glVertex3f( 0.50f, -0.65f, 0.00f);
        	GL11.glVertex3f( 0.00f, -1.00f, 0.00f);
        	                               
        	/***/                          
        	                               
			GL11.glVertex3f( 0.00f, -1.00f, 0.00f);
        	GL11.glVertex3f( 0.00f, -0.65f, 0.50f);
                                           
        	GL11.glVertex3f( 0.00f, -0.65f, 0.50f);
        	GL11.glVertex3f( 0.19f, -0.65f, 0.46f);
        	                               
        	GL11.glVertex3f( 0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f( 0.35f, -0.65f, 0.35f);
        	                               
        	GL11.glVertex3f( 0.35f, -0.65f, 0.35f);
        	GL11.glVertex3f( 0.46f, -0.65f, 0.19f);
        	                               
        	GL11.glVertex3f( 0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f( 0.50f, -0.65f, 0.00f);
                                           
        	GL11.glVertex3f( 0.50f, -0.65f, 0.00f);
        	GL11.glVertex3f( 0.00f, -1.00f, 0.00f);
        	                               
        	/***/                          
        	                               
			GL11.glVertex3f(-0.00f, -1.00f, 0.00f);
        	GL11.glVertex3f(-0.00f, -0.65f, 0.50f);
                                           
        	GL11.glVertex3f(-0.00f, -0.65f, 0.50f);
        	GL11.glVertex3f(-0.19f, -0.65f, 0.46f);
        	                               
        	GL11.glVertex3f(-0.19f, -0.65f, 0.46f);
        	GL11.glVertex3f(-0.35f, -0.65f, 0.35f);
        	                               
        	GL11.glVertex3f(-0.35f, -0.65f, 0.35f);
        	GL11.glVertex3f(-0.46f, -0.65f, 0.19f);
        	                               
        	GL11.glVertex3f(-0.46f, -0.65f, 0.19f);
        	GL11.glVertex3f(-0.50f, -0.65f, 0.00f);
                                           
        	GL11.glVertex3f(-0.50f, -0.65f, 0.00f);
        	GL11.glVertex3f(-0.00f, -1.00f, 0.00f);
        	                               
        	/***/                          
        	                               
			GL11.glVertex3f(-0.00f, -1.00f, 0.00f);
        	GL11.glVertex3f(-0.00f, -0.65f,-0.50f);
                                           
        	GL11.glVertex3f(-0.00f, -0.65f,-0.50f);
        	GL11.glVertex3f(-0.19f, -0.65f,-0.46f);
        	                               
        	GL11.glVertex3f(-0.19f, -0.65f,-0.46f);
        	GL11.glVertex3f(-0.35f, -0.65f,-0.35f);
        	                               
        	GL11.glVertex3f(-0.35f, -0.65f,-0.35f);
        	GL11.glVertex3f(-0.46f, -0.65f,-0.19f);
        	                               
        	GL11.glVertex3f(-0.46f, -0.65f,-0.19f);
        	GL11.glVertex3f(-0.50f, -0.65f, 0.00f);
                                           
        	GL11.glVertex3f(-0.50f, -0.65f, 0.00f);
        	GL11.glVertex3f(-0.00f, -1.00f, 0.00f);
        	
        	
        	/******/
        	
        	

        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.00f, 0.65f,-0.50f);
                                          
        	GL11.glVertex3f( 0.00f, 0.65f,-0.50f);
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	                              
        	GL11.glVertex3f( 0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f( 0.35f, 0.65f,-0.35f);
        	                              
        	GL11.glVertex3f( 0.35f, 0.65f,-0.35f);
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	                              
        	GL11.glVertex3f( 0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
                                          
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f( 0.00f, 0.65f, 0.50f);
                                          
        	GL11.glVertex3f( 0.00f, 0.65f, 0.50f);
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	                              
        	GL11.glVertex3f( 0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f( 0.35f, 0.65f, 0.35f);
        	                              
        	GL11.glVertex3f( 0.35f, 0.65f, 0.35f);
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	                              
        	GL11.glVertex3f( 0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
                                          
        	GL11.glVertex3f( 0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f( 0.00f, 1.00f, 0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f(-0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.00f, 0.65f, 0.50f);
                                          
        	GL11.glVertex3f(-0.00f, 0.65f, 0.50f);
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	                              
        	GL11.glVertex3f(-0.19f, 0.65f, 0.46f);
        	GL11.glVertex3f(-0.35f, 0.65f, 0.35f);
        	                              
        	GL11.glVertex3f(-0.35f, 0.65f, 0.35f);
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	                              
        	GL11.glVertex3f(-0.46f, 0.65f, 0.19f);
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
                                          
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f(-0.00f, 1.00f, 0.00f);
        	                              
        	/***/                         
        	                              
			GL11.glVertex3f(-0.00f, 1.00f, 0.00f);
        	GL11.glVertex3f(-0.00f, 0.65f,-0.50f);
                                          
        	GL11.glVertex3f(-0.00f, 0.65f,-0.50f);
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	                              
        	GL11.glVertex3f(-0.19f, 0.65f,-0.46f);
        	GL11.glVertex3f(-0.35f, 0.65f,-0.35f);
        	                              
        	GL11.glVertex3f(-0.35f, 0.65f,-0.35f);
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	                              
        	GL11.glVertex3f(-0.46f, 0.65f,-0.19f);
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
                                          
        	GL11.glVertex3f(-0.50f, 0.65f, 0.00f);
        	GL11.glVertex3f(-0.00f, 1.00f, 0.00f);
        	
        
        	
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
        
        
        
        
        
        
        

        //rtri+=0.02f;                                     // Increase The Rotation Variable For The Triangle ( NEW )
      //  rquad-=0.015f;                                   // Decrease The Rotation Variable For The Quad     ( NEW )
        return true;
    }
    private void createWindow() throws Exception {
        DisplayMode d[] = Display.getAvailableDisplayModes();
      //  System.out.println(d[0]);
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640
                && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        System.out.println(displayMode);
        
    	DisplayMode displayMode =  new DisplayMode(640, 480);
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        Display.create();
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

package marusenkoSphere;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawSphere extends JPanel{
protected Kugel k = new Kugel();
protected double rotation_x, rotation_y ,rotation_z; //drehung um ensprechende Achse;
protected int p_viewer_x, p_viewer_y, p_viewer_z;
protected int distanz_user;

protected int[][][] sphere = new int [32][6][3];//  Welches Feld / welcher Punkt / x ,y ,z
protected int[][][] poles = new int[24][2][4];
protected int[][][] forms = new int [2][15][3];//  Welche Form / welcher Punkt / x ,y ,z
	
	public DrawSphere(Kugel ki){
		this.k = ki; 
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		defineSphere();
		Color[] color = new Color[8];
		color[0] = new Color(255, 0, 255); 
		color[1] = new Color(255, 255, 0); 
		color[2] = new Color(255, 150, 0); 
		color[3] = new Color(0, 0, 255); 
		color[4] = new Color(255, 0, 0); 
		color[5] = new Color(0, 255, 255); 
		color[6] = new Color(255, 0, 255); 
		color[7] = new Color(0, 255, 0); 

		p_viewer_x = 400;
		p_viewer_y = 0;
		p_viewer_z = 400;
		distanz_user = 100;
		
		double rot_x = 0;
		double rot_y = 30;
		double rot_z = 0;
		
		for(int i = 3; i<4; i++){
						
			Polygon	p = new Polygon();
			
			for(int j = 0; j<6; j++){
			
				
				double x = sphere[i][j][1];
				double y = sphere[i][j][2]; 
				double z = sphere[i][j][0];
				
				System.out.println(x+" / "+y+" / "+z);
				//Rot x
				y = y * Math.cos(rot_x) - z * Math.sin(rot_x);
				z = y * Math.sin(rot_x) + z * Math.cos(rot_x);
								
				//Rot y
				z = z * Math.cos(rot_y) - x * Math.sin(rot_y);
				x = z * Math.sin(rot_y) + x * Math.cos(rot_y);		
				
				//Rot z
				x = x * Math.cos(rot_z) - y * Math.sin(rot_z);
				y = x * Math.sin(rot_z) + y * Math.cos(rot_z);
				
				
				/*x *= 50;
				y *= 50;
				z *= 50;
				
				/*
				double distanz_u_x = p_viewer_x - x; //Distanz zu User
				double distanz_u_y = p_viewer_y - y;
				double distanz_u_z = p_viewer_z - z;
				double distanz_u = Math.sqrt(Math.pow(distanz_u_x,2)+Math.pow(distanz_u_y,2)+Math.pow(distanz_u_z,2));*/
				
				
				
			//	double distanz_screen = distanz_u - distanz_user; 
				
				
				p.addPoint((int) (x/z)+200,(int) (y/z)+200);
				System.out.println(x+" / "+y+" / "+z);
				System.out.println(((x/z)+200)+" / "+((y/z)+200));
			}
			
			
			g.setColor(color[k.p[1]]);
			g.fillPolygon(p);
		}
		
				
	}
	public void defineSphere(){
		
		
		/**
		 * Ursprung 0/0/0 ist in der Mitte der Kugel!
		 * 
		 *  Kugel hat einen Radius von 100
		 *  
		 *  
		 */
		//p[0]
		sphere[0][0][0] = 0;	sphere[0][0][1] = 0;	sphere[0][0][2] = 100;
		sphere[0][1][0] = 0;	sphere[0][1][1] = -50;	sphere[0][1][2] = 65;
		sphere[0][2][0] = 19;	sphere[0][2][1] = -46;	sphere[0][2][2] = 65;
		sphere[0][3][0] = 35;	sphere[0][3][1] = -35;	sphere[0][3][2] = 65;
		sphere[0][4][0] = 46;	sphere[0][4][1] = -19;	sphere[0][4][2] = 65;
		sphere[0][5][0] = 50;	sphere[0][5][1] = 0;	sphere[0][5][2] = 65;
		
		//p[1]
		sphere[1][0][0] = 0;	sphere[1][0][1] = 0;	sphere[1][0][2] = 100;
		sphere[1][1][0] = 0;	sphere[1][1][1] = -50;	sphere[1][1][2] = 65;
		sphere[1][2][0] = -19;	sphere[1][2][1] = -46;	sphere[1][2][2] = 65;
		sphere[1][3][0] = -35;	sphere[1][3][1] = -35;	sphere[1][3][2] = 65;
		sphere[1][4][0] = -46;	sphere[1][4][1] = -19;	sphere[1][4][2] = 65;
		sphere[1][5][0] = -50;	sphere[1][5][1] = 0;	sphere[1][5][2] = 65;
		
		//p[2]
		sphere[2][0][0] = 0;	sphere[2][0][1] = 0;	sphere[2][0][2] = 100;
		sphere[2][1][0] = 0;	sphere[2][1][1] = 50;	sphere[2][1][2] = 65;
		sphere[2][2][0] = -19;	sphere[2][2][1] = 46;	sphere[2][2][2] = 65;
		sphere[2][3][0] = -35;	sphere[2][3][1] = 35;	sphere[2][3][2] = 65;
		sphere[2][4][0] = -46;	sphere[2][4][1] = 19;	sphere[2][4][2] = 65;
		sphere[2][5][0] = -50;	sphere[2][5][1] = 0;	sphere[2][5][2] = 65;
		
		//p[3]
		sphere[3][0][0] = 0;	sphere[3][0][1] = 0;	sphere[3][0][2] = 0;
		sphere[3][1][0] = 100;	sphere[3][1][1] = 0;	sphere[3][1][2] = 0;
		sphere[3][2][0] = 100;	sphere[3][2][1] = 100;	sphere[3][2][2] = 0;
		sphere[3][3][0] = 0;	sphere[3][3][1] = 100;	sphere[3][3][2] = 0;
		sphere[3][4][0] = 0;	sphere[3][4][1] = 0;	sphere[3][4][2] = 0;
		sphere[3][5][0] = 0;	sphere[3][5][1] = 0;	sphere[3][5][2] = 0;
		
		
		
		
		
		
		
	}
}

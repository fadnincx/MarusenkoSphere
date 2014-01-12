package marusenkoSphere;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;



import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Draw extends JPanel{

	protected Kugel k = new Kugel();
	
	public Draw(Kugel ki){
		this.k = ki; 
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Polygon p0 = new Polygon(),
				p1 = new Polygon(),
				p2 = new Polygon(),
				p3 = new Polygon(),
				p4 = new Polygon(),
				p5 = new Polygon(),
				p6 = new Polygon(),
				p7 = new Polygon(),
				p8 = new Polygon(),
				p9 = new Polygon(),
				p10 = new Polygon(),
				p11 = new Polygon(),
				p12 = new Polygon(),
				p13 = new Polygon(),
				p14 = new Polygon(),
				p15 = new Polygon(),
				p16 = new Polygon(),
				p17 = new Polygon(),
				p18 = new Polygon(),
				p19 = new Polygon(),
				p20 = new Polygon(),
				p21 = new Polygon(),
				p22 = new Polygon(),
				p23 = new Polygon();
		Polygon f0 = new Polygon(),
				f1 = new Polygon(),
				f2 = new Polygon(),
				f3 = new Polygon(),
				f4 = new Polygon(),
				f5 = new Polygon(),
				f6 = new Polygon(),
				f7 = new Polygon();
		
		Color[] color = new Color[8];
		color[0] = new Color(255, 255, 255); 
		color[1] = new Color(255, 255, 0); 
		color[2] = new Color(255, 150, 0); 
		color[3] = new Color(0, 0, 255); 
		color[4] = new Color(255, 0, 0); 
		color[5] = new Color(0, 255, 255); 
		color[6] = new Color(255, 0, 255); 
		color[7] = new Color(0, 255, 0); 
		
		
		//Polynome Definieren
		
		//p[0]
		p0.addPoint(100,200);
		p0.addPoint(200,100);
		p0.addPoint(300,200);
		g.setColor(color[k.p[0]]);
		g.fillPolygon(p0);
		
		
		//p[1]
		p1.addPoint(300,200);
		p1.addPoint(400,100);
		p1.addPoint(500,200);
		g.setColor(color[k.p[1]]);
		g.fillPolygon(p1);
	
		//p[2]
		p2.addPoint(500,200);
		p2.addPoint(600,100);
		p2.addPoint(700,200);
		g.setColor(color[k.p[2]]);
		g.fillPolygon(p2);
		
		//p[3]
		p3.addPoint(700,200);
		p3.addPoint(800,100);
		p3.addPoint(900,200);
		g.setColor(color[k.p[3]]);
		g.fillPolygon(p3);

		//p[4]
		p4.addPoint(100,200);
		p4.addPoint(100,300);
		p4.addPoint(200,300);
		g.setColor(color[k.p[4]]);
		g.fillPolygon(p4);

		//p[5]
		p5.addPoint(200,300);
		p5.addPoint(300,300);
		p5.addPoint(300,200);
		g.setColor(color[k.p[5]]);
		g.fillPolygon(p5);

		//p[6]
		p6.addPoint(300,200);
		p6.addPoint(300,300);
		p6.addPoint(400,300);
		g.setColor(color[k.p[6]]);
		g.fillPolygon(p6);

		//p[7]
		p7.addPoint(400,300);
		p7.addPoint(500,300);
		p7.addPoint(500,200);
		g.setColor(color[k.p[7]]);
		g.fillPolygon(p7);
		
		//p[8]
		p8.addPoint(500,200);
		p8.addPoint(500,300);
		p8.addPoint(600,300);
		g.setColor(color[k.p[8]]);
		g.fillPolygon(p8);
		
		//p[9]
		p9.addPoint(600,300);
		p9.addPoint(700,300);
		p9.addPoint(700,200);
		g.setColor(color[k.p[9]]);
		g.fillPolygon(p9);

		//p[10]
		p10.addPoint(700,200);
		p10.addPoint(700,300);
		p10.addPoint(800,300);
		g.setColor(color[k.p[10]]);
		g.fillPolygon(p10);
		
		//p[11]
		p11.addPoint(800,300);
		p11.addPoint(900,300);
		p11.addPoint(900,200);
		g.setColor(color[k.p[11]]);
		g.fillPolygon(p11);

		//p[12]
		p12.addPoint(100,300);
		p12.addPoint(100,400);
		p12.addPoint(200,300);
		g.setColor(color[k.p[12]]);
		g.fillPolygon(p12);

		//p[13]
		p13.addPoint(200,300);
		p13.addPoint(300,400);
		p13.addPoint(300,300);
		g.setColor(color[k.p[13]]);
		g.fillPolygon(p13);

		//p[14]
		p14.addPoint(300,300);
		p14.addPoint(300,400);
		p14.addPoint(400,300);
		g.setColor(color[k.p[14]]);
		g.fillPolygon(p14);
		
		//p[15]
		p15.addPoint(400,300);
		p15.addPoint(500,400);
		p15.addPoint(500,300);
		g.setColor(color[k.p[15]]);
		g.fillPolygon(p15);

		//p[16]
		p16.addPoint(500,300);
		p16.addPoint(500,400);
		p16.addPoint(600,300);
		g.setColor(color[k.p[16]]);
		g.fillPolygon(p16);

		//p[17]
		p17.addPoint(600,300);
		p17.addPoint(700,400);
		p17.addPoint(700,300);
		g.setColor(color[k.p[17]]);
		g.fillPolygon(p17);

		//p[18]
		p18.addPoint(700,300);
		p18.addPoint(700,400);
		p18.addPoint(800,300);
		g.setColor(color[k.p[18]]);
		g.fillPolygon(p18);

		//p[19]
		p19.addPoint(800,300);
		p19.addPoint(900,400);
		p19.addPoint(900,300);
		g.setColor(color[k.p[19]]);
		g.fillPolygon(p19);

		//p[20]
		p20.addPoint(100,400);
		p20.addPoint(200,500);
		p20.addPoint(300,400);
		g.setColor(color[k.p[20]]);
		g.fillPolygon(p20);

		//p[21]
		p21.addPoint(300,400);
		p21.addPoint(400,500);
		p21.addPoint(500,400);
		g.setColor(color[k.p[21]]);
		g.fillPolygon(p21);

		//p[22]
		p22.addPoint(500,400);
		p22.addPoint(600,500);
		p22.addPoint(700,400);
		g.setColor(color[k.p[22]]);
		g.fillPolygon(p22);

		//p[23]
		p23.addPoint(700,400);
		p23.addPoint(800,500);
		p23.addPoint(900,400);
		g.setColor(color[k.p[23]]);
		g.fillPolygon(p23);
		
		//f[0]
		f0.addPoint(100,200);
		f0.addPoint(200,300);
		f0.addPoint(300,200);
		g.setColor(color[k.f[0]]);
		g.fillPolygon(f0);
		
		//f[1]
		f1.addPoint(300,200);
		f1.addPoint(400,300);
		f1.addPoint(500,200);
		g.setColor(color[k.f[1]]);
		g.fillPolygon(f1);

		//f[2]
		f2.addPoint(500,200);
		f2.addPoint(600,300);
		f2.addPoint(700,200);
		g.setColor(color[k.f[2]]);
		g.fillPolygon(f2);

		//f[3]
		f3.addPoint(700,200);
		f3.addPoint(800,300);
		f3.addPoint(900,200);
		g.setColor(color[k.f[3]]);
		g.fillPolygon(f3);

		//f[4]
		f4.addPoint(100,400);
		f4.addPoint(200,300);
		f4.addPoint(300,400);
		g.setColor(color[k.f[4]]);
		g.fillPolygon(f4);

		//f[5]
		f5.addPoint(300,400);
		f5.addPoint(400,300);
		f5.addPoint(500,400);
		g.setColor(color[k.f[5]]);
		g.fillPolygon(f5);

		//f[6]
		f6.addPoint(500,400);
		f6.addPoint(600,300);
		f6.addPoint(700,400);
		g.setColor(color[k.f[6]]);
		g.fillPolygon(f6);

		//f[7]
		f7.addPoint(700,400);
		f7.addPoint(800,300);
		f7.addPoint(900,400);
		g.setColor(color[k.f[7]]);
		g.fillPolygon(f7);

		g.setColor(Color.black);
		
	
		g.drawPolygon(p0);
		g.drawPolygon(p1);
		g.drawPolygon(p2);
		g.drawPolygon(p3);
		g.drawPolygon(p4);
		g.drawPolygon(p5);
		g.drawPolygon(p6);
		g.drawPolygon(p7);
		g.drawPolygon(p8);
		g.drawPolygon(p9);
		g.drawPolygon(p10);
		g.drawPolygon(p11);
		g.drawPolygon(p12);
		g.drawPolygon(p13);
		g.drawPolygon(p14);
		g.drawPolygon(p15);
		g.drawPolygon(p16);
		g.drawPolygon(p17);
		g.drawPolygon(p18);
		g.drawPolygon(p19);
		g.drawPolygon(p20);
		g.drawPolygon(p21);
		g.drawPolygon(p22);
		g.drawPolygon(p23);
		
		
	   
	}
}

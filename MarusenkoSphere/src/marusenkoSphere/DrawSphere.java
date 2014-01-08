package marusenkoSphere;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawSphere extends JPanel{
protected Kugel k = new Kugel();
	
	public DrawSphere(Kugel ki){
		this.k = ki; 
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
	}
	
}

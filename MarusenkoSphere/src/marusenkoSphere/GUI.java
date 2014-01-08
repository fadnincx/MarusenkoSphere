package marusenkoSphere;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;


public class GUI {
	protected JFrame gui = new JFrame("Marusenko");
	protected  Container ContentPane;
	public GUI(Kugel k){
		createFrame(k);
	}
	protected void createFrame(Kugel k){
		gui.setSize(1200, 600);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ContentPane = gui.getContentPane();
        ContentPane.add(new Draw(k));
       // drawKugleToFrame();
        
        
        //gui.setContentPane(new BackgroundPanel("img/backgroundFarbe.jpg"));
 
        
       gui.addMouseListener(new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent e) {
        	   mouseIsPressed(e);
           }
        });
        
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        gui.setResizable(true);
        gui.setLayout(null);
	}
	
	protected void drawKugleToFrame(Kugel k){
		
		//ContentPane.add(new Draw(k));
		ContentPane.repaint();
		gui.setVisible(true);
	}
	
	private void mouseIsPressed(MouseEvent e){
		int mouse_x = e.getXOnScreen();
		int mouse_y = e.getYOnScreen();
		
		System.out.println(mouse_x+ " "+mouse_y);
		
		
		if(mouse_x>100&&mouse_x<900&&mouse_y>100&&mouse_y<500){//in Haupt bereich
			if(mouse_y<200){//Oberste Reihe
				if(mouse_x<300){
					//p[0]
					System.out.println("p[0]");
				}else
				if(mouse_x<500){
					//p[1]
					System.out.println("p[1]");
				}else
				if(mouse_x<700){
					//p[2]
					System.out.println("p[2]");
				}else{
					//p[3]
					System.out.println("p[3]");
				}
			}else
			if(mouse_y<300){//Obere Reihe
				System.out.println("Oben");
			}else
			if(mouse_y<400){//Untere Reihe
				System.out.println("Unten");
			}else{//Unterste Reihe
				System.out.println("Unterst");
			}
			
		}else{
			System.out.println("Out");
		}
		
	}
	
	
	
	//@SuppressWarnings("serial")
	/*class BackgroundPanel extends JPanel {

		Image img = null;
		 
        BackgroundPanel(String imagefile) {
            if (imagefile != null) {
                MediaTracker mt = new MediaTracker(this);
                img = Toolkit.getDefaultToolkit().getImage(imagefile);
                mt.addImage(img, 0);
                try {
                    mt.waitForAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
 
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
        }
	 
	}*/
}

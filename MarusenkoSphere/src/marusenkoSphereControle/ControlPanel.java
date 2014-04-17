package marusenkoSphereControle;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import marusenkoSphere.Manager;

public class ControlPanel implements ActionListener{

	protected Manager m;
	protected JFrame controlp = new JFrame("MarusenkoSphere Controlpanel");
	protected JButton bt_fillSphere = new JButton("Kugel füllen");
	protected JButton bt_solveSphere = new JButton("Kugel lösen");
	protected TextArea txt_kugelInput = new TextArea();
	protected JButton bt_kugel_uebernehmen = new JButton("Kugel übernehmen");
	
	public ControlPanel(Manager m){
		this.m = m;
		createFrame();
	}
	protected void createFrame(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		final int WIDTH = screenSize.width;
		final int HEIGHT = screenSize.height;
		int x=(WIDTH/2)+337;
		int y=(HEIGHT/2)-240;
		controlp.setSize(350, 510);
		controlp.setVisible(true);
		controlp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlp.setLocation(x,y);
		controlp.setResizable(false);
		controlp.setLayout(null);

        display();
	}
	protected void display(){
		bt_fillSphere.setBounds(20, 20, 310, 50);
    	bt_solveSphere.setBounds(20, 90,310, 50);
    	txt_kugelInput.setBounds(20,160,310, 160);
    	bt_kugel_uebernehmen.setBounds(20,340, 310, 50);

    	controlp.add(bt_fillSphere);
    	controlp.add(bt_solveSphere);
    	controlp.add(txt_kugelInput);
    	controlp.add(bt_kugel_uebernehmen);
       
    	bt_fillSphere.addActionListener(this);
        bt_solveSphere.addActionListener(this);
        bt_kugel_uebernehmen.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent z) {
		if (z.getSource() == bt_fillSphere){
			m.fillSphere();
        }else if (z.getSource() == bt_solveSphere){
        	m.startSolve();
        }else if (z.getSource() == bt_kugel_uebernehmen){
        	String in = txt_kugelInput.getText();
        	m.fillSphere(in);
        }
		
	}

}

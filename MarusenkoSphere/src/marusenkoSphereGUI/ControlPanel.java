package marusenkoSphereGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ControlPanel implements ActionListener{

	protected Manager m;
	protected JFrame controlp = new JFrame("MarusenkoSphere Controlpanel");
	protected JButton bt_fillSphere = new JButton("neu mischen");
	protected JButton bt_solveSphere = new JButton("lösen");
	protected TextArea txt_kugelInput = new TextArea();
	protected JButton bt_kugel_uebernehmen = new JButton("Kugel übernehmen");
	protected JButton bt_up = new JButton(new ImageIcon(new ImageIcon("img/up.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	protected JButton bt_right = new JButton(new ImageIcon(new ImageIcon("img/right.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	protected JButton bt_left = new JButton(new ImageIcon(new ImageIcon("img/left.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));;
	protected JButton bt_down = new JButton(new ImageIcon(new ImageIcon("img/down.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	protected JButton bt_reset_view = new JButton("Reset");
	protected JButton bt_oneStep = new JButton("+1Schritt");
	protected JButton bt_backStep = new JButton("-1Schritt");
	protected JLabel lbl_aktuell_anz = new JLabel("Anzahl Schritte:");
	protected JLabel lbl_max_anz = new JLabel("Gelöst bei Schritte:");
	protected TextArea txt_goPos = new TextArea();
	protected JButton bt_goPos = new JButton("Go to");
	
	protected int x;
	protected int y;
	
	public ControlPanel(Manager m){
		this.m = m;
		
		createFrame();
	}
	protected void createFrame(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		final int WIDTH = screenSize.width;
		final int HEIGHT = screenSize.height;
		x=(WIDTH/2)+337;
		y=(HEIGHT/2)-240;
		//System.out.println("X: "+x);
		//System.out.println("Y: "+y);
		controlp.setSize(350, 510);
		controlp.setVisible(true);
		controlp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlp.setLocation(x,y);
		controlp.setBounds(x, y, 350, 510);
		controlp.setResizable(false);
		controlp.setLayout(null);

        display();
	}
	protected void display(){
		bt_fillSphere.setBounds(20, 20, 145, 50);
    	bt_solveSphere.setBounds(185, 20,145, 50);
    	txt_kugelInput.setBounds(20,80,310, 40);
    	bt_kugel_uebernehmen.setBounds(20,130, 310, 50);
    	bt_up.setBounds(150,300,50,50);
    	bt_left.setBounds(90,360,50,50);
    	bt_right.setBounds(210,360,50,50);
    	bt_down.setBounds(150,360,50,50);
    	bt_reset_view.setBounds(250,300,80,50);
    	bt_backStep.setBounds(20,420,145,50);
    	bt_oneStep.setBounds(185,420,145,50);
    	lbl_aktuell_anz.setBounds(20,250,200,20);
    	lbl_max_anz.setBounds(20,270,200,20);
    	txt_goPos.setBounds(20, 190, 145, 50);
    	bt_goPos.setBounds(185, 190, 145, 50);
    	
    	
    	controlp.add(bt_fillSphere);
    	controlp.add(bt_solveSphere);
    	controlp.add(txt_kugelInput);
    	controlp.add(bt_kugel_uebernehmen);
    	controlp.add(bt_up);
    	controlp.add(bt_down);
    	controlp.add(bt_left);
    	controlp.add(bt_right);
    	controlp.add(bt_reset_view);
    	controlp.add(bt_oneStep);
    	controlp.add(bt_backStep);
    	controlp.add(lbl_aktuell_anz);
    	controlp.add(lbl_max_anz);
    	controlp.add(txt_goPos);
    	controlp.add(bt_goPos);
       
    	bt_fillSphere.addActionListener(this);
        bt_solveSphere.addActionListener(this);
        bt_kugel_uebernehmen.addActionListener(this);
        bt_up.addActionListener(this);
        bt_right.addActionListener(this);
        bt_left.addActionListener(this);
        bt_down.addActionListener(this);
        bt_reset_view.addActionListener(this);
        bt_oneStep.addActionListener(this);
        bt_backStep.addActionListener(this);
        bt_goPos.addActionListener(this);
        
        
	}
	public void updateKugelState(int anzAktuell, int anzMax){
		lbl_aktuell_anz.setText("Anzahl Schritte: "+anzAktuell);
		lbl_max_anz.setText("Gelöst bei Schritte: "+anzMax);
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
        }else if (z.getSource() == bt_up){
        	m.kr.drehen(0.0f,-0.2f);
        }else if (z.getSource() == bt_right){
        	m.kr.drehen(+0.2f,0.0f);
        }else if (z.getSource() == bt_left){
        	m.kr.drehen(-0.2f,0.0f);
        }else if (z.getSource() == bt_down){
        	m.kr.drehen(0.0f,+0.2f);
        }else if (z.getSource() == bt_reset_view){
        	m.kr.setDrehen(0.0f,0.0f,0.0f);
        }else if (z.getSource() == bt_oneStep){
        	m.oneStep();
        }else if (z.getSource() == bt_backStep){
        	m.backStep();
        }else if (z.getSource() == bt_goPos){
        	m.setPos(txt_goPos.getText());
        }
		
	}
}

package marusenkoSphereGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Diese Klasse ist für die Darstellung des 2. Fensters, des Kontolpanels zuständig
 * 
 * Zusätzlich ist ein ActionListener implementiert um direkt auf die Aktionen einzugehen
 */

public class ControlPanel implements ActionListener{

	//Manager um die Kugel vom Panel aus zu Steuern
	private Manager m;
	
	//Eigentliches Fenster
	private JFrame controlp = new JFrame("MarusenkoSphere Controlpanel");
	
	//Buttons zum neu mischen, bzw lösen der Kugel
	private JButton bt_fillSphere = new JButton("neu mischen");
	private JButton bt_solveSphere = new JButton("lösen");
	
	//Debugging Feld für eingabe von Kugel inklusive Bestätigungs Button
	private JTextField txt_kugelInput = new JTextField();
	private JButton bt_kugel_uebernehmen = new JButton("Kugel Übernehmen");
	
	//Pfeile zum drehen der Kugel sowie Zurücksetzt Button
	private JButton bt_up = new JButton();
	private JButton bt_right = new JButton();
	private JButton bt_left = new JButton();
	private JButton bt_down = new JButton();
	//new ImageIcon(new ImageIcon("/img/down.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))
	
	private JButton bt_reset_view = new JButton("Reset");
	
	//Schritte vorwärts und zurück gehen
	private JButton bt_oneStep = new JButton("+1 Schritt");
	private JButton bt_backStep = new JButton("-1 Schritt");
	
	//Text um Infos darzustellen
	private JLabel lbl_aktuell_anz = new JLabel("Anzahl Schritte:");
	private JLabel lbl_max_anz = new JLabel("Gelöst bei Schritte:");
	
	//Textfeld um zu Position zu gehen inklusie Bestätigungs Button
	private JTextField txt_goPos = new JTextField();
	private JButton bt_goPos = new JButton("Go to");
	
	//Verschiebung des Fensters, so dass gerade neben dem Hauptfenster dargestellt wird
	private int x;
	private int y;
	
	/**
	 * Konstruktor welcher das Manager-Objekt initialisiert und ein neues Fenster erstellen lässt
	 * @param m : Manager
	 */
	public ControlPanel(Manager m){
		//Initialisiere das Manager-Objekt
		this.m = m;
		
		//Erstelle neues Fenster
		createFrame();
	}
	
	/**
	 * Funktion zum Erstellen eines Neuen Fensters
	 */
	private void createFrame(){
		//Neues Toolkit Objekt erstellen, wird gebraucht um an die Bildschirmauflösung zu kommen
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		
		//Schreibe die Bildschirmauflösung in final int's 
		final int WIDTH = screenSize.width;
		final int HEIGHT = screenSize.height;
		
		//Berechne die Verschiebung des Fensters
		x=(WIDTH/2)+337;
		y=(HEIGHT/2)-240;
		
		//Setzte die Grösse des Controlpanels
		controlp.setSize(350, 510);
		
		//Setzet es sichtbar
		controlp.setVisible(true);
		
		//Definiere was beim Schliessen des Fenster passieren soll --> Java beenden
		controlp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setzte die Position des Fensters
		controlp.setLocation(x,y);
		
		//Setzte, dass Fenster grösse nicht veränderbar durch Benutzer
		controlp.setResizable(false);
		
		//Setzte Layout = null
		controlp.setLayout(null);

		
		//Rufe die Funktion display auf um die Objekte dem eben erstellten Objekt hinzuzufügen
        display();
	}
	
	/**
	 * Funktion zum Alle Objekte dem Fenster hinzuzufügen
	 */
	private void display(){
		
		//Lade die Bilder Für die Pfeiltasten und füge sie den Buttons hinzu
		try {
			ImageIcon imageUp = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/up.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			ImageIcon imageDown = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/down.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			ImageIcon imageRight = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/right.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			ImageIcon imageLeft = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/left.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			
			bt_up.setIcon(imageUp);
			bt_down.setIcon(imageDown);
			bt_right.setIcon(imageRight);
			bt_left.setIcon(imageLeft);
		} catch (IOException e) {
			//Wenn fehlgeschlagen gibt Fehlermeldung aus
			e.printStackTrace();
		}
		
		
		
		//Setze jedem Objekt zwei Korrdinaten die eine Rechteck aufspannen in welchem das Objekt dargestellt wird 
		//(start horizontal, start vertikal, end horizontal, end vertikal) (0,0) ist die Linke obere Ecke
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
    	
    	
    	//Füge jedes Objekt dem Controlpanel hinzu
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
    	
    	//Füge den Buttons ein ActionListener hinzu, um auf Aktionen einzugehen
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
        
        //Controlpanel neu Zeichnen
        controlp.repaint();
        
	}
	
	/**
	 * Methode um die Werte über die Kugel im Konrolpanel zu aktualisieren
	 * @param anzAktuell : Position Aktuell
	 * @param anzMax : Wann Kugel gelösst
	 */
	public void updateKugelState(int anzAktuell, int anzMax){
		//Setzte Text neu
		lbl_aktuell_anz.setText("Anzahl Schritte: "+anzAktuell);
		lbl_max_anz.setText("Gelöst bei Schritte: "+anzMax);
	}
	
	/**
	 * Die ActionListener-Methode für die Buttons, da Klasse die ActionListener implementiert hat
	 */
	@Override
	public void actionPerformed(ActionEvent z) {
		
		//Lange if/else-Kette da switch nur primitive Datentypen erlaubt also keine ActionEvents
		
		if (z.getSource() == bt_fillSphere){
			//Fülle die Kugel neu
			m.fillSphere();
        }else if (z.getSource() == bt_solveSphere){
        	//Löse die Kugel zu ende
        	m.startSolve();
        }else if (z.getSource() == bt_kugel_uebernehmen){
        	//Debugging Option um Kugel zu Status zu setzten
        	String in = txt_kugelInput.getText();
        	m.fillSphere(in);
        }else if (z.getSource() == bt_up){
        	//Kugel nach oben drehen
        	m.rendernDrehen(0.0f,-0.2f,0.0f,0);
        }else if (z.getSource() == bt_right){
        	//Kugel nach rechts drehen
        	m.rendernDrehen(+0.2f,0.0f,0.0f,0);
        }else if (z.getSource() == bt_left){
        	//Kugel nach links drehen
        	m.rendernDrehen(-0.2f,0.0f,0.0f,0);
        }else if (z.getSource() == bt_down){
        	//Kugel nach unten drehen
        	m.rendernDrehen(0.0f,+0.2f,0.0f,0);
        }else if (z.getSource() == bt_reset_view){
        	//Drehen zurück setzen
        	m.rendernDrehen(0.0f,0.0f,0.0f,1);
        }else if (z.getSource() == bt_oneStep){
        	//einen Schritt im lösen weiter
        	m.oneStep();
        }else if (z.getSource() == bt_backStep){
        	//einen Schritt im lösen zurück
        	m.backStep();
        }else if (z.getSource() == bt_goPos){
        	//Zu Position springen --> String to Int erfolg im Manager
        	m.setPos(txt_goPos.getText());
        }
		
	}
}

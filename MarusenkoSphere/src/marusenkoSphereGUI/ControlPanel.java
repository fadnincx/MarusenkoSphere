package marusenkoSphereGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * Diese Klasse ist für die Darstellung des 2. Fensters, des Kontolpanels zuständig
 * 
 * Zusätzlich ist ein ActionListener implementiert um direkt auf die Aktionen einzugehen
 */

public class ControlPanel implements ActionListener{

	//Manager um die Kugel vom Panel aus zu Steuern
	private Manager m;
	
	//Welches Fenster angezeigt werden soll
	private int mode = 0;
	
	//Eigentliche Fenster
	private JFrame controlp = new JFrame("MarusenkoSphere Controlpanel");
	private JFrame editorp = new JFrame("MarusenkoSpher Editor");
	private JFrame devp = new JFrame("MarusenkoSpher Developer");

	
	
	//Buttons zum neu mischen, bzw lösen der Kugel
	private JButton bt_fillSphere = new JButton("neu mischen");
	private JButton bt_solveSphere = new JButton("lösen");
	
	//Pfeile zum drehen der Kugel sowie Zurücksetzt Button
	private JButton bt_up = new JButton();
	private JButton bt_right = new JButton();
	private JButton bt_left = new JButton();
	private JButton bt_down = new JButton();

	private JButton bt_reset_view = new JButton("Reset");
	private JButton bt_reset2 = new JButton("R");
	
	//Schritte vorwärts und zurück gehen
	private JButton bt_oneStep = new JButton("+1 Schritt");
	private JButton bt_backStep = new JButton("-1 Schritt");
	
	//Zu Fenster WechselButton
	private JButton bt_cp_edit = new JButton("Editor");
	private JButton bt_cp_dev = new JButton("Dev");
	
	private JButton bt_edit_dev = new JButton("Dev");
	private JButton bt_edit_sph = new JButton("Kugel");
	
	private JButton bt_dev_edit = new JButton("Editor");
	private JButton bt_dev_sph = new JButton("Kugel");
	

	//Die Farbigen Buttons
	private JButton bt_color_0 = new JButton();
	private JButton bt_color_1 = new JButton();
	private JButton bt_color_2 = new JButton();
	private JButton bt_color_3 = new JButton();
	private JButton bt_color_4 = new JButton();
	private JButton bt_color_5 = new JButton();
	private JButton bt_color_6 = new JButton();
	private JButton bt_color_7 = new JButton();
	
	
	//Farben Definieren
	private Color color0 = new Color(Rendern.getColorFloat(0,0),Rendern.getColorFloat(0,1),Rendern.getColorFloat(0,2),1.0f);
	private Color color1 = new Color(Rendern.getColorFloat(1,0),Rendern.getColorFloat(1,1),Rendern.getColorFloat(1,2),1.0f);
	private Color color2 = new Color(Rendern.getColorFloat(2,0),Rendern.getColorFloat(2,1),Rendern.getColorFloat(2,2),1.0f);
	private Color color3 = new Color(Rendern.getColorFloat(3,0),Rendern.getColorFloat(3,1),Rendern.getColorFloat(3,2),1.0f);
	private Color color4 = new Color(Rendern.getColorFloat(4,0),Rendern.getColorFloat(4,1),Rendern.getColorFloat(4,2),1.0f);
	private Color color5 = new Color(Rendern.getColorFloat(5,0),Rendern.getColorFloat(5,1),Rendern.getColorFloat(5,2),1.0f);
	private Color color6 = new Color(Rendern.getColorFloat(6,0),Rendern.getColorFloat(6,1),Rendern.getColorFloat(6,2),1.0f);
	private Color color7 = new Color(Rendern.getColorFloat(7,0),Rendern.getColorFloat(7,1),Rendern.getColorFloat(7,2),1.0f);
	
	
	//Label für Editor setzten
	private JLabel isError = new JLabel("Die Kugel enthält keine Fehler");
	private JLabel isSelected = new JLabel("Ausgewählt");
	
	//Text um Infos darzustellen
	private JLabel lbl_aktuell_anz = new JLabel("Anzahl Schritte:");
	private JLabel lbl_max_anz = new JLabel("Gelöst bei Schritte:");
	
	//Rotation Speed info
	private JLabel lbl_rotationSpeedInfo = new JLabel("Animationsgeschwindigkeit:");
	
	
	//Textfeld um zu Position zu gehen inklusie Bestätigungs Button
	private JTextField txt_goPos = new JTextField();
	private JButton bt_goPos = new JButton("Go to");
	
	//Debugging Feld für eingabe von Kugel inklusive Bestätigungs Button
	private JTextField txt_kugelInput = new JTextField();
	private JButton bt_kugel_uebernehmen = new JButton("Kugel übernehmen");
	
	private JSlider rotationSpeed = new JSlider();
	
	private JCheckBox checkSolving = new JCheckBox("Kugel lösen");
	
	//Verschiebung des Fensters, so dass gerade neben dem Hauptfenster dargestellt wird
	private int x;
	private int y;
	
	/**
	 * Konstruktor welcher das Manager-Objekt initialisiert und ein neues Fenster erstellen lässt
	 * @param m : Manager
	 */
	public ControlPanel(Manager m, int mode){
		//Initialisiere das Manager-Objekt
		this.mode = mode;
		this.m = m;
		
		//Erstelle neues Fenster
		createFrame();
	}
	
	/**
	 * Funktion zum Erstellen eines Neuen Fensters
	 */
	private void createFrame(){
		//Neues Toolkit Objekt erstellen, wird gebraucht um an die Bildschirmaufläsung zu kommen
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
		editorp.setSize(350, 510);
		devp.setSize(350, 510);
		
		//Setzet es sichtbar
		controlp.setVisible(true);
		editorp.setVisible(false);
		devp.setVisible(false);
		
		//Definiere was beim Schliessen des Fenster passieren soll --> Java beenden
		controlp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		devp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setzte die Position des Fensters
		controlp.setLocation(x,y);
		editorp.setLocation(x,y);
		devp.setLocation(x,y);
		
		//Setzte, dass Fenster grösse nicht veränderbar durch Benutzer
		controlp.setResizable(false);
		editorp.setResizable(false);
		devp.setResizable(false);
		
		//Setzte Layout = null
		controlp.setLayout(null);
		editorp.setLayout(null);
		devp.setLayout(null);

		
		display();
	}
	private void display(){
		//Rufe die Funktion display auf um die Objekte dem eben erstellten Objekt hinzuzufügen
		displayEditor();
		displayKugelControlpanel();
		displayDev();
	}
	private void displayDev(){
		
    	bt_dev_edit.setBounds(20, 20, 145, 50);
    	bt_dev_sph.setBounds(185, 20, 145, 50);
    	
    	txt_kugelInput.setBounds(20,90,310, 40);
    	bt_kugel_uebernehmen.setBounds(20,140, 310, 50);
    	
    	checkSolving.setBounds(20,200,200,30);
    	
    	
    	devp.add(txt_kugelInput);
    	devp.add(bt_kugel_uebernehmen);
    	devp.add(bt_dev_edit);
    	devp.add(bt_dev_sph);
    	devp.add(checkSolving);
    	
    	bt_dev_edit.addActionListener(this);
    	bt_dev_sph.addActionListener(this);
        bt_kugel_uebernehmen.addActionListener(this);
        
    	//Controlpanel neu Zeichnen
        devp.repaint();
	}
	private void displayEditor(){
		
		bt_color_0.setBackground(color0);
		bt_color_1.setBackground(color1);
		bt_color_2.setBackground(color2);
		bt_color_3.setBackground(color3);
		bt_color_4.setBackground(color4);
		bt_color_5.setBackground(color5);
		bt_color_6.setBackground(color6);
		bt_color_7.setBackground(color7);
		
		bt_color_0.setBounds(20, 20, 150, 20);
		bt_color_1.setBounds(20, 50, 150, 20);
		bt_color_2.setBounds(20, 80, 150, 20);
		bt_color_3.setBounds(20, 110, 150, 20);
		bt_color_4.setBounds(20, 140, 150, 20);
		bt_color_5.setBounds(20, 170, 150, 20);
		bt_color_6.setBounds(20, 200, 150, 20);
		bt_color_7.setBounds(20, 230, 150, 20);
	
		bt_edit_dev.setBounds(20,330,145,50);
		bt_edit_sph.setBounds(185, 330, 145,50);
		
		isError.setBounds(20,260,300,50);
		isSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
		
		editorp.add(bt_edit_dev);
		editorp.add(bt_edit_sph);
		editorp.add(bt_color_0);
		editorp.add(bt_color_1);
		editorp.add(bt_color_2);
		editorp.add(bt_color_3);
		editorp.add(bt_color_4);
		editorp.add(bt_color_5);
		editorp.add(bt_color_6);
		editorp.add(bt_color_7);	
		editorp.add(isError);
		editorp.add(isSelected);
		
		
		bt_color_0.addActionListener(this);
		bt_color_1.addActionListener(this);
		bt_color_2.addActionListener(this);
		bt_color_3.addActionListener(this);
		bt_color_4.addActionListener(this);
		bt_color_5.addActionListener(this);
		bt_color_6.addActionListener(this);
		bt_color_7.addActionListener(this);
		bt_edit_dev.addActionListener(this);
		bt_edit_sph.addActionListener(this);
		
		//Controlpanel neu Zeichnen
		editorp.repaint();
	}
	/**
	 * Funktion zum Alle Objekte dem Fenster hinzuzufügen
	 */
	private void displayKugelControlpanel(){
		
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
    	
    	lbl_rotationSpeedInfo.setBounds(20,80,310,20);
    	rotationSpeed.setBounds(20,100,310,30);
    	
    	bt_cp_dev.setBounds(20,130,145,50);
    	bt_cp_edit.setBounds(185,130,145,50);
    	
    	txt_goPos.setBounds(20, 190, 145, 50);
    	bt_goPos.setBounds(185, 190, 145, 50);
    	
    	lbl_aktuell_anz.setBounds(20,250,200,20);
    	lbl_max_anz.setBounds(20,270,200,20);
    	
    	bt_reset_view.setBounds(250,300,80,50);
    	bt_reset2.setBounds(280,360,50,50);
    	bt_up.setBounds(150,300,50,50);
    	bt_left.setBounds(90,360,50,50);
    	bt_right.setBounds(210,360,50,50);
    	bt_down.setBounds(150,360,50,50);
    	
    	
    	bt_backStep.setBounds(20,420,145,50);
    	bt_oneStep.setBounds(185,420,145,50);
    	
    	
    	rotationSpeed.setMinimum(5);    //stellt den Minimalwert auf 10 ein
    	rotationSpeed.setMaximum(180);  //stellt den Maximalwert auf 150 ein
    	rotationSpeed.setValue(30);     //selektiert den Wert 30
    	rotationSpeed.setMinorTickSpacing(5); //Abstönde im Feinraster
    	rotationSpeed.setMajorTickSpacing(10); //Abstönde im Grossraster
    	rotationSpeed.setSnapToTicks(false); //deaktiviert das automatische Versetzen / Einrasten im Raster
    	rotationSpeed.setExtent(0); //der Zeiger verspringt jedesmal 15 Einheiten
    	rotationSpeed.setPaintLabels(false);  //Zahlen werden nicht angezeigt
    	rotationSpeed.setPaintTicks(false);    //Striche werden nicht angezeigt
    	//noch frei 
    	//.setBounds(20,130,310,40);
    	
    	//Füge jedes Objekt dem Controlpanel hinzu
    	controlp.add(bt_fillSphere);
    	controlp.add(bt_solveSphere);
    	controlp.add(bt_up);
    	controlp.add(bt_down);
    	controlp.add(bt_left);
    	controlp.add(bt_right);
    	controlp.add(bt_reset_view);
    	controlp.add(bt_reset2);
    	controlp.add(bt_oneStep);
    	controlp.add(bt_backStep);
    	controlp.add(lbl_aktuell_anz);
    	controlp.add(lbl_max_anz);
    	controlp.add(txt_goPos);
    	controlp.add(bt_goPos);
    	controlp.add(rotationSpeed);
    	controlp.add(lbl_rotationSpeedInfo);
    	controlp.add(bt_cp_dev);
    	controlp.add(bt_cp_edit);
    	
    	//Füge den Buttons ein ActionListener hinzu, um auf Aktionen einzugehen
    	bt_fillSphere.addActionListener(this);
        bt_solveSphere.addActionListener(this);
        bt_up.addActionListener(this);
        bt_right.addActionListener(this);
        bt_left.addActionListener(this);
        bt_down.addActionListener(this);
        bt_reset_view.addActionListener(this);
        bt_reset2.addActionListener(this);
        bt_oneStep.addActionListener(this);
        bt_backStep.addActionListener(this);
        bt_goPos.addActionListener(this);
        bt_cp_dev.addActionListener(this);
        bt_cp_edit.addActionListener(this);
        
        
        //Controlpanel neu Zeichnen
        //controlp.revalidate();
        controlp.repaint();
        
	}
	public double getRotationSpeed(){
		return rotationSpeed.getValue();
	}
	public void updateMode(int mode){
		this.mode = mode;
		editorp.setVisible(false);
		controlp.setVisible(false);
		devp.setVisible(false);
		switch(this.mode){
		case 1:
			editorp.setVisible(true);
			break;
		case 2:
			devp.setVisible(true);
			break;
		default:
			controlp.setVisible(true);	
			break;
		}
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
		//display();
	}
	private void updateSelectedPosition(){
		isSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
		//controlp.revalidate();
        controlp.repaint();
	}
	public void updateAllowKugel(boolean isOk){
		if(isOk){
			isError.setText("Die Kugel enthält keine Fehler");
		}else{
			isError.setText("Die Kugel ist nicht gültig");
		}
	}
	
	/**
	 * Die ActionListener-Methode für die Buttons, da Klasse die ActionListener implementiert hat
	 */
	@Override
	public void actionPerformed(ActionEvent z) {
		
		//Lange if/else-Kette da switch nur primitive Datentypen erlaubt also keine ActionEvents
		
		if (z.getSource() == bt_fillSphere){
			//Fülle die Kugel neu inklusiv blockieren, damit nicht zu schnell
			if(!m.BlockedKey.contains("bt_fillSphere")){
		    	m.BlockedKey.add("bt_fillSphere");
				m.fillSphere();
		    	m.update(100);
		    	m.BlockedKey.remove("bt_fillSphere");
	    	}
        }else if (z.getSource() == bt_solveSphere){
        	//Löse die Kugel zu ende
        	m.startSolve();
        }else if (z.getSource() == bt_kugel_uebernehmen){
        	//Debugging Option um Kugel zu Status zu setzten
        	String in = txt_kugelInput.getText();
        	m.fillSphere(in, checkSolving.isSelected());
        }else if (z.getSource() == bt_up){
        	//Kugel nach oben drehen
        	m.rendernDrehen(0.0f,-1.0f,0.0f,0);
        }else if (z.getSource() == bt_right){
        	//Kugel nach rechts drehen
        	m.rendernDrehen(+1.0f,0.0f,0.0f,0);
        }else if (z.getSource() == bt_left){
        	//Kugel nach links drehen
        	m.rendernDrehen(-1.0f,0.0f,0.0f,0);
        }else if (z.getSource() == bt_down){
        	//Kugel nach unten drehen
        	m.rendernDrehen(0.0f,+1.0f,0.0f,0);
        }else if (z.getSource() == bt_reset_view){
        	// rtrix=20.0f;
           //  rtriy=45.0f; 
             //rtriz=20.0f;
        	//Drehen zurück setzen
        	m.rendernDrehen(45.0f,20.0f,0.0f,1);
        }else if (z.getSource() == bt_reset2){
        	//Drehen zurück setzen
        	m.rendernDrehen(0f,0f,0f,1);
        }else if (z.getSource() == bt_oneStep){
        	//einen Schritt im lösen weiter
        	if(!m.BlockedKey.contains("bt_oneStep")){
		    	m.BlockedKey.add("bt_oneStep");
		    	m.toDoQueue.offer("x");
		    	m.update(100);
		    	m.BlockedKey.remove("bt_oneStep");
	    	}
        }else if (z.getSource() == bt_backStep){
        	//einen Schritt im lösen zurück
        	if(!m.BlockedKey.contains("bt_backStep")){
		    	m.BlockedKey.add("bt_backStep");
		    	m.toDoQueue.offer("y");
		    	m.update(100);
		    	m.BlockedKey.remove("bt_backStep");
	    	}
        }else if (z.getSource() == bt_goPos){
        	//Zu Position springen --> String to Int erfolg im Manager
        	m.goToStep(Integer.parseInt(txt_goPos.getText()));
        }else if (z.getSource() == bt_color_0){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(0);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_1){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(1);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_2){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(2);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_3){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(3);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_4){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(4);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_5){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(5);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_6){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(6);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_color_7){
        	//Ausgewählte Farbe wählen
        	m.changeSelectedColor(7);
        	updateSelectedPosition();
        }else if (z.getSource() == bt_cp_dev || z.getSource() == bt_edit_dev){
        	//Zu dev Fenster wechseln
        	m.changeToMode(2);
        }else if (z.getSource() == bt_cp_edit|| z.getSource() == bt_dev_edit){
        	//Zu editor wechseln
        	m.changeToMode(1);
        }else if (z.getSource() == bt_dev_sph|| z.getSource() == bt_edit_sph){
        	//Zu Kugel wechseln
        	m.changeToMode(0);
        }
		
		
	}
}

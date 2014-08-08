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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Diese Klasse ist für die Darstellung des 2. Fensters, des Kontolpanels zuständig
 * 
 * Zusätzlich ist ein ActionListener implementiert um direkt auf die Aktionen einzugehen
 */

public class ControlPanel implements ActionListener{

	//Manager um die Kugel vom Panel aus zu Steuern
	private Manager m;
	
	//Eigentliche Fenster
	private JFrame controlPanel = new JFrame("MarusenkoSphere Controlpanel");
	private JFrame editorPanel = new JFrame("MarusenkoSpher Editor");
	private JFrame devPanel = new JFrame("MarusenkoSpher Developer");
	
	//Button zum neu mischen
	private JButton cpButtonFillSphere = new JButton("neu mischen");
	
	//Button zum lösen bis am ende
	private JButton cpButtonSolve = new JButton("lösen");
	
	//Buttons mit Pfeile zum Drehen der Kugel
	private JButton cpButtonUp = new JButton();
	private JButton cpButtonRight = new JButton();
	private JButton cpButtonLeft = new JButton();
	private JButton cpButtonDown = new JButton();

	//Kamera zurück setzten
	private JButton cpButtonResetView = new JButton("Reset");
	
	//Schritte vorwärts und zurück gehen
	private JButton cpButtonAddOneStep = new JButton("+1 Schritt");
	private JButton cpButtonSubOneStep = new JButton("-1 Schritt");
	
	//Zu Fenster WechselButton
	private JButton cpChangeEditor = new JButton("Editor");
	private JButton cpChangeDev = new JButton("Dev");
	
	private JButton editChangeDev = new JButton("Dev");
	private JButton editChangeSphere = new JButton("Kugel");
	
	private JButton devChangeEditor = new JButton("Editor");
	private JButton devChangeSphere = new JButton("Kugel");
	

	//Die Farbigen Buttons des Editors
	private JButton editButtonColor0 = new JButton();
	private JButton editButtonColor1 = new JButton();
	private JButton editButtonColor2 = new JButton();
	private JButton editButtonColor3 = new JButton();
	private JButton editButtonColor4 = new JButton();
	private JButton editButtonColor5 = new JButton();
	private JButton editButtonColor6 = new JButton();
	private JButton editButtonColor7 = new JButton();
	
	
	//Farben Definieren
	private Color color0 = Rendern.getColorColor(0);
	private Color color1 = Rendern.getColorColor(1);
	private Color color2 = Rendern.getColorColor(2);
	private Color color3 = Rendern.getColorColor(3);
	private Color color4 = Rendern.getColorColor(4);
	private Color color5 = Rendern.getColorColor(5);
	private Color color6 = Rendern.getColorColor(6);
	private Color color7 = Rendern.getColorColor(7);
	
	
	//Label für Editor setzten
	private JLabel editLabelIsSphereLegal = new JLabel("Die Kugel enthält keine Fehler");
	private JLabel editLabelSelected = new JLabel("Ausgewählt");
	
	//Text um Infos darzustellen
	private JLabel cpLabelSphereStepNow = new JLabel("Anzahl Schritte:");
	private JLabel cpLabelSphereStepMax = new JLabel("Gelöst bei Schritte:");
	private JLabel devLabelSphereStepNow = new JLabel("Anzahl Schritte:");
	private JLabel devLabelSphereStepMax = new JLabel("Gelöst bei Schritte:");
	
	//Rotation Speed info
	private JLabel cpLabelAnimationSpeed= new JLabel("Animationsgeschwindigkeit:");
	
	//Slider für die Animationsgeschwindigkeit
	private JSlider cpSliderAnimationSpeed = new JSlider();
	
	//Textfeld um zu Position zu gehen inklusie Bestätigungs Button
	private JTextField cpTextFieldGoToPosition = new JTextField();
	private JButton cpButtonGoToPosition = new JButton("Go to");
	
	//Debugging Feld für eingabe von Kugel inklusive Bestätigungs Button
	private JTextField devTextFieldSphereDebugStringInput = new JTextField();
	private JButton devButtonSphereDebugStringInput = new JButton("Kugel übernehmen");

	//CheckBox für dev Panel, ob gelöst werden soll
	private JCheckBox devCheckBoxSolve = new JCheckBox("Kugel lösen");
	
	//Verschiebung des Fensters, so dass gerade neben dem Hauptfenster dargestellt wird
	private int windowOffsetX;
	private int windowOffsetY;
	
	/**
	 * Konstruktor welcher das Manager-Objekt initialisiert und ein neues Fenster erstellen lässt
	 */
	protected ControlPanel(Manager m){
		//Übernehme den Manager
		this.m = m;
		
		//Versuche die Fenster zu erstellen
		try {
			createFrames();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Funktion zum Erstellen der Fensters
	 */
	private void createFrames() throws IOException{
		//Neues Toolkit Objekt erstellen, wird gebraucht um an die Bildschirmaufläsung zu kommen
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		//Die Bildschirmdimensionen bekommen
		Dimension screenSize = tk.getScreenSize();
				
		//Berechne die Verschiebung des Fensters
		windowOffsetX=(screenSize.width/2)+337;
		windowOffsetY=(screenSize.height/2)-240;
		
		//Lade das Icon
		Image icon = ImageIO.read(this.getClass().getResource("/img/icon_64.png"));
		
		//Setze das Icon für alle Fenster
		controlPanel.setIconImage(icon);
		editorPanel.setIconImage(icon);
		devPanel.setIconImage(icon);
		
		//Definiere die Grösse der Fenster
		controlPanel.setSize(350, 510);
		editorPanel.setSize(350, 510);
		devPanel.setSize(350, 510);
		
		//Setze nur das controlPanel sichtbar, die anderen auch unsichtbar
		controlPanel.setVisible(true);
		editorPanel.setVisible(false);
		devPanel.setVisible(false);
		
		//Definiere was beim Schliessen des Fenster passieren soll --> Programm beenden
		controlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		devPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setzte die Position der Fenster
		controlPanel.setLocation(windowOffsetX,windowOffsetY);
		editorPanel.setLocation(windowOffsetX,windowOffsetY);
		devPanel.setLocation(windowOffsetX,windowOffsetY);
		
		//Setzte, dass die Fenstergrösse durch den Benutzer nicht veränderbar ist
		controlPanel.setResizable(false);
		editorPanel.setResizable(false);
		devPanel.setResizable(false);
		
		//Setzte Layout = null
		controlPanel.setLayout(null);
		editorPanel.setLayout(null);
		devPanel.setLayout(null);

		initControlpanel();
		initEditor();
		initDev();
	}

	/**
	 * Funktion zum Initialisieren des Controlpanels
	 */
	private void initControlpanel(){
		
		//Versuche die Bilder Für die Pfeiltasten zu laden und den Buttons hinzuzufügen
		try {
			ImageIcon imageUp = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/up.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			ImageIcon imageDown = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/down.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			ImageIcon imageRight = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/right.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			ImageIcon imageLeft = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/left.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			
			cpButtonUp.setIcon(imageUp);
			cpButtonDown.setIcon(imageDown);
			cpButtonRight.setIcon(imageRight);
			cpButtonLeft.setIcon(imageLeft);
			
		} catch (IOException e) {
			
			//Wenn fehlgeschlagen gibt Fehlermeldung aus
			e.printStackTrace();
			
		}

		//Button zum neu füllen der Kugel Position und Grösse zuweisen
		cpButtonFillSphere.setBounds(20, 20, 145, 50);
		
		//Button zum lösen der Kugel Position und Grösse zuweisen
		cpButtonSolve.setBounds(185, 20,145, 50);
    	
		//Dem Label für die Animationsgeschwindigkeit Position und Grösse zuweisen
		cpLabelAnimationSpeed.setBounds(20,80,310,20);
		
		//Dem Slider für die Animationsgeschwindikeit Position und Grösse zuweisen
		cpSliderAnimationSpeed.setBounds(20,100,310,30);
    	
		//Den Buttons für das Wechseln des Modus Position und Grösse zuweisen
    	cpChangeDev.setBounds(20,130,145,50);
    	cpChangeEditor.setBounds(185,130,145,50);
    	
    	//Dem Textfeld für Sprünge in der Lösungsreihe Position und Grösse zuweisen
    	cpTextFieldGoToPosition.setBounds(20, 190, 145, 50);
    	
    	//Dem Button zum akzepieren des Inputs Position und Grösse zuweisen
    	cpButtonGoToPosition.setBounds(185, 190, 145, 50);
    	
    	//Den Labels mit Infos zur Kugel Position und Grösse zuweisen
    	cpLabelSphereStepNow.setBounds(20,250,200,20);
    	cpLabelSphereStepMax.setBounds(20,270,200,20);
    	
    	//Dem Button zum zurücksetzen der Kamera Position und Grösse zuweisen
    	cpButtonResetView.setBounds(250,300,80,50);
    	
    	//Den 4 Buttons mit Pfeilen Position und Grösse zuweisen
    	cpButtonUp.setBounds(150,300,50,50);
    	cpButtonLeft.setBounds(90,360,50,50);
    	cpButtonRight.setBounds(210,360,50,50);
    	cpButtonDown.setBounds(150,360,50,50);
    	
    	//Den Buttons zum einen Schritt machen Position und Grösse zuweisen
    	cpButtonSubOneStep.setBounds(20,420,145,50);
    	cpButtonAddOneStep.setBounds(185,420,145,50);
    	
    	//Dem Slider für die Animationsgeschwindigkeit diverse Einstellungen vornehmen
    	cpSliderAnimationSpeed.setMinimum(5);    //stellt den Minimalwert auf 5 ein
    	cpSliderAnimationSpeed.setMaximum(180);  //stellt den Maximalwert auf 180 ein
    	cpSliderAnimationSpeed.setValue(30);     //selektiert den Wert 30
    	cpSliderAnimationSpeed.setMinorTickSpacing(5); //Abstände im Feinraster
    	cpSliderAnimationSpeed.setMajorTickSpacing(10); //Abstände im Grossraster
    	cpSliderAnimationSpeed.setSnapToTicks(false); //deaktiviert das automatische Versetzen / Einrasten im Raster
    	cpSliderAnimationSpeed.setExtent(0); //der Zeiger verspringt jedesmal 0 Einheiten
    	cpSliderAnimationSpeed.setPaintLabels(false);  //Zahlen werden nicht angezeigt
    	cpSliderAnimationSpeed.setPaintTicks(false);    //Striche werden nicht angezeigt
    	
    	//Zusätzlich wird ein ChangeListener hinzugefügt, damit der Wert im Manager geändert werden kann
    	cpSliderAnimationSpeed.addChangeListener(new ChangeListener() {
    	      public void stateChanged(ChangeEvent evt) {
    	    	  
    	    	  //Statische Variable im Manager ändern
    	          Manager.setAnimationSpeed(cpSliderAnimationSpeed.getValue());
    	          
    	        }
    	      });
    	  	
    	//Füge jedes Objekt dem Controlpanel hinzu
    	controlPanel.add(cpButtonFillSphere);
    	controlPanel.add(cpButtonSolve);
    	controlPanel.add(cpButtonUp);
    	controlPanel.add(cpButtonDown);
    	controlPanel.add(cpButtonLeft);
    	controlPanel.add(cpButtonRight);
    	controlPanel.add(cpButtonResetView);
    	controlPanel.add(cpButtonAddOneStep);
    	controlPanel.add(cpButtonSubOneStep);
    	controlPanel.add(cpLabelSphereStepNow);
    	controlPanel.add(cpLabelSphereStepMax);
    	controlPanel.add(cpTextFieldGoToPosition);
    	controlPanel.add(cpButtonGoToPosition);
    	controlPanel.add(cpSliderAnimationSpeed);
    	controlPanel.add(cpLabelAnimationSpeed);
    	controlPanel.add(cpChangeDev);
    	controlPanel.add(cpChangeEditor);
    	
    	//Füge den Buttons ein ActionListener hinzu
    	cpButtonFillSphere.addActionListener(this);
    	cpButtonSolve.addActionListener(this);
    	cpButtonUp.addActionListener(this);
    	cpButtonRight.addActionListener(this);
    	cpButtonLeft.addActionListener(this);
    	cpButtonDown.addActionListener(this);
    	cpButtonResetView.addActionListener(this);
    	cpButtonAddOneStep.addActionListener(this);
    	cpButtonSubOneStep.addActionListener(this);
    	cpButtonGoToPosition.addActionListener(this);
        cpChangeDev.addActionListener(this);
        cpChangeEditor.addActionListener(this);
        
        
        //Controlpanel neu Zeichnen
        controlPanel.repaint();
	}
	
	/**
	 * Funktion zum initialisieren des Editors
	 */
	private void initEditor(){
		
		//Den farbigen Buttons die Farbe als Hintergrund zuweisen
		editButtonColor0.setBackground(color0);
		editButtonColor1.setBackground(color1);
		editButtonColor2.setBackground(color2);
		editButtonColor3.setBackground(color3);
		editButtonColor4.setBackground(color4);
		editButtonColor5.setBackground(color5);
		editButtonColor6.setBackground(color6);
		editButtonColor7.setBackground(color7);
		
		//Den farbigen Buttons die Position und Grösse zuweisen
		editButtonColor0.setBounds(20, 20, 150, 20);
		editButtonColor1.setBounds(20, 50, 150, 20);
		editButtonColor2.setBounds(20, 80, 150, 20);
		editButtonColor3.setBounds(20, 110, 150, 20);
		editButtonColor4.setBounds(20, 140, 150, 20);
		editButtonColor5.setBounds(20, 170, 150, 20);
		editButtonColor6.setBounds(20, 200, 150, 20);
		editButtonColor7.setBounds(20, 230, 150, 20);
	
		//Den Buttons für das Wechseln des Modus Position und Grösse zuweisen 
		editChangeSphere.setBounds(20,330,145,50);
		editChangeDev.setBounds(185, 330, 145,50);
		
		//Dem Label mit Infos zur Legalität der Kugel Position und Grösse zuweisen
		editLabelIsSphereLegal.setBounds(20,260,300,50);
		
		//Dem Label mit Selected Position und Grösse zuweisen
		editLabelSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
		
		//Alle Elemente zu editorPanel hinzufügen
		editorPanel.add(editChangeDev);
		editorPanel.add(editChangeSphere);
		editorPanel.add(editButtonColor0);
		editorPanel.add(editButtonColor1);
		editorPanel.add(editButtonColor2);
		editorPanel.add(editButtonColor3);
		editorPanel.add(editButtonColor4);
		editorPanel.add(editButtonColor5);
		editorPanel.add(editButtonColor6);
		editorPanel.add(editButtonColor7);	
		editorPanel.add(editLabelIsSphereLegal);
		editorPanel.add(editLabelSelected);
		
		//Den Buttons ein Actionlistener hinzufügen
		editButtonColor0.addActionListener(this);
		editButtonColor1.addActionListener(this);
		editButtonColor2.addActionListener(this);
		editButtonColor3.addActionListener(this);
		editButtonColor4.addActionListener(this);
		editButtonColor5.addActionListener(this);
		editButtonColor6.addActionListener(this);
		editButtonColor7.addActionListener(this);
		editChangeDev.addActionListener(this);
		editChangeSphere.addActionListener(this);
		
		//das EditorPanel neu zeichnen
		editorPanel.repaint();
	}
	
	/**
	 * Funktion zum Initialisieren des DevPanels
	 */
	private void initDev(){
		
		//Buttons für Wechsel des Modus Position und Grösse zuweisen
		devChangeEditor.setBounds(20, 20, 145, 50);
		devChangeSphere.setBounds(185, 20, 145, 50);
		
		//Inputfeld für Debugstring Position und Grösse zuweisen
		devTextFieldSphereDebugStringInput.setBounds(20,90,310, 40);
		
		//Button zum übernehmen des Strings Position und Grösse zuweisen
		devButtonSphereDebugStringInput.setBounds(20,140, 310, 50);
    	
		//Checkbox, ob Kugel gelöst werden soll, Position und Grösse zuweisen
		devCheckBoxSolve.setBounds(20,200,200,30);
		
		//Checkbox standartmässig als markiert setzen
    	devCheckBoxSolve.setSelected(true);
    	
    	//Labels mit Infos zur Kugel Position und Grösse zuweisen
    	devLabelSphereStepNow.setBounds(20,250,200,20);
    	devLabelSphereStepMax.setBounds(20,280,200,20);
    	
    	//Alle Elemente zum devPanel hinzufügen
    	devPanel.add(devTextFieldSphereDebugStringInput);
    	devPanel.add(devButtonSphereDebugStringInput);
    	devPanel.add(devChangeEditor);
    	devPanel.add(devChangeSphere);
    	devPanel.add(devCheckBoxSolve);
    	devPanel.add(devLabelSphereStepNow);
    	devPanel.add(devLabelSphereStepMax);
    	
    	//Für die Buttons den ActionListener setzen
    	devChangeEditor.addActionListener(this);
    	devChangeSphere.addActionListener(this);
    	devButtonSphereDebugStringInput.addActionListener(this);
        
    	//das DevPanel neu zeichnen
        devPanel.repaint();
	}
	
	/**
	 * Funktion zum wechseln des anzeige Modus
	 * @param mode
	 */
	protected void updateMode(int mode){
		
		//Setze alle Fenster auf unsichtbar
		editorPanel.setVisible(false);
		controlPanel.setVisible(false);
		devPanel.setVisible(false);
		
		//Setze entsprechendes Fenster auf sichtbar
		//Controlpanel ist default anstelle 0, damit sicher immer ein Fenster angezeigt wird...
		switch(mode){
			case 1:
				editorPanel.setVisible(true);
				break;
			case 2:
				devPanel.setVisible(true);
				break;
			default:
				controlPanel.setVisible(true);	
				break;
			}
	}
	
	/**
	 * Funktion zum aktuallisieren der Infos über die Kugel
	 */
	protected void updateSphereInfos(int aktuellePosition, int fertigBeiPosition){
		//Setzte Text neu fürs Controlpanel
		cpLabelSphereStepNow.setText("Anzahl Schritte: "+aktuellePosition);
		cpLabelSphereStepMax.setText("Gelöst bei Schritte: "+fertigBeiPosition);
		
		//Setze Text neu fürs DevPanel
		devLabelSphereStepNow.setText("Anzahl Schritte: "+aktuellePosition);
		devLabelSphereStepMax.setText("Gelöst bei Schritte: "+fertigBeiPosition);
	}
	
	/**
	 * Updated die Position des Textes selected
	 */
	private void updateSelectedPosition(){
		editLabelSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
		controlPanel.repaint();
	}
	
	/**
	 * Updated im Editor die Info bezüglich der legalität der Kugel
	 * @param isOk
	 */
	protected void updateInfoIsSphereAllowed(boolean isOk){
		if(isOk){
			editLabelIsSphereLegal.setText("Die Kugel enthält keine Fehler");
		}else{
			editLabelIsSphereLegal.setText("Die Kugel ist nicht gültig");
		}
	}
	
	/**
	 * Die ActionListener-Methode für die Buttons, da Klasse die ActionListener implementiert hat
	 */
	@Override
	public void actionPerformed(ActionEvent z) {
		
		//Lange if/else-Kette da switch nur primitive Datentypen erlaubt also keine ActionEvents
		
		//Ist der Button zum Kugel neu mischen gedrückt?
		if (z.getSource() == cpButtonFillSphere){
			//S als KeyBoard Input hinzufügen
			KeyboardMouseManager.pressedKey.add('s');
		
		//Ist der Button zum start der Animation gedrückt?
        }else if (z.getSource() == cpButtonSolve){
        	//L als KeyBoard Input hinzufügen
        	KeyboardMouseManager.pressedKey.add('l');
        	
        //Ist der Button zum übernehmen des DebuggingStrings gedrückt?
        }else if (z.getSource() == devButtonSphereDebugStringInput){
        	//Debugging Option um Kugel zu Status zu setzten
        	String in = devTextFieldSphereDebugStringInput.getText();
        	m.fillSphereFromDevString(in, devCheckBoxSolve.isSelected());
        	
        //Ist der Button nach Oben gedrückt?
        }else if (z.getSource() == cpButtonUp){
        	//Kugel nach oben drehen
        	m.changeRotationAngle(-10,0);
        	
        //Ist der Button nach Rechts gedrückt?	
        }else if (z.getSource() == cpButtonRight){
        	//Kugel nach rechts drehen
        	m.changeRotationAngle(0,10);
        	
        //Ist der Button nach Links gedrückt?
        }else if (z.getSource() == cpButtonLeft){
        	//Kugel nach links drehen
        	m.changeRotationAngle(0,-10);
        	
        //Ist der Button nach Unten gedrückt?	
        }else if (z.getSource() == cpButtonDown){
        	//Kugel nach unten drehen
        	m.changeRotationAngle(10,0);
        	
        //Ist der Button zum Zurücksetzen der Drehung gedrückt?
        }else if (z.getSource() == cpButtonResetView){
        	//Drehen zurück setzen
        	m.resetRotationAngle();
        	
        //Ist der Button zum im Lösungsweg einen Schritt weiter zu gehen gedrückt?	
        }else if (z.getSource() == cpButtonAddOneStep){
        	//X als KeyBoard Input hinzufügen
			KeyboardMouseManager.pressedKey.add('x');
        	
		//Ist der Button zum im Lösungseg einen Schritt zurück zu gehen gedrückt?	
        }else if (z.getSource() == cpButtonSubOneStep){
        	//Y als KeyBoard Input hinzufügen
        	KeyboardMouseManager.pressedKey.add('y');

        //Ist der Button um an eine Position zu springe gedrückt?	
        }else if (z.getSource() == cpButtonGoToPosition){
        	//Versuche in Integer um zu wandeln und dann zu Position zu springen
        	try{
        		int pos = Integer.parseInt(cpTextFieldGoToPosition.getText());
        		m.goToStep(pos);
        	
        	//Bei Fehler
        	}catch(Exception e){
        		//Ignorieren
        	}
        	
        	
        //Ist Im Editor die Farbe 0 ausgewählt?	
        }else if (z.getSource() == editButtonColor0){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(0);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 1 ausgewählt?		
        }else if (z.getSource() == editButtonColor1){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(1);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 2 ausgewählt?	
        }else if (z.getSource() == editButtonColor2){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(2);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 3 ausgewählt?	
        }else if (z.getSource() == editButtonColor3){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(3);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 4 ausgewählt?	
        }else if (z.getSource() == editButtonColor4){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(4);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 5 ausgewählt?	
        }else if (z.getSource() == editButtonColor5){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(5);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 6 ausgewählt?	
        }else if (z.getSource() == editButtonColor6){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(6);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist Im Editor die Farbe 7 ausgewählt?	
        }else if (z.getSource() == editButtonColor7){
        	//Wechsle ausgewählte Farbe im Manager
        	m.changeSelectedColor(7);
        	//Update Position von Selected Text
        	updateSelectedPosition();
        	
        //Ist der Button gedrück um in den Dev-Modus zu wechseln?        	
        }else if (z.getSource() == cpChangeDev || z.getSource() == editChangeDev){
        	//D als KeyBoard Input hinzufügen
        	KeyboardMouseManager.pressedKey.add('d');
        	
        //Ist der Button gedrückt um in den Editor-Modus zu wechseln?	
        }else if (z.getSource() == cpChangeEditor|| z.getSource() == devChangeEditor){
        	//E als KeyBoard Input hinzufügen
        	KeyboardMouseManager.pressedKey.add('e');
        	
        //Ist der Button gedrückt um in den Normalen-Modus zu wechseln?	
        }else if (z.getSource() == devChangeSphere|| z.getSource() == editChangeSphere){
        	//K als KeyBoard Input hinzufügen
        	KeyboardMouseManager.pressedKey.add('k');
        }
		
		
	}
}

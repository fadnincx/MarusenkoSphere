package marusenkoSphereGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import marusenkoSphere.Settings;

/**
 * Diese Klasse ist für die Darstellung des 2. Fensters, des Kontolpanels zuständig
 * 
 * Zusätzlich ist ein ActionListener implementiert um auf die Betätigung der Buttons zu reagieren
 * Ein KeyListener sorgt dafür, dass auf Tastatur eingaben gemacht werden können
 */

public class ControlPanel implements ActionListener, KeyListener{

	//Manager um die Kugel vom Panel aus zu Steuern
	private Manager m;
	
	//Eigentliche Fenster
	private JFrame controlPanel = new JFrame("MarusenkoSphere Controlpanel");
	private JFrame editorPanel = new JFrame("MarusenkoSphere Editor");
	private JFrame devPanel = new JFrame("MarusenkoSphere Developer");
	
	//Button zum neu mischen
	private JButton cpButtonFillSphere = new JButton("<html>Die Kugel<br>neu mischen</html>");
	
	//Button zum lösen bis ans ende
	private JButton cpButtonSolve = new JButton("<html>Lösungsanimation<br>starten</html>");
	
	//Buttons mit Pfeile zum Drehen der Kugel
	private JButton cpButtonUp = new JButton();
	private JButton cpButtonRight = new JButton();
	private JButton cpButtonLeft = new JButton();
	private JButton cpButtonDown = new JButton();

	//Button um Kamera zurück zu setzten
	private JButton cpButtonResetView = new JButton("<html>Kamera<br>zurücksetzen</html>");
	
	//Schritte vorwärts und zurück gehen
	private JButton cpButtonAddOneStep = new JButton();
	private JButton cpButtonSubOneStep = new JButton();
	
	//Zu Fenster WechselButton
	
	//auf Controlpanel
	private JButton cpChangeEditor = new JButton("zum Editor");
	private JButton cpChangeDev = new JButton("Dev");
	
	//Auf Editorpanel
	private JButton editChangeDev = new JButton("Dev");
	private JButton editChangeSphere = new JButton("zurück zur Kugel");
	
	//Auf Devpanel
	private JButton devChangeEditor = new JButton("Editor");
	private JButton devChangeSphere = new JButton("Kugel");
	
	//Hilfe buttons
	private JButton cpHelpAnimation = new JButton();
	private JButton cpHelpFortschritt = new JButton();
	private JButton cpHelpKamera = new JButton();
	private JButton cpHelpEditor = new JButton();
	private JButton editHelp = new JButton();
	
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
	
	//Text um Infos über die Kugel darzustellen
	//auf Controlpanel
	private JLabel cpLabelSphereStepNow = new JLabel("Anzahl Schritte:");
	private JLabel cpLabelSphereStepMax = new JLabel("Gelöst bei Schritte:");
	
	//Auf devPanel
	private JLabel devLabelSphereStepNow = new JLabel("Anzahl Schritte:");
	private JLabel devLabelSphereStepMax = new JLabel("Gelöst bei Schritte:");
	
	//Animationsgechwindigkeits info
	private JLabel cpLabelAnimationSpeed= new JLabel("Animationsgeschwindigkeit:");
	
	//Slider für die Animationsgeschwindigkeit
	private JSlider cpSliderAnimationSpeed = new JSlider();
	
	//Fortschrittsinfo
	private JLabel cpLabelProgress = new JLabel("Lösungsfortschritt:");
	
	//Slider für den Lösungsweg
	private JSlider cpSliderForSteps = new JSlider();
	
	//Debugging Feld für eingabe von Kugel inklusive Bestätigungs Button
	private JTextField devTextFieldSphereDebugStringInput = new JTextField();
	private JButton devButtonSphereDebugStringInput = new JButton("Kugel übernehmen");

	//CheckBox für dev Panel, ob gelöst werden soll
	private JCheckBox devCheckBoxSolve = new JCheckBox("Kugel lösen");
	
	//Trennlinien
	private JSeparator cpSepSliders = new JSeparator();
	private JSeparator cpSepChangeWindow = new JSeparator();
	private JSeparator cpSepCamera = new JSeparator();
	
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
		windowOffsetX=(screenSize.width/2)+Settings.VERSCHIEBEFENSTERX;
		windowOffsetY=(screenSize.height/2)+Settings.VERSCHIEBEFENSTERY;
		
		//Lade das Icon
		Image icon = ImageIO.read(this.getClass().getResource("/img/icon_64.png"));
		
		//Setze das Icon für alle Fenster
		controlPanel.setIconImage(icon);
		editorPanel.setIconImage(icon);
		devPanel.setIconImage(icon);
		
		//Definiere die Grösse der Fenster
		controlPanel.setSize(350, 509);
		editorPanel.setSize(350, 509);
		devPanel.setSize(350, 509);
		
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
		
		controlPanel.addKeyListener(this);
		
		initControlpanel();
		initEditor();
		initDev();
	}

	/**
	 * Funktion zum Initialisieren des Controlpanels
	 */
	private void initControlpanel() throws IOException{
		
		//Versuche die Bilder für die Pfeiltasten zu laden
		ImageIcon imageUp = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/up.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon imageDown = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/down.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon imageRight = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/right.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon imageLeft = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/left.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		//Versuche die Bilder + und - zu laden
		ImageIcon imagePlus = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/plus.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon imageMinus = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/minus.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		//Versucge das Bild ? zu laden
		ImageIcon imageHelp = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/help.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		//Setze die Bilder der Pfeiltasten
		cpButtonUp.setIcon(imageUp);
		cpButtonDown.setIcon(imageDown);
		cpButtonRight.setIcon(imageRight);
		cpButtonLeft.setIcon(imageLeft);
		
		//Setze die Bilder + und -
		cpButtonAddOneStep.setIcon(imagePlus);
		cpButtonSubOneStep.setIcon(imageMinus);
		
		//Setze die Bilder ?
		cpHelpAnimation.setIcon(imageHelp);
		cpHelpEditor.setIcon(imageHelp);
		cpHelpFortschritt.setIcon(imageHelp);
		cpHelpKamera.setIcon(imageHelp);

		//Button zum neu füllen der Kugel Position und Grösse zuweisen
		cpButtonFillSphere.setBounds(20, 20, 145, 50);
		
		//Info ToolTip Hinzufügen
		cpButtonFillSphere.setToolTipText("Mischt die Kugel neu");
		
		//Button zum lösen der Kugel Position und Grösse zuweisen
		cpButtonSolve.setBounds(185, 20,145, 50);
		
		//Info ToolTip Hinzufügen
		cpButtonSolve.setToolTipText("Löst die Kugel bis ans Ende");
    	
		//Dem Label für die Animationsgeschwindigkeit Position und Grösse zuweisen
		cpLabelAnimationSpeed.setBounds(20,80,310,20);
		
		//Dem Slider für die Animationsgeschwindikeit Position und Grösse zuweisen
		cpSliderAnimationSpeed.setBounds(20,100,270,30);
		
		//Info ToolTip Hinzufügen
		cpSliderAnimationSpeed.setToolTipText("Wie schnell die Animation abläuft");
		
		//Help Animation Position ung Grösse zuweisen
		cpHelpAnimation.setBounds(300,100,30,30);
		
		//Info ToolTip Hinzufügen
		cpHelpAnimation.setToolTipText("Hilfe");
		
		//Help Fortschritt Position und Grösse zuweisen
		cpHelpFortschritt.setBounds(300,240,30,30);
		
		//Info ToolTip Hinzufügen
		cpHelpFortschritt.setToolTipText("Hilfe");
		
		//Help Kamera Position und Grösse zuweisen
		cpHelpKamera.setBounds(300,370,30,30);
		
		//Info ToolTip Hinzufügen
		cpHelpKamera.setToolTipText("Hilfe");
		
		//Help Editor Position und Grösse zuweisen
		cpHelpEditor.setBounds(300,440,30,30);
		
		//Info ToolTip Hinzufügen
		cpHelpEditor.setToolTipText("Hilfe");
		
		//Trennlinie Position und Grösse zuweisen
		cpSepSliders.setBounds(5,140,330,5);
    	
		//Den + und - Buttons Position und Grösse zuweisen
    	cpButtonSubOneStep.setBounds(20,190,30,30);
    	cpButtonAddOneStep.setBounds(300,190,30,30);
    	
    	//Info ToolTip Hinzufügen
    	cpButtonSubOneStep.setToolTipText("Im Lösungsweg einen Schritt zurück");
    	cpButtonAddOneStep.setToolTipText("Im Lösungsweg einen Schritt weiter");
    	
    	
    	//Dem Positions Slider Position und Grösse zuweisen
    	cpSliderForSteps.setBounds(60,190,230,30);
    	
    	//Info ToolTip Hinzufügen
    	cpSliderForSteps.setToolTipText("Position im Lösungsweg");
    	
    	//Dem Infotext Position und Grösse zuweisen
    	cpLabelProgress.setBounds(20,160,310,20);
    	
    	//Den Labels mit Infos zur Kugel Position und Grösse zuweisen
    	cpLabelSphereStepNow.setBounds(20,230,200,20);
    	cpLabelSphereStepMax.setBounds(20,250,200,20);
    	
    	//Info ToolTip Hinzufügen
    	cpLabelSphereStepNow.setToolTipText("Aktuelle Position im Lösungsweg");
    	cpLabelSphereStepMax.setToolTipText("Position bei der die Kugel gelöst ist");
    	
    	//Dem Slider für den Lösungsweg diverse Einstellungen vornehmen
    	cpSliderForSteps.setMinimum(0);    //stellt den Minimalwert auf 0 ein
    	cpSliderForSteps.setMaximum(0);  //stellt den Maximalwert auf 0 ein
    	cpSliderForSteps.setValue(0);     //selektiert den Wert 0
    	cpSliderForSteps.setMinorTickSpacing(5); //Abstände im Feinraster
    	cpSliderForSteps.setMajorTickSpacing(10); //Abstände im Grossraster
    	cpSliderForSteps.setSnapToTicks(false); //deaktiviert das automatische Versetzen / Einrasten im Raster
    	cpSliderForSteps.setExtent(0); //der Zeiger verspringt jedesmal 0 Einheiten
    	cpSliderForSteps.setPaintLabels(false);  //Zahlen werden nicht angezeigt
    	cpSliderForSteps.setPaintTicks(false);    //Striche werden nicht angezeigt
    	
    	//Zusätzlich wird ein ChangeListener hinzugefügt, damit der Wert im Manager geändert werden kann
    	cpSliderForSteps.addChangeListener(new ChangeListener() {
    	      public void stateChanged(ChangeEvent evt) {
    	    	  
    	    	  //Statische Variable im Manager ändern
    	          m.cpSliderChangeState(cpSliderForSteps.getValue());
    	          
    	        }
    	      });
    	
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
    	
    	//Trennlinie
    	cpSepCamera.setBounds(5,280,330,5);
    	
    	//Dem Button zum zurücksetzen der Kamera Position und Grösse zuweisen
    	cpButtonResetView.setBounds(210,290,120,50);
    	
    	//Info ToolTip Hinzufügen
    	cpButtonResetView.setToolTipText("Setzt die Position der Kamera zurück");
    	
    	//Den 4 Buttons mit Pfeilen Position und Grösse zuweisen
    	cpButtonUp.setBounds(150,290,50,50);
    	cpButtonLeft.setBounds(90,350,50,50);
    	cpButtonRight.setBounds(210,350,50,50);
    	cpButtonDown.setBounds(150,350,50,50);
    	  
    	//Info ToolTip Hinzufügen
    	cpButtonUp.setToolTipText("Dreht die Kugel nach oben");
    	cpButtonLeft.setToolTipText("Dreht die Kugel nach links");
    	cpButtonRight.setToolTipText("Dreht die Kugel nach rechts");
    	cpButtonDown.setToolTipText("Dreht die Kugel nach unten");
    	
    	//Trennlinie
    	cpSepChangeWindow.setBounds(5,410,330,5);
    	
    	//ToolTip für Editor Button
    	cpChangeEditor.setToolTipText("Öffne den Editor um eine eigene Kugel zu erstellen");
    	
    	//Den Buttons für das Wechseln des Modus Position und Grösse zuweisen
		if(Settings.DEBUGMODE){
			cpChangeDev.setBounds(20,420,105,50);
		   	cpChangeEditor.setBounds(145,420,145,50);
		}else{
		   	cpChangeEditor.setBounds(20,420,270,50);
		}
    	
    	
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
    	controlPanel.add(cpSepSliders);
    	controlPanel.add(cpSliderForSteps);
    	controlPanel.add(cpLabelProgress);
    	controlPanel.add(cpLabelSphereStepNow);
    	controlPanel.add(cpLabelSphereStepMax);
    	controlPanel.add(cpSliderAnimationSpeed);
    	controlPanel.add(cpLabelAnimationSpeed);
    	controlPanel.add(cpSepCamera);
    	controlPanel.add(cpSepChangeWindow);
    	controlPanel.add(cpChangeDev);
    	controlPanel.add(cpChangeEditor);
    	controlPanel.add(cpHelpAnimation);
    	controlPanel.add(cpHelpFortschritt);
    	controlPanel.add(cpHelpKamera);
    	controlPanel.add(cpHelpEditor);
    	
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
        cpChangeDev.addActionListener(this);
        cpChangeEditor.addActionListener(this);
        cpHelpAnimation.addActionListener(this);
        cpHelpEditor.addActionListener(this);
        cpHelpFortschritt.addActionListener(this);
        cpHelpKamera.addActionListener(this);
        
        //Füge alle Objekten ein KeyListener hinzu
        addKeyListenerToAll();
        
        //Controlpanel neu Zeichnen
        controlPanel.repaint();
        
	}
	
	/**
	 * Funktion zum initialisieren des Editors
	 */
	private void initEditor() throws IOException{
			
		//Versuche das ? Icon zu laden
		ImageIcon imageHelp = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/help.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		//setzet das Icon
		editHelp.setIcon(imageHelp);
		
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
	
		//Help Editor Position und Grösse zuweisen
		editHelp.setBounds(300,440,30,30);
		
		//Info ToolTip Hinzufügen
		editHelp.setToolTipText("Hilfe");
		
		
		//Den Buttons für das Wechseln des Modus Position und Grösse zuweisen 
		if(Settings.DEBUGMODE){
			editChangeSphere.setBounds(20,330,145,50);
			editChangeDev.setBounds(185, 330, 145,50);
		}else{
			editChangeSphere.setBounds(20,330,300,50);
		}
		
		
		//Dem Label mit Infos zur Legalität der Kugel Position und Grösse zuweisen
		editLabelIsSphereLegal.setBounds(20,260,300,50);
		
		//Dem Label mit Selected Position und Grösse zuweisen
		editLabelSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
		
		//Alle Elemente zu editorPanel hinzufügen
		editorPanel.add(editHelp);
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
		editHelp.addActionListener(this);
		
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
	 * Fügt allen Objekten des Controlpanels ein KeyListener hinzu
	 */
	private void addKeyListenerToAll(){
		cpButtonFillSphere.addKeyListener(this);
		cpButtonSolve.addKeyListener(this);
		cpButtonUp.addKeyListener(this);
		cpButtonDown.addKeyListener(this);
		cpButtonRight.addKeyListener(this);
		cpButtonLeft.addKeyListener(this);
		cpButtonResetView.addKeyListener(this);
		cpButtonAddOneStep.addKeyListener(this);
		cpButtonSubOneStep.addKeyListener(this);
		cpChangeEditor.addKeyListener(this);
		cpChangeDev.addKeyListener(this);
		cpHelpAnimation.addKeyListener(this);
		cpHelpEditor.addKeyListener(this);
		cpHelpFortschritt.addKeyListener(this);
		cpHelpKamera.addKeyListener(this);
		cpLabelAnimationSpeed.addKeyListener(this);
		cpLabelProgress.addKeyListener(this);
		cpLabelSphereStepMax.addKeyListener(this);
		cpLabelSphereStepNow.addKeyListener(this);
		cpSepCamera.addKeyListener(this);
		cpSepChangeWindow.addKeyListener(this);
		cpSepSliders.addKeyListener(this);
		cpSliderAnimationSpeed.addKeyListener(this);
		cpSliderForSteps.addKeyListener(this);
	}
	
	/**
	 * Funktion zum wechseln des Anzeigemodus
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
		
		//Setze neues Maximum für Fortschrittsslider
		cpSliderForSteps.setMaximum(fertigBeiPosition);
		
		//Setze neuen Wert für Fortschrittsslider
		cpSliderForSteps.setValue(aktuellePosition);
		
		//Setzte Text neu fürs Controlpanel
		cpLabelSphereStepNow.setText("Aktuelle Position: "+aktuellePosition);
		cpLabelSphereStepMax.setText("Gelöst bei Position: "+fertigBeiPosition);
		
		//Setze Text neu fürs DevPanel
		devLabelSphereStepNow.setText("Anzahl Position: "+aktuellePosition);
		devLabelSphereStepMax.setText("Gelöst bei Position: "+fertigBeiPosition);
		
	}
	
	/**
	 * Funktion zum aktualisieren des Lösungsstatus
	 * @param aktuellePosition
	 * @param solveButton
	 */
	protected void updateSolvingState(int aktuellePosition, boolean solveButton){
		
		//Wenn Button aktiv
		if(solveButton){
			
			//Text == "Lösungsanimation pausieren""
			cpButtonSolve.setText("<html>Lösungsanimation<br>pausieren</html>");
			
		}else
		
		//Wenn Position == 0 ist	
		if(aktuellePosition==0){
			
			//Text == "Lösungsanimation starten"
			cpButtonSolve.setText("<html>Lösungsanimation<br>starten</html>");
			
		//Sonst	
		}else{
			
			//Text == "Lösungsanimation fortsetzen"
			cpButtonSolve.setText("<html>Lösungsanimation<br>fortsetzen</html>");
			
		}
		
	}
	
	/**
	 * Updated die Position des Textes selected im Editor
	 */
	private void updateSelectedPosition(){
		
		//Setze neue Position
		editLabelSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
		
		//Aktuallisiere das editorPanel
		editorPanel.repaint();
		
	}
	
	/**
	 * Updated im Editor die Info bezüglich der Legalität der Kugel
	 * @param isOk
	 */
	protected void updateInfoIsSphereAllowed(boolean isOk){
		
		//Wenn Kugel ok ist
		if(isOk){
			
			//Info Text
			editLabelIsSphereLegal.setText("Die Kugel enthält keine Fehler");
			
			//Text von Button zum zurück kehren
			editChangeSphere.setText("Kugel übernehmen");
			
		//Wenn nicht	
		}else{
			
			//Info Text
			editLabelIsSphereLegal.setText("Die Kugel ist nicht gültig");
			
			//Text von Button zum zurück kehren
			editChangeSphere.setText("abbrechen und zurück zur Kugel");
			
		}
		
	}
	
	/**
	 * Die ActionListener-Methode für die Buttons
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
        	m.changeRotationAngle(-5,0);
        	
        //Ist der Button nach Rechts gedrückt?	
        }else if (z.getSource() == cpButtonRight){
        	
        	//Kugel nach rechts drehen
        	m.changeRotationAngle(0,5);
        	
        //Ist der Button nach Links gedrückt?
        }else if (z.getSource() == cpButtonLeft){
        	
        	//Kugel nach links drehen
        	m.changeRotationAngle(0,-5);
        	
        //Ist der Button nach Unten gedrückt?	
        }else if (z.getSource() == cpButtonDown){
        	
        	//Kugel nach unten drehen
        	m.changeRotationAngle(5,0);
        	
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
        	
        //Ist der Button gedrückt um die Animationshilfe zu starten?
        }else if (z.getSource() == cpHelpAnimation){
        	
        	//Animation stoppen
        	m.stopAnimationToEnd();
        	
        	//Help fenster öffnen
        	new Help(0);
        
        //Ist der Button gedückt um die Editorshilfe zu starten?
        }else if (z.getSource() == cpHelpEditor || z.getSource() == editHelp){
        	
        	//Animation stoppen
        	m.stopAnimationToEnd();
        	
        	//Help fenster öffnen
        	new Help(3);
        
        //Ist der Button gedückt um die Fortschrittshilfe zu starten?
        }else if (z.getSource() == cpHelpFortschritt){
        	
        	//Animation stoppen
        	m.stopAnimationToEnd();
        	
        	//Help fenster öffnen
        	new Help(1);	
        	
        //Ist der Button gedückt um die Kamerahilfe zu starten?
        }else if (z.getSource() == cpHelpKamera){
        	
        	//Animation stoppen
        	m.stopAnimationToEnd();
        	
        	//Help fenster öffnen
        	new Help(2);	
        }
		
	}

	/**
	 * Dreht bei betätigen der Pfeiltasten die Kugel
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		
		//Wenn Pfeiltasten nach Oben gedrückt wurden
		if(key.getKeyCode() == KeyEvent.VK_UP){
			
			//Drehe nach oben
			m.changeRotationAngle(-1, 0);
			
		}else
		
		//Wenn Pfeiltaste nach Unten gedrückt wurde	
		if(key.getKeyCode() == KeyEvent.VK_DOWN){
			
			//Drehe nach unten
			m.changeRotationAngle(1, 0);
			
		}else
			
		//Wenn Pfeiltaste nach Links gedrückt wurde	
		if(key.getKeyCode() == KeyEvent.VK_LEFT){
			
			//Drehen nach links
			m.changeRotationAngle(0, -1);
			
		}else
			
		//Wenn Pfeiltaste nach Rechts gedrückt wurde
		if(key.getKeyCode() == KeyEvent.VK_RIGHT){
			
			//Drehen nach rechts
			m.changeRotationAngle(0, 1);
			
		}
		
	}

	//Die Methoden müssen da sein, wegen dem KeyListener
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}

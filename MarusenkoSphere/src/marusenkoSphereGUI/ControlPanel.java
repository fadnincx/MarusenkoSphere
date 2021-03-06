package marusenkoSphereGUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import marusenkoSphere.Settings;

/**
 * Diese Klasse ist für die Darstellung des 2. Fensters, des Kontolpanels zuständig
 * 
 * Zusätzlich ist ein ActionListener implementiert um auf die Betätigung der Buttons zu reagieren
 * Ein KeyListener sorgt dafür, dass auf Tastatur eingaben gemacht werden können
 */

public class ControlPanel implements ActionListener{

	//Manager
	private Manager m;
	
	//Eigentliche Panels
	private JPanel controlPanel = new JPanel();
	private JPanel editorPanel = new JPanel();
	private JPanel devPanel = new JPanel();
	
	//Button zum neu mischen
	private JButton cpButtonFillSphere = new JButton("<html>Die Kugel<br>neu mischen</html>");
	
	//Button zum lösen bis ans ende
	private JButton cpButtonSolve = new JButton("<html>Lösungsanimation<br>starten</html>");


	//Button um Kamera zurück zu setzten
	private JButton cpButtonResetView = new JButton("Ansicht zurücksetzen");
	private JButton editButtonResetView = new JButton("Ansicht zurücksetzen");
	
	//Schritte vorwärts und zurück gehen
	private JButton cpButtonAddOneStep = new JButton();
	private JButton cpButtonSubOneStep = new JButton();
	
	//Editor switchButton
	private JToggleButton editViewSwitch2D = new JToggleButton("2D");
	private JToggleButton editViewSwitch3D = new JToggleButton("3D");
	private JLabel editViewSwitch = new JLabel("Ansicht");
	
	//Zu Modus WechselButton
	
	//auf Controlpanel
	private JButton cpChangeEditor = new JButton("zum Editor");
	private JButton cpChangeDev = new JButton("Dev");
	
	//Auf Editorpanel
	private JButton editChangeDev = new JButton("Dev");
	private JButton editChangeSphere = new JButton("zurück zur Kugel");
	
	//Auf Devpanel
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

		//Definiere die Grösse und Position der Panels		
		controlPanel.setBounds(640,20,350,518);
		editorPanel.setBounds(640,20,350,518);
		devPanel.setBounds(640,20,350,518);
		
		//Setze nur das controlPanel sichtbar, die anderen auch unsichtbar
		controlPanel.setVisible(true);
		editorPanel.setVisible(false);
		devPanel.setVisible(false);
		
		//Setzte Layout = null
		controlPanel.setLayout(null);
		editorPanel.setLayout(null);
		devPanel.setLayout(null);
		
		//Setze KeyListener
		controlPanel.addKeyListener(m.kmm);
		editorPanel.addKeyListener(m.kmm);
		
		//Füge Panels dem Hauptfensterhinzu
		m.mainFrame.add(controlPanel);
		m.mainFrame.add(editorPanel);
		m.mainFrame.add(devPanel);

		initControlpanel();
		initEditor();
		initDev();
	}

	/**
	 * Funktion zum Initialisieren des Controlpanels
	 */
	private void initControlpanel() throws IOException{

		//Versuche die Bilder + und - zu laden
		ImageIcon imagePlus = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/plus.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon imageMinus = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/minus.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		
		//Setze die Bilder + und -
		cpButtonAddOneStep.setIcon(imagePlus);
		cpButtonSubOneStep.setIcon(imageMinus);
		

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
		cpSliderAnimationSpeed.setBounds(20,100,310,30);
		
		//Info ToolTip Hinzufügen
		cpSliderAnimationSpeed.setToolTipText("Wie schnell die Animation abläuft");

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
    	cpSepCamera.setBounds(5,290,330,5);
    	
    	//Dem Button zum zurücksetzen der Kamera Position und Grösse zuweisen
    	cpButtonResetView.setBounds(20,315,310,50);
    	
    	//Info ToolTip Hinzufügen
    	cpButtonResetView.setToolTipText("Setzt die Position der Kamera zurück");
    	
    	//Trennlinie
    	cpSepChangeWindow.setBounds(5,390,330,5);
    	
    	//ToolTip für Editor Button
    	cpChangeEditor.setToolTipText("Öffne den Editor um eine eigene Kugel zu erstellen");
    	
    	//Den Buttons für das Wechseln des Modus Position und Grösse zuweisen
		if(Settings.DEBUGMODE){
			cpChangeDev.setBounds(20,410,105,50);
		   	cpChangeEditor.setBounds(145,410,185,50);
		}else{
		   	cpChangeEditor.setBounds(20,410,310,50);
		}
    	
    	
    	//Füge jedes Objekt dem Controlpanel hinzu
    	controlPanel.add(cpButtonFillSphere);
    	controlPanel.add(cpButtonSolve);
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
    	
    	//Füge den Buttons ein ActionListener hinzu
    	cpButtonFillSphere.addActionListener(this);
    	cpButtonSolve.addActionListener(this);
    	cpButtonResetView.addActionListener(this);
    	cpButtonAddOneStep.addActionListener(this);
    	cpButtonSubOneStep.addActionListener(this);
        cpChangeDev.addActionListener(this);
        cpChangeEditor.addActionListener(this);

        //Füge alle Objekten ein KeyListener hinzu
        cpButtonFillSphere.addKeyListener(m.kmm);
		cpButtonSolve.addKeyListener(m.kmm);
		cpButtonResetView.addKeyListener(m.kmm);
		cpButtonAddOneStep.addKeyListener(m.kmm);
		cpButtonSubOneStep.addKeyListener(m.kmm);
		cpChangeEditor.addKeyListener(m.kmm);
		cpChangeDev.addKeyListener(m.kmm);
		cpLabelAnimationSpeed.addKeyListener(m.kmm);
		cpLabelProgress.addKeyListener(m.kmm);
		cpLabelSphereStepMax.addKeyListener(m.kmm);
		cpLabelSphereStepNow.addKeyListener(m.kmm);
		cpSepCamera.addKeyListener(m.kmm);
		cpSepChangeWindow.addKeyListener(m.kmm);
		cpSepSliders.addKeyListener(m.kmm);
		cpSliderAnimationSpeed.addKeyListener(m.kmm);
		cpSliderForSteps.addKeyListener(m.kmm);
        
        //Controlpanel neu Zeichnen
        controlPanel.repaint();
        
	}
	
	/**
	 * Funktion zum initialisieren des Editors
	 */
	private void initEditor() throws IOException{
		
		
		//Den farbigen Buttons die Farbe als Hintergrund zuweisen
		editButtonColor0.setBackground(color0);
		editButtonColor1.setBackground(color1);
		editButtonColor2.setBackground(color2);
		editButtonColor3.setBackground(color3);
		editButtonColor4.setBackground(color4);
		editButtonColor5.setBackground(color5);
		editButtonColor6.setBackground(color6);
		editButtonColor7.setBackground(color7);
		
		
		//Wenn Touchscreen, dann die Farbbuttons anderst plazieren
		if(Settings.touchmode){
			editButtonColor0.setBounds(20, 20, 150, 60);
			editButtonColor1.setBounds(170, 20, 150, 60);
			editButtonColor2.setBounds(20, 80, 150, 60);
			editButtonColor3.setBounds(170, 80, 150, 60);
			editButtonColor4.setBounds(20, 140, 150, 60);
			editButtonColor5.setBounds(170, 140, 150, 60);
			editButtonColor6.setBounds(20, 200, 150, 60);
			editButtonColor7.setBounds(170, 200, 150, 60);
			
			
			//Rand entfernen
			editButtonColor0.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor1.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor2.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor3.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor4.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor5.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor6.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor7.setBorder(BorderFactory.createEmptyBorder());
			
			//Label soll nicht sichtbar sein
			editLabelSelected.setBounds(0,0,0,0);
		}else{
			
			//Den farbigen Buttons die Position und Grösse zuweisen
			editButtonColor0.setBounds(20, 20, 150, 20);
			editButtonColor1.setBounds(20, 50, 150, 20);
			editButtonColor2.setBounds(20, 80, 150, 20);
			editButtonColor3.setBounds(20, 110, 150, 20);
			editButtonColor4.setBounds(20, 140, 150, 20);
			editButtonColor5.setBounds(20, 170, 150, 20);
			editButtonColor6.setBounds(20, 200, 150, 20);
			editButtonColor7.setBounds(20, 230, 150, 20);
			
			//Dem Label mit Selected Position und Grösse zuweisen
			editLabelSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
			
		}	
		
		//Plaziere die Switchbutton
		editViewSwitch3D.setBounds(20, 300, 50, 50);
		editViewSwitch2D.setBounds(70, 300, 50, 50);
		editViewSwitch.setBounds(140, 300, 50, 50);
		
		
		//Select 3D
		editViewSwitch3D.setSelected(true);
		editViewSwitch2D.setSelected(false);
		
		//Enable nur 2D
		editViewSwitch3D.setEnabled(false);
    	editViewSwitch2D.setEnabled(true);
		
    	
    	//Füge ToolTip Hinzu
    	editViewSwitch.setToolTipText("Wechselt die Ansicht des Editors");
    	editViewSwitch2D.setToolTipText("Wechselt die Ansicht des Editors auf 2D");
    	editViewSwitch3D.setToolTipText("Wechselt die Ansicht des Editors auf 3D");
		
    	//Setzet Kamerazurücksetzen button
    	editButtonResetView.setBounds(20, 360, 310, 50);
		
		//Den Buttons für das Wechseln des Modus Position und Grösse zuweisen 
		if(Settings.DEBUGMODE){
			editChangeSphere.setBounds(20,420,145,50);
			editChangeDev.setBounds(185, 420, 145,50);
		}else{
			editChangeSphere.setBounds(20,420,310,50);
		}
		
		
		//Dem Label mit Infos zur Legalität der Kugel Position und Grösse zuweisen
		editLabelIsSphereLegal.setBounds(20,260,300,50);
		
		
		
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
		editorPanel.add(editViewSwitch);
		editorPanel.add(editViewSwitch2D);
		editorPanel.add(editViewSwitch3D);
		editorPanel.add(editButtonResetView);
		
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
		editViewSwitch3D.addActionListener(this);
		editViewSwitch2D.addActionListener(this);
		editButtonResetView.addActionListener(this);
		editButtonColor0.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor1.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor2.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor3.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor4.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor5.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor6.addMouseListener(new KeyboardMouseManager(m, this));
		editButtonColor7.addMouseListener(new KeyboardMouseManager(m, this));
		
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
	protected int getClickedButton(MouseEvent m){
		if(m.getSource()==editButtonColor0){
			return 0;
		}else if(m.getSource()==editButtonColor1){
			return 1;
		}else if(m.getSource()==editButtonColor2){
			return 2;
		}else if(m.getSource()==editButtonColor3){
			return 3;
		}else if(m.getSource()==editButtonColor4){
			return 4;
		}else if(m.getSource()==editButtonColor5){
			return 5;
		}else if(m.getSource()==editButtonColor6){
			return 6;
		}else if(m.getSource()==editButtonColor7){
			return 7;
		}else{
			return -1;
		}
	}
	protected void updateTouchscreen(){
		if(Settings.touchmode){
			editButtonColor0.setBounds(20, 20, 150, 60);
			editButtonColor1.setBounds(170, 20, 150, 60);
			editButtonColor2.setBounds(20, 80, 150, 60);
			editButtonColor3.setBounds(170, 80, 150, 60);
			editButtonColor4.setBounds(20, 140, 150, 60);
			editButtonColor5.setBounds(170, 140, 150, 60);
			editButtonColor6.setBounds(20, 200, 150, 60);
			editButtonColor7.setBounds(170, 200, 150, 60);
			
			editButtonColor0.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor1.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor2.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor3.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor4.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor5.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor6.setBorder(BorderFactory.createEmptyBorder());
			editButtonColor7.setBorder(BorderFactory.createEmptyBorder());
			
			editLabelSelected.setBounds(0,0,0,0);
		}else{
			//Den farbigen Buttons die Position und Grösse zuweisen
			editButtonColor0.setBounds(20, 20, 150, 20);
			editButtonColor1.setBounds(20, 50, 150, 20);
			editButtonColor2.setBounds(20, 80, 150, 20);
			editButtonColor3.setBounds(20, 110, 150, 20);
			editButtonColor4.setBounds(20, 140, 150, 20);
			editButtonColor5.setBounds(20, 170, 150, 20);
			editButtonColor6.setBounds(20, 200, 150, 20);
			editButtonColor7.setBounds(20, 230, 150, 20);
			
			//Dem Label mit Selected Position und Grösse zuweisen
			editLabelSelected.setBounds(180, (m.getSelectedColor()*30)+20, 100, 20);
			
			
		}
		
		editorPanel.repaint();
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
	        	editViewSwitch3D.setSelected(false);
	        	editViewSwitch2D.setEnabled(false);
	        	editViewSwitch3D.setEnabled(true);
	        	editButtonResetView.setEnabled(false);
	        	editorPanel.setVisible(true);
				break;
			case 3:
	        	editViewSwitch2D.setSelected(false);
	        	editViewSwitch3D.setEnabled(false);
	        	editViewSwitch2D.setEnabled(true);
	        	editButtonResetView.setEnabled(true);
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
	protected void updateSelectedPosition(){
		
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
			
			//Kugel mischen
			m.fillSphere();
		
		//Ist der Button zum start der Animation gedrückt?
        }else if (z.getSource() == cpButtonSolve){
        	
        	//starte/stoppe Animation
        	m.changeRunAnimationToEnd();
        	
        //Ist der Button zum übernehmen des DebuggingStrings gedrückt?
        }else if (z.getSource() == devButtonSphereDebugStringInput){
        	
        	//Debugging Option um Kugel zu Status zu setzten
        	String in = devTextFieldSphereDebugStringInput.getText();
        	m.fillSphereFromDevString(in, devCheckBoxSolve.isSelected());
        	
        //Ist der Button zum Zurücksetzen der Drehung gedrückt?
        }else if (z.getSource() == cpButtonResetView){
        	
        	//Drehen zurück setzen
        	m.resetRotationAngle();
        	
        //Ist der Button zum Zurücksetzen der Drehung gedrückt?
        }else if (z.getSource() == editButtonResetView){
        	
        	//Drehen zurück setzen
        	m.resetRotationAngle();	
        	
        //Ist der Button zum im Lösungsweg einen Schritt weiter zu gehen gedrückt?	
        }else if (z.getSource() == cpButtonAddOneStep){
        	
        	//X zu Queue hinzufügen
        	m.addToSolvingQueue('x');
        	
		//Ist der Button zum im Lösungseg einen Schritt zurück zu gehen gedrückt?	
        }else if (z.getSource() == cpButtonSubOneStep){
        	
        	//X zu Queue hinzufügen
        	m.addToSolvingQueue('y');
        	
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
        	
        	//Zum Editor wechseln
        	m.changeToMode(2);
        	
        //Ist der Button gedrückt um in den Editor-Modus zu wechseln?	
        }else if (z.getSource() == cpChangeEditor|| z.getSource() == devChangeEditor){
        	
        	//Zum Editor wechseln
        	m.changeToMode(3);
        	
        //Ist der Button gedrückt um in den Normalen-Modus zu wechseln?	
        }else if (z.getSource() == devChangeSphere|| z.getSource() == editChangeSphere){
        	
        	//Zur Kugelansicht wechseln
        	m.changeToMode(0);
        	
        }else if (z.getSource() == editViewSwitch2D){
        	
        	m.changeToMode(1);
        	editViewSwitch3D.setSelected(false);
        	editViewSwitch2D.setEnabled(false);
        	editViewSwitch3D.setEnabled(true);
        	editButtonResetView.setEnabled(false);
        }else if (z.getSource() == editViewSwitch3D){
        	
        	m.changeToMode(3);
        	editViewSwitch2D.setSelected(false);
        	editViewSwitch3D.setEnabled(false);
        	editViewSwitch2D.setEnabled(true);
        	editButtonResetView.setEnabled(true);
        }
		
	}


}

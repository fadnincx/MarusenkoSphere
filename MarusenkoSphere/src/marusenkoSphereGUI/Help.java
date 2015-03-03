package marusenkoSphereGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import marusenkoSphere.Settings;

/**
 * Zeigt das HelpCenter an
 */
public class Help extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8461029529626833515L;

	/**
	 * Erstellt das HilfeCenter als Parameter tab wird der gewünschte start Tab übergeben
	 */
	public Help(final int tab){
		
		//Starte die Grafik in einem neuen Thread...
        SwingUtilities.invokeLater(new Runnable() {
        	
            public void run() {
            	
                //Starte die Grafik --> Wenn Fehler beim laden der Grafiken auftreten, dann Fehler ausgeben
            	try {
            		
					createAndShowGUI(tab);
					
				} catch (IOException e) {
					
					//Wenn nötig Fehler ausgeben
					e.printStackTrace();
					
				}
            }
        });
	}
	/**
	 * Erstelle das Grafikfenster
	 * @param tab : welcher Tab angezeigt werden soll
	 * @throws IOException 
	 */
	private void createAndShowGUI(int tab) throws IOException {
		
        //Erstelle Fenster
        JDialog frame = new JDialog();
        
        frame.setTitle(Settings.TITEL+" - Hilfecenter");
        
        Image icon = ImageIO.read(this.getClass().getResource("/img/icon_64.png"));
        
        frame.setIconImage(icon);
        
        //Setzte, dass bei Klick auf X nur Fenster geschlossen wird
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         
        //Füge dem Fenster die Tabs hinzu
        frame.add(new HelpTabbed(tab), BorderLayout.CENTER);
         
        //Setzte die Fenstergrösse optimal
        frame.pack();
        
        //Bekomme die Bildschirmdiemsionen
      	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      	//Setze die Position des Fensters
      	frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        
        //Setze das Fenster sichtbar
        frame.setVisible(true);
        
        frame.setResizable(false);
        
    }
	
	/**
	 * Klasse für die Generierung der Tabs
	 */
	class HelpTabbed extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -6506176482886760822L;

		/**
		 * Konstruktor für das erstellen der Tabs
		 * @param tab
		 */
		public HelpTabbed(int tab) throws IOException{
			
			//Setze das Layout --> super weil geerbte Klasse von JPanel
			super(new GridLayout(1, 1));
	         
			//Definiere das TabbedPane
	        JTabbedPane tabbedPane = new JTabbedPane();
	        
	        //Lade das Icon für die Tabs
	        ImageIcon icon = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/img/help.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	         
	        
	        //Erstelle das erste Panel
	        JComponent panel1 = createImgTab("/helpCenterPages/helpAnimation.png");
	        
	        //Setze die bevorzugte Grösse des Tabs
	        panel1.setPreferredSize(new Dimension(640, 480));
	        
	        //Füge das Panel mit Titel, Icon und ToolTip dem TabbedPane hinzu
	        tabbedPane.addTab("Animation", icon, panel1, "Hilfe zur Animation");
	        
	        //Setze so, dass darauf gewechselt werde kann
	        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	         
	        
	        //Erstelle das zweite Panel
	        JComponent panel2 = createImgTab("/helpCenterPages/helpFortschritt.png");
	        
	        //Setze die bevorzugte Grösse des Tabs
	        panel2.setPreferredSize(new Dimension(640, 480));
	        
	        //Füge das Panel mit Titel, Icon und ToolTip dem TabbedPane hinzu
	        tabbedPane.addTab("Lösungsfortschritt", icon, panel2, "Hilfe zum Lösungsfortschritt");
	        
	        //Setze so, dass darauf gewechselt werde kann
	        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
	         
	        
	        //Erstelle das dritte Panel
	        JComponent panel3 = createImgTab("/helpCenterPages/helpEditor.png");
	        
	        //Setze die bevorzugte Grösse des Tabs
	        panel3.setPreferredSize(new Dimension(640, 480));
	        
	        //Füge das Panel mit Titel, Icon und ToolTip dem TabbedPane hinzu
	        tabbedPane.addTab("Editor", icon, panel3, "Hilfe für den Editor");
	        
	        //Setze so, dass darauf gewechselt werde kann
	        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
	         
	        
	        //Erstelle das vierte Panel
	        JComponent panel4 = createImgTab("/helpCenterPages/helpEditorPanel.png");
	        
	        //Setze die bevorzugte Grösse des Tabs
	        panel4.setPreferredSize(new Dimension(640, 480));
	        
	        //Füge das Panel mit Titel, Icon und ToolTip dem TabbedPane hinzu
	        tabbedPane.addTab("Editorpanel", icon, panel4, "Hilfe für das Editorpanel");
	        
	        //Setze so, dass darauf gewechselt werde kann
	        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
	         
	        
	        //Füge das tabbedPane zu sich selbst hinzu
	        this.add(tabbedPane);
	         
	        //Aktiviere das scrollen, falls benötigt
	        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	        
	        //Wähle aus, welcher Tab zubeginn angezeigt werden soll
	        tabbedPane.setSelectedIndex(tab);
		        
		 }

		/**
		 * Erstelle den Tab mit Bild und gibt ihn als JComponet zurück
		 */
	    private JComponent createImgTab(String imgUrl) throws IOException{
	    	
	    	//JPanel erstellen
	    	JPanel panel = new JPanel(false);
	    	
	    	//Lade das Hilfebild
	    	ImageIcon img = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(imgUrl))).getImage().getScaledInstance(640, 480, Image.SCALE_SMOOTH));
	    	
	    	//Erstelle Label in welchem das Bild angezeigt wird
	    	JLabel label = new JLabel(img);
	    	
	    	//Füge Label dem Panel hinzu
	    	panel.add(label);
	    	
	    	//Setze das Layout des Panels
	        panel.setLayout(new GridLayout(1, 1));
	        
	        //Gib das Panel zurück
	    	return panel;
	    	
	    }

	}

}

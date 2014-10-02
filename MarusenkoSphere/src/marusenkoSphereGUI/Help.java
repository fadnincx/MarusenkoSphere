package marusenkoSphereGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Help extends JPanel{

	public Help(final int tab){
		//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        createAndShowGUI(tab);
            }
        });
	}
	private void createAndShowGUI(int tab) {
        //Create and set up the window.
        JFrame frame = new JFrame("MarusenkoSphere Hilfecenter");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         
        //Add content to the window.
        frame.add(new HelpTabbed(tab), BorderLayout.CENTER);
         
        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	class HelpTabbed extends JPanel{
		public HelpTabbed(int tab){
			super(new GridLayout(1, 1));
	         
		        JTabbedPane tabbedPane = new JTabbedPane();
		        ImageIcon icon = createImageIcon("/img/help.png");
		         
		        JComponent panel1 = createHelpAnimation();
		        tabbedPane.addTab("Animation", icon, panel1,
		                "Hilfe zur Animation");
		        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		         
		        JComponent panel2 = createHelpFortschritt();
		        tabbedPane.addTab("Lösungsfortschritt", icon, panel2,
		                "Hilfe zum Lösungsfortschritt");
		        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		         
		        JComponent panel3 = createHelpKamera();
		        tabbedPane.addTab("Kamera", icon, panel3,
		                "Hilfe zur Kamera");
		        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		         
		        JComponent panel4 = createHelpEditor();
		        panel4.setPreferredSize(new Dimension(640, 480));
		        tabbedPane.addTab("Editor", icon, panel4,
		                "Hilfe für den Editor");
		        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		         
		        //Add the tabbed pane to this panel.
		        add(tabbedPane);
		         
		        //The following line enables to use scrolling tabs.
		        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		        
		        
		        tabbedPane.setSelectedIndex(tab);
		        
		 }
		protected JComponent makeTextPanel(String text) {
	        JPanel panel = new JPanel(false);
	        JLabel filler = new JLabel(text);
	        filler.setHorizontalAlignment(JLabel.CENTER);
	        panel.setLayout(new GridLayout(1, 1));
	        panel.add(filler);
	        return panel;
	    }
	     
	    /** Returns an ImageIcon, or null if the path was invalid. */
	    protected ImageIcon createImageIcon(String path) {
			try {
				return new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(path))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	    }
	    private JComponent createHelpAnimation(){
	    	JPanel panel = new JPanel(false);
	    	try {
	    		ImageIcon img = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/helpCenterPages/helpAnimation.png"))).getImage().getScaledInstance(640, 480, Image.SCALE_SMOOTH));
	    		JLabel label = new JLabel(img);
	    		panel.add(label);
	    	} catch (IOException e) {
				e.printStackTrace();
	
			}
	        panel.setLayout(new GridLayout(1, 1));
	    	return panel;
	    }
	    private JComponent createHelpFortschritt(){
	    	JPanel panel = new JPanel(false);
	    	try {
	    		ImageIcon img = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/helpCenterPages/helpFortschritt.png"))).getImage().getScaledInstance(640, 480, Image.SCALE_SMOOTH));
	    		JLabel label = new JLabel(img);
	    		panel.add(label);
	    	} catch (IOException e) {
				e.printStackTrace();
	
			}
	        panel.setLayout(new GridLayout(1, 1));
	    	return panel;
	    }
	    private JComponent createHelpKamera(){
	    	JPanel panel = new JPanel(false);
	    	try {
	    		ImageIcon img = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/helpCenterPages/helpKamera.png"))).getImage().getScaledInstance(640, 480, Image.SCALE_SMOOTH));
	    		JLabel label = new JLabel(img);
	    		panel.add(label);
	    	} catch (IOException e) {
				e.printStackTrace();
	
			}
	        panel.setLayout(new GridLayout(1, 1));
	    	return panel;
	    }
	    private JComponent createHelpEditor(){
	    	JPanel panel = new JPanel(false);
	    	JLabel filler = new JLabel("Editor Hilfe");
	        filler.setHorizontalAlignment(JLabel.CENTER);
	        panel.setLayout(new GridLayout(1, 1));
	        panel.add(filler);
	    	return panel;
	    }
	    
	}
}

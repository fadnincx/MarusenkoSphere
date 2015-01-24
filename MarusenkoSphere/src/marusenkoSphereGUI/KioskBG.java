package marusenkoSphereGUI;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class KioskBG{
	
	
	private JFrame kioskPanel = new JFrame("MarusenkoSphere Kiosk");
	private JFrame overlay = new JFrame("MarusenkoSphere Overlay");
	
	private JLabel titel = new JLabel("MarusenkoSphere", JLabel.CENTER);
	private JLabel copyright = new JLabel("Copyright by Marcel Würsten", JLabel.CENTER);

	protected KioskBG(){
		
		kioskPanel.getContentPane().setBackground(new Color(255,255,255));
		kioskPanel.setExtendedState(Frame.MAXIMIZED_BOTH);
		kioskPanel.setResizable(false);
		kioskPanel.setUndecorated(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		kioskPanel.setLocation(0, 0);
		kioskPanel.setSize(dim);
		kioskPanel.setVisible(true);
		kioskPanel.setFocusable(false);
		kioskPanel.setFocusableWindowState(false);
		
		
		overlay.setUndecorated(true);
		overlay.setBackground(new Color(0, 0, 0, 0));
		overlay.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		overlay.setResizable(false);
		
		overlay.setAlwaysOnTop(true);
		overlay.setLocation(0, 0);
		overlay.setSize(dim);
		
		overlay.setFocusable(false);
		overlay.setFocusableWindowState(false);
		overlay.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
		
		titel.setFont(new Font(titel.getFont().getName(), Font.PLAIN, 150));
		copyright.setFont(new Font(copyright.getFont().getName(), Font.PLAIN, 20));
		
		overlay.getContentPane().setLayout(new java.awt.BorderLayout());
		overlay.getContentPane().add(titel, java.awt.BorderLayout.NORTH);
		overlay.getContentPane().add(copyright, java.awt.BorderLayout.PAGE_END);
		
        
	
		overlay.setVisible(true);
		
	}
	protected void changeDebug(String s){
		copyright.setText(s);
	}
	
}

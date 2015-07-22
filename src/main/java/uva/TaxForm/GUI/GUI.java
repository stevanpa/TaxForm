package uva.TaxForm.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import uva.TaxForm.GUI.ActionListeners.LoadMenu;

public class GUI{

	public JFrame frame;
	public JPanel panel;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItemLoad, menuItemSave;
	final JFileChooser fc = new JFileChooser();
	
	public GUI() {
		
		FileFilter ft = new FileNameExtensionFilter("Tax Files", "tax");
		fc.addChoosableFileFilter(ft);
		
		//create frame
		frame = new JFrame( "TaxForm" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setPreferredSize( new Dimension(400, 600) );
		frame.setLayout( new FlowLayout( FlowLayout.CENTER ) );
		
		//show frame
		frame.pack();
		frame.setVisible(true);
		
		//set frame position on screen, center
		frame.setLocationRelativeTo(null);
		
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
				panel.revalidate();
			}
		});
		
		addMenu();
		addPanel();
	}
	
	public GUI resetFrame () {
		frame.getContentPane().removeAll();
		addMenu();
		addPanel();
		
		return this;
	}
	
	private void addMenu() {
		//create menu
		menuBar = new JMenuBar();
		
		//File
		menu = new JMenu( "File" );
		menu.setMnemonic( KeyEvent.VK_F );
		menu.getAccessibleContext().setAccessibleDescription( "File menu" );
		menuBar.add(menu);
		
		//Load File
		menuItemLoad = new JMenuItem( "Load File" );
		menuItemLoad.setMnemonic(KeyEvent.VK_L);
		menuItemLoad.getAccessibleContext().setAccessibleDescription( "Load a new TaxForm file" );
		menu.add(menuItemLoad);
		
		//Save File
		menuItemSave = new JMenuItem( "Save File" );
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.getAccessibleContext().setAccessibleDescription( "Save TaxForm file" );
		menu.add(menuItemSave);
		
		frame.setJMenuBar(menuBar);

		menuItemLoad.addActionListener(new LoadMenu(fc, frame, this));
	}

	public void addPanel() {
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		panel.setBackground(new Color(0, 200, 0));
		frame.add(panel, BorderLayout.CENTER);
		
		frame.pack();
		frame.revalidate();
	}
}

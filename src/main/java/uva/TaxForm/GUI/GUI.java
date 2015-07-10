package uva.TaxForm.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUI{

	JFrame frame;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	final JFileChooser fc = new JFileChooser();
	
	public GUI() {
		
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
		
		addMenu();
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
		menuItem = new JMenuItem( "Load File" );
		menuItem.setMnemonic(KeyEvent.VK_L);
		menuItem.getAccessibleContext().setAccessibleDescription( "Load a new TaxForm file" );
		menu.add(menuItem);
		
		frame.setJMenuBar(menuBar);
		
		menuItem.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(frame);
				
				if ( returnVal == JFileChooser.APPROVE_OPTION ) {
					File file = fc.getSelectedFile();
					System.out.println(file.getName());
				} else {
					System.out.println("Cancelled");
				}
			}
		});
	}
	
	/*public void addContainerPanel() {
		
		containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		//containerPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		containerPanel.setBackground(new Color(0, 200, 0));
		frame.add(containerPanel);
		
		frame.pack();
		frame.revalidate();
	}
	
	public void addQuestion() {
		
		GUIQuestion question = new GUIQuestion("test label", "boolean");
		question.setPreferredSize(new Dimension(frame.getWidth(), 50));
		containerPanel.add( question );

		frame.pack();
		frame.revalidate();
	}*/
}

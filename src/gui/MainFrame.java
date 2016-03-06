package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame {
	
	public static JFrame frame;
	
	public MainFrame(){
		init();
	}
	
	public void init(){
		
		frame = new JFrame("JFreeChart Demo - Project idea for GSOC 16");
		frame.setLayout(new BorderLayout());
		frame.setSize(new Dimension(1200, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponents();
	}
	
	public void addComponents(){
		
		GraphButtons buttons = new GraphButtons();
		
		frame.add(buttons.getPanel(), BorderLayout.WEST);
		frame.add(buttons.getChart().getPanel(), BorderLayout.CENTER);
		frame.add(new BottomPanel(), BorderLayout.SOUTH);
	}
	
	public void show(){
		
		frame.setVisible(true);
	}

}

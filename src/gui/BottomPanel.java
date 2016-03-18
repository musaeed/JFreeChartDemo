package gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartUtilities;

public class BottomPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public JButton exit;
	public static JLabel label;
	
	public BottomPanel(){
		
		init();
		addActions();
	}
	
	public void init(){
		
		setLayout(new BorderLayout());
		exit = new JButton("Exit");
		label = new JLabel("Ready");
		
		JPanel east = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		east.add(exit);
		
		add(label, BorderLayout.WEST);
		add(east, BorderLayout.EAST);
	}
	
	public void addActions(){
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
	}
}

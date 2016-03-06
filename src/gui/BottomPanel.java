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
	public JButton save, exit;
	public static JLabel label;
	
	public BottomPanel(){
		
		init();
		addActions();
	}
	
	public void init(){
		
		setLayout(new BorderLayout());
		save = new JButton("Save as image");
		exit = new JButton("Exit");
		label = new JLabel("Ready");
		
		JPanel east = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		east.add(save);
		east.add(exit);
		
		add(label, BorderLayout.WEST);
		add(east, BorderLayout.EAST);
	}
	
	public void addActions(){
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(Chart.current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no chart open on the panel!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				FileDialog dialog = new FileDialog(MainFrame.frame , "Save as image" , FileDialog.SAVE);
				dialog.setVisible(true);

				String filename;

				try{

					filename = dialog.getFiles()[0].getAbsolutePath();

				} catch(Exception ee){
					return;
				}
				
				if(!filename.contains(".")){
					filename = filename + ".jpg";
				}
				
				File file = new File(filename);
				
				 try {
					ChartUtilities.saveChartAsJPEG( file, Chart.current, 800, 500 );
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
	}
}

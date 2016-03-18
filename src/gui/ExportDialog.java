package gui;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;

import org.jfree.chart.ChartUtilities;

public class ExportDialog {
	
	private JDialog dialog;
	private ColoredButton png, jpg, svg, pdf;
	
	
	public ExportDialog() {
	
		init();
		addActions();
	}
	
	public void init(){
		
		dialog = new JDialog();
		dialog.setTitle("Export as");
		dialog.setLayout(new GridLayout(4, 1));
		dialog.setModal(true);
		dialog.setSize(new Dimension(450, 500));
		dialog.setLocationRelativeTo(MainFrame.frame);
		
		png = ColoredButton.GetRandomButton("PNG", "export the graph as a png image");
		jpg = ColoredButton.GetRandomButton("JPEG", "export the graph as a jpeg image");
		svg = ColoredButton.GetRandomButton("SVG", "get the graph as a svg file format");
		pdf = ColoredButton.GetRandomButton("PDF", "get the graph as a pdf file");
		
		dialog.add(png);
		dialog.add(jpg);
		dialog.add(svg);
		dialog.add(pdf);
	}
	
	public void addActions(){
		
		png.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fd = new FileDialog(MainFrame.frame, "Enter the name of the file", FileDialog.SAVE);
				fd.setVisible(true);
				
				String filename;

				try{

					filename = fd.getFiles()[0].getAbsolutePath();

				} catch(Exception ee){
					return;
				}
				
				if(!filename.contains(".")){
					filename = filename + ".png";
				}
				
				File file = new File(filename);
				
				 try {
					ChartUtilities.saveChartAsPNG( file, Chart.current, 800, 500 );
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		
		jpg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(MainFrame.frame, "Enter the name of the file", FileDialog.SAVE);
				fd.setVisible(true);
				
				String filename;

				try{

					filename = fd.getFiles()[0].getAbsolutePath();

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
		
		svg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(MainFrame.frame, "Enter the name of the file", FileDialog.SAVE);
				fd.setVisible(true);
				
			}
		});
		
		pdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(MainFrame.frame, "Enter the name of the file", FileDialog.SAVE);
				fd.setVisible(true);
			}
		});
	}
	
	public void show(){
		
		dialog.setVisible(true);
	}

}

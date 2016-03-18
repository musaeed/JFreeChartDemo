package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

public class DataViewer {

	private JScrollPane scroll;
	private JTable table;
	private JButton export, close;
	private JDialog dialog;
	private JPanel bottomPanel;

	public DataViewer(){

		init();
		addActions();
	}

	public void init(){

		table = new JTable();
		scroll = new JScrollPane(table);
		export = new JButton("Export");
		close = new JButton("Close");
		dialog = new JDialog();
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		dialog.setTitle("Dataset");
		dialog.setSize(new Dimension(500, 500));
		dialog.setLocationRelativeTo(MainFrame.frame);
		dialog.setModal(true);
		dialog.setLayout(new BorderLayout());
		
		export.setIcon(ImageLoader.loadImage("images/export.png"));
		export.setToolTipText("Export the data to a csv file");

		bottomPanel.add(export);
		bottomPanel.add(close);

		dialog.add(scroll, BorderLayout.CENTER);
		dialog.add(bottomPanel, BorderLayout.SOUTH);
	}

	public void addActions(){

		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dialog.dispose();
			}
		});

		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(MainFrame.frame, "Export to csv format", FileDialog.SAVE);
				fd.setVisible(true);
				
				String filename;

				try{

					filename = fd.getFiles()[0].getAbsolutePath();

				} catch(Exception ee){
					return;
				}
				
				if(!filename.contains(".csv")){
					filename = filename + ".csv";
				}
				
				File file = new File(filename);
				PrintWriter pw = null;
				
				try {
					pw = new PrintWriter(file);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(MainFrame.frame, e);
					e1.printStackTrace();
				}
				

				if(Chart.current.getPlot() instanceof PiePlot){

					PiePlot plot = (PiePlot) Chart.current.getPlot();
					PieDataset dataset = plot.getDataset();

					for( Object v : dataset.getKeys()){
						pw.println(v.toString()+","+dataset.getValue((Comparable)v)); 
					}

				}

				if(Chart.current.getPlot() instanceof CategoryPlot){

					CategoryPlot plot = (CategoryPlot) Chart.current.getPlot();
					CategoryDataset dataset = plot.getDataset();


					for( Object v : dataset.getRowKeys()){
						for( Object vn : dataset.getColumnKeys()){

							pw.println(v.toString() + ","+vn.toString()+","+dataset.getValue((Comparable)v,(Comparable)vn));
						}
					}
				}
				
				if(Chart.current.getPlot() instanceof XYPlot){
					
					XYPlot plot = (XYPlot)Chart.current.getPlot();
					XYDataset dataset = plot.getDataset();
					
					
					for(int s = 0 ; s < dataset.getSeriesCount() ; s++){
						
						for(int i = 0 ; i < dataset.getItemCount(s) ; i++){
							
								
								pw.println(dataset.getX(s, i)+ ","+dataset.getY(s, i));
								
							
						}
						
					}
					
					
				}
				
				pw.flush();
				pw.close();
				
				try {
					Runtime.getRuntime().exec(new String[]{"notify-send", "JFreeChart Demo", "Successfully exported to "+file.getName()});
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}

			}
			
			
		});

	}

	public void show(){
		dialog.setVisible(true);
	}

	@SuppressWarnings("rawtypes")
	public void setData() {

		if(Chart.current.getPlot() instanceof PiePlot){

			PiePlot plot = (PiePlot) Chart.current.getPlot();
			PieDataset dataset = plot.getDataset();

			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.addColumn("Key");
			model.addColumn("Value");
			for( Object v : dataset.getKeys()){
				model.addRow(new Object[]{v.toString(), dataset.getValue((Comparable)v)});
			}


			table.setModel(model);
			table.repaint();
			((DefaultTableModel)table.getModel()).fireTableDataChanged();
		}

		if(Chart.current.getPlot() instanceof CategoryPlot){

			CategoryPlot plot = (CategoryPlot) Chart.current.getPlot();
			CategoryDataset dataset = plot.getDataset();

			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.addColumn("Key");
			model.addColumn("Attribute");
			model.addColumn("Value");


			for( Object v : dataset.getRowKeys()){
				for( Object vn : dataset.getColumnKeys()){

					model.addRow(new Object[]{v.toString(),vn.toString(), dataset.getValue((Comparable)v,(Comparable)vn)});
				}
			}

			table.setModel(model);
			table.repaint();
			((DefaultTableModel)table.getModel()).fireTableDataChanged();
		}
		
		if(Chart.current.getPlot() instanceof XYPlot){
			
			XYPlot plot = (XYPlot)Chart.current.getPlot();
			XYDataset dataset = plot.getDataset();
			
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.addColumn("X");
			model.addColumn("Y");
			
			
			for(int s = 0 ; s < dataset.getSeriesCount() ; s++){
				
				for(int i = 0 ; i < dataset.getItemCount(s) ; i++){
					
						
						model.addRow(new Object[]{dataset.getX(s, i), dataset.getY(s, i)});
						
					
				}
				
			}
			


			table.setModel(model);
			table.repaint();
			((DefaultTableModel)table.getModel()).fireTableDataChanged();
			
		}

	}

}

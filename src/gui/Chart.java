package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartTransferable;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.editor.ChartEditor;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart {

	public JPanel panel, toolbar, graphpanel;
	public JButton export, properties, showDataset, zoomIn, zoomOut, print, copyToClipboard;
	public static JFreeChart current;
	public static JChartPanel chartPanel;

	public Chart(){
		init();
	}

	public void init(){
		panel = new JPanel(new BorderLayout());
		toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		graphpanel = new JPanel();
		graphpanel.setLayout(new FlowLayout());
		graphpanel.add(new JLabel("Click a button from the left options to see the chart"));
		graphpanel.setSize(new Dimension(400, 300));
		graphpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Chart"));
		panel.add(graphpanel, BorderLayout.CENTER);
		panel.add(toolbar, BorderLayout.NORTH);

		initToolbar();
		addToolListeners();
	}

	public void initToolbar(){

		export = new JButton(ImageLoader.loadImage("images/export.png"));
		properties = new JButton(ImageLoader.loadImage("images/properties-icon.png"));
		showDataset = new JButton(ImageLoader.loadImage("images/dataset.png"));
		zoomIn = new JButton(ImageLoader.loadImage("images/zoom-in.png"));
		zoomOut = new JButton(ImageLoader.loadImage("images/zoom-out.png"));
		print = new JButton(ImageLoader.loadImage("images/print.png"));
		copyToClipboard = new JButton(ImageLoader.loadImage("images/copy.gif"));

		export.setToolTipText("Export the graph to image or pdf");
		properties.setToolTipText("Edit the properties of the current chart");
		showDataset.setToolTipText("Show the dataset of the graph");
		zoomIn.setToolTipText("Zoom in the graph");
		zoomOut.setToolTipText("Zoom out the graph");
		print.setToolTipText("print the graph");
		copyToClipboard.setToolTipText("copy the graph to the clipboard as an image");

		toolbar.add(export);
		toolbar.add(properties);
		toolbar.add(showDataset);
		toolbar.add(zoomIn);
		toolbar.add(zoomOut);
		toolbar.add(print);
		toolbar.add(copyToClipboard);

	}

	public void addToolListeners(){

		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				new ExportDialog().show();

			}
		});
		
		properties.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				 ChartEditor editor = ChartEditorManager.getChartEditor(current);
			        int result = JOptionPane.showConfirmDialog(MainFrame.frame, editor,
			                "Edit Chart Properties",
			                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			        if (result == JOptionPane.OK_OPTION) {
			            editor.updateChart(current);
			        }
			}
		});

		showDataset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				DataViewer v = new DataViewer();
				v.setData();
				v.show();

			}
		});

		zoomIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				chartPanel.zoomInBoth(-1, -1);
			}
		});

		zoomOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				chartPanel.zoomOutBoth(-1, -1);
			}
		});

		print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PrinterJob job = PrinterJob.getPrinterJob();
		        PageFormat pf = job.defaultPage();
		        PageFormat pf2 = job.pageDialog(pf);
		        if (pf2 != pf) {
		            job.setPrintable(chartPanel, pf2);
		            if (job.printDialog()) {
		                try {
		                    job.print();
		                }
		                catch (PrinterException ex) {
		                    JOptionPane.showMessageDialog(chartPanel, e);
		                }
		            }
		        }



			}
		});

		copyToClipboard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(current == null){
					JOptionPane.showMessageDialog(MainFrame.frame, "There is no graph opened currently!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Clipboard systemClipboard= Toolkit.getDefaultToolkit().getSystemClipboard();
				ChartTransferable selection = new ChartTransferable(current,chartPanel.getWidth(), chartPanel.getHeight());
				systemClipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog(chartPanel, "Successfully copied the chart to the clipboard!");
			}
		});
	}

	public void createPieChart(){

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
		dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
		dataset.setValue( "MotoG" , new Double( 40 ) );    
		dataset.setValue( "Nokia Lumia" , new Double( 10 ) );

		current = ChartFactory.createPieChart("Mobile Sales",dataset,true,true,false);
		chartPanel = new JChartPanel(current);

		graphpanel.removeAll();
		graphpanel.setLayout(new BorderLayout());
		graphpanel.add(chartPanel, BorderLayout.CENTER);
		graphpanel.repaint();
		graphpanel.revalidate();

		BottomPanel.label.setText("Pie Chart");
	}

	public void createBarChart(){

		final String fiat = "FIAT";        
		final String audi = "AUDI";        
		final String ford = "FORD";        
		final String speed = "Speed";        
		final String millage = "Millage";        
		final String userrating = "User Rating";        
		final String safety = "safety";        
		final DefaultCategoryDataset dataset = 
				new DefaultCategoryDataset( );  

		dataset.addValue( 1.0 , fiat , speed );        
		dataset.addValue( 3.0 , fiat , userrating );        
		dataset.addValue( 5.0 , fiat , millage ); 
		dataset.addValue( 5.0 , fiat , safety );           

		dataset.addValue( 5.0 , audi , speed );        
		dataset.addValue( 6.0 , audi , userrating );       
		dataset.addValue( 10.0 , audi , millage );        
		dataset.addValue( 4.0 , audi , safety );

		dataset.addValue( 4.0 , ford , speed );        
		dataset.addValue( 2.0 , ford , userrating );        
		dataset.addValue( 3.0 , ford , millage );        
		dataset.addValue( 6.0 , ford , safety );

		current = ChartFactory.createBarChart("Cars people like","Category","Score",dataset,PlotOrientation.HORIZONTAL,true, true, false);
		chartPanel = new JChartPanel(current);

		graphpanel.removeAll();
		graphpanel.setLayout(new BorderLayout());
		graphpanel.add(chartPanel, BorderLayout.CENTER);
		graphpanel.repaint();
		graphpanel.revalidate();

		BottomPanel.label.setText("Bar Chart");
	}

	public void createLineChart(){

		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		dataset.addValue( 15 , "schools" , "1970" );
		dataset.addValue( 30 , "schools" , "1980" );
		dataset.addValue( 60 , "schools" ,  "1990" );
		dataset.addValue( 120 , "schools" , "2000" );
		dataset.addValue( 240 , "schools" , "2010" );
		dataset.addValue( 300 , "schools" , "2014" );
		current = ChartFactory.createLineChart(
				"Number of schools vs years",
				"Years","Number of Schools",
				dataset,
				PlotOrientation.VERTICAL,
				true,true,false);

		chartPanel = new JChartPanel(current);

		graphpanel.removeAll();
		graphpanel.setLayout(new BorderLayout());
		graphpanel.add(chartPanel, BorderLayout.CENTER);
		graphpanel.repaint();
		graphpanel.revalidate();

		BottomPanel.label.setText("Line Chart");

	}

	public void createXYChart(){

		final XYSeries firefox = new XYSeries( "Firefox" );          
		firefox.add( 1.0 , 1.0 );          
		firefox.add( 2.0 , 4.0 );          
		firefox.add( 3.0 , 3.0 );          
		final XYSeries chrome = new XYSeries( "Chrome" );          
		chrome.add( 1.0 , 4.0 );          
		chrome.add( 2.0 , 5.0 );          
		chrome.add( 3.0 , 6.0 );          
		final XYSeries iexplorer = new XYSeries( "InternetExplorer" );          
		iexplorer.add( 3.0 , 4.0 );          
		iexplorer.add( 4.0 , 5.0 );          
		iexplorer.add( 5.0 , 4.0 );          
		final XYSeriesCollection dataset = new XYSeriesCollection( );          
		dataset.addSeries( firefox );          
		dataset.addSeries( chrome );          
		dataset.addSeries( iexplorer );

		current = ChartFactory.createXYLineChart(
				"Popular browsers" ,
				"Category" ,
				"Score" ,
				dataset ,
				PlotOrientation.VERTICAL ,
				true , true , false);

		final XYPlot plot = current.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		renderer.setSeriesPaint( 0 , Color.RED );
		renderer.setSeriesPaint( 1 , Color.GREEN );
		renderer.setSeriesPaint( 2 , Color.YELLOW );
		renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
		renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
		renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
		plot.setRenderer( renderer );

		chartPanel = new JChartPanel(current);


		graphpanel.removeAll();
		graphpanel.setLayout(new BorderLayout());
		graphpanel.add(chartPanel, BorderLayout.CENTER);
		graphpanel.repaint();
		graphpanel.revalidate();

		BottomPanel.label.setText("XY line Chart");
	}

	public void createBubbleChart(){

		DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset(); 

		double ad[ ] = { 30 , 40 , 50 , 60 , 70 , 80 };                 
		double ad1[ ] = { 10 , 20 , 30 , 40 , 50 , 60 };                 
		double ad2[ ] = { 4 , 5 , 10 , 8 , 9 , 6 };                 
		double ad3[][] = { ad , ad1 , ad2 };                 
		defaultxyzdataset.addSeries( "Series 1" , ad3 );

		current = ChartFactory.createBubbleChart(
				"AGE vs WEIGHT vs WORK",                    
				"Weight",                    
				"AGE",                    
				defaultxyzdataset,                    
				PlotOrientation.HORIZONTAL,                    
				true, true, false);

		chartPanel = new JChartPanel(current);

		XYPlot xyplot = ( XYPlot )current.getPlot( );                 
		xyplot.setForegroundAlpha( 0.65F );                 
		XYItemRenderer xyitemrenderer = xyplot.getRenderer( );
		xyitemrenderer.setSeriesPaint( 0 , Color.blue );                 
		NumberAxis numberaxis = ( NumberAxis )xyplot.getDomainAxis( );                 
		numberaxis.setLowerMargin( 0.2 );                 
		numberaxis.setUpperMargin( 0.5 );                 
		NumberAxis numberaxis1 = ( NumberAxis )xyplot.getRangeAxis( );                 
		numberaxis1.setLowerMargin( 0.8 );                 
		numberaxis1.setUpperMargin( 0.9 );

		graphpanel.removeAll();
		graphpanel.setLayout(new BorderLayout());
		graphpanel.add(chartPanel, BorderLayout.CENTER);
		graphpanel.repaint();
		graphpanel.revalidate();

		BottomPanel.label.setText("Bubble Chart");
	}

	public void createTimeSeriesChart(){

		final TimeSeries series = new TimeSeries( "Random Data" );         
		Second current1 = new Second( );         
		double value = 100.0;         
		for (int i = 0; i < 4000; i++)    
		{
			try 
			{
				value = value + Math.random( ) - 0.5;                 
				series.add(current1, new Double( value ) );                 
				current1 = ( Second ) current1.next( ); 
			}
			catch ( SeriesException e ) 
			{
				System.err.println("Error adding to series");
			}
		}

		current =  ChartFactory.createTimeSeriesChart(             
				"Computing Test", 
				"Seconds",              
				"Value",              
				new TimeSeriesCollection(series),             
				false,              
				false,              
				false);

		chartPanel = new JChartPanel(current);

		graphpanel.removeAll();
		graphpanel.setLayout(new BorderLayout());
		graphpanel.add(chartPanel, BorderLayout.CENTER);
		graphpanel.repaint();
		graphpanel.revalidate();

		BottomPanel.label.setText("Time Series Chart");
	}

	public JPanel getPanel(){
		return panel;
	}
}

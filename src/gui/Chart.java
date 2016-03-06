package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
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

	public JPanel panel;
	public static JFreeChart current;
	
	public Chart(){
		init();
	}

	public void init(){
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("Click a button from the left options to see the chart"));
		panel.setSize(new Dimension(400, 300));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Chart"));
	}

	public void createPieChart(){

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
		dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
		dataset.setValue( "MotoG" , new Double( 40 ) );    
		dataset.setValue( "Nokia Lumia" , new Double( 10 ) );

		current = ChartFactory.createPieChart("Mobile Sales",dataset,true,true,false);
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(new ChartPanel(current), BorderLayout.CENTER);
		panel.repaint();
		panel.revalidate();

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
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(new ChartPanel(current), BorderLayout.CENTER);
		panel.repaint();
		panel.revalidate();

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
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(new ChartPanel(current), BorderLayout.CENTER);
		panel.repaint();
		panel.revalidate();

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

		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(new ChartPanel(current), BorderLayout.CENTER);
		panel.repaint();
		panel.revalidate();

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

		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(new ChartPanel(current), BorderLayout.CENTER);
		panel.repaint();
		panel.revalidate();

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
		
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.add(new ChartPanel(current), BorderLayout.CENTER);
		panel.repaint();
		panel.revalidate();

		BottomPanel.label.setText("Time Series Chart");
	}

	public JPanel getPanel(){
		return panel;
	}
}

package gui;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class JChartPanel extends ChartPanel{
	

	private static final long serialVersionUID = 1L;

	public JChartPanel(JFreeChart chart) {
		super(chart);
		addListner();
	}
	
	public void addListner(){
		
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				if(e.getPreciseWheelRotation() < 0.0){
					
					zoomInBoth(-1, -1);
				}
				else{
					zoomOutBoth(-1, -1);
				}
				
			}
		} );
	}

}

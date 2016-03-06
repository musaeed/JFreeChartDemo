package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class GraphButtons{

	public ColoredButton pieChart, barChart, lineChart, xyChart, bubbleChart, timeSeriesChart;
	public JPanel panel;
	public Chart chart;
	
	public GraphButtons(){
		
		init();
	}
	
	public void init(){
		
		pieChart = ColoredButton.GetRandomButton("Pie Chart", "Creates a pie chart");
		barChart = ColoredButton.GetRandomButton("Bar Chart", "Creates a bar chart");
		lineChart = ColoredButton.GetRandomButton("line Chart", "Creates a line chart");
		xyChart = ColoredButton.GetRandomButton("XY Chart", "Creates a XY chart");
		bubbleChart = ColoredButton.GetRandomButton("Bubble Chart", "Creates a bubble chart");
		timeSeriesChart = ColoredButton.GetRandomButton("Time Series Chart", "Creates a time series chart");
		
		chart = new Chart();
		
		addActions();
		
		panel = new JPanel(new GridLayout(2, 3));
		panel.add(pieChart);
		panel.add(barChart);
		panel.add(lineChart);
		panel.add(xyChart);
		panel.add(bubbleChart);
		panel.add(timeSeriesChart);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Chart type"));
	}
	
	public void addActions(){
		
		pieChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chart.createPieChart();
				
			}
		});
		
		barChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chart.createBarChart();
			}
		});
		
		lineChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chart.createLineChart();
			}
		});
		
		xyChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chart.createXYChart();
				
			}
		});
		
		bubbleChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				chart.createBubbleChart();
				
			}
		} );
		
		timeSeriesChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chart.createTimeSeriesChart();
			}
		});
		
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public Chart getChart(){
		return chart;
	}
}

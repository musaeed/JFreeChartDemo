package gui;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.jfree.chart.ChartUtilities;
import org.w3c.dom.DOMImplementation;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

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
					JOptionPane.showMessageDialog(MainFrame.frame, e1);
					e1.printStackTrace();
				}

				dialog.dispose();
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
					JOptionPane.showMessageDialog(MainFrame.frame, e1);
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				dialog.dispose();
			}
		});

		svg.addActionListener(new ActionListener() {

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
					filename = filename + ".svg";
				}
				
                DOMImplementation mySVGDOM= GenericDOMImplementation.getDOMImplementation();
                /* create Document object */
                org.w3c.dom.Document document = mySVGDOM.createDocument(null, "svg", null);
                /* Create SVG Generator */
                SVGGraphics2D my_svg_generator = new SVGGraphics2D(document);
                /* Render chart as SVG 2D Graphics object */
                Chart.current.draw(my_svg_generator, new Rectangle2D.Double(0, 0, 640, 480), null);
                /* Write output to file */
                try {
					my_svg_generator.stream(filename);
				} catch (SVGGraphics2DIOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(MainFrame.frame, e1);
					e1.printStackTrace();
				}          

				
				dialog.dispose();
			}
		});

		pdf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(MainFrame.frame, "Enter the name of the file", FileDialog.SAVE);
				fd.setVisible(true);

				String filename;
				int width = 500;
				int height = 350;

				try{

					filename = fd.getFiles()[0].getAbsolutePath();

				} catch(Exception ee){
					return;
				}

				if(!filename.contains(".")){
					filename = filename + ".pdf";
				}

				PdfWriter writer = null;

				Document document = new Document();

				try {

					writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
					document.open();
					PdfContentByte contentByte = writer.getDirectContent();
					PdfTemplate template = contentByte.createTemplate(width, height);
					@SuppressWarnings("deprecation")
					Graphics2D graphics2d = template.createGraphics(width, height,new DefaultFontMapper());
					Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,height);

					Chart.current.draw(graphics2d, rectangle2d);

					graphics2d.dispose();
					contentByte.addTemplate(template, 0, 0);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainFrame.frame, ex);
					ex.printStackTrace();
				}
				document.close();
				
				dialog.dispose();
			}
		});
	}

	public void show(){

		dialog.setVisible(true);
	}

}

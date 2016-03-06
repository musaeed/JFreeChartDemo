import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;

public class MainClass {

	public static void main(String args[]){

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					
					e.printStackTrace();
				}
				
				MainFrame gui = new MainFrame();
				gui.show();

			}
		});
	}
}

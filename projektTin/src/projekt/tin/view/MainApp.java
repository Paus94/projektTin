package projekt.tin.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainApp extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainApp(){
		setTitle("GNR");
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				MainApp mainApp = new MainApp();
			}
		});

	}

}

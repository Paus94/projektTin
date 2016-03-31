package projekt.tin.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import projekt.tin.controller.TextFileReader;

public class MainApp extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private int timeCount;
	private String timePath = "./resources/CZAS.TXT";
	private String intPath = "./resources/INT.TXT";
	private JMenuBar menuBar;
	private JMenu menuHelp;
	private JMenuItem miAuthors, miAboutApp, miAboutGNR, miHowToUse;
	
	public MainApp(){
		setTitle("GNR");
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		menuBar = new JMenuBar();
		menuHelp = new JMenu("Pomoc");
		miAboutApp = new JMenuItem("O programie");
		miAboutGNR = new JMenuItem("O GNR");
		miHowToUse = new JMenuItem("Jak korzysta�");
		miAuthors = new JMenuItem("Autorzy");
		
		setJMenuBar(menuBar);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(menuHelp);
		menuHelp.add(miAboutApp);
		menuHelp.add(miAboutGNR);
		menuHelp.add(miHowToUse);
		menuHelp.add(miAuthors);
		
		miAboutApp.addActionListener(this);
		miAboutGNR.addActionListener(this);
		miAuthors.addActionListener(this);
		miHowToUse.addActionListener(this);
		
		
		TextFileReader fileReader = new TextFileReader(timePath);
		timeCount = fileReader.countFileLines();
		fileReader.setPath(intPath);
		fileReader.numberOfCallsInEachMinute();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){	
				MainApp mainApp = new MainApp();
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if(src==miAboutApp){
			String message = "Ten program wizualizuje r�ne metody wyznaczania Godziny Najwi�kszego Ruchu. Rysuje r�wnie� wykresy.";
			JOptionPane.showMessageDialog(null, message);
		}
		else if(src==miAboutGNR){
			String message = "Tutaj opiszemy wszystkie metody wyznaczania GNR";
			JOptionPane.showMessageDialog(null,message);
		}
		else if(src==miAuthors){
			String message = "Program stworzyli zajebi�ci studenci 2 roku teleinformytyki Politechniki Wroc�awskiej. \n Jakub Parys i Adrian Kuli�ski";
			JOptionPane.showMessageDialog(null, message);
		}
		else if(src==miHowToUse){
			String message = "Ma�y help dla ograniczonych gdzie maj� klika�";
			JOptionPane.showMessageDialog(null, message);
		}
		
	}

}

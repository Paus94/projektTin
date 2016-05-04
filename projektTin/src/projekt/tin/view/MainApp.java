package projekt.tin.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;

import projekt.tin.controller.GNR;

@SuppressWarnings("serial")
public class MainApp extends JFrame implements ActionListener {

	private JMenuBar menuBar;
	private JMenu menuHelp, menuAboutGNR;
	private JMenuItem miAuthors, miAboutApp, miHowToUse, miAboutTCBH, miAboutADPQH, miAboutADPFH, miAboutFDMP, miAboutFDMH;
	private JButton bStart;
	private MainOptionsPanel mainOptionsPanel;
	private AdditionalOptionsPanel additionalOptionsPanel;

	public MainApp() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setTitle("GNR");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		
		menuBar = new JMenuBar();
		menuHelp = new JMenu("Pomoc");
		menuAboutGNR = new JMenu("O GNR");
		miAboutApp = new JMenuItem("O programie");
		miHowToUse = new JMenuItem("Jak korzysta�");
		miAuthors = new JMenuItem("Autorzy");
		miAboutADPFH = new JMenuItem("O metodzie ADPFH");
		miAboutADPQH = new JMenuItem("O metodzie ADPQH");
		miAboutFDMH = new JMenuItem("O metodzie FDMH");
		miAboutFDMP = new JMenuItem("O metodzie FDMP");
		miAboutTCBH = new JMenuItem("O metodzie TCBH");

		setJMenuBar(menuBar);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(menuHelp);
		menuHelp.add(miAboutApp);
		menuHelp.add(menuAboutGNR);
		menuHelp.add(miHowToUse);
		menuHelp.add(miAuthors);
		menuAboutGNR.add(miAboutTCBH);
		menuAboutGNR.add(miAboutADPQH);
		menuAboutGNR.add(miAboutADPFH);
		menuAboutGNR.add(miAboutFDMP);
		menuAboutGNR.add(miAboutFDMH);
		

		miAboutApp.addActionListener(this);
		miAuthors.addActionListener(this);
		miHowToUse.addActionListener(this);
		miAboutADPFH.addActionListener(this);
		miAboutADPQH.addActionListener(this);
		miAboutFDMH.addActionListener(this); 
		miAboutFDMP.addActionListener(this);
		miAboutTCBH.addActionListener(this);
		
		
		mainOptionsPanel = new MainOptionsPanel();
		additionalOptionsPanel = new AdditionalOptionsPanel();
		getContentPane().add(mainOptionsPanel, BorderLayout.WEST);
		bStart = mainOptionsPanel.getbStart();
		bStart.addActionListener(this);
		getContentPane().add(additionalOptionsPanel, BorderLayout.EAST);
		getContentPane().add(bStart, BorderLayout.SOUTH);
		pack();
		setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		
		if(src == bStart){
			GNR gnr = new GNR();
			if(mainOptionsPanel.getMethod() == MainOptionsPanel.TCBH){
				gnr.methodTCBH(mainOptionsPanel.getThirtyDaysCallsInQuarter());
				TimeChart timeChart = new TimeChart();
				
				JFrame chart = new JFrame();
				chart.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				Button saveButton = new Button("Zapisz wykres");
				
				Date date = new Date() ;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
				
				ChartPanel chartPanel = timeChart.addData(mainOptionsPanel.getOneDayCallsInQuarter());
				
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							ChartUtilities.saveChartAsJPEG(new File("charts/chart" + dateFormat.format(date) + ".jpg"), chartPanel.getChart(), 1000, 600);
							JOptionPane.showMessageDialog(null, "Zapisano!");
						} catch (IOException ex) {
							System.err.println(ex);
						}
					}
				});
				
				Button okButton = new Button("Ok");
				
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						chart.dispose();
					}
				});
				
				gbc.insets = new Insets(10, 0, 10, 0);
				gbc.anchor = GridBagConstraints.WEST;
				gbc.gridx = 0;
				gbc.gridy = 0;
				chart.add(new JLabel(gnr.toString()), gbc);
				gbc.gridy++;
				chart.add(chartPanel, gbc);
				gbc.gridy++;
				chart.add(saveButton, gbc);
				gbc.gridx++;
				chart.add(okButton, gbc);
				chart.setVisible(true);
				chart.pack();
			}
			else if(mainOptionsPanel.getMethod() == MainOptionsPanel.ADPQH){
				gnr.methodADPQH(mainOptionsPanel.getThirtyDaysCallsInQuarter());
				JOptionPane.showMessageDialog(null, gnr);
			}
			else if(mainOptionsPanel.getMethod() == MainOptionsPanel.ADPFH){
				gnr.methodADPFH(mainOptionsPanel.getThirtyDaysCallsInHour());
				JOptionPane.showMessageDialog(null, gnr);
			}
		}
		else if (src == miAboutApp) {
			String message = "Ten program wizualizuje r�ne metody wyznaczania Godziny Najwi�kszego Ruchu. Rysuje r�wnie� wykresy.";
			JOptionPane.showMessageDialog(null, message);
		}
//		else if (src == miAboutGNR) {
//			String message = "Tutaj opiszemy wszystkie metody wyznaczania GNR";
//			JOptionPane.showMessageDialog(null, message);
//		}
		else if (src == miAuthors) {
			String message = "Program stworzyli studenci 2 roku teleinformytyki Politechniki Wroc�awskiej. \n Jakub Parys i Adrian Kuli�ski";
			JOptionPane.showMessageDialog(null, message);
		}
		else if (src == miHowToUse) {
			String message = "Ma�y help dla u�ytkownik�w gdzie maj� klika�";
			JOptionPane.showMessageDialog(null, message);
		}
		else if(src == miAboutADPFH){
			JOptionPane.showMessageDialog(null, "ADPFH");
		}

	}
	
	public void enableStartButton() {
		bStart.setEnabled(true);
	}
	
	public static void main(String[] args) {
		
		new MainApp();
	}


}

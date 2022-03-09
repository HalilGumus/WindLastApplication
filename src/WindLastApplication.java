import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import inf.v3d.view.ViewerPanel;





public class WindLastApplication extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static void main(String[] args) throws Exception {
		WindLastApplication wla = new WindLastApplication();
		wla.setVisible(true);
	}

	
	
	
	private static final String[] WIND_TYPES = { "Windlastzone 1", "Windlastzone 2", "Windlastzone 3","Windlastzone 4" };

	private ViewerPanel viewerPanel = new ViewerPanel();
	private JTextField breiteTF = new JTextField("5.52", 5); 	
	private JTextField laengeTF = new JTextField("10", 5);
	private JTextField hoeheTF = new JTextField("5.4", 5);
	private JTextField etagenTF = new JTextField("2", 5);
	private JTextField wandproetageTF = new JTextField("7", 5);
	private JTextField attikaTF = new JTextField("0", 5);
	private JTextField deckenstaerkeTF = new JTextField("0.2", 5);
	private JTextField lichtehoeheTF = new JTextField("2.5", 5);
	private JComboBox windCB = new JComboBox(WIND_TYPES);
	String bezeichnung_Wind;
	private JButton applyB = new JButton("Berechnen");
	private JButton exitB = new JButton("Abbrechen");
	int fontSize = 20; 

	
	String[] columnNames_in = { "Wandnummer", "x", "y", "Wandlänge", "Wanddicke", "Ausrichtung" };
	Object[][] rowData = { { "Wand X.1", 0, 0, 1.25, 0.24, "1" },
			{ "Wand X.2", 3.02, 0, 2.5, 0.24, "1" },
			{ "Wand X.3", 5.4, -0.12, 10, 0.24, "0"},
			{ "Wand X.4", 0, 9.76, 5.52, 0.24, "1" },
			{ "Wand X.5", 0.12, -0.12, 10, 0.24, "0" },
			{ "Wand X.6", 3.28, 5.88, 2, 0.24, "1" },
			{ "Wand X.7", 3.28, 3.88, 2, 0.24, "1" } };
	private DefaultTableModel inputmodel = new DefaultTableModel(rowData, columnNames_in);
	private JTable inputTable = new JTable(inputmodel);
	
	
	String[] columnNames_out = { "Etage.Wand", "Trägheitsmoment [m4]", "Querkraft [kN]", "Moment [kNm]" };
	Object[][] data_out = { { " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " },
			{ " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " },};
	private DefaultTableModel outputmodel = new DefaultTableModel(data_out, columnNames_out);
	private JTable outputTable = new JTable(outputmodel);



	
	
	
	
	public WindLastApplication() throws Exception {

		String str= "com.jtattoo.plaf.mint.MintLookAndFeel";	
		UIManager.setLookAndFeel(str);
		
		
		setTitle("Windlastberechnung");
		
		//ViewerPanel
		this.setLayout(new BorderLayout());
		this.add(this.viewerPanel, BorderLayout.CENTER);
		
		

		// west Panel
		JPanel westPanel = new JPanel(new BorderLayout());
		this.add(westPanel, BorderLayout.WEST);

		
		// Properties Panel
		JPanel gebaeudePanel = new JPanel(new BorderLayout());
		westPanel.add(gebaeudePanel, BorderLayout.NORTH);

		
		// system Gebäude Panel
		// Links & Rechts 
		JPanel abmessungenPanelGroß = new JPanel(new BorderLayout());
		gebaeudePanel.add(abmessungenPanelGroß, BorderLayout.NORTH);
		abmessungenPanelGroß.setBorder(BorderFactory.createTitledBorder("Gebäude Abmessungen"));

		// Links
		JPanel abmessungenPanel = new JPanel(new BorderLayout());
		JPanel p1 = new JPanel(new GridLayout(4, 1));
		JPanel p2 = new JPanel(new GridLayout(4, 1));
		JPanel p3 = new JPanel(new GridLayout(4, 1));

		p1.add(new JLabel("l = "));
		p1.add(new JLabel("b = "));
		p1.add(new JLabel("h = "));

		this.hoeheTF.setEditable(false);
		laengeTF.setBorder(BorderFactory.createEtchedBorder(Color.OPAQUE));
		breiteTF.setBorder(BorderFactory.createEtchedBorder(Color.OPAQUE));
		hoeheTF.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

		p2.add(this.laengeTF); 
		p2.add(this.breiteTF);
		p2.add(this.hoeheTF);

		p3.add(new JLabel(" m       "));
		p3.add(new JLabel(" m       "));
		p3.add(new JLabel(" m       "));

		abmessungenPanel.add(p1, BorderLayout.WEST);
		abmessungenPanel.add(p2, BorderLayout.CENTER);
		abmessungenPanel.add(p3, BorderLayout.EAST);

		abmessungenPanelGroß.add(abmessungenPanel, BorderLayout.WEST);


		// Rechts
		JPanel abmessungenPanelrechtss = new JPanel(new BorderLayout());

		JPanel p11 = new JPanel(new GridLayout(6, 1));
		JPanel p21 = new JPanel(new GridLayout(6, 1));
		JPanel p31 = new JPanel(new GridLayout(6, 1));

		p11.add(new JLabel("Etagen = "));
		p11.add(new JLabel("Wände = "));
		p11.add(new JLabel("Attikahöhe = "));
		p11.add(new JLabel("Deckenstärke = "));
		p11.add(new JLabel("Lichte Höhe = "));

		p21.add(this.etagenTF);
		p21.add(this.wandproetageTF);
		p21.add(this.attikaTF);
		p21.add(this.deckenstaerkeTF);
		p21.add(this.lichtehoeheTF);

		p31.add(new JLabel(" [-]"));
		p31.add(new JLabel(" [-]"));
		p31.add(new JLabel(" m"));
		p31.add(new JLabel(" m"));
		p31.add(new JLabel(" m"));

		abmessungenPanelrechtss.add(p11, BorderLayout.WEST);
		abmessungenPanelrechtss.add(p21, BorderLayout.CENTER);
		abmessungenPanelrechtss.add(p31, BorderLayout.EAST);

		abmessungenPanelGroß.add(abmessungenPanelrechtss, BorderLayout.EAST);

		// Windlast Panel
		gebaeudePanel.add(this.windCB, BorderLayout.CENTER);
		this.windCB.setBorder(BorderFactory.createTitledBorder("WindZone"));

		
		// Tabellarische Eingabe
		JPanel tabellenPanel = new JPanel();
		tabellenPanel.setLayout(new BorderLayout());
		tabellenPanel.setPreferredSize(new Dimension(380, 150));
		tabellenPanel.add(inputTable);
		gebaeudePanel.add(tabellenPanel, BorderLayout.SOUTH);
		tabellenPanel.setBorder(BorderFactory.createTitledBorder("Tabellarische Eingabe der Wände"));

		JScrollPane in_pane = new JScrollPane(inputTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabellenPanel.add(in_pane);

		

		// Tabellarische Ausgabe
		JPanel tabellenPanel2 = new JPanel();
		tabellenPanel2.setLayout(new BorderLayout());
		tabellenPanel2.setPreferredSize(new Dimension(200, 160));
		tabellenPanel2.add(outputTable);

		westPanel.add(tabellenPanel2, BorderLayout.SOUTH);
		tabellenPanel2.setBorder(BorderFactory.createTitledBorder("Tabellarische Ergebnisdarstellung"));

		JScrollPane out_pane = new JScrollPane(outputTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabellenPanel2.add(out_pane);
	

		
		// south Panel
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(this.applyB);
		southPanel.add(this.exitB);

		
		
		// register action listeners
		this.exitB.addActionListener(this);
		this.applyB.addActionListener(this);
		this.wandproetageTF.addActionListener(this);
		this.etagenTF.addActionListener(this);
		this.deckenstaerkeTF.addActionListener(this);
		this.laengeTF.addActionListener(this);
		this.breiteTF.addActionListener(this);
		this.attikaTF.addActionListener(this);
		this.etagenTF.addActionListener(this);
		this.windCB.addActionListener(this);
		this.lichtehoeheTF.addActionListener(this);
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	
	
	
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.exitB) {
			System.exit(0);
		} else {
			try{
			this.apply();
			} catch(Exception e1){
					
				JOptionPane.showMessageDialog(this, "Tabelle Ausfüllen! \n" + e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}

	
	

	private void apply() throws Exception {
		

				
		
		// Alle Vier Windzonen
		Windzone[] wind = new Windzone[4];


		//Basis Geschwindkeitsdruck abhängig von der Höhe des Gebäudes			// h<10m, 10<h<18m, h>18m
		wind[0] = new Windzone(0.5, 0.65, 0.75, "Windlastzone 1");
		wind[1] = new Windzone(0.65, 0.8, 0.9, "Windlastzone 2");
		wind[2] = new Windzone(0.8, 0.95, 1.1, "Windlastzone 3");
		wind[3] = new Windzone(0.95, 1.15, 1.3, "Windlastzone 4");

		
		
		//Entnahme Der Gebäudeinformationen
		double l 	= Double.parseDouble(this.laengeTF.getText());
		double b 	= Double.parseDouble(this.breiteTF.getText());
		String qw 	= (String) this.windCB.getSelectedItem();
		double a 	= Double.parseDouble(this.attikaTF.getText());
		int et 		= Integer.parseInt(this.etagenTF.getText()); 
		double d 	= Double.parseDouble(this.deckenstaerkeTF.getText());
		double lh 	= Double.parseDouble(this.lichtehoeheTF.getText());
		int wandproetage = Integer.parseInt(this.wandproetageTF.getText());
		
		
		if(d > 0.4){
			throw new Exception("Deckenstärken über 40cm sind nicht zulässig!");	
		}

		System.out.println("_______________________________________________________________________");

		
		// Entnahme der Windlastzone
		Windzone wind_in = null;
		if (qw == wind[0].getBezeichnung()) {
			wind_in = wind[0];
			System.out.println( wind_in.getBezeichnung());
		}
		if (qw == wind[1].getBezeichnung()) {
			wind_in = wind[1];
			System.out.println( wind_in.getBezeichnung());
		}
		if (qw == wind[2].getBezeichnung()) {
			wind_in = wind[2];
			System.out.println( wind_in.getBezeichnung());
		}
		if (qw == wind[3].getBezeichnung()) {
			wind_in = wind[3];
			System.out.println( wind_in.getBezeichnung());
		}

		
		
		// Errechnete Gebäudehöhe
		hoeheTF.setEditable(true);
		hoeheTF.setText(String.valueOf((et * (lh + d)) + a)); //(String.format("%.2f",
		if(((et * (lh + d)) + a)>25){
			throw new Exception(" Die Gebäudehöhe über 25m ist nach DIN EN 1991-1-4/NA für die Ermittlung der Böengeschwindigkeit \n unter Vernachlässigung der Bodenrauigkeit nicht Zulässig! ");	
		}
		hoeheTF.setEditable(false);
		double h = Double.parseDouble(this.hoeheTF.getText());

		

		// Wände erstellen
		Wand[] waende = new Wand[wandproetage * et];
		System.out.println("insesamt gibt es " + waende.length + " Wände");
		
		//In-Out/put Einrichten
		outputmodel.setRowCount(wandproetage * et);
		outputTable.setAutoCreateRowSorter(true);			
		inputmodel.setRowCount(wandproetage);

		
		
		//Wände aus Tabelle entnehmen
		int k = 0;
		for (int j = 0; j < et; j++) {
			
			DeckenQuerschnitt s2 = new DeckenQuerschnitt(b, d);
			for (int i = 1; i <= wandproetage; i++) {

				// in dieser klammer ist k= wandarray, j= etage(EG="0"), i=Wandnummer pro Etage (w1="1")

				i--;
				double x = Double.parseDouble(inputTable.getModel().getValueAt(i, 1).toString());
				double y = Double.parseDouble(inputTable.getModel().getValueAt(i, 2).toString());
				double l_wand = Math.abs(Double.parseDouble(inputTable.getModel().getValueAt(i, 3).toString())); //Math.abs(-1);
				inputTable.getModel().setValueAt(l_wand, i, 3);
				double t = Double.parseDouble(inputTable.getModel().getValueAt(i, 4).toString());
				int  o =  Integer.parseInt(inputTable.getModel().getValueAt(i, 5).toString());
				i++;

				
				WandQuerschnitt s1 = new WandQuerschnitt(t, lh);
				bezeichnung_Wind = "Wand " + String.valueOf(j) + "." + String.valueOf(i);
				waende[k] = new Wand(x, y, l_wand, t, lh, j, o, bezeichnung_Wind);
				waende[k].setQuerschnitte(s1, s2);

				k++;

				
				if(o != 1 && o!=0 ){
					
					throw new Exception("Es wird eine '0' oder eine '1' erwartet ! \n"
							+ "Steht die Wand Parallel zur Maßgebenden Windeinwirkung \n"
							+ "geben Sie '1' ein, andernfalls '0'");	
										
				}
				}
		}

		
		
		
		Gebaeude haus = new Gebaeude(b, l, h, waende, wind_in, et, a, d); 

		
		// Größenverhältnis der obersten Lasteinzugshöhe zu den der übrigen Etagen
		haus.setGroeßenverhaeltnis();
		System.out.println("Dies ist der relative Anteil der obersten Etage: " + haus.getGroeßenverhaeltnis() + "[-]");
		
		
		// Windkraft [kN] auf die oberste Decke 
		double FHo = haus.getWindKraft() * haus.getGroeßenverhaeltnis();
		haus.print2();
		haus.print();
		System.out.println("Die Windkraft auf die oberste Etagendecke beträgt: " + FHo + "  [kN]");
		System.out.println("Die Windkraft auf die unteren Etagendecken beträgt:  " + haus.getWindKraft()+ "  [kN]");
		System.out.println("Die Summe aller Trägheitsmomente pro Etage Beträgt:  " + haus.getSummeTraegheitsMoment(0, 0)+ "  [m^4]");
		
		System.out.println("");
		System.out.println("");

		
		
		//Schleife des LVF
		int p = 0;

		for (int j = 0; j < haus.getEtagen(); j++) {
			for (int i = 0; i < waende.length / haus.getEtagen(); i++) {

				System.out.println(" Trägheitsmoment I für  " + haus.getWaende()[p].getBezeichnung() + " : "
						+ haus.getWaende()[p].getTraegheitsmoment()+ "  [m^4]");

				System.out.println(" Lastverteilungsfaktor für  " + haus.getWaende()[p].getBezeichnung() + " : "
						+ haus.getWaende()[p].getLastverteilungsFaktor()+ "  [-]");
				System.out.println(" ");
				p++;
			}
		}

		
		int z = 0;
		for (int q = 0; q < haus.getEtagen(); q++) {
			for (int i = 0; i < haus.getWaende().length / haus.getEtagen(); i++) {

				// q = etagen (EG=0),    i = anzahlwände in etage (w1=0),    z=wandarrayanzahl
				outputTable.setValueAt(haus.getWaende()[z].getBezeichnung(), z, 0);
				outputTable.setValueAt(String.format("%.2f", haus.getWaende()[z].getTraegheitsmoment()), z, 1);

				z++;
			}
		}

		
		
		double[] summeQuerkraft = new double[haus.getWaende().length / haus.getEtagen()];

		z = haus.getWaende().length - 1;

		for (int jjj = 0; jjj < haus.getEtagen(); jjj++) {
			for (int i = haus.getWaende().length / haus.getEtagen() - 1; i >= 0; i--) {

				summeQuerkraft[i] += haus.getWaende()[z].getQuerkraft();

				outputTable.setValueAt(String.format("%.2f", summeQuerkraft[i]), z, 2);

				z--;
			}
		}

		double[] Moment = new double[haus.getWaende().length / haus.getEtagen()];
		double[] summeMoment = new double[haus.getWaende().length / haus.getEtagen()];

		z = haus.getWaende().length - 1;

		for (int jjj = 0; jjj < haus.getEtagen(); jjj++) {
			for (int i = haus.getWaende().length / haus.getEtagen() - 1; i >= 0; i--) {

				Moment[i] += haus.getWaende()[z].getQuerkraft() * (haus.getWaende()[z].getH() + haus.getD());
				summeMoment[i] += Moment[i];

				outputTable.setValueAt(String.format("%.2f", summeMoment[i]), z, 3);

				z--;
			}
		}

		
		// Aufräumen und Zeichnen
		
		
		
		
		
		this.viewerPanel.removeAllObjects3D();
		this.viewerPanel.resetCamera();
		for (int i = 0; i < waende.length; i++) {

			haus.getWaende()[i].drawSystem(this.viewerPanel);
			this.viewerPanel.render();
			
		}
		this.viewerPanel.render();
		this.viewerPanel.resetCamera();


		
		
		
	}
	
	

}

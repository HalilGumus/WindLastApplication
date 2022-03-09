
public class Gebaeude {
	

	// Attribute
	protected final double AUSSENDRUCKBEIWERT = 0.5 + 0.8; 
	
	private double b; 								// Breite des Gebäudes nicht maßgebende seite
	private double l; 								// Länge des Gebäundes
	private double h; 								// Höhe des Gebäudes
	private Wand[] waende; 							// Wände des Gebäudes
	private Windzone wind; 							// Windzone des Gebäudes
	private int e;									// Ttotaletage
	private double a; 								// Attikahöhe
	private double d;								//Deckenstärke
	private double groeßenverhaeltnis; 				// Verhältnis von konstruktiver Höhe des Obersten Geschosses zur Höhe der restlichen Geschosse

	// Konstruktor
	
	/**
	 * @param b = Gebäudebreite
	 * @param l = Gebäudelänge
	 * @param h = Gebäudehöhe
	 * @param e = Totale Anzahl der Etagen
	 * @param a = Attikahöhe
	 * @param d = Deckenstärke 
	 */
	public Gebaeude(double b, double l, double h,Wand[] waende, Windzone wind,  int e, double a, double d) {	// 

		this.b = b;
		this.l = l;
		this.h = h;								
		this.waende = waende;
		this.wind = wind;
		this.e = e;
		this.a = a;
		this.d = d;
		
		for (int i = 0; i < this.waende.length; i++ ) {
			this.waende[i].setGebaeude(this);
		}		
	}
	
	
	public double getGroeßenverhaeltnis() {
		return this.groeßenverhaeltnis;
	}
	
	public void setGroeßenverhaeltnis() {
		this.groeßenverhaeltnis = ((waende[0].getH() / 2) + this.d + this.a) / (waende[0].getH() + this.d);
	}
	
	public double getA(){
		return this.a;
	}
	
	public double getD(){
		return this.d;
	}
	
	public int getEtagen() {
		return this.e;
	}
	
	public double getH() {
		return this.h;
	}
	
	public double getL() {
		return this.l;
	}
	
	public double getB() {
		return this.b;
	}
	
	
	
	
	public void print(){
	
	if (this.h <= 10) {	
		System.out.println("Der Basis Geschwindkeitsdruck beträgt " + wind.getQph1() + "  kN/m²");
		}
	if ( this.h <= 18 && this.h > 10) {
		System.out.println("Der Basis Geschwindkeitsdruck beträgt " + wind.getQph2() + "  kN/m²");		}
	if (this.h <= 25 && this.h > 18) {
		System.out.println("Der Basis Geschwindkeitsdruck beträgt " + wind.getQph3() + "  kN/m²");		}
	}
	
	
	
	
	
	//das ist was auf eine Decke ankommt [kN]
	public double getWindKraft() { 			
		// konstruktive Höhe * Länge * winddruck
		
		
		// Windkraft für gebäude unter 10m in WZ 1 = ergebnis in kn/M²
		if (this.h <= 10) {
			return AUSSENDRUCKBEIWERT * (this.waende[0].getH()+ this.d) * this.l* wind.getQph1() ;
		}
		
		// Windkraft für gebäude unter 18m in WZ 1 = ergebnis in kn/M²
		if ( this.h <= 18 && this.h > 10) {
			return AUSSENDRUCKBEIWERT * (this.waende[0].getH()+ this.d) * this.l* wind.getQph2(); 	

		}
		
		// Windkraft für gebäude unter 25m in WZ 1 = ergebnis in kn/M²
		if (this.h <= 25 && this.h > 18) {
			return AUSSENDRUCKBEIWERT* (this.waende[0].getH()+ this.d) * this.l * wind.getQph3();
		}
		
		else {
		return -1;  	
		}
	}
	

	
	
	public double getSummeTraegheitsMoment (int etage, int orientierung){
		
		double summeTraegheit = 0;

		
		for (int i= 0 ; i < this.waende.length; i++) {			

		
			if ((this.waende[i].getEtage() == etage) && (this.waende[i].getOrientierung() == 1)) {
				
				summeTraegheit += this.waende[i].getTraegheitsmoment();
				
			}
			
		}
		
		return summeTraegheit;
		
	}

	
	public void print2() {
		System.out.println("Gebäudeabmessungen:");
		System.out.println("Breite: " + this.b + ", Länge: " + this.l + ", Höhe: " + this.h );
		


	}
	
	public Wand[] getWaende(){
		return this.waende;
	}
	
}
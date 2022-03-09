
public class Gebaeude {
	

	// Attribute
	protected final double AUSSENDRUCKBEIWERT = 0.5 + 0.8; 
	
	private double b; 								// Breite des Geb�udes nicht ma�gebende seite
	private double l; 								// L�nge des Geb�undes
	private double h; 								// H�he des Geb�udes
	private Wand[] waende; 							// W�nde des Geb�udes
	private Windzone wind; 							// Windzone des Geb�udes
	private int e;									// Ttotaletage
	private double a; 								// Attikah�he
	private double d;								//Deckenst�rke
	private double groe�enverhaeltnis; 				// Verh�ltnis von konstruktiver H�he des Obersten Geschosses zur H�he der restlichen Geschosse

	// Konstruktor
	
	/**
	 * @param b = Geb�udebreite
	 * @param l = Geb�udel�nge
	 * @param h = Geb�udeh�he
	 * @param e = Totale Anzahl der Etagen
	 * @param a = Attikah�he
	 * @param d = Deckenst�rke 
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
	
	
	public double getGroe�enverhaeltnis() {
		return this.groe�enverhaeltnis;
	}
	
	public void setGroe�enverhaeltnis() {
		this.groe�enverhaeltnis = ((waende[0].getH() / 2) + this.d + this.a) / (waende[0].getH() + this.d);
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
		System.out.println("Der Basis Geschwindkeitsdruck betr�gt " + wind.getQph1() + "  kN/m�");
		}
	if ( this.h <= 18 && this.h > 10) {
		System.out.println("Der Basis Geschwindkeitsdruck betr�gt " + wind.getQph2() + "  kN/m�");		}
	if (this.h <= 25 && this.h > 18) {
		System.out.println("Der Basis Geschwindkeitsdruck betr�gt " + wind.getQph3() + "  kN/m�");		}
	}
	
	
	
	
	
	//das ist was auf eine Decke ankommt [kN]
	public double getWindKraft() { 			
		// konstruktive H�he * L�nge * winddruck
		
		
		// Windkraft f�r geb�ude unter 10m in WZ 1 = ergebnis in kn/M�
		if (this.h <= 10) {
			return AUSSENDRUCKBEIWERT * (this.waende[0].getH()+ this.d) * this.l* wind.getQph1() ;
		}
		
		// Windkraft f�r geb�ude unter 18m in WZ 1 = ergebnis in kn/M�
		if ( this.h <= 18 && this.h > 10) {
			return AUSSENDRUCKBEIWERT * (this.waende[0].getH()+ this.d) * this.l* wind.getQph2(); 	

		}
		
		// Windkraft f�r geb�ude unter 25m in WZ 1 = ergebnis in kn/M�
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
		System.out.println("Geb�udeabmessungen:");
		System.out.println("Breite: " + this.b + ", L�nge: " + this.l + ", H�he: " + this.h );
		


	}
	
	public Wand[] getWaende(){
		return this.waende;
	}
	
}
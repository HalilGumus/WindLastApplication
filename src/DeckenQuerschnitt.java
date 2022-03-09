
public class DeckenQuerschnitt {

	private double b;// BREITE DER SECTION
	private double d;// Deckenstärke
	

	public DeckenQuerschnitt(double b, double h) {
		
		this.b = b;
		this.d = h;
	}

	public double getB() {
		return b;
	}

	public double getH() {
		return d;
	}
	        // Decke
 public double[][] getOutline( double t){
	 
	 
	 	// Vierecke und Jeder Ecke hat (X,Y) KOORDINATE
		double [][] w1 = new double [4][2];

		
		
		//WAND 3D
		double b1 = d/2;// vertikal
		double h2 = b/2;// horizontal
		
		w1[0][0] = -h2;
		w1[0][1] = -b1;
		
		w1[1][0] = h2;
		w1[1][1] = -b1;
		
		w1[2][0] = h2;
		w1[2][1] = b1;
		
		w1[3][0] = -h2;
		w1[3][1] = b1;
		
		
		return w1;
	}
	
}

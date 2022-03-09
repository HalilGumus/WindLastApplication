import java.awt.Rectangle;

public class WandQuerschnitt {

	private double b;			// BREITE DER SECTION
	private double h;			// HÖHE DER SECTION
	
	public WandQuerschnitt(double b, double h) {
		
		this.b = b;
		this.h = h;
	}

	public double getB() {
		return b;
	}

	public double getH() {
		return h;
	}
	
public double[][] getOutline( double t){
	
	// Vier Ecken mit (X,Y) KOORDINATEN
		double [][] w1 = new double [4][2];

		
		double b1 = h/2;
		double h2 = t/2;
		
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

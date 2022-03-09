import inf.v3d.obj.Arrow;
import inf.v3d.obj.Extrusion;
import inf.v3d.obj.Sphere;
import inf.v3d.view.*;
import static java.lang.Math.*;
import java.awt.Color;

public class Wand {

	private double x;
	private double y;
	private double l;
	private double t;
	private double h;
	private int etage;					
	private int orientierung; 						
	private String bezeichnung;
	private Gebaeude gebaeude;
	private WandQuerschnitt s1;
	private DeckenQuerschnitt s2;

	/**
	 * Konstruktor der Wand 
	 * @param l Länge der Wand
	 * @param t Tiefe der Wand
	 * @param h Höhe der Wand
	 * 
	 */
	public Wand(double x, double y, double l, double t, double h, int etage, int Orientierung, String bezeichnung)
	{
	
		this.x=x;
		this.y=y;
		this.l=l;
		this.t=t;
		this.h = h;
		this.etage = etage;
		this.orientierung = Orientierung;
		this.bezeichnung = bezeichnung;
	}
	

	public double getH() {
		return h;
	}

	public int getO() {
		return this.orientierung;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public double getTraegheitsmoment() { 													
		if (this.orientierung==1 ){

			return (this.gett() * pow(this.getL(), 3) / 12);  											 
																						
	}	else {
		return 0;
	}
		
		
	}
	
	public double getLastverteilungsFaktor() {

		if (this.orientierung== 1) {
			return this.getTraegheitsmoment() / gebaeude.getSummeTraegheitsMoment(etage, orientierung);

		} else {

			
			return 0; 
		}
	}

	public double getQuerkraft() {
		
		if (this.etage != gebaeude.getEtagen() -1 && this.orientierung == 1 ){										
			return this.getLastverteilungsFaktor() * gebaeude.getWindKraft();
		
		}
		else if (this.etage  ==  gebaeude.getEtagen()-1 && this.orientierung == 1) {
			
			return this.getLastverteilungsFaktor() * gebaeude.getWindKraft()* this.gebaeude.getGroeßenverhaeltnis() ; 
		}
		 
		
		else  {
			return 0;
		}
		}
	
	public int getEtage() {
		return this.etage;
	}

	public int getOrientierung() {
		return this.orientierung;
	}

	public Gebaeude getGebaeude() {
		return gebaeude;
	}

	public void setGebaeude(Gebaeude gebaeude) {
		this.gebaeude = gebaeude;
	}
	
	public void setEtage (int e){
		this.etage = e;
	}


	public double gett() {
		return t;
	}

	public void sett(double t) {
		this.t = t;
	}

	public double getL() {
		return l;
	}

	public void setL(double l) {
		this.l = l;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	
	
	public void drawSystem(ViewerPanel v) {

		this.drawWand(v);
	    drawLoad(v);
	    DrawDecke(v);
	    
	}
	
	public void setQuerschnitte(WandQuerschnitt s1, DeckenQuerschnitt s2){
		this.s1 = s1;
		this.s2 = s2;
	}
	
	private void drawLoad(ViewerPanel v) {

		for (int j = 1; j <= gebaeude.getEtagen(); j++) {
			
		
		
		for (double i = 0; i <= this.gebaeude.getL(); i = i + this.gebaeude.getL()/10) {

			
		if (j == this.gebaeude.getEtagen()) {
			
			Arrow a = new Arrow(-gebaeude.getWindKraft()*0.2*gebaeude.getGroeßenverhaeltnis(), i,  (this.h+gebaeude.getD())*j, -0.5, i,j*(this.h + gebaeude.getD()));			// Startpoint h/2
		
			
		a.setColor("red");
		a.setRadius(0.05 );
		a.setOpacity(0.01);
		v.addObject3D(a);
		
		}
		
			else {
				
				
				Arrow a = new Arrow(-gebaeude.getWindKraft()*0.2, i,  (this.h+gebaeude.getD())*j, -0.5, i,j*(this.h + gebaeude.getD()));// Startpoint h/2
				
				a.setColor("red");
				a.setRadius(0.05 );
				a.setOpacity(0.01);
				v.addObject3D(a);
				
			}
		}
			//Koordinatensystem
			Sphere f = new Sphere(0,0,0);
			Arrow ax = new Arrow(0,0,0,1.5,0,0);
			Arrow ay = new Arrow(0,0,0,0,1.5,0);			
			Arrow az = new Arrow(0,0,0,0,0,1.5);
			
			
			f.setRadius(0.1);
			f.setColor(Color.red);
			
			ax.setColor(Color.red);
			ay.setColor(Color.red);
			az.setColor(Color.red);
			
			v.addObject3D(f);
			v.addObject3D(ax,ay,az);
			
		}
		}

    public void drawWand(ViewerPanel v){

    	for (int i = 0; i < gebaeude.getEtagen(); i++) {
    		
    		
  
			
    	Extrusion e = new Extrusion();
    	
    	e.setOutline(this.s1.getOutline(this.t));
    	
    	e.setPoint1(x, y, h/2+(h+gebaeude.getD())*i);//(X;0;Z) START POINT
    	
			float hh =(float) (((getQuerkraft()*(gebaeude.getEtagen()-getEtage()))/50)+0.5);
			float ss= (float) 0.9f;
			float bb= 0.7f;
			
			
		//Aussteifende Wände
    	if(this.orientierung == 1  ) {
    		
    		e.setPoint2(x +l , y, h/2+(h+gebaeude.getD())*i );						

    	    	 if ((this.getQuerkraft()*(gebaeude.getEtagen()-getEtage()))< 25 ) {       	
        		e.setColor(Color.getHSBColor(hh, ss, bb));
        	}
    		 
    		 	else if ((this.getQuerkraft()*(gebaeude.getEtagen()-getEtage())) >= 25) {        	
        		e.setColor(Color.getHSBColor(1, ss, bb));
        		        	}
    	    		v.addObject3D(e);
    	}	
    	
    	
    
    	//Nicht aussteifende Wände
    	else if (this.orientierung == 0){
    		
    		e.setPoint2(x, y+l, h/2+(h+gebaeude.getD())*i); // Endpoint
    	
    		
    		e.setColor(Color.getHSBColor(0, 0, 1));
    		v.addObject3D(e);
		
    	}
    	}
    }

   
       public void DrawDecke(ViewerPanel v){
    	
    		Extrusion[] e = new Extrusion[gebaeude.getEtagen()];
    		for(int i = 0; i < gebaeude.getEtagen(); i++){
    			e[i] = new Extrusion();
    			e[i].setOutline(this.s2.getOutline(this.gebaeude.getD()));
    			e[i].setColor(Color.white);;
        		e[i].setOpacity(0.03);
        		
        		e[i].setPoint1(this.gebaeude.getB()/2, 0, (i+1)* (this.h+gebaeude.getD()));
        		e[i].setPoint2(this.gebaeude.getB()/2, this.gebaeude.getL(), (i+1)*(this.h+gebaeude.getD()));
        		v.addObject3D(e[i]);
    		}
    		
    	}
    	

    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

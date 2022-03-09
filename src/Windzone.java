public class Windzone  {

	
	// Geschwindkeitdruck qp für h<=10m, 10m<=h<=18m, 18m<=h<=25m
		private double qph1;
		private double qph2;
		private double qph3;
		private String bezeichnung;				// Windzone 1,2,3 und Windzone 4


		public Windzone(double qph1, double qph2, double qph3, String bezeichnung) {
			
			this.qph1 = qph1;
			this.qph2 = qph2;
			this.qph3 = qph3;
			this.bezeichnung = bezeichnung;
		}
		public String getBezeichnung() {
			return bezeichnung;
			
		}
		public double getQph1() {
			return qph1;
		}
		public double getQph2() {
			return qph2;
		}
		public double getQph3() {
			return qph3;
		}
		
}
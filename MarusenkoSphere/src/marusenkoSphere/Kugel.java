package marusenkoSphere;

import java.util.Random;
/**
 * Kugel-Datei
 * 
 * Kugel-Objekt, beinhaltet die Ganze Kugel f�r das ganze Programm, bringt den Zustand der Kugel jeweils von einem Objekt in ein n�chstes
 * 
 */
public class Kugel{
	/**
	 * tri ==> Array, welches die Dreiecke der Pole beinhaltet
	 * con ==> Array, welches die Verbindungsst�cke beinhaltet
	 */
	protected int[] tri; //Arrays for Triangles
	protected int[] con; //Arrays for Connectors
	
	
	/**
	 * Funktion welche zum Initialisieren der Kugel aufgerufen wird
	 * 
	 * dabei wird den Array die Gr�sse zugewiesen
	 */
	public Kugel(){
		tri = new int[24];
		con = new int[8];
	}//#END Kugel()
	
	/**
	 * Funktion, welche die Kugel fix f�llt
	 * 
	 * eine einfarbige Kugel, haupts�chlich f�rs Debugging der Grundfunktionen
	 */
	protected void FillKugelFix(){
		/**
		 * F�llen der Verbingungsst�cke
		 */
		for(int i = 0; i<8;i++){
			con[i]=1;
		}
		/**
		 * F�llen der Dreiecke
		 */
		for(int i = 0; i<24;i++){
			tri[i]=1;
		}
	}//#END FillKugelFix()

	/**
	 * Funktion zum zuf�lligen F�llen der Kugel mit realistischen Werten
	 */
	protected void FillKugelRandom(){
		/**
		 * Objekt rm als Zufallsobjekt, auswelcher dann die Zusatzzahl generiert wird
		 */
		Random rm = new Random();
		
		/**
		 * Alle Verbingungsst�cke werden auf -1 gesetzt, damit klar ist, dass diese noch keine Farbe haben
		 */
		for(int i = 0; i<8;i++){
			con[i]=-1;
		}
		/**
		 * Diesen 8 Verbingungsst�cken wird eine Farbe zu geteilt
		 */
		for(int i = 0; i<8;i++){
			/**
			 * Neue Zufallszahl
			 */
			int r = rm.nextInt(8-i);
			
			/**
			 * Finde das n�chste noch nicht einer Farbe zugeteilte Verbindungsst�ck
			 * 
			 * wenn gefunden, dann weise Farbe zu
			 */
			boolean found = false;
			while(!found){
				if(con[r]==-1){
					con[r] = i;
					found = true;
				}else{
					r++;
					r %= 8;
				}
			}
		}
		
		/**
		 * Die 24 Dreiecken werden auf -1 gesetzt, damit klar ist, dass diese noch keine Farbe haben 
		 */
		for(int i = 0; i<24;i++){
			tri[i]=-1;
		}

		/**
		 * Den 24 Dreiecken wird eine Farbe zugeteilt (jeweils 3 Dreiecken die selbe)
		 */
		for(int i = 0; i<24;i++){
			/**
			 * Neue Zufallszahl
			 */
			int r = rm.nextInt(24-i);
			/**
			 * Durch 3, damit 3 die selbe farbe kriegen
			 */
			int j = i/3;
			
			/**
			 * Finde das n�chste noch nicht einer Farbe zugeteilte Dreieck
			 * 
			 * wenn gefunden, dann weise Farbe zu
			 */
			boolean found = false;
			while(!found){
				if(tri[r]==-1){
					tri[r] = j;
					found = true;
				}else{
					r++;
					r %= 24;
				}
			}
		}		
	}//#END FillKugelRandom
	
	/**
	 * Funktion, welcher die Positionen einer Kugel wechselt, zum drehen eines Pols
	 * @param p1 : 1. Pol
	 * @param p2 : 2. Pol
	 * @param p3 : 3. Pol
	 * @param p4 : 4. Pol
	 */
	protected void cPol(int p1, int p2, int p3, int p4){
		int zs = tri[p1];
		tri[p1] = tri[p2];
		tri[p2] = tri[p3];
		tri[p3] = tri[p4];
		tri[p4] = zs;
	}//#END cPol
	
	/**
	 * Funktion welche cPol(int p1, int p2, int p3, int p4) korrekt aufruft, wenn PolNr und Anzahl der Drehungen gegeben werden  
	 * @param polNr : welcher Pol gedreht wird (int)(Dreieck id / 4) 
	 * @param anz : um welche Anzahl soll gedreht werden
	 */
	protected void changePol(int polNr, int anz){
		for(int i = 0; i<anz;i++){
			cPol(4*i,4*i+1,4*i+2,4*i+3);
		}
	}//#END changePol
	
	/**
	 * �ber pr�ft, ob ein Dreieck im richtigen Pol ist
	 * @param p : welches Dreieck gepr�ft werden soll
	 * @return : gibt true zur�ck, wenn im richtigen Pol
	 */
	protected boolean checkPoleCorrectPositions(int p){
		/**
		 * Setzte Return Variable auf false
		 */
		boolean r = false;
		
		/**
		 * Zurverk�rzung wird von der Position auf den Pol gek�rzt
		 */
		p = p/4;
		
		/**
		 * Switch gem�ss Pole, damit anschliessend gepr�ft werden kann ob im korrekten pole
		 * 
		 * wenn korrekt, dann wird die Return Variable auf true ge�ndert
		 */
		switch(p){
		
		case 0:
			if( tri[0] == con[2] || tri[0] == con[3] || tri[0] == con[6] || tri[0] == con[7] ||
				tri[1] == con[2] || tri[1] == con[3] || tri[1] == con[6] || tri[1] == con[7] ||
				tri[2] == con[2] || tri[2] == con[3] || tri[2] == con[6] || tri[2] == con[7] ||
				tri[3] == con[2] || tri[3] == con[3] || tri[3] == con[6] || tri[3] == con[7]){
				r = true;
			}
		case 1:
			if( tri[4] == con[4] || tri[4] == con[3] || tri[4] == con[8] || tri[4] == con[7] ||
				tri[5] == con[4] || tri[5] == con[3] || tri[5] == con[8] || tri[5] == con[7] ||
				tri[6] == con[4] || tri[6] == con[3] || tri[6] == con[8] || tri[6] == con[7] ||
				tri[7] == con[4] || tri[7] == con[3] || tri[7] == con[8] || tri[7] == con[7]){
				r = true;
			}
		case 2:
			if( tri[8] == con[4] || tri[8] == con[1] || tri[8] == con[5] || tri[8] == con[8] ||
				tri[9] == con[4] || tri[9] == con[1] || tri[9] == con[5] || tri[9] == con[8] ||
				tri[10]== con[4] || tri[10]== con[1] || tri[10]== con[5] || tri[10]== con[8] ||
				tri[11]== con[4] || tri[11]== con[1] || tri[11]== con[5] || tri[11]== con[8]){
				r = true;
			}
		case 3:
			if( tri[12]== con[1] || tri[12]== con[2] || tri[12]== con[5] || tri[12]== con[6] ||
				tri[13]== con[1] || tri[13]== con[2] || tri[13]== con[5] || tri[13]== con[6] ||
				tri[14]== con[1] || tri[14]== con[2] || tri[14]== con[5] || tri[14]== con[6] ||
				tri[15]== con[1] || tri[15]== con[2] || tri[15]== con[5] || tri[15]== con[6]){
				r = true;
			}
		case 4:
			if( tri[16]== con[1] || tri[16]== con[2] || tri[16]== con[3] || tri[16]== con[4] ||
				tri[17]== con[1] || tri[17]== con[2] || tri[17]== con[3] || tri[17]== con[4] ||
				tri[18]== con[1] || tri[18]== con[2] || tri[18]== con[3] || tri[18]== con[4] ||
				tri[19]== con[1] || tri[19]== con[2] || tri[19]== con[3] || tri[19]== con[4]){
				r = true;
			}
		case 5:
			if( tri[20]== con[5] || tri[20]== con[6] || tri[20]== con[7] || tri[20]== con[8] ||
				tri[21]== con[5] || tri[21]== con[6] || tri[21]== con[7] || tri[21]== con[8] ||
				tri[22]== con[5] || tri[22]== con[6] || tri[22]== con[7] || tri[22]== con[8] ||
				tri[23]== con[5] || tri[23]== con[6] || tri[23]== con[7] || tri[23]== con[8]){
				r = true;
			}
		}
		/**
		 * Gebe die Return Variable Zur�ck
		 */
		return r;
	}//#END checkPoleCorrectPositions
	
	/**
	 * �berpr�ft, ob ein Pole vollst�ndig gel�st ist
	 * @param polNr : welcher Pole
	 * @return : gibt true zur�ck, wenn Pole vollst�ngig gel�sst ist
	 */
	protected boolean checkPoleCorrectColor(int polNr){
		int anzKorr = 0;
		for(int i = 0; i<4; i++){
			if(checkPolePositionAndColor(polNr*4+i)){
				anzKorr++;
			}
		}
		if(anzKorr==4){
			return true;
		}else{
			return false;
		}
	}//#END checkPoleCorrectColor
	
	/**
	 * �berpr�ft, ob ein Dreieck mit dem Zwischenteil �bereinstimmt
	 * @param p : welches Dreieck gepr�ft werden soll
	 * @return : gibt true zur�ck, wenn mit Zwischenteil stimmt
	 */
	protected boolean checkPolePositionAndColor(int p){
		
		/**
		 * Setze Return Variable auf false
		 */
		boolean r = false;
		
		/**
		 * Pr�fe nun, ob es stimmt	
		 * 
		 * wenn ja, dann �ndere die Return Variable auf true	
		 */
		if(p == 18 || p == 11 || p == 15){
			if(tri[p] == con[0]){
				r = true;
			}
		}else
		if(p == 19 || p == 12 || p == 3){
			if(tri[p] == con[1]){
				r = true;
			}
		}else
		if(p == 16 || p == 0 || p == 4){
			if(tri[p] == con[2]){
				r = true;
			}
		}else
		if(p == 17 || p == 7 || p == 8){
			if(tri[p] == con[3]){
				r = true;
			}
		}else
		if(p == 22 || p == 10 || p == 14){
			if(tri[p] == con[0]){
				r = true;
			}
		}else
		if(p == 23 || p == 13 || p == 2){
			if(tri[p] == con[1]){
				r = true;
			}
		}else
		if(p == 20 || p == 1 || p == 5){
			if(tri[p] == con[2]){
				r = true;
			}
		}else
		if(p == 21 || p == 6 || p == 9){
			if(tri[p] == con[3]){
				r = true;
			}
		}
		/**
		 * Gibt die Return Variable zur�ck
		 */
		return r;
	}//checkPositioin

}
package marusenkoSphere;

import java.util.Random;

public class Kugel {
	//Variabeln des Objektes Kugel
	protected int[] p;
	protected int[] f;
	
	
	//Funktion zum Erstellen einer neuen Kugel
	public Kugel(){
		//Arrays die Grösse zuweisen
		p = new int[24];
		f = new int[8];
		
		//Arrays mit Farbenwert 0 füllen
		for(int i = 0; i<24;i++){
			p[i]=0;
		}
		
		for(int i = 0; i<8;i++){
			f[i]=0;
		}
	}//#End Kugel()
	
	
	//Funktion zum ändern eines Farbenwertes
	protected void ChangeColor(int Feld, int FarbenWert){
		p[Feld] = FarbenWert;
	}//#End void ChangeColor(int Feld, int FarbenWert)
	
	
	//Funktion zum ändern der Farbe eines Fixpunktes
	protected void ChangeFixColor(int Feld, int FarbenWert){
		f[Feld] = FarbenWert;
	}//#End void ChangeFixColor(int Feld, int FarbenWert)
	
	
	//Kugel zufällig mit Farben befüllen
	protected void FillKugleRandom(int AnzahlFarben){
		
		//Random für Zufall
		Random rm = new Random();
		//Variabel um später nur 2 bzw 4 farben zu zu losen
		int multiplizieren;
		//Variabel je nach Input definieren
		if(AnzahlFarben == 2){
			multiplizieren = 4;
		}else
		if(AnzahlFarben == 4){
			multiplizieren = 2;
		}else{
			multiplizieren = 1;
		}
		//Alle fixen Felder auf -1 setzten, damit sagen, dass noch keine Farbe
		for(int i = 0; i<8;i++){
			f[i]=-1;
		}
		//Die 8 Fixen Positionen Zu ordnen
		for(int i = 0; i<8;i++){
			int r = rm.nextInt(8-i);
			boolean found = false;
			while(!found){
				if(f[r]==-1){
					f[r] = i/multiplizieren;
					found = true;
				}else{
					r++;
					r %= 8;
				}
			}
		}
		//alle variablen Felder auf -1 setzten, damit sagen, dass noch keine Farbe
		for(int i = 0; i<24;i++){
			p[i]=-1;
		}
		//Die 24 variabeln Positionen zu ordnen
		for(int i = 0; i<24;i++){
			int r = rm.nextInt(24-i);
			int j = i/3;
			//r--;
			boolean found = false;
			while(!found){
				if(p[r]==-1){
					p[r] = j/multiplizieren;
					found = true;
				}else{
					r++;
					r %= 24;
				}
			}
		}
		
	}//#End void FillKugleRandom(int AnzahlFarben)
	
	protected Kugel TurnPol(int PolNr, int steps){//Im Uhrzeichersinn
		
		int PolField0 = p[0];
		int PolField1 = p[1];
		int PolField2 = p[2];
		int PolField3 = p[3];
		int Zwischenspeicher;
		
		if(PolNr == 1){
			PolField0 = p[0];
			PolField1 = p[1];
			PolField2 = p[2];
			PolField3 = p[3];
		}else
		if(PolNr == 2){
			PolField0 = p[5];
			PolField1 = p[13];
			PolField2 = p[14];
			PolField3 = p[6];
		}else
		if(PolNr == 3){
			PolField0 = p[7];
			PolField1 = p[15];
			PolField2 = p[16];
			PolField3 = p[8];
		}else
		if(PolNr == 4){
			PolField0 = p[9];
			PolField1 = p[17];
			PolField2 = p[18];
			PolField3 = p[10];
		}else
		if(PolNr == 5){
			PolField0 = p[11];
			PolField1 = p[19];
			PolField2 = p[12];
			PolField3 = p[4];
		}else
		if(PolNr == 6){
			PolField0 = p[20];
			PolField1 = p[21];
			PolField2 = p[22];
			PolField3 = p[23];
		}
		
		
		for(int i = 0; i<steps;i++){
			Zwischenspeicher = PolField0;
			PolField0 = PolField1;
			PolField1 = PolField2;
			PolField2 = PolField3;
			PolField3 = Zwischenspeicher;
		}
		
		if(PolNr == 1){
			p[0] = PolField0;
			p[1] = PolField1;
			p[2] = PolField2;
			p[3] = PolField3;
		}else
		if(PolNr == 2){
			p[5] = PolField0;
			p[13] = PolField1;
			p[14] = PolField2;
			p[6] = PolField3;
		}else
		if(PolNr == 3){
			p[7] = PolField0;
			p[15] = PolField1;
			p[16] = PolField2;
			p[8] = PolField3;
		}else
		if(PolNr == 4){
			p[9] = PolField0;
			p[17] = PolField1;
			p[18] = PolField2;
			p[10] = PolField3;
		}else
		if(PolNr == 5){
			p[11] = PolField0;
			p[19] = PolField1;
			p[12] = PolField2;
			p[4] = PolField3;
		}else
		if(PolNr == 6){
			p[20] = PolField0;
			p[21] = PolField1;
			p[22] = PolField2;
			p[23] = PolField3;
		}
		
		return this;
	}//# End Kugel TurnPol(int PolNr, int steps)
	
	protected void HalbDrehenVertikal(int steps){//Drehen In mittlerer Vertikaler Kante nach oben
		int[] PolFields = new int[12];
		int[] FixFields = new int[4];
		int Zwischenspeicher;
		
		PolFields[0] = p[2];
		PolFields[1] = p[16];
		PolFields[2] = p[23];
		PolFields[3] = p[11];
		
		PolFields[4] = p[3];
		PolFields[5] = p[8];
		PolFields[6] = p[22];
		PolFields[7] = p[19];
		
		
		
		FixFields[0] = f[2];
		FixFields[1] = f[6];
		FixFields[2] = f[7];
		FixFields[3] = f[3];
		
		
		for(int i = 0; i < steps; i++){
			Zwischenspeicher = PolFields[0];
			PolFields[0] = PolFields[1];
			PolFields[1] = PolFields[2];
			PolFields[2] = PolFields[3];
			PolFields[3] = Zwischenspeicher;
			
			Zwischenspeicher = PolFields[4];
			PolFields[4] = PolFields[5];
			PolFields[5] = PolFields[6];
			PolFields[6] = PolFields[7];
			PolFields[7] = Zwischenspeicher;
			
			Zwischenspeicher = FixFields[0];
			FixFields[0] = FixFields[1];
			FixFields[1] = FixFields[2];
			FixFields[2] = FixFields[3];
			FixFields[3] = Zwischenspeicher;
			
			
		}
		
		p[2]  = PolFields[0];
		p[16] = PolFields[1];
		p[23] = PolFields[2];
		p[11] = PolFields[3];
		        	
		p[3]  = PolFields[4];
		p[8]  = PolFields[5];
		p[22] = PolFields[6];
		p[19] = PolFields[7];
		              	
		f[2]  = FixFields[0];
		f[6]  = FixFields[1];
		f[7]  = FixFields[2];
		f[3]  = FixFields[3];
		
		
		TurnPol(4,1);
		
	}//#End void HalbDrehenVertikal(int steps)
	
	
	protected void HalbDrehenHorizontal(int steps){//Drehen In mittlerer Horizontalen Kante nach links (von vorne)
		int[] PolFields = new int[12];
		int[] FixFields = new int[4];
		int Zwischenspeicher;
		
		PolFields[0] = p[13];
		PolFields[1] = p[15];
		PolFields[2] = p[17];
		PolFields[3] = p[19];
		
		PolFields[4] = p[12];
		PolFields[5] = p[14];
		PolFields[6] = p[16];
		PolFields[7] = p[18];
		
		
		
		FixFields[0] = f[4];
		FixFields[1] = f[5];
		FixFields[2] = f[6];
		FixFields[3] = f[7];
		
		
		for(int i = 0; i < steps; i++){
			Zwischenspeicher = PolFields[0];
			PolFields[0] = PolFields[1];
			PolFields[1] = PolFields[2];
			PolFields[2] = PolFields[3];
			PolFields[3] = Zwischenspeicher;
			
			Zwischenspeicher = PolFields[4];
			PolFields[4] = PolFields[5];
			PolFields[5] = PolFields[6];
			PolFields[6] = PolFields[7];
			PolFields[7] = Zwischenspeicher;
			
			Zwischenspeicher = FixFields[0];
			FixFields[0] = FixFields[1];
			FixFields[1] = FixFields[2];
			FixFields[2] = FixFields[3];
			FixFields[3] = Zwischenspeicher;
			
			
		}
		
		p[13] = PolFields[0];
		p[15] = PolFields[1];
		p[17] = PolFields[2];
		p[19] = PolFields[3];
		     
		p[12] = PolFields[4];
		p[14] = PolFields[5];
		p[16] = PolFields[6];
		p[18] = PolFields[7];
		     
		f[4]  = FixFields[0];
		f[5]  = FixFields[1];
		f[6]  = FixFields[2];
		f[7]  = FixFields[3];
		
		
		TurnPol(6,1);
	}//#End void HalbDrehenHorizontal(int steps)
	
	
	protected void DrehenVertikal(int steps){
		int Zwischenspeicher0;
		int Zwischenspeicher1;
		int Zwischenspeicher2;
		int Zwischenspeicher3;
		
		for(int i= 0; i<steps; i++){
			Zwischenspeicher0 = p[0];
			Zwischenspeicher1 = p[1];
			Zwischenspeicher2 = p[2];
			Zwischenspeicher3 = p[3];
			
			p[0] = p[7];
			p[1] = p[15];
			p[2] = p[16];
			p[3] = p[8];
			
			p[7] = p[21];
			p[15] = p[20];
			p[16] = p[23];
			p[8] = p[22];
			
			p[21] = p[12];
			p[20] = p[4];
			p[23] = p[11];
			p[22] = p[19];
			
			p[12] = Zwischenspeicher0;
			p[4] = Zwischenspeicher1;
			p[11] = Zwischenspeicher2;
			p[19] = Zwischenspeicher3;
			
			
			Zwischenspeicher1 = f[0];
			f[0] = f[1];
			f[1] = f[5];
			f[5] = f[4];
			f[4] = Zwischenspeicher1;
			
			Zwischenspeicher1 = f[2];
			f[2] = f[3];
			f[3] = f[7];
			f[7] = f[6];
			f[6] = Zwischenspeicher1;
			
			
			TurnPol(2,3);
			TurnPol(4,1);
			
			
		}
		
	}//#End void DrehenVertikal(int steps)
	
	
	protected void DrehenHorizontal(int steps){
		int[] PolFields = new int[24];
		int[] FixFields = new int[8];
		int Zwischenspeicher;
		
		PolFields[0] = p[13];
		PolFields[1] = p[15];
		PolFields[2] = p[17];
		PolFields[3] = p[19];
		
		PolFields[4] = p[12];
		PolFields[5] = p[14];
		PolFields[6] = p[16];
		PolFields[7] = p[18];
		
		PolFields[8] = p[4];
		PolFields[9] = p[5];
		PolFields[10] = p[6];
		PolFields[11] = p[7];
		
		PolFields[12] = p[8];
		PolFields[13] = p[9];
		PolFields[14] = p[10];
		PolFields[15] = p[11];
		
		
		
		FixFields[0] = f[4];
		FixFields[1] = f[5];
		FixFields[2] = f[6];
		FixFields[3] = f[7];
		
		FixFields[4] = f[1];
		FixFields[5] = f[2];
		FixFields[6] = f[3];
		FixFields[7] = f[4];
		
		
		for(int i = 0; i < steps; i++){
			Zwischenspeicher = PolFields[0];
			PolFields[0] = PolFields[1];
			PolFields[1] = PolFields[2];
			PolFields[2] = PolFields[3];
			PolFields[3] = Zwischenspeicher;
			
			Zwischenspeicher = PolFields[4];
			PolFields[4] = PolFields[5];
			PolFields[5] = PolFields[6];
			PolFields[6] = PolFields[7];
			PolFields[7] = Zwischenspeicher;
			
			Zwischenspeicher = PolFields[8];
			PolFields[8] = PolFields[9];
			PolFields[9] = PolFields[10];
			PolFields[10] = PolFields[11];
			PolFields[11] = Zwischenspeicher;
			
			Zwischenspeicher = PolFields[12];
			PolFields[12] = PolFields[13];
			PolFields[13] = PolFields[14];
			PolFields[14] = PolFields[15];
			PolFields[15] = Zwischenspeicher;
			
			Zwischenspeicher = FixFields[0];
			FixFields[0] = FixFields[1];
			FixFields[1] = FixFields[2];
			FixFields[2] = FixFields[3];
			FixFields[3] = Zwischenspeicher;
			
			Zwischenspeicher = FixFields[4];
			FixFields[4] = FixFields[5];
			FixFields[5] = FixFields[6];
			FixFields[6] = FixFields[7];
			FixFields[7] = Zwischenspeicher;
		}
		
		p[13] = PolFields[0];
		p[15] = PolFields[1];
		p[17] = PolFields[2];
		p[19] = PolFields[3];
		     
		p[12] = PolFields[4];
		p[14] = PolFields[5];
		p[16] = PolFields[6];
		p[18] = PolFields[7];
		     
		p[4] = PolFields[8];
		p[5] = PolFields[9];
		p[6] = PolFields[10];
		p[7] = PolFields[11];
		     
		p[8] = PolFields[12];
		p[9] = PolFields[13];
		p[10] = PolFields[14];
		p[11] = PolFields[15];
		
		f[4]  = FixFields[0];
		f[5]  = FixFields[1];
		f[6]  = FixFields[2];
		f[7]  = FixFields[3];
		
		f[1] = FixFields[4];
		f[2] = FixFields[5];
		f[3] = FixFields[6];
		f[4] = FixFields[7];
		
		
		TurnPol(6,1);
	}//#End void DrehenHorizontal(int steps)
}

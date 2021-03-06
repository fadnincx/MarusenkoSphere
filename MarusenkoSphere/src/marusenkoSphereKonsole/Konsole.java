package marusenkoSphereKonsole;


import marusenkoSphere.Log;
import marusenkoSphereKugel.Kugel;
import marusenkoSphereKugel.SolveCheck;

/**
 * Konsolen-Klasse
 * 
 * Sehr primitive Methode um möglichst viele Kugeln zu lösen und Statistik zu führen
 *
 */
public class Konsole {

	public Kugel k;
	
	/**
	 * Konstruktor der Konsole und einzige Funktion
	 * @param k
	 */
	private double mittelwert = 0;
	private int min = 10000;
	private int max = 0;
	private int[] stats1 = new int[500];
	private int[] stats2 = new int[250];
	private int[] stats5 = new int[100];
	private int[] stats10 = new int[50];
	
	
	
	public Konsole(Kugel k){
		
		//Setze Aktuelle Kugel
		this.k = k;
	
		//Gehe maximale Anzahl schlaufen durch ((2^63)-1) 
		for(long i = 0;i<9223372036854775807L;i++){
			
			//fülle Kugel neu, wobei sie sofort auch gelöst wird
			k.fillRandom();
			
			//Setzet Kugel in die letzte Position
			k.setKugelToStateFromList(k.getSolvingListSize()-1);
			
			//Wenn nicht gelöst wurden
			if(!SolveCheck.isKugelSolved(k)){
				
				//Error ausgeben
				System.out.println("Error, kugel nicht gelöst!!!!");
				
				//Programm beenden
				System.exit(0);
				
			}
			
			//Aktuelle anzahl Schritte
			int aktAnz = k.getSolvingListSize()-1;
			
			//Wenn grösser als aktuelles Maximum
			if(aktAnz>max){
				
				//Setze neues Maximum
				max=aktAnz;
				
			//Wenn kleiner als aktuelles Minimum	
			}else if(aktAnz<min){
				
				//setzte neues Minimum
				min=aktAnz;
				
			}
			
			//Mittelwert aktuallisieren
			mittelwert=((mittelwert*i)+aktAnz)/(i+1);
			if(aktAnz<500){
				stats1[aktAnz]++;
				stats2[aktAnz/2]++;
				stats5[aktAnz/5]++;
				stats10[aktAnz/10]++;
			}
			
			//Jede zehnte Kugel status ausgeben
			if(i%10==0){
				
				//Gib aus, die wie vielte Kugel gelöst wurde
				System.out.println(i+". Kugel solved in "+aktAnz+" steps - Mittelwert: "+mittelwert+" Min: "+min+" Max: "+max);
				
			}
			
			//Alle 1000000 die statistik ausgeben
			if(i%100000==0&&i!=0){
				
				//Statistik....
				String s = "";
				
				for(int j = 0; j<500; j++){
					s+=j+";"+stats1[j]+";"+stats2[j/2]/2+";"+stats5[j/5]/5+";"+stats10[j/10]/10+"\n";
				}
				
				//Als DebugLog speichern
				Log.DebugLog(s);
				
			}
			
		}
		
	}
	
}


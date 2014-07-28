package marusenkoSphereKugel;

import java.util.ArrayList;

import marusenkoSphereGUI.Manager;

/**
 * Diese Klasse übernimmt die ganz Steuerung der Drehungen. Lediglich die Berechnungen
 * weden von der RenderKugel-Klasse übernommen
 *
 */
public class DrehungsManager {
	
	//Um welchen Pol gedreht wird
	private int drehungsPol;
	//In welche Richtung gedreht wird
	private int drehungsRichtung;
	//In welchem Modus gedreht wird (Nur Pol oder halbe Kugel)
	private int drehungsModus;
	//Wie gross die gesammte Drehung um diesen Pol sein muss
	private double gesamteDrehung;
	//Wie gross die verbleibende Drehung um diesen Pol ist
	private double bleibendeDrehung;
	//Bei welchem Step die Kugel vorher war
	private int oldStep;
	//Bei welchem Step die Kugel aktuell ist
	private int step;
	//Die Drehung um welche gedreht wird
	private String drehung;
	//Die Geschwindigkeit mit der gedreht werden soll
	private double sollRotationsGeschwindigkeit;
	//Die Aktuelle Framerate, zum berechnen wie schnell wirklich gedreht werden soll
	private double fps;
	//Geschwindigkeit mit der gedreht wird, framerate bereinigt
	private double istRotationsGeschwindigkeit;
	
	
	/**
	 * Die Variablen werden initialisiert
	 */
	public DrehungsManager(){
		drehungsPol = 0;
		drehungsRichtung = 1;
		gesamteDrehung = 0;
		bleibendeDrehung = 0;
		drehungsModus = 0;
		oldStep = 0;
		step = 0;
		drehung = "000";
	}
	
	/**
	 * Setzt eine Neue Drehung--> wird aufgerufen, wenn zu einem neuen Step gegangen wird
	 * @param newSphere : Die Neue Kugel als String
	 * @param arrayList : Die gesammte ArrayList
	 */
	public void setNewDrehung(String newSphere,ArrayList<String>arrayList){
		//Trim, damit allfällige Leerzeichen abgetrennt werden 
		newSphere = newSphere.trim();
		//Den String splitten
		String[] sp = newSphere.split("n");
		//Drehung aus dem Split nehmen
		drehung = sp[2];
		//den OldStep setzen
		oldStep = step;
		//den neuen Step auslesen
		step = Integer.parseInt(sp[1]);
		//pol+anz+modus
		//Pol, Modus auslesen, sowie die Richtung auf 1 setzen
		drehungsPol = Integer.parseInt(drehung.substring(0,1));
		drehungsModus = Integer.parseInt(drehung.substring(2,3));
		drehungsRichtung = 1;
		
		
		//Falls die Kugel zurück gedreht wird, dann muss die Drehung angepasst werden
		if(step+1==oldStep){
			//Zusätzlich muss die Drehung abgeschlossen werden
			drehungsRichtung*=-1;
			//Setze den neuen String für die Drehung
			String newDrehung = arrayList.get(step+1);
			//Bei diesem String auch die Leerzeichen abtrennen
			newDrehung = newDrehung.trim();
			//Den neuen String splitten
			sp = newDrehung.split("n");
			//die neue Drehung setzen
			drehung = sp[2];
			//Neuer Pol und Modus der Drehung setzten
			drehungsPol = Integer.parseInt(drehung.substring(0,1));
			drehungsModus = Integer.parseInt(drehung.substring(2,3));
		}else
		if(oldStep+1==step){
			//Wenn Normal weiter Gedreht wird, nichts zusätzlich machen
		}else{
			//WEnn Mehr als ein SChritt geändet wird, dann soll keine Drehung angezeigt werden
			drehungsRichtung=0;
			drehung="000";
		}
		//Wenn Drehung um 1 dann gesamte is 90°
		if(Integer.parseInt(drehung.substring(1, 2)) == 1){
			gesamteDrehung = 90.0;
		}else
		//Wenn Drehung um 2 dann gesamte ist 180°
		if(Integer.parseInt(drehung.substring(1, 2)) == 2){
			gesamteDrehung = 180.0;
		}else
		//Wenn Drehung um 3, dann gesammte ist -90° --> 90° und Richtung *-1
		if(Integer.parseInt(drehung.substring(1, 2)) == 3){
			gesamteDrehung = 90.0;
			drehungsRichtung *= -1;
		//Ansonsten Gesamte Drehung = 0
		}else{
			gesamteDrehung = 0.0;
		}

		//System.out.println("Step:"+step+" OldStep:"+oldStep+"-->Drehung"+drehung);
		
		//Setzet die die Gesamte Drehung als blebende Drehung, da noch nicht gedreht wurde
		bleibendeDrehung = gesamteDrehung;
	}
	/**
	 * Rufe die aktuell eingestellte Geschwindigkeit vom Manager ab
	 */
	private void getSollRotationVonManager(){
		sollRotationsGeschwindigkeit = Manager.getRotationSpeed();
	}
	/**
	 * Rufe die aktuelle Framerate vom Manager ab
	 */
	private void getFPSVonManager(){
		fps = Manager.getFPS();
	}
	/**
	 * Berechnet die aktuelle Geschwindigkeit um die gedreht wird
	 */
	private void updateRotationsGeschwindigkeit(){
		getSollRotationVonManager();
		getFPSVonManager();
		istRotationsGeschwindigkeit = (30/fps)*sollRotationsGeschwindigkeit;
	}
	/**
	 * die Rotation für das aktuelle Bild
	 * @return
	 */
	public double getRotationForFrame(){
		updateRotationsGeschwindigkeit();
		return bleibendeDrehung-istRotationsGeschwindigkeit;
	}
	/**
	 * die aktuell bleibende Drehung setzen
	 * @param drehung
	 */
	public void setAktuelleDrehung(double drehung){
		bleibendeDrehung = drehung;
	}
	/**
	 * Getter
	 * @return
	 */
	public int getDrehPol(){
		return drehungsPol;
	}
	public int getDrehRichtung(){
		return drehungsRichtung;
	}
	public int getDrehModus(){
		return drehungsModus;
	}
	public double bleibendeDrehung(){
		return bleibendeDrehung;
	}
}

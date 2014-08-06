package marusenkoSphereKugel;

import java.util.ArrayList;

import marusenkoSphereGUI.Manager;

/**
 * Diese Klasse übernimmt die ganz Steuerung der Drehungen. Lediglich die Berechnungen
 * weden von der RenderKugel-Klasse übernommen
 *
 */
public class AnimationsManager {
	
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
	private double userSetAnimationSpeed;
	
	//Die Aktuelle Framerate, zum berechnen wie schnell wirklich gedreht werden soll
	private double fps;
	
	//Geschwindigkeit mit der gedreht wird, framerate bereinigt
	private double doAnimationSpeed;
	
	
	/**
	 * Die Variablen werden initialisiert
	 */
	protected AnimationsManager(){
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
	protected void setNewDrehung(String newSphere,ArrayList<String>arrayList){
		
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
		
		
		//Falls die Kugel um ein Schritt zurück gedreht wird, dann muss die Drehung angepasst werden
		if(step+1==oldStep){
			
			//Eie Drehung ist eine andere, deshalb muss eine andere Geladen werden
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
			
			//Drehung muss retour ablaufen
			drehungsRichtung*=-1;
			
		}else
		if(oldStep+1==step){
			
			//Einen normaler Schritt nach vorne wird getätigt
			
		}else{
			
			//Wenn Mehr als ein Schritt geändet wird, dann soll keine Drehung angezeigt werden
			//Also Drehrichtung auf 0
			drehungsRichtung=0;
			//Sowie die drehung auf 000;
			drehung="000";
			
		}
		
		
		//Wenn Drehung um 1 dann gesamte is 90°
		if(Integer.parseInt(drehung.substring(1, 2)) == 1){
			
			//Setze neue Gesamt Drehung auf 90°
			gesamteDrehung = 90.0;
			
		}else
			
		//Wenn Drehung um 2 dann gesamte ist 180°
		if(Integer.parseInt(drehung.substring(1, 2)) == 2){
			
			//Setze neue Gesamt Drehung auf 180°
			gesamteDrehung = 180.0;
		}else
			
		//Wenn Drehung um 3, dann gesammte ist -90° --> 90° und Richtung *-1
		if(Integer.parseInt(drehung.substring(1, 2)) == 3){
			
			//Setzte neue Gesamt Drehung auf 90°
			gesamteDrehung = 90.0;
			
			//Zusätzlich drehe in die andere Richtung
			drehungsRichtung *= -1;
			
			
		//Ansonsten ist die drehung um 0°
		}else{
			
			//Setzte neue Gesamt Drehung auf 0°
			gesamteDrehung = 0.0;
		}
		
		//Setzet die die Gesamte Drehung als blebende Drehung, da noch nicht gedreht wurde
		bleibendeDrehung = gesamteDrehung;
		
	}
	
	
	/**
	 * Rufe die aktuell eingestellte Animationnsgeschwindigkeit vom Manager ab
	 */
	private void getAnimaitionSpeedFromManager(){
		userSetAnimationSpeed = Manager.getAnimationsSpeed();
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
	private void updateAnimationsSpeed(){
		
		//Aktuallisiere die vom Benutzer eingestellte Animationsgeschwindigkeit
		getAnimaitionSpeedFromManager();
		
		//Aktuallisiere die aktuelle Bildfrequenz
		getFPSVonManager();
		
		//Berechne die Geschwindigkeit mit der gedreht werden soll, so dass unabhänglig von Bildfrequenz ist
		doAnimationSpeed = (60/fps)*userSetAnimationSpeed;
		
	}
	
	
	/**
	 * gibt die Rotation für das akutelle Bild zurück
	 * zusätzlich verwaltung der weiteren Drehungen
	 */
	public double getRotationForFrame(){
		
		//Aktuallisiere die Variablen 
		updateAnimationsSpeed();
		
		//Wenn bleibende Drehung > die aktuelle drehung
		if(bleibendeDrehung>doAnimationSpeed){
			
			//Rechne bleibendeDrehung - aktuell
			bleibendeDrehung -= doAnimationSpeed;
			
			//gib die bleibendeDrehung zurück
			return bleibendeDrehung;
		
		}else
			
		//Damit eine minimale Pause zwischen den drehungen entsteht (wirkt schöner)		
		if(bleibendeDrehung>-doAnimationSpeed*10){
			
			//Rechne 10 Bilder lang weiter bleibendeDrehung - aktuell
			bleibendeDrehung -= doAnimationSpeed;
			
			//Gib jedoch 0 zurück
			return 0.0;
			
		//Nach 10 Bildern pause
		}else{
			
			//ändere den Status im Manager, dass Animation fertig ist	
			Manager.setAnimationFinished(true);	
			
			//Gib als Drehung 0 zurück
			return 0.0;
		}
	}
	
	/**
	 * Getter
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
}

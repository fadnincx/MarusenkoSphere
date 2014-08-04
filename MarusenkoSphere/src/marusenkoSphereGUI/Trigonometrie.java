package marusenkoSphereGUI;
/**
 * Funktion, welche die Trigonometrischen Funktionen Sinus und Cosinus für jedes Grad berechnen und sie in einem Array speichern, 
 * damit diese nur einmal berechnet werden müssen
 *
 */

public class Trigonometrie {
	
	//Array mit den Werten [0 = Sinus, 1 = Cosinus][Grad von 0-359]
	public static double[][] trigonometrie = new double[2][360];
	
	//Variable die Speichert, ob die Werte schon berechnet wurden
	private static boolean isCalc = false;
	
	/**
	 * Funktion, welche die Werte berechnet
	 */
	public static void CalcTrigonometrie(){
		
		//Gehe für alle Grad von 0-359 durch und speichere Werte
		for(int i = 0; i<360; i++){
			trigonometrie[0][i] = Math.sin(Math.toRadians(i));
			trigonometrie[1][i] = Math.cos(Math.toRadians(i));
		}
		//Setzte Variabel auf berechnet
		isCalc = true;
	}
	
	/**
	 * Funktion welche den Input Validiert und den Wert zurück gibt
	 * @param alpha : Winkel in Grad
	 * @param SinCos : Sinus = 0; Cosinus = 1
	 * @return
	 */
	private static double getValue(double alpha, int SinCos){
		
		//Wenn noch nicht berechnet, dann berechne jetzt
		if(!isCalc){CalcTrigonometrie();}
		
		//+360, damit sicher aller Werte positiv --> Negative Array Indizes gibt es nicht
		alpha+=360;
		
		//Modulo 360, damit Wert zwischen 0 und 395
		alpha%=360;
		
		//Gibt den Wert aus dem Array zurück --> Winkel wird noch zu einem Integer konvertiert, sollte jedoch schon vorher eine ganz Zahl sein.
		return trigonometrie[SinCos][(int) alpha];
	}
	
	/**
	 * Gibt den Sinus aus dem berechneten Array aus
	 */
	public static double sin(double alpha){
		return getValue(alpha, 0);
	}
	
	/**
	 * Gibt den Sinus aus dem berechneten Array aus
	 */
	public static double cos(double alpha){
		return getValue(alpha, 1);
	}

}

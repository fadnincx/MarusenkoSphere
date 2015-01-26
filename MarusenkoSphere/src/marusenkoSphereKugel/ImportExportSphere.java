package marusenkoSphereKugel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ImportExportSphere {

	/**
	 * Speichert die gegebene Kugel in der gegebenen Datei
	 */
	public static boolean save(File file, Kugel k){
		return writeFile(file, k.returnSphereToFile());
	}
	
	/**
	 * Öffnet die Kugel aus der gegebenen Datei und gibt infos an die gegebene Kugel weiter
	 */
	public static boolean open(File file, Kugel k){
		return k.recieveSphereFromFile(readFile(file));
	}
	
	/**
	 * Schreibt ein Text in eine Datei
	 */
	private static boolean writeFile(File file, String s){	
		//Erstelle File Objekt mit Ort beinhaltender Ordner von file
		File parent = file.getParentFile();

		//Wenn dieser Ordner nicht existiert errir zurück geben
		if(!parent.exists()){
		    return false;
		} 
				
		//Versuche Datei zu erstellen ansonsten wirf eine Exception 
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		//Versuche Datei zu schreiben
		try {
			//Versuche einen BufferedWriter als neuer FileWriter initialisieren
		   	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		   	//Datei schreiben
			writer.write(s);
			//Writer schliessen
			writer.close();
		} catch (IOException e) {
			//Bei einem Fehler wirf eine Exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Liest eine Datei und gibt den Inhalt zurück
	 * @param file
	 * @return
	 */
	private static String readFile(File file){	
		
		//Erstelle File Objekt mit Ort beinhaltender Ordner von file
		File parent = file.getParentFile();

		//Wenn dieser Ordner nicht existiert versuche Ihn zu erstellen sonst wirf eine Exception-->in Log schreiben geht nicht...
		if(!parent.exists()){
		    throw new IllegalStateException("Ordner existiert nicht: " + parent);
		} 
		
		//Versuche Datei zu erstellen ansonsten wirf eine Exception 
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Initialisiere die Output Variable
		String out = "";
		
		//Initialisiere FileReader zum bisherige Datei zu lesen 
		FileReader fileR = null;

		//Versuche Datei in out zu lesen
		try {
			//FileReader get File
			fileR = new FileReader(file);
			
			//übergebe an einen BufferedReader
			BufferedReader reader = new BufferedReader(fileR);
		    String line = "";
		    
		    //Gehe Zeile für Zeile durch und schreibe in die Outputvariable out
		    while ((line = reader.readLine()) != null) {
		    	out += line + "\n";
		    }
		    
		    //Wenn möglich FileReader wieder schliessen
		    if(file != null) {
				try{
					fileR.close();
				}catch(IOException e){
					// Ignoriere Fehler beim Schliessen des FileReaders
				}
		    }
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		//Text zurückgeben
		return out;
	}
}

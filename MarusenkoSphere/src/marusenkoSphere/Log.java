package marusenkoSphere;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Die Log Klasse schreibt Fehler die während dem Ausführen des Programms auftreten in eine Log-Datei.
 * Auch können Allgemeine Infos in ein Debug-Log geschrieben werden.
 * 
 * 
 * Dadurch dass alle Methoden statisch sind muss kein Log-Objekt erstellt werden um auf diese zurück zu greifen
 * 
 *
 */
public class Log {

	/**
	 * Schreibe ErrorLog zu Datei
	 */
	public static void ErrorLog(String error){
		if(Settings.ENABLELOG){
			LogFile(error,Settings.LOGPATH+"/error.log");
			System.out.println("!!!Error!!!  "+error);
		}
	}
	
	/**
	 * Schreibe DebugLog zu Datei
	 */
	public static void DebugLog(String debug){
		if(Settings.ENABLELOG){
			LogFile(debug,Settings.LOGPATH+"/debug.log");
			System.out.println("Debug, "+debug);
		}
	}
	
	/**
	 * Schreibe den Log in die Datei
	 * @param log
	 */
	private static void LogFile(String log, String logfile){
		
		//Erstelle File Objekt mit Ort logfile
		File file = new File(logfile);
		
		//Erstelle File Objekt mit Ort beinhaltender Ordner von file
		File parent = file.getParentFile();

		//Wenn dieser Ordner nicht existiert versuche Ihn zu erstellen sonst wirf eine Exception-->in Log schreiben geht nicht...
		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Ordner kann nicht erstellt werden: " + parent);
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
			//FileReader get logFile
			fileR = new FileReader(logfile);
			
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
		
		//Datum generieren
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date date = new Date();
		
		//Vor die Ausgabe Datum hängen
		out = out+("\n"+dateFormat.format(date)+" ");
		out = out+log;
		
		//Versuche Log zu schreiben
		try {
			//Versuche einen BufferedWriter als neuer FileWriter mit logfile initialisieren
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(logfile));
	    	//Log schreiben
			writer.write(out);
			//Writer schliessen
			writer.close();
		} catch (IOException e) {
			//Bei einem Fehler wirf eine Exception
			e.printStackTrace();
		}
	}
	
}

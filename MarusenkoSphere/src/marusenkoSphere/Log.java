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

public class Log {

	
	/**
	 * Schreibe ErrorLog zu Datei
	 * @param error
	 */
	public static void ErrorLog(String error){
		LogFile(error,"/home/marcel/marusenkoLog/error.log");
	}
	/**
	 * Schreibe DebugLog zu Datei
	 * @param debug
	 */
	public static void DebugLog(String debug){
		LogFile(debug,"/home/marcel/marusenkoLog/debug.log");
	}
	
	/**
	 * Schreibe den ErrorLog in die Datei
	 * @param error
	 */
	private static void LogFile(String error, String logfile){
		//Error Log Dateiname
		//String logfile = "/home/marcel/marusenkoLog/error.log";
		//Erstelle File Objekt mit Ort logfile
		File file = new File(logfile);
		//Erstelle File Objekt mit Ort Beinhaltender Ordner von file
		File parent = file.getParentFile();

		//Wenn Ordner nicht existiert versuche zu erstellen sonst Exception
		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		} 
		//Versuche Datei zu erstellen
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
			//Übergebe an einen BufferedReader
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(fileR);
		    String line = "";
		    //Gehe Zeile für Zeile durch und schreibe in out
		    while ((line = reader.readLine()) != null) {
		    	out += line + "\n";
		    }
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			//Wenn möglich FileReader wieder schliessen
			if(file != null) {
				try{
					fileR.close();
				}catch(IOException e){
					// Ignore issues during closing 
				}
		    }
		}
		
		//Datum generieren
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		//Vor die Ausgabe Datum hängen
		out = out+("\n"+dateFormat.format(date)+" ");
		out = out+error;
		
		//Versuche Log zu schreiben
		try {
			//BufferedWriter als neuer FileWriter zu logfile initialisieren
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(logfile));
	    	//Log schreiben
			writer.write(out);
			//Writer schliessen
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

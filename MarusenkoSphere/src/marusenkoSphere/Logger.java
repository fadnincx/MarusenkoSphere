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

import marusenkoSphereKugel.Kugel;

public class Logger {
	/**
	 * 
	 * Logger Klasse 
	 * 
	 * Schreibt Error und Debugging Logs in die Datei
	 * 
	 */
	protected String logfile;
	
	/**
	 * Konstruktor, Setzt den Dateinamen des aktuellen Logger Objekts
	 * @param filename : Dateiname
	 */
	public Logger(String filename){
		this.logfile = "D:/marusenkoLog/"+filename+".txt";
		
	}

	/**
	 * Schreibt ein Log mit Kugel
	 * @param k : Kugel
	 * @param error : Log
	 */
	public void log(Kugel k, String error){
		/**
		 * Liest bisherige Datei aus
		 */
		String out = readLogFile();
		/**
		 * Generiere Datum für anfang der Zeile
		 */
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		/**
		 * Datum und Error zu ausgabe hinzufügen
		 */
		out = out+("\n"+dateFormat.format(date));
		out = out+error;
		/**
		 * Kugel hinzufügen
		 */
		for(int i = 0; i<24;i++){
			out = out+"\n Tri["+i+"]: "+k.tri[i];
		}
		for(int i = 0; i<8;i++){
			out = out+"\n Con["+i+"]: "+k.con[i];
		}
		/**
		 * Log schreiben
		 */
		writeLog(out);
	}
	/**
	 * Schreibe ein Log ohne Kugel
	 * @param error : Log
	 */
	public void log(String error){
		/**
		 * Liest bisherige Datei aus
		 */
		String out = readLogFile();
		/**
		 * Generiere Datum für anfang der Zeile
		 */
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		/**
		 * Datum und Error zu ausgabe hinzufügen
		 */
		out = out+("\n"+dateFormat.format(date)+" ");
		out = out+error;
		/**
		 * Log schreiben
		 */
		writeLog(out);
	}
	/**
	 * Schreibt den Log in die Datei
	 * @param log
	 */
	private void writeLog(String log){
		/**
		 * Versuche BufferedWriter zu öffnen, dann zu schreiben und anschliessend zu schliessen
		 */
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(logfile));
			writer.write(log);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    	
	}
	/**
	 * Liest den die Bisherige Datei ein und gibt sie als String zurück
	 * @return : bisherige Datei
	 */
	@SuppressWarnings("resource")
	private String readLogFile() {
		/**
		 * Prüfe, ob Datei existiert, wenn nicht erstellen
		 */
		checkFileExist();
		String returnValue = "";
		FileReader file = null;
		  /**
		   * Lese Datei
		   */
		try {
			file = new FileReader(logfile);
			BufferedReader reader = new BufferedReader(file);
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		    	returnValue += line + "\n";
		    }
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(file != null) {
				try{
					file.close();
				}catch(IOException e){
					// Ignore issues during closing 
				}
		    }
		}
		return returnValue;
	}
	/**
	 * Prüfen, ob Datei existiert, sonst erstellen
	 */
	private void checkFileExist(){
		File file = new File(logfile);
		File parent = file.getParentFile();

		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		} 
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

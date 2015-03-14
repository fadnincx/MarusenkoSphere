package marusenkoSphere;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CloseOnUSBKey {

	private static File[] root = null;
	
	
	//Initialisiere CloseOnUSBKey in neuem Thread
	public CloseOnUSBKey(){
		new Thread(){
			public void run() {
               go();
            }
		}.start();
	}
	
	//Endlos schlaufe
	private void go(){
		while(true){
			
			//Suche nach der Datei
			if(searchForFiles()){
				
				//Wenn der Schlüssel vorhanden ist
				if(getKey().equals("1200995")){
					
					//Beende das Programm
					System.exit(0);
					
				}
				
			}
			
			//Versuche 1s zu warten
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Do it again
		}
		
	}
	
	//Suche in den gegebenen Verzeichnissen nach der KeyDatei und gib gegebenenfalls den Key zurück
	private String getKey(){
		String id = "";
		for (File file : root){
			id = getId(file);
			if (id!="") {
				return id;
			}

		}
		return "";
	}
	
	//Suche nach neuen Root Verzeichnissen
	private boolean searchForFiles(){
		
		//Veränderungen fanden keine statt
		boolean changed;
		
		//Wenn das erste Mal aufgerufen
		if(root==null){
			
			//speichere Liste der root verzeichnisse
			root = File.listRoots();
			
			//veränderungen fanden statt
			changed = true;
			
		}else{
			
			//Bekomme die liste der root Verzeichnisse
			File[] newcheck = File.listRoots();
			
			//Wenn diese verändert wurde
			if(arrayChanged(root, newcheck)){
				
				//Veränderung fand statt
				changed = true;
				
				//neues root setzen
				root = newcheck;
				
			//Wenn nichts verändert wurde	
			}else{
				
				//Keine veränderung
				changed = false;
			}
			
		}
		
		//Gib zurück, ob verändert wurde
		return changed;

	}
	
	
	//Vergleicht 2 Arrays File[] auf die Länge
	private boolean arrayChanged(File[] array1, File[] array2) {
		 
		//Wenn die Länge nicht gleichlang ist, wurde verändert
		if (array1.length!=array2.length) {
			
			return true;
			
		}
		
		return false;
		
	}
 
	//Bekomme die ID aus einem Ordner
	private String getId(File f){
 
		//Bekomme Dateien eines Ordners
		File[] listFiles = f.listFiles();
		
		//Wenn es Dateien gibt
		if (listFiles!=null) {
			
			//Gehe alle Dateien durch
			for (File file : listFiles){
				
				//Wenn es eine entsprechende Datei gibt
				if (file.isFile() && file.getName().equalsIgnoreCase("MyCloseKey.dat")) {
					
					//Versuche Datei zu lesen
					try{
						String out = "";
						FileReader fileR = new FileReader(file);
						
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
						
					    //Ersetze Umbruch mit Leerschlag und entferne diese am Anfang und Ende
					    out = out.replace('\n', ' ');
					    
					    out = out.trim();
					    
					    return out;
					    
					//Igonoriere die Exceptions  
					}catch (FileNotFoundException e){
						//Ignorieren
					}catch (IOException e){
						//Ignorieren
					}
					
				}
 
			}
			
		}
 
		return "";
		
	}
	
	
}

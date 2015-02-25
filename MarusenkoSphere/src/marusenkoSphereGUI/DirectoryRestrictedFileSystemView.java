package marusenkoSphereGUI;

import java.io.File;
import javax.swing.filechooser.FileSystemView;

/**
 * Klasse um den Dateizugriff beim Öffnen/Speichern einzuschränken
 */
public class DirectoryRestrictedFileSystemView extends FileSystemView{
	
	//RootDirectories
    private final File[] rootDirectories;

    //Erstelle FileSystemView mit Beschränkung mit nur einem rootDirectory
    public DirectoryRestrictedFileSystemView(File rootDirectory){
        this.rootDirectories = new File[] {rootDirectory};
    }

    //Erstelle FileSystemView mit Beschränkung mit mehereren rootDirectoies
    public DirectoryRestrictedFileSystemView(File[] rootDirectories){
        this.rootDirectories = rootDirectories;
    }

    //Funktion zum Ordner erstellen --> return null
    @Override
    public File createNewFolder(File containingDir){       
    	return null;
    }

    //Bekomme die Rootdirectories
    @Override
    public File[] getRoots(){
        return rootDirectories;
    }

    //Prüfe ob der gegebene Ordner Root ist
    @Override
    public boolean isRoot(File file){
        for (File root : rootDirectories) {
            if (root.equals(file)) {
                return true;
            }
        }
        return false;
    }
    
    //Setzte Homedirectory
    @Override
    public File getHomeDirectory() { 
    	return rootDirectories[0]; 
    }

    //Setzte default directory
	@Override
	public File getDefaultDirectory() {
		return rootDirectories[0]; 
	}
}

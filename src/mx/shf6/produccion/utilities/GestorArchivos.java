package mx.shf6.produccion.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GestorArchivos {
    
    //VARIABLES
    private static List<String> content;
    public static String nameDB;
    public static String hostDB;
    public static String userDB;
    public static String passwordDB;
    public static String claveEquipo;
    
    public static void leerArchivo(){
        try {
        	//PUT CONNECTIONDATA FILE ON C:\MaxicomercioTools\ PATH
            GestorArchivos.content = Files.readAllLines(Paths.get("C:\\MaxicomercioTools\\ConnectionData.dat"));
            GestorArchivos.nameDB = content.get(2);
            GestorArchivos.hostDB = content.get(4);
            GestorArchivos.userDB = content.get(6);
            GestorArchivos.passwordDB = content.get(8);
        } catch (IOException | IndexOutOfBoundsException ioe) {
            Notificacion.dialogoException(ioe);
        }//END TRY/CATCH
    }//END METHOD
    
    /**
     *
     * @return
     */
    public static String aCadena() {
        String datos = "Base de Datos: " + nameDB + "\n"
                + "Host: " + hostDB + "\n"
                + "Usuario: " + userDB + "\n"
                + "Contraseña: " + passwordDB;
        return datos;
    }//END METHOD  
    
    public static boolean cargarArchivo(File archivo, String destino) {
    	if (archivo.renameTo(new File(destino)))
    		return true;
    	else
    		return false;
    }//FIN METODO
    
}//END CLASS

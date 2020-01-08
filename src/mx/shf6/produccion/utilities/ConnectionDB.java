package mx.shf6.produccion.utilities;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB extends Thread{
    
    //VARIABLES
    private Connection conexion;
    private final String nombreBD;
    private final String hostBD;
    private final String usuarioBD;
    private final String contrasenaBD;
    
    public ConnectionDB(String nBD, String hBD, String uBD, String cBD) {
        this.nombreBD = nBD;
        this.hostBD = hBD;
        this.usuarioBD = uBD;
        this.contrasenaBD = cBD;
    }//END METHOD
    
    public Connection conectarMySQL(){
        try{
            Class.forName("java.sql.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + hostBD + "/" + nombreBD + "?useSSL=true", usuarioBD, contrasenaBD);
            //System.out.println("Conexion exitosa");
            return conexion;
        }catch(ClassNotFoundException | SQLException e){
            Notificacion.dialogoException(e);
        	//System.out.println("Conexion rechazada: " + e.toString());
            return null;
        }//END TRY-CATCH
    }//END CONECTARMYSQL
    
    /* Método de cerrar concexion */
    public void terminarConexion(Connection connection){
        try{
        	connection.close();
            //System.out.println("Conexion finalizada");
        }catch(SQLException sqle){
            Notificacion.dialogoException(sqle);
        }//END TRY-CATCH
    }//END TERMINAR CONEXION
    
    public void run() {
    	String consulta = "SHOW SESSION VARIABLES LIKE 'wait_timeout'";
		try {
			while (true) {
				Statement sentencia = conexion.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					//System.out.println(resultados.getString(1));
					//System.out.println(resultados.getString(2));
				}//FIN WHILE
			Thread.sleep(100000);
			}//FIN WHILE
		} catch (SQLException | InterruptedException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
    }//FIN METODO
    
}//END CLASS

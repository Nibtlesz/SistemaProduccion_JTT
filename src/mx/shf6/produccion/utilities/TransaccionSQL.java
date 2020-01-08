package mx.shf6.produccion.utilities;

import java.sql.Connection;
import java.sql.SQLException;

public class TransaccionSQL {

	public static final int AUTOCOMMIT_OFF = 0;
	public static final int AUTOCOMMIT_ON = 1;
	public static final int COMMIT_TRANSACTION = 2;
	public static final int ROLLBACK_TRANSACTION = 3;
	
	public static void setStatusTransaccion(Connection connection, int status) {
		try {
			if (status == AUTOCOMMIT_OFF)
				connection.setAutoCommit(false);
			else if (status == AUTOCOMMIT_ON)
				connection.setAutoCommit(true);
			else if (status == COMMIT_TRANSACTION) {
				connection.commit();
				connection.setAutoCommit(true);
			} else if (status == ROLLBACK_TRANSACTION) {
				connection.rollback();
				connection.setAutoCommit(true);
			}//FIN IF/ELSE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METOOO
	
}//FIN CLASE

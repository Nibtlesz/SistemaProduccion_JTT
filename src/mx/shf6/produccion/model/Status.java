package mx.shf6.produccion.model;

public final class Status {

	//CONSTANTES
	public static final int NO_VISIBLE = 0;
	public static final int VISIBLE = 1;
	public static final int BLOQUEADO = 2;
	public static final int DESBLOQUEADO = 3;
	public static final int CANCELADO = 99;
	
	public static String toString(int status) {
		switch (status) {
			case NO_VISIBLE:
				return "No Visible";
			case VISIBLE:
				return "Visible";
			case BLOQUEADO:
				return "Bloqueado";
			case DESBLOQUEADO: 
				return "Desbloqueado";
			case CANCELADO:
				return "Cancelado";
			default:
				return "Null";
		}//FIN SWITCH
	}//FIN METODO
	
	public static int toInt(String status) {
		switch (status) {
			case "No Visible":
				return NO_VISIBLE;
			case "Visible":
				return VISIBLE;
			case "Bloqueado":
				return BLOQUEADO;
			case "Desbloqueado": 
				return DESBLOQUEADO;
			case "Cancelado":
				return CANCELADO;
			default:
				return -1;
		}//FIN SWITCH
	}//FIN METODO
	
}//FIN CLASE

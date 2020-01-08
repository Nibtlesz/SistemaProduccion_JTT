package mx.shf6.produccion.model;

public class TipoComponente {

	public static final String ENSAMBLE = "Ensamble";
	public static final String SUB_ENSAMBLE = "Sub Ensamble";
	public static final String PARTE_PRIMARIA = "Parte Primaria";
	public static final String COMPRADO = "Comprado";
	public static final String MATERIA_PRIMA = "Materia Prima";
	
	public static String toCharacter(String stringComponente) {
		switch (stringComponente) {
		case "Comprado":
			return "C";
		case "Materia Prima":
			return "M";
		case "Sub Ensamble":
			return "E";
		case "Parte Primaria":
			return "P";
		case "Ensamble":
			return "A";
		default:
			return "";	
		}//FIN SWITCH
	}//FIN METODO
	
	public static String toString(String charComponente) {
		switch (charComponente) {
		case "C":
			return "Comprado";
		case "M":
			return "Materia Prima";
		case "E":
			return "Sub Ensamble";
		case "P":
			return "Parte Primaria";
		case "A":
			return "Ensamble";
		default:
			return "";	
		}//FIN SWITCH
	}//FIN METODO
	
	public static boolean isComponenteBasico(String stringComponente) {
		if(stringComponente == COMPRADO || stringComponente == MATERIA_PRIMA)
			return true;
		else
			return false;
	}//FIN METODO
	
}//FIN METODO


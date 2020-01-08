package mx.shf6.produccion.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public class RestriccionTextField {
	
	public static void limitarNumeroCaracteres(TextField textField, int numeroCaracteres){
		textField.setOnKeyTyped(KeyEvent  -> {
			if (textField.getText().length()== numeroCaracteres) {
				KeyEvent.consume();
			}//FIN IF						
		});//FIN LISTENER
	}//FIN METODO
	
	public static void soloNumeros(TextField textField) {		
		textField.setTextFormatter(new TextFormatter<>(change ->
        (change.getControlNewText().matches("([0-9]*)?")) ? change : null));
	}//FIN METODO
	
	public static void soloLetras(TextField textField) {
		textField.setTextFormatter(new TextFormatter<>(change ->
        (change.getControlNewText().matches("([^0-9]*)?")) ? change : null));
	}//FIN METODO
	
	public static void limitarPuntoDecimal(TextField textField ) {
		final String pattern = "(\\d)+(\\.?\\d*)?$";
	    
	    
		textField.setOnKeyTyped(event -> {
	        String newText = textField.getText() + event.getCharacter();
	        if (!newText.matches(pattern)) {
	            event.consume();
	        }//FIN IF
	    });//FIN METODO
	}//FIN METODO
	
	public static final boolean validarEmail(String mail) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail);
       
        return matcher.matches();
	}//FIN METODO

}//FIN CLASE

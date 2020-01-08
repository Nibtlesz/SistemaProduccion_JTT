package mx.shf6.produccion.utilities;

import java.awt.Toolkit;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;

public class Notificacion {
    
    public static void dialogoException(Exception ex) {
    	Toolkit.getDefaultToolkit().beep();
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Alerta del Sistema");
        alert.setHeaderText("Manufacturas G | Error en el sistema");
        alert.setContentText("Ocurrio un error inesperado durante la ejecución del sistema: \n" + ex.getMessage());

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("El error fue ocacionado por:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }//END METHOD
    
    @SuppressWarnings("unused")
	public static void dialogoDetalleMensaje(String mensaje) {
    	Toolkit.getDefaultToolkit().beep();
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Información del Sistema");
        alert.setHeaderText("Manufacturas G");
        //alert.setContentText("Ocurrio un error inesperado durante la ejecución del sistema: \n" + mensaje);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        //ex.printStackTrace(pw);
        String exceptionText = mensaje;

        Label label = new Label("Lista de materiales:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }//END METHOD
    
    public static void dialogoAlerta(AlertType alertaTipo, String titulo, String mensaje) {
    	Toolkit.getDefaultToolkit().beep();
    	Alert alert = new Alert(alertaTipo);
    	alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Alerta del Sistema");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }//END METHOD
    
    public static String dialogoEntradaTexto(String titulo, String mensaje, String entrada) {
    	String retorno = "";
    	Toolkit.getDefaultToolkit().beep();
    	TextInputDialog alert = new TextInputDialog(entrada);
    	alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Alerta del Sistema");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        Optional<String> resultado = alert.showAndWait();
        if(resultado.isPresent()) 
        	retorno = resultado.get();
        return retorno;
    }//END METHOD
        
    public static boolean dialogoPreguntar(String titulo, String mensaje){
    	Toolkit.getDefaultToolkit().beep();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Alerta del Sistema");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }//END METHOD
    
}//END CLASS

package mx.shf6.produccion.view;

import java.security.SecureRandom;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleOrdenCompra;
import mx.shf6.produccion.model.DetalleOrdenProduccion;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.dao.DetalleOrdenCompraDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenProduccionDAO;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoEnProduccion {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private DetalleOrdenCompra detalleOrdenCompra;
	
	//VARIABLES
	private Integer cantidadActualizar;
	private Integer cantidadRestante;
	
	//CONSTANTES	
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoEnProduccion;
	@FXML private TextField campoTextoPedido;
	@FXML private TextField campoTextoActualizar;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		RestriccionTextField.soloNumeros(campoTextoActualizar);
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, DetalleOrdenCompra detalleOrdenCompra) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.detalleOrdenCompra = detalleOrdenCompra;
		this.inicializarComponentes();
		inicializarComponentes();
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {
		this.campoTextoPedido.setEditable(false);
		this.campoTextoPedido.setText(String.valueOf(this.detalleOrdenCompra.getPorEntregar()));
		this.campoTextoEnProduccion.setEditable(false);
		this.campoTextoEnProduccion.setText(String.valueOf(this.detalleOrdenCompra.getSaldo()));
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoActualizar.getText().isEmpty() || String.valueOf(this.campoTextoActualizar.getText().charAt(0)).equals(" ")) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Actualizar\" no puede estar vacio");
			return false;
		}//FIN IF
		this.cantidadRestante = Integer.valueOf(this.campoTextoPedido.getText()) - Integer.valueOf(this.campoTextoEnProduccion.getText());
		this.cantidadActualizar = Integer.valueOf(this.campoTextoActualizar.getText());
		if (this.cantidadActualizar <= 0) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cantidad a producir\" no puede estar en cero");
			return false;
		}else if (this.cantidadActualizar > this.cantidadRestante) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Cantidad a producir\" no puede ser mayor al pedido");
			return false;
		}//FIN IF/ELSE
		return true;
	}//FIN METODO
	
	private String generarNumeroSerie() {
		String result = new SecureRandom().ints(0,36)
	            .mapToObj(i -> Integer.toString(i, 36))
	            .map(String::toUpperCase).distinct().limit(8).collect(Collectors.joining());
		
		return result;
	}//FIN METODO
	
	private String generarLote() {
		int syspk = (OrdenProduccionDAO.ultimoSysPK(this.mainApp.getConnection()) + 1);
		Month mes = LocalDate.now().getMonth();
        String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        char[] l = nombre.toUpperCase().toCharArray();
        String m = String.valueOf(l[0])+String.valueOf(l[1])+String.valueOf(l[2]);
        String fechaSys = String.valueOf(LocalDate.now().getDayOfMonth()) + m + String.valueOf(LocalDate.now().getYear() + String.valueOf(syspk));
        
        return fechaSys;
	}//FIN METODO
	
	private void iniciarProduccion() {
		if (this.validarDatos()) {
			if (Notificacion.dialogoPreguntar("Confirmación para generar una orden de trabajo", "¿Desea generar una orden de trabajo?")){
				OrdenProduccion orden = new OrdenProduccion();
				orden.setLote(generarLote());
				orden.setCantidad(Integer.valueOf(this.campoTextoActualizar.getText()));
				orden.setDetalleOrdenCompraFK(detalleOrdenCompra.getSysPK());
				DetalleOrdenCompra dOrdenCompra = new DetalleOrdenCompra();
				dOrdenCompra.setSysPK(this.detalleOrdenCompra.getSysPK());
				dOrdenCompra.setSaldo(this.cantidadActualizar);
					
		    	if (OrdenProduccionDAO.createOrdenProduccion(this.conexion, orden) && DetalleOrdenCompraDAO.update(conexion, dOrdenCompra)) {
		    		for (int i = 0; i < detalleOrdenCompra.getPorEntregar(); i++) {
		    			int syspk = OrdenProduccionDAO.ultimoSysPK(this.conexion);
		    			DetalleOrdenProduccion detalleOrden = new DetalleOrdenProduccion();
		    			detalleOrden.setNumeroSerie(generarNumeroSerie());
		    			detalleOrden.setOrdenProduccionFK(syspk);
		    			DetalleOrdenProduccionDAO.createDetalleOrdenProduccion(this.conexion, detalleOrden);
		    		}//FIN FOR		            				
		    		Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Se generó su orden de producción correctamente");
		    		this.mainApp.getEscenarioDialogosAlternoSecundario().close();
		    	}//FIN IF
			}//FIN IF		
		}//FIN IF 
	}
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		iniciarProduccion();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlternoSecundario().close();
	}//FIN METODO
}//FIN CLASE

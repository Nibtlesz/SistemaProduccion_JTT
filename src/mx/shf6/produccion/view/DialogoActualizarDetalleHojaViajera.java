package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Timestamp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.DetalleHojaViajera;
import mx.shf6.produccion.model.dao.DetalleHojaViajeraDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;

public class DialogoActualizarDetalleHojaViajera {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private DetalleHojaViajera detalleHojaViajera;
	
	//VARIABLES
	private Integer opcion;
	private Integer cantidadActualizar;
	private Integer cantidadProcesos;
	
	//CONSTANTES
	public static final int LIBERAR = 1;
	public static final int DEVOLVER = 2;	
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoEnProceso;
	@FXML private TextField campoTextoTerminado;
	@FXML private TextField campoTextoActualizar;
	@FXML private Label etiquetaActualizar;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		RestriccionTextField.soloNumeros(campoTextoActualizar);
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, DetalleHojaViajera detalleHojaViajera, int cantidadProcesos, int opcion) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.detalleHojaViajera = detalleHojaViajera;
		this.opcion = opcion;
		this.cantidadProcesos = cantidadProcesos;
		this.inicializarComponentes();
		inicializarComponentes();
	}//FIN METODO
	
	//INICIALIZA COMPONENTE
	private void inicializarComponentes() {
		this.campoTextoEnProceso.setEditable(false);
		this.campoTextoEnProceso.setText(String.valueOf(this.detalleHojaViajera.getCantidadEnProceso()));
		this.campoTextoTerminado.setEditable(false);
		this.campoTextoTerminado.setText(String.valueOf(this.detalleHojaViajera.getCantidadTermiando()));
		if (this.opcion == LIBERAR)
			this.etiquetaActualizar.setText("Liberar");
		else if (this.opcion == DEVOLVER)
			this.etiquetaActualizar.setText("Devolver");
	}//FIN METODO
	
	//VALIDAR DATOS
	private boolean validarDatos() {
		if (this.campoTextoActualizar.getText().isEmpty() || String.valueOf(this.campoTextoActualizar.getText().charAt(0)).equals(" ")) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Actualizar\" no puede estar vacio");
			return false;
		}//FIN IF
		this.cantidadActualizar = Integer.valueOf(this.campoTextoActualizar.getText());
		return true;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonAceptar() {
		if (this.validarDatos()) {
			if (this.opcion == LIBERAR) {
				if ((Double.compare(this.cantidadActualizar, this.detalleHojaViajera.getCantidadEnProceso()) <= 0) && (Double.compare(this.cantidadActualizar, 0.0) > 0)) {
					
					this.detalleHojaViajera.setCantidadEnProceso(this.detalleHojaViajera.getCantidadEnProceso() - this.cantidadActualizar);
					this.detalleHojaViajera.setCantidadTerminado(this.detalleHojaViajera.getCantidadTermiando() + this.cantidadActualizar);
					if (Double.compare(this.detalleHojaViajera.getCantidadEnProceso(), 0.0) == 0)
						this.detalleHojaViajera.setFechaHoraFinal(new Timestamp(System.currentTimeMillis()));
					DetalleHojaViajeraDAO.updateDetalleHojaViajera(this.conexion, this.detalleHojaViajera);
					
					if (this.detalleHojaViajera.getDetalleProcesoOperacion() < this.cantidadProcesos) {
						DetalleHojaViajera detalleHojaViajeraSiguiente = DetalleHojaViajeraDAO.readHojaViajeraPorOrdenProduccionComponente(this.conexion, this.detalleHojaViajera.getHojaViajeraFK()).get(this.detalleHojaViajera.getDetalleProcesoOperacion());
						detalleHojaViajeraSiguiente.setCantidadEnProceso(detalleHojaViajeraSiguiente.getCantidadEnProceso() + this.cantidadActualizar);
						if (detalleHojaViajeraSiguiente.getFechaHoraInicio() == null)
							detalleHojaViajeraSiguiente.setFechaHoraInicio(new Timestamp(System.currentTimeMillis()));
						DetalleHojaViajeraDAO.updateDetalleHojaViajera(this.conexion, detalleHojaViajeraSiguiente);
					}//FIN FIN
					
					this.mainApp.getEscenarioDialogosAlternoSecundario().close();
				} else {
					Notificacion.dialogoAlerta(AlertType.WARNING, "", "La cantidad que deseas liberar es incorrecta");
				}//FIN IF/ELSE
			}//FIN IF
		}//FIN IF 
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlternoSecundario().close();
	}//FIN METODO
	
}//FIN CLASE

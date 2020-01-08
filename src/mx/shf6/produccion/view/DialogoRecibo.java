package mx.shf6.produccion.view;

import java.sql.Connection;
import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Cliente;
import mx.shf6.produccion.model.DocumentosCuentasXCobrar;
import mx.shf6.produccion.model.Recibo;
import mx.shf6.produccion.model.dao.DocumentosCuentasXCobrarDAO;
import mx.shf6.produccion.model.dao.FolioDAO;
import mx.shf6.produccion.model.dao.ReciboDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.RestriccionTextField;
import mx.shf6.produccion.utilities.TransaccionSQL;

public class DialogoRecibo {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private Cliente cliente;
	private Recibo recibo;

	//VARIABLES
	private DecimalFormat decimalFormat;


	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoReferencia;
	@FXML private TextField campoTextoCliente;
	@FXML private TextField campoTextoImporte;
	@FXML private TextArea areaTextoNotas;

	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		RestriccionTextField.limitarPuntoDecimal(this.campoTextoImporte);
	}//FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Cliente cliente) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.cliente = cliente;
		this.decimalFormat = new DecimalFormat("00000000");
		this.recibo = new Recibo();

		mostrarDatosInterfaz();
	}//FIN METODO

	private void mostrarDatosInterfaz() {
		this.campoTextoCliente.setDisable(true);
		this.campoTextoReferencia.setDisable(true);
		this.campoTextoCliente.setText(this.cliente.getNombre());
		this.campoTextoReferencia.setText("R" + decimalFormat.format((FolioDAO.readSeriePorFolio(conexion, "R") + 1)));
	}// FIN METODO

	private boolean validarCampos(){
		if(this.campoTextoImporte.getText().isEmpty()){
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Importe\" no puede estar vacio");
			return false;
		} else if (this.areaTextoNotas.getText().isEmpty()){
			Notificacion.dialogoAlerta(AlertType.ERROR, "", "El campo \"Notas\" no puede estar vacio");
			return false;
		}//FIN IF ELSE
	return true;
	}//FIN METODO

	private void generarRecibo(){
		if(validarCampos()){
			recibo.setClienteFK(cliente.getSysPK());
			recibo.setReferencia(this.campoTextoReferencia.getText());
			recibo.setNotas(this.areaTextoNotas.getText());
			recibo.setImporte(Double.parseDouble(this.campoTextoImporte.getText()));

			DocumentosCuentasXCobrar documentosCuentasXCobrar = new DocumentosCuentasXCobrar();
			documentosCuentasXCobrar.setDocumento(DocumentosCuentasXCobrar.RECIBO);
			documentosCuentasXCobrar.setReferencia(recibo.getReferencia());
			documentosCuentasXCobrar.setNotas(recibo.getNotas());
			documentosCuentasXCobrar.setHaber(recibo.getImporte());
			documentosCuentasXCobrar.setXAplicar(recibo.getImporte());
			documentosCuentasXCobrar.setClienteFK(cliente.getSysPK());

			TransaccionSQL.setStatusTransaccion(this.mainApp.getConnection(), TransaccionSQL.AUTOCOMMIT_OFF);
			if(ReciboDAO.create(conexion, recibo)){
				documentosCuentasXCobrar.setReciboFK(ReciboDAO.ultimoSysPk(conexion));
					if(DocumentosCuentasXCobrarDAO.create(mainApp.getConnection(), documentosCuentasXCobrar)){
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.COMMIT_TRANSACTION);
						Notificacion.dialogoAlerta(AlertType.INFORMATION,"", "¡El recibo se guardó de forma correcta!");
					}else{
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo guardar el recibo,  ¡revisa que la información sea correcta!");
					}//FIN IF ELSE DE CREAR DCXC
				this.mainApp.getEscenarioDialogosAlterno().close();
			}else{
				TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
				Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se pudo guardar el registro, ¡revisa que la información sea correcta!");
			}//FIN IF ELSE DE CREAR RECIBO
		}//FIN IF
	}//FIN METODO

	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		generarRecibo();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO
}//FIN CLASE

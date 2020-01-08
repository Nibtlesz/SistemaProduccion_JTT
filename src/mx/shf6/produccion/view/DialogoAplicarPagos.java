package mx.shf6.produccion.view;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.AplicarCuentasXCobrar;
import mx.shf6.produccion.model.DocumentosCuentasXCobrar;
import mx.shf6.produccion.model.dao.AplicarCuentasXCobrarDAO;
import mx.shf6.produccion.model.dao.DocumentosCuentasXCobrarDAO;
import mx.shf6.produccion.utilities.Notificacion;
import mx.shf6.produccion.utilities.PTableColumn;
import mx.shf6.produccion.utilities.TransaccionSQL;

public class DialogoAplicarPagos {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ArrayList<DocumentosCuentasXCobrar> listaDetalleRecibos;
	private DocumentosCuentasXCobrar documentosCuentasXCobrar;
	private ObservableList<DocumentosCuentasXCobrar> itemsTabla;
	private ArrayList<AplicarCuentasXCobrar> listaImportesAplicar;

	//VARIABLES
	Double sumaPagos = 0.0;
	boolean aplicarPagos = false;
	boolean transaccionAplc = false;
	boolean transaccionDcxc = false;
	Double xAplicar = 0.0;

	//COMPONENTES INTERFAZ
	@FXML private TableView<DocumentosCuentasXCobrar> tablaDetalleRecibos;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, String> columnaReferencia;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Date> columnaFecha;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaImporte;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, Double> columnaDisponible;
	@FXML private PTableColumn<DocumentosCuentasXCobrar, String> columnaImporteAplicar;
	@FXML private Label etiquetaSaldo;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
	}//FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, DocumentosCuentasXCobrar documentosCuentasXCobrar) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.documentosCuentasXCobrar = documentosCuentasXCobrar;
		this.listaDetalleRecibos = new ArrayList<DocumentosCuentasXCobrar>();
		this.itemsTabla = FXCollections.observableArrayList();
		this.listaImportesAplicar = new ArrayList<AplicarCuentasXCobrar>();

		this.inicializaComponentes();
		this.inicializaTabla();
		this.actualizarTabla();
	}//FIN METODO

	private void inicializaComponentes() {
		this.etiquetaSaldo.setText("Saldo "+Double.toString(this.documentosCuentasXCobrar.getSaldo()));
	}
	private void inicializaTabla() {
		this.columnaReferencia.setCellValueFactory(cellData -> cellData.getValue().referenciaProperty());
		this.columnaFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		this.columnaImporte.setCellValueFactory(cellData -> cellData.getValue().haberProperty());
		this.columnaDisponible.setCellValueFactory(cellData -> cellData.getValue().xAplicarProperty());
		this.columnaImporteAplicar.setCellValueFactory(cellData -> cellData.getValue().importeAplicarProperty());
		this.columnaImporteAplicar.setCellFactory(TextFieldTableCell.<DocumentosCuentasXCobrar>forTableColumn());

		this.columnaImporteAplicar.setOnEditCommit(data -> {
			if(data.getNewValue() != null && !data.getNewValue().equals("") && !data.getNewValue().isEmpty()){
				this.tablaDetalleRecibos.getSelectionModel().getSelectedItem().setImporteAplicar(data.getNewValue());
			} else {
				this.tablaDetalleRecibos.getSelectionModel().getSelectedItem().setImporteAplicar("0.0");
			}//FIN IF ELSE
		});
	}//FIN METODO

	private void actualizarTabla() {
		this.tablaDetalleRecibos.setItems(null);
		this.listaDetalleRecibos.clear();
		this.listaDetalleRecibos = DocumentosCuentasXCobrarDAO.readRecibosPorAplicarCliente(conexion, this.documentosCuentasXCobrar.getClienteFK());
		this.tablaDetalleRecibos.setItems(FXCollections.observableArrayList(listaDetalleRecibos));
	}//FIN METODO

	private void validarAbonos() {
		this.sumaPagos = 0.0;
		this.itemsTabla = this.tablaDetalleRecibos.getItems();
		for(DocumentosCuentasXCobrar documentosCuentasXCobrar : this.itemsTabla){
			if(!documentosCuentasXCobrar.getImporteAplicar().equals("0.0")){
				this.sumaPagos = this.sumaPagos + Double.parseDouble(documentosCuentasXCobrar.getImporteAplicar());
					if(this.sumaPagos <= this.documentosCuentasXCobrar.getSaldo()){

						if(documentosCuentasXCobrar.getXAplicar() >=  Double.parseDouble(documentosCuentasXCobrar.getImporteAplicar())){

							AplicarCuentasXCobrar aplicarCuentasXCobrar = new AplicarCuentasXCobrar();
							aplicarCuentasXCobrar.setDcxcFK(documentosCuentasXCobrar.getSysPK());
							aplicarCuentasXCobrar.setAplicadoA(this.documentosCuentasXCobrar.getSysPK());
							aplicarCuentasXCobrar.setImporte(Double.parseDouble(documentosCuentasXCobrar.getImporteAplicar()));

							this.listaImportesAplicar.add(aplicarCuentasXCobrar);
							this.aplicarPagos = true;
						} else {
							this.aplicarPagos = false;
							Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se puede aplicar el pago del recibo " + documentosCuentasXCobrar.getReferencia());
							break;
						}//FIN IF ELSE. VALIDA SI EL IMPORTE PARA APLICAR ES MENOR O IGUAL A DISPONIBLE
					}else{
						this.aplicarPagos = false;
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No se han podido aplicar los pagos. El total de los pagos es mayor al Saldo Actual");
					}//FIN IF ELSE. VALIDA SI EL SALDO ES MENOR O IGUAL A LA SUMA DE LOS PAGOS QUE SE QUIERE APLICAR
		    }//FIN IF. VALIDA SI LA CANTIDAD DE POR APLICAR ES DIFERENTE DE CERO
		}//FIN FOR
		if(this.sumaPagos == 0){
			Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "No ha ingresado pagos para Aplicar!");
		}//FIN IF
	}//FIN METODO

	private void generarTransacciones() {
		if(this.aplicarPagos){
				TransaccionSQL.setStatusTransaccion(this.mainApp.getConnection(), TransaccionSQL.AUTOCOMMIT_OFF);
				for(AplicarCuentasXCobrar aplicarCuentasXCobrar : this.listaImportesAplicar){
					if(AplicarCuentasXCobrarDAO.create(conexion, aplicarCuentasXCobrar)){
						transaccionAplc = true;
					}else {
						transaccionAplc = false;
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
						break;
					}//FIN IF ELSE
				}//FIN FOR

				for(DocumentosCuentasXCobrar documentosCuentasXCobrar : this.itemsTabla){

					documentosCuentasXCobrar.setXAplicar(documentosCuentasXCobrar.getXAplicar() -  Double.parseDouble(documentosCuentasXCobrar.getImporteAplicar()));
					if(DocumentosCuentasXCobrarDAO.updateXAplicar(conexion, documentosCuentasXCobrar)){
						transaccionDcxc = true;
					}else {
						transaccionDcxc = false;
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
						break;
					}//FIN IF ELSE
				}//FIN FOR

				if(transaccionAplc && transaccionDcxc){
					this.documentosCuentasXCobrar.setPagos(this.documentosCuentasXCobrar.getPagos() + sumaPagos);
					if(DocumentosCuentasXCobrarDAO.updatePagos(conexion, this.documentosCuentasXCobrar)){
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.COMMIT_TRANSACTION);
						Notificacion.dialogoAlerta(AlertType.INFORMATION,"", "¡Los pagos han sido aplicados!");
						this.etiquetaSaldo.setText("Saldo "+ (Double.toString(this.documentosCuentasXCobrar.getSaldo() - this.sumaPagos)));
						this.mainApp.getEscenarioDialogosAlterno().close();
					}else{
						TransaccionSQL.setStatusTransaccion(this.conexion, TransaccionSQL.ROLLBACK_TRANSACTION);
						Notificacion.dialogoAlerta(AlertType.INFORMATION, "", "Los pagos no pudieron ser aplicados");
					}//FIN IF ELSE
				}//FIN IF
		}//FIN IF
	}//FIN METODO

	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		this.validarAbonos();
		this.generarTransacciones();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogosAlterno().close();
	}//FIN METODO

}

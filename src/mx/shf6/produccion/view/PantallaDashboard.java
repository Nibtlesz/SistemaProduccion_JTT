package mx.shf6.produccion.view;

/*import java.sql.Connection;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.application.Platform;
//import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.ControlOperacion;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleOrdenProduccion;
import mx.shf6.produccion.model.OrdenProduccion;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.ControlOperacionesDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleOrdenProduccionDAO;
import mx.shf6.produccion.model.dao.OrdenProduccionDAO;
import mx.shf6.produccion.utilities.AutoCompleteComboBoxListener;
import mx.shf6.produccion.utilities.Notificacion;

public class PantallaDashboard extends Thread{
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<OrdenProduccion> listaLotes;
	private ArrayList<DetalleOrdenProduccion> listaSeries;
	private ArrayList<String> listaComboLotes;
	private ArrayList<String> listaComboLotes2;
	private ArrayList<String> listaStatusLote;
	private ArrayList<String> listaStatusSerie;
	private StackedBarChart<Number, String> graficaPorSeries;
	private CategoryAxis xAxisSerie;
	private NumberAxis yAxisSerie;
	private TreeItem<ControlOperacion> root;
	private OrdenProduccion raiz;
	private ControlOperacion detalleRaiz;
	@SuppressWarnings("unused")
	private Componente componente;
	private ArrayList<ControlOperacion> listaComponentes;
	
	//VARIABLES
	int i = 0;
	
	//CONSTANTES
	
	//COMPONENTES INTERFAZ
	@FXML private ComboBox<String> comboLotes;
	@FXML private ComboBox<String> comboLotes2;
	@FXML private BarChart<String, Integer> graficaPorLotes;
	@FXML private HBox series;
	@FXML private CategoryAxis xAxisLote;
	@FXML private NumberAxis yAxisLote;
	@FXML private TreeTableView<ControlOperacion> ttv = new TreeTableView<ControlOperacion>();
	@FXML private TreeTableColumn <ControlOperacion, String> columnaComponente;
	@FXML private TreeTableColumn <ControlOperacion, Integer> columnaNivel;
	@FXML private TreeTableColumn <ControlOperacion, String> columnaStatus;
	
	//METODOS
	@FXML private void initialize() {
		this.raiz = new OrdenProduccion();
		this.detalleRaiz = new ControlOperacion();
		this.root = new TreeItem<ControlOperacion>();
		this.listaLotes = new ArrayList<OrdenProduccion>();
		this.listaSeries = new ArrayList<DetalleOrdenProduccion>();
		this.listaComboLotes = new ArrayList<String>();
		this.listaComboLotes2 = new ArrayList<String>();
		this.listaStatusLote = new ArrayList<String>();
		this.listaStatusSerie = new ArrayList<String>();
		this.componente = new Componente();
		this.listaComponentes = new ArrayList<ControlOperacion>();
		this.inicializarComponentes();	
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
	}//FIN METODO
	
	private void inicializarComponentes() {
		
		//GRAFICA POR LOTES
		this.listaStatusLote.add("Pendiente");
		this.listaStatusLote.add("En Proceso");
		this.listaStatusLote.add("Paro");
		this.xAxisLote.setCategories(FXCollections.observableArrayList(this.listaStatusLote));
		this.xAxisLote.setLabel("Lotes");
		this.yAxisLote.setLabel("Cantidad");
		
		//GRAFICA POR SERIES 
		this.listaStatusSerie.add("PE");
		this.listaStatusSerie.add("PR");
		this.listaStatusSerie.add("PA");
		this.listaStatusSerie.add("TE");
		this.xAxisSerie = new CategoryAxis();
		this.xAxisSerie.setCategories(FXCollections.observableArrayList(this.listaStatusSerie));
		this.yAxisSerie = new NumberAxis();
		this.yAxisSerie.setAutoRanging(false);
		this.yAxisSerie.setTickUnit(25);
		this.graficaPorSeries = new StackedBarChart<Number, String>(yAxisSerie, xAxisSerie);
		this.graficaPorSeries.setLegendVisible(false);
		this.graficaPorSeries.setAnimated(false);
			
	}//FIN METODO
	
	@Override
	public void run() {
		try {			
			while(true) {
				Platform.runLater(() -> {
					inicializarCombo();
					graficaPorLotes();
					graficaPorSeries();
					//obtenerArbol(this.comboLotes2.getValue());
				});//FIN SENTENCIA
				Thread.sleep(600000);
			}//FIN WHILE
		}catch(InterruptedException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
	}//FIN METODO
		
	private void inicializarCombo() {
		//COMBO LOTES 1
		this.listaComboLotes.clear();
		this.listaComboLotes2.clear();
		this.listaComboLotes.add("Todos");
		for (OrdenProduccion orden : OrdenProduccionDAO.readLoteProduccion(this.connection)){
			this.listaComboLotes.add(orden.getLote());
			this.listaComboLotes2.add(orden.getLote());
		}			
		this.comboLotes.setItems(FXCollections.observableArrayList(this.listaComboLotes));
		new AutoCompleteComboBoxListener(this.comboLotes);	
		
		//COMBO LOTES 2
		this.comboLotes2.setItems(FXCollections.observableArrayList(this.listaComboLotes2));
		new AutoCompleteComboBoxListener(this.comboLotes2);
		this.comboLotes2.valueProperty().addListener((ov, oldValue, newValue) -> {	
			obtenerArbol(newValue);
		});
	}//FIN METODO
	
	private void graficaPorLotes() {
		this.listaLotes.clear();
		this.listaLotes = OrdenProduccionDAO.readLoteProduccion(connection);
		int[] contadorStatus = new int[3]; 
		for (OrdenProduccion orden : this.listaLotes) {
			int status = orden.getStatus();
			contadorStatus[status]++;
		}//FIN FOR
		
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		this.graficaPorLotes.getData().clear();
		for (int i = 0; i < contadorStatus.length; i++) {
			series.getData().add(new XYChart.Data<>(this.listaStatusLote.get(i), contadorStatus[i]));
		}//FIN FOR	
		this.graficaPorLotes.getData().add(series);
	}//FIN METODO
	
	@SuppressWarnings("unchecked")
	private void graficaPorSeries() {
		int sysPKLote = 0;
		int cantidadTotal = 1;
		this.listaSeries.clear();
		this.graficaPorSeries.getData().clear();
		this.series.getChildren().clear();
		
		if(this.comboLotes.getSelectionModel().isEmpty() || this.comboLotes.getValue().equals("Todos"))
			this.listaSeries = DetalleOrdenProduccionDAO.readDetalleLoteProduccion(this.connection, -1);
		else {
			sysPKLote = OrdenProduccionDAO.sysPKOrdenProduccion(this.connection, this.comboLotes.getValue());
			this.listaSeries = DetalleOrdenProduccionDAO.readDetalleLoteProduccion(this.connection, sysPKLote);
		}//FIN IF-ELSE
		
		if (this.listaSeries.size() != 0) {
			cantidadTotal = this.listaSeries.size();
		}//FIN METODO
		
		int[] contadorStatus = new int[4];
		for (DetalleOrdenProduccion detalle: this.listaSeries) {
			int series = detalle.getStatus();
			contadorStatus[series]++;
		}//FIN FOR
		
		XYChart.Series<Number, String> serie1 = new XYChart.Series<Number, String>();
		XYChart.Series<Number, String> serie2 = new XYChart.Series<Number, String>();
		
		for (int i = 0; i < contadorStatus.length; i++) {
			serie1.getData().add(new XYChart.Data<Number, String>((contadorStatus[i] *100)/ cantidadTotal, this.listaStatusSerie.get(i)));
			serie2.getData().add(new XYChart.Data<>( 100-((contadorStatus[i] *100)/ cantidadTotal), this.listaStatusSerie.get(i)));
		}//FIN FOR
		this.graficaPorSeries.getData().addAll(serie1, serie2);
		this.series.getChildren().add(this.graficaPorSeries);
	}//FIN METODO
	
	private void inicializarTabla(int niveles) {
		this.columnaComponente.setCellValueFactory(new TreeItemPropertyValueFactory<ControlOperacion, String>("numeroSerie"));
		this.columnaStatus.setCellValueFactory(new TreeItemPropertyValueFactory<ControlOperacion, String>("detalleStatus"));
		
		for (int i = 1; i <= niveles; i++) {
			this.columnaNivel = new TreeTableColumn<>("Nivel " + i);
			this.columnaNivel.setCellValueFactory(new TreeItemPropertyValueFactory<ControlOperacion, Integer>("niveles"));
			this.columnaNivel.setCellFactory(new Callback<TreeTableColumn<ControlOperacion, Integer>, TreeTableCell<ControlOperacion, Integer>>(){
				public TreeTableCell<ControlOperacion, Integer> call(final TreeTableColumn<ControlOperacion, Integer> p){
					return new TreeTableCell<ControlOperacion, Integer>(){
						protected void updateItem(Integer item, boolean empty){
							super.updateItem(item, empty);
							if(empty)
								setText(null);
							else
								setText(p.getCellObservableValue(niveles).toString());
						}//FIN METODO
					};//FIN RETURN
				}//FIN METODO
			});//FIN METODO
		}//FIN FOR
	}//FIN METODO
	
	private void obtenerArbol(String lote) {	
		
		root.getChildren().clear();
		
		raiz = OrdenProduccionDAO.loteOrdenProduccion(this.connection, lote);
		detalleRaiz.setNumeroSerie(raiz.getLote());
		detalleRaiz.setStatus(raiz.getStatus());
		detalleRaiz.detalleStatusProperty();
		root.setValue(detalleRaiz);
		ttv.setRoot(root);
	
		
		ArrayList<ControlOperacion> listaControl  = new ArrayList<ControlOperacion>();
		listaControl = ControlOperacionesDAO.readLote(connection, lote);
		for(ControlOperacion detalleControl : listaControl){
			TreeItem<ControlOperacion> nodo = new TreeItem<>(detalleControl);
			root.getChildren().add(nodo);
			obtenerEstructura(lote, nodo);
		}//FIN FOR
		
		
	}//FIN METODO
	
	private void obtenerEstructura(String lote, TreeItem<ControlOperacion> treeItem){
		int nivel = 0;
		this.listaComponentes.clear();
		this.listaComponentes = ControlOperacionesDAO.readControlLote(connection, lote);
		for(ControlOperacion operacion : this.listaComponentes) {
			TreeItem<ControlOperacion> nodo = new TreeItem<>(operacion);
			if (operacion.getNivel() > nivel) 
				nivel = operacion.getNivel();
			treeItem.getChildren().add(nodo);
		}//FIN FOR
		inicializarTabla(nivel);
	}//FIN METODO
	//MANEJADORES 
}//FIN METODO*/

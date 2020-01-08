package mx.shf6.produccion.view;


import java.sql.Connection;
import java.util.ArrayList;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import mx.shf6.produccion.MainApp;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.Proyecto;
import mx.shf6.produccion.model.TipoComponente;
import mx.shf6.produccion.model.dao.ComponenteDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;

public class DialogoEstructuraNiveles {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	@SuppressWarnings("unused")
	private Proyecto proyecto;
	private Componente componenteRaiz;
	private DetalleComponente detalleComponenteRaiz;


	//VARIABLES
	int i = 0;

	//CONSTANTES


	//COMPONENTES INTERFAZ
	@FXML private TreeItem<DetalleComponente> root = new TreeItem<>();
	@FXML private TreeView<String> treeView = new TreeView<>();

	@FXML private TreeTableView<DetalleComponente> ttv = new TreeTableView<DetalleComponente>();
	@FXML private TreeTableColumn <DetalleComponente, String> columnaComponente;
	@FXML private TreeTableColumn <DetalleComponente, Integer> columnaNivel;
	@FXML private TreeTableColumn <DetalleComponente, Double> columnaCantidad;
	@FXML private TreeTableColumn <DetalleComponente, String> columnaAcciones;
	@FXML private Label etiquetaComponente;


	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {

	}// FIN METODO


	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp, Proyecto proyecto) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.proyecto = proyecto;
		this.componenteRaiz = new Componente();
		this.detalleComponenteRaiz = new DetalleComponente();

		 inicializarTabla();
		 obtenerArbol(proyecto.getComponenteFK(), root);
	}//FIN METODO

	private void inicializarTabla(){
		this.columnaComponente.setCellValueFactory(new TreeItemPropertyValueFactory<DetalleComponente, String>("numeroDescripcionComponenteIferior"));
		this.columnaCantidad.setCellValueFactory(new TreeItemPropertyValueFactory<DetalleComponente, Double>("cantidad"));

		this.columnaNivel.setCellFactory(new Callback<TreeTableColumn<DetalleComponente, Integer>, TreeTableCell<DetalleComponente, Integer>>(){
			public TreeTableCell<DetalleComponente, Integer> call(final TreeTableColumn<DetalleComponente, Integer> p){
				return new TreeTableCell<DetalleComponente, Integer>(){
					protected void updateItem(Integer item, boolean empty){
						super.updateItem(item, empty);
						if(empty)
							setText(null);
						else
							setText(Integer.toString(ttv.getTreeItemLevel(ttv.getTreeItem(getIndex())) + 1));
					}//FIN METODO
				};//FIN RETURN
			}//FIN METODO
		});//FIN METODO
	}//FIN METODO

	private void obtenerArbol(int componenteFK, TreeItem<DetalleComponente> treeItem){
		i++;
		componenteRaiz = ComponenteDAO.readComponente(conexion, componenteFK);

		TreeItem<DetalleComponente> root = new TreeItem<>();
		if(i == 1){
			detalleComponenteRaiz.setComponenteInferiorFK(componenteRaiz.getSysPK());
			detalleComponenteRaiz.setNumeroParteComponenteInferior(componenteRaiz.getNumeroParte());
			detalleComponenteRaiz.setDescripcionComponenteInferior(componenteRaiz.getDescripcion());
			detalleComponenteRaiz.setNumeroDescripcionComponenteIferior();
			this.etiquetaComponente.setText(detalleComponenteRaiz.getNumeroDescripcionComponenteIferior());
			root.setValue(detalleComponenteRaiz);
			ttv.setRoot(root);
		}//FIN IF

		ArrayList<DetalleComponente> listaDetalleComponente = new ArrayList<DetalleComponente>();
		if(componenteRaiz.getTipoComponente().equals(TipoComponente.ENSAMBLE) || componenteRaiz.getTipoComponente().equals(TipoComponente.SUB_ENSAMBLE)){
			listaDetalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(conexion, componenteFK);
			if(i==1){
				for(DetalleComponente detalleComponente : listaDetalleComponente){
					TreeItem<DetalleComponente> nodo = new TreeItem<>(detalleComponente);
					root.getChildren().add(nodo);
					obtenerArbol(detalleComponente.getComponenteInferiorFK(), nodo);
				}//FIN FOR
			}else{
			for(DetalleComponente detalleComponente : listaDetalleComponente){
				TreeItem<DetalleComponente> nodo = new TreeItem<>(detalleComponente);
				treeItem.getChildren().add(nodo);
				obtenerArbol(detalleComponente.getComponenteInferiorFK(), nodo);
				}//FIN FOR
			}//FIN IF ELSE
		}//FIN IF
	}//FIN METODO

	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonAceptar() {

	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDialogos().close();
	}//FIN METODO

}

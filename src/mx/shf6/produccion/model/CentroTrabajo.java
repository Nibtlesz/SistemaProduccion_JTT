package mx.shf6.produccion.model;





import java.sql.Connection;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.GrupoTrabajoDAO;

	public class CentroTrabajo {
	
	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty codigo;
	private StringProperty descripcion;
	private ObjectProperty<Integer> grupoTrabajoFK;
	private StringProperty codigoGrupoTrabajo;
	
	//VARIABLES
	
	//CONSTRUCTORES
	public CentroTrabajo() {
		this(0, "", "" ,0, "");	
	}//FIN CONSTRUCTOR
	
	public CentroTrabajo(Integer sysPK, String codigo, String descripcion, Integer grupoTrabajoFK, String codigoGrupoTrabajo) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleStringProperty(codigo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.grupoTrabajoFK = new SimpleObjectProperty<Integer>(grupoTrabajoFK);
		this.codigoGrupoTrabajo = new SimpleStringProperty(codigoGrupoTrabajo);
	}//FIN CONSTRUCTOR
	
	//METODO SET SYSPK
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);	
	}//FIN METODO
	
	//METODOS ACCEDER AL SYS_PK
	public int getSysPK() {
		return sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return sysPK;
	}//FIN METODO
	//FIN METODOS SYS_PK
	
	//METODO SET CODIGO
	public void setCodigo(String codigo) {
		this.codigo.set(codigo);	
	}//FIN METODO
	
	//METODO GET CODIGO
	public String getCodigo() {
		return codigo.get();
	}//FIN METODO
	
	public StringProperty codigoProperty() {
		return codigo;
	}//FIN METODO
	//FIN METODOS CODIGO
	
	//METODO SET DESCRIPCION
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);	
	}//FIN METODO
	
	//METODO ACCEDER A LA DESCRIPCION
	public String getDescripcion() {
		return descripcion.get();
	}//FIN METODO
	
	public StringProperty descripcionProperty() {
		return descripcion;
	}//FIN METODO
	//FIN METODOS DESCRIPCION
	
	//METODO SET GRUPOTRABAJO
	public void setgrupoTrabajoFK(Integer grupoTrabajoFK){
		this.grupoTrabajoFK.set(grupoTrabajoFK);	
	}//FIN METODO
	
	//METODO ACCDEDER AL GRUPO_TRABAJO
	public int getGrupoTrabajoFK() {
		return grupoTrabajoFK.get();
	}//FIN METODO
	
	//METODO OBJECT GRUPO TRABAJO
	public ObjectProperty<Integer> grupoTrabajoFK() {
		return grupoTrabajoFK;
	}//FIN METODO
	
	
	//OBTENER CODIGO GRUPO TRABAJO
	
	//METODO SET CODIGO GRUPO
	public void setCodigoGrupoTrabajo(String codigoGrupoTrabajo) {
		this.codigoGrupoTrabajo.set(codigoGrupoTrabajo);
	}// FIN METODO
	
	//METODO GET CODIGO GRUPO TRABAJO
	public String getCodigoGrupoTrabajo() {
		return codigoGrupoTrabajo.get();
	}//FIN METODO

	public StringProperty codigoGrupoTrabajo(){
		return this.codigoGrupoTrabajo;
	}

	public GrupoTrabajo getGrupoTrabajoFK(Connection connection) {
		return GrupoTrabajoDAO.readGrupoTrabajo(connection, getGrupoTrabajoFK());
	}
	//FIN METODOS GRUPO TRABAJO
}//FIN CLASE

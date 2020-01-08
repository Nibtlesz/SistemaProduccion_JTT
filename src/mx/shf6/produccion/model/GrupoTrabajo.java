package mx.shf6.produccion.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

	public class GrupoTrabajo {
		
		//PROPIEDADES
		private ObjectProperty<Integer> sysPK;
		private StringProperty codigo;
		private StringProperty descripcion;
		private ObjectProperty<Integer> grupoTrabajoFK;
		
		//VARIABLES
		
		//CONTRUCTORES
		public GrupoTrabajo() {
			this(0, "", "", 0);

		}//FIN CONSTRUCTOR
		public GrupoTrabajo(Integer sysPK, String codigo, String descripcion,Integer grupoTrabajoFK) {
			this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
			this.codigo = new SimpleStringProperty(codigo);
			this.descripcion = new SimpleStringProperty(descripcion);
			this.grupoTrabajoFK = new SimpleObjectProperty<Integer>(grupoTrabajoFK);
		}//FIN CONSTRUCTOR
		
		
		//METODOS
		//METODO SET SYSPK
		public void setSysPK(Integer sysPK) {
			this.sysPK.set(sysPK);	
		}//FIN METODO
		
		//METODO GET SYSPK
		public int getSysPK() {
			return sysPK.get();
		}//FIN METODO
		
		//METODO PROPERTY SYSPK
		public ObjectProperty<Integer> sysPKProperty() {
			return sysPK;
		}//FIN METODO
		
		//METODO SET CONDIGO
		public void setCodigo(String codigo) {
			this.codigo.set(codigo);	
		}//FIN METODO
		
		//METODO GET CODIGO
		public String getCodigo() {
			return codigo.get();
		}//FIN METODO
		
		//METODO PROPERTY CODIGO
		public StringProperty codigoProperty() {
			return codigo;
		}//FIN METODO
		
		//METODO SET DESCRIPCION
		public void setDescripcion(String descripcion) {
			this.descripcion.set(descripcion);	
		}//FIN METODO
		
		//METODO GET DESRIPCION
		public String getDescripcion() {
			return descripcion.get();
		}//FIN METODO
		
		//METODO PROPERTY DESCRIPCION
		public StringProperty descripcionProperty() {
			return descripcion;
		}//FIN METODO
		
		public void setGrupoTrabajoFK(Integer grupoTrabajoFK) {
			this.grupoTrabajoFK.set(grupoTrabajoFK);	
		}//FIN METODO
		
		public int getGrupoTrabajoFK() {
			return grupoTrabajoFK.get();
		}//FIN METODO
		
		public ObjectProperty<Integer> grupoTrabajoFK(){
			return grupoTrabajoFK;
		}//FIN METODO
		
		
		
	}//FIN CLASE



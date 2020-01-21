package mx.shf6.produccion.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.AcabadoDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.model.dao.TipoMiscelaneoDAO;
import mx.shf6.produccion.model.dao.TratamientoDAO;
import mx.shf6.produccion.utilities.Dimensiones;

public class DetalleComponente {

	private ObjectProperty<Integer> sysPK;
	private ObjectProperty<Componente> codigo;
	private ObjectProperty<Dimensiones> dimensiones;
	private StringProperty gradoMaterial;
	private ObjectProperty<Material> materialFK;
	private ObjectProperty<Integer> tipoMiscelaneoFK;
	private ObjectProperty<Integer> tipoMateriaPrimaFK;
	private ObjectProperty<Integer> acabadoFK;
	private ObjectProperty<Integer> tratamientoFK;
	private StringProperty notas;
	private StringProperty status;
	private StringProperty revision;
	
	private ObjectProperty<Integer> cantidad;

	public DetalleComponente() {
		this(0, new Componente(), new Dimensiones(), "", new Material(), 0, 0, 0, 0, "", "", "",0);
	}//FIN CONSTRUCTOR

	public DetalleComponente(int sysPK, Componente codigo, Dimensiones dimensiones, String gradoMaterial, Material materialFK, int tipoMiscelaneoFK, int tipoMateriaPrimaFK, int acabadoFK, int tratamientoFK, String notas, String status, String revision, int cantidad) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.codigo = new SimpleObjectProperty<Componente>(codigo);
		this.dimensiones = new SimpleObjectProperty<Dimensiones>(dimensiones);
		this.gradoMaterial = new SimpleStringProperty(gradoMaterial);
		this.materialFK = new SimpleObjectProperty<Material>(materialFK);
		this.tipoMiscelaneoFK = new SimpleObjectProperty<Integer>(tipoMiscelaneoFK);
		this.tipoMateriaPrimaFK = new SimpleObjectProperty<Integer>(tipoMateriaPrimaFK);
		this.acabadoFK = new SimpleObjectProperty<Integer>(acabadoFK);
		this.tratamientoFK = new SimpleObjectProperty<Integer>(tratamientoFK);
		this.notas = new SimpleStringProperty(notas);
		this.status = new SimpleStringProperty(status);
		this.revision = new SimpleStringProperty(revision);
		this.cantidad = new SimpleObjectProperty<Integer>(cantidad);
	}//FIN CONSTRUCTOR

	public void setSysPK(int sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public int getSysPK() {
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO

	public void setCodigo(Componente codigo) {
		this.codigo.set(codigo);
	}//FIN METODO

	public Componente getCodigo() {
		return this.codigo.get();
	}//FIN METODO

	public ObjectProperty<Componente> codigoProperty() {
		return this.codigo;
	}//FIN METODO

	public void setGradoMaterial(String gradoMaterial) {
		this.gradoMaterial.set(gradoMaterial);
	}//FIN METODO

	public String getGradoMaterial() {
		return this.gradoMaterial.get();
	}//FIN METODO

	public StringProperty gradoMaterialProperty() {
		return this.gradoMaterial;
	}//FIN METODO

	public void setDimensiones(Dimensiones dimensiones) {
		this.dimensiones.set(dimensiones);
	}//FIN METODO

	public Dimensiones getDimensiones() {
		return this.dimensiones.get();
	}//FIN METODO

	public ObjectProperty<Dimensiones> dimesionesProperty() {
		return this.dimensiones;
	}//FIN METODO

	public void setMaterialFK(Material materialFK) {
		this.materialFK.set(materialFK);
	}//FIN METODO

	public Material getMaterialFK() {
		return this.materialFK.get();
	}//FIN METODO

	public ObjectProperty<Material> materialFKProperty() {
		return this.materialFK;
	}//FIN METODO

	public Material getMaterial(Connection connection) {
		return MaterialDAO.readMaterial(connection, this.getMaterialFK().getSysPK());
	}//FIN METODO

	public void setTipoMiscelaneoFK(int tipoMiscelaneoFK) {
		this.tipoMiscelaneoFK.set(tipoMiscelaneoFK);
	}//FIN METODO

	public int getTipoMiscelaneoFK() {
		return this.tipoMiscelaneoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> tipoMiscelaneoFKProperty() {
		return this.tipoMiscelaneoFK;
	}//FIN METODO

	public TipoMiscelaneo getTipoMiscelaneo(Connection connection) {
		return TipoMiscelaneoDAO.readTipoMiscelaneo(connection, this.getTipoMiscelaneoFK());
	}//FIN METODO

	public void setTipoMateriaPrimaFK(int tipoMateriaPrimaFK) {
		this.tipoMateriaPrimaFK.set(tipoMateriaPrimaFK);
	}//FIN METODO

	public int getTipoMateriaPrimaFK() {
		return this.tipoMateriaPrimaFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> tipoMateriaPrimaFKProperty() {
		return this.tipoMateriaPrimaFK;
	}//FIN METODO

	public TipoMateriaPrima getTipoMateriaPrima(Connection connection) {
		return TipoMateriaPrimaDAO.readTipoMateriaPrima(connection, getTipoMateriaPrimaFK());
	}//FIN METODO

	public void setAcabadoFK(int acabadoFK) {
		this.acabadoFK.set(acabadoFK);
	}//FIN METODO

	public int getAcabadoFK() {
		return this.acabadoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> acabadoFKProperty() {
		return this.acabadoFK;
	}//FIN METODO

	public Acabado getAcabado(Connection connection) {
		return AcabadoDAO.readAcabado(connection, this.getAcabadoFK());
	}//FIN METODO

	public void setTratamientoFK(int tratamientoFK) {
		this.tratamientoFK.set(tratamientoFK);
	}//FIN METODO

	public int getTratamientoFK() {
		return this.tratamientoFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> tratamientoFKProperty() {
		return this.tratamientoFK;
	}//FIN METODO

	public Tratamiento getTratamiento(Connection connection) {
		return TratamientoDAO.readTratamiento(connection, this.getTratamientoFK());
	}//FIN METODO

	public void setNotas(String notas) {
		this.notas.set(notas);
	}//FIN METODO

	public String getNotas() {
		return this.notas.get();
	}//FIN METODO

	public StringProperty notasProperty() {
		return this.notas;
	}//FIN METODO

	public void setStatus(int status) {
		this.status.set(Status.toString(status));
	}//FIN METODO

	public String getStatus() {
		return this.status.get();
	}//FIN METODO

	public StringProperty statusProperty() {
		return this.status;
	}//FIN METODO

	public int getStatusFK() {
		return Status.toInt(this.getStatus());
	}//FIN METODO
	
	//METODOS PARA ACCEDER A REVISION
	public void setRevision(String revision) {
		this.revision.set(revision);
	}//FIN METODO
	
	public String getRevision() {
		return this.revision.get();
	}//FIN METODO
	
	public StringProperty revisionProperty() {
		return this.revision;
	}//FIN METODO

	public void setCantidad(int cantidad) {
		this.cantidad.set(cantidad);
	}//FIN METODO

	public int getCantidad() {
		return this.cantidad.get();
	}//FIN METODO

	public ObjectProperty<Integer> cantidadProperty() {
		return this.cantidad;
	}//FIN METODO
}//FIN CLASE

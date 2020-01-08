package mx.shf6.produccion.model;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.produccion.model.dao.AcabadoDAO;
import mx.shf6.produccion.model.dao.ClienteDAO;
import mx.shf6.produccion.model.dao.DetalleComponenteDAO;
import mx.shf6.produccion.model.dao.MaterialDAO;
import mx.shf6.produccion.model.dao.TipoMateriaPrimaDAO;
import mx.shf6.produccion.model.dao.TipoMiscelaneoDAO;
import mx.shf6.produccion.model.dao.TratamientoDAO;
import mx.shf6.produccion.utilities.Dimensiones;

public class Componente {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty numeroParte;
	private StringProperty descripcion;
	private ObjectProperty<Dimensiones> dimensiones;
	private StringProperty gradoMaterial;
	private StringProperty tipoComponente;
	private ObjectProperty<Double> costo;
	private ObjectProperty<Double> costoDirecto;
	private ObjectProperty<Double> costoIndirecto;
	private ObjectProperty<Integer> materialFK;
	private ObjectProperty<Integer> tipoMiscelaneoFK;
	private ObjectProperty<Integer> tipoMateriaPrimaFK;
	private ObjectProperty<Integer> acabadoFK;
	private ObjectProperty<Integer> tratamientoFK;
	private StringProperty notas;
	private StringProperty status;
	private ObjectProperty<Integer> consecutivo;
	private ObjectProperty<Integer> clienteFK;
	private StringProperty materialDescripcion;
	private ObjectProperty<Integer> esInterno;
	private StringProperty hilos;
	private StringProperty revision;

	//CONSTRUCTOR VACIO
	public Componente() {
		this(0, "", "", new Dimensiones(), "", "", 0.0, 0.0, 0.0, 0, 0, 0, 0, 0, "", "", 0, 0, "", 0, "", "");
	}//FIN CONSTRUCTOR

	public Componente(int sysPK, String numeroParte, String descripcion, Dimensiones dimensiones, String gradoMaterial, String tipoComponente, Double costo, Double costoDirecto, Double costoIndirecto, int materialFK, int tipoMiscelaneoFK,
			int tipoMateriaPrimaFK, int acabadoFK, int tratamientoFK, String notas, String status, int consecutivo, int clienteFK, String materialDescripcion, int esInterno, String hilos, String revision) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.numeroParte = new SimpleStringProperty(numeroParte);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.dimensiones = new SimpleObjectProperty<Dimensiones>(dimensiones);
		this.gradoMaterial = new SimpleStringProperty(gradoMaterial);
		this.tipoComponente = new SimpleStringProperty(tipoComponente);
		this.costo = new SimpleObjectProperty<Double>(costo);
		this.costoDirecto = new SimpleObjectProperty<Double>(costoDirecto);
		this.costoIndirecto = new SimpleObjectProperty<Double>(costoIndirecto);
		this.materialFK = new SimpleObjectProperty<Integer>(materialFK);
		this.tipoMiscelaneoFK = new SimpleObjectProperty<Integer>(tipoMiscelaneoFK);
		this.tipoMateriaPrimaFK = new SimpleObjectProperty<Integer>(tipoMateriaPrimaFK);
		this.acabadoFK = new SimpleObjectProperty<Integer>(acabadoFK);
		this.tratamientoFK = new SimpleObjectProperty<Integer>(tratamientoFK);
		this.notas = new SimpleStringProperty(notas);
		this.status = new SimpleStringProperty(status);
		this.consecutivo = new SimpleObjectProperty<Integer>(consecutivo);
		this.clienteFK = new SimpleObjectProperty<Integer>(clienteFK);
		this.materialDescripcion = new SimpleStringProperty(materialDescripcion);
		this.esInterno = new SimpleObjectProperty<Integer>(esInterno);
		this.hilos = new SimpleStringProperty(hilos);
		this.revision = new SimpleStringProperty(revision);
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

	public void setNumeroParte(String numeroParte) {
		this.numeroParte.set(numeroParte);
	}//FIN METODO

	public String getNumeroParte() {
		return this.numeroParte.get();
	}//FIN METODO

	public String doNumeroParte(Connection connection) {
		DecimalFormat decimalFormat = new DecimalFormat("000");
		if (this.getTipoComponente() == TipoComponente.SUB_ENSAMBLE || this.getTipoComponente() == TipoComponente.PARTE_PRIMARIA)
			 this.numeroParte.set(this.getCliente(connection).getCodigo().substring(5, 7) + this.getMaterial(connection).getCodigo() + decimalFormat.format(this.getConsecutivo()) + this.getTipoComponenteChar());
		else if (this.getTipoComponente() == TipoComponente.MATERIA_PRIMA)
			this.numeroParte.set(this.getTipoMateriaPrima(connection).getCodigo() + this.getMaterial(connection).getCodigo() + this.getAcabado(connection).getCodigo() + decimalFormat.format(this.getConsecutivo()) +  this.getTipoComponenteChar());
		else if (this.getTipoComponente() == TipoComponente.COMPRADO)
			this.numeroParte.set(this.getTipoMiscelaneo(connection).getCodigo() + this.getMaterial(connection).getCodigo() + this.getTratamiento(connection).getCodigo() + decimalFormat.format(this.getConsecutivo()) +  this.getTipoComponenteChar());
		return this.numeroParte.get();
	}//FIN METODO

	public StringProperty numeroParteProperty() {
		return this.numeroParte;
	}//FIN METODO

	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}//FIN METODO

	public String getDescripcion() {
		return this.descripcion.get();
	}//FIN METODO

	public StringProperty descripcionProperty() {
		return this.descripcion;
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

	public void setTipoComponente(String charComponente) {
		this.tipoComponente.set(TipoComponente.toString(charComponente));
	}//FIN METODO

	public String getTipoComponente() {
		return this.tipoComponente.get();
	}//FIN METODO

	public StringProperty tipoComponenteProperty() {
		return this.tipoComponente;
	}//FIN METODO

	public String getTipoComponenteChar() {
		return TipoComponente.toCharacter(this.getTipoComponente());
	}//FIN METODO

	public void setCosto(Double costo) {
		this.costo.set(costo);
	}//FIN METODO

	public Double getCosto() {
		return this.costo.get();
	}//FIN METODO

	public ObjectProperty<Double> costoProperty() {
		return this.costo;
	}//FIN METODO

	public void setCostoDirecto(Double costoDirecto) {
		this.costoDirecto.set(costoDirecto);
	}//FIN METODO

	public Double getCostoDirecto() {
		return this.costoDirecto.get();
	}//FIN METODO

	public ObjectProperty<Double> costoDirectoProperty() {
		return this.costoDirecto;
	}//FIN METODO

	public void setCostoIndirecto(Double costoIndirecto) {
		this.costoIndirecto.set(costoIndirecto);
	}//FIN METODO

	public Double getCostoIndirecto() {
		return this.costoIndirecto.get();
	}//FIN METODO

	public ObjectProperty<Double> costoIndirectoProperty() {
		return this.costoIndirecto;
	}//FIN METODO

	public void setMaterialFK(int materialFK) {
		this.materialFK.set(materialFK);
	}//FIN METODO

	public int getMaterialFK() {
		return this.materialFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> materialFKProperty() {
		return this.materialFK;
	}//FIN METODO

	public Material getMaterial(Connection connection) {
		return MaterialDAO.readMaterial(connection, this.getMaterialFK());
	}//FIN METODO

	public void setMaterialDescripcion(String materialDescripcion){
		this.materialDescripcion.set(materialDescripcion);
	}

	public String getMaterialDescripcion(){
		return this.materialDescripcion.get();
	}

	public StringProperty materialDescripcion(){
		return this.materialDescripcion;
	}

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

	public void setConsecutivo(int consecutivo) {
		this.consecutivo.set(consecutivo);
	}//FIN METODO

	public int getConsecutivo() {
		return this.consecutivo.get();
	}//FIN METODO

	public ObjectProperty<Integer> consecutivoProperty() {
		return this.consecutivo;
	}//FIN METODO

	public void setClienteFK(int clienteFK) {
		this.clienteFK.set(clienteFK);
	}//FIN METODO

	public int getclienteFK() {
		return this.clienteFK.get();
	}//FIN METODO

	public ObjectProperty<Integer> clienteFKProperty() {
		return this.clienteFK;
	}//FIN METODO

	public Cliente getCliente(Connection connection) {
		return ClienteDAO.readCliente(connection, this.getclienteFK());
	}//FIN METODO

	public static String mostrarInformacionEnsamble(Connection connection, Componente componente, int nivel, String lista) {
		nivel += 1;
		System.out.print(componente.getNumeroParte());
		lista += componente.getNumeroParte();
		System.out.print(" " + componente.getDescripcion() + " - ");
		lista += " " + componente.getDescripcion() + " - ";
		System.out.print("Nivel: " + nivel + "\n");
		lista += "Nivel: " + nivel + "\n";
		if (componente.getTipoComponente() == TipoComponente.ENSAMBLE || componente.getTipoComponente() == TipoComponente.SUB_ENSAMBLE) {
			ArrayList<DetalleComponente> detalleComponente = DetalleComponenteDAO.readDetalleComponenteSuperiorFK(connection, componente.getSysPK());
			for (DetalleComponente detalle : detalleComponente) {
				System.out.print(detalle.getCantidad() + " ");
				lista += detalle.getCantidad() + " ";
				mostrarInformacionEnsamble(connection, detalle.getComponenteInferior(connection), nivel, lista);
			}
		}
		return lista;
	}//FIN METODO

	public void setEsInterno(Integer esInterno) {
		this.esInterno.set(esInterno);
	}//FIN METODO
 
	public Integer getEsInterno() {
		return this.esInterno.get();
	}//FIN METODO

	public ObjectProperty<Integer> esInternoProperty() {
		return this.esInterno;
	}//FIN METODO
	
	//METODO PARA ACCEDER A HILOS
	public void setHilos(String hilos) {
		this.hilos.set(hilos);
	}//FIN METODO
	
	public String getHilos() {
		return this.hilos.get();
	}//FIN METODO
	
	public StringProperty hilosProperty() {
		return this.hilos;
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
}//FIN CLASE
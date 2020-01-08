package mx.shf6.produccion.utilities;

public class Dimensiones {
	
	private Double largo;
	private Double ancho;
	private Double alto;
	private Double diametroInterior;
	private Double diametroExterior; //DIAMETRO SI LA PIEZA NO ES HUECA
	private Double espesor;
	private Double alto2;
	private Double anchoTotal; //ANCHO + ANCHO2 + DIAMETRO
	private String codigoCatalogo; //CODIGO DEL CATALGO PERFIL CORTINA
	private String unidadLargo;
	private String unidadAncho;
	private String unidadAlto;
	private String unidadEspesor;
	private String unidadDExt;
	private String unidadDInt;
	private String unidadAlto2;
	private String unidadAnchoTotal;
	
	//CONSTRUCTOR VACIO
	public Dimensiones (){
		this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, "", "", "", "", "", "", "", "", "");
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR
	public Dimensiones(double largo, double ancho, double alto, double diametroInterior, double diametroExterior, double espesor, double alto2, double anchoTotal, String codigoCatalogo, String unidadLargo, String unidadAncho, String unidadAlto, String unidadEspesor, String unidadDExt, String unidadDInt, String unidadAlto2, String unidadAnchoTotal) {
		this.largo = largo;
		this.ancho = ancho;
		this.alto = alto;
		this.diametroInterior = diametroInterior;
		this.diametroExterior = diametroExterior;
		this.espesor = espesor;
		this.alto2 = alto2;
		this.anchoTotal = anchoTotal;
		this.codigoCatalogo = codigoCatalogo;
		this.unidadLargo = unidadLargo;
		this.unidadAncho = unidadAncho;
		this.unidadAlto = unidadAlto;
		this.unidadEspesor = unidadEspesor;
		this.unidadDExt = unidadDExt;
		this.unidadDInt = unidadDInt;
		this.unidadAlto2 = unidadAlto2;
		this.unidadAnchoTotal = unidadAnchoTotal;
	}//FIN CONSTRUCTOR
	
	//METODOS PARA ACCEDER A LARGO
	public void setLargo(Double largo) {
		this.largo = largo;
	}//FIN METODO
	
	public Double getLargo() {
		return this.largo;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A ANCHO
	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}//FIN METODO
	
	public Double getAncho() {
		return this.ancho;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A ALTO
	public void setAlto(Double alto) {
		this.alto = alto;
	}//FIN METODO
	
	public Double getAlto() {
		return this.alto;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A DIAMETRO INTERIOR
	public void setDiametroInterior(Double diametroInterior) {
		this.diametroInterior = diametroInterior;
	}///FIN METODO
	
	public Double getDiametroInterior() {
		return this.diametroInterior;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A DIAMETRO EXTERIOR
	public void setDiametroExterior(Double diametroExterior) {
		this.diametroExterior = diametroExterior;
	}//FIN METODO
	
	public Double getDiametroExterior() {
		return this.diametroExterior;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A ESPESOR
	public void setEspesor(Double espesor) {
		this.espesor = espesor;
	}//FIN METODO
	
	public Double getEspesor() {
		return this.espesor;
	}//FIN METODO
	
	//METODO PARA ACCEDER A ALTO2
	public void setAlto2(Double alto2) {
		this.alto2 = alto2;
	}//FIN METODO
	
	public Double getAlto2() {
		return this.alto2;
	}//FIN METODO
	
	//METODO PARA ACCEDER A ANCHO TOTAL
	public void setAnchoTotal(Double anchoTotal) {
		this.anchoTotal = anchoTotal;
	}//FIN METODO
	
	public Double getAnchoTotal() {
		return this.anchoTotal;
	}//FIN METODO
	
	//METODO PARA ACCEDER CODIGO CATALOGO	
	public void setCodigoCatalogo(String codigoCatalogo) {
		this.codigoCatalogo = codigoCatalogo;
	}//FIN METODO
	
	public String getCodigoCatalogo() {
		return this.codigoCatalogo;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD LARGO
	public void setUnidadLargo(String unidadLargo) {
		this.unidadLargo = unidadLargo;
	}//FIN METODO
	
	public String getUnidadLargo() {
		return this.unidadLargo;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD ANCHO
	public void setUnidadAncho(String unidadAncho) {
		this.unidadAncho = unidadAncho;
	}//FIN METODO
	
	public String getUnidadAncho() {
		return this.unidadAncho;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD ALTO
	public void setUnidadAlto(String unidadAlto) {
		this.unidadAlto = unidadAlto;
	}//FIN METODO
	
	public String getUnidadAlto() {
		return this.unidadAlto;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD ESPESOR
	public void setUnidadEspesor (String unidadEspesor) {
		this.unidadEspesor = unidadEspesor;
	}//FIN METODO
	
	public String getUnidadEspesor() {
		return this.unidadEspesor;
	}//FIN METODO
	
	//METODO PARA ACCEDER AUNIDAD DIAMETRO EXTERIOR
	public void setUnidadDExt (String unidadDext) {
		this.unidadDExt = unidadDext;
	}//FIN METODO
	
	public String getUnidadDExt() {
		return this.unidadDExt;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD DIAMETRO INTERIOR
	public void setUnidadDInt(String unidadDInt) {
		this.unidadDInt = unidadDInt;
	}//FIN METODO
	
	public String getUnidadDInt() {
		return this.unidadDInt;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD ALTO2
	public void setUnidadAlto2(String unidadAlto2) {
		this.unidadAlto2 = unidadAlto2;
	}//FIN METODO
	
	public String getUnidadAlto2() {
		return this.unidadAlto2;
	}//FIN METODO
	
	//METODO PARA ACCEDER A UNIDAD ANCHO TOTAL
	public void setUnidadAnchoTotal(String unidadAnchoTotal) {
		this.unidadAnchoTotal = unidadAnchoTotal;
	}//FIN METODO
	
	public String getUnidadAnchoTotal() {
		return this.unidadAnchoTotal;
	}//FIN METODO
}//FIN CLASE

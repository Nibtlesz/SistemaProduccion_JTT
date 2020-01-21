package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mx.shf6.produccion.model.Componente;
import mx.shf6.produccion.utilities.Dimensiones;
import mx.shf6.produccion.utilities.Notificacion;

public class ComponenteDAO {

	//METODO PARA CREAR UN REGISTRO
	public static boolean createComponente(Connection connection, Componente componente) {
		String consulta = "INSERT INTO componentes (NumeroParte, Descripcion, Largo, UnidadLargo, Ancho, UnidadAncho, Alto, UnidadAlto, Grado, Espesor, UnidadEspesor, DiametroExterno, UnidadDExt, DiametroInterno, UnidadDInt, Alto2, UnidadAlto2, AnchoTotal, UnidadAnchoTotal, CodigoCatalogo, TipoComponente, Costo, CostoDirecto, CostoIndirecto, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK, EsInterno, Hilos, Revision) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, componente.getNumeroParte());
			sentenciaPreparada.setString(2, componente.getDescripcion());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Componente> readComponente(Connection connection) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM producto";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static Componente readComponente(Connection connection, int sysPK) {
		Componente componente = new Componente();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM producto WHERE Sys_PK =  " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return componente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static ArrayList<Componente> readComponente(Connection connection, String like) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, UnidadLargo, Ancho, UnidadAncho, Alto, UnidadAlto, Grado, Espesor, UnidadEspesor, DiametroExterno, UnidadDExt, DiametroInterno, UnidadDInt, Alto2, UnidadAlto2, AnchoTotal, UnidadAnchoTotal, CodigoCatalogo, TipoComponente, Costo, CostoDirecto, CostoIndirecto, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK, EsInterno, Hilos, Revision FROM componentes WHERE Descripcion LIKE '%" + like + "%' OR NumeroParte LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setUnidadLargo(resultados.getString(5));
				dimensiones.setAncho(resultados.getDouble(6));
				dimensiones.setUnidadAncho(resultados.getString(7));
				dimensiones.setAlto(resultados.getDouble(8));
				dimensiones.setUnidadAlto(resultados.getString(9));
				dimensiones.setEspesor(resultados.getDouble(11));
				dimensiones.setUnidadEspesor(resultados.getString(12));
				dimensiones.setDiametroExterior(resultados.getDouble(13));
				dimensiones.setUnidadDExt(resultados.getString(14));
				dimensiones.setDiametroInterior(resultados.getDouble(15));
				dimensiones.setUnidadDInt(resultados.getString(16));
				dimensiones.setAlto2(resultados.getDouble(17));
				dimensiones.setUnidadAlto2(resultados.getString(18));
				dimensiones.setAnchoTotal(resultados.getDouble(19));
				dimensiones.setUnidadAnchoTotal(resultados.getString(20));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO

	//METODO PARA OBTENER UN REGISTRO
	public static Componente readComponenteNumeroParte(Connection connection, String numeroParte) {
		Componente componente = new Componente();
		String consulta = "SELECT Sys_PK, Codigo, Descripcion FROM producto WHERE Codigo = '" + numeroParte + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return componente;
	}//FIN METODO

	//METODO PARA OBTENER TODOS LOS COMPONENTES DE UN TIPOP EN ESPECIFICO
	public static ArrayList<Componente> readComponenteTipoComponente(Connection connection, String tipoComponenteChar) {
		ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, UnidadLargo, Ancho, UnidadAncho, Alto, UnidadAlto, Grado, Espesor, UnidadEspesor, DiametroExterno, UnidadDExt, DiametroInterno, UnidadDInt, Alto2, UnidadAlto2, AnchoTotal, UnidadAnchoTotal, CodigoCatalogo, TipoComponente, Costo, CostoDirecto, CostoIndirecto, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK, EsInterno, Hilos, Revision FROM componentes WHERE TipoComponente = '" + tipoComponenteChar + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setUnidadLargo(resultados.getString(5));
				dimensiones.setAncho(resultados.getDouble(6));
				dimensiones.setUnidadAncho(resultados.getString(7));
				dimensiones.setAlto(resultados.getDouble(8));
				dimensiones.setUnidadAlto(resultados.getString(9));
				dimensiones.setEspesor(resultados.getDouble(11));
				dimensiones.setUnidadEspesor(resultados.getString(12));
				dimensiones.setDiametroExterior(resultados.getDouble(13));
				dimensiones.setUnidadDExt(resultados.getString(14));
				dimensiones.setDiametroInterior(resultados.getDouble(15));
				dimensiones.setUnidadDInt(resultados.getString(16));
				dimensiones.setAlto2(resultados.getDouble(17));
				dimensiones.setUnidadAlto2(resultados.getString(18));
				dimensiones.setAnchoTotal(resultados.getDouble(19));
				dimensiones.setUnidadAnchoTotal(resultados.getString(20));
				arrayListComponente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponente;
	}//FIN METODO
	
	//METODO PARA OBTENER TODOS LOS COMPONENTES DE UN TIPOP EN ESPECIFICO
		public static ArrayList<Componente> readComponenteTipoComponenteParte(Connection connection, String tipoComponenteChar, String numeroParte) {
			ArrayList<Componente> arrayListComponente = new ArrayList<Componente>();
			String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, UnidadLargo, Ancho, UnidadAncho, Alto, UnidadAlto, Grado, \r\n" + 
					"Espesor, UnidadEspesor, DiametroExterno, UnidadDExt, DiametroInterno, UnidadDInt, Alto2, UnidadAlto2, AnchoTotal, UnidadAnchoTotal, \r\n" + 
					"CodigoCatalogo, TipoComponente, Costo, CostoDirecto, CostoIndirecto, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK,\r\n" + 
					" Notas, Status, Consecutivo, ClienteFK, EsInterno, Hilos, Revision FROM componentes WHERE TipoComponente = '" + tipoComponenteChar + "' AND Descripcion LIKE '%" + numeroParte + "%'";
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					Componente componente = new Componente();
					componente.setSysPK(resultados.getInt(1));
					componente.setNumeroParte(resultados.getString(2));
					componente.setDescripcion(resultados.getString(3));
					Dimensiones dimensiones = new Dimensiones();
					dimensiones.setLargo(resultados.getDouble(4));
					dimensiones.setUnidadLargo(resultados.getString(5));
					dimensiones.setAncho(resultados.getDouble(6));
					dimensiones.setUnidadAncho(resultados.getString(7));
					dimensiones.setAlto(resultados.getDouble(8));
					dimensiones.setUnidadAlto(resultados.getString(9));
					dimensiones.setEspesor(resultados.getDouble(11));
					dimensiones.setUnidadEspesor(resultados.getString(12));
					dimensiones.setDiametroExterior(resultados.getDouble(13));
					dimensiones.setUnidadDExt(resultados.getString(14));
					dimensiones.setDiametroInterior(resultados.getDouble(15));
					dimensiones.setUnidadDInt(resultados.getString(16));
					dimensiones.setAlto2(resultados.getDouble(17));
					dimensiones.setUnidadAlto2(resultados.getString(18));
					dimensiones.setAnchoTotal(resultados.getDouble(19));
					dimensiones.setUnidadAnchoTotal(resultados.getString(20));;
					arrayListComponente.add(componente);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListComponente;
		}//FIN METODO


	//METODO PARA OBTENER TODOS LOS NUMEROS DE PARTE
	public static ObservableList<String> listaNumerosParte(Connection connection) {
		ObservableList<String> arrayListNumeroParteComponente = FXCollections.observableArrayList();
		String consulta = "SELECT NumeroParte FROM componentes ORDER BY NumeroParte ASC";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				String numeroParte = resultados.getString(1);
				arrayListNumeroParteComponente.add(numeroParte);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListNumeroParteComponente;
	}//FIN METODO

	//METODO PARA OBTENER TODOS LOS NUMEROS DE PARTE
	public static ArrayList<Componente> readComponentesEnsambleCliente(Connection connection, int clienteFK) {
		ArrayList<Componente> arrayListComponentesEnsambleCliente = new ArrayList<Componente>();
		String consulta = "SELECT * FROM componentes WHERE ClienteFK = "+ clienteFK +" AND TipoComponente = 'A'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
				Componente componente = new Componente();
				componente.setSysPK(resultados.getInt(1));
				componente.setNumeroParte(resultados.getString(2));
				componente.setDescripcion(resultados.getString(3));
				Dimensiones dimensiones = new Dimensiones();
				dimensiones.setLargo(resultados.getDouble(4));
				dimensiones.setUnidadLargo(resultados.getString(5));
				dimensiones.setAncho(resultados.getDouble(6));
				dimensiones.setUnidadAncho(resultados.getString(7));
				dimensiones.setAlto(resultados.getDouble(8));
				dimensiones.setUnidadAlto(resultados.getString(9));
				dimensiones.setEspesor(resultados.getDouble(11));
				dimensiones.setUnidadEspesor(resultados.getString(12));
				dimensiones.setDiametroExterior(resultados.getDouble(13));
				dimensiones.setUnidadDExt(resultados.getString(14));
				dimensiones.setDiametroInterior(resultados.getDouble(15));
				dimensiones.setUnidadDInt(resultados.getString(16));
				dimensiones.setAlto2(resultados.getDouble(17));
				dimensiones.setUnidadAlto2(resultados.getString(18));
				dimensiones.setAnchoTotal(resultados.getDouble(19));
				dimensiones.setUnidadAnchoTotal(resultados.getString(20));
				arrayListComponentesEnsambleCliente.add(componente);
			}//FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		return arrayListComponentesEnsambleCliente;
	}//FIN METODO

	//METODO PARA OBTENER TODOS LOS NUMEROS DE PARTE
		public static ArrayList<Componente> readComponentesEnsambleAndPrimarias(Connection connection, int clienteFK) {
			ArrayList<Componente> arrayListComponentesEnsambleCliente = new ArrayList<Componente>();
			String consulta = "SELECT * FROM componentes WHERE (TipoComponente = 'A' OR TipoComponente = 'P') AND ClienteFK = " + clienteFK;
			try {
				Statement sentencia = connection.createStatement();
				ResultSet resultados = sentencia.executeQuery(consulta);
				while (resultados.next()) {
					Componente componente = new Componente();
					componente.setSysPK(resultados.getInt(1));
					componente.setNumeroParte(resultados.getString(2));
					componente.setDescripcion(resultados.getString(3));
					Dimensiones dimensiones = new Dimensiones();
					dimensiones.setLargo(resultados.getDouble(4));
					dimensiones.setUnidadLargo(resultados.getString(5));
					dimensiones.setAncho(resultados.getDouble(6));
					dimensiones.setUnidadAncho(resultados.getString(7));
					dimensiones.setAlto(resultados.getDouble(8));
					dimensiones.setUnidadAlto(resultados.getString(9));
					dimensiones.setEspesor(resultados.getDouble(11));
					dimensiones.setUnidadEspesor(resultados.getString(12));
					dimensiones.setDiametroExterior(resultados.getDouble(13));
					dimensiones.setUnidadDExt(resultados.getString(14));
					dimensiones.setDiametroInterior(resultados.getDouble(15));
					dimensiones.setUnidadDInt(resultados.getString(16));
					dimensiones.setAlto2(resultados.getDouble(17));
					dimensiones.setUnidadAlto2(resultados.getString(18));
					dimensiones.setAnchoTotal(resultados.getDouble(19));
					dimensiones.setUnidadAnchoTotal(resultados.getString(20));
					arrayListComponentesEnsambleCliente.add(componente);
				}//FIN WHILE
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
			return arrayListComponentesEnsambleCliente;
		}//FIN METODO

		
	//METODO PARA CREAR UN REGISTRO
	public static boolean updateComponente(Connection connection, Componente componente) {
		String consulta = "UPDATE componentes SET NumeroParte = ?, Descripcion = ?, Largo = ?, UnidadLargo = ?, Ancho = ?, UnidadAncho = ?, "
				+ "Alto = ?, UnidadAlto = ?, Grado = ?, Espesor = ?, UnidadEspesor = ?, DiametroExterno = ?, UnidadDExt = ?, "
				+ "DiametroInterno = ?, UnidadDInt = ?, Alto2 = ?, UnidadAlto2 = ?, AnchoTotal = ?, UnidadAnchoTotal = ?, CodigoCatalogo = ?,"
				+ " TipoComponente = ?, Costo = ?, CostoDirecto = ?, CostoIndirecto = ?, MaterialFK = ?, TipoMiscelaneoFK = ?,"
				+ " TipoMateriaPrimaFK = ?, AcabadoFK = ?, TratamientoFK = ?, Notas = ?, Status = ?, Consecutivo = ?, ClienteFK = ?,"
				+ " EsInterno = ?, Hilos = ?, Revision = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setString(1, componente.getNumeroParte());
			sentenciaPreparada.setString(2, componente.getDescripcion());
			sentenciaPreparada.setInt(37, componente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA CREAR UN REGISTRO
	public static boolean deleteComponente(Connection connection, Componente componente) {
		String consulta = "DELETE FROM componentes WHERE Sys_PK = ?";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(consulta);
			sentenciaPreparada.setInt(1, componente.getSysPK());
			sentenciaPreparada.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY/CATCH
	}//FIN METODO

	//METODO PARA CONVERTIR ARRAYLIST EN OBSERVABLELIST
	public static ObservableList<Componente> toObservableList(ArrayList<Componente> arrayList) {
		ObservableList<Componente> listaObservableComponente = FXCollections.observableArrayList();
		for (Componente componente : arrayList)
			listaObservableComponente.add(componente);
		return listaObservableComponente;
	}//FIN METODO

}//FIN CLASE
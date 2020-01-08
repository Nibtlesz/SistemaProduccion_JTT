package mx.shf6.produccion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
			sentenciaPreparada.setDouble(3, componente.getDimensiones().getLargo());
			sentenciaPreparada.setString(4, componente.getDimensiones().getUnidadLargo());
			sentenciaPreparada.setDouble(5, componente.getDimensiones().getAncho());
			sentenciaPreparada.setString(6, componente.getDimensiones().getUnidadAncho());
			sentenciaPreparada.setDouble(7, componente.getDimensiones().getAlto());
			sentenciaPreparada.setString(8, componente.getDimensiones().getUnidadAlto());
			sentenciaPreparada.setString(9, componente.getGradoMaterial());
			sentenciaPreparada.setDouble(10, componente.getDimensiones().getEspesor());
			sentenciaPreparada.setString(11, componente.getDimensiones().getUnidadEspesor());
			sentenciaPreparada.setDouble(12, componente.getDimensiones().getDiametroExterior());
			sentenciaPreparada.setString(13, componente.getDimensiones().getUnidadDExt());
			sentenciaPreparada.setDouble(14, componente.getDimensiones().getDiametroInterior());
			sentenciaPreparada.setString(15, componente.getDimensiones().getUnidadDInt());
			sentenciaPreparada.setDouble(16, componente.getDimensiones().getAlto2());
			sentenciaPreparada.setString(17, componente.getDimensiones().getUnidadAlto2());
			sentenciaPreparada.setDouble(18, componente.getDimensiones().getAnchoTotal());
			sentenciaPreparada.setString(19, componente.getDimensiones().getUnidadAnchoTotal());
			sentenciaPreparada.setString(20, componente.getDimensiones().getCodigoCatalogo());
			sentenciaPreparada.setString(21, componente.getTipoComponenteChar());
			sentenciaPreparada.setDouble(22, componente.getCosto());
			sentenciaPreparada.setDouble(23, componente.getCostoDirecto());
			sentenciaPreparada.setDouble(24, componente.getCostoIndirecto());
			if (componente.getMaterialFK() > 0)
				sentenciaPreparada.setInt(25, componente.getMaterialFK());
			else
				sentenciaPreparada.setNull(25, Types.INTEGER);
			if (componente.getTipoMiscelaneoFK() > 0)
				sentenciaPreparada.setInt(26, componente.getTipoMiscelaneoFK());
			else
				sentenciaPreparada.setNull(26, Types.INTEGER);
			if (componente.getTipoMateriaPrimaFK() > 0)
				sentenciaPreparada.setInt(27, componente.getTipoMateriaPrimaFK());
			else
				sentenciaPreparada.setNull(27, Types.INTEGER);
			if (componente.getAcabadoFK() > 0)
				sentenciaPreparada.setInt(28, componente.getAcabadoFK());
			else
				sentenciaPreparada.setNull(28, Types.INTEGER);
			if (componente.getTratamientoFK() > 0)
				sentenciaPreparada.setInt(29, componente.getTratamientoFK());
			else
				sentenciaPreparada.setNull(29, Types.INTEGER);
			sentenciaPreparada.setString(30, componente.getNotas());
			sentenciaPreparada.setInt(31, componente.getStatusFK());
			sentenciaPreparada.setInt(32, componente.getConsecutivo());
			if (componente.getclienteFK() > 0)
				sentenciaPreparada.setInt(33, componente.getclienteFK());
			else
				sentenciaPreparada.setNull(33, Types.INTEGER);
			sentenciaPreparada.setInt(34, componente.getEsInterno());
			sentenciaPreparada.setString(35, componente.getHilos());
			sentenciaPreparada.setString(36, componente.getRevision());
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
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, UnidadLargo, Ancho, UnidadAncho, Alto, UnidadAlto, Grado, Espesor, UnidadEspesor, DiametroExterno, UnidadDExt, DiametroInterno, UnidadDInt, Alto2, UnidadAlto2, AnchoTotal, UnidadAnchoTotal, CodigoCatalogo, TipoComponente, Costo, CostoDirecto, CostoIndirecto, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK, EsInterno, Hilos, Revision FROM componentes";
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
				componente.setGradoMaterial(resultados.getString(10));
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
				dimensiones.setCodigoCatalogo(resultados.getString(21));
				componente.setTipoComponente(resultados.getString(22));
				componente.setCosto(resultados.getDouble(23));
				componente.setCostoDirecto(resultados.getDouble(24));
				componente.setCostoIndirecto(resultados.getDouble(25));
				componente.setMaterialFK(resultados.getInt(26));;
				componente.setTipoMiscelaneoFK(resultados.getInt(27));
				componente.setTipoMateriaPrimaFK(resultados.getInt(28));
				componente.setAcabadoFK(resultados.getInt(29));
				componente.setTratamientoFK(resultados.getInt(30));
				componente.setNotas(resultados.getString(31));
				componente.setStatus(resultados.getInt(32));
				componente.setConsecutivo(resultados.getInt(33));
				componente.setClienteFK(resultados.getInt(34));
				componente.setEsInterno(resultados.getInt(35));
				componente.setHilos(resultados.getString(36));
				componente.setRevision(resultados.getString(37));
				componente.setDimensiones(dimensiones);
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
		String consulta = "SELECT componentes.Sys_PK, componentes.NumeroParte, componentes.Descripcion, componentes.Largo, componentes.UnidadLargo, componentes.Ancho, componentes.UnidadAncho, componentes.Alto, componentes.UnidadAlto, componentes.Grado, componentes.Espesor, componentes.UnidadEspesor, componentes.DiametroExterno, componentes.UnidadDExt, componentes.DiametroInterno, componentes.UnidadDInt, componentes.Alto2, componentes.UnidadAlto2, componentes.AnchoTotal, componentes.UnidadAnchoTotal, componentes.CodigoCatalogo, componentes.TipoComponente, componentes.Costo, componentes.CostoDirecto, componentes.CostoIndirecto, componentes.MaterialFK, componentes.TipoMiscelaneoFK, componentes.TipoMateriaPrimaFK, componentes.AcabadoFK, componentes.TratamientoFK, componentes.Notas, componentes.Status, componentes.Consecutivo, componentes.ClienteFK, componentes.EsInterno, componentes.Hilos, componentes.Revision, materiales.Descripcion FROM componentes  INNER JOIN materiales ON componentes.MaterialFK = materiales.Sys_PK WHERE componentes.Sys_PK =  " + sysPK;
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
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
				componente.setGradoMaterial(resultados.getString(10));
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
				dimensiones.setCodigoCatalogo(resultados.getString(21));
				componente.setTipoComponente(resultados.getString(22));
				componente.setCosto(resultados.getDouble(23));
				componente.setCostoDirecto(resultados.getDouble(24));
				componente.setCostoIndirecto(resultados.getDouble(25));
				componente.setMaterialFK(resultados.getInt(26));;
				componente.setTipoMiscelaneoFK(resultados.getInt(27));
				componente.setTipoMateriaPrimaFK(resultados.getInt(28));
				componente.setAcabadoFK(resultados.getInt(29));
				componente.setTratamientoFK(resultados.getInt(30));
				componente.setNotas(resultados.getString(31));
				componente.setStatus(resultados.getInt(32));
				componente.setConsecutivo(resultados.getInt(33));
				componente.setClienteFK(resultados.getInt(34));
				componente.setEsInterno(resultados.getInt(35));
				componente.setHilos(resultados.getString(36));
				componente.setRevision(resultados.getString(37));
				componente.setMaterialDescripcion(resultados.getString(38));
				componente.setDimensiones(dimensiones);
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
				componente.setGradoMaterial(resultados.getString(10));
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
				dimensiones.setCodigoCatalogo(resultados.getString(21));
				componente.setTipoComponente(resultados.getString(22));
				componente.setCosto(resultados.getDouble(23));
				componente.setCostoDirecto(resultados.getDouble(24));
				componente.setCostoIndirecto(resultados.getDouble(25));
				componente.setMaterialFK(resultados.getInt(26));;
				componente.setTipoMiscelaneoFK(resultados.getInt(27));
				componente.setTipoMateriaPrimaFK(resultados.getInt(28));
				componente.setAcabadoFK(resultados.getInt(29));
				componente.setTratamientoFK(resultados.getInt(30));
				componente.setNotas(resultados.getString(31));
				componente.setStatus(resultados.getInt(32));
				componente.setConsecutivo(resultados.getInt(33));
				componente.setClienteFK(resultados.getInt(34));
				componente.setEsInterno(resultados.getInt(35));
				componente.setHilos(resultados.getString(36));
				componente.setRevision(resultados.getString(37));
				componente.setDimensiones(dimensiones);
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
		String consulta = "SELECT Sys_PK, NumeroParte, Descripcion, Largo, UnidadLargo, Ancho, UnidadAncho, Alto, UnidadAlto, Grado, Espesor, UnidadEspesor, DiametroExterno, UnidadDExt, DiametroInterno, UnidadDInt, Alto2, UnidadAlto2, AnchoTotal, UnidadAnchoTotal, CodigoCatalogo, TipoComponente, Costo, CostoDirecto, CostoIndirecto, MaterialFK, TipoMiscelaneoFK, TipoMateriaPrimaFK, AcabadoFK, TratamientoFK, Notas, Status, Consecutivo, ClienteFK, EsInterno, Hilos, Revision FROM componentes WHERE NumeroParte = '" + numeroParte + "'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(consulta);
			while (resultados.next()) {
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
				componente.setGradoMaterial(resultados.getString(10));
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
				dimensiones.setCodigoCatalogo(resultados.getString(21));
				componente.setTipoComponente(resultados.getString(22));
				componente.setCosto(resultados.getDouble(23));
				componente.setCostoDirecto(resultados.getDouble(24));
				componente.setCostoIndirecto(resultados.getDouble(25));
				componente.setMaterialFK(resultados.getInt(26));;
				componente.setTipoMiscelaneoFK(resultados.getInt(27));
				componente.setTipoMateriaPrimaFK(resultados.getInt(28));
				componente.setAcabadoFK(resultados.getInt(29));
				componente.setTratamientoFK(resultados.getInt(30));
				componente.setNotas(resultados.getString(31));
				componente.setStatus(resultados.getInt(32));
				componente.setConsecutivo(resultados.getInt(33));
				componente.setClienteFK(resultados.getInt(34));
				componente.setEsInterno(resultados.getInt(35));
				componente.setHilos(resultados.getString(36));
				componente.setRevision(resultados.getString(37));
				componente.setDimensiones(dimensiones);
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
				componente.setGradoMaterial(resultados.getString(10));
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
				dimensiones.setCodigoCatalogo(resultados.getString(21));
				componente.setTipoComponente(resultados.getString(22));
				componente.setCosto(resultados.getDouble(23));
				componente.setCostoDirecto(resultados.getDouble(24));
				componente.setCostoIndirecto(resultados.getDouble(25));
				componente.setMaterialFK(resultados.getInt(26));;
				componente.setTipoMiscelaneoFK(resultados.getInt(27));
				componente.setTipoMateriaPrimaFK(resultados.getInt(28));
				componente.setAcabadoFK(resultados.getInt(29));
				componente.setTratamientoFK(resultados.getInt(30));
				componente.setNotas(resultados.getString(31));
				componente.setStatus(resultados.getInt(32));
				componente.setConsecutivo(resultados.getInt(33));
				componente.setClienteFK(resultados.getInt(34));
				componente.setEsInterno(resultados.getInt(35));
				componente.setHilos(resultados.getString(36));
				componente.setRevision(resultados.getString(37));
				componente.setDimensiones(dimensiones);
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
					componente.setGradoMaterial(resultados.getString(10));
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
					dimensiones.setCodigoCatalogo(resultados.getString(21));
					componente.setTipoComponente(resultados.getString(22));
					componente.setCosto(resultados.getDouble(23));
					componente.setCostoDirecto(resultados.getDouble(24));
					componente.setCostoIndirecto(resultados.getDouble(25));
					componente.setMaterialFK(resultados.getInt(26));;
					componente.setTipoMiscelaneoFK(resultados.getInt(27));
					componente.setTipoMateriaPrimaFK(resultados.getInt(28));
					componente.setAcabadoFK(resultados.getInt(29));
					componente.setTratamientoFK(resultados.getInt(30));
					componente.setNotas(resultados.getString(31));
					componente.setStatus(resultados.getInt(32));
					componente.setConsecutivo(resultados.getInt(33));
					componente.setClienteFK(resultados.getInt(34));
					componente.setEsInterno(resultados.getInt(35));
					componente.setHilos(resultados.getString(36));
					componente.setRevision(resultados.getString(37));
					componente.setDimensiones(dimensiones);
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
				componente.setGradoMaterial(resultados.getString(10));
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
				dimensiones.setCodigoCatalogo(resultados.getString(21));
				componente.setTipoComponente(resultados.getString(22));
				componente.setCosto(resultados.getDouble(23));
				componente.setCostoDirecto(resultados.getDouble(24));
				componente.setCostoIndirecto(resultados.getDouble(25));
				componente.setMaterialFK(resultados.getInt(26));;
				componente.setTipoMiscelaneoFK(resultados.getInt(27));
				componente.setTipoMateriaPrimaFK(resultados.getInt(28));
				componente.setAcabadoFK(resultados.getInt(29));
				componente.setTratamientoFK(resultados.getInt(30));
				componente.setNotas(resultados.getString(31));
				componente.setStatus(resultados.getInt(32));
				componente.setConsecutivo(resultados.getInt(33));
				componente.setClienteFK(resultados.getInt(34));
				componente.setEsInterno(resultados.getInt(35));
				componente.setHilos(resultados.getString(36));
				componente.setRevision(resultados.getString(37));
				componente.setDimensiones(dimensiones);
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
					componente.setGradoMaterial(resultados.getString(10));
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
					dimensiones.setCodigoCatalogo(resultados.getString(21));
					componente.setTipoComponente(resultados.getString(22));
					componente.setCosto(resultados.getDouble(23));
					componente.setCostoDirecto(resultados.getDouble(24));
					componente.setCostoIndirecto(resultados.getDouble(25));
					componente.setMaterialFK(resultados.getInt(26));;
					componente.setTipoMiscelaneoFK(resultados.getInt(27));
					componente.setTipoMateriaPrimaFK(resultados.getInt(28));
					componente.setAcabadoFK(resultados.getInt(29));
					componente.setTratamientoFK(resultados.getInt(30));
					componente.setNotas(resultados.getString(31));
					componente.setStatus(resultados.getInt(32));
					componente.setConsecutivo(resultados.getInt(33));
					componente.setClienteFK(resultados.getInt(34));
					componente.setEsInterno(resultados.getInt(35));
					componente.setHilos(resultados.getString(36));
					componente.setRevision(resultados.getString(37));
					componente.setDimensiones(dimensiones);
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
			sentenciaPreparada.setDouble(3, componente.getDimensiones().getLargo());
			sentenciaPreparada.setString(4, componente.getDimensiones().getUnidadLargo());
			sentenciaPreparada.setDouble(5, componente.getDimensiones().getAncho());
			sentenciaPreparada.setString(6, componente.getDimensiones().getUnidadAncho());
			sentenciaPreparada.setDouble(7, componente.getDimensiones().getAlto());
			sentenciaPreparada.setString(8, componente.getDimensiones().getUnidadAlto());
			sentenciaPreparada.setString(9, componente.getGradoMaterial());
			sentenciaPreparada.setDouble(10, componente.getDimensiones().getEspesor());
			sentenciaPreparada.setString(11, componente.getDimensiones().getUnidadEspesor());
			sentenciaPreparada.setDouble(12, componente.getDimensiones().getDiametroExterior());
			sentenciaPreparada.setString(13, componente.getDimensiones().getUnidadDExt());
			sentenciaPreparada.setDouble(14, componente.getDimensiones().getDiametroInterior());
			sentenciaPreparada.setString(15, componente.getDimensiones().getUnidadDInt());
			sentenciaPreparada.setDouble(16, componente.getDimensiones().getAlto2());
			sentenciaPreparada.setString(17, componente.getDimensiones().getUnidadAlto2());
			sentenciaPreparada.setDouble(18, componente.getDimensiones().getAnchoTotal());
			sentenciaPreparada.setString(19, componente.getDimensiones().getUnidadAnchoTotal());
			sentenciaPreparada.setString(20, componente.getDimensiones().getCodigoCatalogo());
			sentenciaPreparada.setString(21, componente.getTipoComponenteChar());
			sentenciaPreparada.setDouble(22, componente.getCosto());
			sentenciaPreparada.setDouble(23, componente.getCostoDirecto());
			sentenciaPreparada.setDouble(24, componente.getCostoIndirecto());
			if (componente.getMaterialFK() > 0)
				sentenciaPreparada.setInt(25, componente.getMaterialFK());
			else
				sentenciaPreparada.setNull(25, Types.INTEGER);
			if (componente.getTipoMiscelaneoFK() > 0)
				sentenciaPreparada.setInt(26, componente.getTipoMiscelaneoFK());
			else
				sentenciaPreparada.setNull(26, Types.INTEGER);
			if (componente.getTipoMateriaPrimaFK() > 0)
				sentenciaPreparada.setInt(27, componente.getTipoMateriaPrimaFK());
			else
				sentenciaPreparada.setNull(27, Types.INTEGER);
			if (componente.getAcabadoFK() > 0)
				sentenciaPreparada.setInt(28, componente.getAcabadoFK());
			else
				sentenciaPreparada.setNull(28, Types.INTEGER);
			if (componente.getTratamientoFK() > 0)
				sentenciaPreparada.setInt(29, componente.getTratamientoFK());
			else
				sentenciaPreparada.setNull(29, Types.INTEGER);
			sentenciaPreparada.setString(30, componente.getNotas());
			sentenciaPreparada.setInt(31, componente.getStatusFK());
			sentenciaPreparada.setInt(32, componente.getConsecutivo());
			if (componente.getclienteFK() > 0)
				sentenciaPreparada.setInt(33, componente.getclienteFK());
			else
				sentenciaPreparada.setNull(33, Types.INTEGER);
			sentenciaPreparada.setInt(34, componente.getEsInterno());
			sentenciaPreparada.setString(35, componente.getHilos());
			sentenciaPreparada.setString(36, componente.getRevision());
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
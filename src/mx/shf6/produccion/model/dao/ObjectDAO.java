package mx.shf6.produccion.model.dao;
import java.util.ArrayList;

import java.sql.Connection;

public interface ObjectDAO {
	public boolean crear(Connection connection, Object objeto);
	public ArrayList<Object> leer(Connection connection, String campoBusqueda, String valorBusqueda);
	public boolean modificar(Connection connection, Object objeto);
	public boolean eliminar(Connection connection, Object objeto);
}
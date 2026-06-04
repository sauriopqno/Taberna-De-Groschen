package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.Conexion;
import modelo.Producto;

public class ProductoSQL {
    
    public List<Producto> listarProductos(){
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Menu";
        Connection connect = Conexion.conectar();
        
        try {
            PreparedStatement consulta = connect.prepareStatement(sql);
            ResultSet result = consulta.executeQuery();
            while(result.next()) {
                
                lista.add(new Producto(
                    result.getInt("id_producto"), 
                    result.getString("nombre"), 
                    result.getString("descripcion"), 
                    result.getString("categoria"), 
                    result.getDouble("precio")
                ));
            }
            result.close();
            consulta.close();
            connect.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
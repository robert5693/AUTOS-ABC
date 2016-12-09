/*
 * Clase que permite la conexion con la Base de datos.
 */
import java.sql.*;
/**
 *
 * @author Leoviviana M.
 */
public class Fachada {
    
    private String url, usuario, pwd;
    private Connection conexion;
    
    public Fachada(){ 
        //mysql
        url = "jdbc:mysql://localhost/AutosABC";
        usuario="soporte";
        pwd ="Soporte1@";
        /* //postgresql
        url = "jdbc:postgresql://localhost:5693/EmpresaABC";
        usuario="postgres";
        pwd ="yuliandrea5693";
        */
    }
    
    public Connection conectarABD(){
        
        try{
            //postgresql
            //Class.forName("org.postgresql.Driver");
            //mysql
            Class.forName("com.mysql.jdbc.Driver");
            
            System.out.println( "Driver Cargado" );
        }catch(Exception e){
            System.out.println( "No se pudo cargar el driver."+e );
        }
              
        try{
            conexion = DriverManager.getConnection(url, usuario, pwd);
            System.out.println( "Conexion Abierta" );
            return conexion;
        } catch( Exception e ) {
            System.out.println( "No se pudo abrir."+e );
            return null;
        }
        
    }//fin metodo conectarABD
    
    public void cerrarConexion(Connection c){
            try{
                 c.close();
            } catch( Exception e ) {
                System.out.println( "No se pudo cerrar." );
            }
        }//fin metodo cerrarConexion
    
}

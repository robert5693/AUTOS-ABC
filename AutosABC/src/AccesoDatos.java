/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author USUARIO
 */
public class AccesoDatos {
    
    Fachada fachada;
    ResultSet respuesta;
    Statement instruccion;
    
    public AccesoDatos(){
        fachada = new Fachada();
    }
    
    /**
     * Método que se encarga de ingresar la información de un programa a la base de datos 
     
     */
    
    public int ingresarUsuario(String id, String pass, String cdl, String pNom, String sNom,String pApe,String sApe, String tel1,String tel2,String dir,String foto,String fdn,String estado,String id_sede,String tipo){
        int numFilas;
        String consulta="INSERT INTO usuario VALUES ('"+ id+"','"+pass+"','"+cdl+"','"+pNom+"','"+sNom+"','"+pApe+"','"+sApe+"','"+tel1+"','"+tel2+"','"+dir+"',"+foto+",'"+fdn+"','"+estado+"','"+id_sede+"','"+tipo+"');";
        System.out.println(consulta);
        try{
           Connection con = fachada.conectarABD();
           instruccion = con.createStatement();
           numFilas = instruccion.executeUpdate(consulta);
           fachada.cerrarConexion(con);
        }catch(SQLException sqle){
            System.out.println("Error de Sql al conectar en programa \n"+sqle);
            numFilas=-1;
        }
        catch(Exception e){
            System.out.println("Ocurrió cualquier otra excepcion en programa"+e);
            numFilas=-1;
        }
        
        return numFilas;
    }
    
    
    //Metodo que se encarga de ingresar una sede a la base de datos
    public int ingresarSede(String id_sede,String nom,String ciudad,String direccion,String tlf1,String tlf2,String gerente){
        int numFilas;
        String consulta = "INSERT INTO sede VALUES ('"+ id_sede + "','" + nom + "','" + ciudad + "','" + direccion + "','"+ tlf1+  "','" + tlf2 + "','"+gerente+"');";
        try{
           Connection con = fachada.conectarABD();
           instruccion = con.createStatement();
           numFilas = instruccion.executeUpdate(consulta);
           fachada.cerrarConexion(con);
        }catch(SQLException sqle){
            System.out.println("Error de Sql al conectar en programa \n"+sqle);
            numFilas=-1;
        }
        catch(Exception e){
            System.out.println("Ocurrió cualquier otra excepcion en programa"+e);
            numFilas=-1;
        }
        return numFilas;
    }
    

    //Metodo quese encarga de retornar los datos de los usuarios que estan registrados para poder compararlos
    //con los datos que el usuario digita en la ventana
    public ResultSet loguearse(String nom){
        String consulta ="SELECT id_usuario,password FROM usuario WHERE id_usuario='" + nom + "';";
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            
            fachada.cerrarConexion(con);
            JOptionPane.showMessageDialog(null,respuesta);
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        return respuesta;
    }
    
    //Toma el id de usuario y extrae el tipo  de ese usuario para asi saber que ventana retornara 
    public ResultSet validarUsuario(String nom){
        String consulta ="SELECT id_usuario,tipo FROM usuario WHERE id_usuario='" + nom + "';";
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        return respuesta;
    }

    /* Extrae de la base de datos la informacion de una sede, esto es importante para la modificacion de la sede*/
    public ResultSet consultaSede(String id_sede) {
        String consulta ="SELECT * FROM sede WHERE id_sede = '"+ id_sede + "';";
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        
        return respuesta;
    }
    
    
    /*Extrae los id y nombres de las sedes, esto es para llenar el combobox de modificar */
    public ResultSet consultarSede() {
        String consulta ="SELECT id_sede,nombre FROM sede;";
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        
        return respuesta;
    }

    
    /*selecciona los id de los usuarios tipo Gerentes, esto es para llenar el combobox del registro de la sede*/
    public ResultSet tomarGerentes() {
        String consulta ="SELECT id_usuario FROM usuario WHERE tipo = 'Gerente';";
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        
        return respuesta;
    }
    
    //actualizacion de las sedes, realiza el update para que se actualice esa sede en especifico 
    public int actualizarSede(String id_sede,String nom,String ciudad,String direccion,String tlf1,String tlf2,String gerente){
        int numFilas;
        String consulta = "UPDATE sede SET id_sede = '"+ id_sede + "',nombre = '" + nom + "',ciudad = '" + ciudad + "',direccion = '" + direccion + "',telefono1 = '"+ tlf1+  "',telefono2 = '" + tlf2 + "',id_gerente = '"+gerente+"' WHERE id_sede = '"+id_sede+"';";
        System.out.println(consulta);
        try{
           Connection con = fachada.conectarABD();
           instruccion = con.createStatement();
           numFilas = instruccion.executeUpdate(consulta);
           fachada.cerrarConexion(con);
        }catch(SQLException sqle){
            System.out.println("Error de Sql al conectar en programa \n"+sqle);
            numFilas=-1;
        }
        catch(Exception e){
            System.out.println("Ocurrió cualquier otra excepcion en programa"+e);
            numFilas=-1;
        }
        return numFilas;
    }  
    
    public ResultSet consultarUsuario(String id){
        ResultSet usuario = null;
        
        String consulta = "SELECT * FROM usuario WHERE id_usuario = " + "'"+id+"'"; 
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            usuario = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos"+sqle);
        }
        
        
        return usuario;
    }

    ResultSet consultarCliente(String cedula) {
        ResultSet cliente = null;
        
        String consulta = "SELECT * FROM cliente WHERE cedula = " + "'"+cedula+"'"; 
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            cliente = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos"+sqle);
        }
        
        
        return cliente;
    }

    ResultSet consultarSede(String id_vendedor) {
        ResultSet usuario = null;
        
        String consulta = "SELECT id_sede FROM usuario WHERE id_usuario = " + "'"+id_vendedor+"'"; 
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            usuario = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos"+sqle);
        }
        
        
        return usuario;
    }

    ResultSet listarCotizaciones(String cedula) {
        String consulta ="SELECT id_cotizacion,fecha FROM cotizacion WHERE cedula_cliente = '"+cedula+"';";
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        
        return respuesta;
    }

    ResultSet consultarCotizacionFecha(String fecha) {
        String consulta ="SELECT id_cotizacion,fecha FROM cotizacion WHERE fecha = '"+fecha+"';";
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        
        return respuesta;
    }

    ResultSet consultarFoto(String id) {
        String consulta ="SELECT foto FROM usuario WHERE id_usuario = '"+id+"';";
        
        try{
            Connection con = fachada.conectarABD();
            instruccion = con.createStatement();
            respuesta = instruccion.executeQuery(consulta);
            fachada.cerrarConexion(con);
            
               
        }catch(SQLException sqle){
            System.out.println("Error al consultar datos");
        }
        
        return respuesta;
    }
}
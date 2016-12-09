
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juancho270
 */
public class Implementacion {
    AccesoDatos datos;

    public Implementacion() {
        datos = new AccesoDatos();
    }
    
    
    /*Metodo que valida si los datos ingresados pr el usuario pertenencen a algun registro de usuario en la Base de Datos*/
    public boolean Loguearse (String nombre, String contraseña) throws SQLException{
       int contador = 0;
       int contador2 = 0;
       boolean validacion = false;
       String[] info = null;
       String[] pass = null;
       ResultSet respuesta = datos.loguearse(nombre);
       
       while (respuesta.next()) {           
               String em = respuesta.getString(1);
               JOptionPane.showMessageDialog(null,"hola");
               String pw = respuesta.getString(2);   
       }
                if (info == null || pass == null){
                   validacion = false;
               }else{
               for (int i =0; i < info.length; i++){
                   if(info[i].equals(nombre)){
                       contador2++;
                   }
               }
               for (int i= 0 ; i < pass.length;i++){
                   if (pass[i].equals(contraseña)){
                       contador++;
                   }
               }
                }
               if (contador != 0 && contador2 != 0){
                   validacion = true;
               }
        
        return validacion;
       
    }
    
    /*Retorana el tipo de usuario dado su id_usuario esto para poder ingresar a la ventana q le corresponde
    una vez logueado*/
    public String tipoUsuario(String nombre) throws SQLException{
         String respuestas = "";
         String[] tipo = null;
         ResultSet respuesta = datos.validarUsuario(nombre);
       
       while (respuesta.next()) {
               String pw = respuesta.getString("tipo");
               tipo = pw.split("\n");   
       }
        respuestas = tipo[0];
        return respuestas;
    }
    
    //Metodo que transforma el ResultSet en un arraylist de vectores
    public ArrayList<String> tiposUsuarios() throws SQLException{
      ArrayList<String> datosFila=new ArrayList();
      String dato = null;
               try {
                ResultSet rs = datos.tomarGerentes();
                //coloca los datos del Resulset en el DefaultTableModel
                while(rs.next()){
                    dato = rs.getString("id_usuario");
                    datosFila.add(dato);
                }    
        } catch (SQLException ex) {
            System.out.println("error al saar la información del resultset");
            
        }
        return datosFila;
    }
            
    
    //Metodo parsea los ResultSet en String y los pinta en la tabla para listar las sedes
     public void listarS(DefaultTableModel modelo){
        
        try {
                ResultSet rs = datos.consultarSede();
                ResultSetMetaData metadatos = rs.getMetaData();
                
                //limpiar valores del modelo si existen
                while(modelo.getRowCount() >0){
                    modelo.removeRow(0);
                }
                
                int numCol = metadatos.getColumnCount();
                Object[] nombCol = new Object[numCol];
                                              
                //nombres de las etiquetas de las columnas de la consulta
                for (int i = 0; i < nombCol.length; i++) {
                    nombCol[i] = metadatos.getColumnName(i+1);
                }
                
                //coloca los datos del Resulset en el DefaultTableModel
                modelo.setColumnIdentifiers(nombCol);
                while(rs.next()){
                    Object[] datosFila = new Object[numCol];
                    //se sacan los datos de cada fila del rs
                    for (int i = 0; i < datosFila.length; i++) {
                        datosFila[i] = rs.getObject(i+1);
                    }
                    //se añade la fila al modelo del table
                    modelo.addRow(datosFila);
                } 
        } catch (SQLException ex) {
            System.out.println("error al saar la información del resultset");
            
        }
    }
     
     
     //MEtodo que parsea los resultset de las sedes para ponerlos en el combobox para que el cliente seleccione la sede que desea modificar
     public ArrayList<String> consultarSede (){
          ArrayList<String> datosFila=new ArrayList();
          String dato = null;
          String nombre = null;
 
               try {
                ResultSet rs = datos.consultarSede();
                while(rs.next()){
                    dato = rs.getString("id_sede");
                    nombre = rs.getString("nombre");
                    datosFila.add(dato + "-" + nombre);
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al saar la información del resultset");
            
        }
        return datosFila;
     }
       
    //Datos de la sede antes de la modificacion.
     public ArrayList<String> datosSede(String id_sede){
          ArrayList<String> datosFila=new ArrayList();
          String id,nombre,ciudad,direccion,telefono1,telefono2,id_gerente;
               try {
                ResultSet rs = datos.consultaSede(id_sede);
                while(rs.next()){
                    id= rs.getString("id_sede");
                    nombre = rs.getString("nombre");
                    ciudad = rs.getString("ciudad");
                    direccion = rs.getString("direccion");
                    telefono1 = rs.getString("telefono1");
                    telefono2 = rs.getString("telefono2");
                    id_gerente = rs.getString("id_gerente");
                    datosFila.add(id);
                    datosFila.add(nombre);
                    datosFila.add(ciudad);
                    datosFila.add(direccion);
                    datosFila.add(telefono1);
                    datosFila.add(telefono2);
                    datosFila.add(id_gerente);
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al sacar la información del resultset");
            
        }
        return datosFila;
         
     }
     
     public boolean esUsuario(String id) throws SQLException{
    boolean bool = false;
        
    ResultSet consulta = datos.consultarUsuario(id);
    
         if (consulta.next()) {
             bool = true;
         } 
         
    return bool;
    
    }
    
    ArrayList<String> consultarCliente(String cedula) {
        ArrayList<String> datosCliente=new ArrayList();
        try {
                ResultSet rs = datos.consultarCliente(cedula);
                while(rs.next()){
                    datosCliente.add(rs.getString("cedula"));
                    datosCliente.add(rs.getString("primer_nombre"));                   
                    datosCliente.add(rs.getString("segundo_nombre"));
                    datosCliente.add(rs.getString("primer_apellido"));
                    datosCliente.add( rs.getString("segundo_apellido"));
                    datosCliente.add( rs.getString("telefono"));
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al sacar la información del resultset");
            
        }
        return datosCliente;
    }
    
    ArrayList<String> consultarUsuario(String id) {
         ArrayList<String> datosUsuario=new ArrayList();
               try {
                ResultSet rs = datos.consultarUsuario(id);
                while(rs.next()){
                    datosUsuario.add(rs.getString("id_usuario"));
                    datosUsuario.add(rs.getString("password"));
                    datosUsuario.add(rs.getString("cedula"));
                    datosUsuario.add(rs.getString("primer_nombre"));
                    datosUsuario.add(rs.getString("segundo_nombre"));
                    datosUsuario.add(rs.getString("primer_apellido"));
                    datosUsuario.add(rs.getString("segundo_Apellido"));
                    datosUsuario.add(rs.getString("telefono1"));
                    datosUsuario.add(rs.getString("telefono2"));
                    datosUsuario.add(rs.getString("direccion"));
                    datosUsuario.add(rs.getString("foto"));
                    datosUsuario.add(rs.getString("fecha_nacimiento"));
                    datosUsuario.add(rs.getString("estado"));
                    datosUsuario.add(rs.getString("id_sede"));
                    datosUsuario.add(rs.getString("tipo"));
                }   
                
        } catch (SQLException ex) {
            System.out.println("error al saar la información del resultset");
            
        }
        return datosUsuario;
    }

    String consultarSede(String id_vendedor) {
        String id_sede="";
 
               try {
                ResultSet rs = datos.consultarSede(id_vendedor);
                while(rs.next()){
                    id_sede = rs.getString("id_sede");
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al saar la información del resultset");
            
        }
        return id_sede;
    }

    ArrayList<String> listarCotizaciones(String cedula) {
        ArrayList<String> aux=new ArrayList();
        try {
                ResultSet rs = datos.listarCotizaciones(cedula);
                while(rs.next()){
                    aux.add(rs.getString("id_cotizacion"));
                    aux.add(rs.getString("fecha"));
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al sacar la información del resultset");
            
        }
        return aux;
    }

    ArrayList<String> listarCotizacionesfecha(String fecha) {
        ArrayList<String> aux=new ArrayList();
        try {
                ResultSet rs = datos.consultarCotizacionFecha(fecha);
                while(rs.next()){
                    
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al sacar la información del resultset");
            
        }
        return aux;
    }

    String consultarFoto(String id) {
        String ruta="";
        try {
                ResultSet rs = datos.consultarFoto(id);
                while(rs.next()){
                    ruta=rs.getString("foto");
                }    
                
        } catch (SQLException ex) {
            System.out.println("error al sacar la información del resultset");
            
        }
        return ruta;
    }
    
    
}

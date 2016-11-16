
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
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
               String em = respuesta.getString("id_usuario");
               info = em.split("\n");
               String pw = respuesta.getString("password");
               pass = pw.split("\n");   
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
        
    ResultSet consulta = datos.consultaUsuario(id);
    
         if (consulta.next()) {
             bool = true;
         } 
         
    return bool;
    
    }
}

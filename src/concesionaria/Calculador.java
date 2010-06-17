package concesionaria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
//import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import java.sql.*;

//Esta clase realiza lectura y escritura de archivos 
public class Calculador {

  //Estos HashMaps se poblaran inicialmente con contenidos de varios
  //archivo.txt
  private HashMap diccionario_vehiculos = new HashMap();
  //coeficientes.txt
  private HashMap diccionario_coeficientes = new HashMap();
  //Para la base de datos
  static String bd = "concesionaria";
  static String login = "root";
  static String password = "mariscos";
  static String url = "jdbc:mysql://localhost/" + bd;

  /*Este es el constructor. Es un metodo que se dispara automaticamente
  durante la instancia de cualquier objeto de tipo Calculador */
  public Calculador() {

    Connection conn = null;

    //recordar agregar Biblioteca para MySQL en el árbol del proyecto!
    try {
      try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
      } catch (InstantiationException ex) {
        Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
        Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
      }
      conn = DriverManager.getConnection(url, login, password);

      if (conn != null) {
        System.out.println("Conexión a base: " + url + " ... Ok");

        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM datos");

        //Debugging por abajo...
        System.out.println("\n\timpuestos \t utilidades \n");

        res.first();

        String id = res.getString("id");
        double impuestos = res.getDouble("impuestos");
        double utilidades = res.getDouble("utilidades");

        System.out.printf("%f - %f \n", impuestos, utilidades);
        //System.out.println(id + " \t " + impuestos.toString() + " \t " + utilidades.toString());

        //Ponemos (put) dentro de diccionario_vehiculos el valor que nos interesa.
        //Para encontrarlo mas tarde, le ponemos una palabra que lo identifique

        //Aqui es interesante observar el contenido del objeto diccionario_vehiculos usando
        //la ejecución Paso a Paso (F7)
        diccionario_vehiculos.put("transporte-impuestos", new Double(impuestos));
        diccionario_vehiculos.put("transporte-utilidades", new Double(utilidades));

        //debug
        //System.out.println("--------------"+diccionario_vehiculos.get("transporte-impuestos").toString());

        res.next();


        //Seguimos alimentando nuestro diccionario_vehiculos, con palabras nuevas
        impuestos = res.getDouble("impuestos");
        utilidades = res.getDouble("utilidades");

        diccionario_vehiculos.put("particulares-impuestos", impuestos);
        diccionario_vehiculos.put("particulares-utilidades", utilidades);

        res.next();
        impuestos = res.getDouble("impuestos");
        utilidades = res.getDouble("utilidades");

        diccionario_vehiculos.put("carga-impuestos", new Double(impuestos));
        diccionario_vehiculos.put("carga-utilidades", new Double(utilidades));


        res.close();
        stmt.close();

        // para cargar en la mysql el viejo coeficientes.txt
        /*
        LOAD DATA LOCAL INFILE '/tmp/phpyJiy7K' REPLACE INTO TABLE `coeficientes`
        FIELDS TERMINATED BY ',' ESCAPED BY '\\'  LINES TERMINATED BY '\n'
        # 63 fila(s) fueron afectadas.
         */

        stmt = conn.createStatement();
        res = stmt.executeQuery("SELECT * FROM coeficientes");

        //Debugging por abajo...
        System.out.println("\n\tAño \t coeficientes \n");

        res.first();


        while (res.next()) {
          int anio = res.getInt("anio");
          double coeficiente = res.getDouble("coeficiente");
          System.out.printf("#########%d - %f \n", anio, coeficiente);

          this.diccionario_coeficientes.put(new Integer(anio), new Double(coeficiente));
        }

        res.close();
        stmt.close();




        conn.close();
      }//fin if
    }//fin try
    catch (SQLException ex) {
      System.out.println(ex);
    }//fin catch
    catch (ClassNotFoundException ex) {
      System.out.println(ex);
      System.out.println("No encuentro el Conector JDBC");
    }//fin catch










  }//fin constructor

  /**
   * @return the diccionario_vehiculos
   */
  public HashMap getDiccionario_vehiculos() {
    return diccionario_vehiculos;
  }

  /**
   * @param diccionario_vehiculos the diccionario_vehiculos to set
   */
  public void setDiccionario_vehiculos(HashMap diccio) {
    //copio el nuevo diccionario sobre el viejo
    this.diccionario_vehiculos = diccio;

    //Actualizo el archivo.txt
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter("archivo.txt", false));

      out.write(this.diccionario_vehiculos.get("transporte-impuestos") + ","
              + this.diccionario_vehiculos.get("transporte-utilidades") + "\n");
      out.write(this.diccionario_vehiculos.get("particulares-impuestos") + ","
              + this.diccionario_vehiculos.get("particulares-utilidades") + "\n");
      out.write(this.diccionario_vehiculos.get("carga-impuestos") + ","
              + this.diccionario_vehiculos.get("carga-utilidades") + "\n");

      /*
      3010.3,41.5
      728.25,30.44
      2100.5,21.5
       */


      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


     Connection conn = null;

    //recordar agregar Biblioteca para MySQL en el árbol del proyecto!
    try {
      try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
      } catch (InstantiationException ex) {
        Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
        Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
      }
      conn = DriverManager.getConnection(url, login, password);

      if (conn != null) {
        System.out.println("Conexión a base: " + url + " ... Ok");

        //Borro los datos nuevos
        Statement borrar = conn.createStatement();
        int res = borrar.executeUpdate("TRUNCATE TABLE `concesionaria`.`datos`");

        //Inserto los modificados
        Statement nuevos = conn.createStatement();
        int resNuevos;
        resNuevos = nuevos.executeUpdate("INSERT INTO `concesionaria`.`datos` (`id` ,`impuestos` ,`utilidades`) VALUES (NULL , '"+ this.diccionario_vehiculos.get("transporte-impuestos") +"', '"+ this.diccionario_vehiculos.get("transporte-utilidades") +"')");
        resNuevos = nuevos.executeUpdate("INSERT INTO `concesionaria`.`datos` (`id` ,`impuestos` ,`utilidades`) VALUES (NULL , '"+ this.diccionario_vehiculos.get("particulares-impuestos") +"', '"+ this.diccionario_vehiculos.get("particulares-utilidades") +"')");
        resNuevos = nuevos.executeUpdate("INSERT INTO `concesionaria`.`datos` (`id` ,`impuestos` ,`utilidades`) VALUES (NULL , '"+ this.diccionario_vehiculos.get("carga-impuestos") +"', '"+ this.diccionario_vehiculos.get("carga-utilidades") +"')");

        nuevos.close();

        conn.close();
      }//fin if
    }//fin try
    catch (SQLException ex) {
      System.out.println(ex);
    }//fin catch
    catch (ClassNotFoundException ex) {
      System.out.println(ex);
      System.out.println("No encuentro el Conector JDBC");
    }//fin catch





    JOptionPane.showMessageDialog(null, "Valores actualizados");
  }

  public HashMap getDiccionario_coeficientes() {
    return diccionario_coeficientes;
  }
}//Fin class



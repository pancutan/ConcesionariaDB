package concesionaria;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ComboBoxModelFiltro extends AbstractListModel implements ComboBoxModel {

  static String bd = "concesionaria";
  static String login = "root";
  static String password = "mariscos";
  static String url = "jdbc:mysql://localhost/" + bd;
  //String[] items = { "Monitor", "Key Board", "Mouse", "Joy Stick", "Modem", "CD ROM",
  //"RAM Chip", "Diskette" };
  String[] items;
  String selection = null;

  public ComboBoxModelFiltro(String tabla, String campo) {

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
      conn = (Connection) DriverManager.getConnection(url, login, password);

      if (conn != null) {
        System.out.println("Conexión a base: " + url + " ... Ok");

        Statement stmt = (Statement) conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT " + campo + " FROM " + tabla);

        String itemCandidato;
        //Debugging por abajo...
        System.out.println("\n\t itemCandidato \n");



        String[] aux = new String[40];
        int i = 0;
        while (res.next()) {
          itemCandidato = res.getString(campo);

          System.out.printf("%s\n", itemCandidato);
          //rellenamos el string[] aux
          aux[i] = itemCandidato;
          //i nos servira para ajustar con exactitud el combo
          i++;

        }

        //reservamos lugar en items, de tamaño i
        this.items = new String[i];

        //se ve confuso, pero asi evito exceso de opciones en el combo
        int n = 0;
        while (n < i) { //copio en el string ajustado a la cantidad de respuestas del select, osea items
          this.items[n] = aux[n]; //solo los valores encontrados
          n++;
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




  }

  public Object getElementAt(int index) {
    return items[index];
  }

  public int getSize() {
    return items.length;
  }

  public void setSelectedItem(Object anItem) {
    selection = (String) anItem; // to select and register an
  } // item from the pull-down list

  // Methods implemented from the interface ComboBoxModelFiltro
  public Object getSelectedItem() {
    return selection; // to add the selection to the combo box
  }
}

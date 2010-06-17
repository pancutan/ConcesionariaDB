package reportes;

import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Explicación: Tomcat reconoce que log4j ha sido cargado, pero advierte que no hay archivo properties. No lo hay para Tomcat, sólo lo hemos puesto en nuestro contexto de aplicación. El mensaje es inofensivo, lo que se dice a continuación puede evitar esta advertencia.
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.*;

import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class Main {

  Connection conn = null;

  public void initConnection() {

    String HOST = "jdbc:mysql://localhost:3306/concesionaria";
    String USERNAME = "root";
    String PASSWORD = "mariscos";
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }

    try {
      conn = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void showReport() {

    //Path to your .jasper file in your package
    //String reportName = "id/hans/employee/report/EmployeeReport.jasper";
    String reportName = "reportes/Report3.jasper";

    //Get a stream to read the file
    InputStream is = this.getClass().getClassLoader().getResourceAsStream(reportName);
    if (is == null) {
      System.out.println("Archivo jasper no se encontró");
    }
    try {
      //Fill the report with parameter, connection and the stream reader
      JasperPrint jp = JasperFillManager.fillReport(is, null, conn);

      //Viewer for JasperReport
      JRViewer jv = new JRViewer(jp);

      //Insert viewer to a JFrame to make it showable
      JFrame jf = new JFrame();
      jf.getContentPane().add(jv);
      jf.validate();
      jf.setVisible(true);
      jf.setSize(new Dimension(800, 600));
      jf.setLocation(300, 100);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (JRException ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {

    Main ma = new Main();
    ma.initConnection();
    ma.showReport();
  }
}

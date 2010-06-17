package concesionaria;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

import javax.swing.JOptionPane;
/*
import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.  .sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
 */

public class GUI extends javax.swing.JFrame {

  Calculador calculadora = new Calculador();
  Double valorCoeficiente;
  DefaultListModel modelo = new DefaultListModel();
  DefaultListModel modeloFiltroComboTipo = new DefaultListModel();
  //ComboBoxModelFiltro comboBoxModelFiltroTipo = new ComboBoxModelFiltro();
  static String bd = "concesionaria";
  static String login = "root";
  static String password = "mariscos";
  static String url = "jdbc:mysql://localhost/" + bd;

  public GUI() {

    initComponents();


//##################### EMPIEZA ZONA DE FILTROS #########################3

    //cadena es una la concatenacion de las caracteristicas del vehiculo, para mostar en la JList
//      String cadena = marca + " " + color + ", modelo " + anioFabricac
    //            + ", a " + tipoCombustible + ", Px de fab: " + precFabrica;

    //  this.modelo.addElement(cadena);

    //muestro en consola, para debuggear nomas sin F4
    //System.out.println(cadena);


    /*
    this.modelo.addElement("ppp");
    this.modelo.addElement("ppp");
    this.modelo.addElement("ppp");
    this.modelo.addElement("ppp");
     */






//##################### FIN ZONA DE FILTROS #########################3
















    //Puebla arriba a la derecha, las cajas con valores obtenidos de los archivos

    //--------- COMIENZA LLENADO DE CAJAS SUPERIORES ----------------

    //Usa un metodo publico de tipo getter para recibir una copia del diccionario
    HashMap p = calculadora.getDiccionario_vehiculos();

    //Saca del diccionario el primer valor
    Double unDouble = (Double) p.get("transporte-impuestos");//ojo q es Double
    System.out.printf("=================%f", unDouble);
    //Lo convierte en una Cadena
    String unString = Double.toString(unDouble);
    //...Para poder mostrarlo en una caja de texto
    Caja1.setText(unString);


    //Saca del diccionario el segundo valor
    unDouble = (Double) p.get("transporte-utilidades");//ojo q es Double
    //Lo convierte en una Cadena
    unString = Double.toString(unDouble);
    //...Para poder mostrarlo en una caja de texto
    Caja2.setText(unString);

    //Saca del diccionario el tercer valor
    unDouble = (Double) p.get("particulares-impuestos");//ojo q es Double

    //Lo convierte en una Cadena
    unString = Double.toString(unDouble);

    //...Para poder mostrarlo en una caja de texto
    Caja3.setText(unString);

    //Saca del diccionario el cuarto valor
    unDouble = (Double) p.get("particulares-utilidades");//ojo q es Double

    //Lo convierte en una Cadena
    unString = Double.toString(unDouble);

    //...Para poder mostrarlo en una caja de texto
    Caja4.setText(unString);

    //Saca del diccionario el quinto valor
    unDouble = (Double) p.get("carga-impuestos");//ojo q es Double

    //Lo convierte en una Cadena
    unString = Double.toString(unDouble);

    //...Para poder mostrarlo en una caja de texto
    Caja5.setText(unString);

    //Saca del diccionario el sexto valor
    unDouble = (Double) p.get("carga-utilidades");//ojo q es Double

    //Lo convierte en una Cadena
    unString = Double.toString(unDouble);

    //...Para poder mostrarlo en una caja de texto
    Caja6.setText(unString);

    //--------- FIN LLENADO DE CAJAS SUPERIORES ----------------



    //~~~~~~~ COMIENZA LLENADO DE CAJA DE COEFICIENTE ~~~~~~~~~~

    //Saca del diccionario el primer valor
    //Double unDouble = (Double) p.get("transporte-impuestos");//ojo q es Double
    //System.out.printf("=================%f", unDouble);
    //Lo convierte en una Cadena
    //String unString = Double.toString(unDouble);



    //Usa un metodo publico de tipo getter para recibir una copia del diccionario
    HashMap q = calculadora.getDiccionario_coeficientes();

    //Buscando año en curso
    Calendar ahora = Calendar.getInstance();
    Integer anio = ahora.get(Calendar.YEAR);

    //Seteando año en curso sobre el Label "Anio"
    Anio.setText(anio.toString());

    //Buscando en el diccionario q, el valorCoeficiente. Para lograrlo, buscamos entre
    //las llaves, una que se denomine 2010 

    //Lo dejo como atributo, ya que será de utilidad cuando pulsen el valor Cotizar
    //valorCoeficiente será una expresión al estilo 0.0046
    valorCoeficiente = (Double) q.get(anio);


    //Seteando valorCoeficiente que corresponde al año en curso
    Coeficiente.setText(valorCoeficiente.toString());

    /*
    //Saca del diccionario el primer valor
    Double otroDouble = (Double) p.get("transporte-impuestos");//ojo q es Double
    //Lo convierte en una Cadena
    String unString = Double.toString(unDouble);
    //...Para poder mostrarlo en una caja de texto
    Caja1.setText(unString);
     */

    //~~~~~~~ FIN LLENADO DE CAJA DE COEFICIENTE ~~~~~~~~~~


    //######## COMIENZA LLENADO DE JLists con Vehiculos ###############

//    Vector<TransporteCarga> v = (Vector)calculadora.getVector();



    //######## FIN LLENADO DE JLists con Vehiculos ####################


    this.setVisible(true);
  }//Constructor

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

    jLabel1 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    Caja1 = new javax.swing.JTextField();
    Caja3 = new javax.swing.JTextField();
    Caja2 = new javax.swing.JTextField();
    Caja4 = new javax.swing.JTextField();
    Caja5 = new javax.swing.JTextField();
    Caja6 = new javax.swing.JTextField();
    jLabel7 = new javax.swing.JLabel();
    Anio = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    Coeficiente = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    CajaValorReajuste = new javax.swing.JTextField();
    CajaCuotas = new javax.swing.JTextField();
    BotonCotizar = new javax.swing.JButton();
    jScrollPane3 = new javax.swing.JScrollPane();
    Lista3 = new javax.swing.JList();
    jLabel9 = new javax.swing.JLabel();
    EtiquetaSeguro = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    EtiquetaVehiculoElegido = new javax.swing.JLabel();
    EtiquetaPrecioVenta = new javax.swing.JLabel();
    jLabel15 = new javax.swing.JLabel();
    EtiquetaValordeCuota = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jLabel12 = new javax.swing.JLabel();
    jLabel13 = new javax.swing.JLabel();
    jComboBoxTipo = new javax.swing.JComboBox();
    jLabel14 = new javax.swing.JLabel();
    jComboBoxMarca = new javax.swing.JComboBox();
    jButtonBuscar = new javax.swing.JButton();
    jLabel16 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jLabel17 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jLabel1.setText("Valores generales");

    jButton1.setText("Actualizar");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    jLabel3.setText("Impuesto");

    jLabel4.setText("Utilidad");

    jLabel7.setText("Coeficiente correspondiente al año en curso:");

    Anio.setText("jLabel8");

    jLabel8.setText("->");

    Coeficiente.setText("jLabel9");

    jLabel2.setText("Escoja vehiculo de nuestro stock:");

    jLabel5.setText("Valor de Reajuste");

    jLabel6.setText("Cuotas");

    CajaCuotas.setText("1");

    BotonCotizar.setText("Cotizar");
    BotonCotizar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        BotonCotizarActionPerformed(evt);
      }
    });

    Lista3.setModel(modelo);
    Lista3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane3.setViewportView(Lista3);

    jLabel9.setText("Seguro:");

    jLabel10.setText("Precio de Venta:");

    EtiquetaVehiculoElegido.setBackground(new java.awt.Color(217, 204, 214));

    org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, Lista3, org.jdesktop.beansbinding.ELProperty.create("${selectedElement}"), EtiquetaVehiculoElegido, org.jdesktop.beansbinding.BeanProperty.create("text"));
    bindingGroup.addBinding(binding);

    jLabel15.setText("Valor de la cuota:");

    jLabel11.setText("Transporte");

    jLabel12.setText("Particular");

    jLabel13.setText("Carga");

    jComboBoxTipo.setModel(new concesionaria.ComboBoxModelFiltro("tipos", "tipo"));

    jLabel14.setText("Filtrar");

    jComboBoxMarca.setModel(new concesionaria.ComboBoxModelFiltro("marcas","marca"));
    jComboBoxMarca.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBoxMarcaActionPerformed(evt);
      }
    });

    jButtonBuscar.setText("Buscar");
    jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonBuscarActionPerformed(evt);
      }
    });

    jLabel16.setText("Pagar hasta:");

    jLabel17.setText("Disponibles:");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2)
                .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabel11)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Caja1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(12, 12, 12)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(Caja2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jButton1)))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6))
                      .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                      .addComponent(jLabel10)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CajaCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CajaValorReajuste, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EtiquetaPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(BotonCotizar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
              .addComponent(jLabel7)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(Anio)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(jLabel8)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(Coeficiente)))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel12)
              .addComponent(jLabel13))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(Caja3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(Caja5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(Caja6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(Caja4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(jLabel15)
              .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(EtiquetaSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(EtiquetaValordeCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(7, 7, 7))
          .addComponent(EtiquetaVehiculoElegido, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel14)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jComboBoxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel16)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButtonBuscar))
          .addComponent(jLabel17)
          .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))
        .addContainerGap())
    );

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Caja1, Caja2, Caja3, Caja4, Caja5, Caja6, jButton1, jLabel3});

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CajaCuotas, CajaValorReajuste});

    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel7)
          .addComponent(Anio)
          .addComponent(jLabel8)
          .addComponent(Coeficiente))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BotonCotizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addComponent(jLabel1))
        .addGap(14, 14, 14)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(jLabel5)
          .addComponent(CajaValorReajuste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(jLabel4)
          .addComponent(jLabel6)
          .addComponent(CajaCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(EtiquetaPrecioVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
          .addComponent(Caja1)
          .addComponent(Caja2)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jLabel11)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel12)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(Caja3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Caja4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel9))
          .addComponent(EtiquetaSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(6, 6, 6)
            .addComponent(jLabel13))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(Caja5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Caja6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
          .addComponent(EtiquetaValordeCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addComponent(EtiquetaVehiculoElegido, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel14)
          .addComponent(jComboBoxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel16)
          .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jButtonBuscar))
        .addGap(20, 20, 20)
        .addComponent(jLabel17)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Caja1, Caja2, Caja3, Caja4, Caja5, Caja6});

    bindingGroup.bind();

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      //Un HashMap auxiliar donde guardar todas las cajas de texto
      HashMap aux = new HashMap();

      //Rellenando el diccionario auxiliar...
      aux.put("transporte-impuestos", new Double(Caja1.getText()));
      aux.put("transporte-utilidades", new Double(Caja2.getText()));
      aux.put("particulares-impuestos", new Double(Caja3.getText()));
      aux.put("particulares-utilidades", new Double(Caja4.getText()));
      aux.put("carga-impuestos", new Double(Caja5.getText()));
      aux.put("carga-utilidades", new Double(Caja6.getText()));

      calculadora.setDiccionario_vehiculos(aux);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void BotonCotizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCotizarActionPerformed

      //el operador ha ingresado lo necesario?
      if (Lista3.isSelectionEmpty()) {
        JOptionPane.showMessageDialog(null, "Debe escoger un vehiculo antes de comenzar la cotización");
      }

      if (CajaValorReajuste.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Indique un valor de reajuste");
      }



      /* Segun postulado:
       * "Cualquiera sea el tipo de vehículo, el calculo del precio de venta se realiza
       * mediante el siguiente método:
       *
       * Precio Venta = Precio de Fabrica * 1.21 + Utilidad + Impuestos"
       */

      /* Busco el precio de fabrica a partir de la etiqueta EtiquetaVehiculoElegido
       * y lo convierto a double para trabajarlo enseguida
       */

      String PrecioFabricaAConvertir = EtiquetaVehiculoElegido.getText();

      //Recordar postulado: Use control de excepciones ante posibles errores

      try {
        String[] PrecioFabricaAConvertirEnPedacitos = PrecioFabricaAConvertir.split(":");

        double pxFabrica = Double.valueOf(PrecioFabricaAConvertirEnPedacitos[1]).doubleValue();

        double utilidad = Double.valueOf(Caja6.getText()).doubleValue();
        double impuestos = Double.valueOf(Caja5.getText()).doubleValue();

        double pxVenta = pxFabrica * 1.21 + utilidad + impuestos;

        //Lo dejamos en dos decimales... no sea que la multiplicacion arroje cualquier cosa
        DecimalFormat formateador = new DecimalFormat("####.##");

        EtiquetaPrecioVenta.setText(formateador.format(pxVenta));


        /* Además cada vez que se vende un vehículo este debe ser asegurado.
         * Para calcular el valor del seguro se emplea el siguiente método:
         * Seguro = Precio de venta * Coeficiente (registrado en un archivo y que
         * varia según el año de fabricación del vehículo) + reajuste (ingresado
         * por el operador en el momento de realizar el cálculo.
         */

        double reajuste = Double.valueOf(CajaValorReajuste.getText()).doubleValue();

        Double seguro = pxVenta * valorCoeficiente + reajuste;
        EtiquetaSeguro.setText(formateador.format(seguro));

        /*
         * Si el vehículo de vende financiado, el valor de la cuota se calcula
         * mediante el siguiente método:
         * Cuota = (Precio de venta  + Seguro) * Interés Anual / Número de Cuotas
         *
         * "Interes Anual" es el coeficiente anual correspondiente al año en curso ?         *
         */
        double cuotas = Double.valueOf(CajaCuotas.getText()).doubleValue();


        Double interesAnual = 1.002;
        //double cuota = pxVenta + seguro * valorCoeficiente / cuotas;
        //             (200000  +  2000)   * 1.002 / 10

        double cuota = (pxVenta + seguro) * interesAnual / cuotas;

        EtiquetaValordeCuota.setText(formateador.format(cuota));



      } catch (java.lang.NumberFormatException e) {
        System.out.println("La caja conteniendo el valor de reajuste debe ser llenada\n"
                + " Caso contrario, atrapamos la excepción: " + e.getMessage());

      } catch (java.lang.NullPointerException e) {
        System.out.println("Atrapando excepción: si la Lista de vehiculos no ha sido seleccionada, no \n"
                + "hay manera de calcular el px de venta ");
      }


    }//GEN-LAST:event_BotonCotizarActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed

      //Limpia el modelo (que controla por dentro a Lista3), es para que no se sumen
      //mas autos si se vuelve a hacer click en otra seleccion
      modelo.clear();

      Connection conne = null;

      //recordar agregar Biblioteca para MySQL en el árbol del proyecto!
      try {
        try {
          Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException ex) {
          Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
          Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
        }
        conne = (Connection) DriverManager.getConnection(url, login, password);

        // ================ OBTENIENDO VALOR DEL PRIMER FILTRO ==================================
        System.out.println("jComboBoxTipo ha seleccionado: " + jComboBoxTipo.getSelectedItem().toString());

        String primerFiltro = jComboBoxTipo.getSelectedItem().toString(); //ejemplo: particular


        System.out.println("Conexión a base: " + url + " ... Ok");

        Statement stmt2 = (Statement) conne.createStatement();
        String sql = "SELECT * FROM `tipos` WHERE `tipos`.`tipo` LIKE '" + primerFiltro + "'";
        System.out.println("sql: " + sql);
        ResultSet res = stmt2.executeQuery(sql);

        res.first();

        Integer tipo_id = res.getInt("id");

        System.out.println("tipo_id es " + tipo_id);

        // ================ FIN OBTENIENDO VALOR DEL PRIMER FILTRO ==================================


        // ================ OBTENIENDO VALOR DEL SEGUNDO FILTRO ==================================       

        System.out.println("jComboBoxMarca ha seleccionado: " + jComboBoxMarca.getSelectedItem().toString());

        String segundoFiltro = jComboBoxMarca.getSelectedItem().toString(); //ejemplo: particular

        stmt2 = (Statement) conne.createStatement();
        sql = "SELECT * FROM `marcas` WHERE `marcas`.`marca` LIKE '" + segundoFiltro + "'";
        System.out.println("sql: " + sql);
        res = stmt2.executeQuery(sql);

        res.first();//SI SOLO NECESITAMOS UN VALOR, DEBEMOS POSICIONAR EL PUNTERO EN first!!!!

        Integer marca_id = res.getInt("id");

        System.out.println("marca_id es " + marca_id);
        // ================ FIN OBTENIENDO VALOR DEL SEGUNDO FILTRO ==================================

        // ================ OBTENIENDO VALOR DEL TERCER FILTRO ==================================
        //to Do..
        // ================ FIN OBTENIENDO VALOR DEL TERCER FILTRO ==================================


        // ================ COMPAGINACION FINAL ==================================

        Statement stmt4 = (Statement) conne.createStatement();

        sql = "SELECT *  FROM `stock` WHERE `tipo_id` = '" + tipo_id
                + "' AND `marca_id` = '" + marca_id + "'";
        System.out.println("sql: " + sql);
        ResultSet res4 = stmt4.executeQuery(sql);
        //res4.first();

        int i = 0;
        while (res4.next()) {
          //Cual color?
          Statement stmt5 = (Statement) conne.createStatement();
          sql = "SELECT *  FROM `colores` WHERE `id` = '" + res4.getInt("color_id") + "'";
          System.out.println("sql: " + sql);
          ResultSet res5 = stmt5.executeQuery(sql);
          res5.first();

          //Cual combustible?
          Statement stmt6 = (Statement) conne.createStatement();
          sql = "SELECT *  FROM `combustibles` WHERE `id` = '" + res4.getInt("combustible_id") + "'";
          System.out.println("sql: " + sql);
          ResultSet res6 = stmt6.executeQuery(sql);
          res6.first();

          //Compaginando todo
          String item = "Transporte " + primerFiltro + " " + segundoFiltro + " " + res5.getString("color")
                  + ", fabricado en " + res4.getInt("anioFabricac") + ", a " + res6.getString("combustible")
                  + ", " + res4.getInt("cantidadEnStock")
                  + " en stock, Px de Fabrica: " + res4.getDouble("precFabrica");

          System.out.println("Agregando item al Model: " + item);

          this.modelo.addElement(item);
          i++;
        }

        if (i == 0) {
          this.modelo.addElement("Intente con otros valores");
          //EtiquetaVehiculoElegido.setText("Intente con otros valores");
          System.out.println("Intente con otros valores");
        }



        /*Ejemplo
        this.modelo.addElement("ppp");
        this.modelo.addElement("ppp");
        this.modelo.addElement("ppp");*/


        Lista3.setModel(modelo);

        res.close();
        stmt2.close();





        conne.close();

      }//fin try
      catch (SQLException ex) {
        System.out.println(ex);
        System.out.println(ex.getCause());
      }//fin catch
      catch (ClassNotFoundException ex) {
        System.out.println(ex);
        System.out.println("No encuentro el Conector JDBC");
      }//fin catch



    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jComboBoxMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMarcaActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMarcaActionPerformed

  //señores, el main, por si lo desea ejecutar desde aqui en lugar desde el Main.java
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new GUI().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel Anio;
  private javax.swing.JButton BotonCotizar;
  private javax.swing.JTextField Caja1;
  private javax.swing.JTextField Caja2;
  private javax.swing.JTextField Caja3;
  private javax.swing.JTextField Caja4;
  private javax.swing.JTextField Caja5;
  private javax.swing.JTextField Caja6;
  private javax.swing.JTextField CajaCuotas;
  private javax.swing.JTextField CajaValorReajuste;
  private javax.swing.JLabel Coeficiente;
  private javax.swing.JLabel EtiquetaPrecioVenta;
  private javax.swing.JLabel EtiquetaSeguro;
  private javax.swing.JLabel EtiquetaValordeCuota;
  private javax.swing.JLabel EtiquetaVehiculoElegido;
  private javax.swing.JList Lista3;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButtonBuscar;
  private javax.swing.JComboBox jComboBoxMarca;
  private javax.swing.JComboBox jComboBoxTipo;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel16;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JTextField jTextField1;
  private org.jdesktop.beansbinding.BindingGroup bindingGroup;
  // End of variables declaration//GEN-END:variables
}

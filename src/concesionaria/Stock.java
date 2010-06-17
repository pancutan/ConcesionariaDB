package concesionaria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Stock {

    //stock.txt
    //private HashMap diccionario_stock = new HashMap();
    //Para manejar vectores con objetos:
    //http://www.forosdelweb.com/f45/array-objetos-488424/
    //Arreglo de objetos de tipo Transporte 
    private Vector<Transporte> vectorCarga = new Vector<Transporte>();

    private void llenaVector(Transporte tc) {
        this.vectorCarga.addElement(tc);
    }

    public Vector<Transporte> getVector() {
        return vectorCarga;
    }


    public String[] getArregloCarga() {

        String [] arreglo = new String[9];

        for (int i = 0; i < vectorCarga.size(); i++) {
            //System.out.println(vectorCarga.firstElement().marca.toString());
            System.out.println(vectorCarga.elementAt(i).marca.toString());
            //Object <vectorCarga> o =<vectorCarga> vectorCarga.elementAt(i);
            String s = (String) vectorCarga.elementAt(i).getMarca();

            arreglo[i] = (String) vectorCarga.elementAt(i).getMarca();
            System.out.println(arreglo[i]);

        }
      return arreglo;




    }

    public void recorreVector() {

        for (int i = 0; i < vectorCarga.size(); i++) {
            //System.out.println(vectorCarga.firstElement().marca.toString());
            System.out.println(vectorCarga.elementAt(i).marca.toString());
            //Object <vectorCarga> o =<vectorCarga> vectorCarga.elementAt(i);
            String s = (String) vectorCarga.elementAt(i).getMarca();
            System.out.println(s);

        }

        /*
        
        // aqui sacamos el objeto que se encuantra en el indice o posicion i-esima
        Object objeto = vectorCarga.elementAt(i);

        // si queremos convertirlo a algun tipo, nos toca hacer un casting (Tipo), asi:
        String str = (String) objeto; // ojo! esto solo lo puedes hacer si agregaste objetos de tipo String en el vector
        objeto.getMarca();
        }*/
    }

    public Stock() {



        /* ###############################################################
         * Comienza lectura del archivo que contiene el stock
         * ###############################################################
         * Cuidado: este archivo, llamado stock.txt debe esta ubicado en
         * la raiz del proyecto, no en src/concesionaria
         *
         * Contiene una información del tipo
         *
         * particular,fox,azul,2003,nafta,50000,10
         * particular,palio,rojo,2005,nafta,45000,18
         * pasajero,traffic,blanco,2001,gasoil,62000,5
         * pasajero,nissan,crema,2002,gasoil,68000,4
         * carga,scania,negro,2008,gasoil,200000,3
         * carga,ford,blanco,2009,gasoil,180000,4
         * ...
         * etc
         */


        File archivo_stock = null;
        FileReader fr3 = null;
        BufferedReader br3 = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo_stock = new File("stock.txt");
            fr3 = new FileReader(archivo_stock);
            br3 = new BufferedReader(fr3);

            //Linea leera lineas completas
            String linea;

            //enPedacitos recibirá las partes de esa linea que esten entre ,
            String[] enPedacitos = null;

            /*
             * Cualquiera sea el tipo de vehículo, el calculo del precio de venta se realiza mediante el siguiente método:
            Precio Venta = Precio de Fabrica * 1.21 + Utilidad + Impuestos

            Los impuestos  se obtienen de una tabla (archivo.txt) que contiene los valores correspondientes a cada tipo de vehículo. Estos valores deben poder modificarse si es necesario.

            Las utilidades  se obtienen de una tabla (archivo.txt) que contiene los valores correspondientes a cada marca de vehículo. Estos valores deben poder modificarse si es necesario.
             */


            // Lectura del archivo:
            //-----------------------------------------------------------------
            //Recorremos todo el archivo, mientras sea distinto de nulo
            while ((linea = br3.readLine()) != null) {
                enPedacitos = linea.split(",");

                //this.diccionario_coeficientes.put(new Integer(enPedacitos[0]), new Integer(enPedacitos[1]));

                //en lugar de usar un hashmap usaremos un array de objetos.
                //He creado algunos setters en Transporte.java

                //if (enPedacitos[0].equals("carga")) {

                    Transporte unTransporte = new Transporte();

                    //Relleno el objeto con los valores del archivo

                    unTransporte.setMarca(enPedacitos[1]);
                    unTransporte.setColor(enPedacitos[2]);

                    int aux = Integer.parseInt(enPedacitos[3]);
                    unTransporte.setAnioFabricac(aux);

                    unTransporte.setTipoCombustible(enPedacitos[4]);

                    double aux2 = Double.parseDouble(enPedacitos[5]);
                    unTransporte.setPrecFabrica(aux2);

                    int aux3 = Integer.parseInt(enPedacitos[6]);
                    unTransporte.setCantidadEnStock(aux3);

                    //por fin el objeto unTransporte esta lleno: a mandarlo al Vector!
                    this.llenaVector(unTransporte);

                    //ACA ME QUEDÉ:
                    //RELLENAR LOS OTROS VECTORES
                    //POBLAR LAS JLists con los objetos


                //}

            }
            //Pongo a salvo el archivo contra cuelgues o caidas del sistema.
            //Si toda su información esta en RAM, no tiene sentido que siga abierto
            fr3.close();
            br3.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * ###############################################################
         * FIN lectura del archivo que contiene el stock
         * ###############################################################
         */





    }

    //para probar nomas
    public static void main(String[] args) {

        //esto ok
        //new Stock().recorreVector();

        Stock unStock = new Stock();
        Vector v = unStock.getVector();


        for (int i = 0; i < v.size(); i++) {
            TransporteCarga aux = (TransporteCarga) v.elementAt(i);
            String s = (String) aux.getMarca();
            System.out.println(s);
            //Object o = (TransporteCarga) v.elementAt(i);
            //System.out.println(o.getMarca().toString);


            //System.out.println(vectorCarga.firstElement().marca.toString());
            //System.out.println(v.elementAt(i).marca.toString());
            //Object <vectorCarga> o =<vectorCarga> vectorCarga.elementAt(i);
            //String s = (String)v.elementAt(i).getMarca();
            //System.out.println(s);
            // v.elementAt(i).

        }



    }
}

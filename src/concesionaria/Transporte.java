package concesionaria;

public class Transporte {

  String marca;
  String color;
  int anioFabricac;
  String tipoCombustible;
  double precFabrica;
  int cantidadEnStock;

  public int getAnioFabricac() {
    return anioFabricac;
  }

  public void setAnioFabricac(int anioFabricac) {
    this.anioFabricac = anioFabricac;
  }

  public int getCantidadEnStock() {
    return cantidadEnStock;
  }

  public void setCantidadEnStock(int cantidadEnStock) {
    this.cantidadEnStock = cantidadEnStock;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public double getPrecFabrica() {
    return precFabrica;
  }

  public void setPrecFabrica(double precFabrica) {
    this.precFabrica = precFabrica;
  }

  public String getTipoCombustible() {
    return tipoCombustible;
  }

  public void setTipoCombustible(String tipoCombustible) {
    this.tipoCombustible = tipoCombustible;
  }


  
}

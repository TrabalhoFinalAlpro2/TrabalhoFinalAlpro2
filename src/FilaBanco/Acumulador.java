/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilaBanco;



/*
 * Classe utilitaria que realiza calculos de media aritmetica
 */
public class Acumulador {
  private double valor;
  private int contador;
  public Acumulador() {
    valor = 0;
    contador = 0;
  }
  public double getValor() {
    return(valor);
  }
  public int getContagem() {
    return contador;
  }
  public void adicionar(double n) {
    valor = valor + n;
    contador++;
  }
  public void adicionar(int n) {
    valor = valor + n;
    contador++;
  }
  public double getMedia() {
    if(contador != 0)
       return valor/contador;
    else
       return 0;
  }
}

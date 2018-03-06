package uk.ac.imperial.matrixmult;

public class MultiplyRow extends Thread{

  int i;
  double[][] c;
  Matrix a;
  Matrix b;

  public MultiplyRow(int i, double[][] c, Matrix a, Matrix b) {
    this.i = i;
    this.c = c;
    this.a = a;
    this.b = b;
  }

  @Override
  public void run() {
    for (int j = 0; j < b.getNumColumns(); j++) { // bColumn
      for (int k = 0; k < a.getNumColumns(); k++) { // aColumn
        c[i][j] += a.get(i,k) * b.get(k,j);
      }
    }
  }
}

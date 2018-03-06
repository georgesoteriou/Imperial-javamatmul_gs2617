package uk.ac.imperial.matrixmult;

public class MultiplyOne extends Thread{
  int i;
  int j;
  double[][] c;
  Matrix a;
  Matrix b;

  public MultiplyOne(int i, int j, double[][] c, Matrix a, Matrix b) {
    this.i = i;
    this.j = j;
    this.c = c;
    this.a = a;
    this.b = b;
  }

  @Override
  public void run() {
    for (int k = 0; k < a.getNumColumns(); k++) { // aColumn
      c[i][j] += a.get(i,k) * b.get(k,j);
    }
  }
}

package uk.ac.imperial.matrixmult;

public class MultiplyRow extends Thread{
  double[][] c;
  Matrix a;
  Matrix b;
  int start;
  int end;

  public MultiplyRow(int start, int end, double[][] c, Matrix a, Matrix b) {
    this.c = c;
    this.a = a;
    this.b = b;
    this.start = start;
    if(end > a.getNumRows()) {
      this.end = a.getNumRows();
    }else{
      this.end = end;
    }
  }

  @Override
  public void run() {
    for (int i = start; i < end; i++) { //aRows
      for (int k = 0; k < a.getNumColumns(); k++) { // aColumn
        for (int j = 0; j < b.getNumColumns(); j++) { // bColumn
          c[i][j] += a.get(i, k) * b.get(k, j);
        }
      }
    }
  }
}

package uk.ac.imperial.matrixmult;

import java.util.Arrays;

public class MatrixMultiplier {

  public static Matrix multiply(Matrix a, Matrix b) throws Exception {
    if(a.getNumColumns() == b.getNumRows()){
      double[][] c = new double[a.getNumRows()][b.getNumColumns()];

      MultiplyRow[] rowThreads = new MultiplyRow[a.getNumRows()];


      for (int i = 0; i < a.getNumRows(); i++) { // aRow
        rowThreads[i] = new MultiplyRow(i, c, a, b);
      }

      Arrays.stream(rowThreads).forEach(Thread::start);

      Arrays.stream(rowThreads).forEach(u -> {
        try {
          u.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });


      return new MyMatrix(c);
    }else {
      throw new Exception();
    }
  }
  
}

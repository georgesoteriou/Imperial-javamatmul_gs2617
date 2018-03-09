package uk.ac.imperial.matrixmult;

import static sun.swing.MenuItemLayoutHelper.max;

import java.util.Arrays;

public class MatrixMultiplier {
  private static final int NUMBEROFTHREADS = 12;
  public static Matrix multiply(Matrix a, Matrix b) throws Exception {

    if(a.getNumColumns() == b.getNumRows()){
      double[][] c = new double[a.getNumRows()][b.getNumColumns()];

      MultiplyRow[] rowThreads = new MultiplyRow[NUMBEROFTHREADS];

      int RowsPerThread = max(a.getNumRows()/NUMBEROFTHREADS, 2);

      for (int i = 0; i < NUMBEROFTHREADS; i++) {
        rowThreads[i] = new MultiplyRow(i*RowsPerThread, RowsPerThread + i*RowsPerThread, c, a, b);
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

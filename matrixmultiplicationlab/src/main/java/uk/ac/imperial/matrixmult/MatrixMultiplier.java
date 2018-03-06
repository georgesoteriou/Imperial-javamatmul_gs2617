package uk.ac.imperial.matrixmult;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MatrixMultiplier {

  public static Matrix multiply(Matrix a, Matrix b) throws Exception {
    if(a.getNumColumns() == b.getNumRows()){

      return new MyMatrix(Arrays.stream(a.getMatrix()).parallel()
              .map(row -> IntStream.range(0, b.getNumColumns())
                      .mapToDouble(i -> IntStream.range(0, b.getNumRows())
                              .mapToDouble(j -> row[j] * b.get(j,i)).sum())
                      .toArray())
              .toArray(double[][]::new));

    }else {
      throw new Exception();
    }
  }
  
}

package uk.ac.imperial.matrixmult;

import java.io.BufferedReader;
import java.io.FileReader;

public class MatrixBuilder {


  public static Matrix build(double[][] source) {
    return new MyMatrix(source);
  }

  public static Matrix build(int nRows, int nCols) {
    return new MyMatrix(nRows, nCols);
  }

}

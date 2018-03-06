package uk.ac.imperial.matrixmult;


import static uk.ac.imperial.matrixmult.Multiply.strassen;

public class MatrixMultiplier {

  public static Matrix multiply(Matrix a, Matrix b) throws Exception {
    if(a.getNumColumns() == b.getNumRows()){
      return new MyMatrix(strassen(a,b));
    }else {
      throw new Exception();
    }
  }
  
}

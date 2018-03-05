package uk.ac.imperial.matrixmult;

public class MatrixMultiplier {

  public static Matrix multiply(Matrix a, Matrix b) throws Exception {
    if(a.getNumColumns() == b.getNumRows()){
      double[][] c = new double[a.getNumRows()][b.getNumColumns()];

      for (int i = 0; i < a.getNumRows(); i++) { // aRow
        for (int j = 0; j < b.getNumColumns(); j++) { // bColumn
          for (int k = 0; k < a.getNumColumns(); k++) { // aColumn
            c[i][j] += a.get(i,k) * b.get(k,j);
          }
        }
      }

      return new MyMatrix(c);
    }else {
      throw new Exception();
    }
  }
  
}

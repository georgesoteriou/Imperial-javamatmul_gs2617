package uk.ac.imperial.matrixmult;

import java.util.Arrays;

public class MyMatrix implements Matrix {
    int nRows;
    int nCols;
    public double[][] matrix;

    MyMatrix(int nRows, int nCols){
        this.nRows = nRows;
        this.nCols = nCols;
        this.matrix = new double[nRows][nCols];
    }

    MyMatrix(double[][] source){
        this.matrix = source;
        this.nRows = source.length;
        this.nCols = source[0].length;
    }

    @Override
    public double get(int row, int column) {
        return matrix[row][column];
    }

    @Override
    public void set(int row, int column, double value) {
        matrix[row][column] = value;
    }

    @Override
    public int getNumRows() {
        return nRows;
    }

    @Override
    public int getNumColumns() {
        return nCols;
    }
}

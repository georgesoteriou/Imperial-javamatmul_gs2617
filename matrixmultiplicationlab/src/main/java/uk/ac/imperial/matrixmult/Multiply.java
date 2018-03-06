package uk.ac.imperial.matrixmult;

import java.util.ArrayList;

import static java.lang.Math.max;


public class Multiply extends Thread {

    static int LEAF_SIZE = 128;

    public static double[][] ikjAlgorithm(double[][] A, double[][] B) {
        int n = A.length;

        // initialise C
        double[][] C = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    private static double[][] add(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    private static double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    private static int nextPowerOfTwo(int n) {
        int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, log2);
    }

    public static double[][] strassen(Matrix A,Matrix B) {
        // Make the matrices bigger so that you can apply the strassen
        // algorithm recursively without having to deal with odd
        // matrix sizes

        int m = max(
                    max(
                        nextPowerOfTwo(A.getNumRows()),
                        nextPowerOfTwo(A.getNumColumns())),
                    max(
                        nextPowerOfTwo(B.getNumRows()),
                        nextPowerOfTwo(B.getNumColumns()))
                );

        double[][] APrep = new double[m][m];
        double[][] BPrep = new double[m][m];

        for (int i = 0; i < A.getNumRows(); i++) {
            for (int j = 0; j < A.getNumColumns(); j++) {
                APrep[i][j] = A.get(i,j);
            }
        }

        for (int i = 0; i < B.getNumRows(); i++) {
            for (int j = 0; j < B.getNumColumns(); j++) {
                BPrep[i][j] = B.get(i,j);
            }
        }

        double[][] CPrep = strassenR(APrep, BPrep);
        double[][] C = new double[A.getNumRows()][B.getNumColumns()];
        for (int i = 0; i < A.getNumRows(); i++) {
            System.arraycopy(CPrep[i], 0, C[i], 0, B.getNumColumns());
        }
        return C;
    }

    private static double[][] strassenR(double[][] A, double[][] B) {
        int n = A.length;

        if (n <= LEAF_SIZE) {
            return ikjAlgorithm(A, B);
        } else {
            // initializing the new sub-matrices
            int newSize = n / 2;
            double[][] a11 = new double[newSize][newSize];
            double[][] a12 = new double[newSize][newSize];
            double[][] a21 = new double[newSize][newSize];
            double[][] a22 = new double[newSize][newSize];

            double[][] b11 = new double[newSize][newSize];
            double[][] b12 = new double[newSize][newSize];
            double[][] b21 = new double[newSize][newSize];
            double[][] b22 = new double[newSize][newSize];

            double[][] aResult;
            double[][] bResult;

            // dividing the matrices in 4 sub-matrices:
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    a11[i][j] = A[i][j]; // top left
                    a12[i][j] = A[i][j + newSize]; // top right
                    a21[i][j] = A[i + newSize][j]; // bottom left
                    a22[i][j] = A[i + newSize][j + newSize]; // bottom right

                    b11[i][j] = B[i][j]; // top left
                    b12[i][j] = B[i][j + newSize]; // top right
                    b21[i][j] = B[i + newSize][j]; // bottom left
                    b22[i][j] = B[i + newSize][j + newSize]; // bottom right
                }
            }

            // Calculating p1 to p7:
            aResult = add(a11, a22);
            bResult = add(b11, b22);
            double[][] p1 = strassenR(aResult, bResult);
            // p1 = (a11+a22) * (b11+b22)

            aResult = add(a21, a22); // a21 + a22
            double[][] p2 = strassenR(aResult, b11); // p2 = (a21+a22) * (b11)

            bResult = subtract(b12, b22); // b12 - b22
            double[][] p3 = strassenR(a11, bResult);
            // p3 = (a11) * (b12 - b22)

            bResult = subtract(b21, b11); // b21 - b11
            double[][] p4 = strassenR(a22, bResult);
            // p4 = (a22) * (b21 - b11)

            aResult = add(a11, a12); // a11 + a12
            double[][] p5 = strassenR(aResult, b22);
            // p5 = (a11+a12) * (b22)

            aResult = subtract(a21, a11); // a21 - a11
            bResult = add(b11, b12); // b11 + b12
            double[][] p6 = strassenR(aResult, bResult);
            // p6 = (a21-a11) * (b11+b12)

            aResult = subtract(a12, a22); // a12 - a22
            bResult = add(b21, b22); // b21 + b22
            double[][] p7 = strassenR(aResult, bResult);
            // p7 = (a12-a22) * (b21+b22)

            // calculating c21, c21, c11 e c22:
            double[][] c12 = add(p3, p5); // c12 = p3 + p5
            double[][] c21 = add(p2, p4); // c21 = p2 + p4

            aResult = add(p1, p4); // p1 + p4
            bResult = add(aResult, p7); // p1 + p4 + p7
            double[][] c11 = subtract(bResult, p5);
            // c11 = p1 + p4 - p5 + p7

            aResult = add(p1, p3); // p1 + p3
            bResult = add(aResult, p6); // p1 + p3 + p6
            double[][] c22 = subtract(bResult, p2);
            // c22 = p1 + p3 - p2 + p6

            // Grouping the results obtained in a single matrix:
            double[][] C = new double[n][n];
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    C[i][j] = c11[i][j];
                    C[i][j + newSize] = c12[i][j];
                    C[i + newSize][j] = c21[i][j];
                    C[i + newSize][j + newSize] = c22[i][j];
                }
            }
            return C;
        }
    }
}

package com.katpara.follium.linear.squares;

import com.katpara.follium.linear.Matrix;
import com.katpara.follium.util.Rounding;

/**
 * The interface defines a square matrix in the system.
 * A square matrix by definition is a matrix which has the same
 * number of elements in each rows and columns.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface SquareMatrix extends Matrix {
    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    boolean isSymmetric();

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    boolean isLowerTriangular();

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    boolean isUpperTriangular();

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    double determinant();

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     * This method rounds up the determinant to the given decimal accuracy.
     *
     * @param decimals accuracy to defined decimal points
     *
     * @return the determinant of the square matrix
     */
    double determinant(final Rounding.Decimals decimals);
}

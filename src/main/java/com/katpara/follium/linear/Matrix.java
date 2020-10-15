package com.katpara.follium.linear;

import com.katpara.follium.Field;

/**
 * The interface defines a Matrix in the system, and it's operations.
 * Mathematically A matrix is an rectangular object of numbers, which
 * has rows and columns.
 * <p>
 * The mathematical application of a matrix is to perform a learning
 * transformation of a space. It can scale up or down, turn and twist
 * the space. That can lead to deformation of vectors as well.
 * </p>
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Matrix extends Field<Matrix> {
    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    int[] size();

    /**
     * The method returns true if the matrix is a
     * row vector, i.e. the dimension is 1 x n.
     *
     * @return true if it is a row vector, otherwise false
     */
    boolean isRowVector();

    /**
     * The method returns true if the matrix is a
     * column vector, i.e. the dimension is n x 1.
     *
     * @return true if it is a column vector, otherwise false
     */
    boolean isColumnVector();

    /**
     * The method returns true if the matrix is a square matrix.
     *
     * @return true if it is a square matrix, otherwise false
     */
    boolean isSquareMatrix();

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    boolean isDiagonal();

    /**
     * The method returns all elements of the matrix.
     *
     * @return the matrix elements
     */
    double[] toArray();

    /**
     * The method returns the row elements of the Matrix.
     *
     * @param row the row index
     *
     * @return the matrix row elements
     */
    double[] getRow(final int row);

    /**
     * The method returns the column elements of a Matrix.
     *
     * @param column the column index
     *
     * @return the matrix column elements
     */
    double[] getColumn(final int column);

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    int getRank();

    /**
     * The method returns the transposed matrix.
     *
     * @return the transposed matrix
     */
    Matrix getTransposed();

    /**
     * The method returns the diagonal entries of the matrix.
     *
     * @return the diagonal entries
     */
    double[] getDiagonalEntries();
}

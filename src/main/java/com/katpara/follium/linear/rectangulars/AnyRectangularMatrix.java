package com.katpara.follium.linear.rectangulars;

import com.katpara.follium.exceptions.linears.NotInvertibleException;
import com.katpara.follium.exceptions.linears.NotRectangularMatrixException;
import com.katpara.follium.exceptions.linears.NotSquareMatrixException;
import com.katpara.follium.linear.AbstractMatrix;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.squares.AnySquareMatrix;

public final class AnyRectangularMatrix extends AbstractMatrix {
    /**
     * The constructor is used for rectangular matrices.
     *
     * @param e the element array
     * @param s the dimensions
     */
    public AnyRectangularMatrix(final double[] e, final int[] s) {
        super(e, s);
    }

    /**
     * The constructor is used for rectangular matrices.
     *
     * @param e the element array
     * @param r a number of rows
     * @param c a number of columns
     */
    public AnyRectangularMatrix(final double[] e, final int r, final int c) {
        super(e, r, c);
    }

    /**
     * The constructor is used for a two-dimensional array.
     *
     * @param e the two-dimensional elements
     */
    public AnyRectangularMatrix(final double[][] e) {
        super(e);

        if (e.length == e[0].length)
            throw new NotRectangularMatrixException();
    }

    /**
     * The method returns true if the matrix is a
     * row vector, i.e. the dimension is 1 x n.
     *
     * @return true if it is a row vector, otherwise false
     */
    @Override
    public boolean isRowVector() {
        return s[0] == 1 && s[1] > 1;
    }

    /**
     * The method returns true if the matrix is a
     * column vector, i.e. the dimension is n x 1.
     *
     * @return true if it is a column vector, otherwise false
     */
    @Override
    public boolean isColumnVector() {
        return s[0] > 1 && s[1] == 1;
    }

    /**
     * The method returns true if the matrix is a square matrix.
     *
     * @return true if it is a square matrix, otherwise false
     */
    @Override
    public boolean isSquareMatrix() {
        return false;
    }

    /**
     * The method is implemented by the subclasses.
     *
     * @param n the added array
     *
     * @return the matrix
     */
    @Override
    protected Matrix doAdd(final double[] n) {
        return new AnyRectangularMatrix(n, s);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the subtracted array
     *
     * @return the matrix
     */
    @Override
    protected Matrix doSubtract(final double[] n) {
        return new AnyRectangularMatrix(n, s);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the inverse array
     *
     * @return the inverse matrix
     */
    @Override
    protected Matrix doAdditiveInverse(final double[] n) {
        return new AnyRectangularMatrix(n, s);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the scaled array
     *
     * @return the scaled matrix
     */
    @Override
    protected Matrix doMultiply(final double[] n) {
        return new AnyRectangularMatrix(n, s);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the multiplied array
     * @param m the multiplying matrix
     *
     * @return the multiplied matrix
     */
    @Override
    protected Matrix doMultiply(final double[] n, final Matrix m) {
        var _s = m.size();

        return (s[0] == _s[1]) ? new AnySquareMatrix(n)
                       : new AnyRectangularMatrix(n, s[0], _s[1]);
    }

    /**
     * The method returns the matrix of transposed array.
     *
     * @param n the transposed array
     *
     * @return the transposed matrix
     */
    @Override
    protected Matrix doTranspose(final double[] n) {
        return new AnyRectangularMatrix(n, s[1], s[0]);
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public int getRank() {
        return 0;
    }

    /**
     * A method returns an absolute value of the field.
     *
     * @return the absolute value
     */
    @Override
    public double abs() {
        throw new NotSquareMatrixException();
    }

    /**
     * The method returns the field with the given power.
     *
     * @param power the power
     *
     * @return the powered field
     */
    @Override
    public Matrix power(final int power) {
        throw new NotSquareMatrixException();
    }

    /**
     * The method returns the multiplicative inverse of the field.
     *
     * @return the multiplicative inverse field
     */
    @Override
    public Matrix getMultiplicativeInverse() {
        throw new NotInvertibleException();
    }
}

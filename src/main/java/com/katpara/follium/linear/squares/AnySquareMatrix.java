package com.katpara.follium.linear.squares;

import com.katpara.follium.exceptions.linears.NotSquareMatrixException;
import com.katpara.follium.linear.AbstractMatrix;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.constants.IdentityMatrix;
import com.katpara.follium.linear.rectangulars.AnyRectangularMatrix;
import com.katpara.follium.util.Rounding;

import java.util.Arrays;

public final class AnySquareMatrix extends AbstractMatrix implements SquareMatrix {

    /**
     * This constructor is specifically used for square matrices.
     *
     * @param e the element array
     */
    public AnySquareMatrix(final double[] e) {
        super(e);
    }

    /**
     * The constructor is used for a two-dimensional array.
     *
     * @param e the two-dimensional elements
     */
    public AnySquareMatrix(final double[][] e) {
        super(e);

        if (e.length != e[0].length)
            throw new NotSquareMatrixException();
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public boolean isSymmetric() {
        int _r = s[1] - 1, _s = s[1] * _r / 2;

        for (int i = 0, _c = 1; i < _s; i++, _c--) {
            if (_c == 0)
                _c = s[1] - --_r;

            if (e[_r * s[1] - _c] !=
                        e[((s[1] - _c) * s[1]) + (_r - 1)])
                return false;
        }

        return true;
    }

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    @Override
    public boolean isLowerTriangular() {
        int _r = s[1] - 1, _s = s[1] * _r / 2;

        for (int i = 0, _c = 1; i < _s; i++, _c--) {
            if (_c == 0)
                _c = s[1] - --_r;

            if (e[_r * s[1] - _c] != 0)
                return false;
        }

        return true;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public boolean isUpperTriangular() {
        int _r = 1, _s = s[1] * (s[1] - 1) / 2;

        for (int i = 0, _c = _r; i < _s; i++) {
            if (_c == 0)
                _c = ++_r;

            if (e[_r * s[1] + --_c] != 0)
                return false;
        }

        return true;
    }

    /**
     * A determinant is a scalar value computed for a square matrix; that
     * encodes many properties of the linear algebra described by the matrix.
     * It is denoted as det(A), where A is a matrix or |A|.
     *
     * @return the determinant of the square matrix
     */
    @Override
    public double determinant() {
        return 0;
    }

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
    @Override
    public double determinant(final Rounding.Decimals decimals) {
        return 0;
    }

    /**
     * The method returns true if the matrix is a
     * row vector, i.e. the dimension is 1 x n.
     *
     * @return true if it is a row vector, otherwise false
     */
    @Override
    public final boolean isRowVector() {
        return false;
    }

    /**
     * The method returns true if the matrix is a
     * column vector, i.e. the dimension is n x 1.
     *
     * @return true if it is a column vector, otherwise false
     */
    @Override
    public final boolean isColumnVector() {
        return false;
    }

    /**
     * The method returns true if the matrix is a square matrix.
     *
     * @return true if it is a square matrix, otherwise false
     */
    @Override
    public final boolean isSquareMatrix() {
        return true;
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
     * The method is implemented by the subclasses.
     *
     * @param n the added array
     *
     * @return the matrix
     */
    @Override
    protected Matrix doAdd(final double[] n) {
        return new AnySquareMatrix(n);
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
        return new AnySquareMatrix(n);
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
        return new AnySquareMatrix(n);
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
        return new AnySquareMatrix(n);
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
        return new AnySquareMatrix(n);
    }

    /**
     * A method returns an absolute value of the field.
     *
     * @return the absolute value
     */
    @Override
    public double abs() {
        return determinant();
    }

    /**
     * The method returns the field with the given power.
     *
     * @param p the power
     *
     * @return the powered field
     */
    @Override
    public Matrix power(final int p) {
        if (p == -1)
            return getMultiplicativeInverse();
        else if (p == 0)
            return new IdentityMatrix(s[0]);
        else if (p == 1)
            return this;
        else {
            var n = Arrays.copyOf(e, e.length);
            for (int i = 1; i < p; i++) {
                var _n = new double[e.length];
                for (int j = 0; j < e.length; j++) {
                    int _r = j / s[0], _c = j % s[0];

                    for (int k = 0; k < s[0]; k++) {
                        _n[j] += n[(_r * s[0]) + k] * e[(k * s[0]) + _c];
                    }
                }
                n = _n;
            }

            if (p < 0)
                return getMultiplicativeInverse();      //TODO: FIX THIS ONCE I HAVE INVERSE IMPLEMENTED

            return new AnySquareMatrix(n);
        }
    }

    /**
     * The method returns the multiplicative inverse of the field.
     *
     * @return the multiplicative inverse field
     */
    @Override
    public Matrix getMultiplicativeInverse() {
        return null;
    }
}

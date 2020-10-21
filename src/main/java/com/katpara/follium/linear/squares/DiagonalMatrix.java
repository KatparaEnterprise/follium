package com.katpara.follium.linear.squares;

import com.katpara.follium.exceptions.InvalidParameterProvidedException;
import com.katpara.follium.exceptions.linears.*;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.constants.IdentityMatrix;
import com.katpara.follium.linear.constants.ZeroMatrix;
import com.katpara.follium.linear.rectangulars.AnyRectangularMatrix;
import com.katpara.follium.util.Rounding;

import java.util.Arrays;
import java.util.HashMap;

public final class DiagonalMatrix implements SquareMatrix {

    /**
     * Holds the matrix elements
     */
    protected final double[] e;

    /**
     * Holds the matrix size
     */
    protected final int s;

    /**
     * The diagonal matrix construction from an array
     *
     * @param e the diagonal array
     */
    public DiagonalMatrix(final double[] e) {
        if (e.length == 0)
            throw new InvalidMatrixDimensionProvidedException();

        this.e = e;
        this.s = e.length;
    }

    /**
     * The diagonal matrix construction fom a two-dimensional array.
     *
     * @param e the two-dimensional array
     */
    public DiagonalMatrix(final double[][] e) {
        if (e.length == 0 || e[0].length == 0)
            throw new InvalidMatrixDimensionProvidedException();

        if (e.length != e[0].length)
            throw new NotSquareMatrixException();

        var n = new double[e.length];
        for (int i = 0; i < e.length; i++) {
            n[i] = e[i][i];

            for (int j = 0; j < e.length; j++) {
                if (i != j && e[i][j] != 0) {
                    throw new InvalidParameterProvidedException();
                }
            }
        }

        this.e = n;
        this.s = e.length;
    }

    /**
     * The method returns the diagonal entries of the matrix.
     *
     * @return the diagonal entries
     */
    @Override
    public final double[] getDiagonalEntries() {
        return e;
    }

    /**
     * The method checks if the data is symmetric.
     *
     * @return true if symmetric, otherwise false
     */
    @Override
    public final boolean isSymmetric() {
        return true;
    }

    /**
     * the method returns true if the matrix is a lower triangular matrix
     *
     * @return true if it's a lower triangular
     */
    @Override
    public final boolean isLowerTriangular() {
        return true;
    }

    /**
     * the method returns true if the matrix is an upper triangular matrix
     *
     * @return true if it's a upper triangular
     */
    @Override
    public final boolean isUpperTriangular() {
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
        var det = 1.0;
        for (double _e: e) {
            det *= _e;
        }
        return det;
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
        return Double.parseDouble(Rounding.round(this.determinant(), decimals));
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public final int[] size() {
        return new int[]{s, s};
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
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public final boolean isDiagonal() {
        return true;
    }

    /**
     * The method returns all elements of the matrix.
     *
     * @return the matrix elements
     */
    @Override
    public double[] toArray() {
        var n = new double[s * s];

        for (int i = 0; i < s; i++) {
            n[(i * s) + i] = e[i];
        }

        return n;
    }

    /**
     * The method returns the row elements of the Matrix.
     *
     * @param row the row index
     *
     * @return the matrix row elements
     */
    @Override
    public double[] getRow(final int row) {
        if (row < 0 || row >= s)
            throw new RowOutOfBoundException();

        var n = new double[s];
        n[row] = e[row];
        return n;
    }

    /**
     * The method returns the column elements of a Matrix.
     *
     * @param column the column index
     *
     * @return the matrix column elements
     */
    @Override
    public double[] getColumn(final int column) {
        if (column < 0 || column >= s)
            throw new ColumnOutOfBoundException();

        var n = new double[s];
        n[column] = e[column];
        return n;
    }

    /**
     * A rank of a matrix is independent rows of a matrix. That shows that how many
     * rows of a matrix are totally independent, or co-dependent on other rows.
     *
     * @return the rank of matrix
     */
    @Override
    public final int getRank() {
        return s;
    }

    /**
     * The method returns the transposed matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public final Matrix getTransposed() {
        return this;
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
     * A fields can be added to another field of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public Matrix add(final Matrix m) {
        var _s = m.size();
        if (!Arrays.equals(this.size(), _s))
            throw new MatrixDimensionMismatchException();

        if (m instanceof ZeroMatrix)
            return this;

        if (m instanceof IdentityMatrix || m instanceof DiagonalMatrix) {
            double[] n;

            if (m instanceof IdentityMatrix) {
                n = Arrays.copyOf(e, s);
                for (int i = 0; i < s; i++) {
                    n[i] += 1;
                }

            } else {
                n = Arrays.copyOf(m.getDiagonalEntries(), s);
                for (int i = 0; i < s; i++) {
                    n[i] += e[i];
                }
            }

            return new DiagonalMatrix(n);
        }

        double[] n = Arrays.copyOf(m.toArray(), _s[0] * _s[1]);
        for (int i = 0; i < s; i++) {
            n[(i * s) + i] += e[i];
        }

        return new AnySquareMatrix(n);
    }

    /**
     * A fields can be subtracted from another field of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public Matrix subtract(final Matrix m) {
        var _s = m.size();
        if (!Arrays.equals(this.size(), _s))
            throw new MatrixDimensionMismatchException();

        if (this == m)
            return new ZeroMatrix(s);

        if (m instanceof ZeroMatrix)
            return this;

        if (m instanceof IdentityMatrix || m instanceof DiagonalMatrix) {
            double[] n;

            if (m instanceof IdentityMatrix) {
                n = Arrays.copyOf(e, s);
                for (int i = 0; i < s; i++) {
                    n[i] -= 1;
                }

            } else {
                n = Arrays.copyOf(m.getDiagonalEntries(), s);
                for (int i = 0; i < s; i++) {
                    n[i] = e[i] - n[i];
                }
            }

            return new DiagonalMatrix(n);
        }

        var d = s + 1;
        double[] n = Arrays.copyOf(m.toArray(), _s[0] * _s[1]);
        for (int i = 0; i < n.length; i++) {
            n[i] = (i % d == 0) ? e[i / d] - n[i] : -n[i];
        }

        return new AnySquareMatrix(n);
    }

    /**
     * The method multiplies the field with a scalar entity.
     *
     * @param scalar the scalar entity
     *
     * @return the multiplied field
     */
    @Override
    public Matrix multiply(final double scalar) {
        var n = Arrays.copyOf(e, s);
        for (int i = 0; i < n.length; i++) {
            n[i] *= scalar;
        }

        return new DiagonalMatrix(n);
    }

    /**
     * A field can multiply with another of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public Matrix multiply(final Matrix m) {
        var _s = m.size();
        if (s != _s[0])
            throw new MatrixDimensionMismatchException();

        if (m instanceof IdentityMatrix)
            return this;

        if (m instanceof ZeroMatrix)
            return new ZeroMatrix(s, _s[1]);

        return multiply(_s, m);
    }

    /**
     * A field can divided by another field of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public Matrix divide(final Matrix m) {
        if(this == m)
            return new IdentityMatrix(s);

        var _s = m.size();
        if (s != _s[0])
            throw new MatrixDimensionMismatchException();

        if (m instanceof IdentityMatrix)
            return this;

        return multiply(_s, m.getMultiplicativeInverse());
    }

    /**
     * A private method to multiply two matrices.
     *
     * @param _s the size of the given matrix
     * @param m the multiplyng matrix
     *
     * @return the resulting matrix
     */
    private Matrix multiply(final int[] _s, final Matrix m) {
        if (m instanceof DiagonalMatrix) {
            double[] n = Arrays.copyOf(m.getDiagonalEntries(), s);
            for (int i = 0; i < n.length; i++) {
                n[i] *= e[i];
            }
            return new DiagonalMatrix(n);
        }

        var n = Arrays.copyOf(m.toArray(), _s[0] * _s[1]);
        for (int i = 0; i < n.length; i++) {
            n[i] *= e[i / _s[1]];
        }

        return (_s[0] == _s[1]) ? new AnySquareMatrix(n)
                       : new AnyRectangularMatrix(n, s, _s[1]);
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
            return new IdentityMatrix(s);
        else if (p == 1)
            return this;
        else {
            var n = Arrays.copyOf(e, s);
            for (int i = 0; i < n.length; i++) {
                n[i] = Math.pow(n[i], p);
            }

            return new DiagonalMatrix(n);
        }
    }

    /**
     * The method returns the additive inverse of the field.
     *
     * @return the additive inverse field
     */
    @Override
    public Matrix getAdditiveInverse() {
        var n = Arrays.copyOf(e, s);

        for (int i = 0; i < n.length; i++) {
            n[i] = -n[i];
        }

        return new DiagonalMatrix(n);
    }

    /**
     * The method returns the multiplicative inverse of the field.
     *
     * @return the multiplicative inverse field
     */
    @Override
    public Matrix getMultiplicativeInverse() {
        var n = Arrays.copyOf(e, s);

        for (int i = 0; i < n.length; i++) {
            n[i] = 1 / n[i];
        }

        return new DiagonalMatrix(n);
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link Object#equals(Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined
     * by class {@code Object} does return distinct integers for
     * distinct objects. (The hashCode may or may not be implemented
     * as some function of an object's memory address at some point
     * in time.)
     *
     * @return a hash code value for this object.
     *
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        var result = 17;
        result = 31 * result + Arrays.hashCode(e);
        result = 31 * result + s;
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj
     *         argument; {@code false} otherwise.
     *
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;
        if (this.hashCode() == obj.hashCode()) return true;

        final Matrix that = (Matrix) obj;
        if (!Arrays.equals(size(), that.size())) return false;

        if (obj instanceof DiagonalMatrix)
            return Arrays.equals(e, that.getDiagonalEntries());

        return Arrays.equals(this.toArray(), that.toArray());
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.toString(Rounding.Decimals.FOUR);
    }

    /**
     * The method returns a string representing the field upto
     * specified decimal points.
     *
     * @param decimals the decimal precision
     *
     * @return the string representing the field
     */
    @Override
    public String toString(final Rounding.Decimals decimals) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            sb.append("|");
            for (int j = 0; j < s; j++) {
                if (i == j) {
                    sb.append(Rounding.round(e[i], decimals));
                } else {
                    sb.append(Rounding.round(0, decimals));
                }

                if (j != s - 1)
                    sb.append(" ");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}

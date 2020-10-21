package com.katpara.follium.linear.constants;

import com.katpara.follium.exceptions.InvalidParameterProvidedException;
import com.katpara.follium.exceptions.linears.InvalidMatrixDimensionProvidedException;
import com.katpara.follium.exceptions.linears.MatrixDimensionMismatchException;
import com.katpara.follium.exceptions.linears.NotInvertibleException;
import com.katpara.follium.exceptions.linears.NotSquareMatrixException;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.util.Rounding;

import java.util.Arrays;
import java.util.HashMap;

public final class ZeroMatrix implements Matrix {

    /**
     * Holds the matrix size
     */
    protected final int[] s;

    public ZeroMatrix(final int r, final int c) {
        if (r == 0 || c == 0)
            throw new InvalidMatrixDimensionProvidedException();

        this.s = new int[]{r, c};
    }

    public ZeroMatrix(final int r) {
        this(r, r);
    }

    public ZeroMatrix(final double[] e, final int[] s) {
        if (s.length != 2 || s[0] <= 0 || s[1] <= 0 || e.length != s[0] * s[1])
            throw new InvalidMatrixDimensionProvidedException();

        for (final double _e: e) {
            if (_e != 0) throw new InvalidParameterProvidedException();
        }

        this.s = s;
    }

    public ZeroMatrix(final double[] e, final int r, final int c) {
        this(e, new int[]{r, c});
    }

    public ZeroMatrix(final double[][] e) {
        if (e.length == 0 || e[0].length == 0)
            throw new InvalidMatrixDimensionProvidedException();

        for (final double[] _e: e) {
            for (final double __e: _e) {
                if (__e != 0) throw new InvalidParameterProvidedException();
            }
        }

        this.s = new int[]{e.length, e[0].length};
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public int[] size() {
        return Arrays.copyOf(s, 2);
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
        return s[0] == s[1];
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public boolean isDiagonal() {
        return true;
    }

    /**
     * The method returns all elements of the matrix.
     *
     * @return the matrix elements
     */
    @Override
    public double[] toArray() {
        var n = new double[s[0] * s[1]];
        Arrays.fill(n, 0);
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
        if (row < 0 || row >= s[0])
            throw new IndexOutOfBoundsException();

        var n = new double[s[1]];
        Arrays.fill(n, 0);
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
        if (column < 0 || column >= s[1])
            throw new IndexOutOfBoundsException();

        var n = new double[s[0]];
        Arrays.fill(n, 0);
        return n;
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
     * The method returns the transposed matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public Matrix getTransposed() {
        return this;
    }

    /**
     * The method returns the diagonal entries of the matrix.
     *
     * @return the diagonal entries
     */
    @Override
    public double[] getDiagonalEntries() {
        return getRow(0);
    }

    /**
     * A method returns an absolute value of the field.
     *
     * @return the absolute value
     */
    @Override
    public double abs() {
        if (s[0] != s[1])
            throw new NotSquareMatrixException();

        return 0;
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
        if (!Arrays.equals(s, m.size()))
            throw new MatrixDimensionMismatchException();

        return m;
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
        if (!Arrays.equals(s, m.size()))
            throw new MatrixDimensionMismatchException();

        return m.getAdditiveInverse();
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
        return this;
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
        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        return new ZeroMatrix(s[0], _s[1]);
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
        return multiply(m.getMultiplicativeInverse());
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
        return this;
    }

    /**
     * The method returns the additive inverse of the field.
     *
     * @return the additive inverse field
     */
    @Override
    public Matrix getAdditiveInverse() {
        return this;
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
        for (int i = 0; i < s[0]; i++) {
            sb.append("|");
            for (int j = 0; j < s[1]; j++) {
                sb.append(0);

                if (j != s[1] - 1)
                    sb.append(" ");
            }
            sb.append("|\n");
        }
        return sb.toString();
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
        result = 31 * result + Arrays.hashCode(s);
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

        return (obj instanceof ZeroMatrix) || Arrays.equals(this.toArray(), that.toArray());
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
        return toString(Rounding.Decimals.ZERO);
    }
}

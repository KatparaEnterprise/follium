package com.katpara.follium.linear;

import com.katpara.follium.exceptions.linears.*;
import com.katpara.follium.linear.constants.IdentityMatrix;
import com.katpara.follium.linear.constants.ZeroMatrix;
import com.katpara.follium.util.Rounding;

import java.util.Arrays;
import java.util.HashMap;

/**
 * The class is an abstract class that provides implementation to some
 * of the common method that is shared between Square and Rectangular
 * matrices.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public abstract class AbstractMatrix implements Matrix {

    /**
     * Holds the matrix elements
     */
    protected final double[] e;

    /**
     * Holds the matrix size
     */
    protected final int[] s;

    /**
     * This constructor is specifically used for square matrices.
     *
     * @param e the element array
     */
    protected AbstractMatrix(final double[] e) {
        var s = Math.sqrt(e.length);
        if (s == 0 || (s - Math.floor(s)) != 0)
            throw new InvalidMatrixDimensionProvidedException();

        this.e = e;
        this.s = new int[]{(int) s, (int) s};
    }

    /**
     * The constructor is used for rectangular matrices.
     *
     * @param e the element array
     * @param s the dimensions
     */
    protected AbstractMatrix(final double[] e, final int[] s) {
        if (s.length != 2 || s[0] <= 0 || s[1] <= 0 || e.length != s[0] * s[1])
            throw new InvalidMatrixDimensionProvidedException();

        if (s[0] == s[1])
            throw new NotRectangularMatrixException();

        this.e = e;
        this.s = s;
    }

    /**
     * The constructor is used for rectangular matrices.
     *
     * @param e the element array
     * @param r a number of rows
     * @param c a number of columns
     */
    protected AbstractMatrix(final double[] e, final int r, final int c) {
        this(e, new int[]{r, c});
    }

    /**
     * The constructor is used for a two-dimensional array.
     *
     * @param e the two-dimensional elements
     */
    protected AbstractMatrix(final double[][] e) {
        if (e.length == 0 || e[0].length == 0)
            throw new InvalidMatrixDimensionProvidedException();

        s = new int[]{e.length, e[0].length};
        this.e = new double[s[0] * s[1]];

        for (int i = 0; i < s[0]; i++) {
            System.arraycopy(e[i], 0, this.e, i * s[1], s[1]);
        }
    }

    /**
     * The method returns the dimension of a matrix.
     *
     * @return the dimension of the matrix
     */
    @Override
    public final int[] size() {
        return Arrays.copyOf(s, 2);
    }

    /**
     * The method returns all elements of the matrix.
     *
     * @return the matrix elements
     */
    @Override
    public final double[] toArray() {
        return e;
    }

    /**
     * The method returns true if the matrix is diagonal.
     *
     * @return true if the matrix is diagonal
     */
    @Override
    public final boolean isDiagonal() {
        var _t = s[1] + 1;
        for (int i = 0; i < e.length; i++) {
            if (i % _t != 0 && e[i] != 0)
                return false;
        }
        return true;
    }

    /**
     * The method returns the row elements of the Matrix.
     *
     * @param r the row index
     *
     * @return the matrix row elements
     */
    @Override
    public final double[] getRow(final int r) {
        if (r < 0 || r >= s[0])
            throw new RowOutOfBoundException();

        var f = r * s[1];
        return Arrays.copyOfRange(e, f, f + s[1]);
    }

    /**
     * The method returns the column elements of a Matrix.
     *
     * @param c the column index
     *
     * @return the matrix column elements
     */
    @Override
    public final double[] getColumn(final int c) {
        if (c < 0 || c >= s[1])
            throw new ColumnOutOfBoundException();

        var n = new double[s[0]];
        for (int i = 0; i < s[0]; i++) {
            n[i] = e[(i * s[1]) + c];
        }

        return n;
    }

    /**
     * A fields can be added to another field of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public final Matrix add(final Matrix m) {
        var _s = m.size();
        if (!Arrays.equals(_s, size()))
            throw new MatrixDimensionMismatchException();

        if (m instanceof ZeroMatrix)
            return this;

        double[] n = Arrays.copyOf(m.toArray(), _s[0] * _s[1]);
        for (int i = 0; i < e.length; i++)
            n[i] += e[i];

        return doAdd(n);
    }

    /**
     * The method is implemented by the subclasses.
     *
     * @param n the added array
     *
     * @return the matrix
     */
    protected abstract Matrix doAdd(final double[] n);

    /**
     * A fields can be subtracted from another field of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public final Matrix subtract(final Matrix m) {
        if (!Arrays.equals(size(), m.size()))
            throw new MatrixDimensionMismatchException();

        if (this.equals(m))
            return new ZeroMatrix(s[0], s[1]);

        if (m instanceof ZeroMatrix)
            return this;

        double[] n = Arrays.copyOf(e, e.length), _e = m.toArray();
        for (int i = 0; i < e.length; i++)
            n[i] -= _e[i];

        return doSubtract(n);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the subtracted array
     *
     * @return the matrix
     */
    protected abstract Matrix doSubtract(final double[] n);

    /**
     * The method returns the additive inverse of the field.
     *
     * @return the additive inverse field
     */
    @Override
    public final Matrix getAdditiveInverse() {
        var n = Arrays.copyOf(e, e.length);

        for (int i = 0; i < e.length; i++) {
            n[i] = -n[i];
        }

        return doAdditiveInverse(n);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the inverse array
     *
     * @return the inverse matrix
     */
    protected abstract Matrix doAdditiveInverse(final double[] n);

    /**
     * The method multiplies the field with a scalar entity.
     *
     * @param scalar the scalar entity
     *
     * @return the multiplied field
     */
    @Override
    public final Matrix multiply(final double scalar) {
        if(scalar == -1)
            return getAdditiveInverse();
        if(scalar == 0)
            return new ZeroMatrix(s[0], s[1]);
        if(scalar == 1)
            return this;

        var n = Arrays.copyOf(e, e.length);
        for (int i = 0; i < n.length; i++) {
                n[i] *= scalar;
        }

        return doMultiply(n);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the scaled array
     *
     * @return the scaled matrix
     */
    protected abstract Matrix doMultiply(final double[] n);

    /**
     * A field can multiply with another of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public final Matrix multiply(final Matrix m) {
        var _s = m.size();
        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        if(m instanceof ZeroMatrix)
            return new ZeroMatrix(s[0], _s[1]);

        if(m instanceof IdentityMatrix)
            return this;

        return multiply(_s, m);
    }

    /**
     * The method is implemented by the sub-classes.
     *
     * @param n the multiplied array
     * @param m the multiplying matrix
     *
     * @return the multiplied matrix
     */
    protected abstract Matrix doMultiply(final double[] n, final Matrix m);


    /**
     * A field can divided by another field of the same type.
     *
     * @param m the other field
     *
     * @return the resulting field
     */
    @Override
    public final Matrix divide(final Matrix m) {
        var _s = m.size();
        if (s[1] != _s[0])
            throw new MatrixDimensionMismatchException();

        if (isSquareMatrix() && m == this)
            return new IdentityMatrix(s[0]);

        if(m instanceof IdentityMatrix)
            return this;

        return multiply(_s, m.getMultiplicativeInverse());
    }

    /**
     * The method multiplies two matrix data.
     *
     * @param m the multiplying matrix
     * @param _s the size of multiplying matrix
     *
     * @return the multiplicative inverse
     */
    private Matrix multiply(final int[] _s, final Matrix m) {
        double[] n = new double[s[0] * _s[1]],
                _e = m.toArray();

        for (int i = 0; i < n.length; i++) {
            int _r = i / _s[1], _c = i % _s[1];

            for (int j = 0; j < s[1]; j++) {
                n[i] += e[(_r * s[1]) + j] * _e[(_s[1] * j) + _c];
            }
        }

        return doMultiply(n, m);
    }

    /**
     * The method returns the transposed matrix.
     *
     * @return the transposed matrix
     */
    @Override
    public final Matrix getTransposed() {
        var n = new double[e.length];

        for (int i = 0; i < e.length; i++) {
            n[i] = e[((i % s[0]) * s[1]) + (i / s[0])];
        }

        return doTranspose(n);
    }

    /**
     * The method returns the matrix of transposed array.
     *
     * @param n the transposed array
     *
     * @return the transposed matrix
     */
    abstract protected Matrix doTranspose(final double[] n);

    /**
     * The method returns the diagonal entries of the matrix.
     *
     * @return the diagonal entries
     */
    @Override
    public final double[] getDiagonalEntries() {
        var n = new double[Math.min(s[0], s[1])];
        for (int i = 0; i < n.length; i++) {
            n[i] = e[(i * s[1]) + i];
        }

        return n;
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
    public final String toString() {
        return this.toString(Rounding.Decimals.FOUR);
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
    public final String toString(final Rounding.Decimals decimals) {
        StringBuilder sb = new StringBuilder();
        var _t = s[1] - 1;
        for (int i = 0; i < e.length; i++) {
            if (i % s[1] == 0) {
                sb.append("|");
                sb.append(Rounding.round(e[i], decimals));
            } else if (i % s[1] == _t) {
                sb.append(" ");
                sb.append(Rounding.round(e[i], decimals));
                sb.append("|\n");
            } else {
                sb.append(" ");
                sb.append(Rounding.round(e[i], decimals));
            }
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
     *
     * @return a hash code value for this object.
     *
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public final int hashCode() {
        var result = 17;
        result = 31 * result + Arrays.hashCode(e);
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
    public final boolean equals(final Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;
        if (this.hashCode() == obj.hashCode()) return true;

        final Matrix that = (Matrix) obj;
        if (!Arrays.equals(size(), that.size())) return false;
        return Arrays.equals(e, that.toArray());
    }
}

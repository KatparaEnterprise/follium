package com.katpara.follium.linear.constants;

import com.katpara.follium.exceptions.InvalidParameterProvidedException;
import com.katpara.follium.exceptions.linears.InvalidMatrixDimensionProvidedException;
import com.katpara.follium.exceptions.linears.MatrixDimensionMismatchException;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.squares.AnySquareMatrix;
import com.katpara.follium.linear.squares.DiagonalMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZeroMatrixTest {

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(0, 5)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(5, 0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[0], 0, 0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0}, 0, 2)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0}, 2, 0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0, 0, 0}, 2, 1)),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> new ZeroMatrix(new double[]{0, 1, 0, 0}, 2, 2)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[0][0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[][]{{}})),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> new ZeroMatrix(new double[][]{{1, 0}, {0, 0}})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0}, new int[0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0}, new int[]{-1, 3})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0}, new int[]{3, 0})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0}, new int[]{3})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new ZeroMatrix(new double[]{0, 0, 0, 0}, new int[]{3, 3}))
        );
    }

    @Test
    void rowAndColumnAndSquare() {
        Matrix m1 = new ZeroMatrix(1, 3);
        Matrix m2 = new ZeroMatrix(3, 1);
        Matrix m3 = new ZeroMatrix(3, 3);

        assertAll(
                () -> assertTrue(m1.isRowVector()),
                () -> assertFalse(m1.isColumnVector()),
                () -> assertFalse(m1.isSquareMatrix()),
                () -> assertTrue(m2.isColumnVector()),
                () -> assertFalse(m2.isRowVector()),
                () -> assertFalse(m2.isSquareMatrix()),
                () -> assertTrue(m3.isSquareMatrix()),
                () -> assertFalse(m3.isRowVector()),
                () -> assertFalse(m3.isColumnVector())
        );
    }

    @Test
    void toArray() {
        Matrix m1 = new ZeroMatrix(3, 2);
        var a = new double[]{0, 0, 0, 0, 0, 0};

        assertArrayEquals(a, m1.toArray());
    }

    @Test
    void getRowAndColumn() {
        Matrix m1 = new ZeroMatrix(4, 5);
        var r = new double[]{0, 0, 0, 0, 0};
        var c = new double[]{0, 0, 0, 0};

        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class, () -> m1.getRow(-1)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> m1.getRow(4)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> m1.getRow(6)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> m1.getColumn(-1)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> m1.getColumn(5)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> m1.getColumn(7)),
                () -> assertArrayEquals(r, m1.getRow(2)),
                () -> assertArrayEquals(c, m1.getColumn(0))
        );
    }

    @Test
    void add() {
        Matrix m1 = new ZeroMatrix(4);
        Matrix m2 = new IdentityMatrix(3);
        Matrix m3 = new AnySquareMatrix(new double[]{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4});

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m2)),
                () -> assertEquals(m3, m1.add(m3))
        );
    }

    @Test
    void subtract() {
        Matrix m1 = new ZeroMatrix(4);
        Matrix m2 = new IdentityMatrix(3);
        Matrix m3 = new AnySquareMatrix(new double[]{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4});

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m2)),
                () -> assertEquals(m3.getAdditiveInverse(), m1.subtract(m3))
        );
    }

    @Test
    void multiply() {
        Matrix m1 = new ZeroMatrix(2, 4);
        Matrix m2 = new IdentityMatrix(3);
        Matrix m3 = new AnySquareMatrix(new double[]{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4});

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m2)),
                () -> assertEquals(m1, m1.multiply(m3))
        );
    }

    @Test
    void equals() {
        Matrix m1 = new ZeroMatrix(3);
        Matrix m2 = new DiagonalMatrix(new double[]{0, 0, 0});
        Matrix m3 = new AnySquareMatrix(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0});

        assertAll(
                () -> assertEquals(m1, m2),
                () -> assertEquals(m1, m3)
        );
    }

    @Test
    void divide() {
        Matrix m1 = new ZeroMatrix(2, 3);
        Matrix m2 = new IdentityMatrix(4);
        Matrix m3 = new IdentityMatrix(3);

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.divide(m2)),
                () -> assertEquals(m1, m1.divide(m3))
        );
    }
}
package com.katpara.follium.linear.constants;

import com.katpara.follium.exceptions.InvalidParameterProvidedException;
import com.katpara.follium.exceptions.linears.InvalidMatrixDimensionProvidedException;
import com.katpara.follium.exceptions.linears.MatrixDimensionMismatchException;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.squares.AnySquareMatrix;
import com.katpara.follium.linear.squares.DiagonalMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityMatrixTest {

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new IdentityMatrix(0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new IdentityMatrix(-1)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new IdentityMatrix(new double[0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new IdentityMatrix(new double[]{})),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> new IdentityMatrix(new double[]{1, 2, 3})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new IdentityMatrix(new double[0][0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new IdentityMatrix(new double[][]{})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class,
                        () -> new IdentityMatrix(new double[][]{{1, 2, 3}, {1, 2, 3}})),
                () -> assertThrows(InvalidParameterProvidedException.class,
                        () -> new IdentityMatrix(new double[][]{
                                {1, 0, 0}, {0, 2, 0}, {0, 0, 1}})),
                () -> assertThrows(InvalidParameterProvidedException.class,
                        () -> new IdentityMatrix(new double[][]{
                                {1, 0, 0}, {1, 1, 0}, {0, 0, 1}})),
                () -> new IdentityMatrix(3),
                () -> new IdentityMatrix(new double[]{1, 1, 1}),
                () -> new IdentityMatrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}})
        );
    }

    @Test
    void hashCod() {
        Matrix m1 = new IdentityMatrix(5);
        Matrix m2 = new IdentityMatrix(new double[]{1, 1, 1, 1, 1});
        Matrix m3 = new IdentityMatrix(new double[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        });

        assertAll(
                () -> assertEquals(m1.hashCode(), m2.hashCode()),
                () -> assertEquals(m2.hashCode(), m3.hashCode()),
                () -> assertEquals(m3.hashCode(), m1.hashCode())
        );
    }

    @Test
    void rowAndColumn() {
        Matrix m1 = new IdentityMatrix(new double[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        });

        assertAll(
                () -> assertArrayEquals(new double[]{0, 1, 0, 0, 0}, m1.getRow(1)),
                () -> assertArrayEquals(new double[]{0, 0, 1, 0, 0}, m1.getColumn(2))
        );
    }

    @Test
    void toArray() {
        Matrix m1 = new IdentityMatrix(3);
        assertArrayEquals(new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, m1.toArray());
    }

    @Test
    void additiveInverse() {
        Matrix m1 = new IdentityMatrix(3);
        Matrix r1 = new DiagonalMatrix(new double[]{-1, -1, -1});
        assertEquals(m1.getAdditiveInverse(), r1);
    }

    @Test
    void add() {
        Matrix m1 = new IdentityMatrix(5);
        Matrix m2 = new IdentityMatrix(3);
        Matrix m3 = new IdentityMatrix(5);
        Matrix m5 = new DiagonalMatrix(new double[]{1, 2, 3});
        Matrix m6 = new DiagonalMatrix(new double[]{1, 2, 3, 4, 5});
        Matrix m7 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        });
        Matrix m8 = new AnySquareMatrix(new double[][]{
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5}
        });

        Matrix r1 = new DiagonalMatrix(new double[]{2, 2, 2, 2, 2});
        Matrix r2 = new DiagonalMatrix(new double[]{2, 3, 4, 5, 6});
        Matrix r3 = new AnySquareMatrix(new double[][]{
                {2, 2, 3, 4, 5},
                {1, 3, 3, 4, 5},
                {1, 2, 4, 4, 5},
                {1, 2, 3, 5, 5},
                {1, 2, 3, 4, 6}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m2)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m5)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m7)),
                () -> assertEquals(r1, m1.add(m3)),
                () -> assertEquals("DiagonalMatrix", m1.add(m3).getClass().getSimpleName()),
                () -> assertEquals(r2, m1.add(m6)),
                () -> assertEquals("DiagonalMatrix", m1.add(m6).getClass().getSimpleName()),
                () -> assertEquals(r3, m1.add(m8)),
                () -> assertEquals("AnySquareMatrix", m1.add(m8).getClass().getSimpleName())
        );
    }

    @Test
    void subtract() {
        Matrix m1 = new IdentityMatrix(5);
        Matrix m2 = new IdentityMatrix(3);
        Matrix m3 = new IdentityMatrix(5);
        Matrix m5 = new DiagonalMatrix(new double[]{1, 2, 3});
        Matrix m6 = new DiagonalMatrix(new double[]{2, 3, 4, 5, 6});
        Matrix m7 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        });
        Matrix m8 = new AnySquareMatrix(new double[][]{
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5}
        });

        Matrix r1 = new DiagonalMatrix(new double[]{-1, -2, -3, -4, -5});
        Matrix r2 = new AnySquareMatrix(new double[][]{
                {0, 2, 3, 4, 5},
                {1, -1, 3, 4, 5},
                {1, 2, -2, 4, 5},
                {1, 2, 3, -3, 5},
                {1, 2, 3, 4, -4}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m2)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m5)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m7)),
                () -> assertEquals("ZeroMatrix", m1.subtract(m3).getClass().getSimpleName()),
                () -> assertEquals(r1, m1.subtract(m6)),
                () -> assertEquals("DiagonalMatrix", m1.subtract(m6).getClass().getSimpleName()),
                () -> assertEquals(r2, m1.subtract(m8)),
                () -> assertEquals("AnySquareMatrix", m1.subtract(m8).getClass().getSimpleName())
        );
    }

    @Test
    void multiply() {
        Matrix m1 = new IdentityMatrix(3);
        Matrix m2 = new IdentityMatrix(5);
        Matrix m3 = new DiagonalMatrix(new double[]{1, 2, 3});
        Matrix m4 = new DiagonalMatrix(new double[]{1, 2, 3, 4});
        Matrix m5 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        });
        Matrix m6 = new AnySquareMatrix(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m2)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m4)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m6)),
                () -> assertEquals(m3, m1.multiply(m3)),
                () -> assertEquals("DiagonalMatrix", m1.multiply(m3).getClass().getSimpleName()),
                () -> assertEquals(m5, m1.multiply(m5)),
                () -> assertEquals("AnySquareMatrix", m1.subtract(m5).getClass().getSimpleName())
        );
    }
}
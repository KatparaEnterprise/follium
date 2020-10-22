package com.katpara.follium.linear.rectangulars;

import com.katpara.follium.exceptions.linears.*;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.constants.IdentityMatrix;
import com.katpara.follium.linear.constants.ZeroMatrix;
import com.katpara.follium.linear.squares.AnySquareMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnyRectangularMatrixTest {

    @Test
    void size() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {1, 2},
                {1, 2},
                {1, 2},
                {1, 2}
        });

        assertArrayEquals(new int[]{2, 4}, m1.size());
        assertArrayEquals(new int[]{4, 2}, m2.size());
    }

    @Test
    void array() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });
        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3}
        });

        assertAll(
                () -> assertArrayEquals(new double[]{1, 2, 3, 4, 1, 2, 3, 4}, m1.toArray()),
                () -> assertArrayEquals(new double[]{1, 2, 3, 1, 2, 3}, m2.toArray())
        );
    }

    @Test
    void diagonal() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 3, 0}
        });

        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3}
        });

        Matrix m4 = new AnyRectangularMatrix(new double[][]{
                {1, 0, 0},
                {0, 2, 0}
        });

        Matrix m5 = new AnyRectangularMatrix(new double[][]{
                {1, 2},
                {1, 2},
                {1, 2},
                {1, 2}
        });

        Matrix m6 = new AnyRectangularMatrix(new double[][]{
                {1, 0},
                {0, 2},
                {0, 0},
                {0, 0}
        });

        assertAll(
                () -> assertFalse(m1.isDiagonal()),
                () -> assertFalse(m3.isDiagonal()),
                () -> assertFalse(m5.isDiagonal()),
                () -> assertTrue(m2.isDiagonal()),
                () -> assertTrue(m4.isDiagonal()),
                () -> assertTrue(m6.isDiagonal())
        );
    }

    @Test
    void rowAndColumn() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        assertAll(
                () -> assertThrows(RowOutOfBoundException.class, () -> m1.getRow(-1)),
                () -> assertThrows(RowOutOfBoundException.class, () -> m1.getRow(3)),
                () -> assertArrayEquals(new double[]{1, 2, 3, 4}, m1.getRow(1)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m1.getColumn(-1)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m1.getColumn(4)),
                () -> assertArrayEquals(new double[]{1, 2, 6}, m1.getColumn(1))
        );
    }

    @Test
    void add() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {0, 1},
                {1, 2},
                {5, 6}
        });

        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {0, 2, 4, 6},
                {2, 4, 6, 8},
                {10, 12, 14, 16}
        });


        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m2)),
                () -> assertEquals(m1, m1.add(new ZeroMatrix(3, 4))),
                () -> assertEquals(r1, m1.add(m1)),
                () -> assertEquals(r1, m1.add(m3))
        );
    }

    @Test
    void subtract() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 2, 4, 6},
                {2, 4, 6, 8},
                {10, 12, 14, 16}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {0, 1},
                {1, 2},
                {5, 6}
        });

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m2)),
                () -> assertEquals(m1, m1.subtract(new ZeroMatrix(3, 4))),
                () -> assertEquals(new ZeroMatrix(3, 4), m1.subtract(m1)),
                () -> assertEquals(r1, m1.subtract(r1))
        );
    }

    @Test
    void transpose() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 1},
                {1, 2},
                {5, 6}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 5},
                {1, 2, 6}
        });

        Matrix r2 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 5},
                {1, 2, 6},
                {2, 3, 7},
                {3, 4, 8}
        });

        assertAll(
                () -> assertEquals(r1, m1.getTransposed()),
                () -> assertEquals(r2, m2.getTransposed())
        );
    }

    @Test
    void additiveInverse() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {1, 1},
                {1, 2},
                {5, 6}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {1, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {-1, -1},
                {-1, -2},
                {-5, -6}
        });

        Matrix r2 = new AnyRectangularMatrix(new double[][]{
                {-1, -1, -2, -3},
                {-1, -2, -3, -4},
                {-5, -6, -7, -8}
        });

        assertAll(
                () -> assertEquals(r1, m1.getAdditiveInverse()),
                () -> assertEquals(r2, m2.getAdditiveInverse())
        );
    }

    @Test
    void multiply() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3},
                {5, 6, 7},
                {5, 6, 7}
        });

        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2, 3},
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });

        Matrix m4 = new AnyRectangularMatrix(new double[][]{
                {0, 1},
                {1, 2},
                {5, 6}
        });

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {11, 14, 17, 20},
                {17, 23, 29, 35}
        });

        Matrix r2 = new AnySquareMatrix(new double[][]{
                {11, 14},
                {17, 23}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m2)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(new ZeroMatrix(4, 6))),
                () -> assertEquals(new ZeroMatrix(2, 5), m1.multiply(new ZeroMatrix(3, 5))),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(new IdentityMatrix(4))),
                () -> assertEquals(m1, m1.multiply(new IdentityMatrix(3))),
                () -> assertEquals(r1, m1.multiply(m3)),
                () -> assertEquals(r2, m1.multiply(m4))
        );
    }

    @Test
    void diagonalEntries() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3},
                {5, 6, 7},
                {8, 2, 3}
        });

        assertAll(
                () -> assertArrayEquals(new double[]{0, 2}, m1.getDiagonalEntries()),
                () -> assertArrayEquals(new double[]{0, 2, 7}, m2.getDiagonalEntries())
        );
    }

    @Test
    void equals() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3}
        });

        Matrix m2 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3},
                {5, 6, 7},
                {8, 2, 3}
        });

        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {1, 2, 3}
        });

        assertAll(
                () -> assertNotEquals(m1, m2),
                () -> assertEquals(m1, m3)
        );
    }

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0], new int[]{1, 2, 3})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0], new int[]{0, 2})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0], new int[]{1, 0})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(
                        new double[]{1, 2, 3, 4, 5, 6}, new int[]{3, 3})),
                () -> assertThrows(NotRectangularMatrixException.class, () -> new AnyRectangularMatrix(
                        new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[]{3, 3})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0], 0, 2)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0], 1, 0)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(
                        new double[]{1, 2, 3, 4, 5, 6}, 3, 3)),
                () -> assertThrows(NotRectangularMatrixException.class, () -> new AnyRectangularMatrix(
                        new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3, 3)),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0][0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[0][1])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnyRectangularMatrix(new double[1][0])),
                () -> assertThrows(NotRectangularMatrixException.class, () -> new AnyRectangularMatrix(new double[][]{
                        {1, 2, 3},
                        {1, 2, 3},
                        {1, 2, 3}
                })),
                () -> new AnyRectangularMatrix(new double[]{1, 2, 3, 4, 5, 6}, new int[]{2, 3}),
                () -> new AnyRectangularMatrix(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                () -> new AnyRectangularMatrix(new double[][]{
                        {1, 2, 3, 4},
                        {1, 2, 3, 4}
                }),
                () -> new AnyRectangularMatrix(new double[][]{
                        {1, 2},
                        {1, 2},
                        {1, 2},
                        {1, 2}
                })
        );
    }

    @Test
    void multiplyScalar() {
        Matrix m1 = new AnyRectangularMatrix(new double[][]{
                {1, 1, 2},
                {1, 2, 3}
        });

        Matrix r1 = new AnyRectangularMatrix(new double[][]{
                {-1, -1, -2},
                {-1, -2, -3}
        });

        Matrix r2 = new AnyRectangularMatrix(new double[][]{
                {5, 5, 10},
                {5, 10, 15}
        });
        Matrix r3 = new AnyRectangularMatrix(new double[][]{
                {-7, -7, -14},
                {-7, -14, -21}
        });

        assertAll(
                () -> assertEquals(r1, m1.multiply(-1)),
                () -> assertEquals(new ZeroMatrix(2, 3), m1.multiply(0)),
                () -> assertEquals(m1, m1.multiply(1)),
                () -> assertEquals(r2, m1.multiply(5)),
                () -> assertEquals(r3, m1.multiply(-7))
        );
    }
}
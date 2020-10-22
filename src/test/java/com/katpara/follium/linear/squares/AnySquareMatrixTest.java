package com.katpara.follium.linear.squares;

import com.katpara.follium.exceptions.linears.*;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.constants.IdentityMatrix;
import com.katpara.follium.linear.constants.ZeroMatrix;
import com.katpara.follium.linear.rectangulars.AnyRectangularMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnySquareMatrixTest {

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnySquareMatrix(new double[0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnySquareMatrix(new double[]{})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnySquareMatrix(new double[]{1, 2})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnySquareMatrix(new double[]{1, 2, 3})),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnySquareMatrix(new double[0][0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new AnySquareMatrix(new double[][]{{}})),
                () -> assertThrows(NotSquareMatrixException.class, () -> new AnySquareMatrix(new double[][]{{1, 2, 3}, {1, 2, 3}})),
                () -> assertArrayEquals(new int[]{2, 2}, new AnySquareMatrix(new double[][]{{1, 2}, {1, 2}}).size())
        );
    }

    @Test
    void size() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {10, 12, 9, 8},
                {4, 1, 0, 0},
                {5, 6, 1, 15},
                {30, 22, 0, 13}
        });

        assertArrayEquals(m.size(), new int[]{4, 4});
    }

    @Test
    void elements() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {1, 0, 0},
                {2, 1, 3},
                {2, 3, 1}
        });

        assertArrayEquals(m.toArray(), new double[]{1, 0, 0, 2, 1, 3, 2, 3, 1});
    }

    @Test
    void diagonal() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {11, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 25, 0},
                {0, 0, 0, 36}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {11, 0, 10, 0},
                {0, 0, 0, 0},
                {0, 0, 25, 0},
                {0, 0, 0, 36}
        });

        Matrix m3 = new AnySquareMatrix(new double[][]{
                {11, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 25, 0},
                {14, 0, 0, 36}
        });

        assertAll(
                () -> assertTrue(m1.isDiagonal()),
                () -> assertFalse(m2.isDiagonal()),
                () -> assertFalse(m3.isDiagonal())
        );
    }

    @Test
    void getRow() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {10, 12, 9, 8},
                {4, 1, 0, 0},
                {5, 6, 1, 15},
                {30, 22, 0, 13}
        });

        assertAll(
                () -> assertThrows(RowOutOfBoundException.class, () -> m.getRow(-1)),
                () -> assertThrows(RowOutOfBoundException.class, () -> m.getRow(4)),
                () -> assertArrayEquals(m.getRow(1), new double[]{4, 1, 0, 0})
        );
    }

    @Test
    void getColumn() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {10, 12, 9, 8},
                {4, 1, 0, 0},
                {5, 6, 1, 15},
                {30, 22, 0, 13}
        });

        assertAll(
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m.getColumn(-1)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m.getColumn(4)),
                () -> assertArrayEquals(m.getColumn(1), new double[]{12, 1, 6, 22})
        );
    }

    @Test
    void transpose() {
        Matrix m = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Matrix t = new AnySquareMatrix(new double[][]{
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        });

        assertEquals(m.getTransposed(), t);
    }

    @Test
    void isLowerTriangle() {
        SquareMatrix m1 = new AnySquareMatrix(new double[][]{
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 0, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},

        });

        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1}
        });

        SquareMatrix m3 = new AnySquareMatrix(new double[][]{
                {1, 0, 0},
                {4, 5, 0},
                {7, 8, 9}
        });

        SquareMatrix m4 = new AnySquareMatrix(new double[][]{
                {1, 1, 0},
                {4, 5, 0},
                {7, 8, 9}
        });

        SquareMatrix m5 = new AnySquareMatrix(new double[][]{
                {1, 0, 1},
                {4, 5, 1},
                {7, 8, 9}
        });

        assertAll(
                () -> assertFalse(m1.isLowerTriangular()),
                () -> assertTrue(m2.isLowerTriangular()),
                () -> assertTrue(m3.isLowerTriangular()),
                () -> assertFalse(m4.isLowerTriangular()),
                () -> assertFalse(m5.isLowerTriangular())
        );
    }

    @Test
    void isUpperTriangle() {
        SquareMatrix m1 = new AnySquareMatrix(new double[][]{
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1},
                {0, 0, 0, 1, 1},
                {0, 0, 0, 0, 1},
        });

        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 0, 1},
        });

        assertAll(
                () -> assertTrue(m1.isUpperTriangular()),
                () -> assertFalse(m2.isUpperTriangular())
        );
    }

    @Test
    void isSymmetric() {
        SquareMatrix m1 = new AnySquareMatrix(new double[][]{
                {1, 2, 2, 2, 2},
                {2, 1, 3, 3, 3},
                {2, 3, 1, 4, 4},
                {2, 3, 4, 1, 5},
                {2, 3, 4, 5, 1},
        });

        SquareMatrix m2 = new AnySquareMatrix(new double[][]{
                {1, 2, 2, 2, 2},
                {2, 1, 3, 3, 3},
                {20, 3, 1, 40, 4},
                {2, 3, 4, 1, 5},
                {2, 3, 4, 5, 1},
        });

        assertAll(
                () -> assertTrue(m1.isSymmetric()),
                () -> assertFalse(m2.isSymmetric())
        );
    }

    @Test
    void diagonalEntries() {
        SquareMatrix m1 = new AnySquareMatrix(new double[][]{
                {1, 0, 1},
                {4, 5, 1},
                {7, 8, 9}
        });
        var a = new double[]{1, 5, 9};
        assertArrayEquals(a, m1.getDiagonalEntries());
    }

    @Test
    void add() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        });

        Matrix m3 = new AnySquareMatrix(new double[][]{
                {1, 2},
                {4, 5}
        });

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {2, 6, 10},
                {6, 10, 14},
                {10, 14, 18}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m3)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(new ZeroMatrix(5, 8))),
                () -> assertEquals(m1, m1.add(new ZeroMatrix(3))),
                () -> assertEquals(r1, m1.add(m2))
        );
    }

    @Test
    void subtract() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {2, 6, 10},
                {6, 10, 14},
                {10, 14, 18}
        });

        Matrix m3 = new AnySquareMatrix(new double[][]{
                {1, 2},
                {4, 5}
        });

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {-1, -4, -7},
                {-2, -5, -8},
                {-3, -6, -9}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m3)),
                () -> assertEquals(new ZeroMatrix(3), m1.subtract(m1)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(new ZeroMatrix(5))),
                () -> assertEquals(m1, m1.subtract(new ZeroMatrix(3))),
                () -> assertEquals(r1, m1.subtract(m2))
        );
    }

    @Test
    void multiply() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {0, 1, 2},
                {4, 5, 6},
                {8, 9, 10},
        });

        Matrix m3 = new AnySquareMatrix(new double[][]{
                {12, 13, 14, 15},
                {8, 9, 10, 11},
                {4, 5, 6, 7},
                {0, 1, 2, 3}
        });

        Matrix m4 = new AnySquareMatrix(new double[][]{
                {0, 1},
                {4, 5}
        });

        Matrix m5 = new AnySquareMatrix(new double[][]{
                {4, 5},
                {0, 1}
        });

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {16, 22, 28, 34},
                {112, 134, 156, 178},
                {208, 246, 284, 322},
                {304, 358, 412, 466}
        });

        Matrix r2 = new AnySquareMatrix(new double[][]{
                {0, 1},
                {16, 25}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m2)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(new ZeroMatrix(3, 4))),
                () -> assertEquals(new ZeroMatrix(4, 3), m1.multiply(new ZeroMatrix(4, 3))),
                () -> assertEquals(m1, m1.multiply(new IdentityMatrix(4))),
                () -> assertEquals(r1, m1.multiply(m3)),
                () -> assertEquals(r2, m4.multiply(m5))
        );
    }

    @Test
    void divide() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {0, 1},
                {4, 5}
        });

        Matrix m3 = new AnyRectangularMatrix(new double[][]{
                {0, 1, 2},
                {4, 5, 6},
                {8, 9, 10},
                {12, 13, 14}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.divide(m2)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.divide(new IdentityMatrix(3))),
                () -> assertThrows(NotInvertibleException.class, () -> m1.divide(new ZeroMatrix(4))),
                () -> assertThrows(NotInvertibleException.class, () -> m1.divide(m3)),
                () -> assertEquals(m1, m1.divide(new IdentityMatrix(4)))
        );
    }

    @Test
    void additiveInverse() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {1, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        });
        Matrix r1 = new AnySquareMatrix(new double[][]{
                {-1, -1, -2, -3},
                {-4, -5, -6, -7},
                {-8, -9, -10, -11},
                {-12, -13, -14, -15}
        });

        assertEquals(r1, m1.getAdditiveInverse());
    }

    @Test
    void power() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {1, 2},
                {3, 4}
        });

        Matrix m2 = new AnySquareMatrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 9, 9, 9},
                {6, 7, 9, 10}
        });

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {3984819343.0, 5807583730.0},
                {8711375595.0, 12696194938.0}
        });

        Matrix r2 = new AnySquareMatrix(new double[][]{
                {1220308.0, 1380938.0, 1606548.0, 1767178.0},
                {2932556.0, 3318570.0, 3860740.0, 4246754.0},
                {3852558.0, 4359672.0, 5071932.0, 5579046.0},
                {3635976.0, 4114582.0, 4786800.0, 5265406.0}
        });

        System.out.println(m2.power(7));

        assertAll(
                () -> assertEquals(m1, m1.power(1)),
                () -> assertEquals(new IdentityMatrix(4), m2.power(0)),
                () -> assertEquals(r1, m1.power(14)),
                () -> assertEquals(r2, m2.power(5))
        );
    }

    @Test
    void multiplyScalar() {
        Matrix m1 = new AnySquareMatrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        });

        Matrix r1 = new AnySquareMatrix(new double[][]{
                {-1, -2, -3},
                {-4, -5, -6},
                {-7, -8, -9},
        });

        Matrix r2 = new AnySquareMatrix(new double[][]{
                {05, 10, 15},
                {20, 25, 30},
                {35, 40, 45},
        });

        assertAll(
                () -> assertEquals(r1, m1.multiply(-1)),
                () -> assertEquals(new ZeroMatrix(3, 3), m1.multiply(0)),
                () -> assertEquals(m1, m1.multiply(1)),
                () -> assertEquals(r2, m1.multiply(5))
        );
    }
}

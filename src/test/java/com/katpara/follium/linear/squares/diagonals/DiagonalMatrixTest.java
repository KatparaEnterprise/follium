package com.katpara.follium.linear.squares.diagonals;

import com.katpara.follium.exceptions.InvalidParameterProvidedException;
import com.katpara.follium.exceptions.linears.*;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.constants.IdentityMatrix;
import com.katpara.follium.linear.constants.ZeroMatrix;
import com.katpara.follium.linear.rectangulars.AnyRectangularMatrix;
import com.katpara.follium.linear.squares.AnySquareMatrix;
import com.katpara.follium.linear.squares.DiagonalMatrix;
import com.katpara.follium.linear.squares.SquareMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalMatrixTest {
    private SquareMatrix m1, m2;

    @BeforeEach
    void setUp() {
        m1 = new DiagonalMatrix(new double[][]{
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 3}
        });

        m2 = new DiagonalMatrix(new double[]{1, 2, 3, 4});

    }

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new DiagonalMatrix(new double[0])),
                () -> assertThrows(InvalidMatrixDimensionProvidedException.class, () -> new DiagonalMatrix(new double[0][0])),
                () -> assertThrows(NotSquareMatrixException.class, () -> new DiagonalMatrix(new double[][]{
                        {1, 2, 3, 4},
                        {1, 2, 3, 4}
                })),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> new DiagonalMatrix(new double[][]{
                        {1, 2, 0},
                        {0, 2, 0},
                        {0, 0, 0},
                })),
                () -> new DiagonalMatrix(new double[]{1, 2, 3})
        );
    }

    @Test
    void diagonalEntries() {

        assertAll(
                () -> assertArrayEquals(new double[]{1, 2, 3}, m1.getDiagonalEntries()),
                () -> assertArrayEquals(new double[]{1, 2, 3, 4}, m2.getDiagonalEntries())
        );
    }

    @Test
    void determinant() {
        assertAll(
                () -> assertEquals(6, m1.determinant()),
                () -> assertEquals(24, m2.determinant())
        );

    }

    @Test
    void size() {
        assertAll(
                () -> assertArrayEquals(new int[]{3, 3}, m1.size()),
                () -> assertArrayEquals(new int[]{4, 4}, m2.size())
        );
    }

    @Test
    void array() {
        assertAll(
                () -> assertArrayEquals(new double[]{1, 0, 0, 0, 2, 0, 0, 0, 3}, m1.toArray()),
                () -> assertArrayEquals(new double[]{1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 0, 4}, m2.toArray())
        );
    }

    @Test
    void rowAndColumn() {
        assertAll(
                () -> assertThrows(RowOutOfBoundException.class, () -> m1.getRow(-1)),
                () -> assertThrows(RowOutOfBoundException.class, () -> m1.getRow(3)),
                () -> assertThrows(RowOutOfBoundException.class, () -> m2.getRow(-1)),
                () -> assertThrows(RowOutOfBoundException.class, () -> m2.getRow(4)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m1.getColumn(-1)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m1.getColumn(3)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m2.getColumn(-1)),
                () -> assertThrows(ColumnOutOfBoundException.class, () -> m2.getColumn(4)),
                () -> assertArrayEquals(new double[]{0, 2, 0}, m1.getRow(1)),
                () -> assertArrayEquals(new double[]{0, 0, 3}, m1.getColumn(2)),
                () -> assertArrayEquals(new double[]{0, 0, 3, 0}, m2.getRow(2)),
                () -> assertArrayEquals(new double[]{0, 0, 0, 4}, m2.getColumn(3))
        );
    }

    @Test
    void add() {
        Matrix m3 = new DiagonalMatrix(new double[]{1, 2, 3});
        Matrix m4 = new AnySquareMatrix(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

        Matrix r1 = new DiagonalMatrix(new double[]{2, 3, 4});
        Matrix r2 = new DiagonalMatrix(new double[]{2, 4, 6});
        Matrix r3 = new AnySquareMatrix(new double[]{2, 2, 3, 4, 7, 6, 7, 8, 12});

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m2)),
                () -> assertEquals(m1, m1.add(new ZeroMatrix(3))),
                () -> assertEquals(m2, m2.add(new ZeroMatrix(4))),
                () -> assertEquals(r1, m1.add(new IdentityMatrix(3))),
                () -> assertEquals("DiagonalMatrix", m1.add(new IdentityMatrix(3)).getClass().getSimpleName()),
                () -> assertEquals(r2, m1.add(m3)),
                () -> assertEquals("DiagonalMatrix", m1.add(m3).getClass().getSimpleName()),
                () -> assertEquals(r3, m1.add(m4)),
                () -> assertEquals("AnySquareMatrix", m1.add(m4).getClass().getSimpleName())
        );
    }

    @Test
    void subtract() {
        Matrix m3 = new DiagonalMatrix(new double[]{2, 4, 6});
        Matrix m4 = new AnySquareMatrix(new double[][]{
                {2, 3, 4},
                {2, 3, 4},
                {2, 3, 4}
        });
        Matrix m5 = new AnySquareMatrix(new double[][]{
                {2, 3, 4, 5},
                {2, 3, 4, 5},
                {2, 3, 4, 5},
                {2, 3, 4, 5}
        });

        Matrix r1 = new DiagonalMatrix(new double[]{0, 1, 2});
        Matrix r2 = new AnySquareMatrix(new double[][]{
                {-1, -3, -4},
                {-2, -1, -4},
                {-2, -3, -1}
        });
        Matrix r3 = new AnySquareMatrix(new double[][]{
                {-1, -3, -4, -5},
                {-2, -1, -4, -5},
                {-2, -3, -1, -5},
                {-2, -3, -4, -1}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m2)),
                () -> assertEquals(new ZeroMatrix(3), m1.subtract(m1)),
                () -> assertEquals(new ZeroMatrix(4), m2.subtract(m2)),
                () -> assertEquals(m1, m1.subtract(new ZeroMatrix(3))),
                () -> assertEquals(m2, m2.subtract(new ZeroMatrix(4))),
                () -> assertEquals(r1, m1.subtract(new IdentityMatrix(3))),
                () -> assertEquals(m1, m3.subtract(m1)),
                () -> assertEquals("DiagonalMatrix", m3.subtract(m1).getClass().getSimpleName()),
                () -> assertEquals(r2, m1.subtract(m4)),
                () -> assertEquals(r3, m2.subtract(m5)),
                () -> assertEquals("AnySquareMatrix", m2.subtract(m5).getClass().getSimpleName())
        );
    }

    @Test
    void multiply() {
        Matrix m3 = new DiagonalMatrix(new double[]{3, 4, 5});
        Matrix m4 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3, 4, 5},
                {9, 8, 7, 6, 5},
                {0, 0, 4, 5, 6}
        });

        Matrix r1 = new DiagonalMatrix(new double[]{3, 8, 15});
        Matrix r2 = new AnyRectangularMatrix(new double[][]{
                {1, 2, 3, 4, 5},
                {18, 16, 14, 12, 10},
                {0, 0, 12, 15, 18}
        });

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.multiply(m2)),
                () -> assertEquals(m1, m1.multiply(new IdentityMatrix(3))),
                () -> assertEquals(new ZeroMatrix(3), m1.multiply(new ZeroMatrix(3))),
                () -> assertEquals(new ZeroMatrix(3, 5), m1.multiply(new ZeroMatrix(3, 5))),
                () -> assertEquals(r1, m1.multiply(m3)),
                () -> assertEquals("DiagonalMatrix", m1.multiply(m3).getClass().getSimpleName()),
                () -> assertEquals(r2, m1.multiply(m4)),
                () -> assertEquals("AnyRectangularMatrix", m1.multiply(m4).getClass().getSimpleName())
        );
    }
}
package com.katpara.follium.linear.squares.diagonals;

import com.katpara.follium.exceptions.InvalidParameterProvidedException;
import com.katpara.follium.exceptions.linears.MatrixDimensionMismatchException;
import com.katpara.follium.exceptions.linears.NotSquareMatrixException;
import com.katpara.follium.linear.Matrix;
import com.katpara.follium.linear.squares.AnySquareMatrix;
import com.katpara.follium.linear.squares.DiagonalMatrix;
import com.katpara.follium.linear.squares.SquareMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalMatrixTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void constructor() {
        assertAll(
                () -> assertThrows(NotSquareMatrixException.class, () -> new DiagonalMatrix(new double[][]{
                        {1, 2, 3},
                        {1, 2, 3}
                })),
                () -> assertThrows(InvalidParameterProvidedException.class, () -> new DiagonalMatrix(new double[][]{
                        {1, 2, 3},
                        {1, 2, 3},
                        {1, 2, 3}
                })),
                () -> new DiagonalMatrix(new double[][]{{1, 0, 0}, {0, 2, 0}, {0, 0, 3}}),
                () -> assertArrayEquals(new double[]{1, 2, 3}, new DiagonalMatrix(new double[][]{
                        {1, 0, 0},
                        {0, 2, 0},
                        {0, 0, 3}}
                ).getDiagonalEntries())
        );
    }

    @Test
    void determinant() {
        SquareMatrix m = new DiagonalMatrix(new double[][]{
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 3}}
        );

        assertEquals(6, m.determinant());
    }

    @Test
    void toArray() {
        Matrix m = new DiagonalMatrix(new double[]{1, 2, 3});
        assertArrayEquals(new double[]{1, 0, 0, 0, 2, 0, 0, 0, 3}, m.toArray());
    }

    @Test
    void getRowAndColumn() {
        Matrix m = new DiagonalMatrix(new double[]{1, 2, 3});
        assertAll(
                () -> assertArrayEquals(new double[]{0, 2, 0}, m.getRow(1)),
                () -> assertArrayEquals(new double[]{0, 2, 0}, m.getColumn(1))
        );
    }

    @Test
    void equals() {
        Matrix m1 = new AnySquareMatrix(new double[][]{{1, 0, 0}, {0, 5, 0}, {0, 0, 9}});
        Matrix m2 = new DiagonalMatrix(new double[]{1, 5, 9});
        assertEquals(m2, m1);
    }

    @Test
    void add() {
        Matrix m1 = new DiagonalMatrix(new double[]{1, 2, 3});
        Matrix m2 = new DiagonalMatrix(new double[]{1, 5, 9});
        Matrix m3 = new DiagonalMatrix(new double[]{1, 5});
        Matrix m5 = new AnySquareMatrix(new double[][]{{1, 2}, {4, 5}});
        Matrix m6 = new AnySquareMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});

        Matrix r1 = new DiagonalMatrix(new double[]{2, 7, 12});
        Matrix r2 = new AnySquareMatrix(new double[][]{{2, 2, 3}, {4, 7, 6}, {7, 8, 12}});

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m3)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.add(m5)),
                () -> assertEquals(r1, m1.add(m2)),
                () -> assertEquals(r2, m1.add(m6))
        );
    }

    @Test
    void subtract() {
        Matrix m1 = new DiagonalMatrix(new double[]{1, 2, 3});
        Matrix m2 = new DiagonalMatrix(new double[]{1, 5, 9});
        Matrix m3 = new DiagonalMatrix(new double[]{1, 5});
        Matrix m5 = new AnySquareMatrix(new double[][]{{1, 2}, {4, 5}});
        Matrix m6 = new AnySquareMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});

        Matrix r1 = new DiagonalMatrix(new double[]{0, 3, 6});
        Matrix r2 = new AnySquareMatrix(new double[][]{{0, 2, 3}, {4, 3, 6}, {7, 8, 6}});

        assertAll(
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m3)),
                () -> assertThrows(MatrixDimensionMismatchException.class, () -> m1.subtract(m5)),
                () -> assertEquals(r1, m2.subtract(m1)),
                () -> assertEquals(r2, m6.subtract(m1))
        );
    }
}
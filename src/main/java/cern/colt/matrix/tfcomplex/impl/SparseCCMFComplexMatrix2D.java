/*
Copyright (C) 1999 CERN - European Organization for Nuclear Research.
Permission to use, copy, modify, distribute and sell this software and its documentation for any purpose 
is hereby granted without fee, provided that the above copyright notice appear in all copies and 
that both that copyright notice and this permission notice appear in supporting documentation. 
CERN makes no representations about the suitability of this software for any purpose. 
It is provided "as is" without expressed or implied warranty.
 */
package cern.colt.matrix.tfcomplex.impl;

import cern.colt.matrix.tfcomplex.FComplexMatrix2D;

/**
 * Sparse column-compressed-modified 2-d matrix holding <tt>complex</tt>
 * elements. Each column is stored as SparseFComplexMatrix1D.
 * 
 * @author Piotr Wendykier (piotr.wendykier@gmail.com)
 * 
 */
public class SparseCCMFComplexMatrix2D extends WrapperFComplexMatrix2D {

    private static final long serialVersionUID = 1L;
    private SparseFComplexMatrix1D[] elements;

    /**
     * Constructs a matrix with a given number of rows and columns. All entries
     * are initially <tt>0</tt>.
     * 
     * @param rows
     *            the number of rows the matrix shall have.
     * @param columns
     *            the number of columns the matrix shall have.
     * @throws IllegalArgumentException
     *             if
     *             <tt>rows<0 || columns<0 || (double)columns*rows > Integer.MAX_VALUE</tt>
     *             .
     */
    public SparseCCMFComplexMatrix2D(int rows, int columns) {
        super(null);
        try {
            setUp(rows, columns);
        } catch (IllegalArgumentException exc) { // we can hold rows*columns>Integer.MAX_VALUE cells !
            if (!"matrix too large".equals(exc.getMessage()))
                throw exc;
        }
        elements = new SparseFComplexMatrix1D[columns];
        for (int i = 0; i < columns; ++i)
            elements[i] = new SparseFComplexMatrix1D(rows);
    }

    public SparseFComplexMatrix1D[] elements() {
        return elements;
    }

    public float[] getQuick(int row, int column) {
        return elements[column].getQuick(row);
    }

    public void setQuick(int row, int column, float[] value) {
        elements[column].setQuick(row, value);
    }

    public void setQuick(int row, int column, float re, float im) {
        elements[column].setQuick(row, re, im);
    }

    public void trimToSize() {
        for (int c = 0; c < columns; c++) {
            elements[c].trimToSize();
        }
    }

    public SparseFComplexMatrix1D viewColumn(int column) {
        return elements[column];
    }

    protected FComplexMatrix2D getContent() {
        return this;
    }

    public FComplexMatrix2D like(int rows, int columns) {
        return new SparseCCMFComplexMatrix2D(rows, columns);
    }
}

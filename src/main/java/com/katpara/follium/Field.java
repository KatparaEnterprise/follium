package com.katpara.follium;

import com.katpara.follium.util.Rounding;

/**
 * A field is simply an any object created by this library.
 * A field object has a certain set of properties defined here.
 *
 * @param <F> the field type
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public interface Field<F> {

    /**
     * A fields can be added to another field of the same type.
     *
     * @param f the other field
     *
     * @return the resulting field
     */
    F add(F f);

    /**
     * A fields can be subtracted from another field of the same type.
     *
     * @param f the other field
     *
     * @return the resulting field
     */
    F subtract(F f);

    /**
     * A field can multiply with another of the same type.
     *
     * @param f the other field
     *
     * @return the resulting field
     */
    F multiply(F f);

    /**
     * A field can divided by another field of the same type.
     *
     * @param f the other field
     *
     * @return the resulting field
     */
    F divide(F f);

    /**
     * The method returns the field with the given power.
     *
     * @param power the power
     *
     * @return the powered field
     */
    F power(final int power);

    /**
     * The method returns the additive inverse of the field.
     *
     * @return the additive inverse field
     */
    F getAdditiveInverse();

    /**
     * The method returns the multiplicative inverse of the field.
     *
     * @return the multiplicative inverse field
     */
    F getMultiplicativeInverse();

    /**
     * The method returns a string representing the field upto
     * specified decimal points.
     *
     * @param decimals the decimal precision
     *
     * @return the string representing the field
     */
    String toString(Rounding.Decimals decimals);
}

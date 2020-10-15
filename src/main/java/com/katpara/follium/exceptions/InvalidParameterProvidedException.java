package com.katpara.follium.exceptions;

/**
 * The exception is thrown when an invalid parameter is passed as an argument.
 *
 * @author Mehul Katpara
 * @since 1.0.0
 */
public class InvalidParameterProvidedException extends RuntimeException {
    private static final long serialVersionUID = 3727500311644363939L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public InvalidParameterProvidedException() {
        super(ExceptionMessages.INVALID_PARAMETER_PROVIDED_MESSAGE);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidParameterProvidedException(final String message) {
        super(message);
    }
}

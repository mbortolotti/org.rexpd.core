package org.rexpd.core.base;

public class AnalysisException extends Exception {

	private static final long serialVersionUID = 2696002484412014091L;
	
	public static final String DEFAULT_MESSAGE = "Exception while running analysis";
	
	/**
	 * Creates an {@code AnalysisException} with the specified message.
	 * @param message The associated message
	 */
	public AnalysisException(String message) {
		super(message);
	}

}

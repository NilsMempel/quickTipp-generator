
package util;

/**
 * {@code IllegalUserInputException} is thrown when the user makes an illegal input in the console.
 * 
 * @author Nils Mempel
 *
 */
public class IllegalUserInputException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalUserInputException(String errorMessage) {
		super(errorMessage);
	}

}

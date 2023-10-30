// TODO: Auto-generated Javadoc
public class ExpressionEvaluator {
	// These are the required error strings for that MUST be returned on the appropriate error 
	// for the JUnit tests to pass. DO NOT CHANGE!!!!!
	private static final String PAREN_ERROR = "Paren Error: ";
	private static final String OP_ERROR = "Op Error: ";
	private static final String DATA_ERROR = "Data Error: ";
	private static final String DIV0_ERROR = "Div0 Error: ";

	// The placeholder for the two stacks
	private GenericStack<Double> dataStack;
	private GenericStack<String>  operStack;
	
	/**
	 * Convert to tokens. Takes a string and splits it into tokens that
	 * are either operators or data. This is where you should convert 
	 * implicit multiplication to explicit multiplication. It is also a candidate
	 * for recognizing negative numbers, and then including that negative sign
	 * as part of the appropriate data token.
	 *
	 * @param str the str
	 * @return the string[] of tokens
	 */
	private String[] convertToTokens(String str) {
		return null;
	}
	
	/**
	 * Evaluate expression. This is it, the big Kahuna....
	 * It is going to be called by the GUI (or the JUnit tester),
	 * and:
	 * a) convert the string to tokens. This should detect and handle
	 *    implicit multiplication:
	 *    -- examples: ")   (" or "9(" or ")3" -- 
	 *    and negation:
	 *    -- examples: "7 + -9" or "-.9" or "-3(" but not ")-3"
	 * b) if conversion successful, perform static error checking
	 *    - Paren Errors
	 *    - Op Errors 
	 *    - Data Errors
	 * c) if static error checking is successful:
	 *    - evaluate the expression, catching any runtime errors.
	 *      For the purpose of this project, the only runtime errors are 
	 *      divide-by-0 errors.
	 *
	 * @param str the str
	 * @return the string
	 */
	protected String evaluateExpression(String str) {
        dataStack =  new GenericStack<Double>();
		operStack =  new GenericStack<String>();
		
		return ("You need to code this!");
	}

}

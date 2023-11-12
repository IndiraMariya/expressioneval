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
		str = str.replaceAll("([\\+\\*\\/\\-])\\-\\(", "$1-1(");
		
		String startNeg = "^(\\s*)\\-([0-9\\.])";
		str = str.replaceAll(startNeg, "$1Neg$2");
		String neg = "([\\+\\/\\*\\-\\(]\\s*)\\-([0-9\\.])";
		str = str.replaceAll(neg, "$1 Neg$2");
		

		str = str.replaceAll("\\)\\s*\\(", ") * (");
		str = str.replaceAll("(\\d)(?=\\()", "$1*");
		str = str.replaceAll("(\\.\\d)(?=\\()", "$1*");
		str = str.replaceAll("(?<=\\))(\\d)", "*$1");
		str = str.replaceAll("(?<=\\))(\\.\\d)", "*$1");

		
		String op = "([\\+\\-\\/\\*\\(\\)])";
		str = str.replaceAll(op, " $1 ");
		
		str = str.replaceAll("Neg(\\d*)", "-$1");
		
		str = str.trim();
		String[] tokens = str.split("\\s+");
		
		return tokens;
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
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			char character = str.charAt(i);
			String sub = str.substring(i, i+1);
			if (!sub.matches("[0-9\\.\\(\\)\\+\\-\\*\\/\\s]"))
				return "Data Error: ";
			if (character == '(') {
				if (i < str.length()-1 && str.charAt(i+1) == '+') {
					return "Op Error: ";
				}
				count++;
			}
			if (character == ')') {
				if (str.length()== 1) {
					return "Paren Error: ";
				}
				int num = str.charAt(i-1) - '0';
				if (num < 0 || num > 9) {
					if (str.charAt(i-1) == '*' || str.charAt(i-1) == '+'|| str.charAt(i-1) == '\\'|| str.charAt(i-1) == '-')
						return "Op Error:";
					else {
						return "Paren Error: ";
					}
				}
				count--;
			}
			if (count < 0)
				return "Paren Error: more Rparen";
		}
		if (count !=0)
			return "Paren Error: paren pairs dont match";
		
		
 		String tokens[] = convertToTokens(str);
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			
			if (i == 0 && !token.matches("^\\.[0-9]+|^\\d+\\.$|[0-9]+\\.[0-9]+|[0-9\\(\\-]|[0-9]+|^-?[0-9]+\\.?[0-9]*$|^-[.0-9]+$")) {
				if (token.matches("[\\+\\*\\/]"))
					return "Op Error: ";
			    return "Data Error: ";
			}

			if (i >= 1) {
				if (i >=2) {
					if (token.matches("[\\+\\*\\/\\-]") && tokens[i-1].matches("[\\+\\*\\/\\-]") && tokens[i-2].matches("[\\+\\*\\/\\-]") )
						return "Op Error: ";
				}
				if (token.matches("\\)") && tokens[i-1].matches("[\\+\\*\\/\\-]"))
					return "Op Error: ";
				if (token.matches("^\\.[0-9]+|^\\d+\\.$|[0-9]+\\.[0-9]+|[0-9\\(]|[0-9]+") && !tokens[i-1].matches("[\\+\\*\\/\\-\\)\\(]"))
					return "Data Error: ";
				if (token.matches("[\\+\\*\\/\\-]") && !tokens[i-1].matches("^\\.[0-9]+|^\\d+\\.$|[0-9]+\\.[0-9]+|[0-9\\(\\)]|[0-9]+|^-?[0-9]+\\.?[0-9]*$"))
					 return "Op Error: ";
			}
			
			if (i == tokens.length-1 && token.matches("[\\+\\*\\/\\-]"))
				return "Op Error: ";

			if (token.matches("[\\+\\-\\*\\/\\(\\)]")) {
				if (token.equals(")")) {
					while (!operStack.peek().equals("(")) {
						if (operStack.peek().equals("/")&& dataStack.peek() == 0) {
							return "Div0 Error: ";
						}
						evaluateTOS();
					}
					if (operStack.peek().equals("("))
						operStack.pop();
				}
				else if (operStack.size()== 0 || precedence(token) > precedence(operStack.peek()))
					operStack.push(token);
				else {
					if (operStack.peek().equals("("))
						operStack.push(token);
					else {
						while (dataStack.size() != 1 && !operStack.peek().equals("(")) {
							if (operStack.peek().equals("/")&& dataStack.peek() == 0) {
								return "Div0 Error: ";
							}
					        evaluateTOS();
					    }
						operStack.push(token);
					}
				}	
			}
			else {
				Double data = Double.parseDouble(token);
				dataStack.push(data);
			}
		}
		while (dataStack.size() != 1) {
			if (operStack.peek().equals("/")&& dataStack.peek() == 0) {
				return "Div0 Error: ";
			}
			evaluateTOS();
		}
		return (str + "=" + dataStack.pop());
	}
	
	private int precedence(String op1) {
		if (op1.matches("\\("))
			return 3;
		if (op1.matches("[\\/\\*]"))
			return 2;
		return 1;
	}
	
	private void evaluateTOS(){
		Double result = 0.0;
		String op = operStack.pop();
		Double d2 = dataStack.pop();
		Double d1 = dataStack.pop();
		if (op.equals("+"))
		    result = d1 + d2;
		if (op.equals("-"))
		    result = d1 - d2;
		if (op.equals("/"))
		    result = d1 / d2;
		if (op.equals("*"))
		    result = d1 * d2;
		dataStack.push(result);	
		
	}
	
	

}

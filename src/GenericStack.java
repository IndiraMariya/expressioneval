import java.util.ArrayList;
import java.util.EmptyStackException;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericStack.
 *
 * @param <E> the element type
 */

/**
 * @author Your name here
 *
 */
public class GenericStack<E> {

	/** Using an ArrayList as the data structure for the stack */
	private ArrayList<E> stack;
	
	/**
	 * Instantiates a new generic stack.
	 */
	public GenericStack() {
		stack = new ArrayList<E>();
	}
	
	/**
	 * Checks if the stack is empty.
	 *
	 * @return true, if the stack is empty
	 */
	public boolean empty() {
		return stack.isEmpty();	
	}

	/**
	 * Gets the size of the stack. The Top of Stack is size - 1;
	 *
	 * @return the size
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Peek - this is a Java stack function that returns the object at the
	 * top of the stack without removing it from the stack.
	 *
	 * @return the object at the top of the stack
	 * @throws EmptyStackException if an attempt was made to peek on an empty stack
	 */
	public E peek() throws EmptyStackException {
		EmptyStackException e = new EmptyStackException();
		E topVal = null;
		if (!stack.isEmpty())
			topVal = stack.get(stack.size() -1);
		else {
			throw e;
		}
		return topVal;
	}

	/**
	 * Pops the object off of the top of the stack, and returns it. The ArrayList
	 * remove method is used to implement this.
	 *
	 * @return the object at the top of the stack
	 * @throws EmptyStackException if an attempt was made to pop on an empty stack
	 */
	public E pop() throws EmptyStackException {
		EmptyStackException e = new EmptyStackException();
		if (stack.isEmpty()) {
			throw e;
		}
		E var = stack.get(stack.size() -1);
		stack.remove(stack.size()-1);
		return var;
	}
	
	/**
	 * Pushes an object onto the stack using the ArrayList add method. This also
	 * adjusts the size of the stack directly...
	 *
	 * @param o the object to be added to the stack
	 */
	public void push(E o) {
		stack.add(o);
	}

	/**
	 * To string
	 *
	 * @return the string
	 */
	@Override
   	public String toString() {
	   return("stack: "+stack.toString());
	}
}

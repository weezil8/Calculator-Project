// Post Fix Calculator
// Calculator that takes a users inputed equation and converts it to postfix notation and solves it
// Using stacks to do the math 
// Capable of taking decimals and working with them
// By Shane Leinbach
// Published 10/17/2018


import java.text.DecimalFormat;
import java.util.*;

public class Calculator {

	public static void main(String[] args) {
		//Scanner for input
		Scanner scan = new Scanner(System.in);
		//Variable to determine if to repeat the program
		boolean calculated = false;
		//Runs the whole body of the program
		while (calculated == false) {
			System.out.println("Please input an equation:");
			String input = scan.nextLine();
			//Runs the transform
			System.out.println(transform(input));
			//Evaluates the translated equation
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println(df.format(evaluatePostfix(transform(input))));

			//A way to run many equations without having to restart the whole program
			System.out.println("Would you like to run another equation y/n?");
			String choice = scan.nextLine().toLowerCase();

			//Checks the users input when asked if they want to rerun the program
			if (choice.equalsIgnoreCase("y")) {
				calculated = false;
			} else if (choice.equalsIgnoreCase("n")) {
				calculated = true;
				System.out.println("GoodBye!");
				System.exit(0);

			}else {
				System.out.println("PUT IN A COMMAND GOODBYE");
				System.exit(0);
			}
		}
	}

	// Method taken from geeks for geeks that determines the precedence of the
	// operators
	// By taking individual characters of the input equation
	// @param ch is a character taken from the operends and has its precedence determined
	// @https://www.geeksforgeeks.org/stack-set-2-infix-to-postfix/
	static int Prec(char ch) {
		switch (ch) {
		case '+':
		case '-':
			return 1;

		case '*':
		case '/':
			return 2;

		case '^':
			return 3;
		}
		return -1;
	}

	// Method taken from geeks for geeks that Takes the given equation and
	// translates it to postfix to be solved 
	// I modified it to handle multidigit numbers 
	// @param input is a string of a users inputed equation
	// @https://www.geeksforgeeks.org/stack-set-2-infix-to-postfix/
	static String transform(String input) {
		// empty string to return the transformed equation
		String output = new String("");
		// Creates an empty stack to store in
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < input.length(); i++) {
			char x = input.charAt(i);

			//If there is a space in the loop it moves on because when the program gets to
			//the stack it thinks there is an empty value and breaks
			if (x == ' ') {
				continue;	
			}

			
			else if (Character.isDigit(input.charAt(i)) || x == '.' ) {
				output = output + x;
				//This handles the multidigit numbers by determining if there is another digit
				//Next or if there is another operator or blank space
				// Looks to the next number after the decimal and then determines if it should combine the numbers or move on
				if (i + 1 >= input.length() ||(!Character.isDigit(input.charAt(i + 1)) && input.charAt(i+1) != '.')) {
					output += ' ';
				}
			} else if (x == '(')
				stack.push(x);

			// If the scanned character is an ')', pop and output from the stack
			// until an '(' is encountered.
			else if (x == ')') {
				while (!stack.isEmpty() && stack.peek() != '(')
					output += stack.pop();

				if (!stack.isEmpty() && stack.peek() != '(')
					return "Invalid Expression"; // invalid expression
				else
					stack.pop();
			} else // an operator is encountered
			{
				while (!stack.isEmpty() && Prec(x) <= Prec(stack.peek()))
					output += stack.pop();
				stack.push(x);
			}
		}
		// pop all the operators from the stack
		while (!stack.isEmpty())
		{
			output += stack.pop();
		}
		// returns end result all converted
		return output;
	}
	// Method that takes the post fix expression and evaluates it
	// Found and modified from geeks for geeks
	//Now returns decimal values as well as handling the multidigit numbers in the equation
	// @param postFixInput is the equation translated into post fix notation to be solved by this method
	// @https://www.geeksforgeeks.org/stack-set-4-evaluation-postfix-expression/
	static double evaluatePostfix(String postFixInput) {
		// create a stack
		Stack<Double> stack = new Stack<>();

		// Scan all characters one by one
		for (int i = 0; i < postFixInput.length(); i++) {
			char c = postFixInput.charAt(i);

			// If the checked character is an space it just continues to the next character
			if (c == ' ')
				continue;
			// If the scanned character is an operand
			// (number here),extract the number
			// Push it to the stack.
			else if (Character.isDigit(c) || c == '.') {
				double n = 0;

				// extract the characters and store it in num
				// converts the string and takes it to ints
				String num = "";
				while(Character.isDigit(c) || c== '.')
				{
					num += c;
					i++;
					c = postFixInput.charAt(i);
				}
				n = Double.parseDouble(num);
				i--;
				// push the number in stack
				stack.push(n);
			}
			// If the scanned character is an operator, pop two
			// elements from stack apply the operator
			else {
				Double val1 = stack.pop();
				Double val2 = stack.pop();

				switch (c) {
				case '+':
					stack.push(val2 + val1);
					break;

				case '-':
					stack.push(val2 - val1);
					break;

				case '/':
					stack.push(val2 / val1);
					break;

				case '*':
					stack.push(val2 * val1);
					break;
				}
			}
		}
		return stack.pop();
	}
}
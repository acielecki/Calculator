/**
* The Calc program is a calculator that can evaluate
* expressions which use the + , - , * and / operators.
* It takes in a string from the user and converts 
* this expression to postfix notation to then 
* calculate the answer
* 
* @author  Alissa Cielecki
* @version 1.0
* @since   2019-10-21
*/
import java.util.Scanner;
import java.util.LinkedList;
@SuppressWarnings("resource")

public class Calc {
	static String input;
	static LinkedList<String> postFix = new LinkedList<String>();
	
	public static void main(String[] args) {
		getInput();
		postFix(toList());
		System.out.println("That equals " + calculate());
	}
	
	//takes input from the user and removes any spaces
	public static void getInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an expression to calculate:");
		input = in.nextLine();
		
		//remove spaces 
		input = input.replaceAll("\\s", "");
	}
	
	//turns the input into a LinkedList of Strings, accounting for multiple digit numbers
	public static LinkedList<String> toList() {
		LinkedList<String> inFix = new LinkedList<String>();
		String num = "";
		//traverses the input string, adding each element to the list
		for(int i = 0; i < input.length(); i++) {
			//checks if character is a number and concatenates consecutive digits in num
			if(Character.isDigit(input.charAt(i)) || input.charAt(i)=='.') {
				num+=input.charAt(i);
				if(i+1>=input.length()) {  //if last item, add num to list
					inFix.add(num);
				}
			}
			else { 
				//when the next char is not a digit, add num to list
				if(num!="") { 
					inFix.add(num);
					num = ""; //reset num string
				}
				inFix.add(input.charAt(i) + ""); //automatically add non-numeric items to list
			}
		}
		return inFix;
	}
	
	//takes inFix list from toList() and turns it into postfix notation
	public static LinkedList<String> postFix(LinkedList<String> inFix) {
		 LinkedList<String> stack = new LinkedList<>(); 
		 
	     //traverses inFix list
	     for (int i = 0; i < inFix.size(); ++i) { 
	    	 String s = inFix.get(i); 
	    	 //automatically add numbers to postFix
	         if (isNumeric(s)) {
	            postFix.add(s); 
	         } //automatically push all ( to stack
	         else if (s.equals("(")) {
	        	 stack.push(s); 
	         } //when ) is reached, pop all operators and add to postFix
	         else if (s.equals(")")) { 
	             while (!stack.isEmpty() && !(stack.peek().equals("("))) {
	            	 postFix.add(stack.pop()); 
	             }
	             stack.pop(); //remove last (
	         }
	         else { //pop all operators of lesser or equal value and add to postFix
	        	 while (!stack.isEmpty() && precedence(s) <= precedence(stack.peek())) { 
	        		 postFix.add(stack.pop()); 
	             }	 
	        	 //add operator to stack
	             stack.push(s); 
	         } 
	     }
	     //empty stack into postFix
	     while (!stack.isEmpty()) { 
	    	postFix.add(stack.pop()); 
	     } 
	     return postFix;
	}
	
	//evaluates the expression through implementation of a stack
	public static double calculate() {
		LinkedList<Double> stack = new LinkedList<Double>();
		
		//traverses the postfix list, removing numbers and operators to do operations as it goes
		for(int i = 0; i < postFix.size(); i++) {
			String s = postFix.get(i);

			if(isNumeric(s)) { //push numeric value to stack
				stack.push(Double.parseDouble(s));
			}
			else { 
				double right = stack.pop(); //get numbers to operate on
				double left = stack.pop();
				switch(s) { //do calculation then, add new number to top of stack
					case "+":
						stack.push(left+right);
						break;
					case "-":
						stack.push(left-right);
						break;
					case "*":
						stack.push(left*right);
						break;
					case "/":
						stack.push(left/right);
						break;
				}
			}
		}
		return stack.pop(); //last value in stack is the answer
	}
	
	//checks to see if the value in our String lists is a double
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(Exception e){  
		    return false;  
		  }  
	}
	
	//returns a value for the operator type to determine precedence
	public static int precedence(String operator) {
		switch(operator) {
		 	case "+": 
	        case "-": 
	            return 1; 
	        case "*": 
	        case "/": 
	            return 2; 
	        default:
	        	return -1;
		}
	}
	
}

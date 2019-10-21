import java.util.Scanner;
import java.util.LinkedList;
@SuppressWarnings("resource")

public class Calc {
	static String input;
	static LinkedList<String> inFix = new LinkedList<String>();
	static LinkedList<String> postFix = new LinkedList<String>();
	
	public static void main(String[] args) {
		getInput();
		removeSpaces();
		toList();
		postFix();
		System.out.println("That equals " + evaluate());
	}
	
	public static void getInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an expression to calculate:");
		input = in.nextLine();
	}
	
	public static void removeSpaces() {
		input = input.replaceAll("\\s", "");
	}
	
	public static LinkedList<String> toList() {
		String num = "";
		for(int i = 0; i < input.length(); i++) {
			if(Character.isDigit(input.charAt(i)) || input.charAt(i)=='.') {
				num+=input.charAt(i);
				if(i+1>=input.length()) {
					inFix.add(num);
				}
			}
			else {
				if(num!="") {
					inFix.add(num);
					num = "";
				}
				inFix.add(input.charAt(i) + "");
			}
		}
		return inFix;
	}
	
	public static LinkedList<String> postFix() {
		 LinkedList<String> stack = new LinkedList<>(); 
	        String s;
	        for (int i = 0; i < inFix.size(); ++i) { 
	            	s = inFix.get(i); 
	            if (isNumeric(s)) {
	            	postFix.add(s); 
	            } else if (s.equals("(")) {
	            	stack.push(s); 
	            } else if (s.equals(")")) { 
	                while (!stack.isEmpty() && !(stack.peek().equals("("))) {
	                	 postFix.add(stack.pop()); 
	                }
	                stack.pop(); 
	            } 
	            else { 
	                while (!stack.isEmpty() && precedence(s) <= precedence(stack.peek())) { 
	                    postFix.add(stack.pop()); 
	                } 
	                stack.push(s); 
	            } 
	        }
	        while (!stack.isEmpty()) { 
	            postFix.add(stack.pop()); 
	        } 
	        return postFix;
	}
	
	public static double evaluate() {
		LinkedList<Double> stack = new LinkedList<Double>();
		for(int i = 0; i < postFix.size(); i++) {
			String s = postFix.get(i);

			if(isNumeric(s)) {
				stack.push(Double.parseDouble(s));
			}
			else {
				double right = stack.pop();
				double left = stack.pop();
				switch(s) {
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
		return stack.pop();
	}
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(Exception e){  
		    return false;  
		  }  
	}
	
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

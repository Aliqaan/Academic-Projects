import java.util.*;
public class Calculator {

	public static void main(String[] args) {
		
		Scanner console =new Scanner(System.in);
		String line1 = console.nextLine();
		String line2 = console.nextLine();										//Getting the inputs from the user
		String line3 = console.nextLine();
		String expression = console.nextLine();
		
		
		expression = variableReplace(line1, expression);						//Replace the given variables with its value.
		expression = variableReplace(line2, expression);
		expression = variableReplace(line3, expression);
		
		expression = expression.replace(";", "");									
		expression = expression.replace("\t", "");								//Modify the string to get rid of the ; \t character and spaces
		expression = " " +expression.replace(" ", "") + " ";	
		
		
		
		expression = paranthesis(expression);									//returns a String without parenthesises

		expression = multiplyAndDivide(expression);							//returns a String that only contains + and, or -
		
		expression = addAndSubtract(expression);								//returns the answer
		System.out.println(expression);
		
}	
	
	public static boolean isDouble (String str) {							//Returns if the number before or after is a double or not.
		return str.contains(".");
	}
	public static String findBefore(String expression, String numBefore,  int i) {			//Returns the number before the operator depending on the precedence.
		
		for(int j=i-1 ; ; j--){												//int i is the index of the operator. We go back until we find something non-number.
			
			if ( expression.charAt(j) == '+' || expression.charAt(j)== '*' || j==0 
					|| expression.charAt(j) == '-' || expression.charAt(j)== '/' || expression.charAt(j)=='(' || expression.charAt(j)==')' ) {
				break;
			}
			
				numBefore =  expression.charAt(j) + numBefore;				//We store the number as a string and return its value.
		}
		
				return numBefore;
		
	}
	public static String findAfter(String expression, String numAfter, int i) {				//Returns the number after the operator depending on the precedence.
		
		for(int j=i+1; ; j++) {												//int i is the index of the operator. We go back until we find something non-number.
			
			if ( expression.charAt(j) == '+' || expression.charAt(j)== '*' || j==expression.length()-1 
					|| expression.charAt(j) == '-' || expression.charAt(j)== '/' || expression.charAt(j)=='(' || expression.charAt(j)==')') {
				break;
			}
			numAfter = numAfter + expression.charAt(j);						//We store the number as a string and return its value.
		}
		return numAfter;
	}
	public static String multiplyAndDivide(String expression) {				// Finds and handles both the multiplication and division.

			while (expression.contains("*") || expression.contains("/")) {	//As long as there is this operators this method works.
	
			String str ,initial;										// initial stands for the string that contains expression.
			String numBefore = "";										//str holds the result of that expression in order to replace.
			String numAfter = "";										//numBefore and numAfter holds the numbers come before and after, respectively.
			
			int n1,n2;													//If the number is integer it is stored as int, if it is double, it is stored as double.
			double d1,d2;
			
				for(int i=0; i< expression.length(); i++) {				//Search the whole expression string.
				
					if(expression.charAt(i)=='*') {									//Need to make a multiplication.
						numBefore = findBefore(expression, numBefore,i);			//We find the numbers thanks to findBefore and findAfter methods.
						numAfter =  findAfter(expression,numAfter,i);
						if(  isDouble(numBefore) && !isDouble(numAfter)) {			//This if statements help us to store the number in the correct form.
						d1 = Double.parseDouble(numBefore);
						n2 = Integer.parseInt(numAfter);
						str = Double.toString(d1*n2);								//Stores the result of expression.
						}
						else if( !isDouble(numBefore) && isDouble(numAfter)) {
							d2 = Double.parseDouble(numAfter);
							n1 = Integer.parseInt(numBefore);
							str = Double.toString(n1*d2);
						}
						else if ( isDouble(numBefore) && isDouble(numAfter)){
							d1= Double.parseDouble(numBefore);
							d2 = Double.parseDouble(numAfter);
							str = Double.toString(d1*d2);
						}
						else {
							n1 = Integer.parseInt(numBefore);
							n2 = Integer.parseInt(numAfter);
							str = Integer.toString(n1*n2);
						}
					initial = numBefore + "*" + numAfter;						//String to hold expression, will be replaced soon.
					expression= expression.replace(initial, str);				//To replace the expression with the answer.
					
					
					break;														//Allows us continue searching for operators.
				}
				
				else if(expression.charAt(i)=='/') {								//Need to make a division.
					numBefore = findBefore(expression, numBefore,i);				//We find the numbers thanks to findBefore and findAfter methods.
					numAfter =  findAfter(expression,numAfter,i);
					if(  isDouble(numBefore) && !isDouble(numAfter)) {					//This if statements help us to store the number in the correct form.
						d1 = Double.parseDouble(numBefore);
						n2 = Integer.parseInt(numAfter);
						str = Double.toString(d1/n2);								//Stores the result of expression.
						}
						else if( !isDouble(numBefore) && isDouble(numAfter)) {
							d2 = Double.parseDouble(numAfter);
							n1 = Integer.parseInt(numBefore);
							str = Double.toString(n1/d2);
						}
						else if ( isDouble(numBefore) && isDouble(numAfter)){
							d1= Double.parseDouble(numBefore);
							d2 = Double.parseDouble(numAfter);
							str = Double.toString(d1/d2);
						}
						else {
							n1 = Integer.parseInt(numBefore);
							n2 = Integer.parseInt(numAfter);
							str = Integer.toString(n1/n2);
						}
					initial = numBefore + "/" + numAfter;							//String to hold expression, will be replaced soon.
					expression= expression.replace(initial, str);					//To replace the expression with the answer.
					
					break;															//Allows us continue searching for operators.
				}
			}
				str = "";															//In these lines we restore all the variables to their initial values.
				initial="";															//So that there will be no mistakes during replacing or finding numbers.
				numBefore = "";
				numAfter = "";
				n1=0; n2=0;d1=0; d2=0;
				
		}
		
		return expression;															//And finally when the loop is done, we can move on to the next operators.
		
	}
	public static String addAndSubtract(String expression) {				//Finds and handles the addition and subtraction.
		
		while ( expression.contains("+") || expression.contains("-"))  {			//As long as there is this operators this method works.
			
			String str ,initial;													// Same procedure in the multiplyAndDivide
			String numBefore = "";													
			String numAfter = "";
			
			int n1,n2;
			double d1,d2;
			
			for(int i=0; i< expression.length(); i++) {
				
				if(expression.charAt(i)=='-') {										//Need to make a subtraction.
					
					numBefore = findBefore(expression, numBefore,i);
					numAfter =  findAfter(expression,numAfter,i);					//Again the same steps, except this time we are subtracting numbers.
					if(  isDouble(numBefore) && !isDouble(numAfter)) {
						d1 = Double.parseDouble(numBefore);
						n2 = Integer.parseInt(numAfter);
						str = Double.toString(d1-n2);
						}
						else if( !isDouble(numBefore) && isDouble(numAfter)) {
							d2 = Double.parseDouble(numAfter);
							n1 = Integer.parseInt(numBefore);
							str = Double.toString(n1-d2);
						}
						else if ( isDouble(numBefore) && isDouble(numAfter)){
							d1= Double.parseDouble(numBefore);
							d2 = Double.parseDouble(numAfter);
							str = Double.toString(d1-d2);
						}
						else {
							n1 = Integer.parseInt(numBefore);
							n2 = Integer.parseInt(numAfter);
							str = Integer.toString(n1-n2);
						}
					initial = numBefore + "-" + numAfter;										//replacing the old expression with the result.
					expression = expression.replace(initial, str);	
					
					break;																		//Allows us continue searching for operators.
				}	
				
				else if(expression.charAt(i)=='+') {									//Need to make addition.
					numBefore = findBefore(expression, numBefore,i);
					numAfter =  findAfter(expression,numAfter,i);						//Doing the same steps with addition.
					if(  isDouble(numBefore) && !isDouble(numAfter)) {
						d1 = Double.parseDouble(numBefore);
						n2 = Integer.parseInt(numAfter);
						str = Double.toString(d1+n2);
						}
						else if( !isDouble(numBefore) && isDouble(numAfter)) {
							d2 = Double.parseDouble(numAfter);
							n1 = Integer.parseInt(numBefore);
							str = Double.toString(n1+d2);
						}
						else if ( isDouble(numBefore) && isDouble(numAfter)){
							d1= Double.parseDouble(numBefore);
							d2 = Double.parseDouble(numAfter);
							str = Double.toString(d1+d2);
						}
						else {
							n1 = Integer.parseInt(numBefore);
							n2 = Integer.parseInt(numAfter);
							str = Integer.toString(n1+n2);
						}
					initial = numBefore + "+" + numAfter;												//replacing the old expression with the result.
					expression = expression.replace(initial, str);		
					
					break;																			//Allows us continue searching for operators.
				}
				str = "";
				initial="";																	//Restoring our variables.
				numBefore = "";
				numAfter = "";
				n1=0; n2=0;d1=0; d2=0;
						
			}
			
		}
		return expression.replace(" ", "");														//Returns the final string in which there is the result.
	}
	public static String paranthesis(String expression) {					//Takes care of the paranthesis.					
		
		while ( expression.contains("(") ) {								//We need a while loop to handle all.
			String	inside = "";											//Stores the inside of the paranthesis.
			String	old = "";												//Stores the whole expression including paranthesis.
			
				for( int i=0; i<expression.length(); i++) {					
					
					if(expression.charAt(i)==')') {							//As soon as we find a right paranthesis
						
						for(int j= i-1; ; j--) {							//We go backwards to find a left paranthesis.
							
						if( expression.charAt(j)!='(' ) { 
							inside = expression.charAt(j) + inside;			//Storing the inside.
						}
						else {
						break;	
						}
						old = "(" + inside + ")";							//Making ready to part we replace.
					}	
						
				}
					if( !old.equals("")) {									//When we get to inside of it we escape the loop.
						break;
					}
					
					
				}
						//Below this line we send inside of the paranthesis to our methods to make the calculations and finding a result we can replace.
				expression = expression.replace(old, addAndSubtract(multiplyAndDivide( " " + inside + " ")) );
				expression =  " " + expression.replace(" ", "") + " ";								//We modify our string to get rid of any possible spaces.
		
		}
		return expression;									//Returning an expression without any paranthesis.
	}
	public static String variableReplace(String line,String expression) {	//Replacing the variables taken by the user if they are in the expression.
		
		String value = "";													//This string holds the value of variable.
		String variableName = "";											//Holds the name of the variable.
		
		line = line.replace(" ", "");										//Modifying the line so that we can work on it easily.
		line = line.replace("\t", "");
		if(line.contains("int")) {											//If the user input contains int, we store the value as an int.
			
			variableName = line.substring(line.indexOf('t') + 1,line.indexOf('='));			//Taking the variable's name.
			value = line.substring(line.indexOf('=') + 1, line.indexOf(';'));				//Taking its value.
		}
		else {																//If it is a double, we store it as a double.
			variableName = line.substring(line.indexOf('e') + 1,line.indexOf('='));
			value = line.substring(line.indexOf('=') + 1, line.indexOf(';'));
			if(!value.contains(".")) {										//If the input has no '.' after the double's value, we add one.
				value = value + ".";
			}
		}
		
		expression = expression.replace(variableName, value);				//We replace the variable with the value.
		return expression;													//Returns our expression string.
		
	}
			
	}

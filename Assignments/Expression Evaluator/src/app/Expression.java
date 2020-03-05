package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

    public static String delims = " \t*+-/()[]";

    public static void makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {

        expr = expr.trim();
        expr = expr.replaceAll("\\s+", "");

        int length = expr.length();
        int counter;

        String name = new String("");

        for (counter = 0; counter < length; counter++) {
            char ch = expr.charAt(counter);
            if (expr.charAt(counter) == '+' || expr.charAt(counter) == '-' || expr.charAt(counter) == '*' || expr.charAt(counter) == '/' || expr.charAt(counter) == '(' || expr.charAt(counter) == ')' || expr.charAt(counter) == ']' || expr.charAt(counter) == '[') {
                continue;
            } else if (Character.isDigit(ch)) {
                continue;
            } else if (Character.isLetter(ch)) {
                name = name + String.valueOf(ch);
                if ((counter + 1) < length) {
                    if (Character.isLetter(expr.charAt(counter + 1))) {
                        continue;
                    } else {
                        if (expr.charAt(counter + 1) == '[') {
                            Array newArray = new Array(name);
                            arrays.add(newArray);
                            name = "";
                            continue;
                        } else {
                            Variable newVariable = new Variable(name);
                            vars.add(newVariable);
                            name = "";
                            continue;
                        }
                    }
                } else {
                    Variable newVariable = new Variable(name);
                    vars.add(newVariable);
                    name = "";
                    continue;
                }
            }
        }
    }


    public static void
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
            throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
                continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
                arr = arrays.get(arri);
                arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok, " (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;
                }
            }
        }
    }

    private static Variable sequentialSearchVars(ArrayList<Variable> vars, String target) {
        for (int j = 0; j < vars.size(); j++) {
            if (vars.get(j).name.equals(target)) {
                return vars.get(j);
            }
        }
        return null;
    }

    private static Array sequentialSearchArrays(ArrayList<Array> arrays, String target) {
        for (int j = 0; j < arrays.size(); j++) {
            if (arrays.get(j).name.equals(target)) {
                return arrays.get(j);
            }
        }
        return null;
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }


    public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        expr = expr.trim();
        expr = expr.replaceAll("\\s+", "");

//        System.out.println("The first var is: " + vars.get(0));
//        System.out.println("The second var is: " + vars.get(1));
//        System.out.println("The third var is: " + vars.get(2));
//        System.out.println("The fourth var is: " + vars.get(3));
//        System.out.println("The first array is: " + arrays.get(0));
//        System.out.println("The second array is: " + arrays.get(1));
//        System.out.println("The expression is: " + expr);
//        System.out.println("The values of the B array are: " + arrays.get(1).values[0] + "," + arrays.get(1).values[1]);

        int length = expr.length();
        int counter;

        float total = 0;
        int currentTotal;

        int digitValue = 0;

        Stack<Float> operands = new Stack<Float>();
        Stack<Character> operator = new Stack<Character>();

        String temp = new String("");
        float tempfloat = 0;

        int precedence = 0;

        char operatorsign;
        float operand1;
        float operand2;
        float tempsum;


        if (length == 1) {
            char ch = expr.charAt(0);
            if (Character.isDigit(ch)) {
                int currentInt = Character.getNumericValue(ch);
                float currentFloat = currentInt;
                operands.push(currentFloat);
            }
            if (Character.isLetter(ch)) {
                temp = Character.toString(ch);
                Variable temporary = sequentialSearchVars(vars, temp);
                int currentInt = temporary.value;
                float currentFloat = currentInt;
                operands.push(currentFloat);
            }
//            System.out.println("The first digit is: " + operands.peek());
            return operands.peek();


        } else {

            for (counter = 0; counter < length; counter++) {
                char ch = expr.charAt(counter);

                if (Character.isDigit(ch)) {
                    digitValue = digitValue * 10 + Character.getNumericValue(ch);
                    if ((counter + 1) < length) {
                        if (!Character.isDigit(expr.charAt(counter + 1))) {
                            tempfloat = digitValue;
                            operands.push(tempfloat);
                            tempfloat = 0;
                            digitValue = 0;
                            continue;
                        }
                        continue;
                    } else {
                        tempfloat = digitValue;
                        operands.push(tempfloat);
                        tempfloat = 0;
                        digitValue = 0;
                        continue;
                    }


                } else if (Character.isLetter(ch)) {
                    temp = temp + Character.toString(ch);
                    if ((counter + 1) < length) {
                        if (expr.charAt(counter + 1) == '[') {
                            counter++;
                            Array tempo = sequentialSearchArrays(arrays, temp);
                            int startarr = counter + 1;
                            int leftarraycount = 1;
                            int rightarraycount = 0;
                            int strarr;
                            for (strarr = startarr; strarr < length; strarr++) {
                                char potentialarraybracket = expr.charAt(strarr);
                                if (potentialarraybracket == '[') {
                                    leftarraycount++;
                                }
                                if (potentialarraybracket == ']') {
                                    rightarraycount++;
                                }
                                if (leftarraycount == rightarraycount) {
                                    float dummy = (evaluate(expr.substring(startarr, strarr), vars, arrays));
                                    int dummy2 = (int) dummy;
                                    dummy = tempo.values[dummy2];
                                    operands.push(dummy);
                                    counter = strarr;
                                    temp = "";
                                    break;
                                }
                            }
                            continue;
                        }

                        if (Character.isLetter(expr.charAt(counter + 1))) {
                            continue;
                        } else {
                            Variable temporary = sequentialSearchVars(vars, temp);
                            int currentInt = temporary.value;
                            float currentFloat = currentInt;
                            operands.push(currentFloat);
                            temp = "";
                        }
                    } else {
                        Variable temporary = sequentialSearchVars(vars, temp);
                        int currentInt = temporary.value;
                        float currentFloat = currentInt;
                        operands.push(currentFloat);
                        temp = "";
                    }


                } else if (ch == '(') {
                    int start = counter + 1;
                    int leftbracketcount = 1;
                    int rightbracketcount = 0;
                    int stringtraversal;
                    for (stringtraversal = start; stringtraversal < length; stringtraversal++) {
                        char potentialbrakcet = expr.charAt(stringtraversal);
                        if (potentialbrakcet == '(') {
                            leftbracketcount++;
                        }
                        if (potentialbrakcet == ')') {
                            rightbracketcount++;
                        }
                        if (leftbracketcount == rightbracketcount) {
                            operands.push(evaluate(expr.substring(start, stringtraversal), vars, arrays));
                            counter = stringtraversal;
                            break;
                        }
                    }


                } else if (ch == '+' || ch == '-' || ch == '/' || ch == '*') {

                    if (operator.isEmpty()) {
                        operator.push(ch);
                        continue;
                    }

                    if (!operator.isEmpty()) {
                        if (precedence(ch) < precedence(operator.peek()) || precedence(ch) == precedence(operator.peek())) {
                            operand2 = operands.pop();
                            operand1 = operands.pop();
                            operatorsign = operator.pop();
                            if (operatorsign == '*') {
                                tempsum = operand1 * operand2;
                                operands.push(tempsum);
                            } else if (operatorsign == '/') {
                                tempsum = operand1 / operand2;
                                operands.push(tempsum);
                            } else if (operatorsign == '+') {
                                tempsum = operand1 + operand2;
                                operands.push(tempsum);
                            } else if (operatorsign == '-') {
                                tempsum = operand1 - operand2;
                                operands.push(tempsum);
                            }
                            operator.push(ch);
                            operand2 = 0;
                            operand1 = 0;
                            tempsum = 0;

                        } else if (precedence(ch) > precedence(operator.peek())) {
                            operator.push(ch);
                            continue;
                        }

                    }
                }
            }
        }

        Stack<Float> operands2 = new Stack<Float>();
        Stack<Character> operator2 = new Stack<Character>();

        while (counter == length && !operator.isEmpty()) {
            operand2 = operands.pop();
            operand1 = operands.pop();
            operatorsign = operator.pop();
            if (operatorsign == '*') {
                tempsum = operand1 * operand2;
                operands.push(tempsum);
            } else if (operatorsign == '/') {
                tempsum = operand1 / operand2;
                operands.push(tempsum);
            } else if (operatorsign == '+') {
                operands.push(operand1);
                operands2.push(operand2);
                operator2.push(operatorsign);
//                tempsum = operand1 + operand2;
//                operands.push(tempsum);
            } else if (operatorsign == '-') {
                tempsum = operand1 - operand2;
                operands.push(tempsum);
            }
        }
        operands2.push(operands.pop());

        float newtempsum;

        while (counter == length && !operator2.isEmpty()) {
            operand2 = operands2.pop();
            operand1 = operands2.pop();
            operatorsign = operator2.pop();
            if (operatorsign == '*') {
                newtempsum = operand1 * operand2;
                operands2.push(newtempsum);
            } else if (operatorsign == '/') {
                newtempsum = operand1 / operand2;
                operands2.push(newtempsum);
            } else if (operatorsign == '+') {
                newtempsum = operand1 + operand2;
                operands2.push(newtempsum);
            } else if (operatorsign == '-') {
                newtempsum = operand1 - operand2;
                operands2.push(newtempsum);
            }
        }

//        System.out.println("The last digit is: " + operands2.peek());

        return operands2.pop();
    }
}



package test;

import java.util.*;

public class Q3 {
    public static double calc(String s){
        try {
            Queue<String> queue =toQueue(s);
            Expression e = parse(queue);
            return restrictDecimals(e.calculate(),3);
        }catch(Exception e){return 0;}
    }
    public static double restrictDecimals(double value, int decimalPlaces) {
        return Math.round(value * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
    }
    public static Queue<String> toQueue(String s) {
            Stack<String> stack = new Stack<>();
            Queue<String> queue = new LinkedList<>();
            HashMap<String, Integer> precedence = new HashMap<>();
            precedence.put("(", 0);
            precedence.put("+", 1);
            precedence.put("-", 1);
            precedence.put("*", 2);
            precedence.put("/", 2);

            int i=0;
            char[]sc = s.toCharArray();

            while (i < sc.length) {
                if (Character.isDigit(sc[i])) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(sc[i]);
                    while (i < sc.length-1 && (Character.isDigit(sc[i+1]) || sc[i+1]=='.'))  {
                        builder.append(sc[i+1]);
                        i++;
                    }
                    queue.add(builder.toString());
                } else {
                    switch (sc[i]) {
                        case '+', '-', '*', '/' -> {
                            while (!stack.isEmpty() && precedence.get(Character.toString(sc[i])) < precedence.get(stack.peek())) {
                                queue.add(stack.pop());
                            }
                            stack.push(Character.toString(sc[i]));
                        }
                        case '(' -> stack.push(Character.toString(sc[i]));
                        case ')' -> {
                            String op = stack.pop();
                            while (!op.equals("(")) {
                                queue.add(op);
                                op = stack.pop();
                            }
                        }
                    }
                }
                i++;
            }
            while (!stack.isEmpty()) {
                queue.add(stack.pop());
            }
            return queue;
    }

    public static Expression parse(Queue<String> q){
        Stack<Expression> stack = new Stack<>();
        while(!q.isEmpty()){
            String p= q.poll();
            switch (p) {
                case "+" -> {
                    Expression r = stack.pop();
                    Expression l = stack.pop();
                    stack.push(new Plus(l, r));
                }
                case "-" -> {
                    Expression r = stack.pop();
                    Expression l = stack.pop();
                    stack.push(new Minus(l, r));
                }
                case "*" -> {
                    Expression r = stack.pop();
                    Expression l = stack.pop();
                    stack.push(new Mul(l, r));
                }
                case "/" -> {
                    Expression r = stack.pop();
                    Expression l = stack.pop();
                    stack.push(new Div(l, r));
                }
                default -> stack.push(new Number(Double.parseDouble(p)));
            }
        }
        return stack.pop();
    }
}
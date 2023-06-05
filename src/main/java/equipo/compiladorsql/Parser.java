
package equipo.compiladorsql;

import java.util.Stack;
    
public class Parser {
    private final Stack<String> stack;
    private final String[][] parsingTable;
    
    public Parser() {
        stack = new Stack<>();
        
        // Inicializar la tabla de análisis sintáctico ascendente
        parsingTable = new String[][] {
            /*   |   |   |   |   |   |   |   |   */
            /*----------------------------------*/
            {"r1", "", "", "", "", "", "", "", ""},  // Q
            {"", "r2", "", "", "", "", "", "", ""},  // D
            {"", "", "r3", "", "r3", "", "", "", ""},  // P
            {"", "", "", "", "", "r4", "r5", "", ""},  // A
            {"", "", "", "", "", "", "", "", ""},  // A1
            {"", "", "", "", "", "r7", "", "", ""},  // A2
            {"", "", "", "", "", "r8", "", "", ""},  // A3
            {"", "", "", "r9", "", "", "", "", ""},  // T
            {"", "", "", "", "", "", "r10", "", ""},  // T1
            {"", "", "", "", "", "r12", "", "", ""}  // T2
        };
    }
    
public boolean parse(String[] tokens) {
    stack.push("$");
    stack.push("Q");
    int index = 0;

    while (!stack.isEmpty()) {
        String currentToken = tokens[index];
        String top = stack.peek();
        
        System.out.println("Current Token: " + currentToken);
        System.out.println("Top of Stack: " + top);
        
        if (isTerminal(top)) {
            if (top.equals(currentToken)) {
                stack.pop();
                index++;
                System.out.println("Matched terminal: " + currentToken);
            } else {
                System.out.println("Unexpected token: " + currentToken);
                return false;
            }
        } else {
            int row = getRow(top);
            int col = getCol(currentToken);
            
            System.out.println("Row: " + row);
            System.out.println("Column: " + col);
            
            String action = parsingTable[row][col];
            
            System.out.println("Action: " + action);
            
            if (action.isEmpty()) {
                System.out.println("Undefined action for symbol: " + top + " and token: " + currentToken);
                return false;
            }
            
            if (action.startsWith("r")) {
                int rule = Integer.parseInt(action.substring(1));
                reduce(rule);
                System.out.println("Reduced using rule: " + rule);
            } else if (action.startsWith("s")) {
                int state = Integer.parseInt(action.substring(1));
                stack.push(currentToken);
                stack.push("s" + state);
                index++;
                System.out.println("Shifted token: " + currentToken);
            } else if (action.equals("accept")) {
                System.out.println("Accepted");
                return true;
            }
        }
    }
    
    return false;
}
    
    private boolean isTerminal(String symbol) {
        // Verificar si el símbolo es una terminal
        // Implementar lógica según las reglas de la gramática
        return symbol.equals("select") ||
               symbol.equals("distinct") ||
               symbol.equals("from") ||
               symbol.equals("*") ||
               symbol.equals("id") ||
               symbol.equals(",");
    }
    
    private int getRow(String symbol) {
        // Obtener el índice de la fila en la tabla de análisis
        // Implementar lógica según las reglas de la gramática
        if (symbol.equals("Q")) {
            return 0;
        } else if (symbol.equals("D")) {
            return 1;
        } else if (symbol.equals("P")) {
            return 2;
        } else if (symbol.equals("A")) {
            return 3;
        } else if (symbol.equals("A1")) {
            return 4;
        }
        return 0;
    }
    
    private int getCol(String token) {
    // Obtener el índice de la columna en la tabla de análisis
    // Implementar lógica según las reglas de la gramática
    switch (token) {
        case "select":
            return 0;
        case "distinct":
            return 1;
        case "from":
            return 2;
        case "*":
            return 3;
        case "id":
            return 4;
        case ",":
            return 5;
        default:
            // Columna no definida en la tabla
            return -1;
        }
    }
    
    private void reduce(int rule) {
    // Aplicar una reducción basada en la regla especificada
    // Implementar lógica según las reglas de la gramática
    
    switch (rule) {
        case 1:
            stack.pop(); // Q
            break;
        case 2:
            stack.pop(); // D
            break;
        case 3:
            stack.pop(); // P
            break;
        case 4:
            stack.pop(); // A
            stack.pop(); // A1
            stack.push("A"); // Push A back to the stack
            break;
        case 5:
            stack.pop(); // A1
            break;
        case 6:
            stack.pop(); // A2
            stack.push("A"); // Push A back to the stack
            break;
        case 7:
            stack.pop(); // A3
            stack.push("A"); // Push A back to the stack
            break;
        case 9:
            stack.pop(); // T
            break;
        case 11:
            stack.pop(); // T1
            break;
        case 12:
            stack.pop(); // T2
            stack.push("T"); // Push T back to the stack
            break;
        case 13:
            stack.pop(); // T3
            stack.push("T"); // Push T back to the stack
            break;
        default:
            // Regla no definida
            break;
    }
    
    // Realizar acciones adicionales si es necesario
}
}

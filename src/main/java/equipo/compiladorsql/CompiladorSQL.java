
package equipo.compiladorsql;

public class CompiladorSQL {

    public static void main(String[] args) {
        String[] tokens = {"select", "*", "from" , "id"};
        
        Parser parser = new Parser();
        boolean result = parser.parse(tokens);
        
        if (result) {
            System.out.println("La secuencia es válida según la gramática.");
        } else {
            System.out.println("La secuencia no es válida según la gramática.");
        }
    }
}

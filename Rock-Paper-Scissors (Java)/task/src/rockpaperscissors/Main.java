package rockpaperscissors;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
// rock 1
// paper 2
// scissors 3
public class Main {
    public static int score = 0;
    public static String[] vars;
    public static HashSet<String> variables;
    public static boolean[][] responseMatch = new boolean[][] {{true, false, true},
                                                        {true, true, false},
                                                        {false, true, true}};


    public static String createResponse(String input) {
        if (input.equals("!rating")) {
            return "Your rating: " + score;
        }
        Random random = new Random();
        int numR = random.nextInt(responseMatch.length) + 1;

        int numI = 0;
        for (int i = 0; i < vars.length; i++){
            if (vars[i].equals(input)){
                numI = i+1;
            }
        }

        if (numI == numR ) {
            score += 50;
            return ("There is a draw (" + input + ")");
        } else if ( responseMatch[numI - 1][numR - 1] ) {
            score += 100;
            return("Well done. The computer chose "+ vars[numR-1] +" and failed");
        } else {
            return("Sorry, but the computer chose " +vars[numR-1]);
        }
    }

    public static boolean isInputValid(String input) {
        if (variables.contains(input)||
                input.contains("!exit")
                || input.contains("!rating")) {
            return true;
        } else {
            System.out.println("Invalid input");
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        boolean stopGame = false;
        System.out.println("Enter your name:");
        String name = scan.nextLine();
        System.out.printf("Hello, %s", name);
        File file = new File("rating.txt");

        try (Scanner scanner_f = new Scanner(file)) {
            while (scanner_f.hasNext()) {
                String s = scanner_f.nextLine();
                String[] sc = s.split(" ");

                if (sc[0].equals(name)) {
                    score = Integer.parseInt(sc[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }

        //System.out.printf("Your rating: %d", score );
       // if (score == 0 ) score = 350;
        String options = scan.nextLine();
        if (options.equals("")) {
            options = "rock,paper,scissors";
        }
        vars = options.split(",");

        responseMatch = new boolean[vars.length][vars.length];
        variables = new HashSet<>();
        for (int i = 0; i < vars.length; i++){
            variables.add(vars[i]);
            for (int j = 0; j < vars.length; j++) {
                if (i == 0){
                    if (j == 0){
                        responseMatch[i][j] = true;
                    } else {
                        if (j > vars.length/2){
                            responseMatch[i][j] = true;
                        } else {
                            responseMatch[i][j] = false;
                        }

                    }
                } else {
                    if (j > 0)
                    responseMatch[i][j] = responseMatch[i-1][j-1];
                    else responseMatch[i][j] = responseMatch[i-1][vars.length-1];
                }

            }
        }
        /*for (int i = 0; i < vars.length; i++){
            for (int j = 0; j < vars.length; j++) {
                System.out.print( " " + responseMatch[i][j]);
            }
            System.out.println("");
        }*/

        System.out.println("Okay, let's start");
        while (!stopGame) {

            String input = scan.nextLine();
            if (isInputValid(input)) {
                if (input.contains("!exit")) {
                    System.out.println("Bye!");
                    stopGame = true;
                } else {
                    String response = createResponse(input);

                    System.out.println(response);
                }
            }
        }

    }
}

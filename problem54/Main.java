import java.io.*;

public class Main {
    public static void main(String [] args) throws IOException {
        if (args.length < 1) {
            System.out.println("No filename specified.");
        }
        String fileName = args[0];
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        int num_p1_wins = 0;

        String line = reader.readLine();
        while (line != null) {
            // Process single line and determine winner 
            Round curr_round = new Round(line);
            if (curr_round.getWinner() == Player.ONE) {
                num_p1_wins++;
            }
            
            line = reader.readLine();
        }

        System.out.println("Number of Player 1 Wins: " + num_p1_wins);
    }
}
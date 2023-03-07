import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class CompareDistinctCategoryHands {
   public static void main(String[] args) {
      int p1wins = 0;
      try {
         for (String line : Files.readAllLines(Paths.get("src/distinctHandPairs.txt"))) {
            Hand hand1 = new Hand(line.substring(0, 14));
            Hand hand2 = new Hand(line.substring(14, 29).trim());
            HashMap<Integer, Integer> m = new HashMap<>();

            if (hand1.getHandValue() > hand2.getHandValue()) {
               System.out.println("Player 1: " + hand1 + "  (" + Hand.nameMap.get(hand1.getHandValue()) +
                     ")\nPlayer 2: " + hand2 +  "  (" + Hand.nameMap.get(hand2.getHandValue()) +
                     ")\n\t\t\t    Player 1 wins.\n");
               p1wins++;
            } else if (hand1.getHandValue() < hand2.getHandValue())
               System.out.println("Player 1: " + hand1 + "  (" + Hand.nameMap.get(hand1.getHandValue()) +
                     ")\nPlayer 2: " + hand2 +  "  (" + Hand.nameMap.get(hand2.getHandValue()) +
                     ")\n\t\t\t    Player 2 wins.\n");
            else {
               if(Arrays.equals(hand1.getCardRanks(), hand2.getCardRanks())) {
                  System.out.println("Player 1: " + hand1 + "  (" + Hand.nameMap.get(hand1.getHandValue()) +
                          ")\nPlayer 2: " + hand2 +  "  (" + Hand.nameMap.get(hand2.getHandValue()) +
                          ")\n\t\t\t\t Tie.\n");
               } else {
                  String winner = "";
                  if(hand1.compareTo(hand2) == 1) winner = "Player 1";
                  else if(hand1.compareTo(hand2) == -1) winner = "Player 2";
                  else if(hand1.compareTo(hand2) == 0) winner = "Tie";

                  System.out.println("Player 1: " + hand1 + "  (" + Hand.nameMap.get(hand1.getHandValue()) +
                          ")\nPlayer 2: " + hand2 + "  (" + Hand.nameMap.get(hand2.getHandValue()) +
                          ")\n\t\t\t\t" + winner + " wins.\n");
               }
            }
         }
         System.out.println("Number of hands won by player 1: " + p1wins);
      } catch (Exception e) {
         System.out.println(e);
      }
   }

}

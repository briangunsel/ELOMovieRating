import java.util.*;

public class Hand {
   /*************************** Public static field ***************************/
   // the name of this hand
   public static Map<Integer, String> nameMap = Map.of(
         8, "straight flush",
         7, "four of a kind",
         6, "full house",
         5, "flush",
         4, "straight",
         3, "three of a kind",
         2, "two pair",
         1, "pair",
         0, "nothing");

   /****************************** Private fields ******************************/
   // A list of cards in this hand. E.g., [8C, TS, KC, 9H, 4S] is a list of cards.
   private ArrayList<Card> hand = new ArrayList<>();

   // A map of each card's rank to the number of times it occurs in the hand. E.g,
   // "7D 7S 5D 7C 5H" has two 5s and three 7s, so its frequency map is {5=2, 7=3}.
   private Map<Integer, Integer> rankFrequency = new HashMap<>();

   // Ranks of the cards in this hand in reverse-sorted order. E.g., the hand
   // "8H KC 2S 3S QD" has ranks [13, 12, 8, 3, 2].
   private Integer[] cardRanks = new Integer[5];

   // The integer value of this poker hand in the range from 0 to 8. 8 corresponds
   // to a straight flush and 0 to a nothing hand.
   // (See the instructions or the Wikipedia article for poker hand rankings.)
   private int handValue;

   /********************************  Constructor ********************************/
   // Creates a Hand object from an input string representing a hand.
   public Hand(String cards) {
      hand.removeAll(hand); // clear this hand to create a new one
      for (String token : cards.split("\\s+")) {
         Card card = new Card(token);
         hand.add(card);
      }
      check();                 // make sure this is a valid 5-card hand
      buildRankFrequencyMap(); // generate a map of card-rank frequencies
      buildRankArray();        // generate a card-rank array in reverse-sorted order
      determineHandRank();     // the rank of this hand on the scale 0 to 8
   }

   /*******************************  Private Methods *******************************/
   // How many times does each rank repeat in this hand? Maps each rank to the number
   // of times it occurs in this hand and returns the map. E.g., "KD KS 9H JC 9S" has
   // two Ks, two 9s, and one J, so its map is {13=2, 11=1, 9=2}.
   private void buildRankFrequencyMap() {
      for(int i = 0; i < hand.size(); i++) {
         if(!rankFrequency.containsKey(hand.get(i).getRank())) {
            rankFrequency.put(hand.get(i).getRank(), 1);
         } else {
            rankFrequency.put(hand.get(i).getRank(), rankFrequency.get(hand.get(i).getRank()) + 1);
         }
      }
   }

   // generate a list of ranks in decreasing order (the highest rank first, the lowest last).
   private void buildRankArray() {
      int i = 0;
      for (Card c : hand){
         cardRanks[i] = c.getRank();
         i++;
      }
      Arrays.sort(cardRanks, Collections.reverseOrder());

      // the only exception is a low straight (called a wheel) where Ace
      // counts as 1, not as 14. So if the hand is "Ace, 5, 4, 3, 2"
      // we want to return "5, 4, 3, 2, 1" instead of "14, 5, 4, 3, 2"
      if (Arrays.equals(cardRanks, new Integer[]{14, 5, 4, 3, 2}))
         cardRanks = new Integer[]{5, 4, 3, 2, 1};
   }

   // determine the ranking of this hand
   private void determineHandRank() {
      if      (this.isStraightFlush()) handValue = 8;
      else if (this.isFourOfaKind())   handValue = 7;
      else if (this.isFullHouse())     handValue = 6;
      else if (this.isFlush())         handValue = 5;
      else if (this.isStraight())      handValue = 4;
      else if (this.isThreeOfaKind())  handValue = 3;
      else if (this.isTwoPair())       handValue = 2;
      else if (this.isPair())          handValue = 1;
      else                             handValue = 0;
   }

   // Check if the hand has 5 cards and if each card is in the correct format.
   private void check() {
      if (hand.size() != 5)
         throw new RuntimeException("Not a 5-card hand. Try again.");
      Set<String> cardSet = new HashSet<>();
      for (Card c : hand) cardSet.add(c.toString());  // (card validity itself is checked in Card.java)
      if (cardSet.size() != 5)
         throw new RuntimeException("Duplicate card! Try again.");
   }

   /***********************************  Getters ***********************************/
   // returns and Integer array of the card ranks in this hand in descending order
   public Integer[] getCardRanks() { return cardRanks; }

   // returns the frequency map for this hand
   public Map<Integer, Integer> getRankFrequency() { return rankFrequency; }

   // returns the list of Cards in this hand
   public ArrayList<Card> getHand() { return hand; }

   // return the rank of this hand
   public int getHandValue() { return handValue; }

   /*********************************************************************************
                        Methods that check for the hand categories
    ********************************************************************************/
   public boolean isStraightFlush() {
      return isFlush() && isStraight();
   }

   public boolean isFourOfaKind() {
      return getRankFrequency().containsValue(4);
   }

   public boolean isFullHouse() {
      return getRankFrequency().containsValue(3) && getRankFrequency().containsValue(2);
   }

   public boolean isFlush() {
      for(int i = 0; i < hand.size(); i++) {
         for(int j = 0; j < hand.size(); j++) {
            if(hand.get(i).getSuit() != hand.get(j).getSuit()) return false;
         }
      }
      return true;
   }

   public int max() {
      int max = 0;
      for (Card card : hand) {
         if (card.getRank() > max) {
            max = card.getRank();
         }
      }
      return max;
   }

   public int min() {
      int min = 15;
      for (Card card : hand) {
         if (card.getRank() < min) {
            min = card.getRank();
         }
      }
      return min;
   }

   public boolean isStraight() {
      return max() - min() == 4 && !isPair();
   }

   public boolean isThreeOfaKind() {
      return getRankFrequency().containsValue(3);
   }

   public boolean isTwoPair() {
      return getRankFrequency().containsValue(2) && getRankFrequency().containsValue(1) && getRankFrequency().size() == 3;
   }

   public boolean isPair() {
      return getRankFrequency().containsValue(2) && getRankFrequency().size() == 4;
   }

   public boolean isNothing() {
      return !(isFlush() || isStraight() || isFullHouse()
              || isFourOfaKind() || isThreeOfaKind()
              || isPair() || isTwoPair());
   }

   public int compareTo(Hand hand2) {
      int total1 = 0;
      int total2 = 0;

      for (Card card : this.getHand()) {
         total1 += card.getRank();
      }

      for (Card card : hand2.getHand()) {
         total2 += card.getRank();
      }

      if (total1 > total2) { return 1; }
      else if (total2 > total1) return -1;
      else return 0;
   }

   @Override
   public String toString() { return hand.toString(); }
}

package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	private ArrayList<Card> CardsInHand=new ArrayList<Card>();

	private int HandStrength;
	private int HiHand;
	private int LoHand;
	private int Kicker;
	private boolean bScored = false;

	private boolean Flush;
	private boolean Straight;
	private boolean Ace;
	private boolean Joker;

	public Hand(Deck d) {
		ArrayList<Card> Import = new ArrayList<Card>();
		for (int x = 0; x < 5; x++) {
			Import.add(d.drawFromDeck());
		}
		CardsInHand = Import;
	}

	public Hand(Hand handA){
		for (int i=0;i<handA.CardsInHand.size();i++){
		this.CardsInHand.add(handA.CardsInHand.get(i));
		}
		this.HandStrength=handA.HandStrength;
		this.HiHand=handA.HiHand;
		this.LoHand=handA.LoHand;
		this.Kicker=handA.Kicker;
		
	}
	
	public Hand(ArrayList<Card> Cards){
		this.CardsInHand=Cards;
	}
	
	public static Hand NewInstance(Hand handA){
		return new Hand(handA.getCards());
	}
	
	public ArrayList<Card> getCards() {
		return CardsInHand;
	}

	public int getHandStrength() {
		return HandStrength;
	}

	public int getKicker() {
		return Kicker;
	}

	public int getHighPairStrength() {
		return HiHand;
	}

	public int getLowPairStrength() {
		return LoHand;
	}

	public boolean getAce() {
		return Ace;
	}

	public boolean getJoker(){
		for (int i=0;i<this.CardsInHand.size();i++){
			if(this.CardsInHand.get(i).getRank()==eRank.JOKER
					&& this.CardsInHand.get(i).getSuit()==eSuit.WILD)
				return true;
		}
		return false;
	}

	public static Hand EvalHand(ArrayList<Card> SeededHand) {		
		Deck d = new Deck();
		Hand h = new Hand(d);
		h.CardsInHand = SeededHand;
		h.EvalHand();
		return h;
	}	

	public void EvalHand() {
		// Checks for Jokers, if none evaluates as normal hand, built recursively to handle all possible joker combinations

		// Sort the cards!
		Collections.sort(CardsInHand, Card.CardRank);
		if(this.getJoker()){
			Hand Tempnew= new Hand(this);
			Deck d = new Deck();
			Hand TempBest = new Hand(d);
			TempBest.ScoreHand(eHandStrength.HighCard, 0, 0, 0);
			Tempnew.CardsInHand.remove(eCardNo.FirstCard.getCardNo());
			Hand baseCase = new Hand(Tempnew);
			for (short i = 0; i <= 3; i++) {
				eSuit SuitValue = eSuit.values()[i];			
				for (short j = 0; j <= 12; j++) {
					eRank RankValue = eRank.values()[j];				
					Card NewCard = new Card(SuitValue,RankValue);
					Tempnew.CardsInHand.add(NewCard);
					Tempnew.EvalHand();
					if (HandRank.compare(TempBest, Tempnew)>0){
						TempBest= new Hand(Tempnew);
					}
					Tempnew= new Hand(baseCase);
				}
			}
			this.HandStrength = TempBest.HandStrength;
			this.HiHand = TempBest.HiHand;
			this.LoHand = TempBest.LoHand;
			this.Kicker = TempBest.Kicker;
			this.bScored = true;


		}else{
			// Evaluates if the hand is a flush and/or straight then figures out
			// the hand's strength attributes

			// Ace Evaluation
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
				Ace = true;
			}

			// Flush Evaluation
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getSuit()) {
				Flush = true;
			} else {
				Flush = false;
			}

			// Straight Evaluation
			if (Ace) {
				// Looks for Ace, King, Queen, Jack, 10
				if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
						&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == eRank.JACK
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
					Straight = true;
					// Looks for Ace, 2, 3, 4, 5
				} else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
						&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == eRank.THREE
						&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
						&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.FIVE) {
					Straight = true;
				} else {
					Straight = false;
				}
				// Looks for straight without Ace
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					.getRank() + 1
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank() + 2
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
					.getRank() + 3
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank() + 4) {
				Straight = true;
			} else {
				Straight = false;
			}

			// Evaluates the hand type
			if (Straight == true && Flush == true
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN && Ace) {
				ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
			}

			// Straight Flush
			else if (Straight == true && Flush == true) {
				ScoreHand(eHandStrength.StraightFlush, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, 0);
			}

			// Five of a Kind

			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.FiveOfAKind, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0,0);
			}

			// Four of a Kind

			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.FourOfAKind, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FifthCard.getCardNo())
						.getRank().getRank());
			}

			else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.FourOfAKind, CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FirstCard.getCardNo())
						.getRank().getRank());
			}

			// Full House
			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.FullHouse, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank().getRank(), 0);
			}

			else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.FullHouse, CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank(), CardsInHand.get(eCardNo.FirstCard.getCardNo())
						.getRank().getRank(), 0);
			}

			// Flush
			else if (Flush) {
				ScoreHand(eHandStrength.Flush, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, 0);
			}

			// Straight
			else if (Straight) {
				ScoreHand(eHandStrength.Straight, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, 0);
			}

			// Three of a Kind
			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.ThreeOfAKind, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank().getRank());
			}

			else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.ThreeOfAKind, CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FifthCard.getCardNo())
						.getRank().getRank());
			} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.ThreeOfAKind, CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FirstCard.getCardNo())
						.getRank().getRank());
			}

			// Two Pair
			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					&& (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank())) {
				ScoreHand(eHandStrength.TwoPair, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), CardsInHand.get(eCardNo.ThirdCard.getCardNo())
						.getRank().getRank(), CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank());
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					&& (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank())) {
				ScoreHand(eHandStrength.TwoPair, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank().getRank(), CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank());
			} else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					&& (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank())) {
				ScoreHand(eHandStrength.TwoPair, CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank(), CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank().getRank(), CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank());
			}

			// Pair
			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.Pair, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.ThirdCard.getCardNo())
						.getRank().getRank());
			} else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.Pair, CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FirstCard.getCardNo())
						.getRank().getRank());
			} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.Pair, CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FirstCard.getCardNo())
						.getRank().getRank());
			} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.Pair, CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.FirstCard.getCardNo())
						.getRank().getRank());
			}

			else {
				ScoreHand(eHandStrength.HighCard, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, CardsInHand.get(eCardNo.SecondCard.getCardNo())
						.getRank().getRank());
			}
		}
	}

	public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand{
		Collections.sort(Hands,HandRank);
		try{
			if(HandRank.compare(Hands.get(Hands.size()), Hands.get(Hands.size()-1))==0){
				int i=1;
				while(HandRank.compare(Hands.get(Hands.size()), Hands.get(Hands.size()-i-1))==0){
					i++;
				}
				throw new exHand(i);
			}
			return Hands.get(Hands.size());
		}catch(exHand e){
			return Hands.get(Hands.size());
		}
	}

	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, int Kicker) {
		this.HandStrength = hST.getHandStrength();
		this.HiHand = HiHand;
		this.LoHand = LoHand;
		this.Kicker = Kicker;
		this.bScored = true;

	}

	/**
	 * Custom sort to figure the best hand in an array of hands
	 */
	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.HandStrength - h1.HandStrength;

			if (result != 0) {
				return result;
			}

			result = h2.HiHand - h1.HiHand;
			if (result != 0) {
				return result;
			}

			result = h2.LoHand = h1.LoHand;
			if (result != 0) {
				return result;
			}

			result = h2.Kicker = h1.Kicker;
			if (result != 0) {
				return result;
			}

			return 0;
		}
	};
}

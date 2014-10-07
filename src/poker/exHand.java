package poker;
@SuppressWarnings("serial")

public class exHand extends Exception {
	
	public exHand(int numberOfHands){
		System.out.println(numberOfHands + " hands have tied");
	}

}

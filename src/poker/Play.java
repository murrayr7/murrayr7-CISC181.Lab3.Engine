/*
package poker;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class Play extends JApplet {

	private JLabel jlblCard1 = new JLabel();
	private JLabel jlblCard2 = new JLabel();
	private JLabel jlblCard3 = new JLabel();
	private JLabel jlblCard4 = new JLabel();
	private JLabel jlblCard5 = new JLabel();
	
	private JLabel jlblCard6 = new JLabel();
	private JLabel jlblCard7 = new JLabel();
	private JLabel jlblCard8 = new JLabel();
	private JLabel jlblCard9 = new JLabel();
	private JLabel jlblCard10 = new JLabel();	
	
	private JLabel jlblWinner = new JLabel();

	private JButton jbtRefresh = new JButton("Refresh");
	private ImageIcon[] cardIcons = new ImageIcon[52];
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panelwinner = new JPanel();
	
	public Play() {

		// Load the image icons
		for (int i = 0; i < 52; i++)
			cardIcons[i] = new ImageIcon(
					"C:/Users/Bert.Gibbons/workspace/Poker/img/"
							+ (i + 1) + ".png");

		panel1.add(jlblCard1);
		panel1.add(jlblCard2);
		panel1.add(jlblCard3);
		panel1.add(jlblCard4);
		panel1.add(jlblCard5);
		
		panel2.add(jlblCard6);
		panel2.add(jlblCard7);
		panel2.add(jlblCard8);
		panel2.add(jlblCard9);
		panel2.add(jlblCard10);
		
		panelwinner.add(jlblWinner);
		
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		panel3.add(jbtRefresh);

		add(panel1, BorderLayout.WEST);
		add(panel2, BorderLayout.EAST);
		add(panelwinner,BorderLayout.CENTER);
		add(panel3, BorderLayout.SOUTH);

		refresh();

		jbtRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				refresh();
			
				int x = 0;
				while (x < 310000)
				{
					refresh();
				x++;
				}
			
				
			}
		});

	}

	private void refresh() {
		Deck deck = new Deck();
		Hand hand = new Hand(deck);
		hand.EvalHand();
		int handWinner;
		
		
		Hand hand2 = new Hand(deck);
		hand2.EvalHand();
		
		// You can set the Hand Strength here. Use this to check if its working
		
		//while (hand.getHandStrength() != 10) 
		{
			deck = new Deck();
			hand = new Hand(deck);
			hand.EvalHand();
		}
		System.out.println("Hand Strength " + hand.getHandStrength());
		System.out.println("Kicker " + hand.getKicker());
		System.out.println("High Pair Strength " + hand.getHighPairStrength());
		System.out.println("Low Pair Strength " + hand.getLowPairStrength());
		System.out.println("Ace " + hand.getAce());

		
		
		
		jlblWinner.setText(hand.getHandStrength() + " << Winner " + hand2.getHandStrength());
		
		if (hand.getHandStrength() > hand2.getHandStrength())
		{
			handWinner = 1;
		}
		else if (hand.getHandStrength() == hand2.getHandStrength())
		{
			if (hand.getHighPairStrength() > hand2.getHighPairStrength())
			{
				handWinner = 1;
			}
			else if (hand.getHighPairStrength() == hand2.getHighPairStrength())
			{
				if (hand.getLowPairStrength() > hand2.getLowPairStrength())
				{
					handWinner = 1;
				}
				else if (hand.getLowPairStrength() == hand2.getLowPairStrength())
				{
					if (hand.getKicker() > hand2.getKicker())
					{
						handWinner = 1;
					}
					else if (hand.getKicker() == hand2.getKicker())
					{
						handWinner = 3;
					}
					else
						handWinner = 2;
				}
				else
					handWinner = 2;
			}
			else
				handWinner = 2;
		
		}
		else
		{
			handWinner = 2;
			//jlblWinner.setText(hand.getHandStrength() + " Winner>> " + hand2.getHandStrength());
		}
		
		switch  (handWinner){
		case 1: jlblWinner.setText(hand.getHandStrength() + " <<Winner " + hand2.getHandStrength());
				break;
		case 2: jlblWinner.setText(hand.getHandStrength() + " Winner>> " + hand2.getHandStrength());
				break;
		case 3: jlblWinner.setText(hand.getHandStrength() + " << Tie >> " + hand2.getHandStrength());
				break;
		}
		
		
		
		
		
		int index1 = hand.getCards().get(0).getNumber();
		int index2 = hand.getCards().get(1).getNumber();
		int index3 = hand.getCards().get(2).getNumber();
		int index4 = hand.getCards().get(3).getNumber();
		int index5 = hand.getCards().get(4).getNumber();
		int index6 = hand2.getCards().get(0).getNumber();
		int index7 = hand2.getCards().get(1).getNumber();
		int index8 = hand2.getCards().get(2).getNumber();
		int index9 = hand2.getCards().get(3).getNumber();
		int index10 = hand2.getCards().get(4).getNumber();		

		jlblCard1.setIcon(cardIcons[index1]);
		jlblCard2.setIcon(cardIcons[index2]);
		jlblCard3.setIcon(cardIcons[index3]);
		jlblCard4.setIcon(cardIcons[index4]);
		jlblCard5.setIcon(cardIcons[index5]);
		
		jlblCard6.setIcon(cardIcons[index6]);
		jlblCard7.setIcon(cardIcons[index7]);
		jlblCard8.setIcon(cardIcons[index8]);
		jlblCard9.setIcon(cardIcons[index9]);
		jlblCard10.setIcon(cardIcons[index10]);
		
		//WriteDB DB = new WriteDB();
		//DB.WriteHand(hand);
		
	}

	public static void main(String[] args) {
		Play applet = new Play();
		JFrame frame = new JFrame();
		// EXIT_ON_CLOSE == 3
		frame.setDefaultCloseOperation(3);
		frame.setTitle("GUIDemoPickFourCards");
		frame.add(applet, BorderLayout.CENTER);
		applet.init();
		applet.start();
		frame.pack();
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setVisible(true);
	}
}

*/
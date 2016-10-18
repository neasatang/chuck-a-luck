import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

	Wallet playerWallet = new Wallet();
	Money bettingMoney = new Money();
	Money winningMoney = new Money();
	Money playerMoney = new Money();
	DiceI dice1 = new Dice();
	DiceI dice2 = new Dice();
	DiceI dice3 = new Dice();

	/*********** declaring labels for frame 1 ***********/
	private JFrame f;
	private JPanel p, p2;
	private JButton b1;
	private JLabel welcome, eurosign, intro, confirm, amount;
	private JTextField wallet;
	private double limit;

	/************ declaring labels for frame 2 **********/
	private JFrame f2;
	private JPanel p3, p4, p5, labelPanel;
	private JButton b2, b3;
	private JLabel bet, result;

	/****** declare constructor of the class *******/
	public Main() {

		/** create method **/
		createGUI();
	}

	/*** MAIN CLASS FUNCTION ***/
	public static void main(String[] args) {

		/**
		 * calls the main constructor that then calls the createGUI function
		 **/
		new Main();

	}

	/****************** CREATEGUI FUNCTION ****************/
	public void createGUI() {

		/****************** FRAME ONE - START OF GAME ****************/
		f = new JFrame("Chuck-a-Luck Game"); // create the JFrame object
		f.setVisible(true); // want the frame to be visible
		f.setSize(500, 200); // set the size
		f.setLocationRelativeTo(null); // center frame on screen
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close on demand

		/**** create panels ****/
		p = new JPanel();
		p2 = new JPanel();
		p.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);

		/**** create button ****/
		b1 = new JButton("Play now!");

		/**** add action listener for button ****/
		b1.addActionListener(new ActionListener() {

			/** create a method for when the button is pressed **/
			public void actionPerformed(ActionEvent e) {

				/****************** FRAME TWO -THE GAME ****************/
				f2 = new JFrame("Chuck-a-Luck Game");
				f2.setVisible(true);
				f2.setSize(500, 450);
				f2.setLocationRelativeTo(null);
				f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				/**** create panels for frame 2 ****/
				p3 = new JPanel();
				p3.setBackground(Color.WHITE);
				p4 = new JPanel();
				p4.setBackground(Color.WHITE);
				p5 = new JPanel();
				p5.setBackground(Color.WHITE);

				labelPanel = new JPanel();
				labelPanel.setBackground(Color.WHITE);

				/** create 2D array of bets **/

				final String[][] typeofbets = new String[10][2];
				typeofbets[0][0] = "Number[1]";
				typeofbets[1][0] = "Number[2]";
				typeofbets[2][0] = "Number[3]";
				typeofbets[3][0] = "Number[4]";
				typeofbets[4][0] = "Number[5]";
				typeofbets[5][0] = "Number[6]";
				typeofbets[6][0] = "Triple";
				typeofbets[7][0] = "Field";
				typeofbets[8][0] = "High";
				typeofbets[9][0] = "Low";

				typeofbets[0][1] = "0";
				typeofbets[1][1] = "0";
				typeofbets[2][1] = "0";
				typeofbets[3][1] = "0";
				typeofbets[4][1] = "0";
				typeofbets[5][1] = "0";
				typeofbets[6][1] = "0";
				typeofbets[7][1] = "0";
				typeofbets[8][1] = "0";
				typeofbets[9][1] = "0";

				/** create the JTable for the bets **/
				Object rowData[][] = typeofbets;
				Object columnNames[] = { "Type of bets", "Betting amount in €" };
				JTable table = new JTable(typeofbets, columnNames);
				table.setRowHeight(30);
				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setPreferredSize(new Dimension(400, 330));

				/** adding JTable to labelPanel **/
				labelPanel.add(scrollPane, BorderLayout.CENTER);
				f2.getContentPane().add(labelPanel);
				f2.setVisible(true);

				/**** create labels ****/
				bet = new JLabel("Place your bets! Press enter and then click 'Bet!'");
				result = new JLabel("Your starting amount was: " + (playerWallet.check()).toString());

				/**** create button ****/
				b2 = new JButton("Bet!");
				b2.addActionListener(new ActionListener() {

					/****************** BETTING TYPES ****************/

					public void actionPerformed(ActionEvent e) {

						/** goes through array **/
						for (int i = 0; i < typeofbets.length; i++) {
							try {
								double betAmount = Double.parseDouble(typeofbets[i][1]);
								int euro = (int) betAmount;
								int cents = (int) ((betAmount - euro) * 100);
								MoneyI bettingMoney = new Money(euro, cents);

								try {
									playerWallet.take(bettingMoney);
									// System.out.println(bettingMoney);//check
									// betting money test

									/** roll dices **/
									dice1.roll();
									dice2.roll();
									dice3.roll();

									double total = dice1.value() + dice2.value() + dice3.value();
									double winnings = 0;

									/** Number[n] Betting Type **/
									for (int j = 0; j < 6; j++) {
										if (i == j) {
											int count = 0;
											if (dice1.value() == (i + 1)) {
												count++;
											}
											if (dice2.value() == (i + 1)) {
												count++;
											}
											if (dice3.value() == (i + 1)) {
												count++;
											}
											if (count == 3) {
												winnings = 11 * betAmount;
											} else if (count == 2) {
												winnings = 3 * betAmount;
											} else if (count == 1) {
												winnings = 2 * betAmount;
											}
											MoneyI winningMoney = new Money((int) winnings,
													(int) ((winnings - (int) winnings) * 100));
											playerMoney = (Money) playerMoney.plus(winningMoney);
											playerWallet.put(playerMoney);
											playerMoney = new Money(0, 0);
											// System.out.println((playerWallet.check()).toString());//check
											// balance test

										}
									}

									/** Triple Betting Type **/
									if (i == 6) {
										if ((dice1.value() == dice2.value()) && (dice2.value() == dice3.value())) {
											winnings = (31) * betAmount;
											MoneyI winningMoney = new Money((int) winnings,
													(int) ((winnings - (int) winnings) * 100));
											playerMoney = (Money) playerMoney.plus(winningMoney);
											playerWallet.put(playerMoney);
											playerMoney = new Money(0, 0);

										}
									}

									/** Field Betting Type **/
									else if (i == 7) {
										if ((total < 8) || (total > 12)) {
											winnings = (2) * betAmount;
											MoneyI winningMoney = new Money((int) winnings,
													(int) ((winnings - (int) winnings) * 100));
											playerMoney = (Money) playerMoney.plus(winningMoney);
											playerWallet.put(playerMoney);
											playerMoney = new Money(0, 0);
										}
									}

									/** High Betting Type **/
									else if (i == 8) {
										if ((total > 10) && !(total / 3 == dice1.value())) {
											winnings = (2) * betAmount;
											MoneyI winningMoney = new Money((int) winnings,
													(int) ((winnings - (int) winnings) * 100));
											playerMoney = (Money) playerMoney.plus(winningMoney);
											playerWallet.put(playerMoney);
											playerMoney = new Money(0, 0);
										}

									}

									/** Low Betting Type **/
									else if (i == 9) {
										if ((total < 11) && !(total / 3 == dice1.value())) {
											winnings = (2) * betAmount;
											MoneyI winningMoney = new Money((int) winnings,
													(int) ((winnings - (int) winnings) * 100));
											playerMoney = (Money) playerMoney.plus(winningMoney);
											playerWallet.put(playerMoney);
											playerMoney = new Money(0, 0);
										}
									}
									// System.out.println("result" +
									// (playerWallet.check()).toString());
									// check result after each bet test

								} catch (Exception InsufficientResourcesException) {
									JOptionPane.showMessageDialog(null,
											"You have insufficient money! Please place a lower bet or exit the game.");
								}
							}

							/** error - if incorrect input **/
							catch (NumberFormatException a) {
								JOptionPane.showMessageDialog(null, "You must enter numbers.");
							}

						}
						/** updated bet wallet **/
						JOptionPane.showMessageDialog(null,
								"Your betting wallet is now: " + (playerWallet.check()).toString());

						/** error - if the user has no money left **/
						if ((playerWallet.check()).remainingCent() == 0) {
							if ((playerWallet.check()).totalEuro() == 0) {
								JOptionPane.showMessageDialog(null, "You have no money. Game over! Game will now quit");
								System.exit(0);
							}
						}

						/** set bet amounts back to 0 **/
						typeofbets[0][1] = "0";
						typeofbets[1][1] = "0";
						typeofbets[2][1] = "0";
						typeofbets[3][1] = "0";
						typeofbets[4][1] = "0";
						typeofbets[5][1] = "0";
						typeofbets[6][1] = "0";
						typeofbets[7][1] = "0";
						typeofbets[8][1] = "0";
						typeofbets[9][1] = "0";

					}

					/****************** END OF BETTING TYPES ****************/

				});

				/** create exit game button **/
				b3 = new JButton("Exit game");
				b3.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == b3) {
							System.exit(0); // ends the game
						}
					}

				});

				/**** add to panel 3 (place bet label and button) ****/
				p3.add(bet);
				p3.add(b2);
				f2.add(p3, BorderLayout.NORTH);
				p4.add(result);
				p4.add(b3);
				f2.add(p4, BorderLayout.SOUTH);

				/**** add to labelPanel (place JTable) ****/
				labelPanel.setLocation(150, 500);
				f2.add(labelPanel);

			}

			/*************** END OF FRAME TWO - THE GAME ****************/

		});

		/*************** BACK TO FRAME ONE - START OF GAME ****************/

		/**** create labels ****/
		welcome = new JLabel("Welcome to the game, Chuck-a-Luck!");
		intro = new JLabel("Enter the amount of money in your purse/wallet: €");
		confirm = new JLabel("Then press enter to confirm and click 'Play Now!' to start.");
		eurosign = new JLabel("€");
		amount = new JLabel();

		/** create TextField **/
		wallet = new JTextField(10);

		/** add action listener **/
		wallet.addActionListener(new ActionListener() {

			/** create a method for when enter button is pressed **/
			public void actionPerformed(ActionEvent e) {
				String input = wallet.getText();
				amount.setText(input);
				try {
					limit = Double.parseDouble(input);
				} catch (NumberFormatException a) {
					JOptionPane.showMessageDialog(null, "You must enter numbers.");
				}
				int euro = (int) limit;
				int cents = (int) ((limit - euro) * 100);
				MoneyI playerMoney = new Money(euro, cents);
				playerWallet.put(playerMoney);
				// System.out.println(playerMoney.toString());
			}
		});

		/** adding labels and buttons to panel **/
		p.add(welcome);
		p.add(Box.createVerticalStrut(30)); // set 50 pixels apart
		p.add(intro);
		p.add(amount);
		p.add(Box.createVerticalStrut(30)); // set 50 pixels apart
		p.add(confirm);
		p.add(Box.createVerticalStrut(30)); // set 50 pixels apart
		p2.add(eurosign);
		p2.add(wallet);
		p2.add(b1);

		/** adding panels to frame **/
		f.add(p);
		f.add(p2, BorderLayout.SOUTH); // set p2 to bottom of frame

		/****************** END OF FRAME ONE - START OF GAME ****************/

	}
	/************** END OF CREATEGUI FUNCTION ***************/

}
/********************** END OF PROGRAM *********************/
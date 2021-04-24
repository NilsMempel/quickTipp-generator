package generators;

import java.util.Collection;
import java.util.Random;

/**
 * {@code EurojackpotGenerator} generates a quick tipp for Eurojackpot. It is
 * possible to choose numbers, which the quick tipp cannot contain.
 * 
 * @author Nils Mempel
 *
 */
public class EurojackpotGenerator implements BetGenerator {

	/* length of the generated bet */
	private static final int BET_LENGTH = 7;

	/* length, minimum and maximum number of the first part of the bet */
	private static final int BET_LENGTH_FIRSTPART = 5;
	private static final int MINIMUM_BETNUMBER_FIRSTPART = 1;
	private static final int MAXIMUM_BETNUMBER_FIRSTPART = 50;

	/* length, minimum and maximum number of the second part of the bet */
	private static final int BET_LENGTH_SECONDPART = 2;
	private static final int MINIMUM_BETNUMBER_SECONDPART = 1;
	private static final int MAXIMUM_BETNUMBER_SECONDPART = 10;

	/**
	 * Calls constructor of super class.
	 */
	public EurojackpotGenerator() {
		super();
	}

	@Override
	public int[] generateBet(Collection<Integer> unluckyNumbers) {
		/* will be filled and returned */
		int[] bet = new int[BET_LENGTH];

		Random random = new Random();

		int currentMinimumBetnumber = MINIMUM_BETNUMBER_FIRSTPART;
		int currentMaximumBetnumber = MAXIMUM_BETNUMBER_FIRSTPART;

		int nextBetNumber;

		/* iterate over bet array */
		for (int i = 0; i < BET_LENGTH; i++) {

			/* change possible generated tipp numbers when second part of the tipp begins */
			if (i == BET_LENGTH_FIRSTPART) {
				currentMinimumBetnumber = MINIMUM_BETNUMBER_SECONDPART;
				currentMaximumBetnumber = MAXIMUM_BETNUMBER_SECONDPART;
			}

			boolean isUnluckyNumber = false;

			/* iterate until random number is found which is not an unlucky number */
			while (!isUnluckyNumber) {
				/*
				 * generate random int in the interval [1,50] for first part and [1,10] for
				 * second part
				 */
				nextBetNumber = random.nextInt(currentMaximumBetnumber + 1 - currentMinimumBetnumber)
						+ currentMinimumBetnumber;

				/* test if generated tipp number is an unlucky number */
				for (int j : unluckyNumbers) {
					if (nextBetNumber == j) {
						isUnluckyNumber = true;
						break;
					}
				}

				/* test if generated tipp number is already selected */
				if (i < BET_LENGTH_FIRSTPART) {
					for (int k = 0; k < BET_LENGTH_FIRSTPART; k++) {
						if (nextBetNumber == bet[k]) {
							isUnluckyNumber = true;
							break;
						}
					}
				} else if (i == BET_LENGTH - 1 && !isUnluckyNumber)
					isUnluckyNumber = bet[i - 1] == nextBetNumber;

				/* stop iteration if generated tipp number is valid */
				if (!isUnluckyNumber) {
					bet[i] = nextBetNumber;
					isUnluckyNumber = true;
				} else {
					isUnluckyNumber = false;
				}
			}
		}
		return bet;
	}
}

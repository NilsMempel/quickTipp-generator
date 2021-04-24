package generators;

import java.util.Collection;
import java.util.Random;

/**
 * {@code LottoGenerator} generates a quick tipp for Lotto. It is
 * possible to choose numbers, which the quick tipp cannot contain.
 * 
 * @author Nils Mempel
 *
 */
public class LottoGenerator implements BetGenerator {

	/* length, minimum and maximum number of the bet */
	private static final int BET_LENGTH = 5;
	private static final int MINIMUM_BETNUMBER = 1;
	private static final int MAXIMUM_BETNUMBER = 49;

	/**
	 * Calls construtor of super class.
	 */
	public LottoGenerator() {
		super();
	}
	
	@Override
	public int[] generateBet(Collection<Integer> unluckyNumbers) {
		/* will be filled and returned */
		int[] bet = new int[BET_LENGTH];

		Random random = new Random();

		int nextBetNumber;

		/* iterate over bet array */
		for (int i = 0; i < BET_LENGTH; i++) {
			boolean isUnluckyNumber = false;

			/* iterate until random number is found which is not an unlucky number */
			while (!isUnluckyNumber) {
				/* generate random int in the interval [1,49] */
				nextBetNumber = random.nextInt(MAXIMUM_BETNUMBER + 1 - MINIMUM_BETNUMBER) + MINIMUM_BETNUMBER;

				/* test if generated tipp number is an unlucky number */
				for (int j : unluckyNumbers) {
					if (nextBetNumber == j) {
						isUnluckyNumber = true;
						break;
					}
				}

				/* test if generated tipp number is already selected */
				for (int k : bet) {
					if (nextBetNumber == k) {
						isUnluckyNumber = true;
						break;
					}
				}

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

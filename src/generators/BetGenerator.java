package generators;

import java.util.Collection;

/**
 * Interface for a {@code BetGenerator} that generates a random quick tipp for
 * lottery. It is possible to pass numbers which the quick tipp cannot conatin
 * then.
 * 
 * @author Nils Mempel
 * 
 */
public interface BetGenerator {

	/**
	 * Generates a random quick tipp for a certain bet type. The quick tipp contains
	 * only numbers, the argument {@code unluckyNumbers} does not contain.
	 * 
	 * @param unluckyNumbers the numbers that the quick tipp cannot contain
	 * @return the quick tipp as an {@code int[]}
	 */
	abstract int[] generateBet(Collection<Integer> unluckyNumbers);

}

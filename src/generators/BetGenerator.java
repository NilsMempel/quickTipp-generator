package generators;

import java.util.Collection;

public interface BetGenerator {

	abstract int[] generateBet(Collection<Integer> unluckyNumbers);
	
}

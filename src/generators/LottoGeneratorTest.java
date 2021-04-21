
package generators;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LottoGeneratorTest {

	private LottoGenerator lottoGenerator = new LottoGenerator();

	/* testdata for unlucky numbers */
	private List<Integer> list1;
	private List<Integer> list2;
	private List<Integer> list3;
	private List<Integer> list4;
	private List<Integer> list5;

	/* number of iterations of generating */
	private int runs = 100;

	@BeforeEach
	void setUp() throws Exception {
		/* testdata for unlucky numbers */
		list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
		list2 = Arrays.asList(55, 2, 6, 32, 49);
		list3 = Arrays.asList(22, 5, 7, 55, 35, 7568, 8, 45, 345, 6, 80, 223);
		list4 = Arrays.asList(5);
		list5 = Arrays.asList(-4, -5, -45, 0, 4, 4);
	}

	@Test
	void show() {

		list2.stream().forEach(val -> System.out.print(val + ", "));
		System.out.println();
		System.out.println(Arrays.toString(lottoGenerator.generateBet(list2)));
	}

	@Test
	void testGenerationOfUnluckyNumbers() {
		/* contains every generated tipp number*/
		List<Integer> collection1 = new LinkedList<>();
		List<Integer> collection2 = new LinkedList<>();

		int[] generatedNumbers1;
		int[] generatedNumbers2;

		/* generating tipp numbers */
		for (int i = 0; i < runs; i++) {
			generatedNumbers1 = lottoGenerator.generateBet(list1);
			generatedNumbers2 = lottoGenerator.generateBet(list2);

			/* add generated tipp numbers */
			for (int num : generatedNumbers1) {
				collection1.add(num);
			}
			for (int num : generatedNumbers2) {
				collection2.add(num);
			}
		}
		
		/* sorting for better performance */
		collection1.sort((val1, val2) -> val1 - val2);
		collection2.sort((val1, val2) -> val1 - val2);
		
		boolean hasDuplicates1 = false;
		boolean hasDuplicates2 = false;
		
		/* checking if generated numbers contain any unlucky number */
		for (int num : list1) {
			hasDuplicates1 = collection1.contains(num) ? true : hasDuplicates1;
		}
		for (int num : list2) {
			hasDuplicates2 = collection2.contains(num) ? true : hasDuplicates2;
		}
		
		assertFalse(hasDuplicates1);
		assertFalse(hasDuplicates2);
	}

	@Test
	void testModifiesArgument() {
		List<Integer> listCopy1 = new LinkedList<>();
		List<Integer> listCopy2 = new LinkedList<>();
		List<Integer> listCopy3 = new LinkedList<>();
		List<Integer> listCopy4 = new LinkedList<>();
		List<Integer> listCopy5 = new LinkedList<>();

		listCopy1.addAll(list1);
		listCopy2.addAll(list2);
		listCopy3.addAll(list3);
		listCopy4.addAll(list4);
		listCopy5.addAll(list5);

		lottoGenerator.generateBet(list1);
		lottoGenerator.generateBet(list2);
		lottoGenerator.generateBet(list3);
		lottoGenerator.generateBet(list4);
		lottoGenerator.generateBet(list5);

		assertTrue(list1.equals(listCopy1));
		assertTrue(list2.equals(listCopy2));
		assertTrue(list3.equals(listCopy3));
		assertTrue(list4.equals(listCopy4));
		assertTrue(list5.equals(listCopy5));

	}

	@Test
	void testArgumentNull() {
		try {
			lottoGenerator.generateBet(null);
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}
}

package test.generators;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.GenerationController;
import generators.BetGenerator;
import generators.EurojackpotGenerator;
import generators.LottoGenerator;

/**
 * Test of class {@link EurojackpotGenerator}.
 * 
 * @author Nils Mempel
 *
 */
class EurojackpotGeneratorTest {

	private EurojackpotGenerator eurojackpotGenerator = new EurojackpotGenerator();

	/* testdata for unlucky numbers */
	private List<Integer> list1;
	private List<Integer> list2;
	private List<Integer> list3;
	private List<Integer> list4;
	private List<Integer> list5;

	/* number of iterations of generating */
	private int runs = 100;

	/** 
	 * Sets up test data.
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		/* testdata for unlucky numbers */
		list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
		list2 = Arrays.asList(55, 2, -1, 32, 49);
		list3 = Arrays.asList(22, 5, 7, 55, 35, 7568, 8, 45, 345, 6, 80, 223);
		list4 = Arrays.asList(5);
		list5 = Arrays.asList(-4, -5, -45, 0, 4, 4);
	}

	/**
	 * Shows a generated quick tipp and the assigned unlucky numbers.
	 */
	@Test
	void show() {

		list2.stream().forEach(val -> System.out.print(val + ", "));
		System.out.println();
		System.out.println(Arrays.toString(eurojackpotGenerator.generateBet(list2)));
	}

	/**
	 * Test if {@link EurojackpotGenerator#generateBet(java.util.Collection)}
	 * generates a valid quick tipp which means:
	 * <ul>
	 * <li>No duplicates in the first part and in the second part
	 * <li>Contains no number of the given input
	 * <li>Contains only numbers in range [1,50]
	 * </ul>
	 */
	@Test
	void testGenerationOfUnluckyNumbers() {
		/* contains every generated tipp number */
		List<Integer> collection1 = new LinkedList<>();
		List<Integer> collection2 = new LinkedList<>();

		/* contains the currently generated tipp numbers */
		List<Integer> collectionCurrent1 = new LinkedList<>();
		List<Integer> collectionCurrent2 = new LinkedList<>();

		int[] generatedNumbers1;
		int[] generatedNumbers2;

		/* generating tipp numbers */
		for (int i = 0; i < runs; i++) {
			generatedNumbers1 = eurojackpotGenerator.generateBet(list1);
			generatedNumbers2 = eurojackpotGenerator.generateBet(list2);

			/*
			 * add generated tipp numbers and check if any number is not in [1,50] and if
			 * any number was generated before in the current generation for the first part
			 */
			for (int j = 0, num = generatedNumbers1[j]; j < 5; j++, num = generatedNumbers1[j]) {
				collection1.add(num);
				assertTrue(1 <= num && num <= 50);
				assertFalse(collectionCurrent1.contains(num));
				collectionCurrent1.add(num);
			}
			for (int j = 0, num = generatedNumbers2[j]; j < 5; j++, num = generatedNumbers2[j]) {
				collection2.add(num);
				assertTrue(1 <= num && num <= 50);
				assertFalse(collectionCurrent2.contains(num));
				collectionCurrent2.add(num);
			}

			collectionCurrent1.clear();
			collectionCurrent2.clear();

			/* check for duplicates in the second part */
			assertTrue(generatedNumbers1[5] != generatedNumbers1[6]);
			assertTrue(generatedNumbers2[5] != generatedNumbers2[6]);

			/* check if any number is not in [1,50] for the second part */
			assertTrue(1 <= generatedNumbers1[5] && generatedNumbers1[5] <= 50);
			assertTrue(1 <= generatedNumbers1[6] && generatedNumbers1[6] <= 50);
			assertTrue(1 <= generatedNumbers2[5] && generatedNumbers2[5] <= 50);
			assertTrue(1 <= generatedNumbers2[6] && generatedNumbers2[6] <= 50);

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

	/**
	 * Test if {@link EurojackpotGenerator#generateBet(java.util.Collection)} modifies the
	 * argument.
	 */
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

		eurojackpotGenerator.generateBet(list1);
		eurojackpotGenerator.generateBet(list2);
		eurojackpotGenerator.generateBet(list3);
		eurojackpotGenerator.generateBet(list4);
		eurojackpotGenerator.generateBet(list5);

		assertTrue(list1.equals(listCopy1));
		assertTrue(list2.equals(listCopy2));
		assertTrue(list3.equals(listCopy3));
		assertTrue(list4.equals(listCopy4));
		assertTrue(list5.equals(listCopy5));

	}

	@Test
	void testArgumentNull() {
		try {
			eurojackpotGenerator.generateBet(null);
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}
}

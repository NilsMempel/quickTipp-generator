package controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import generators.EurojackpotGenerator;
import generators.LottoGenerator;
import util.IllegalUserInputException;

/**
 * Controls the generation of quick tipps.
 * 
 * @author Nils Mempel
 *
 */
public class GenerationController {

	/* controller */
	private MainController mainController;
	private UIController uiController;
	private LogController logController;

	/* quick tipp generators */
	private LottoGenerator lottoGenerator;
	private EurojackpotGenerator eurojackpotGenerator;

	/* unlucky numbers which wont be considerated for generation */
	private int[] unluckyNumbers;

	/* Logger for logging events in the appplication */
	private Logger logger;

	/* minumum and maximum of possible unlucky numbers */
	private static final int MINIMUM_UNLUCKYNUMBER = 1;
	private static final int MAXIMUM_UNLUCKYNUMBER = 50;

	/* maximum quantity of unlucky numbers */
	private static final int MAXIMUM_QUANTITY_UNLUCKYNUMBER = 6;

	/* predefined error messages for illegal user input */
	private static final String USER_INPUT_ERROR_MESSAGE_NOINT = "Alle Argumente muessen Zahlen sein.";
	private static final String USER_INPUT_ERROR_MESSAGE_OUTOFRANGE = String
			.format("Alle Zahlen muessen zwischen %d und %d liegen.", MINIMUM_UNLUCKYNUMBER, MAXIMUM_UNLUCKYNUMBER);
	private static final String USER_INPUT_ERROR_MESSAGE_TOOMANYARGUMENTS = String
			.format("Es duerfen maximal %d Zahlen übergeben werden.", MAXIMUM_QUANTITY_UNLUCKYNUMBER);

	/**
	 * Sets {@code mainController} and initializes the generators.
	 * 
	 * @param mainController the mainController to set
	 */
	public GenerationController(MainController mainController) {
		this.mainController = mainController;
		lottoGenerator = new LottoGenerator();
		eurojackpotGenerator = new EurojackpotGenerator();
	}

	/**
	 * Validates if {@code userInput} fullfills the terms and conditions for the
	 * input of unlucky numbers.<br>
	 * Conditions are String contains at most
	 * {@value GenerationController#MAXIMUM_QUANTITY_UNLUCKYNUMBER} elements which
	 * can be parsed to an {@code int}. Any number has to be between
	 * {@value GenerationController#MINIMUM_UNLUCKYNUMBER} and
	 * {@value GenerationController#MAXIMUM_UNLUCKYNUMBER} (both inclusive).
	 * 
	 * @param userInput unlucky numbers stored in a {@code String[]} object
	 * @return true if {@code userInput} meets the conditions
	 * @throws IllegalUserInputException contains error message to show the user
	 */
	public boolean validateUnluckyNumbers(String[] userInput) throws IllegalUserInputException {
		/* test for existence of arguments */
		if (userInput.length == 0)
			return true;
		/* test for too many arguments */
		else if (userInput.length > MAXIMUM_QUANTITY_UNLUCKYNUMBER)
			throw new IllegalUserInputException(USER_INPUT_ERROR_MESSAGE_TOOMANYARGUMENTS);

		int num;

		/* test if any number is out of range or not an int */
		for (String s : userInput) {
			try {
				num = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				throw new IllegalUserInputException(USER_INPUT_ERROR_MESSAGE_NOINT);
			}

			if (num < MINIMUM_UNLUCKYNUMBER || num > MAXIMUM_UNLUCKYNUMBER)
				throw new IllegalUserInputException(USER_INPUT_ERROR_MESSAGE_OUTOFRANGE);
		}

		logger.info("Unlucky numbers validated.");

		return true;
	}

	/**
	 * Generates a quick tipp for lotto which does not contain an element of {@code unluckyNumbers}.
	 * 
	 * @return a quick tipp for lotto as an {@code int[]}
	 */
	public int[] generateLottoBet() {
		int[] val = lottoGenerator.generateBet(convertToCollection(unluckyNumbers));
	
		logger.info("Lotto bet generated.");
	
		return val;
	
	}

	/**
	 * Generates a quick tipp for eurojackpot which does not contain an element of {@code unluckyNumbers}.
	 * 
	 * @return a quick tipp for eurojackpot as an {@code int[]}
	 */
	public int[] generateEurojackpotBet() {
		int[] val = eurojackpotGenerator.generateBet(convertToCollection(unluckyNumbers));
	
		logger.info("Eurojackpot bet generated");
	
		return val;
	}

	/**
	 * Converts an {@code int[]} to a {@code Collection<Integer>} with the condition {@code equals()} returns true.
	 * 
	 * 
	 * @param arr an {@code int[]} to convert
	 * @return a {@code Collection<Integer>}
	 */
	private Collection<Integer> convertToCollection(int[] arr) {
		List<Integer> collection = new LinkedList<>();
		for (int i : arr) {
			collection.add(i);
		}
	
		return collection;
	}

	/**
	 * @param uiController the uiController to set
	 */
	public void setUiController(UIController uiController) {
		this.uiController = uiController;
	}

	/**
	 * @param logController the logController to set
	 */
	public void setLogController(LogController logController) {
		this.logController = logController;
	}

	/**
	 * 
	 * @return the unluckyNumbers
	 */
	public int[] getUnluckyNumbers() {
		/* load if not set yet */
		if (unluckyNumbers == null) {
			unluckyNumbers = logController.loadUnluckyNumbers();
		}
		return this.unluckyNumbers;
	}

	/**
	 * @param unluckyNumbers the unluckyNumbers to set
	 */
	public void setUnluckyNumbers(String[] userInput) {
		/* put unlucky numbers in an int array */
		unluckyNumbers = new int[userInput.length];
		for (int i = 0; i < userInput.length; i++) {
			unluckyNumbers[i] = Integer.parseInt(userInput[i]);
		}

		/* save unlucky numbers */
		logController.saveUnluckyNumbers(unluckyNumbers);
	}

	/**
	 * @return the lottoGenerator
	 */
	public LottoGenerator getLottoGenerator() {
		return lottoGenerator;
	}

	/**
	 * @return the eurojackpotGenerator
	 */
	public EurojackpotGenerator getEurojackpotGenerator() {
		return eurojackpotGenerator;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}

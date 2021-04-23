package controller;


import generators.EurojackpotGenerator;
import generators.LottoGenerator;
import util.IllegalUserInputException;

public class GenerationController {

	private MainController mainController;
	private UIController uiController;
	private LogController logController;
	
	/* quick tipp generators */
	private LottoGenerator lottoGenerator;
	private EurojackpotGenerator eurojackpotGenerator;
	
	public GenerationController() {
		lottoGenerator = new LottoGenerator();
		eurojackpotGenerator = new EurojackpotGenerator();
	}
	
	private int[] unluckyNumbers;

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
			.format("Es duerfen maximal %d Zahlen übergeben werden", MAXIMUM_QUANTITY_UNLUCKYNUMBER);

	public GenerationController(MainController mainController) {
		this.mainController = mainController;
	}

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
		return true;
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
		logController.save(unluckyNumbers);
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

}

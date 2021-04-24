package controller;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import util.IllegalUserInputException;

/**
 * Handles communication with the user through the console.
 * 
 * @author Nils Mempel
 *
 */
public class UIController {

	/* controller */
	private MainController mainController;
	private GenerationController generationController;
	private LogController logController;

	/* if true the Application terminates */
	private boolean endApplication = false;

	/* gets user input thorugh the console */
	private Scanner scanner;

	/* Logger for logging events in the appplication */
	private Logger logger;

	/* predefined input shortcuts for centext menu options */
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_END_SHORTCUT = "b";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS_SHORTCUT = "u";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS_SHORTCUT = "d";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO_SHORTCUT = "lotto";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT_SHORTCUT = "euro";

	/* predefined options in context menu */
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_END = String.format("BEENDEN (Eingabe -> '%s')",
			CONTEXT_MENU_DIALOGUE_OPTION_END_SHORTCUT);
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS = String.format(
			"UNGLUECKSZAHLEN ANZEIGEN (Eingabe -> '%s')", CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS_SHORTCUT);
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS = String.format(
			"UNGLUECKSZAHLEN LOESCHEN (Eingabe -> '%s')", CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS_SHORTCUT);
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO = String
			.format("QUICKTIPP LOTTO (Eingabe -> '%s')", CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO_SHORTCUT);
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT = String.format(
			"QUICKTIPP EUROJACKPOT (Eingabe -> '%s')", CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT_SHORTCUT);

	/* predefined message for user input */
	private static final String USER_INPUT_DIALOGUE_MESSAGE = "Geben Sie Ihre Unglueckszahlen erneut ein (In einer Reihe und durch ein Leerzeichen getrennt).";
	private static final String USER_INPUT_DIALOGUE_INVALID_MESSAGE = "Ihre Eingabe war ungueltig.";
	private static final String USER_INPUT_DIALOGUE_ENDAPPLICATION_MESSAGE = "Die Anwendung wurde beendet.";
	private static final String USER_INPUT_DIALOGUE_DELETE_MESSAGE = "Ihre Unglueckszahlen wurden geloescht.";
	private static final String USER_INPUT_DIALOGUE_LOAD = "Ihre Unglueckszahlen wurden geladen, da Sie keine Zahlen bei Programmstart angegeben haben.";
	private static final String USER_INPUT_DIALOGUE_SAVED_MESSAGE = "Ihre Unglueckszahlen wurden gespeichert.";

	/* predefined message to show in context menu */
	private static final String CONTEXT_MENU_DIALOGUE_MESSAGE = String.format(
			"Waehlen Sie eine der folgenden Optionen aus, indem Sie eine Eingabe machen.%n%s%n%s%n%s%n%s%n%s",
			CONTEXT_MENU_DIALOGUE_OPTION_END, CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS,
			CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS, CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO,
			CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT);

	/**
	 * Sets the {@code mainController} and initializes the {@code scanner} for
	 * receiving user input through console.
	 * 
	 * @param mainController the mainController to set
	 */
	public UIController(MainController mainController) {
		this.mainController = mainController;
		scanner = new Scanner(System.in);
	}

	/**
	 * Processes the user input given at program start from the user. Validates it
	 * and shows feedback in the console.
	 * 
	 * @param applicationStartUserInput user input at program start
	 */
	public void processApplicationStartUserInput(String[] applicationStartUserInput) {
		boolean isValid = false;
		String newInput;

		/* terminates if input is valid */
		while (!isValid) {
			try {
				isValid = generationController.validateUnluckyNumbers(applicationStartUserInput);
			} catch (IllegalUserInputException e) {
				System.out.println(e.getMessage());
				logger.info("Illegal user input.");
			}

			/* no input */
			if (isValid && applicationStartUserInput.length == 0) {
				generationController.getUnluckyNumbers();
				System.out.println(USER_INPUT_DIALOGUE_LOAD);
				logger.info("Illegal user input.");
				/* valid input */
			} else if (isValid) {
				generationController.setUnluckyNumbers(applicationStartUserInput);
				System.out.println(USER_INPUT_DIALOGUE_SAVED_MESSAGE);
				logger.info("Valid user input.");
				/* input not valid */
			} else {
				System.out.println(USER_INPUT_DIALOGUE_MESSAGE);
				newInput = scanner.nextLine();
				applicationStartUserInput = newInput.split(" ");
			}
		}
	}

	/**
	 * Listens to the input given from the user through the console. Shows a
	 * ceontext menu and delegates the handling of the input.
	 */
	public void listenToEvents() {
		String userInput;
		while (!endApplication) {
			showContextMenuDialogue();

			userInput = scanner.nextLine();

			logger.info("Userinput: " + userInput);

			processUserInput(userInput);
		}

		logger.info("Application terminates.");
	}

	/**
	 * Processes the user input given through the console.
	 * 
	 * @param userInput the user input given through the console
	 */
	private void processUserInput(String userInput) {
		switch (userInput) {

		/* end application */
		case CONTEXT_MENU_DIALOGUE_OPTION_END_SHORTCUT:
			endApplication = true;
			System.out.println(USER_INPUT_DIALOGUE_ENDAPPLICATION_MESSAGE);
			break;
		/* show unlucky numbers */
		case CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS_SHORTCUT:
			System.out.println("Unglueckszahlen: " + Arrays.toString(generationController.getUnluckyNumbers()));
			break;
		/* delete unlucky numbers */
		case CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS_SHORTCUT:
			generationController.setUnluckyNumbers(new String[0]);
			System.out.println(USER_INPUT_DIALOGUE_DELETE_MESSAGE);
			break;
		/* generate lotto quick tipp */
		case CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO_SHORTCUT:
			System.out.println("QuickTipp Lotto: " + Arrays.toString(generationController.generateLottoBet()));
			break;
		/* generate eurojackpot quick tipp */
		case CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT_SHORTCUT:
			System.out.println(
					"QuickTipp Eurojackpot: " + Arrays.toString(generationController.generateEurojackpotBet()));
			break;
		/* invalid input */
		default:
			System.out.println(USER_INPUT_DIALOGUE_INVALID_MESSAGE);
			break;
		}

		logger.info("User input succesfully processed.");

	}

	/**
	 * Shows predefined context menu in console.
	 */
	private void showContextMenuDialogue() {
		System.out.println(CONTEXT_MENU_DIALOGUE_MESSAGE + "\n");
		logger.info("Context menu displayed to user.");
	}

	/**
	 * @param generationController the generationController to set
	 */
	public void setGenerationController(GenerationController generationController) {
		this.generationController = generationController;
	}

	/**
	 * @param logController the logController to set
	 */
	public void setLogController(LogController logController) {
		this.logController = logController;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}

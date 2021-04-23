package controller;

import java.util.Arrays;
import java.util.Scanner;

import util.IllegalUserInputException;

public class UIController {

	private MainController mainController;
	private GenerationController generationController;
	private LogController logController;

	/* if true the application terminates */
	private boolean endApplication = false;

	/* gets user input thorugh the console */
	private Scanner scanner;

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

	public UIController(MainController mainController) {
		this.mainController = mainController;
		scanner = new Scanner(System.in);
	}

	public void processApplicationStartUserInput(String[] applicationStartUserInput) {
		boolean isValid = false;
		String newInput;

		/* terminates if input is valid */
		while (!isValid) {
			try {
				isValid = generationController.validateUnluckyNumbers(applicationStartUserInput);
			} catch (IllegalUserInputException e) {
				System.out.println(e.getMessage());
			}

			/* no input */
			if (isValid && applicationStartUserInput.length == 0) {
				generationController.getUnluckyNumbers();
				System.out.println(USER_INPUT_DIALOGUE_LOAD);
				/* valid input */
			} else if (isValid) {
				generationController.setUnluckyNumbers(applicationStartUserInput);
				System.out.println(USER_INPUT_DIALOGUE_SAVED_MESSAGE);
				/* input not valid */
			} else {
				System.out.println(USER_INPUT_DIALOGUE_MESSAGE);
				newInput = scanner.nextLine();
				applicationStartUserInput = newInput.split(" ");
			}
		}
	}

	public void listenToEvents() {
		String userInput;
		while (!endApplication) {
			showContextMenuDialogue();

			userInput = scanner.nextLine();

			processUserInput(userInput);
		}
	}

	private void processUserInput(String userInput) {
		switch (userInput) {

		case CONTEXT_MENU_DIALOGUE_OPTION_END_SHORTCUT:
			endApplication = true;
			System.out.println(USER_INPUT_DIALOGUE_ENDAPPLICATION_MESSAGE);
			break;
		case CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS_SHORTCUT:
			System.out.println("Unglueckszahlen: " + Arrays.toString(generationController.getUnluckyNumbers()));
			break;
		case CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS_SHORTCUT:
			generationController.setUnluckyNumbers(new String[0]);
			System.out.println(USER_INPUT_DIALOGUE_DELETE_MESSAGE);
			break;
		case CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO_SHORTCUT:
			System.out.println("QuickTipp Lotto: " + Arrays.toString(generationController.generateLottoBet()));
			break;
		case CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT_SHORTCUT:
			System.out.println(
					"QuickTipp Eurojackpot: " + Arrays.toString(generationController.generateEurojackpotBet()));
			break;
		default:
			System.out.println(USER_INPUT_DIALOGUE_INVALID_MESSAGE);
			break;
		}

	}

	private void showContextMenuDialogue() {
		System.out.println(CONTEXT_MENU_DIALOGUE_MESSAGE + "\n");
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

}

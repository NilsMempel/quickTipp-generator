package controller;

import java.util.Scanner;

public class UIController {

	private MainController mainController;
	private GenerationController generationController;
	private LogController logController;

	/* if true the application terminates */
	private boolean endApplication = false;

	/* gets user input thorugh the console */
	private Scanner scanner;

	private static final String CONTEXT_MENU_DIALOGUE_OPTION_END = "BEENDEN (Eingabe -> 'b')";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_SHOW_UNLUCKYNUMBERS = "UNGLUECKSZAHLEN ANZEIGEN (Eingabe -> 'u')";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_DELETE_UNLUCKYNUMBERS = "UNGLUECKSZAHLEN LOESCHEN (Eingabe -> 'l')";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_LOTTO = "QUICKTIPP LOTTO (Eingabe -> 'lotto')";
	private static final String CONTEXT_MENU_DIALOGUE_OPTION_GENERATE_EUROJACKPOT = "QUICKTIPP EUROJACKPOT (Eingabe -> 'euro')";

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

	}

	public void listenToEvents() {
		while (!endApplication) {
			showContextMenuDialogue();

//			endApplication = true;
		}
	}

	private void showContextMenuDialogue() {
		System.out.println(CONTEXT_MENU_DIALOGUE_MESSAGE);
		String userInput = scanner.next();
		System.out.printf("Input was %s.%n", userInput);
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

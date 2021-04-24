package main;

import controller.MainController;

/**
 * This application generates random Quick-Tipps for lottery with the option to
 * pick some numbers, that won't be consideradet for generation.
 * <p>
 * The follwing functionalitys are provided.
 * <ul>
 * <li>The application can be started and controlled with a console</li>
 * <li>Generation of Quick-Tipps for "Lotto" and "Eurojackpot"</li>
 * <li>Input of up to six unlucky numbers as arguments when starting the application (numbers that won't be generated for Quick-Tipps) which will be saved and which can be deleted</li>
 * <li>Logging of activities in the application saved in a log-document
 * </ul>
 * 
 * @author Nils Mempel
 *
 */
public class Application {

	/**
	 * The application starts by initializing controller and model.
	 * 
	 * @param args the user input given at programmstart
	 */
	public static void main(String[] args) {
		MainController mainController = new MainController(args);
		mainController.runApplication();
	}

}

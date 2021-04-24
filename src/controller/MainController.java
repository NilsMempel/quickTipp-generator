package controller;

import java.util.logging.Logger;

/**
 * {@code mainController} coordinates communication between other controler and initialization including the Logger.
 * 
 * @author Nils Mempel
 *
 */
public class MainController {

	/* controler */
	private LogController logController;
	private UIController uiController;
	private GenerationController generationController;
	
	/* Logger for logging events in the appplication */
	private Logger logger;

	/* store user input from program start */
	private String[] applicationStartUserInput;

	/**
	 * Stores user input and initializes the other controller and the logger.
	 * 
	 * @param args the user input made when the program started
	 */
	public MainController(String[] args) {
		applicationStartUserInput = args;
		initializeController();
		logController.initializeLogger();
		
		logger.info("Initializing completed.");
	}

	/**
	 * Starts the application. If it terminates the Application will be closed.
	 * <p>
	 * Delegates handling userinput to the {@code UIController}.
	 */
	public void runApplication() {
		uiController.processApplicationStartUserInput(applicationStartUserInput);
		uiController.listenToEvents();
	}

	/**
	 * Initializes every Controller({@code LogController}, {@code UIController},
	 * {@code GenerationController} and sets associations to the other Controller.
	 * 
	 */
	private void initializeController() {
		logController = new LogController(this);
		uiController = new UIController(this);
		generationController = new GenerationController(this);

		logController.setGenerationController(generationController);
		logController.setUiController(uiController);

		uiController.setGenerationController(generationController);
		uiController.setLogController(logController);

		generationController.setLogController(logController);
		generationController.setUiController(uiController);
	}

	/**
	 * @return the logController
	 */
	public LogController getLogController() {
		return logController;
	}

	/**
	 * @param logController the logController to set
	 */
	public void setLogController(LogController logController) {
		this.logController = logController;
	}

	/**
	 * @return the uiController
	 */
	public UIController getUiController() {
		return uiController;
	}

	/**
	 * @param uiController the uiController to set
	 */
	public void setUiController(UIController uiController) {
		this.uiController = uiController;
	}

	/**
	 * @return the generationController
	 */
	public GenerationController getGenerationController() {
		return generationController;
	}

	/**
	 * @param generationController the generationController to set
	 */
	public void setGenerationController(GenerationController generationController) {
		this.generationController = generationController;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}

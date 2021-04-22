package controller;

public class MainController {

	private LogController logController;
	private UIController uiController;
	private GenerationController generationController;

	/* store user input from program start */
	private String[] applicationStartUserInput;

	/**
	 * 
	 * @param args the user input made when the program started
	 */
	public MainController(String[] args) {
		applicationStartUserInput = args;
		initializeController();
	}

	/**
	 * Starts the Application. If it terminates the application will be closed.
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
}

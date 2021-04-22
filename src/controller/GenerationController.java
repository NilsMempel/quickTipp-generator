package controller;

public class GenerationController {

	private MainController mainController;
	private UIController uiController;
	private LogController logController;

	public GenerationController(MainController mainController) {
		this.mainController = mainController;
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

}

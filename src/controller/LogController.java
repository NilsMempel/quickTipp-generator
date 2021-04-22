package controller;


public class LogController {
	
	private MainController mainController;
	private UIController uiController;
	private GenerationController generationController;

	public LogController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * @param uiController the uiController to set
	 */
	public void setUiController(UIController uiController) {
		this.uiController = uiController;
	}

	/**
	 * @param generationController the generationController to set
	 */
	public void setGenerationController(GenerationController generationController) {
		this.generationController = generationController;
	}

}

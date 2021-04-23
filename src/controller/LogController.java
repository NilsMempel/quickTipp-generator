package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.System.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class LogController {

	private MainController mainController;
	private UIController uiController;
	private GenerationController generationController;

	private java.util.logging.Logger logger;

	public LogController(MainController mainController) {
		this.mainController = mainController;
	}
	
	public void initializeLogger() {
		configureLogger();
		passLoggerToController();
	}

	private void passLoggerToController() {
		mainController.setLogger(logger);
		generationController.setLogger(logger);
		uiController.setLogger(logger);
	}

	private void configureLogger() {
		logger = logger.getGlobal();

		logger.setLevel(Level.ALL);
		
		logger.setUseParentHandlers(false);

		try {
			FileHandler fileHandler;
			fileHandler = new FileHandler("src/data/logging");
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Logger initialized and configured.");

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

	public void save(int[] unluckyNumbers) {
		/* serialize unlucky numbers */
		try (FileOutputStream fileOut = new FileOutputStream("src/data/unluckyNumbers.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {

			/* write object to document */
			out.writeObject(unluckyNumbers);
		} catch (IOException i) {
			i.printStackTrace();
		}
		
		logger.info("Unlucky numbers saved.");
	}

	public int[] loadUnluckyNumbers() {
		int[] loadedUnluckyNumbers = null;

		/* deserialize unlucky numbers */
		try (FileInputStream fileIn = new FileInputStream("src/data/unluckyNumbers.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);) {

			/* read object from document */
			loadedUnluckyNumbers = (int[]) in.readObject();

		} catch (java.io.EOFException e) {
			return new int[0];
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		
		logger.info("Unlucky numbers loaded.");
		
		return loadedUnluckyNumbers;
	}

}

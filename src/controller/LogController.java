package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class LogController {

	/* controller */
	private MainController mainController;
	private UIController uiController;
	private GenerationController generationController;

	/* Logger for logging events in the appplication */
	private java.util.logging.Logger logger;

	private static final String LOGFILE_PATH = "src/data/logging";

	private static final String UNLUCKYNUMBERS_SERIALIZATION_PATH = "src/data/unluckyNumbers.ser";

	/**
	 * Sets the {@code mainController}.
	 * 
	 * @param mainController the mainController to set
	 */
	public LogController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Initializes, configures and passes the global logger to the other controller.
	 */
	public void initializeLogger() {
		configureLogger();
		passLoggerToController();
	}

	/**
	 * Loads the {@code unluckyNumbers} object from a serialization file.
	 * 
	 * @return the unlucky numbers as an {@code int[]}
	 */
	public int[] loadUnluckyNumbers() {
		int[] loadedUnluckyNumbers = null;
	
		/* deserialize unlucky numbers */
		try (FileInputStream fileIn = new FileInputStream(UNLUCKYNUMBERS_SERIALIZATION_PATH);
				ObjectInputStream in = new ObjectInputStream(fileIn);) {
	
			/* read object from document */
			loadedUnluckyNumbers = (int[]) in.readObject();
	
		} catch (java.io.EOFException e) {
			/* unlucky numbers are not saved */
			return new int[0];
		} catch (IOException i) {
			i.printStackTrace();
			logger.severe(i.getMessage());
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			logger.severe(c.getMessage());
		}
	
		logger.info("Unlucky numbers loaded.");
	
		return loadedUnluckyNumbers;
	}

	/**
	 * Saves the {@code unluckyNumbers} object given as an argument in a serialization file.
	 * 
	 * @param unluckyNumbers the unlucky numbers given as arguments at programstart
	 */
	public void saveUnluckyNumbers(int[] unluckyNumbers) {
		/* serialize unlucky numbers */
		try (FileOutputStream fileOut = new FileOutputStream(UNLUCKYNUMBERS_SERIALIZATION_PATH);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
	
			/* write object to document */
			out.writeObject(unluckyNumbers);
		} catch (IOException i) {
			i.printStackTrace();
			logger.severe(i.getMessage());
		}
	
		logger.info("Unlucky numbers saved.");
	}

	/**
	 * Configures the global logger, so that every log messsage is logged without
	 * regard to it's level.<br>
	 * Registeres a handler for the logger which saves the log messages in a log
	 * file and ungegisteres the console as a handler.
	 */
	private void configureLogger() {
		logger = logger.getGlobal();
	
		logger.setLevel(Level.ALL);
	
		logger.setUseParentHandlers(false);
	
		try {
			FileHandler fileHandler;
			fileHandler = new FileHandler(LOGFILE_PATH);
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
	 * Passes the global logger to the other controller.
	 */
	private void passLoggerToController() {
		mainController.setLogger(logger);
		generationController.setLogger(logger);
		uiController.setLogger(logger);
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

package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	public void save(int[] unluckyNumbers) {
		/* serialize unlucky numbers */
		try (FileOutputStream fileOut = new FileOutputStream("src/data/unluckyNumbers.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {

			/* write object to document */
			out.writeObject(unluckyNumbers);
		} catch (IOException i) {
			i.printStackTrace();
		}
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
		return loadedUnluckyNumbers;
	}

}

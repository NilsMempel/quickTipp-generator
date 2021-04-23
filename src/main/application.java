package main;

import controller.MainController;

public class application {

	public static void main(String[] args) {
		MainController mainController = new MainController(args);
		mainController.runApplication();
	}

}

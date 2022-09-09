package ru.itlyc.tutorial1;

import com.badlogic.gdx.Game;

public class MainController extends Game {

	@Override
	public void create() {
		FirstLevelScreen firstLevelScreen = new FirstLevelScreen();

		setScreen(firstLevelScreen);
	}
}

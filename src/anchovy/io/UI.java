package anchovy.io;

import anchovy.GameEngine;

// TODO needs to be made abstract, not for now so that i don't have to create the functionality
public class UI {
	GameEngine gameEngine;
	
	public UI(GameEngine gameEngine){
		this.gameEngine = gameEngine;
	}
}

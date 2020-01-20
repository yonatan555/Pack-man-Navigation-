package gameClient;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;


import Server.Game_Server;
import Server.game_service;

class autoTest {

	@Test
	void testGetrobs() throws JSONException {
		auto m =   new auto ();
		game_service game = Game_Server.getServer(23);
		int i  = m.getrobs(game);
		
		assertEquals(3, i);
		
	}
}

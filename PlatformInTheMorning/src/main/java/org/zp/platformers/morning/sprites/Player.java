package org.zp.platformers.morning.sprites;

import org.zp.gworks.gui.sprites.Sprite;
import org.zp.platformers.morning.states.PlayState;
import org.zp.platformers.morning.states.ticklisteners.PlayerMovement;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 8/29/2015
 * Time: 6:26 PM
 */
public class Player extends Sprite {
	private PlayerMovement playerMovement;

	public Player(PlayState playState) {
		movement.setAcceleration(200);
		movement.setMaxVelocity(1000);
		movement.setNaturalDeceleration(-200);
		movement.setDeceleration(-200);
		getRenderer().setSprite(createSprite());
		playerMovement = new PlayerMovement(playState, this);
	}


	private BufferedImage createSprite() {
		BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 100, 100);
		g.dispose();
		return image;
	}

	public PlayerMovement getPlayerMovement() {
		return playerMovement;
	}
}

package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.sprites.game.Player;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerMovement implements GTickListener {
	private Player player;
	private KeyEvent lastEvent;
	private Movement movement = Movement.NONE;

	enum Movement {
		NONE,
		UP,
		RIGHT,
		DOWN,
		LEFT
	}
	//TODO EXPAND ENUM FOR DIRECTIONAL PRESS AND RELEASE
	public PlayerMovement(Player player) {
		this.player = player;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		KeyEvent pressedEvent = GameFrame.getCanvas().getGKeyListener().getNextPressedEvent();
		if(pressedEvent != null) {
			if(lastEvent == null || pressedEvent.getWhen() > lastEvent.getWhen()) {
				switch (pressedEvent.getKeyCode()) {
					case KeyEvent.VK_UP:
						movement = Movement.UP;
						lastEvent = pressedEvent;
						break;
					case KeyEvent.VK_RIGHT:
						movement = Movement.RIGHT;
						lastEvent = pressedEvent;
						break;
					case KeyEvent.VK_DOWN:
						movement = Movement.DOWN;
						lastEvent = pressedEvent;
						break;
					case KeyEvent.VK_LEFT:
						movement = Movement.LEFT;
						lastEvent = pressedEvent;
						break;
				}
			}
		}
		KeyEvent releasedEvent = GameFrame.getCanvas().getGKeyListener().getNextReleasedEvent();
		if(releasedEvent != null) {
			if(lastEvent == null || releasedEvent.getWhen() > lastEvent.getWhen()) {
				switch (releasedEvent.getKeyCode()) {
					case KeyEvent.VK_UP:
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_LEFT:
						movement = Movement.NONE;
						lastEvent = releasedEvent;
						break;
				}
			}
		}
		if(movement != Movement.NONE) {
			int distance = Math.round(player.getSpeed() * delta / 1000000000);
			switch (movement) {
				case UP:
					player.setLocation(new Point(player.getLocation().x, player.getLocation().y - distance));
					break;
				case RIGHT:
					player.setLocation(new Point(player.getLocation().x + distance, player.getLocation().y));
					break;
				case DOWN:
					player.setLocation(new Point(player.getLocation().x, player.getLocation().y + distance));
					break;
				case LEFT:
					player.setLocation(new Point(player.getLocation().x - distance, player.getLocation().y));
					break;
			}
		}
	}
}

package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.sprites.game.Player;
import org.zp.blockdude.states.playstate.PlayState.UI_CONSTANTS;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 7/31/2014
 * Time: 5:48 PM
 */
public class HealthRenderer implements GRenderListener {
	private final int HEALTH_SPEED = 50; //health points per second
	private int lastHealth;
	private Player player;

	public HealthRenderer(Player player) {
		this.player = player;
		lastHealth = player.getHealth();
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.GREEN);
		double dHealth = HEALTH_SPEED * delta / 1000000000D;
		if (player.getHealth() - lastHealth < 0) {
			lastHealth -= Math.ceil(dHealth);
		} else if (player.getHealth() - lastHealth > 0) {
			lastHealth += Math.ceil(dHealth);
		}

		graphics.setColor(Color.GREEN);

		int healthWidth = Math.round(
				Math.round(
						(UI_CONSTANTS.INFO_AREA_RIGHT - UI_CONSTANTS.HEALTH_BAR_START) * (lastHealth / 100D)
				)
		);

		graphics.fillRect(UI_CONSTANTS.HEALTH_BAR_START,
				UI_CONSTANTS.INFO_AREA_TOP,
				healthWidth,
				UI_CONSTANTS.INFO_AREA_HEIGHT);

		graphics.setColor(Color.BLACK);
		graphics.drawRect(
				UI_CONSTANTS.HEALTH_BAR_START,
				UI_CONSTANTS.INFO_AREA_TOP,
				UI_CONSTANTS.INFO_AREA_RIGHT - UI_CONSTANTS.HEALTH_BAR_START,
				UI_CONSTANTS.INFO_AREA_HEIGHT
		);

		int xPos = UI_CONSTANTS.LIVES_BAR_END - 25;
		for (int iii = 0; iii < player.getLives(); iii++) {
			graphics.drawImage(player.getRenderer().getSprite(), xPos, UI_CONSTANTS.INFO_AREA_TOP, 25, 25, null);
			xPos -= 30;
		}
	}

	public int getHealth() {
		return lastHealth;
	}
}

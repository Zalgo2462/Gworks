package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 8/28/2015
 * Time: 5:02 PM
 */
public class EnemyHealthRenderer implements GRenderListener {
	private static final int HEALTH_SPEED = 50; //health points per second
	private int displayedHealth;
	private Enemy enemy;

	public EnemyHealthRenderer(Enemy enemy) {
		this.enemy = enemy;
		this.displayedHealth = enemy.getHealth();
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		int health = enemy.getHealth();
		double dHealth = HEALTH_SPEED * delta / 1000000000D;
		if (health - displayedHealth < 0) {
			if (displayedHealth - Math.ceil(dHealth) < health) {
				dHealth = displayedHealth - health;
			}
			displayedHealth -= Math.ceil(dHealth);
		} else if (health - displayedHealth > 0) {
			if (displayedHealth + Math.ceil(dHealth) > enemy.getHealth()) {
				dHealth = health - displayedHealth;
			}
			displayedHealth += Math.ceil(dHealth);
		}

		int healthWidth = Math.round(Math.round(PlayState.UI_CONSTANTS.ENEMY_HEALTH_WIDTH * (displayedHealth / 100D)));
		int posX = (int) enemy.getMovement().getLocation().getX() +
				(int) (enemy.getRenderer().getBounds().getWidth() / 2) +
				-(PlayState.UI_CONSTANTS.ENEMY_HEALTH_WIDTH / 2);
		int posY = (int) enemy.getMovement().getLocation().getY() + (int) (enemy.getRenderer().getBounds().getHeight() / 2);
		int padY = -1 * (20 + PlayState.UI_CONSTANTS.ENEMY_HEALTH_HEIGHT + (int) enemy.getRenderer().getBounds().getHeight() / 2);
		if (posY + padY <= PlayState.UI_CONSTANTS.PLAY_AREA_TOP) {
			padY *= -1;
		}
		graphics.setColor(ColorScheme.MENU_BACKGROUND.getColor().brighter().brighter());
		graphics.fillRect(posX, posY + padY, PlayState.UI_CONSTANTS.ENEMY_HEALTH_WIDTH, PlayState.UI_CONSTANTS.ENEMY_HEALTH_HEIGHT);
		graphics.setColor(ColorScheme.LIGHTER_RED.getColor());
		graphics.fillRect(posX, posY + padY, healthWidth, PlayState.UI_CONSTANTS.ENEMY_HEALTH_HEIGHT);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(posX, posY + padY, PlayState.UI_CONSTANTS.ENEMY_HEALTH_WIDTH, PlayState.UI_CONSTANTS.ENEMY_HEALTH_HEIGHT);
	}
}

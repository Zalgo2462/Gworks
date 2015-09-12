package org.zp.platformers.morning.states.renderlisteners;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.gui.sprites.Sprite;
import org.zp.gworks.logic.GTickListener;
import org.zp.platformers.morning.states.PlayState;

import java.awt.*;

/**
 * Date: 8/29/2015
 * Time: 6:36 PM
 */
public class Camera implements GRenderListener, GTickListener {
	private PlayState playState;
	private Sprite[] sprites;
	private Rectangle viewport;

	public Camera(PlayState playState) {
		this.playState = playState;
		viewport = new Rectangle();
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		/*
		if (playState.getLevel() != null) {
			int c_x = (int) playState.getPlayer().getMovement().getLocation().getX() +
					(playState.getPlayer().getRotation().getRotatedBounds().getBounds().width / 2);
			int c_y = (int) playState.getPlayer().getMovement().getLocation().getY() +
					(playState.getPlayer().getRotation().getRotatedBounds().getBounds().height / 2);
			int x = Math.max(0, c_x - canvas.getWidth() / 2);
			int y = Math.max(0, c_y - canvas.getHeight() / 2);
			if (x + canvas.getWidth() > playState.getLevel().getWidth()) {
				x -= x + canvas.getWidth() - playState.getLevel().getWidth();
			}
			if (y + canvas.getHeight() > playState.getLevel().getHeight()) {
				y -= y + canvas.getHeight() - playState.getLevel().getHeight();
			}

			//TODO: Max bounds

			viewport.setBounds(x, y, canvas.getWidth(), canvas.getHeight());
			Sprite[] newSprites = playState.getLevel().getSpritesInBounds(viewport);
			if (sprites != null) {
				for (Sprite s : sprites) {
					s.getRenderer().setRendered(false);
				}
			}
			for (Sprite s : newSprites) {
				s.getRenderer().setRendered(true);
			}
			sprites = newSprites;
		}
		*/
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		if (sprites != null) {
			graphics.translate(-viewport.x, -viewport.y);
			for (Sprite sprite : sprites) {
				sprite.getRenderer().paint(canvas, graphics, delta);
			}
			graphics.translate(viewport.x, viewport.y);
		}
	}
}

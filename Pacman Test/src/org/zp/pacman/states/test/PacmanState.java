package org.zp.pacman.states.test;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;
import org.zp.pacman.resources.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 7/14/2014
 * Time: 12:46 AM
 */
public class PacmanState extends GImmutableState implements GTickListener, GRenderListener {
	int x = 0;
	int y = 0;
	int iteration = 0;
	boolean mouthOpen = false;
	BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

	public PacmanState(GCanvas canvas) {
		super(canvas);
		setTickListeners(new GTickListener[]{this});
		setRenderListeners(new GRenderListener[]{this});
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		image = !mouthOpen ?
				Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_1") :
				Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_2");
		x += Math.round(100 * delta / 1000000000F);
		if (iteration % 4 == 0)
			mouthOpen = !mouthOpen;
		if (x + image.getWidth() + 10 >= canvas.getWidth()) {
			x = 0;
			y += image.getHeight() + 10;
		}
		if (y + image.getHeight() + 10 >= canvas.getHeight()) {
			x = 0;
			y = 0;
		}
		iteration++;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphics.drawImage(image, x, y, image.getWidth() + 10, image.getHeight() + 10, null);
	}
}

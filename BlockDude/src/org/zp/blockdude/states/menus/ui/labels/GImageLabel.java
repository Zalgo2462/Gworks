package org.zp.blockdude.states.menus.ui.labels;

import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 8/2/2014
 * Time: 1:31 PM
 */
public class GImageLabel extends GLabel {
	BufferedImage image;

	public GImageLabel(BufferedImage image) {
		this.image = image;
		updateButtonBounds();
	}

	@Override
	protected void paintContents(GCanvas canvas, Graphics graphics, long delta) {
		graphics.drawImage(
				image,
				(labelBounds.width - image.getWidth()) / 2 + location.x,
				(labelBounds.height - image.getHeight()) / 2 + location.y,
				null
		);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	protected Rectangle getInnerBounds() {
		return new Rectangle(0, 0, image.getWidth(), image.getHeight());
	}
}

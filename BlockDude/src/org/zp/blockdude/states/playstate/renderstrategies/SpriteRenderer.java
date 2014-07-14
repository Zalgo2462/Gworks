package org.zp.blockdude.states.playstate.renderstrategies;

import org.zp.blockdude.sprites.Sprite;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SpriteRenderer implements GRenderStrategy {
	private final Sprite SPRITE;
	public SpriteRenderer(final Sprite sprite) {
		this.SPRITE = sprite;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		if(SPRITE.shouldRender()) {
			final AffineTransform trans = new AffineTransform();
			trans.rotate(SPRITE.getOrientation());
			trans.translate(SPRITE.getLocation().getX(), SPRITE.getLocation().getY());
			((Graphics2D)graphics).drawImage(SPRITE.getSprite(), trans, null);
		}
	}
}

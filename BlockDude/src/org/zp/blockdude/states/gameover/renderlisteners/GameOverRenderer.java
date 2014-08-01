package org.zp.blockdude.states.gameover.renderlisteners;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Date: 8/1/2014
 * Time: 2:23 PM
 */
public class GameOverRenderer implements GRenderListener {
	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphics.setFont(graphics.getFont().deriveFont(100F));
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D bounds = fontMetrics.getStringBounds("GAME OVER", graphics);
		int x = (int) (canvas.getWidth() / 2 - bounds.getCenterX());
		int y = (int) (canvas.getHeight() / 2 - bounds.getCenterY());
		graphics.setColor(Color.WHITE);
		graphics.drawString("GAME OVER", x, y);
	}
}

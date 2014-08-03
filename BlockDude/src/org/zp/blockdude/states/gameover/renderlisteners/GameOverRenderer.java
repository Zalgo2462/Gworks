package org.zp.blockdude.states.gameover.renderlisteners;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.states.gameover.GameOverState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Date: 8/1/2014
 * Time: 2:23 PM
 */
public class GameOverRenderer implements GRenderListener {
	private GameOverState gameOverState;

	public GameOverRenderer(GameOverState gameOverState) {
		this.gameOverState = gameOverState;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(ColorScheme.DARKER_GREEN.getColor());
		graphics.setFont(new Font("BatmanForeverOutline", Font.BOLD, 50));
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D bounds1 = fontMetrics.getStringBounds("GAME OVER", graphics);
		Rectangle2D bounds2 = fontMetrics.getStringBounds("FINAL SCORE: " + gameOverState.getScore(), graphics);
		Rectangle2D bounds3 = new Rectangle2D.Double(
				Math.min(bounds1.getX(), bounds2.getX()),
				Math.min(bounds1.getY(), bounds2.getY()),
				Math.max(bounds1.getWidth(), bounds2.getWidth()),
				bounds1.getHeight() + bounds2.getHeight()
		);
		int x = (int) (canvas.getWidth() / 2 - bounds1.getCenterX());
		int y = (int) (canvas.getHeight() / 2 - bounds3.getCenterY());
		graphics.drawString("GAME OVER", x, y);
		x = (int) (canvas.getWidth() / 2 - bounds2.getCenterX());
		y += bounds2.getHeight();
		graphics.drawString("FINAL SCORE: " + gameOverState.getScore(), x, y);
	}
}

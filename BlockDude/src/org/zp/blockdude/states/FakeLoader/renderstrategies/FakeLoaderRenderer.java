package org.zp.blockdude.states.fakeloader.renderstrategies;

import org.zp.blockdude.states.fakeloader.FakeLoaderState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FakeLoaderRenderer implements GRenderStrategy{
	private final FakeLoaderState state;
	public FakeLoaderRenderer(final FakeLoaderState state) {
		this.state = state;
	}

	@Override
	public void paint(final GCanvas canvas, final Graphics graphics) {
		FontMetrics fm = graphics.getFontMetrics();
		String loading = "Loading Game";
		Rectangle2D stringBounds = fm.getStringBounds(loading, graphics);
		int stringX = canvas.getWidth()/2 - (int)stringBounds.getWidth()/2;
		int stringY = canvas.getHeight()/2 - (int)stringBounds.getHeight()/2;
		graphics.drawString("Loading Game", stringX, stringY);
		Rectangle loadingArea = new Rectangle(stringX, stringY + (int)stringBounds.getHeight(),
				(int)stringBounds.getWidth(), (int)stringBounds.getHeight());
		graphics.drawRect(loadingArea.x, loadingArea.y, loadingArea.width, loadingArea.height);
		graphics.fillRect(loadingArea.x, loadingArea.y, (int)(loadingArea.width * (state.getProgress())), loadingArea.height);
	}
}

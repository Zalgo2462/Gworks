package org.zp.gtest.rendertests.ImageViewer;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;

public class ImageViewerRenderer implements GRenderStrategy {
	private final ImageViewerState state;

	public ImageViewerRenderer(final ImageViewerState state) {
		this.state = state;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.drawImage(state.getImage(), state.getX(), state.getY(), null);
	}
}

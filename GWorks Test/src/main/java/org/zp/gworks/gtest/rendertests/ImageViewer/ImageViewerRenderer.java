package org.zp.gworks.gtest.rendertests.ImageViewer;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

public class ImageViewerRenderer implements GRenderListener {
	private final ImageViewerState state;

	public ImageViewerRenderer(final ImageViewerState state) {
		this.state = state;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.drawImage(state.getImage(), state.getX(), state.getY(), null);
	}
}

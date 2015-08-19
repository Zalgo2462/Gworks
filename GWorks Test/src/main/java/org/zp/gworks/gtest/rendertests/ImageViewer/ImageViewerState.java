package org.zp.gworks.gtest.rendertests.ImageViewer;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

import java.awt.image.BufferedImage;

/**
 * Date: 7/13/2014
 * Time: 5:04 PM
 */
public class ImageViewerState extends GImmutableState {
	private final BufferedImage image;
	private int x, y = 0;

	public ImageViewerState(final GCanvas canvas, final BufferedImage image) {
		super(canvas);
		this.image = image;
		setTickListeners(new GTickListener[]{new ImageViewerController(this)});
		setRenderListeners(new GRenderListener[]{new ImageViewerRenderer(this)});
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(final int y) {
		this.y = y;
	}
}

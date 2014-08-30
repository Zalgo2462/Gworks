package org.zp.gworks.gui.menus.ui.labels;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 8/1/2014
 * Time: 8:00 PM
 */
public abstract class GLabel implements GRenderListener {
	protected Color bgColor;
	protected Color fgColor;
	protected boolean outlined;
	protected Point location;
	protected int horizontalMargin;
	protected int verticalMargin;
	protected Rectangle labelBounds;

	protected GLabel() {
		this.fgColor = Color.BLACK;
		this.bgColor = Color.WHITE;
		this.outlined = false;
		this.location = new Point(0, 0);
		this.horizontalMargin = 0;
		this.verticalMargin = 0;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(bgColor);
		graphics.fillRect(location.x, location.y, labelBounds.width, labelBounds.height);
		graphics.setColor(fgColor);
		paintContents(canvas, graphics, delta);
		if (outlined) {
			graphics.setColor(fgColor);
			graphics.drawRect(location.x, location.y, labelBounds.width, labelBounds.height);
		}
	}

	protected abstract void paintContents(GCanvas canvas, Graphics graphics, long delta);

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getFgColor() {
		return fgColor;
	}

	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}

	public boolean isOutlined() {
		return outlined;
	}

	public void setOutlined(boolean outlined) {
		this.outlined = outlined;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
		updateButtonBounds();
	}

	public int getHorizontalMargin() {
		return horizontalMargin;
	}

	public void setHorizontalMargin(int horizMargin) {
		this.horizontalMargin = horizMargin;
		updateButtonBounds();
	}

	public int getVerticalMargin() {
		return verticalMargin;
	}

	public void setVerticalMargin(int vertMargin) {
		this.verticalMargin = vertMargin;
		updateButtonBounds();
	}

	public Rectangle getBounds() {
		return labelBounds;
	}

	protected final void updateButtonBounds() {
		Rectangle innerBounds = new Rectangle(getInnerBounds());
		innerBounds.grow(getHorizontalMargin(), getVerticalMargin());
		innerBounds.setLocation(getLocation());
		labelBounds = innerBounds;
	}

	protected abstract Rectangle getInnerBounds();
}

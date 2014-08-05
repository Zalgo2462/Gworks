package org.zp.blockdude.states.menus.ui.buttons;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Date: 8/1/2014
 * Time: 8:00 PM
 */
public abstract class GButton implements GTickListener, GRenderListener {
	protected Color bgColor;
	protected Color fgColor;
	protected boolean outlined;
	protected Point location;
	protected int horizontalMargin;
	protected int verticalMargin;
	protected Rectangle buttonBounds;
	protected boolean pressed;
	protected ConcurrentLinkedQueue<MouseEvent> mouseClicks;
	protected LinkedList<Runnable> runnables;

	protected GButton() {
		this.fgColor = Color.BLACK;
		this.bgColor = Color.WHITE;
		this.outlined = true;
		this.location = new Point(0, 0);
		this.horizontalMargin = 0;
		this.verticalMargin = 0;
		pressed = false;
		mouseClicks = new ConcurrentLinkedQueue<MouseEvent>();
		runnables = new LinkedList<Runnable>();
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(bgColor);
		graphics.fillRect(location.x, location.y, buttonBounds.width, buttonBounds.height);
		graphics.setColor(fgColor);
		paintContents(canvas, graphics, delta);
		if (outlined) {
			graphics.setColor(fgColor);
			graphics.drawRect(location.x, location.y, buttonBounds.width, buttonBounds.height);
		}
		if (pressed) {
			graphics.setColor(new Color(0, 0, 0, 32));
			graphics.fillRect(location.x, location.y, buttonBounds.width, buttonBounds.height);
		}
	}

	protected abstract void paintContents(GCanvas canvas, Graphics graphics, long delta);

	@Override
	public void tick(GCanvas canvas, long delta) {
		MouseEvent event = mouseClicks.poll();
		if (event != null) {
			for (Runnable r : runnables) {
				r.run();
			}
		}
	}

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
		return buttonBounds;
	}

	protected final void updateButtonBounds() {
		Rectangle innerBounds = new Rectangle(getInnerBounds());
		innerBounds.grow(getHorizontalMargin(), getVerticalMargin());
		innerBounds.setLocation(getLocation());
		buttonBounds = innerBounds;
	}

	protected abstract Rectangle getInnerBounds();

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public void buttonClicked(MouseEvent e) {
		mouseClicks.offer(e);
	}

	public void addRunnable(Runnable r) {
		runnables.offer(r);
	}
}

package org.zp.blockdude.states.menus.ui;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Date: 8/1/2014
 * Time: 8:00 PM
 */
public class GButton implements GTickListener, GRenderListener {
	private Color bgColor;
	private Color fgColor;
	private Point location;
	private int margin;
	private Rectangle textBounds;
	private Rectangle buttonBounds;
	private Font font;
	private String text;
	private boolean pressed;
	private ConcurrentLinkedQueue<MouseEvent> mouseClicks;
	private LinkedList<Runnable> runnables;

	public GButton(String text) {
		this(text, 20);
	}

	public GButton(String text, int margin) {
		this(text, new Font("Arial", Font.PLAIN, 18), margin);
	}

	public GButton(String text, Font font, int margin) {
		this(Color.WHITE, Color.BLACK, text, font, margin);
	}

	public GButton(Color fgColor, Color bgColor, String text, int margin) {
		this(fgColor, bgColor, text, new Font("Arial", Font.PLAIN, 18), margin);
	}

	public GButton(Color fgColor, Color bgColor, String text, Font font, int margin) {
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.text = text;
		this.font = font;
		this.location = new Point(0, 0);
		this.margin = margin;
		pressed = false;
		mouseClicks = new ConcurrentLinkedQueue<MouseEvent>();
		runnables = new LinkedList<Runnable>();
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setFont(font);
		updateBounds(((Graphics2D) graphics).getFontRenderContext());
		graphics.setColor(
				pressed ? bgColor.darker().darker() : bgColor
		);
		graphics.fillRect(location.x, location.y, buttonBounds.width, buttonBounds.height);
		graphics.setColor(
				pressed ? fgColor.darker().darker() : fgColor
		);
		graphics.drawString(
				text,
				(int) (buttonBounds.getCenterX() - textBounds.getCenterX()),
				(int) (buttonBounds.getCenterY() - textBounds.getCenterY())
		);
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		MouseEvent event = mouseClicks.poll();
		if (event != null) {
			for (Runnable r : runnables) {
				r.run();
			}
		}
	}

	private void updateBounds() {
		FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
		updateBounds(frc);
	}

	private void updateBounds(FontRenderContext frc) {
		TextLayout textLayout = new TextLayout(text, font, frc);
		Rectangle stringBounds = textLayout.getBounds().getBounds();
		textBounds = new Rectangle(stringBounds);
		stringBounds.grow(margin, margin);
		stringBounds.setLocation(location);
		buttonBounds = stringBounds;
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

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
		updateBounds();
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
		updateBounds();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		updateBounds();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		updateBounds();
	}

	public Rectangle getBounds() {
		return buttonBounds;
	}

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

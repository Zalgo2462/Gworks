package org.zp.gworks.gui.menus;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.gui.menus.ui.buttons.GButton;
import org.zp.gworks.gui.menus.ui.labels.GLabel;
import org.zp.gworks.logic.GState.GMutableState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

/**
 * Date: 8/1/2014
 * Time: 7:56 PM
 */
public class GMenuState extends GMutableState {
	private Color bgColor;
	private Rectangle bounds;
	private LinkedList<GButton> buttons;
	private LinkedList<GLabel> labels;
	private GMenuMouseListener mouseListener;

	public GMenuState(GCanvas canvas, Color bgColor, int x, int y, int width, int height) {
		super(canvas);
		this.bgColor = bgColor;
		this.bounds = new Rectangle(x, y, width, height);
		this.buttons = new LinkedList<GButton>();
		this.labels = new LinkedList<GLabel>();
		this.mouseListener = new GMenuMouseListener();
		addRenderListener(new GMenuRenderer());
	}

	public void addGLabel(GLabel label) {
		labels.offer(label);
		addRenderListener(label);
	}

	public boolean removeGLabel(GLabel label) {
		boolean toReturn = labels.remove(label);
		return removeRenderListener(label) && toReturn;
	}

	public void addGButton(GButton button) {
		buttons.offer(button);
		addRenderListener(button);
		addTickListener(button);
	}

	public boolean removeGButton(GButton button) {
		boolean toReturn = buttons.remove(button);
		toReturn = removeRenderListener(button) && toReturn;
		toReturn = removeTickListener(button) && toReturn;
		return toReturn;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void onAddState() {
		canvas.addMouseListener(mouseListener);
	}

	@Override
	public void onRemoveState() {
		canvas.removeMouseListener(mouseListener);
	}

	private class GMenuMouseListener implements MouseListener {

		private GButton currentlyPressed;

		@Override
		public void mouseClicked(MouseEvent e) {
			if (bounds.contains(e.getPoint())) {
				for (GButton button : buttons) {
					if (button.getBounds().contains(e.getPoint())) {
						button.buttonClicked(e);
						return;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (bounds.contains(e.getPoint())) {
				for (GButton button : buttons) {
					if (button.getBounds().contains(e.getPoint())) {
						button.setPressed(true);
						currentlyPressed = button;
						return;
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (bounds.contains(e.getPoint())) {
				for (GButton button : buttons) {
					if (button.getBounds().contains(e.getPoint()) && button.isPressed()) {
						button.setPressed(false);
						currentlyPressed = null;
						return;
					}
				}
			}
			if (currentlyPressed != null) {
				currentlyPressed.setPressed(false);
				currentlyPressed = null;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	private class GMenuRenderer implements GRenderListener {

		@Override
		public void paint(GCanvas canvas, Graphics graphics, long delta) {
			graphics.setColor(bgColor);
			graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
}

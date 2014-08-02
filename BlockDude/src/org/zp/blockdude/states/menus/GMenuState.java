package org.zp.blockdude.states.menus;

import org.zp.blockdude.states.menus.ui.GButton;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
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
	private GMenuMouseListener mouseListener;

	public GMenuState(GCanvas canvas, Color bgColor, int x, int y, int width, int height) {
		super(canvas);
		this.bgColor = bgColor;
		this.bounds = new Rectangle(x, y, width, height);
		this.buttons = new LinkedList<GButton>();
		this.mouseListener = new GMenuMouseListener();
		addGRenderListener(new GMenuRenderer());
	}

	public void addGButton(GButton button) {
		buttons.offer(button);
		addGRenderListener(button);
		addGTickListener(button);
	}

	public boolean removeGButton(GButton button) {
		boolean toReturn = buttons.remove(button);
		toReturn = removeGRenderListener(button) && toReturn;
		toReturn = removeGTickListener(button) && toReturn;
		return toReturn;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void onAddGState() {
		canvas.addMouseListener(mouseListener);
	}

	@Override
	public void onRemoveGState() {
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

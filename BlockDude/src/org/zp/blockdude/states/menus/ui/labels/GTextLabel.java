package org.zp.blockdude.states.menus.ui.labels;

import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.font.FontRenderContext;

/**
 * Date: 8/2/2014
 * Time: 1:31 PM
 */
public class GTextLabel extends GLabel {
	private Font font;
	private String text;

	private Rectangle textBounds;

	public GTextLabel(String text) {
		super();
		this.font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
		this.text = text;
		updateTextBounds();
		updateButtonBounds();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		updateTextBounds();
		updateButtonBounds();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		updateTextBounds();
		updateButtonBounds();
	}

	protected void paintContents(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setFont(font);
		updateTextBounds(((Graphics2D) graphics).getFontRenderContext());

		graphics.drawString(
				text,
				(labelBounds.width - textBounds.width) / 2 + location.x + graphics.getFontMetrics().getLeading(),
				(labelBounds.height - textBounds.height) / 2 + location.y + graphics.getFontMetrics().getAscent()
		);
	}

	private void updateTextBounds() {
		FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
		updateTextBounds(frc);
	}

	private void updateTextBounds(FontRenderContext frc) {
		textBounds = font.getStringBounds(text, frc).getBounds();
	}

	protected Rectangle getInnerBounds() {
		return textBounds;
	}
}

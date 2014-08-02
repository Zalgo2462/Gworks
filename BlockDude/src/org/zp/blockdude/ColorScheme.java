package org.zp.blockdude;

import java.awt.*;

/**
 * Date: 8/2/2014
 * Time: 4:15 PM
 */
public enum ColorScheme {
	BUTTON_BACKGROUND(Color.decode("#30332E")),
	MENU_BACKGROUND(Color.decode("#1D1F1C")),
	DARKER_GREEN(Color.decode("#00B22A")),
	LIGHTER_GREEN(Color.decode("#19FF4F")),
	GREEN(Color.decode("#00FF3B")),
	DARKER_RED(Color.decode("#B20010")),
	LIGHTER_RED(Color.decode("#FF0017"));

	private final Color color;

	ColorScheme(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}

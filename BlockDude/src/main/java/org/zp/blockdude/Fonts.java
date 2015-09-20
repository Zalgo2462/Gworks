package org.zp.blockdude;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Logan on 8/4/2014.
 */
public enum Fonts {
	BFO(Font.TRUETYPE_FONT, "/fonts/batmfo.ttf");

	Font font;

	Fonts(int fontType, String url) {
		try {
			this.font = Font.createFont(fontType, getClass().getResourceAsStream(url));
		} catch (FontFormatException e) {
			e.printStackTrace();
			this.font = Font.getFont(Font.SANS_SERIF);
		} catch (IOException e) {
			e.printStackTrace();
			this.font = Font.getFont(Font.SANS_SERIF);
		}
	}

	public Font getFont() {
		return font;
	}
}

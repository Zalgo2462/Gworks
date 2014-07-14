package org.zp.gtest.rendertests.ColorChanger;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;
import java.util.Random;

public class ColorChangerRenderer implements GRenderStrategy {
	private Color now = Color.BLACK;
	private Color next = Color.WHITE;
	private Random random = new Random();

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		if(!now.equals(next)) {
			int r1 = now.getRed();
			int g1 = now.getGreen();
			int b1 = now.getBlue();
			int rTest = r1;
			int gTest = g1;
			int bTest = b1;
			int r2 = next.getRed();
			int g2 = next.getGreen();
			int b2 = next.getBlue();

			r1 += (r2 - r1) / 10;
			g1 += (g2 - g1) / 10;
			b1 += (b2 - b1) / 10;

			if(r1 == rTest && g1 == gTest && b1 == bTest) {
				r1 = r2;
				g1 = g2;
				b1 = b2;
			}

			now = new Color(r1, g1, b1);
		} else {
			next = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		}

		graphics.setColor(now);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
}

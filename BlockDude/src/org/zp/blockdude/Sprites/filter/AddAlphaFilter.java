package org.zp.blockdude.sprites.filter;

import java.awt.*;
import java.awt.image.VolatileImage;

/**
 * Date: 8/23/2014
 * Time: 5:38 PM
 */
public class AddAlphaFilter extends Filter {
	@Override
	protected Image filter(Image inputImage) {
		VolatileImage outputImage = createVolatileImage(inputImage.getWidth(null), inputImage.getHeight(null));
		Graphics2D g = outputImage.createGraphics();
		g.setColor(new Color(0F, 0F, 0F, 0F));
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
		g.setComposite(AlphaComposite.SrcOver);
		g.drawImage(inputImage, 0, 0, null);
		g.dispose();
		return outputImage;
	}
}

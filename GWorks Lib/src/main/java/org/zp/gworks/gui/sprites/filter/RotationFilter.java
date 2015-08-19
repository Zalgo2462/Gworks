package org.zp.gworks.gui.sprites.filter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;

/**
 * Date: 8/29/2014
 * Time: 4:13 PM
 */
public class RotationFilter extends Filter {
	private double orientation = 0D;

	@Override
	protected Image filter(Image inputImage) {
		AffineTransform transform = AffineTransform.getRotateInstance(
				orientation, inputImage.getWidth(null) / 2, inputImage.getHeight(null) / 2
		);

		Rectangle r;
		r = transform.createTransformedShape(new Rectangle(inputImage.getWidth(null), inputImage.getHeight(null))).getBounds();
		int w = r.x + r.width;
		int h = r.y + r.height;

		VolatileImage rotatedSprite = createVolatileImage(w, h);
		Graphics2D g = rotatedSprite.createGraphics();
		g.setComposite(AlphaComposite.DstOut);
		g.fillRect(0, 0, rotatedSprite.getWidth(), rotatedSprite.getHeight());
		g.setComposite(AlphaComposite.SrcOver);
		g.drawImage(inputImage, transform, null);
		g.dispose();
		return rotatedSprite;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
}

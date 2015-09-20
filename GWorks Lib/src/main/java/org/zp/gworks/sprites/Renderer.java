package org.zp.gworks.sprites;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.sprites.animations.Animation;
import org.zp.gworks.sprites.filter.AddAlphaFilter;
import org.zp.gworks.sprites.filter.Filter;
import org.zp.gworks.sprites.filter.SquaringFilter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.LinkedList;

/**
 * Date: 9/19/2015
 * Time: 5:15 PM
 */
public class Renderer implements GRenderListener {
	private Sprite spriteObject;
	private boolean rendered;
	private float opacity;
	private BufferedImage spriteBacker;
	private VolatileImage sprite;
	private Animation animation;
	private AddAlphaFilter addAlphaFilter;
	private SquaringFilter squaringFilter;
	private LinkedList<Filter> filters;

	Renderer(Sprite spriteObject) {
		this.spriteObject = spriteObject;
		this.opacity = 1F;
		this.filters = new LinkedList<Filter>();
		this.squaringFilter = new SquaringFilter();
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		if (animation != null && animation.isRunning()) {
			BufferedImage image = animation.getSprite(delta);
			if (!image.equals(spriteBacker)) {
				setSprite(image);
			}
			//TODO: move this into ticklistener
			Shape collArea = animation.getBounds(delta);
			if (!collArea.equals(spriteObject.getMovement().getCollisionArea())) {
				spriteObject.getMovement().setCollisionArea(collArea);
			}
		}

		if (sprite.contentsLost()) {
			makeTranslucentSprite();
		}

		VolatileImage filteredImage = sprite;

		for (int i = 0; i < filters.size(); i++) {
			if (filteredImage.contentsLost()) {
				filteredImage = sprite;
				i = 0;
				continue;
			}
			Filter filter = filters.get(i);
			filteredImage = filter.getFilteredImage(filteredImage);
		}


		if (spriteObject.getRotation().getCurrentAngle() != 0 || spriteObject.getRotation().isMoving()) {
			filteredImage = squaringFilter.getFilteredImage(filteredImage);
			filteredImage = spriteObject.getRotation().getRotatedFilter().getFilteredImage(filteredImage);
		}

		if (rendered) {
			((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			graphics.drawImage(
					filteredImage,
					Math.round(Math.round(spriteObject.getMovement().getLocation().getX() - squaringFilter.getxOffset())),
					Math.round(Math.round(spriteObject.getMovement().getLocation().getY() - squaringFilter.getyOffset())),
					null
			);
		}
	}

	public BufferedImage getSprite() {
		return spriteBacker;
	}

	public void setSprite(BufferedImage sprite) {
		this.spriteBacker = sprite;
		this.addAlphaFilter = new AddAlphaFilter();
		makeTranslucentSprite();
	}

	private void makeTranslucentSprite() {
		this.sprite = addAlphaFilter.getFilteredImage(spriteBacker);
	}

	public Rectangle2D.Double getBounds() {
		if (getSprite() == null) {
			return null;
		}
		return new Rectangle2D.Double(spriteObject.getMovement().getLocation().getX(), spriteObject.getMovement().getLocation().getY(),
				sprite.getWidth(), sprite.getHeight());
	}

	public boolean shouldRender() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public boolean removeFilter(Filter filter) {
		return filters.remove(filter);
	}

	public boolean addFilter(Filter filter) {
		return filters.offer(filter);
	}
}

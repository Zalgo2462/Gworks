package org.zp.platformers.morning.sprites.collisions;

import org.junit.Before;
import org.junit.Test;
import org.zp.gworks.sprites.Sprite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 8/30/2015
 * Time: 11:48 AM
 */
public class DynamicSpriteListTest {
	private final static double EPSILON = .000001;
	private DynamicSpriteList spriteList;
	private TestSprite a, b, c, d, e;

	@Before
	public void setUp() {
		this.spriteList = new DynamicSpriteList();
		this.a = new TestSprite(0, 0, 50, 50);
		this.b = new TestSprite(48, 48, 50, 50);
		this.c = new TestSprite(200, 200, 100, 100);
		this.d = new TestSprite(250, 250, 25, 25);
		this.e = new TestSprite(0, 0, 500, 500);
	}

	@Test
	public void testCornerCollision() {
		spriteList.addSprite(a);
		Sprite collision = spriteList.getFirstCollision(b);
		assertNotNull(collision);
		assertEquals("Collision x var is testSpriteA",
				collision.getMovement().getLocation().getX(),
				a.getMovement().getLocation().getX(), EPSILON);
		assertEquals("Collision y var is testSpriteA",
				collision.getMovement().getLocation().getY(),
				a.getMovement().getLocation().getY(), EPSILON);
		assertEquals("Collision is testSpriteA", collision, a);
		spriteList.removeSprite(a);
	}

	@Test
	public void testWholeCollision() {
		spriteList.addSprite(c);
		Sprite collision = spriteList.getFirstCollision(d);
		assertEquals("Collision x var is testSpriteC",
				collision.getMovement().getLocation().getX(),
				c.getMovement().getLocation().getX(), EPSILON);
		assertEquals("Collision y var is testSpriteC",
				collision.getMovement().getLocation().getY(),
				c.getMovement().getLocation().getY(), EPSILON);
		assertEquals("Collision is testSpriteC", collision, c);
		spriteList.removeSprite(c);
	}

	@Test
	public void testMultipleCollisions() {
		spriteList.addSprite(a);
		spriteList.addSprite(b);
		spriteList.addSprite(c);
		spriteList.addSprite(d);
		Sprite[] collisions = spriteList.getAllCollisions(e);
		assertEquals("Sprite E collides with all others", collisions.length, 4);
		spriteList.removeSprite(a);
		spriteList.removeSprite(b);
		spriteList.removeSprite(c);
		spriteList.removeSprite(d);
	}
}

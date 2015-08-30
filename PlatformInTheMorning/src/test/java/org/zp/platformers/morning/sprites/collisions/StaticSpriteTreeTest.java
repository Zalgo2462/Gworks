package org.zp.platformers.morning.sprites.collisions;

import org.junit.Before;
import org.junit.Test;
import org.zp.gworks.gui.sprites.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Date: 8/30/2015
 * Time: 12:24 PM
 */
public class StaticSpriteTreeTest {
	private final static double EPSILON = .000001;
	private final static int NUM_SPRITES = 100;
	private StaticSpriteTree spriteTree;
	private ArrayList<Sprite> sprites;

	@Before
	public void setUp() {
		this.spriteTree = new StaticSpriteTree();
		sprites = new ArrayList<Sprite>(100);
		Random r = new Random(1);
		for (int i = 0; i < NUM_SPRITES; i++) {
			Sprite s = new TestSprite(i * 100, i * 100, 99, 99);  //never intersect
			sprites.add(s);
		}
		//Needed for efficiency reasons
		ArrayList<Sprite> shuffled = new ArrayList<Sprite>(sprites);
		Collections.shuffle(shuffled, r);
		for (int i = 0; i < shuffled.size(); i++) {
			Sprite s = shuffled.get(i);
			spriteTree.addSprite(s);
		}
	}


	@Test
	public void testCornerCollision() {
		System.out.println("test");
		Sprite s = new TestSprite(98, 0, 10, 10);
		Sprite[] coll = spriteTree.getAllCollisions(s);
		assertEquals("Corner collision returned one sprite", coll.length, 1);
		assertEquals("Collided with first sprite", sprites.get(0), coll[0]);
	}

	@Test
	public void testWholeCollision() {
		Sprite s = new TestSprite(150, 150, 25, 25);
		Sprite collision = spriteTree.getFirstCollision(s);
		assertEquals("Collision x var is second sprite",
				collision.getMovement().getLocation().getX(),
				sprites.get(1).getMovement().getLocation().getX(), EPSILON);
		assertEquals("Collision y var is second sprite",
				collision.getMovement().getLocation().getY(),
				sprites.get(1).getMovement().getLocation().getY(), EPSILON);
		assertEquals("Collision is second sprite", collision, sprites.get(1));
	}

	@Test
	public void testMultipleCollisions() {
		Sprite testSprite = new TestSprite(0, 0, NUM_SPRITES * 100, NUM_SPRITES * 100);
		Sprite[] collisions = spriteTree.getAllCollisions(testSprite);
		assertEquals("Test Sprite collides with all others", collisions.length, NUM_SPRITES);
	}

}

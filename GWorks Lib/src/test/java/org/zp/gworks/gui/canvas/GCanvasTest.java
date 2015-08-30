package org.zp.gworks.gui.canvas;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zp.gworks.logic.GState.GImmutableState;

import java.awt.*;

import static org.junit.Assert.*;

public class GCanvasTest {
	private Frame frame;
	private GCanvas canvas;

	@Before
	public void setUp() throws Exception {
		frame = new Frame("test");
		canvas = new GCanvas(new Dimension(800, 600), 60, 2);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		canvas.requestFocus();
	}

	@Test
	public void testDimensions() throws Exception {
		Dimension testDim = new Dimension(800, 600);
		assertTrue(canvas.getMinimumSize().equals(testDim));
		assertTrue(canvas.getPreferredSize().equals(testDim));
		assertTrue(canvas.getMaximumSize().equals(testDim));
	}

	@Test
	public void testFramerate() throws Exception {
		assertTrue(canvas.FPS == 60);
		assertTrue(canvas.FRAME_DELAY == Math.round(1000000000.0F / (float) 60));
	}


	@Test
	public void testGetLoop() throws Exception {
		assertNotNull(canvas.getLoop());
	}

	@Test
	public void testGetGameThread() throws Exception {
		assertNotNull(canvas.getGameThread());
	}

	@Test
	public void testAddGState() throws Exception {
		GImmutableState state = new GImmutableState(canvas) {
		};
		canvas.addState(state);
		assertFalse(canvas.getStates().isEmpty());
		canvas.removeState(state);
	}

	@Test
	public void testRemoveGState() throws Exception {
		GImmutableState state = new GImmutableState(canvas) {
		};
		canvas.addState(state);
		canvas.removeState(state);
		assertTrue(canvas.getStates().isEmpty());
	}

	@Test
	public void testGetGStates() throws Exception {
		assertNotNull(canvas.getStates());
	}

	@Test
	public void testGetRenderer() throws Exception {
		assertNotNull(canvas.getRenderer());
	}

	//TODO: Add robot
	@Test
	public void testGetGKeyListener() throws Exception {
		assertNotNull(canvas.getGKeyListener());
	}

	//TODO: Add robot
	@Test
	public void testGetGMouseListener() throws Exception {
		assertNotNull(canvas.getGMouseListener());
	}

	@After
	public void tearDown() throws Exception {
		canvas.dispose();
		frame.dispose();
	}
}
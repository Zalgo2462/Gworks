import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.platformers.morning.levels.LevelOne;
import org.zp.platformers.morning.states.PlayState;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	public static final Dimension DIMENSION = new Dimension(800, 600);
	private static GCanvas canvas;
	private static GameFrame gameFrame;
	private static PlayState playState;

	public GameFrame() {
		setResizable(false);
		init();
		pack();
		setTitle("Platform In The Morning");
		setVisible(true);
		requestFocus();
		canvas.requestFocus();
	}

	public static void main(String[] args) {
		gameFrame = new GameFrame();
	}

	public static void exit() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				gameFrame.dispose();
			}
		});
	}

	public void init() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		canvas = new GCanvas(DIMENSION, 60, 2);
		playState = new PlayState(canvas);
		playState.setLevel(new LevelOne(playState));
		canvas.addState(playState);
		add(canvas);
	}

	public void dispose() {
		if (EventQueue.isDispatchThread()) {
			canvas.dispose();
			super.dispose();
		}
	}
}

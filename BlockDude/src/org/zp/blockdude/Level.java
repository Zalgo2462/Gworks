package org.zp.blockdude;

public enum Level {
	ONE(1, 25, 250, 1, 1, 5),
	TWO(2, 25, 250, 1, 1, 5),
	THREE(2, 25, 250, 1, 1, 4),
	FOUR(3, 25, 250, 1, 1, 4),
	FIVE(5, 25, 250, 1, 1, 4),
	SIX(5, 20, 300, 1, 1, 4),
	SEVEN(7, 20, 250, 1, 1, 3),
	EIGHT(8, 15, 250, 1, 1, 3),
	NINE(9, 10, 250, 1, 1, 2),
	TEN(10, 10, 250, 1, 1, 0);
	private final int enemies;
	private final int enemySize;
	private final int enemySpeed;
	private final double playerAttraction;
	private final double enemyRepulsion;
	private final int randomness;


	private Level(final int enemies, final int enemySize, final int enemySpeed,
	              final double playerAttraction, final double enemyRepulsion, final int randomness) {
		this.enemies = enemies;
		this.enemySize = enemySize;
		this.enemySpeed = enemySpeed;
		this.playerAttraction = playerAttraction;
		this.enemyRepulsion = enemyRepulsion;
		this.randomness = randomness;
	}

	public int getEnemies() {
		return enemies;
	}

	public int getEnemySize() {
		return enemySize;
	}

	public int getEnemySpeed() {
		return enemySpeed;
	}

	public double getPlayerAttraction() {
		return playerAttraction;
	}

	public double getEnemyRepulsion() {
		return enemyRepulsion;
	}

	public int getRandomness() {
		return randomness;
	}
}

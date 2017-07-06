import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void canRoll() {
        game.roll(0);
    }

    @Test
    public void gutterGame() {
        rollMany(20, 0);
        assertThat(game.score(), is(0));
    }

    @Test
    public void allOnes() {
        rollMany(20, 1);
        assertThat(game.score(), is(20));
    }

    @Test
    public void oneSpare() {
        rollSpare();
        game.roll(3);
        rollMany(17, 0);
        assertThat(game.score(), is(16));
    }

    private void rollSpare() {
        game.roll(5);
        game.roll(5);
    }

    private void rollMany(int rolls, int pins) {
        for (int i = 0; i < rolls; i++) {
            game.roll(pins);
        }
    }

    private class Game {
        public static final int FRAME_SIZE = 10;
        private int[] rolls = new int[21];
        private int currentRoll = 0;

        public void roll(int pins) {
            rolls[currentRoll++] = pins;
        }

        public Integer score() {
            int score = 0;

            int firstTry = 0;
            for (int frame = 0; frame < FRAME_SIZE; frame++) {
                if (isSpare(firstTry)) {
                    score += 10 + rolls[firstTry + 2];
                    firstTry += 2;
                } else {
                    score += rolls[firstTry] + rolls[firstTry + 1];
                    firstTry += 2;
                }
            }

            return score;
        }

        private boolean isSpare(int firstTry) {
            return rolls[firstTry] + rolls[firstTry + 1] == 10;
        }
    }
}

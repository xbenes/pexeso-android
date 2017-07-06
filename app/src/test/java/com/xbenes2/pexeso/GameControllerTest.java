package com.xbenes2.pexeso;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class GameControllerTest {
    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private static final int UNKNOWN = -10;
    private static final int OVER = -11;
    private static final int[] identifiers = new int[] { FIRST, SECOND };

    @Before
    public void setupShuffleMock() {
        // mock utils: leave concat intact while mock shuffle to leave the array as-is
        PowerMockito.mockStatic(Utils.class);
        when(Utils.concat(any(int[].class), any(int[].class))).thenCallRealMethod();
        when(Utils.shuffle(any(int[].class))).then(returnsFirstArg());
    }

    @Test
    public void returns_all_images_unknown_at_the_beginning() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);


        int[] expected = new int[] { UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN };
        assertArrayEquals(gc.getItems(), expected);
    }

    @Test
    public void returns_game_status_noop_for_first_open_image() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        assertEquals(status, GameStatus.NOOP);
    }

    @Test
    public void does_nothing_if_clicked_twice_on_same_image() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(0);
        assertEquals(status, GameStatus.NOOP);
    }

    @Test
    public void returns_unfinished_go_if_two_items_match() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(2);
        assertEquals(again, GameStatus.SHOULD_FINISH_GO);
    }

    @Test
    public void returns_unfinished_go_if_two_items_not_the_same() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(1);
        assertEquals(again, GameStatus.SHOULD_FINISH_GO);
    }

    @Test
    public void should_have_two_items_visible_before_finish_go_if_match() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(2);
        int[] expected = new int[] { FIRST, UNKNOWN, FIRST, UNKNOWN };
        assertArrayEquals(gc.getItems(), expected);
    }

    @Test
    public void should_have_two_items_visible_before_finish_go_if_not_match() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(1);
        int[] expected = new int[] { FIRST, SECOND, UNKNOWN, UNKNOWN };
        assertArrayEquals(gc.getItems(), expected);
    }

    @Test
    public void should_finish_go_and_make_all_unknown_if_two_different_items_selected() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(1);
        gc.finishGo();

        int[] expected = new int[] { UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN };
        assertArrayEquals(gc.getItems(), expected);
    }

    @Test
    public void should_finish_go_and_mark_two_items_as_over_if_match() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);
        GameStatus status = gc.go(0);
        GameStatus again = gc.go(2);
        gc.finishGo();
        int[] expected = new int[] { OVER, UNKNOWN, OVER, UNKNOWN };
        assertArrayEquals(gc.getItems(), expected);
    }

    @Test
    public void should_reset_game_on_game_over() {
        GameController gc = new GameController(identifiers, UNKNOWN, OVER);

        GameStatus statusFirst = gc.go(0);
        GameStatus againFirst = gc.go(2);
        gc.finishGo();

        GameStatus statusSecond = gc.go(1);
        GameStatus againSecond = gc.go(3);
        gc.finishGo();

        int[] expected = new int[] { UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN };
        assertArrayEquals(gc.getItems(), expected);
    }
}
package com.xbenes2.pexeso;

import java.util.HashSet;
import java.util.Set;

public class GameController {
    private int[] ids;
    private int unknownId;
    private int overId;

    private int[] duplicated;
    private int[] shuffled;
    private int firstOpen = -1;
    private int secondOpen = -1;

    private boolean ready = true;
    private Set<Integer> over = new HashSet<Integer>();

    public GameController(int[] identifiers, int unknownIdentifier, int overIdentifier) {
        ids = identifiers;
        unknownId = unknownIdentifier;
        overId = overIdentifier;

        duplicated = Utils.concat(identifiers, identifiers);
        init();
    }

    public void init() {
        shuffled = Utils.shuffle(duplicated);
        over = new HashSet<Integer>();
    }

    public int[] getItems() {
        int[] result = new int[shuffled.length];

        for (int i = 0; i < shuffled.length; i++) {
            if (i == firstOpen || i == secondOpen) {
                result[i] = shuffled[i];
            } else if (over.contains(i)) {
                result[i] = overId;
            } else {
                result[i] = unknownId;
            }
        }

        return result;
    }

    public GameStatus go(int x) {
        if (nothingToDo(x)) {
            return GameStatus.NOOP;
        } else if (firstOpen == -1) {
            firstOpen = x;
            return GameStatus.NOOP;
        }

        if (itemsMatch(x, firstOpen)) {
            over.add(x);
            over.add(firstOpen);
        }

        secondOpen = x;
        ready = false;

        return GameStatus.SHOULD_FINISH_GO;
    }

    public void finishGo() {
        firstOpen = -1;
        secondOpen = -1;
        ready = true;
        if (isGameOver()) {
            init();
        }
    }

    private boolean nothingToDo(int position) {
        return over.contains(position) || !ready || firstOpen == position;
    }

    private boolean itemsMatch(int firstPosition, int secondPosition) {
        int first = shuffled[firstPosition];
        int second = shuffled[secondPosition];

        return firstPosition != secondPosition && first == second;
    }

    private boolean isGameOver() {
        return over.size() == shuffled.length;
    }
}

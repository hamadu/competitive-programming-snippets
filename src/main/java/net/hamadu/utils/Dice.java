package net.hamadu.utils;

class Dice {
    final static int MAXCODE = 777;

    static enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1);

        int dx;
        int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    static int[][][][][] diceMap = new int[7][7][7][4][3];

    static void buildDiceMap() {
        for (int i = 1 ; i <= 6 ; i++) {
            for (int j = 1 ; j <= 6 ; j++) {
                for (int k = 1 ; k <= 6 ; k++) {
                    if (i == j || j == k || i == k) {
                        continue;
                    }
                    Dice dice = new Dice(i, j, k);
                    Dice[] dices = new Dice[]{dice.right(), dice.up(), dice.down(), dice.left()};
                    for (int d = 0 ; d < 4 ; d++) {
                        Dice di = dices[d];
                        diceMap[i][j][k][d] = new int[]{di.top, di.up, di.right};
                    }
                }
            }
        }
    }

    int top;
    int up;
    int right;

    public Dice(int t, int u, int r) {
        top = t;
        up = u;
        right = r;
    }

    public String toString() {
        return String.format("%d/%d/%d", top, up, right);
    }

    public Dice move(Direction dir) {
        switch (dir) {
            case LEFT:
                return left();
            case RIGHT:
                return right();
            case UP:
                return up();
            case DOWN:
                return down();
        }
        throw new RuntimeException("invalid direction : " + dir);
    }

    private Dice left() {
        return new Dice(right, up, 7-top);
    }

    private Dice right() {
        return new Dice(7-right, up, top);
    }

    private Dice up() {
        return new Dice(7-up, top, right);
    }

    private Dice down() {
        return new Dice(up, 7-top, right);
    }

    public int encode() {
        return (top<<6)+(up<<3)+right;
    }

    public static Dice decode(int code) {
        int top = (code>>6)&7;
        int up = (code>>3)&7;
        int right = code&7;
        return new Dice(top, up, right);
    }

    public static Dice initialDice() {
        return new Dice(1, 2, 3);
    }
}
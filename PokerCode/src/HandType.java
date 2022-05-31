/*
An enum that represents all the different types of hand one can have
 */

public enum HandType {
    ROYAL_FLUSH {
        public int getHandTypeVal() {return 9; }
    },
    STRAIGHT_FLUSH {
        public int getHandTypeVal() {return 8; }
    },
    FOUR_OF_A_KIND {
        public int getHandTypeVal() {return 7; }
    },
    FULL_HOUSE {
        public int getHandTypeVal() {return 6; }
    },
    FLUSH {
        public int getHandTypeVal() {return 5; }
    },
    STRAIGHT {
        public int getHandTypeVal() {return 4; }
    },
    THREE_OF_A_KIND {
        public int getHandTypeVal() {return 3; }
    },
    TWO_PAIRS {
        public int getHandTypeVal() {return 2; }
    },
    PAIR {
        public int getHandTypeVal() {return 1; }
    },
    HIGH_CARD {
        public int getHandTypeVal() {return 0; }
    };

    public abstract int getHandTypeVal();
}

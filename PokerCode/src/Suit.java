/*
Represents the suit of each card
 */
public enum Suit {
    DIAMOND {
        public int getSuitAsInt() { return 0; }
    },
    HEART {
        public int getSuitAsInt() { return 1; }
    },
    SPADE {
        public int getSuitAsInt() { return 2; }
    },
    CLUB {
        public int getSuitAsInt() { return 3; }
    };

    public abstract int getSuitAsInt();
}
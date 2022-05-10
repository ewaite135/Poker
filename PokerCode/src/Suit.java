/*
An enum is basically like a variable but it only has a certain number of states, like how a season can only be
summer, fall, winter or spring.
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
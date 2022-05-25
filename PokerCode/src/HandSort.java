/*
I just stole this code off the internet for merge sorting, and thenm
 */

import java.util.ArrayList;
import java.util.*;

public class HandSort {
    public static void main(String[]args) {
        System.out.println("TreeMap using a TreeMap constructor");
        treeMapTest2();
    }
    private static void treeMapTest1() {
        TreeMap<Integer, String> tree_map = new TreeMap<Integer, String>();
        tree_map.put(9, "Noah");
        tree_map.put(5, "Eli");
        tree_map.put(3, "Rowan");
        tree_map.put(6, "Gavin");
        System.out.println("TreeMap: " + tree_map);
    }

    private static void treeMapTest2() {

        TreeMap<Double, Card[]> tree_map2 = new TreeMap<Double, Card[]>();
        //Card[] cardList1 = {new Card("5", Suit.SPADE), new Card("Ace", Suit.DIAMOND)};
        Card[] cardList2 = {new Card("8", Suit.HEART), new Card("Ace", Suit.CLUB)};
        tree_map2.put(2.2, cardList2);
        tree_map2.put(2.0, new Card[]{new Card("5", Suit.SPADE), new Card("Ace", Suit.DIAMOND)});
        System.out.println("TreeMap: " + tree_map2);
    }
}

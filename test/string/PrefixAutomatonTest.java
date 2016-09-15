package string;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PrefixAutomatonTest {
    final char[] ATOZ = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    @Test
    public void testAtoZ() {
        PrefixAutomaton trie = new PrefixAutomaton(new char[][]{ATOZ});

        for (int i = 1 ; i <= 26 ; i++) {
            assertThat(trie.lastCharacter[i], is(ATOZ[i-1]-'a'));
        }
        assertThat(trie.go("abcdefghijklmnopqrstuvwxyz".toCharArray()), is(26));
        assertThat(trie.go("ababcdefabcdabc".toCharArray()), is(3));
    }

    @Test
    public void testAbracatabra() {
        // abra : 1-4
        // abracatabra : 1-11
        // bra : 12-14
        PrefixAutomaton trie = new PrefixAutomaton(new char[][]{
                "abra".toCharArray(),
                "abracatabra".toCharArray(),
                "bra".toCharArray()
        });

        assertThat(trie.go("abra".toCharArray()), is(4));
        assertThat(trie.go("abraca".toCharArray()), is(6));

        assertThat(trie.go("abracabra".toCharArray()), is(4));
        assertThat(trie.go("abracabracatabraca".toCharArray()), is(6));

        assertThat(trie.go("abracabracatabracbra".toCharArray()), is(14));
    }
}

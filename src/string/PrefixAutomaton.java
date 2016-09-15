package string;

public class PrefixAutomaton {
    int n;
    char[][] words;
    int[][] next;
    int[] parentId;
    int[] lastCharacter;
    int[] failure;
    int ni;

    public PrefixAutomaton(char[][] words) {
        n = 1;
        this.words = words;
        for (int i = 0; i < words.length ; i++) {
            n += words[i].length;
        }
        next = new int[n+1][26];
        parentId = new int[n+1];
        lastCharacter = new int[n+1];
        ni = 1;
        for (char[] w : words) {
            add(w);
        }
        buildFailureLink();
    }

    public int go(char[] l) {
        int head = 0;
        for (int i = 0; i < l.length ; i++) {
            head = next[head][l[i]-'a'];
        }
        return head;
    }

    private void add(char[] c) {
        int head = 0;
        for (int i = 0; i < c.length ; i++) {
            int ci = c[i]-'a';
            if (next[head][ci] == 0) {
                next[head][ci] = ni++;
            }
            parentId[next[head][ci]] = head;
            head = next[head][ci];
            lastCharacter[head] = ci;
        }
    }

    private void buildFailureLink() {
        int[] que = new int[n];
        int qh = 0;
        int qt = 0;
        que[qh++] = 0;
        failure = new int[n];
        while (qt < qh) {
            int now = que[qt++];
            if (parentId[now] >= 1) {
                int parFail = failure[parentId[now]];
                failure[now] = next[parFail][lastCharacter[now]];
            }
            for (int j = 0; j < 26 ; j++) {
                if (next[now][j] == 0) {
                    next[now][j] = next[failure[now]][j];
                } else {
                    que[qh++] = next[now][j];
                }
            }
        }
    }
}
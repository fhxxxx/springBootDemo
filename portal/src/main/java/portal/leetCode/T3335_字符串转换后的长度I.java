package portal.leetCode;

/**
 * 给你一个字符串 s 和一个整数 t，表示要执行的 转换 次数。每次 转换 需要根据以下规则替换字符串 s 中的每个字符：
 * <p>
 * 如果字符是 'z'，则将其替换为字符串 "ab"。
 * 否则，将其替换为字母表中的下一个字符。例如，'a' 替换为 'b'，'b' 替换为 'c'，依此类推。
 * 返回 恰好 执行 t 次转换后得到的字符串的 长度。
 * <p>
 * 由于答案可能非常大，返回其对 109 + 7 取余的结果。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入： s = "abcyy", t = 2
 * <p>
 * 输出： 7
 * <p>
 * 解释：
 * <p>
 * 第一次转换 (t = 1)
 * 'a' 变为 'b'
 * 'b' 变为 'c'
 * 'c' 变为 'd'
 * 'y' 变为 'z'
 * 'y' 变为 'z'
 * 第一次转换后的字符串为："bcdzz"
 * 第二次转换 (t = 2)
 * 'b' 变为 'c'
 * 'c' 变为 'd'
 * 'd' 变为 'e'
 * 'z' 变为 "ab"
 * 'z' 变为 "ab"
 * 第二次转换后的字符串为："cdeabab"
 * 最终字符串长度：字符串为 "cdeabab"，长度为 7 个字符。
 * 示例 2：
 * <p>
 * 输入： s = "azbk", t = 1
 * <p>
 * 输出： 5
 * <p>
 * 解释：
 * <p>
 * 第一次转换 (t = 1)
 * 'a' 变为 'b'
 * 'z' 变为 "ab"
 * 'b' 变为 'c'
 * 'k' 变为 'l'
 * 第一次转换后的字符串为："babcl"
 * 最终字符串长度：字符串为 "babcl"，长度为 5 个字符。
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 10^5
 * s 仅由小写英文字母组成。
 * 1 <= t <= 10^5
 *
 * @author gangxiang.guan
 * @create 2025/5/13 10:14
 */
public class T3335_字符串转换后的长度I {


    public static void main(String[] args) {
        System.out.println(Solution2.lengthAfterTransformations("jqktcurgdvlibczdsvnsg", 7517));
    }

    //递归查找，效能极差，leetCode超时了
    public static int lengthAfterTransformations(String s, int t) {
        int[] keyMap = new int[26];
        for (int i = 0; i < 26; i++) {
            keyMap[i] = getLength(i, t);
        }

        int total = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            total += keyMap[(int) chars[i] - 97] % (1e9 + 7);
        }
        return total;
    }

    /**
     * 求得字符x经过y次转换后的位数
     * @param x
     * @param y
     * @return
     */
    public static int getLength(int x, int y) {
        if (x + y < 26) {
            return 1;
        }
        return getLength(0, x + y - 26) + getLength(1, x + y - 26);
    }

    /**
     * 官方题解
     */
    static class Solution {
        private static final int MOD = 1000000007;

        public static int lengthAfterTransformations(String s, int t) {
            int[] cnt = new int[26];
            for (char ch : s.toCharArray()) {
                ++cnt[ch - 'a'];
            }
            for (int round = 0; round < t; ++round) {
                int[] nxt = new int[26];
                nxt[0] = cnt[25];
                nxt[1] = (cnt[25] + cnt[0]) % MOD;
                for (int i = 2; i < 26; ++i) {
                    nxt[i] = cnt[i - 1];
                }
                cnt = nxt;
            }
            int ans = 0;
            for (int i = 0; i < 26; ++i) {
                ans = (ans + cnt[i]) % MOD;
            }
            return ans;
        }
    }

    /**
     * 性能最高的题解
     */
    static class Solution1 {
        public static int lengthAfterTransformations(String s, int t) {
            char[] ch = s.toCharArray();
            int n = ch.length;
            int ans = n;
            int mod = (int) (1e9 + 7);
            int[] cnt = new int[26];
            for (char c : ch) {
                cnt[c - 'a']++;
            }
            int a = 0;
            int b = 1;
            int z = 25;
            for (int i = 0; i < t; i++) {
                b = a;
                cnt[b] += cnt[z];
                cnt[b] %= mod;
                ans += cnt[z];
                ans %= mod;
                a = z;
                z = z == 0? 25 : z - 1;
            }
            return ans % mod;
        }
    }

    static class Solution2 {
        private static final int MOD = (int) (1e9 + 7);
        private static final int MAX_T = (int) 1e5;  // 题目给定的 t 的最大范围
        private static final int MX = 26;            // 字母表大小（a-z）
        private static final long[][] dp = new long[MX][MAX_T + 1];

        // 静态初始化块，在类加载时执行预处理
        static {
            // 初始化：t=0 时，每个字符的长度都是 1
            for (int ch = 0; ch < MX; ch++) {
                dp[ch][0] = 1;  // 还未转换时，只有自己
            }

            // 动态规划预处理
            for (int t = 1; t <= MAX_T; t++) {
                for (int ch = 0; ch < MX; ch++) {
                    if (ch == 25) {
                        // 如果当前字符是 'z'，则替换为 'a' 和 'b'，长度变为两者之和
                        dp[ch][t] = (dp[0][t - 1] + dp[1][t - 1]) % MOD;
                    } else {
                        // 其他字符替换为下一个字符
                        dp[ch][t] = dp[ch + 1][t - 1];
                    }
                }
            }
        }

        public static int lengthAfterTransformations(String s, int t) {
            long res = 0;
            for (char c : s.toCharArray()) {
                int ch = c - 'a';  // 将字符转换为 0-25 的索引
                res = (res + dp[ch][t]) % MOD;
            }
            return (int) res;
        }
    }

}




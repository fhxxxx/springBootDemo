package portal.leetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/find-closest-node-to-given-two-nodes/
 * <p>
 * 给你一个 n 个节点的 有向图 ，节点编号为 0 到 n - 1 ，每个节点 至多 有一条出边。
 * <p>
 * 有向图用大小为 n 下标从 0 开始的数组 edges 表示，表示节点 i 有一条有向边指向 edges[i] 。如果节点 i 没有出边，那么 edges[i] == -1 。
 * <p>
 * 同时给你两个节点 node1 和 node2 。
 * <p>
 * 请你返回一个从 node1 和 node2 都能到达节点的编号，使节点 node1 和节点 node2 到这个节点的距离 较大值最小化。如果有多个答案，请返回 最小 的节点编号。如果答案不存在，返回 -1 。
 * <p>
 * 注意 edges 可能包含环。
 *
 * @author gangxiang.guan
 * @create 2025/5/30 9:53
 */
public class T2359_找到离给定两个节点最近的节点 {
    public static void main(String[] args) {
        System.out.println(Solution2.closestMeetingNode(new int[]{9,8,7,0,5,6,1,3,2,2}, 1,6));
    }

    static class Solution {
        public static int closestMeetingNode(int[] edges, int node1, int node2) {
            Map<Integer, Integer> node1Map = new HashMap<>();
            Map<Integer, Integer> node2Map = new HashMap<>();
            int node1Flag = 0, node2Flag = 0;
            int node1Clone = node1, node2Clone = node2;
            do {
                node1Map.put(node1Clone, node1Flag++);
                node1Clone = edges[node1Clone];
            } while (node1Clone != -1 && node1Clone != node1);
            do {
                node2Map.put(node2Clone, node2Flag++);
                node2Clone = edges[node2Clone];
            } while (node2Clone != -1 && node2Clone != node2);

            int max = -1, minNode = -1;
            for (Map.Entry<Integer, Integer> entry : node1Map.entrySet()) {
                if (node2Map.containsKey(entry.getKey())) {
                    int maxTmp = Integer.max(entry.getValue(), node2Map.get(entry.getKey()));
                    if (max == -1 || maxTmp < max) {
                        max = maxTmp;
                        minNode = entry.getKey();
                    }
                }
            }
            return minNode;
        }
    }

    static class Solution2 {
        public static int closestMeetingNode(int[] edges, int node1, int node2) {
            int n = edges.length;
            int[] d1 = get(edges, node1);
            int[] d2 = get(edges, node2);

            int res = -1;
            for (int i = 0; i < n; i++) {
                if (d1[i] != -1 && d2[i] != -1) {
                    if (res == -1 || Math.max(d1[res], d2[res]) > Math.max(d1[i], d2[i])) {
                        res = i;
                    }
                }
            }
            return res;
        }

        private static int[] get(int[] edges, int node) {
            int n = edges.length;
            int[] dist = new int[n];
            Arrays.fill(dist, -1);
            int distance = 0;
            while (node != -1 && dist[node] == -1) {
                dist[node] = distance++;
                node = edges[node];
            }
            return dist;
        }
    }
}

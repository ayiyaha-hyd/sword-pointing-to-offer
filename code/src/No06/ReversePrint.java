package No06;

import java.util.Arrays;
import java.util.Stack;

/**
 * 剑指 Offer 06. 从尾到头打印链表
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 *
 *
 *
 * 示例 1：
 *
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *
 *
 * 限制：
 *
 * 0 <= 链表长度 <= 10000
 */
public class ReversePrint {
    public static void main(String[] args) {
        ListNode n0 = new ListNode(0);
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);

        n0.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        int[] res = new Solution02().reversePrint(n0);
        System.out.println(Arrays.toString(res));
    }
}


class ListNode {
     int val;
    ListNode next;
   ListNode(int x) { val = x; }
 }

/**
 * 解法一：
 * 辅助栈
 * 利用栈先进后出的特点
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        if(head==null){
            return new int[0];
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;
        while (temp !=null){
            stack.push(temp);
            temp = temp.next;
        }
        int size = stack.size();
        int[] res = new int[size];
        for(int i=0;i<size;i++){
            res[i] = stack.pop().val;
        }
        return res;
    }
}

/**
 * 解法二：
 * 递归
 *
 */
class Solution02 {
    int[] res;
    int i;
    public int[] reversePrint(ListNode head) {
        recur(head,0);
        return res;
    }
    void recur(ListNode node,int count){
        if(node==null){
            res = new int[count];
            i =count -1;
            return;
        }
        recur(node.next,count+1);
        res[i-count] = node.val;

    }
}

/**
 * 解法三：
 * 非递归
 */
class Solution03 {
    public int[] reversePrint(ListNode head) {
        int[] res = new int[0];

        return res;
    }
}

/**
 * 解法四：
 * 反转链表
 */
class Solution04 {
    public int[] reversePrint(ListNode head) {
        int size = 0;
        ListNode pre = null;
        ListNode cur = head;
        ListNode temp = cur;
        while (cur!=null){
            temp = cur.next;//保存后续节点
            cur.next = pre;//反转
            pre = cur;//指针后移
            cur = temp;
            size++;//统计
        }
        int[] res = new int[size];
        size = 0;
        while (pre!=null){
            res[size++]=pre.val;
            pre = pre.next;
        }
        return res;
    }
}

/**
 * 解法五：
 * 先遍历链表获取长度，再遍历链表倒序存入数组
 */
class Solution05 {
    public int[] reversePrint(ListNode head) {
        ListNode temp = head;
        int len = 0;
        while (temp!=null){
            len++;
            temp = temp.next;
        }
        int[] res = new int[len];
        temp = head;
        while (temp!=null){
            res[--len]=temp.val;
            temp = temp.next;
        }
        return res;
    }
}
package com.nathan.list;

import java.util.Stack;

public class ReverseList206 {
    /**
     * leet code 206 双指针，反转链表
     *
     *定义两个指针： pre 和 cur , pre 在前 cur 在后。
     * 每次让 pre 的 next 指向 cur ，实现一次局部反转
     * 局部反转完成之后， pre 和 cur 同时往前移动一个位置
     * 循环上述过程，直至 pre 到达链表尾部
     *
     * */
    public ListNode reverseList(ListNode head) {
        if (null == head || null == head.next){
            return head;
        }

        ListNode cur = null;
        ListNode pre = head;
        while (pre != null) {
            ListNode next = pre.next;
            pre.next = cur;

            cur = pre;
            pre = next;
        }
        return cur;
    }

    public ListNode reverseList2(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }
        Stack<ListNode> stack = new Stack<>();
        // 节点全部入栈
        while (head != null) {
            stack.push(head);
            // 保留一个前指针用于断链
            ListNode pre = head;
            head = head.next;
            // 断链
            pre.next = null;
        }
        // 弹出栈顶元素作为新的头节点
        ListNode newHead = stack.pop();
        // 指针指向新头节点
        ListNode dummy = newHead;
        while (!stack.isEmpty()) {
            dummy.next = stack.pop();
            dummy = dummy.next;
        }
        return newHead;
    }

}

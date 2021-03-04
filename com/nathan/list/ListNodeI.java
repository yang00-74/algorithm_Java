package com.nathan.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义实现链表
 */

public class ListNodeI {
    public static void main(String[] args) {

        ListNode a = new ListNode(1);
        ListNode b = new ListNode(6);
        ListNode c = new ListNode(2);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(3);
        ListNode f = new ListNode(4);
        ListNode g = new ListNode(5);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
       // f.next = g;

    //    new com.nathan.list.ListNodeI().deleteDuplication2(a);
        ListNode n = new ListNodeI().findMidNode(a);

        while (a != null) {
            System.out.print(n.val);
            if (n.next != null)
                n = n.next;
            else {
                return;
            }
        }
    }

    //链表元素出现重复就删除
    public ListNode deleteDuplication2(ListNode pHead) {
        ListNode result;
        ListNode temp = pHead;
        ListNode index = new ListNode(1);//index比pHead多一个结点，遍历时可在pHead出现相同的前一节点停住，从而错开
        index.next = pHead;

        result = index;//记住index链表的头结点
        while (temp != null) {
            if (temp.next != null && temp.next.val == temp.val) {
                while (temp.next != null && temp.next.val == temp.val) {
                    temp = temp.next;
                }
                temp = temp.next;
                index.next = temp;//处理index链表
            } else {
                index = index.next;
                temp = temp.next;
            }
        }
        return result.next;

    }

    //打印链表倒数第k个元素
    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null) return null;
        int count = 0;
        ListNode node = head;
        while (node != null) {
            node = node.next;
            count++;
        }
        if (count < k) return null;

        ListNode p = head;
        for (int i = 0; i < count - k; i++) {
            p = p.next;
        }
        return p;
    }

    //链表复制
    public ListNode Clone(ListNode pHead) {
        if (pHead == null) return null;
        ListNode p = new ListNode(pHead.val);
        p.next = pHead.next;

        p.next = Clone(pHead.next);

        return p;
    }

    //合并两个有序链表
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode p = null;
        if (list1.val < list2.val) {
            p = list1;
            p.next = Merge(list1.next, list2);
        } else {
            p = list2;
            p.next = Merge(list1, list2.next);
        }
        return p;
    }

    //去掉链表中重复的元素
    public ListNode deleteDuplication(ListNode pHead) {
        while (pHead != null) {
            ListNode N = new ListNode(0);
            if (pHead.next == null) return pHead;

            N = pHead.next;
            while (pHead.val == N.val) {//判断结点是否有连续相等
                N = N.next;
            }
            pHead.next = N;
            pHead = pHead.next;
        }
        return pHead;
    }

    //求带环链表的入口
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null) return null;
        ListNode p = pHead;
        ListNode q = pHead;
        while (q != null && q.next != null) {
            p = p.next;
            q = q.next.next;
            if (p == q) {
                q = pHead;
                while (p != q) {
                    p = p.next;
                    q = q.next;
                }
                return p;
            }
        }
        return null;
    }

    //双指针，反转链表
    public ListNode ReverseList(ListNode head) {
        if (head == null) return null;

        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;

            head = next;

        }
        return pre;
    }

    /**
     * 一次遍历访问链表中间节点
     *
     * 思路:
     * 快慢指针,当快指针到达链表尾部时,慢指针到达链表中部
     */
    public ListNode findMidNode(ListNode root) {
        if (root == null) return null;
        ListNode twoStep = root;

        while (twoStep.next != null) {
            root = root.next;
            twoStep = twoStep.next.next;
            if (twoStep == null) {
                break;
            }
        }
        System.out.println(root.val);
        return root;
    }

    public ListNode sortList(ListNode head) {
        List<Integer> list = new ArrayList();
        ListNode cur = head;
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        Collections.sort(list);
        head = cur;
        for (int i = 0; i < list.size(); i++) {
            head.val = list.get(i);
            head = head.next;
        }
        return cur;

    }

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

}
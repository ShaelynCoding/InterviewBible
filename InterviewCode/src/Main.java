import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.Stack;
public class Main {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /*请完成下面这个函数，实现题目要求的功能
     ******************************开始写代码******************************/
    static ListNode partition(ListNode head,int m) {

        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        ListNode smallerTail = newHead;
        ListNode prev = newHead;
        while(head!=null)
        {
            if(head.val < m && smallerTail.next!=head)
            {
                prev.next = head.next;
                head.next = smallerTail.next;
                smallerTail.next = head;
                smallerTail = head;
                head = prev.next;
            }
            else if(head.val < m && smallerTail.next==head)
            {
                smallerTail = head;
                prev=head;
                head = head.next;
            }
            else
            {
                prev=head;
                head = head.next;
            }

        }
        return newHead.next;


    }

    static String resolve(String expr) {
        if(expr == null || expr.length() == 0)
            return "";
        Stack<Character> stack = new Stack<>();
        String reverse = "";
        for(int i=0;i<expr.length();i++)
        {
            reverse="";
            Character c = expr.charAt(i);
            if(c!=')')
            {
               stack.push(c);

            }
            else
            {
                Character charInstack = ' ';
                while(!stack.empty() &&  (charInstack= stack.pop()) != '(' )
                {
                    reverse += charInstack;
                }
                if(charInstack == '(')
                {
                    for(int j=0;j<reverse.length();j++)
                    {
                        stack.push(reverse.charAt(j));
                    }
                }

            }

        }
        if(stack.empty()) return reverse;
        reverse = "";
        while(!stack.empty())
        {
            Character x = stack.pop();
            if(x == '(')
                return "";
            reverse = x+ reverse;
        }
        return reverse;

    }

    static int schedule(int m,int[] array) {
        int n = array.length;
        int left = 0;
        int right = 0;
        for (int i = 0; i < n; i++){
            left = Math.max(left, array[i]);
            right += array[i];
        }
        int mid = 0;
        while(left < right){
            mid = (left + right) / 2;
            int t = 1;
            int sum = array[0];
            for(int i = 1; i < n; i++){
                if(sum + array[i] <= mid){
                    sum += array[i];
                }else{
                    t++;
                    if(t > m) break;
                    sum = array[i];
                }
            }
            if(t > m){
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        return right;
    }
    /******************************结束写代码******************************/
    public static double Power(double base, int exponent) {
        if(exponent == 0 ) return 1;
        if(exponent == 1 ) return base;
        return base * Power(base, exponent-1);
    }

    public static void main(String[] args) {
        System.out.println(Power(3,3));
    }

}


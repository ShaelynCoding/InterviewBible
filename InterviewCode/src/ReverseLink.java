import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.Stack;
  

public class ReverseLink {
    public class ListNode {
        int val;
        ListNode next = null;
        ListNode(int val) {
            this.val = val;
        }
    }
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        while(listNode!=null)
        {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while(!stack.empty())
        {
            arrayList.add(stack.pop());
        }
        return arrayList;
    }

    public ArrayList<Integer> printListFromTailToHeadRecursion(ListNode listNode) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        AddElements(arrayList, listNode);
        return arrayList;
    }
    public void AddElements(ArrayList<Integer> arrayList, ListNode currentNode)
    {
        if(currentNode==null)
            return;

        AddElements(arrayList,currentNode.next);
        arrayList.add(currentNode.val);
    }

}

public class reConstructBinaryTree {

   public static class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;

       TreeNode(int x) {
           val = x;
       }
   }
    public static TreeNode reConstructBinaryTree(int[] pre,int[] in) {
        if(pre.length == 0 || in.length == 0 || pre.length!=in.length)
            return null;
        return ConstructTree(0,pre.length-1,0,in.length-1,pre,in);
    }

    public static TreeNode ConstructTree(int preStartIndex, int preEndIndex, int inStartIndex, int inEndIndex, int[] pre, int[] in)
    {
        if(preStartIndex > preEndIndex) return null;
        int root = pre[preStartIndex];
        TreeNode node = new TreeNode(root);
        int index = inStartIndex;
        for(;index < inEndIndex;index++)
        {
            if(in[index]==root)
                break;
        }
        int preEnd = index-inStartIndex+preStartIndex;
        node.left = ConstructTree(preStartIndex+1, preEnd, inStartIndex, index-1, pre,in);
        node.right = ConstructTree(preEnd+1, preEndIndex,index+1, inEndIndex, pre, in);
        return node;
    }

    public static  void main(String[] args)
    {
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};
        TreeNode node = reConstructBinaryTree(pre,in);
    }
}

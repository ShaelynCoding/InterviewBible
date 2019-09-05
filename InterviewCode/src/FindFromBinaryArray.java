public class FindFromBinaryArray {
    public static boolean Find(int target, int [][] array) {
        int rowSize = array.length;
        if(rowSize == 0) return false;
        int colSize = array[0].length;
        int row = 0;
        int col = colSize - 1;
        while(col >=0 && row < rowSize)
        {
            if(array[row][col] == target)
            {
                return true;
            }
            else if(array[row][col] > target)
            {
                col--;
            }
            else
            {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args)
    {
        int[][] array = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        Find(7, array);

    }
}

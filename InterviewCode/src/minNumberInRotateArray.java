public class minNumberInRotateArray {
    public int minNumberInRotateArray(int [] array) {
        if(array.length == 0) return 0;
        if(array.length == 1) return array[0];

        int left = 0;
        int right = array.length-1;
        int mid = 0;
        while(array[left]>=array[right])
        {
            if(right - left == 1)
            {
                mid = right;
                break;
            }
            mid = (left + right) / 2;
            if(array[mid] >= array[left])
            {
                left = mid;
            }
            if(array[mid] <= array[right])
            {
                right = mid;
            }


        }
        return array[mid];
    }
}


public class ReplaceSpaceInString {
    public  static String replaceSpace(StringBuffer str) {
        int originIndex = str.length();
        int spaceNum = 0;
        for(int i=0;i<originIndex;i++)
        {
            if(str.charAt(i)==' ')
                spaceNum++;
        }
        int newIndex = spaceNum*2+originIndex;
       str.setLength(newIndex);
        --newIndex;
        --originIndex;
        while(originIndex>=0)
        {
            char c = str.charAt(originIndex--);
            if(c!=' ')
            {
                str.setCharAt(newIndex--, c);
            }
            else
            {
                str.setCharAt(newIndex--, '0');
                str.setCharAt(newIndex--, '2');
                str.setCharAt(newIndex--, '%');
            }

        }
        return str.toString();
    }
    public static void main(String[] args)
    {
        StringBuffer buffer = new StringBuffer("We Are Happy");
        System.out.println(replaceSpace(buffer));
    }
}

package No05;

/**
 * 剑指 Offer 05. 替换空格
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 *
 *
 * 限制：
 *
 * 0 <= s 的长度 <= 10000
 */
public class ReplaceSpace {
}

/**
 * 解法一：
 * 利用StringBuilder替换
 */
class Solution {
    public String replaceSpace(String s) {
        StringBuilder str = new StringBuilder();
        for(char c:s.toCharArray()){
            if(c == ' '){
                str.append("%20");
            }else {
                str.append(c);
            }
        }
        return str.toString();
    }
}

/**
 * 解法二：
 */
class Solution01 {
    public String replaceSpace(String s) {
        char[] chars = new char[s.length()*3];
        int j=0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c == ' '){
                chars[j++]='%';
                chars[j++]='2';
                chars[j++]='0';
            }
            else {
                chars[j++]=c;
            }
        }
        return new String(chars,0,j);
    }
}
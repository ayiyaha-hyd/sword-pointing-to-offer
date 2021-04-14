package No03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 找出数组中重复的数字。
 *
 *
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 示例 1：
 *
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 *  
 *
 * 限制：
 *
 * 2 <= n <= 100000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Duplicate {
    public static void main(String[] args) {
        int[] arr= {2,1,3,1,4};
        System.out.println(new Solution03().findRepeatNumber(arr));
    }
}

/**
 * 解法一：
 * 利用哈希表(Set)遍历
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int repeat = -1;
        for(int num:nums){
            //set.add(num)方法如果已经有num，那么返回false
            if(!set.add(num)){
                repeat = num;
                break;
            }
        }
        return repeat;
    }
}
class Solution01 {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num:nums){
            if(set.contains(num)){
                return num;
            }
            set.add(num);
        }
        return -1;
    }
}

/**
 * 解法二：
 * 对数组进行排序，重复数字则相邻数组间必定存在相等
 * 时间复杂度：O(nlg n)
 */
class Solution02 {
    public int findRepeatNumber(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0; i<nums.length-1; i++){
            if(nums[i]==nums[i+1]){
                return nums[i];
            }
        }
        return -1;
    }
}
/**
 * 解法三：
 * 原地交换（对号入座）
 * 此题题目说明了一个含义：数组元素的 索引 和 值 是 一对多 的关系。
 * 因此，可遍历数组并通过交换操作，使元素的 索引 与 值 一一对应（即 nums[i] = inums[i]=i ）。因而，就能通过索引映射对应的值，起到与字典等价的作用。
 * 时间复杂度 O(N)
 * 空间复杂度 O(1)
 */
class Solution03{
    public int findRepeatNumber(int[] nums) {
        int temp;
        for(int i=0;i<nums.length;i++){
            //如果没有重复数字，那么正常排序后，元素nums[i]应该在下标为i的位置，一个索引i可能对应多个元素
            //元素nums[i]不在正确位置上 nums[3]=5;5!=3
            while (nums[i]!=i){
                //如果发现他要放入的位置，及下标为nums[i]的元素与它相等（重复）
                //发现nums[3]=5,nums[nums[3]]及nums[5]=5，重复
                if (nums[i] == nums[nums[i]]){
                    return nums[i];
                }
                //把元素nums[i]放到下标为nums[i]的位置上，nums[nums[i]]=原nums[i]即nums[元素]=元素
                //交换nums[5]=7与nums[3]=5，使得nums[5]=5,nums[3]=7
                /*
                异或交换(了解） a=1;b=2;
                a=a^b; a=1^2;b=2;
                b=a^b; b=(1^2)^2=1;a=1^2;
                a=a^b; a=(1^2)^1=2;b=1;
                 */
                temp = nums[i];
                nums[i] = nums[temp];
                nums[temp] = temp;
            }

        }
        return -1;
    }
}

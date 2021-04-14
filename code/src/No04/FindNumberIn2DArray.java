package No04;

/**
 * 剑指 Offer 04. 二维数组中的查找
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 *
 *
 * 示例:
 *
 * 现有矩阵 matrix 如下：
 *
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 *
 * 给定 target = 20，返回 false。
 *
 *
 *
 * 限制：
 *
 * 0 <= n <= 1000
 *
 * 0 <= m <= 1000
 */
public class FindNumberIn2DArray {

    public static void main(String[] args) {

        int[][] arr = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        boolean numberIn2DArray = new Solution().findNumberIn2DArray(arr, 5);
        System.out.println(numberIn2DArray);
    }
}

/**
 * 解法一：
 * 线性查找
 * 利用给定的二维数组具备每行从左到右递增以及每列从上到下递增的特点
 * 时间复杂度：O(n+m)
 */
class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length-1;
        while (row<=matrix.length-1&&col>=0){
            if(target==matrix[row][col]){
                return true;
            }
            else if(target<matrix[row][col]){
                col--;
            }
            else if(target>matrix[row][col]){
                row++;
            }
        }
        return false;
    }
}

/**
 * 解法二：
 * 暴力法（遍历）二分查找
 * 时间复杂度：O(logn)
 */
class Solution02 {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {

        for(int i=0;i<matrix.length;i++){
            int start = 0;
            int end = matrix[0].length-1;
            while (start<=end){
                int mid = (start+end)>>1;
                if(target==matrix[i][mid]){
                    return true;
                }
                else if(target<matrix[i][mid]){
                    end=mid-1;
                }
                else if(target>matrix[i][mid]){
                    start=mid+1;
                }
            }
        }
        return false;
    }
}
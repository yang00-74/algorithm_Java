package com.nathan.leetcode.editor.cn;
/*
 * @lc app=leetcode.cn id=162 lang=java
 *
 * [162] 寻找峰值
 *
 * https://leetcode.cn/problems/find-peak-element/description/
 *
 * algorithms
 * Medium (49.48%)
 * Likes:    1244
 * Dislikes: 0
 * Total Accepted:    362.4K
 * Total Submissions: 732.4K
 * Testcase Example:  '[1,2,3,1]'
 *
 * 峰值元素是指其值严格大于左右相邻值的元素。
 * 
 * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
 * 
 * 你可以假设 nums[-1] = nums[n] = -∞ 。
 * 
 * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 输入：nums = [1,2,3,1]
 * 输出：2
 * 解释：3 是峰值元素，你的函数应该返回其索引 2。
 * 
 * 示例 2：
 * 
 * 
 * 输入：nums = [1,2,1,3,5,6,4]
 * 输出：1 或 5 
 * 解释：你的函数可以返回索引 1，其峰值元素为 2；
 * 或者返回索引 5， 其峰值元素为 6。
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= nums.length <= 1000
 * -2^31 <= nums[i] <= 2^31 - 1
 * 对于所有有效的 i 都有 nums[i] != nums[i + 1]
 * 
 * 
 */

// @lc code=start
class Solution162 {
    /**
     * 如果 nums[i]<nums[i+1] 并且我们从位置 i 向右走到了位置 i+1，那么位置 i 左侧的所有位置是不可能在后续的迭代中走到的。
     *
     * 这是因为我们每次向左或向右移动一个位置，要想「折返」到位置 i 以及其左侧的位置，我们首先需要在位置 i+1 向左走到位置 i，但这是不可能的。
     *
     * 并且我们知道位置 i+1 以及其右侧的位置中一定有一个峰值，因此我们可以设计出如下的一个算法：
     *
     * 对于当前可行的下标范围 [l,r]我们随机一个下标 i；
     *
     * 如果下标 i 是峰值，我们返回 i 作为答案；
     *
     * 如果 nums[i]<nums[i+1]，那么我们抛弃 [l,i] 的范围，在剩余 [i+1,r] 的范围内继续随机选取下标；
     *
     * 如果 nums[i]>nums[i+1]，那么我们抛弃 [i,r] 的范围，在剩余 [l,i−1] 的范围内继续随机选取下标。
     *
     * 在上述算法中，如果我们固定选取 i为 [l,r] 的中点，那么每次可行的下标范围会减少一半，成为一个类似二分查找的方法，时间复杂度为 O(log⁡n)。
     *
     * */
    public int findPeakElement(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        // -n,[8,7,2,5,4,3,1] l=0, r=6
        // -n,[8,7,2,5] l=0, r=3
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
// @lc code=end


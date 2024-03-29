#### 前言

本题你可以选择直接调用库函数来对序列进行排序，但意义不大。由于排序算法有很多，本文只介绍三种常见的基于比较的复杂度较低的排序。

#### 方法一：快速排序

**思路和算法**

快速排序的主要思想是通过划分将待排序的序列分成前后两部分，其中前一部分的数据都比后一部分的数据要小，然后再递归调用函数对两部分的序列分别进行快速排序，以此使整个序列达到有序。

我们定义函数 `randomized_quicksort(nums, l, r)` 为对 `nums` 数组里 *[l,r]* 的部分进行排序，每次先调用 `randomized_partition` 函数对 `nums` 数组里 *[l,r]* 的部分进行划分，并返回分界值的下标 `pos`，然后按上述将的递归调用 `randomized_quicksort(nums, l, pos - 1)` 和 `randomized_quicksort(nums, pos + 1, r)` 即可。

那么核心就是划分函数的实现了，划分函数一开始需要确定一个分界值（我们称之为主元 `pivot`)，然后再进行划分。而主元的选取有很多种方式，这里我们采用随机的方式，对当前划分区间 *[l,r]* 里的数等概率随机一个作为我们的主元，再将主元放到区间末尾，进行划分。

整个划分函数 `partition` 主要涉及两个指针 *i* 和 *j*，一开始 `i = l - 1`，`j = l`。我们需要实时维护两个指针使得任意时候，对于任意数组下标 *k*，我们有如下条件成立：
1. ![l\leqk\leqi ](./p__lleq_kleq_i_.png)  时，![\textit{nums}\[k\]\leq\textit{pivot} ](./p__textit{nums}_k_leq_textit{pivot}_.png) 。

2. ![i+1\leqk\leqj-1 ](./p__i+1leq_kleq_j-1_.png)  时，![\textit{nums}\[k\]>\textit{pivot} ](./p__textit{nums}_k___textit{pivot}_.png) 。

3. *k==r* 时，![\textit{nums}\[k\]=\textit{pivot} ](./p__textit{nums}_k_=textit{pivot}_.png) 。

我们每次移动指针 *j* ，如果 ![\textit{nums}\[j\]>pivot ](./p__textit{nums}_j___pivot_.png) ，我们只需要继续移动指针 *j* ，即能使上述三个条件成立，否则我们需要将指针 *i* 加一，然后交换 ![\textit{nums}\[i\] ](./p__textit{nums}_i__.png)  和 ![\textit{nums}\[j\] ](./p__textit{nums}_j__.png) ，再移动指针 *j* 才能使得三个条件成立。

当 *j* 移动到 *r-1* 时结束循环，此时我们可以由上述三个条件知道 *[l,i]* 的数都小于等于主元 `pivot`，*[i+1,r-1]* 的数都大于主元 `pivot`，那么我们只要交换 ![\textit{nums}\[i+1\] ](./p__textit{nums}_i+1__.png)  和 ![\textit{nums}\[r\] ](./p__textit{nums}_r__.png)  ，即能使得 *[l,i+1]* 区间的数都小于 *[i+2,r]* 区间的数，完成一次划分，且分界值下标为 *i+1*，返回即可。

如下的动图展示了一次划分的过程，刚开始随机选了 *4* 作为主元，与末尾元素交换后开始划分：

 [fig1](https://assets.leetcode-cn.com/solution-static/912/912_fig1.gif)

```C++ [sol1-C++]
class Solution {
    int partition(vector<int>& nums, int l, int r) {
        int pivot = nums[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; ++j) {
            if (nums[j] <= pivot) {
                i = i + 1;
                swap(nums[i], nums[j]);
            }
        }
        swap(nums[i + 1], nums[r]);
        return i + 1;
    }
    int randomized_partition(vector<int>& nums, int l, int r) {
        int i = rand() % (r - l + 1) + l; // 随机选一个作为我们的主元
        swap(nums[r], nums[i]);
        return partition(nums, l, r);
    }
    void randomized_quicksort(vector<int>& nums, int l, int r) {
        if (l < r) {
            int pos = randomized_partition(nums, l, r);
            randomized_quicksort(nums, l, pos - 1);
            randomized_quicksort(nums, pos + 1, r);
        }
    }
public:
    vector<int> sortArray(vector<int>& nums) {
        srand((unsigned)time(NULL));
        randomized_quicksort(nums, 0, (int)nums.size() - 1);
        return nums;
    }
};
```
```Java [sol1-Java]
class Solution {
    public int[] sortArray(int[] nums) {
        randomizedQuicksort(nums, 0, nums.length - 1);
        return nums;
    }

    public void randomizedQuicksort(int[] nums, int l, int r) {
        if (l < r) {
            int pos = randomizedPartition(nums, l, r);
            randomizedQuicksort(nums, l, pos - 1);
            randomizedQuicksort(nums, pos + 1, r);
        }
    }

    public int randomizedPartition(int[] nums, int l, int r) {
        int i = new Random().nextInt(r - l + 1) + l; // 随机选一个作为我们的主元
        swap(nums, r, i);
        return partition(nums, l, r);
    }

    public int partition(int[] nums, int l, int r) {
        int pivot = nums[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; ++j) {
            if (nums[j] <= pivot) {
                i = i + 1;
                swap(nums, i, j);
            }
        }
        swap(nums, i + 1, r);
        return i + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```


**复杂度分析**

- 时间复杂度：基于随机选取主元的快速排序时间复杂度为期望 ![O(n\logn) ](./p__O_nlog_n__.png) ，其中 *n* 为数组的长度。详细证明过程可以见《算法导论》第七章，这里不再大篇幅赘述。

- 空间复杂度：*O(h)*，其中 *h* 为快速排序递归调用的层数。我们需要额外的 *O(h)* 的递归调用的栈空间，由于划分的结果不同导致了快速排序递归调用的层数也会不同，最坏情况下需 *O(n)* 的空间，最优情况下每次都平衡，此时整个递归树高度为 ![\logn ](./p__log_n_.png) ，空间复杂度为 ![O(\logn) ](./p__O_log_n__.png) 。 

#### 方法二：堆排序

**预备知识**

- 堆

**思路和算法**

堆排序的思想就是先将待排序的序列建成大根堆，使得每个父节点的元素大于等于它的子节点。此时整个序列最大值即为堆顶元素，我们将其与末尾元素交换，使末尾元素为最大值，然后再调整堆顶元素使得剩下的 *n-1* 个元素仍为大根堆，再重复执行以上操作我们即能得到一个有序的序列。

如下两个动图展示了对 `[4, 6, 8, 5, 9]` 这个数组堆排序的过程：

 [fig2](https://assets.leetcode-cn.com/solution-static/912/912_fig2.gif)

 [fig3](https://assets.leetcode-cn.com/solution-static/912/912_fig3.gif)

```C++ [sol2-C++]
class Solution {
    void maxHeapify(vector<int>& nums, int i, int len) {
        for (; (i << 1) + 1 <= len;) {
            int lson = (i << 1) + 1;
            int rson = (i << 1) + 2;
            int large;
            if (lson <= len && nums[lson] > nums[i]) {
                large = lson;
            } else {
                large = i;
            }
            if (rson <= len && nums[rson] > nums[large]) {
                large = rson;
            }
            if (large != i) {
                swap(nums[i], nums[large]);
                i = large;
            } else {
                break;
            }
        }
    }
    void buildMaxHeap(vector<int>& nums, int len) {
        for (int i = len / 2; i >= 0; --i) {
            maxHeapify(nums, i, len);
        }
    }
    void heapSort(vector<int>& nums) {
        int len = (int)nums.size() - 1;
        buildMaxHeap(nums, len);
        for (int i = len; i >= 1; --i) {
            swap(nums[i], nums[0]);
            len -= 1;
            maxHeapify(nums, 0, len);
        }
    }
public:
    vector<int> sortArray(vector<int>& nums) {
        heapSort(nums);
        return nums;
    }
};
```
```Java [sol2-Java]
class Solution {
    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }

    public void heapSort(int[] nums) {
        int len = nums.length - 1;
        buildMaxHeap(nums, len);
        for (int i = len; i >= 1; --i) {
            swap(nums, i, 0);
            len -= 1;
            maxHeapify(nums, 0, len);
        }
    }

    public void buildMaxHeap(int[] nums, int len) {
        for (int i = len / 2; i >= 0; --i) {
            maxHeapify(nums, i, len);
        }
    }

    public void maxHeapify(int[] nums, int i, int len) {
        for (; (i << 1) + 1 <= len;) {
            int lson = (i << 1) + 1;
            int rson = (i << 1) + 2;
            int large;
            if (lson <= len && nums[lson] > nums[i]) {
                large = lson;
            } else {
                large = i;
            }
            if (rson <= len && nums[rson] > nums[large]) {
                large = rson;
            }
            if (large != i) {
                swap(nums, i, large);
                i = large;
            } else {
                break;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```
```Python [sol2-Python3]
class Solution:
    def max_heapify(self, heap, root, heap_len):
        p = root
        while p * 2 + 1 < heap_len:
            l, r = p * 2 + 1, p * 2 + 2
            if heap_len <= r or heap[r] < heap[l]:
                nex = l
            else:
                nex = r
            if heap[p] < heap[nex]:
                heap[p], heap[nex] = heap[nex], heap[p]
                p = nex
            else:
                break
        
    def build_heap(self, heap):
        for i in range(len(heap) - 1, -1, -1):
            self.max_heapify(heap, i, len(heap))

    def heap_sort(self, nums):
        self.build_heap(nums)
        for i in range(len(nums) - 1, -1, -1):
            nums[i], nums[0] = nums[0], nums[i]
            self.max_heapify(nums, 0, i)
            
    def sortArray(self, nums: List[int]) -> List[int]:
        self.heap_sort(nums)
        return nums
```

**复杂度分析**

- 时间复杂度：![O(n\logn) ](./p__O_nlog_n__.png) 。初始化建堆的时间复杂度为 *O(n)*，建完堆以后需要进行 *n-1* 次调整，一次调整（即 `maxHeapify`） 的时间复杂度为 ![O(\logn) ](./p__O_log_n__.png) ，那么 *n-1* 次调整即需要 ![O(n\logn) ](./p__O_nlog_n__.png)  的时间复杂度。因此，总时间复杂度为 ![O(n+n\logn)=O(n\logn) ](./p__O_n+nlog_n_=O_nlog_n__.png) 。

- 空间复杂度：*O(1)*。只需要常数的空间存放若干变量。

#### 方法三：归并排序

**思路**

归并排序利用了分治的思想来对序列进行排序。对一个长为 *n* 的待排序的序列，我们将其分解成两个长度为 ![\frac{n}{2} ](./p__frac{n}{2}_.png)  的子序列。每次先递归调用函数使两个子序列有序，然后我们再线性合并两个有序的子序列使整个序列有序。

**算法**

定义 `mergeSort(nums, l, r)` 函数表示对 `nums` 数组里 *[l,r]* 的部分进行排序，整个函数流程如下：

1. 递归调用函数 `mergeSort(nums, l, mid)` 对 `nums` 数组里 ![\[l,\textit{mid}\] ](./p___l,textit{mid}__.png)  部分进行排序。

2. 递归调用函数 `mergeSort(nums, mid + 1, r)` 对 `nums` 数组里 ![\[\textit{mid}+1,r\] ](./p___textit{mid}+1,r__.png)  部分进行排序。

3. 此时 `nums` 数组里 ![\[l,\textit{mid}\] ](./p___l,textit{mid}__.png)  和 ![\[\textit{mid}+1,r\] ](./p___textit{mid}+1,r__.png)  两个区间已经有序，我们对两个有序区间线性归并即可使 `nums` 数组里 *[l,r]* 的部分有序。

   线性归并的过程并不难理解，由于两个区间均有序，所以我们维护两个指针 *i* 和 *j* 表示当前考虑到 ![\[l,\textit{mid}\] ](./p___l,textit{mid}__.png)  里的第 *i* 个位置和 ![\[\textit{mid}+1,r\] ](./p___textit{mid}+1,r__.png)  的第 *j* 个位置。

   如果 `nums[i] <= nums[j]` ，那么我们就将 ![\textit{nums}\[i\] ](./p__textit{nums}_i__.png)  放入临时数组 `tmp` 中并让 `i += 1` ，即指针往后移。否则我们就将 ![\textit{nums}\[j\] ](./p__textit{nums}_j__.png)  放入临时数组 `tmp` 中并让 `j += 1` 。如果有一个指针已经移到了区间的末尾，那么就把另一个区间里的数按顺序加入 `tmp` 数组中即可。

   这样能保证我们每次都是让两个区间中较小的数加入临时数组里，那么整个归并过程结束后 *[l,r]* 即为有序的。
   
如下的动图展示了两个有序数组线性归并的过程：

 [fig4](https://assets.leetcode-cn.com/solution-static/912/912_fig4.gif)

函数递归调用的入口为 `mergeSort(nums, 0, nums.length - 1)`，递归结束当且仅当 `l >= r`。

```C++ [sol3-C++]
class Solution {
    vector<int> tmp;
    void mergeSort(vector<int>& nums, int l, int r) {
        if (l >= r) return;
        int mid = (l + r) >> 1;
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        int i = l, j = mid + 1;
        int cnt = 0;
        while (i <= mid && j <= r) {
            if (nums[i] <= nums[j]) {
                tmp[cnt++] = nums[i++];
            }
            else {
                tmp[cnt++] = nums[j++];
            }
        }
        while (i <= mid) {
            tmp[cnt++] = nums[i++];
        }
        while (j <= r) {
            tmp[cnt++] = nums[j++];
        }
        for (int i = 0; i < r - l + 1; ++i) {
            nums[i + l] = tmp[i];
        }
    }
public:
    vector<int> sortArray(vector<int>& nums) {
        tmp.resize((int)nums.size(), 0);
        mergeSort(nums, 0, (int)nums.size() - 1);
        return nums;
    }
};
```
```Java [sol3-Java]
class Solution {
    int[] tmp;

    public int[] sortArray(int[] nums) {
        tmp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void mergeSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) >> 1;
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        int i = l, j = mid + 1;
        int cnt = 0;
        while (i <= mid && j <= r) {
            if (nums[i] <= nums[j]) {
                tmp[cnt++] = nums[i++];
            } else {
                tmp[cnt++] = nums[j++];
            }
        }
        while (i <= mid) {
            tmp[cnt++] = nums[i++];
        }
        while (j <= r) {
            tmp[cnt++] = nums[j++];
        }
        for (int k = 0; k < r - l + 1; ++k) {
            nums[k + l] = tmp[k];
        }
    }
}
```
```Python [sol3-Python3]
class Solution:
    def merge_sort(self, nums, l, r):
        if l == r:
            return
        mid = (l + r) // 2
        self.merge_sort(nums, l, mid)
        self.merge_sort(nums, mid + 1, r)
        tmp = []
        i, j = l, mid + 1
        while i <= mid or j <= r:
            if i > mid or (j <= r and nums[j] < nums[i]):
                tmp.append(nums[j])
                j += 1
            else:
                tmp.append(nums[i])
                i += 1
        nums[l: r + 1] = tmp

    def sortArray(self, nums: List[int]) -> List[int]:
        self.merge_sort(nums, 0, len(nums) - 1)
        return nums
```

**复杂度分析**

- 时间复杂度：![O(n\logn) ](./p__O_nlog_n__.png) 。由于归并排序每次都将当前待排序的序列折半成两个子序列递归调用，然后再合并两个有序的子序列，而每次合并两个有序的子序列需要 *O(n)* 的时间复杂度，所以我们可以列出归并排序运行时间 *T(n)* 的递归表达式：
![T(n)=2T(\frac{n}{2})+O(n) ](./p___T_n_=2T_frac{n}{2}_+O_n___.png) 
​		根据主定理我们可以得出归并排序的时间复杂度为 ![O(n\logn) ](./p__O_nlog_n__.png) 。

- 空间复杂度：*O(n)*。我们需要额外 *O(n)* 空间的 ![\textit{tmp} ](./p__textit{tmp}_.png)  数组，且归并排序递归调用的层数最深为 ![\log_2n ](./p__log_2_n_.png) ，所以我们还需要额外的 ![O(\logn) ](./p__O_log_n___.png)  的栈空间，所需的空间复杂度即为 ![O(n+\logn)=O(n) ](./p__O_n+log_n__=_O_n__.png) 。
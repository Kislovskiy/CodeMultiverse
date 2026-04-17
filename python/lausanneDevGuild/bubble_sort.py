def bubble_sort(nums):
    swapping = True
    end = len(nums)
    while swapping is True:
        swapping = False
        for i in range(1, end):
            if nums[i - 1] > nums[i]:
                nums[i], nums[i - 1] = nums[i - 1], nums[i]
                swapping = True
        end -= 1
    return nums
    
def bubble_sort2(nums):
    end = len(nums)
    while end > 0:
        for i in range(1, end):
            if nums[i - 1] > nums[i]:
                nums[i], nums[i - 1] = nums[i - 1], nums[i]
        end -= 1
    return nums
    
if __name__ == "__main__":
    print(f"{bubble_sort([5, 7, 3, 6, 8])=}")
    print(f"{bubble_sort2([5, 7, 3, 6, 8])=}")

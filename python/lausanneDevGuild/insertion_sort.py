def insertion_sort(nums):
    for i in range(1, len(nums)):
        j = i
        while j > 0 and nums[j] < nums[j - 1]:
            nums[j - 1], nums[j] = nums[j], nums[j - 1]
            j -= 1
        
    return nums
    
if __name__ == "__main__":
    print(insertion_sort([9, 5, -3, 7]))
    
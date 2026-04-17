def quick_sort(nums, low, high):
    if low < high:
        middle_index = partition(nums, low, high)
        quick_sort(nums, low, middle_index - 1)
        quick_sort(nums, middle_index + 1, high)
    return nums


def partition(nums, low, high):
    pivot = nums[high]
    i = low - 1
    for j in range(low, high):
        if nums[j] < pivot:
            i += 1
            nums[i], nums[j] = nums[j], nums[i]
    nums[i + 1], nums[high] = nums[high], nums[i + 1]
    return i + 1


if __name__ == "__main__":
    input_array = [9, 6, 2, 1, 8, 7]
    print(quick_sort(input_array, 0, len(input_array) - 1))

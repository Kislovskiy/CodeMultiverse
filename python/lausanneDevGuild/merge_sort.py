def merge_sort(nums):
    input_length = len(nums)
    if input_length < 2:
        return nums
    else:
        middle = input_length // 2
        nums_left_side, nums_right_side = nums[:middle], nums[middle:]
        sorted_left_side = merge_sort(nums_left_side)
        sorted_right_side = merge_sort(nums_right_side)
        return merge(sorted_left_side, sorted_right_side)

def merge(first, second):
    final = []
    i, j = 0, 0
    while i < len(first) and j < len(second):
        if first[i] <= second[j]:
            final.append(first[i])
            i += 1
        else:
            final.append(second[j])
            j += 1
    if len(first[i:]) > 0:
        final = final + first[i:]
    elif len(second[j:]) > 0:
        final = final + second[j:]
    return final

if __name__ == "__main__":
    print(merge_sort([4, -7, 1, 0, 5]))
    
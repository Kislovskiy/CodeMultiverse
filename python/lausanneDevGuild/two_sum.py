def twoSum(nums: list[int], target: int) -> list[int]:
    """Given an array of integers nums and an integer target,
    Returns indices of the two numbers such that they add up to target.
    """
    reminder = {}
    for i in range(len(nums)):
        if nums[i] in reminder.keys():
            return [reminder[nums[i]], i]
        else:
            reminder[target - nums[i]] = i
    return []


def threeSum(nums: list[int]) -> list[list[int]]:
    """Given an integer array nums, return all the triplets
    [nums[i], nums[j], nums[k]]
    such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0
    """
    nums = sorted(nums)
    result = []
    for i in range(len(nums) - 2):
        if nums[i] > 0:
            break
        if i > 0 and nums[i] == nums[i - 1]:
            continue

        low = i + 1
        high = len(nums) - 1
        while low < high:
            total = nums[i] + nums[low] + nums[high]
            if total == 0:
                result.append([nums[i], nums[low], nums[high]])
                while low < high and nums[low] == nums[low + 1]:
                    low += 1
                while low < high and nums[high] == nums[high - 1]:
                    high -= 1
                low += 1
                high -= 1
            elif total > 0:
                high -= 1
            else:
                low += 1

    return result


if __name__ == "__main__":
    print(twoSum([2, 7, 11, 15], 9))
    print(twoSum([3, 2, 4], 6))
    print(twoSum([3, 3], 6))

    print(threeSum([-1, 0, 1, 2, -1, -4]))
    print(threeSum([0, 1, 1]))

def threeSum(array: list[int]) -> list[list[int]]:
    """Find all unique triplets in an array that sum to zero.
    [-1, 0, 1, 2, -1, -4] -> [(-1, -1, 2), ...]
    """
    sorted_array = sorted(array)
    results = []
    for i in range(len(sorted_array)):
        low = i + 1
        high = len(sorted_array) - 1
        while low < high:
            triplet = (sorted_array[i], sorted_array[low], sorted_array[high])
            sum_triplet = sum(triplet)
            if sum_triplet == 0:
                if triplet not in results:
                    results.append(triplet)
                low += 1
            elif sum_triplet > 0:
                high -= 1
            else:
                low += 1
               
    return results 
    

if __name__ == "__main__":
    nums_arrs = [
           [-1, 0, 1, 2, -1, -4],
           [1, 2, 3, 4, 5],
           [0, 0, 0, 0],
           [-4, -1, -1, 0, 1, 2, 2],
           [-10, -7, -3, -1, 0, 3, 7, 10],
           [-3, -5, -7, -9]
       ]   
    for arr in nums_arrs:
       print(f"{arr=} {threeSum(arr)=}")

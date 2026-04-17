import random

def find_smallest(array: list[int]) -> int:
    smallest = float("inf")
    smallest_idx = -1
    for i in range(len(array)):
        if array[i] < smallest:
           smallest_idx = i
           smallest = array[i]
    return smallest_idx
           
def find_smallest2(array: list[int]) -> int:
    smallest_idx = 0
    smallest = array[smallest_idx]
    for i in range(len(array)):
        if array[i] < smallest:
           smallest_idx = i
           smallest = array[i]
    return smallest_idx
    
def selection_sort(input_list: list[int]) -> list[int]:
    new_array = list(input_list)
    results = []
    for _ in range(len(input_list)):
        # find the smallest
        # remove the smallest and append it to the result array
        smallest_idx = find_smallest2(new_array)
        results.append(new_array.pop(smallest_idx))
        
    return results


if __name__ == "__main__":
    # print(selection_sort([5, 3, 6, 2, 10]))
    input = [random.randint(1, 2000) for _ in range(5)]
    print(input, selection_sort(input))

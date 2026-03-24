from functools import reduce
from operator import xor


def get_unique_element(array: list[int]) -> int:
    """Return the single element that appears an odd number of times.

    Assumes exactly one such element exists. Uses XOR: duplicates cancel out.
    Raises ValueError for empty input.
    """
    if not array:
        raise ValueError("Array must not be empty")
    return reduce(xor, array)


if __name__ == "__main__":
    test_cases = [([1, 2, 3, 2, 1], 3), ([1, 1, 2, 1, 1], 2)]
    for array, expected in test_cases:
        result = get_unique_element(array)
        status = "✓" if result == expected else "✗"
        print(f"{status} get_unique_element({array}) = {result} (expected {expected})")

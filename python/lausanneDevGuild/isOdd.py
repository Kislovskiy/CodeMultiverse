def isOdd(number: int) -> bool:
    """Return True if number is odd, using a bitwise AND check on the least significant bit (LSB)."""
    return (number & 1) == 1


if __name__ == "__main__":
    test_cases = [0, 1, -1, -4, 7, 100, 999]
    for n in test_cases:
        print(f"is_odd({n}) = {isOdd(n)}")

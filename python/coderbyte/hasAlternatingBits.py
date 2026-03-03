# 693. Binary Number with Alternating Bits
class Solution:
    def hasAlternatingBits(self, n: int) -> bool:
        """
        5 => true 5 = 101
        7 => false 7 = 111
        11 => false 11 = 1011
        10 => true 10 = 1010

        previous_bit = n & 1
        n = n >> 1

        while n > 0:
            current_bit = n & 1
            if current_bit == previous_bit:
                return False
            previous_bit = current_bit
            n = n >> 1

        return True
        """
        previous_bit = n & 1
        n = n >> 1

        while n > 0:
            current_bit = n & 1
            if current_bit == previous_bit:
                return False
            previous_bit = current_bit
            n = n >> 1

        return True

    def hasAlternateBits2(self, n: int) -> bool:
        m = n ^ (n >> 1)
        return m & (m + 1) == 0


solution = Solution()
print(f"{solution.hasAlternatingBits(5)} {solution.hasAlternateBits2(5)}")
print(f"{solution.hasAlternatingBits(7)} {solution.hasAlternateBits2(7)}")
print(f"{solution.hasAlternatingBits(10)} {solution.hasAlternateBits2(10)}")
print(f"{solution.hasAlternatingBits(11)} {solution.hasAlternateBits2(11)}")
print(f"{solution.hasAlternatingBits(1)} {solution.hasAlternateBits2(1)}")

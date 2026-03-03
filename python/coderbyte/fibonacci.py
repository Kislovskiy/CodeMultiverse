from functools import lru_cache


def fib(n, memo=None):
    if memo is None:
        memo = {}
    if n in memo.keys():
        return memo[n]
    elif n == 0:
        return 0
    elif n <= 2:
        return 1
    else:
        result = fib(n - 1, memo) + fib(n - 2, memo)
        memo[n] = result
        return result


@lru_cache
def fib2(n):
    if n == 0:
        return 0
    elif n <= 2:
        return 1
    else:
        return fib(n - 1) + fib(n - 2)


print(fib.__defaults__)
for i in range(10):
    print(f"fib({i})={fib(i)}")
print(fib.__defaults__)

print("with lru_cache")
for i in range(100):
    print(f"fib2({i})={fib2(i)}")
print(fib2.cache_info())

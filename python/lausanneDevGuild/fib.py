def fib(n):
    if n == 0:
        return 0
    if n == 1:
        return 1
    grandparent = 0
    parent = 1
    current = 0
    for i in range(n - 1):
        current = parent + grandparent
        grandparent = parent
        parent = current
    return current


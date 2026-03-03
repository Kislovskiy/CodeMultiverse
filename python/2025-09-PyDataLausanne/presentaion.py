import memory_graph as mg

def func1(a, b):
    a = 'new-value'
    b = b + 1
    return a, b 

x, y = 'old-value', 99
func1(x, y)
print("finished")

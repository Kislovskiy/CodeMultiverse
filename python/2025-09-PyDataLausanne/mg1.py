import memory_graph as mg

tuple1 = (100, 200)
tuple2 = tuple1
tuple2 += (300,)

list1 = [100, 200]
list2 = list1
list2 += [300]

print(tuple1[-1], list1[-1])

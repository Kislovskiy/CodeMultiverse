
import memory_graph as mg
from matplotlib 

fig, ax 
class Object:
    def __init__(self):
        self.id = id(self)
        print(f"Object id: {self.id}")

mg.show(locals())


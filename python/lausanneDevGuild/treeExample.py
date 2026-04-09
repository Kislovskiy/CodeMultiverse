class Node:
    def __init__(self, value, left, right):
        self.value = value
        self.left = left
        self.right = right
    
    def __str__(self):
        return f"Node({self.value}, {self.left}, {self.right})"
    
    def __repr__(self):
        return f"Node({self.value}, {self.left}, {self.right})"

if __name__ == "__main__":
    a = Node(1, None, None)
    print(a)

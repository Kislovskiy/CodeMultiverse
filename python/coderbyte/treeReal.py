from typing import List, Optional, Dict
from copy import deepcopy

class Node:
    def __init__(self, value: int, left: Optional["Node"] = None, right: Optional["Node"] = None):
        self.value = value
        self.left = left
        self.right = right

    def to_dict(self) -> Dict:
        return {
            "value": self.value,
            "left": self.left.to_dict() if self.left else None,
            "right": self.right.to_dict() if self.right else None
        }

class InvalidTreeError(Exception):
    pass

def create_binary_tree(array: List[int]) -> Node:
    if len(array) == 0 or array[0] == -1:
        raise InvalidTreeError("The root node is missing.")
    def create_node(idx: int) -> Node | None:
        if idx >= len(array) or array[idx] == -1:
            return None
        node = Node(array[idx])
        node.left = create_node(2 * idx + 1)
        node.right = create_node(2 * idx + 2)
        return node
    return create_node(0)

    
def generate_permutations(node: Node) -> List[Node]:
    if node is None:
        return [None]

    if node.left == None and node.right == None:
        return [node]
    
    output = []

    left_permutations = generate_permutations(node.left)
    right_permutations = generate_permutations(node.right)

    for left in left_permutations:
        for right in right_permutations:
            node1 = Node(node.value)
            node1.left = deepcopy(left)
            node1.right = deepcopy(right)
            output.append(node1)

            node2 = Node(node.value)
            node2.left = deepcopy(right)
            node2.right = deepcopy(left)
            output.append(node2)

    return output

print(create_binary_tree([3, 7, 9, 12, 17, 16, 15]).to_dict())
for i, permutation in enumerate(generate_permutations(create_binary_tree([3, 7, 9, 12, 17, 16, 15]))):
    print(f"{i=} {permutation.to_dict()}")

print(create_binary_tree([1, -1, 2]).to_dict())
# print(create_binary_tree([]))
# print(create_binary_tree([-1, 2, 3]))

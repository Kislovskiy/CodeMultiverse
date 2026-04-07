from copy import deepcopy


class Node:
    def __init__(self, value, left: "Node | None" = None, right: "Node | None" = None):
        self.value = value
        self.left: "Node | None" = left
        self.right: "Node | None" = right

    def __repr__(self):
        return f"Node(value={self.value}, left={self.left}, right={self.right})"

    def to_dict(self) -> dict:
        return {
            "value": self.value,
            "left": self.left.to_dict() if self.left else None,
            "right": self.right.to_dict() if self.right else None,
        }


class InvalidTreeError(Exception):
    pass


def create_binary_tree(array: list[int]) -> Node:
    if len(array) == 0 or array[0] == -1:
        raise InvalidTreeError("The root node is missing.")

    def create_node(idx: int) -> Node | None:
        if idx >= len(array) or array[idx] == -1:
            return None
        node = Node(array[idx], create_node(2 * idx + 1), create_node(2 * idx + 2))
        return node

    node = create_node(0)
    assert node is not None
    return node


def generate_permutations(node: Node | None) -> list[Node | None]:
    r"""Generate all structural permutations of a binary tree.

    At every internal node there is a binary choice: keep or swap its
    children. This function explores all such choices independently,
    producing 2^n permutations for a tree with n internal nodes.

    Strategy (divide and conquer):
        1. Recursively collect all permutations of the left subtree.
        2. Recursively collect all permutations of the right subtree.
        3. For every (left, right) pair in their cartesian product,
            emit two nodes: one with original order, one with swapped order.

    Base cases:
        - None        → [None]          (empty branch, one option: nothing)
        - Leaf node   → [Node(value)]   (no children to swap, one option)

    Example:
        Tree with 3 internal nodes → 2^3 = 8 permutations.

            3               3
          /   \    ...     / \
         7     9          9   7   (swapped at root)
        / \   /  \
       12 17 16  15

    Args:
        node: Root of the (sub)tree to permute.

    Returns:
        List of all structurally distinct trees produced by
        independently swapping children at every node.
    """
    if node is None:
        return [None]

    if node.left is None and node.right is None:
        return [Node(node.value)]

    output = []

    left_permutations = generate_permutations(node.left)
    right_permutations = generate_permutations(node.right)

    for left in left_permutations:
        for right in right_permutations:
            left_copy = deepcopy(left)
            right_copy = deepcopy(right)
            node1 = Node(node.value, left=left_copy, right=right_copy)
            node2 = Node(node.value, left=right_copy, right=left_copy)
            output.append(node1)
            output.append(node2)

    return output


if __name__ == "__main__":
    print(create_binary_tree([3, 7, 9, 12, 17, 16, 15]).to_dict())
    for i, permutation in enumerate(
        generate_permutations(create_binary_tree([3, 7, 9, 12, 17, 16, 15]))
    ):
        if permutation is not None:
            print(f"{i=} {permutation.to_dict()}")

    print(create_binary_tree([1, -1, 2]).to_dict())
    # print(create_binary_tree([]))
    # print(create_binary_tree([-1, 2, 3]))

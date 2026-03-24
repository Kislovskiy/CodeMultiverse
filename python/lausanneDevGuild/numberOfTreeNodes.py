"""
Count nodes in a complete binary tree in O(log²n).

A complete binary tree fills all levels left-to-right. This allows
binary search on the last level: the index of a node on the last
level IS its traversal path in binary — each bit (MSB → LSB) encodes
a left (0) or right (1) turn from the root.
"""


class Node:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

    def __str__(self):
        return f"Node({self.val=} {self.left=}, {self.right=})"

    def __repr__(self):
        return f"Node({self.val}, {self.left}, {self.right})"


def get_height(node):
    """Return tree height by walking leftmost path (O(log n))."""
    height = 1
    while node.left is not None:
        height += 1
        node = node.left
    return height


def get_number_of_nodes_except_last(node):
    """Return total nodes on all levels except the last.

    A complete tree of height h has levels 0..h-2 fully filled:
      level 0: 1 node  (2^0)
      level 1: 2 nodes (2^1)
      ...
      level h-2: 2^(h-2) nodes

    Sum = 2^0 + 2^1 + ... + 2^(h-2) = 2^(h-1) - 1

    Using left shift: (1 << (h-1)) - 1  is equivalent to 2^(h-1) - 1
    """
    # accumulator = 0
    # for level in range(get_height(node) - 1):
    #     accumulator += 2 ** (level)
    # return accumulator
    return (0b1 << (get_height(node) - 1)) - 1


def node_exist(node, target: int, h: int) -> bool:
    """Check whether the last-level node at index `target` exists.

    The traversal path from root to any last-level node is encoded
    in the binary representation of `target`. For a tree of height h,
    the path has h-1 steps (bits h-2 down to 0):

      bit = 0  →  go left
      bit = 1  →  go right

    Example (h=4, target=5 → binary 101):
      i=2: (5 >> 2) & 1 = 1  → right
      i=1: (5 >> 1) & 1 = 0  → left
      i=0: (5 >> 0) & 1 = 1  → right
    """
    for i in range(h - 2, -1, -1):
        if (target >> i) & 1 == 0:
            if node.left is None:
                return False
            node = node.left
        else:
            if node.right is None:
                return False
            node = node.right
    return True


def count_nodes(root) -> int:
    """Count nodes in a complete binary tree in O(log²n).

    The last level has at most 2^(h-1) nodes, indexed 0 to 2^(h-1)-1.
    Binary search finds the rightmost existing node: the answer is
    all nodes on full levels + (binary search result).
    """
    h = get_height(root)
    if h == 1:
        return 1
    left, right = 0, 2 ** (h - 1) - 1
    while left <= right:
        mid = (left + right) >> 1
        if node_exist(root, mid, h):
            left = mid + 1
        else:
            right = mid - 1
    return get_number_of_nodes_except_last(root) + left


def build_complete_tree(values):
    """Build a complete binary tree from left-to-right level order values."""
    if not values:
        return None
    nodes = [Node(v) for v in values]
    for i, node in enumerate(nodes):
        if (j := 2 * i + 1) < len(nodes):
            node.left = nodes[j]
        if (j := 2 * i + 2) < len(nodes):
            node.right = nodes[j]
    return nodes[0]


if __name__ == "__main__":
    for n in range(1, 12):
        root = build_complete_tree(range(1, n + 1))
        result = count_nodes(root)
        print(f"{n=}: {result=}")

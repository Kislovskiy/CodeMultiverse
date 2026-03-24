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
    height = 1
    while node.left is not None:
        height += 1
        node = node.left
    return height


def get_number_of_nodes_except_last(node):
    accumulator = 0
    for level in range(get_height(node) - 1):
        accumulator += 2 ** (level)
    return accumulator


def node_exist(node, path):
    exist = True
    for leaf in path:
        if leaf == "0":
            if node.left is None:
                exist = False
            else:
                node = node.left
        else:
            if node.right is None:
                exist = False
            else:
                node = node.right
    return exist


def count_nodes(root):
    h = get_height(root)
    if h == 1:
        return 1
    left = 0
    right = 2 ** (h - 1) - 1
    mid = 0
    while left <= right:
        mid = (left + right) // 2
        binary_mid = bin(mid)[2:].zfill(h - 1)
        if node_exist(root, binary_mid):
            left = mid + 1
        else:
            right = mid - 1
    total_number = get_number_of_nodes_except_last(root) + left
    return total_number


def build_complete_tree(values):
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

import pytest


def test_variables_are_labels():
    """The best metaphor to think of variables in Python is that they are labels with names attached to objects.
    ┌───┐┌───┐
    │ a ││ b │──┐
    └───┘└───┘  │
    │ [1, 2, 3] │
    └───────────┘
    """
    a = list([1, 2, 3])
    b = a
    a.append(4)
    assert a == [1, 2, 3, 4]
    assert b == [1, 2, 3, 4]


class Object:
    def __init__(self):
        self.id = id(self)
        print(f"Object id: {self.id}")


def test_bounding_to_object():
    """To understand the assignment read the rghthand side first: that's where the object is created or retieved.
    After that, the variable on the left is bound to the object like a label stuck to it.
    """

    obj1 = Object()
    obj2 = Object()

    assert obj1.id != obj2.id


def test_that_object_instantiated_before_multiplication(capsys):
    """Here we use pytest capsys to enable capturing of writes to sys.stdout (print)
    We demonstrate that object instantiation happens before multiplication
    """

    with pytest.raises(TypeError):
        Object() * 10

    capture_object_creation_1 = capsys.readouterr()
    assert capture_object_creation_1.out.startswith("Object id:")

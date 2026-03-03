import pytest


def test_variables_are_labels():
    """The best metaphor to think of variables in Python is that they are labels with names attached to objects.
    ┌───┐┌───┐
    │ a ││ b │──┐
    └───┘└───┘  │
    │ [1, 2, 3] │
    └───────────┘
    More about this in Python Data Model: https://docs.python.org/3/reference/datamodel.html#objects-values-and-types
    """
    a = list([1, 2, 3])  #  = is an assignment statement to create a new variable that holds a reference to a list.
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


def f(x: int) -> int:
    print(f"id(x) = {id(x)}")
    x = x + 5  # assignment statement to create a new variable that holds a reference to an integer number
    print(f"id(x) = {id(x)}")


def test_immutable_variables_are_not_affected():
    """In Python arguments to a function are passed by value, where values are references to the objects.
    num -> 5         (num points to int object with value 5, it's a sticker note with name "num")
    num -> 5 <- x    (inside f(x) the local variable x points to the object with value 5,
                     we have two sticker notes "x" and "num" pointing to the same object
                     The function's formal parameter becomes an alias of the global variable)
    num -> 5
    x -> 10          int object is immutable, so expression (x + 5) creates a new int object and "x"
                     is a new label that points to the int object with value 10

    num -> 5        when function terminates the local variables are removed.
    """
    num = 5
    num_id_before = id(num)
    f(num)
    num_id_after = id(num)
    assert num_id_before == num_id_after


def test_variables_are_labels_pointing_to_the_same_object():
    """It's interesting that for immutable types in Python we can put as many "labels" as we want and they
    will point to the same object.

    Whenever you assign an existing variable to a new one, you create an ALIAS for the original variable.
    Variable aliases share a reference to the same memory address, pointing to the same object or piece of data
    """
    num = 5
    another_num = 5
    assert id(num) == id(another_num)

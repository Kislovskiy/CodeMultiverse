# Define a funciton that take any number of integers as positional arguments.
# It returns a tuple with 2 elements, the smallest and largest itegers from the input arguments.
# We can assume that we received at least one argument


def smallest_and_largest(*args):
    smallest = args[0]
    largest = args[0]
    for element in args:
        if element < smallest:
            smallest = element
        if element > largest:
            largest = element
    return smallest, largest


print(smallest_and_largest(1, 2, 3, 4, 5))
print(smallest_and_largest(23, 23, 1, 299))


# Define a funciton that takes one "regular" argument, and then any number of keyword arguments, which will have funcitons as values.
# Each function is applied to that value, giving us a new dict back as a return value.


def apply_function(value, **kwargs):
    output = {}

    for key, function in kwargs.items():
        output[key] = function(value)

    return output


print(apply_function("aBcD", lower=str.lower, upper=str.upper, len=len))


def apply_function_comprehension(value, **kwargs):
    return {key: function(value) for key, function in kwargs.items()}


print(apply_function_comprehension("aBcD", lower=str.lower, upper=str.upper, len=len))

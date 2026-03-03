# Have the function FindIntersection(strArr) read the array of strings stored in strArr which will contain 2 elements:
# the first element will represent a list of comma-separated numbers sorted in ascending order, the second element will
# represent a second list of comma-separated numbers (also sorted). Your goal is to return a comma-separated string
# containing the numbers that occur in elements of strArr in sorted order. If there is no intersection, return the string false.


def FindIntersection(strArr):
    listA = get_list_from_string(strArr[0])
    listB = get_list_from_string(strArr[1])

    intersection = []
    i = 0
    j = 0
    while i < len(listA) and j < len(listB):
        if listA[i] == listB[j]:
            if intersection == [] or listA[i] != intersection[-1]:
                intersection.append(listA[i])
            i = i + 1
            j = j + 1
        elif listA[i] < listB[j]:
            i = i + 1
        else:
            j = j + 1

    if len(intersection) == 0:
        return "false"
    else:
        return ",".join(map(str, intersection))


# keep this function call here


def get_list_from_string(input_string):
    return [int(x.strip()) for x in input_string.split(",")]


assert get_list_from_string("1, 3, 4, 7, 13") == [1, 3, 4, 7, 13]


assert FindIntersection(["1, 3, 4, 7, 13", "1, 2, 4, 13, 15"]) == "1,4,13"
assert FindIntersection(["1, 3, 9, 10, 17, 18", "1, 4, 9, 10"]) == "1,9,10"
assert FindIntersection(["1, 3, 9, 10, 17, 18", "123"]) == "false"
assert FindIntersection(["1, 1, 2, 4", "1, 1, 3, 4"]) == "1,4"

print(FindIntersection(["1, 1, 2, 4", "4, 1, 3, 1"]))

# print(FindIntersection(input()))

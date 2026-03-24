def MinWindowSubstring(strArr):
    """
    return smallest substring from N that has all chars from K 

    N              K
    ["aaabaaddae", "aed"] => dae
    ["aabdccdbcacd", "aad"] => aabd
    """
    def contains(string: str, letters: str) -> bool:
        if string == "":
            return False
        stack = [x for x in letters]
        # print(f"{stack=}")
        if len(stack) > len(string):
            return False
        else:                 
            for letter in string:
                if letter in stack:
                    # print(f"{letter=}, {stack=}")
                    if len(stack) > 0:
                        stack.remove(letter)
                    else:
                        return True
            if len(stack) == 0:
                return True
            else:
                return False

    N = strArr[0]
    K = strArr[1]
    i = 0
    j = 0
    min_string = N

    while j <= len(N):
        tmp = N[i:j]
        # print(f"{i=} {j=} {tmp=}")
        if contains(tmp, K):
            if len(tmp) < len(min_string):
                min_string = tmp
            i = i + 1
        else:
            j = j + 1

    return min_string

# keep this function call here 
print(MinWindowSubstring(["ahffaksfajeeubsne", "jefaa"]))
print(MinWindowSubstring(["aaffhkksemckelloe", "fhea"]))

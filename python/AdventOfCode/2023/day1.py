NAME_TO_DIGIT = {
    "one": "1",
    "two": "2",
    "three": "3",
    "four": "4",
    "five": "5",
    "six": "6",
    "seven": "7",
    "eight": "8",
    "nine": "9",
}


def extract_digits(line: str) -> str:
    return "".join(i for i in line if i.isdigit())


def extract_complex_digits(line: str) -> str:
    digitalised = ""
    for i in range(len(line)):
        if line[i].isdigit():
            digitalised += line[i]
        else:
            for key in NAME_TO_DIGIT.keys():
                if line[i:].startswith(key):
                    digitalised += str(NAME_TO_DIGIT[key])

    return extract_digits(digitalised)


def get_first_and_last(string_with_digits: str) -> int:
    if string_with_digits:
        return int(string_with_digits[0]) * 10 + int(string_with_digits[-1])
    else:
        return 0


with open("resources/day1.txt") as f:
    print(
        f"Part 1: {sum(get_first_and_last(extract_digits(raw)) for raw in f.readlines())}"
    )

with open("resources/day1.txt") as f:
    print(
        f"Part 2: {sum(get_first_and_last(extract_complex_digits(raw)) for raw in f.readlines())}"
    )

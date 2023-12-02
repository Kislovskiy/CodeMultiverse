import re


def valid_game(inp: str) -> bool:
    max_red = 12
    max_green = 13
    max_blue = 14

    validity = []
    (_, games) = inp.split(":")
    for game in games.split(";"):
        for cube in game.strip().split(","):
            (n, color) = cube.strip().split(" ")
            match color:
                case "blue":
                    validity.append(int(n) <= max_blue)
                case "green":
                    validity.append(int(n) <= max_green)
                case "red":
                    validity.append(int(n) <= max_red)
    return False not in validity


def get_game_id(inp: str) -> int:
    return int(re.compile(r"Game (\d+):").search(inp).group(1))


def power_set_of_cubes(inp: str) -> int:
    (blues, greens, reds) = ([], [], [])
    (_, games) = inp.split(":")
    for game in games.split(";"):
        for cube in game.strip().split(","):
            (n, color) = cube.strip().split(" ")
            match color:
                case "blue":
                    blues.append(int(n))
                case "green":
                    greens.append(int(n))
                case "red":
                    reds.append(int(n))
    return max(blues) * max(reds) * max(greens)


with open("resources/day2.txt") as f:
    print(f"Part 1: {sum([get_game_id(line) for line in f.readlines() if valid_game(line)])}")

with open("resources/day2.txt") as f:
    print(f"Part 2: {sum([power_set_of_cubes(line) for line in f.readlines()])}")

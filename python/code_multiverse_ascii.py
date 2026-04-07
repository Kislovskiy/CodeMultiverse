# /// script
# requires-python = ">=3.13"
# dependencies = [
#     "art>=6.5",
# ]
# ///

from art import text2art

def main() -> None:
    # Art = text2art("Code Multiverse", "chunky")
    Art = text2art("Code Multiverse", "crawford")
    # Art = text2art("Code Multiverse", "pawp")
    # Art = text2art("Code Multiverse", "small")
    print(Art)


if __name__ == "__main__":
    main()

"""
Saas-Fee Descent Simulator

Simulates a skier descending from the Allalinhorn glacier (3900m)
to Saas-Fee valley (1600m). Each cycle descends 400m then regains
200m via an uphill traverse — net 200m per cycle.

The final leg is capped to stop exactly at 1600m, with no traverse.
Total descent (4300m) exceeds the altitude difference (2300m)
due to the uphill traverses.
"""

if __name__ == "__main__":
    height = 3900
    descent = 0
    while height > 1600:
        step_down = min(400, height - 1600)
        height -= step_down
        descent += step_down
        if height > 1600:
            height += 200
        print(f"{height=}, {descent=}")

    print(f"Total descent = {descent}")

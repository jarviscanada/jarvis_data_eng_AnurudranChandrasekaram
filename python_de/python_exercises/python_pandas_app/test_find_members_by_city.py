import json
import pandas as pd

from app_find_memebers_csv_json import find_members_by_city
def test_find_members_by_city():
    find_members_by_city("test/test_members.csv", "Boston","test/test.json")
    actual_df = pd.read_json("test/test.json")
    expected_df = pd.read_json("test/test2.json")
    assert(expected_df.equals(actual_df))


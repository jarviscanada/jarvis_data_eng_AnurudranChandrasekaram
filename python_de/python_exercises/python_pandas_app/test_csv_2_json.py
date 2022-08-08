import json
from app_convert_csv_to_json import csv_2_json

def test_csv_2_json():
   csv_2_json("test/test_members.csv", "test/test.json")
   file = open("test/test.json")
   data_actual = json.loads(file.read())
   file.close()
   file = open("test/expected_test.json")
   data_expected = json.loads(file.read())
   file.close()
   assert(data_actual==data_expected)
   

import json
import sys
import pandas as pd

def find_members_by_city(csv_file, city_name, output_file):
    #members_df = read csv_file into a dataframe
    #members_city_df = find all memebrs from city_name if city_name is in the address field
    #export members_city_df to a output_file.json
    members_df = pd.read_csv(csv_file)
    members_city_df = members_df.loc[members_df['address'].str.contains(city_name)]
    members_city_df.to_json(output_file)


if __name__ == '__main__':
    find_members_by_city(sys.argv[1], sys.argv[2], sys.argv[3])
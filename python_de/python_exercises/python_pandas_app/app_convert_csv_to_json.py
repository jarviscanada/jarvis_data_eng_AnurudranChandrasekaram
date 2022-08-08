from asyncore import write
import csv
import sys
import json

def csv_2_json(input_csv, output_json):
    #read CSV file into a list of dicts
    #write the list of dicts into an output_json file
    data = []
    with open(input_csv) as csvf:
        csvReader = csv.DictReader(csvf)
        for rows in csvReader:
            data.append(rows)
    with open(output_json, 'w') as jsonf:
        jsonf.write(json.dumps(data, indent=4))


if __name__ == '__main__':
    csv_2_json(sys.argv[1], sys.argv[2])
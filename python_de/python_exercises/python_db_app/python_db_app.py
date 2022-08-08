from sqlite3 import Cursor
import psycopg2
import sys
import csv

def find_company_info(host, port, username, password, database, table, out_file):
    #create database connection
    #rows = execute `SELECT * FROM table`
    #export rows into an out_file in CSV format
    conn = psycopg2.connect(database = database, user = username, password = password, host = host, port = port)
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM " + table)
    rows = cursor.fetchall()
    # open the file in the write mode
    f = open(out_file, 'w')

    # create the csv writer
    writer = csv.writer(f)
    for row in rows:
        writer.writerow(row)
    cursor.close()
    f.close()

if __name__ == '__main__':
    find_company_info(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6], sys.argv[7])
import requests
import sys
import json


def find_company_info(ticker):
    #use `ticker` endpoint from https://rapidapi.com/Glavier/api/google-finance4/
	#use python `requests` lib (e.g. import requests)
    #construct a HTTP GET request
    #Get response body into a dict
    #print the following fields `info.title`, `about.ceo`, `price.previous_close`
    url = "https://google-finance4.p.rapidapi.com/ticker/"

    querystring = {"t":ticker,"hl":"en","gl":"US"}

    headers = {
	    "X-RapidAPI-Key": "cca9cdc8c7msh68d622433b08149p179749jsncec439a31e7b",
	    "X-RapidAPI-Host": "google-finance4.p.rapidapi.com"
    }

    response = requests.request("GET", url, headers=headers, params=querystring)
    data = json.loads(response.text)
    print(data["info"]["title"])
    print(data["about"]["ceo"])
    print(data["price"]["previous_close"])


if __name__ == '__main__':
    find_company_info(sys.argv[1])
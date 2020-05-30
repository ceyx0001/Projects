from selectorlib import Extractor
import requests 
import json 
from time import sleep
import tkinter as tk 

extractor = Extractor.from_yaml_file('template.yml')

def scrape(url):    
    headers = {
        'authority': 'old.reddit.com',
        'pragma': 'no-cache',
        'cache-control': 'no-cache',
        'dnt': '1',
        'upgrade-insecure-requests': '1',
        'user-agent': 'Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36',
        'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        'accept-language': 'en-GB,en-US;q=0.9,en;q=0.8',
    }

    # Download the page using requests
    print("Downloading %s"%url)
    r = requests.get(url, headers=headers)
    # Simple check to check if page was blocked (Usually 503)
    if r.status_code > 500:
        print("Page %s must have been blocked by website as the status code was %d"%(url,r.status_code))
        return None
    # Pass the HTML of the page and create 
    return extractor.extract(r.text)

# product_data = []
with open("urls.txt",'r') as urllist, open('output.jsonl','w') as outfile:
    for url in urllist.read().splitlines():
        data = scrape(url) 
        if data:
            json.dump(data,outfile, indent=2)
            # sleep(5)
    
import requests
import logging

from typing import List
from models.country import Country

# Enable logging
logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', level=logging.INFO)
logger = logging.getLogger(__name__)

# Define API endpoints
COUNTRY_LIST_API = 'http://localhost:8080/countries'  # replace with actual URL
COUNTRY_DETAIL_API = 'http://localhost:8080/countries/{name}'  # replace with actual URL
AUTH_HEADERS = {'X-API-Key': '1abfb4e8-8c94-4e3f-a4ef-0c6dcb377f4b'}

class CountryService:

    def __init__(self):
        all_countries = self.get_countries(0, 1000)
        self.total_countries = len(all_countries)
        print(self.total_countries)

    def get_country(self, country_name: str) -> Country:
        if country_name != None and country_name != "":
            response = requests.get(COUNTRY_DETAIL_API.format(name = country_name), headers=AUTH_HEADERS)
            if response.status_code == 200:
                country_details = response.json()
                logger.info("Country {name} fetched.".format(name = country_name))
                return Country(country_details)
            else:
                logger.error("Request to fetch country {name} failed!".format(name = country_name))
                if response.status_code == 404:
                    return None
                else:
                    raise ConnectionRefusedError()
        else:
            raise AttributeError()

    def get_countries(self, page_num: int = 0, page_size: int = 10) -> List[str]:
        response = requests.get(COUNTRY_LIST_API, params={"pageNum": page_num, "pageSize": page_size}, headers=AUTH_HEADERS)
        if response.status_code == 200:
            country_list = response.json()["countries"]
            logger.info("Country names fetched!")
            return [country["name"] for country in country_list]
        else:
            logger.error(f"Request to fetch countries failed with status code = {response.text}")
            raise TimeoutError()

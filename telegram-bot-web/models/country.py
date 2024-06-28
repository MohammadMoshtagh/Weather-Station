from models.currency import Currency


class Country:

    def __init__(self, name: str, capital: str, iso2: str, population, pop_growth: float, currency: Currency):
        self.name = name
        self.capital = capital
        self.iso2 = iso2
        self.population = population
        self.pop_growth = pop_growth
        self.currency = currency

    def __init__(self, json_string):
        self.from_json(json_string)

    def from_json(self, json_obj):
        self.__dict__ = json_obj

    def __str__(self) -> str:
        return f'''{{
    'name': {self.name},
    'capital': {self.capital},
    'iso2': {self.iso2},
    'population': {self.population},
    'pop_growth': {self.pop_growth},
    'currency': {self.currency}
}}'''

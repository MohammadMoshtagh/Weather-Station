package edu.sharif.webproject.weather.country;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import edu.sharif.webproject.weather.country.entity.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryParserService {

    private final Gson gson;

    public CountryNamesResponse parseCountriesNames(String responseBody, int pageNum, int pageSize) {
        var jsonObject = parseToJsonObject(responseBody);
        var countryNames = new CountryNamesResponse();
        var countryArray = jsonObject.get("data").getAsJsonArray();

        for (int i = 0; i < countryArray.size(); ++i) {
            if (i < pageSize * pageNum || i >= (pageNum + 1) * pageSize) {
                continue;
            }
            var countryName = new CountryNameDto(countryArray.get(i).getAsJsonObject().get("country").getAsString());
            countryNames.addCountryName(countryName);
        }

        return countryNames;
    }

    public CountryDto parseCountry(String responseBody) throws JsonParseException {
        var jsonArray = parseToJsonArray(responseBody);
        if (jsonArray.isEmpty()) {
            throw new JsonParseException("Can not parse response body as json");
        }
        var countryData = jsonArray.get(0).getAsJsonObject();

        var countryName = countryData.get("name").getAsString();
        var capitalName = countryData.get("capital").getAsString();
        var iso2 = countryData.get("iso2").getAsString();
        var population = countryData.get("population").getAsLong();
        var pop_growth = countryData.get("pop_growth").getAsFloat();

        var currencyJson = countryData.get("currency").getAsJsonObject();
        var currency = new CurrencyDto(currencyJson.get("code").getAsString(), currencyJson.get("name").getAsString());

        return new CountryDto(countryName, capitalName, iso2, population, pop_growth, currency);
    }

    public CountryWeatherDto parseCityWeather(String responseBody, String countryName, String capital) {
        var jsonObject = parseToJsonObject(responseBody);

        var windSpeed = jsonObject.get("wind_speed").getAsFloat();
        var windDegrees = jsonObject.get("wind_degrees").getAsFloat();
        var temp = jsonObject.get("temp").getAsFloat();
        var humidity = jsonObject.get("humidity").getAsFloat();

        return new CountryWeatherDto(countryName, capital, windSpeed, windDegrees, temp, humidity);
    }

    private JsonObject parseToJsonObject(String responseBody) {
        return gson.fromJson(responseBody, JsonObject.class);
    }

    private JsonArray parseToJsonArray(String responseBody) {
        return gson.fromJson(responseBody, JsonArray.class);
    }
}

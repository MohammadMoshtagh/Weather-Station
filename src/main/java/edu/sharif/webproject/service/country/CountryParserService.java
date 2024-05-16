package edu.sharif.webproject.service.country;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.sharif.webproject.model.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@Service
public class CountryParserService {

    private final Gson gson;

    public CountryParserService() {
        gson = new Gson();
    }

    public CountryNamesDto parseCountriesNames(String responseBody) {
        var jsonObject = parseToJsonObject(responseBody);
        var countryNames = new CountryNamesDto();

        for (var element : jsonObject.get("data").getAsJsonArray()) {
            var countryName = new CountryNameDto(element.getAsJsonObject().get("country").getAsString());
            countryNames.addCountryName(countryName);
        }

        return countryNames;
    }

    public CountryDto parseCountry(String responseBody) {
        var jsonArray = parseToJsonArray(responseBody);
        var countryData = jsonArray.get(0).getAsJsonObject();

        var countryName = countryData.get("name").getAsString();
        var capitalName = countryData.get("capital").getAsString();
        var iso2 = countryData.get("iso2").getAsString();
        var population = countryData.get("population").getAsLong();
        var pop_growth = countryData.get("pop_growth").getAsFloat();

        var currencyJson = countryData.get("currency").getAsJsonObject();
        var currency = new CurrencyDto(currencyJson.get("code").getAsString(),
                currencyJson.get("name").getAsString());

        return new CountryDto(
                countryName,
                capitalName,
                iso2,
                population,
                pop_growth,
                currency);
    }

    public CountryWeatherDto parseCityWeather(String responseBody, String countryName, String capital) {
        var jsonObject = parseToJsonObject(responseBody);

        var windSpeed = jsonObject.get("wind_speed").getAsFloat();
        var windDegrees = jsonObject.get("wind_degrees").getAsFloat();
        var temp = jsonObject.get("temp").getAsFloat();
        var humidity = jsonObject.get("humidity").getAsFloat();

        return new CountryWeatherDto(
                countryName,
                capital,
                windSpeed,
                windDegrees,
                temp,
                humidity);
    }

    private JsonObject parseToJsonObject(String responseBody) {
        return gson.fromJson(responseBody, JsonObject.class);
    }

    private JsonArray parseToJsonArray(String responseBody) {
        return gson.fromJson(responseBody, JsonArray.class);
    }
}

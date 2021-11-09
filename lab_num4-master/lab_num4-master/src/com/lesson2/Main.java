package com.lesson2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.lesson2.Util.Requests;

public class Main {
    public static void main(String[] args) {
        Requests requests = new Requests();
        Requests.url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        requests.start();

        System.out.println("[Waiting for data...]");
        String json = requests.jsonIn;

        Object obj = null;
        try {
            obj = new JSONParser().parse(json);
        } catch (org.json.simple.parser.ParseException ex) {
            ex.printStackTrace();
        }

        JSONArray jsonArray = (JSONArray) obj;
        Currencies currencies = new Currencies();

        for (Object jsonObject : jsonArray) {
            JSONObject current = (JSONObject) jsonObject;
            String ccy = (String) current.get("ccy");
            String base_ccy = (String) current.get("base_ccy");
            double buy = Double.parseDouble((String) current.get("buy"));
            double sale = Double.parseDouble((String) current.get("sale"));
            Currency currency = new Currency(ccy, base_ccy, buy, sale);
            currencies.addCurrency(currency);
        }

        System.out.println("======NO SORT======");
        System.out.println(currencies);

        System.out.println("======DIFFERENT SORTS======");
        currencies.getCurrencies().sort(Currency.byNameAsc);
        System.out.println(currencies);

        currencies.getCurrencies().sort(Currency.byNameDesc);
        System.out.println(currencies);

        currencies.getCurrencies().sort(Currency.byValueAsc);
        System.out.println(currencies);

        currencies.getCurrencies().sort(Currency.byValueDesc);
        System.out.println(currencies);
    }
}

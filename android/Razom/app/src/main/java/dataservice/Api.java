package dataservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit.RestAdapter;

/**
 * Created by ykomasyuk on 17.05.2014.
 */
public class Api {

    public static DataService DataService;
    private static RestAdapter RestAdapter;

    public static void InitDataService() {
        RestAdapter = new RestAdapter.Builder()
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .setEndpoint("http://razom.batros.in.ua")
                .build();
        DataService = RestAdapter.create(DataService.class);
    }

}

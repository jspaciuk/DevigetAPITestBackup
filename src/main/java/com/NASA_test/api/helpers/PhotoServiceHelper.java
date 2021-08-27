package com.NASA_test.api.helpers;

import com.NASA_test.api.model.ResponseNASA;
import com.NASA_test.api.utils.ConfigManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static com.NASA_test.api.constants.Endpoints.GET_NASA_PHOTOS_CURIOSITY;

public class PhotoServiceHelper {

    private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
    private static final String API_KEY_NAME = ConfigManager.getInstance().getString("api_key_name");
    private static final String API_KEY_VALUE = ConfigManager.getInstance().getString("api_key_value");
    private static final String JSON_PATH_SOL_NAME = "findAll{it.sol==1000}";
    private static final String JSON_PATH_EARTH_VALUE = "findAll{it.earth_date=='2015-05-30'}";

    public enum cameras {CHEMCAM, FHAZ, MAHLI, MARDI, MAST, NAVCAM, RHAZ}

    public PhotoServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    /**
     * Performs a GET to GET_NASA_PHOTOS_CURIOSITY endpoint and
     * returns a list with all required amount of photos by dateType
     *
     * @param amountPhotos
     * @param dateType
     * @param dateValue
     * @return List<Map < String, String>>
     * @throws JsonProcessingException
     */
    public List<Map<String, String>> getPhotoNASAWithPageFilter(int amountPhotos, String dateType, String dateValue) throws JsonProcessingException {

        Response response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .queryParam(dateType, dateValue).queryParam(API_KEY_NAME, API_KEY_VALUE).queryParam("page", 1)
                .get(GET_NASA_PHOTOS_CURIOSITY).andReturn();

        Assert.assertEquals(response.statusCode(), 200);
        ResponseNASA responseNASA = response.getBody().as(ResponseNASA.class);
        Assert.assertNotNull(responseNASA, "Photo is not null");
        ObjectMapper mapper = new ObjectMapper();
        String arrayToJson = mapper.writeValueAsString(responseNASA.photos);
        JsonPath jsonPath = new JsonPath(arrayToJson);
        List<Map<String, String>> allPhotosCuriosity = jsonPath.getList(getJsonPathQueryParamsByDateType(dateType));

        return allPhotosCuriosity.subList(0, amountPhotos); // return first 10 photos;

    }

    /**
     * Performs a GET to GET_NASA_PHOTOS_CURIOSITY endpoint and
     * returns a list with all total photos by dateType
     *
     * @param dateType
     * @param dateValue
     * @return List<Map < String, String>>
     * @throws JsonProcessingException
     */
    public List<Map<String, String>> getPhotoNASA(String dateType, String dateValue) throws JsonProcessingException {

        Response response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .queryParam(dateType, dateValue).queryParam(API_KEY_NAME, API_KEY_VALUE)
                .get(GET_NASA_PHOTOS_CURIOSITY).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Assert.assertEquals(response.statusCode(), 200);
        ResponseNASA responseNASA = response.getBody().as(ResponseNASA.class);
        Assert.assertNotNull(responseNASA, "Photo is not null");
        String arrayToJson = mapper.writeValueAsString(responseNASA.photos);
        JsonPath jsonPath = new JsonPath(arrayToJson);
        List<Map<String, String>> allPhotosCuriosity = jsonPath.getList(getJsonPathQueryParamsByDateType(dateType));

        return allPhotosCuriosity; // return all photos;

    }

    /**
     * This method extracts JsonPath QueryParams from a const By DateType and returns it.
     *
     * @param dateType
     * @return String
     */
    public String getJsonPathQueryParamsByDateType(String dateType) {
        String jsonPath;
        if (dateType.equals("sols"))
            jsonPath = JSON_PATH_SOL_NAME;
        else
            jsonPath = JSON_PATH_EARTH_VALUE;

        return jsonPath;
    }
}

package com.NASA_test.api.tests;

import com.NASA_test.api.helpers.PhotoServiceHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.var;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.NASA_test.api.constants.Endpoints.GET_NASA_PHOTOS_CURIOSITY;
import static com.NASA_test.api.helpers.PhotoServiceHelper.cameras;
import static io.restassured.RestAssured.given;

public class Test_NASA_deviget {

    public PhotoServiceHelper photoServiceHelper;

    @BeforeClass
    public void init() {

        photoServiceHelper = new PhotoServiceHelper();
    }

    @Test(testName = "Retrieve the first 10 Mars photos made by 'Curiosity' on 1000 Martian sol.", priority = 1)
    public void RetrieveTheFirst10MarsPhotosMadeByCuriosityOn1000MartianSol() throws JsonProcessingException {

        List<Map<String, String>> first10photosCuriosity = photoServiceHelper.getPhotoNASAWithPageFilter(10, "sol", "1000");
        System.out.println(first10photosCuriosity); // I print first 10 Sol photos

    }

    @Test(testName = "Retrieve the first 10 Mars photos made by 'Curiosity' on Earth date equal to 1000 Martian sol. Date: 2015-06-13",  priority = 2)
    public void RetrieveTheFirst10MarsPhotosMadeByCuriosityOnEarthDateEqualTo1000MartianSol() throws JsonProcessingException {

        List<Map<String, String>> first10photosCuriosity = photoServiceHelper.getPhotoNASAWithPageFilter(10, "earth_date", "2015-05-30");
        System.out.println(first10photosCuriosity); // I print first 10 Earth Date photos

    }

    @Test(testName = "Retrieve and compare the first 10 Mars photos made by 'Curiosity' on 1000 sol and on Earth date equal to 1000 Martian sol.", priority = 3)
    public void RetrieveAndCompareTheFirst10MarsPhotosMadeByCuriosityOn1000SolAndOnEarthDateEqualTo1000MartianSol() throws JsonProcessingException {

        List<Map<String, String>> first10photosCuriositySols = photoServiceHelper.getPhotoNASAWithPageFilter(10, "sol", "1000");
        List<Map<String, String>> first10photosCuriosityEarthDate = photoServiceHelper.getPhotoNASAWithPageFilter(10, "earth_date", "2015-05-30");
        Assert.assertTrue(first10photosCuriositySols.equals(first10photosCuriosityEarthDate));

    }

    @Test(testName = "Validate that the amounts of pictures that each 'Curiosity' camera took on 1000 Mars sol is not greater than 10 times the amount taken by other cameras on the same date.", priority = 4)
    public void ValidateThatAmountsPicturesEachCuriosityCameraTookOn1000MarsSolIsGreater10TimesAmountTakenByOtherCamerasOnTheSameDate() throws JsonProcessingException {

        List<Map<String, String>> AllPhotosCuriosity1000Sols = photoServiceHelper.getPhotoNASA("sol", "1000");
        int countAllPhotos1000Sols = AllPhotosCuriosity1000Sols.size();

        for (int index = 0; index < cameras.values().length; index++) {
            var cameraName = cameras.values()[index];
            Response response = given().queryParam("api_key", "DEMO_KEY")
                    .queryParam("sol", "1000").queryParam("camera", cameraName)
                    .when().get(GET_NASA_PHOTOS_CURIOSITY).then().extract().response();

            JsonPath jsonPath = response.jsonPath();
            var countCameraPhotos = jsonPath.getInt("photos.size()");
            var compareValue = (countAllPhotos1000Sols - countCameraPhotos) * 10;
            if (countCameraPhotos < compareValue) {
                System.out.println("The amounts of pictures for " + cameraName + " camera is not greater than 10 times the amount taken by other cameras on the same date.");
            } else {
                System.out.println("The amounts of pictures for " + cameraName + " camera is greater than 10 times the amount taken by other cameras on the same date.");
            }
        }
    }
}

# DevigetAPITest
Challenge test from deviget to test NASA { APIs }

Used these API Technologies for this Pilot Test framework:


- Maven - maven archetype quickstart project
- jackson-databind version 2.12.3 
- io.rest-assured  version 4.0.0
- testng version 6.14.3
- allure-testng version  2.13.5

Excercise:

Create pilot Java test framework for testing NASA's open API.

NASA has an open API: https://api.nasa.gov/index.html#getting-started. It grants access to different features e.g: Astronomy Picture of the Day, Mars Rover Photos, etc.

We would like to test different scenarios that the API offers:

Retrieve the first 10 Mars photos made by "Curiosity" on 1000 Martian sol.
Retrieve the first 10 Mars photos made by "Curiosity" on Earth date equal to 1000 Martian sol.
Retrieve and compare the first 10 Mars photos made by "Curiosity" on 1000 sol and on Earth date equal to 1000 Martian sol.
Validate that the amounts of pictures that each "Curiosity" camera took on 1000 Mars sol is not greater than 10 times the amount taken by other cameras on the same date.

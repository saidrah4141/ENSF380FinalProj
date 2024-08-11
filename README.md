# SubwayScreen Project

Welcome to the **SubwayScreen** project! This application simulates the display systems used in subway stations and inside subway trains, providing real-time updates on weather, news, and train locations.

## Components

### 1. TimeDisplayApp

**TimeDisplayApp** is the main screen display for a subway station. It takes three user inputs:

- **Weather City Code**: This code is retrieved from the OpenWeather API to display current weather information.
- **Station Code**: The reference station code used to show relevant train information.
- **News Topic**: A keyword or topic to fetch and display current news headlines.

**Map and Ads Rotation**:
- The map on the screen will switch every 5 seconds.
- The advertisements will rotate every 10 seconds.

### 2. InsideTrainDisplay

The **InsideTrainDisplay** component provides real-time updates for passengers inside the train. It prompts the user for:

- **Reference Train Code**: Enter the code for the train to track its journey.

The app will show:
- The next 4 stations from the current position.
- The previous station.
- The current station.

The display updates automatically as the train moves from station to station.

## Setup Instructions

### Database Credentials

Ensure you have access to the database with the following credentials:
- **Username**: `user`
- **Password**: `ensf380`

### Weather Integration

To display weather information, you need to use the OpenWeather API. This requires:

- **Selenium**: For automating browser interactions.
- **ChromeDriver**: For browser compatibility with Selenium.

**Important**: Update the path in the `WeatherService` class to point to the location of your ChromeDriver executable. The current path used in the code is specific to my local setup and will need to be updated for your environment. Download the ChromeDriver JAR file and adjust the path accordingly in the `WeatherService` class.

**Note**: The `WeatherService` class should be exported as a JAR file for use in the application.

## Running the Application

1. **Configure Database**: Make sure your database is set up with the provided credentials.
2. **Update Paths**: Adjust the ChromeDriver path in the `WeatherService` class.
3. **Launch the App**: Run the `TimeDisplayApp` and `InsideTrainDisplay` classes to start the displays.

Feel free to reach out if you have any questions or need further assistance!

Happy coding!

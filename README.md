# Parent Project

This is the parent project that contains two submodules: **myplace**.
The code is not currently fully functional and work in progress.

## Project Structure

- **myplace**: This is the main application. It functions as both a control board simulator and a myplace service

- **myplace-frontend**: This is a frontend webpage to access the myplace server.

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the parent project directory:
   ```
   cd parent-project
   ```

3. Build the project using Maven:
   ```
   mvn clean install
   ```

## Usage

To run the **myplace** module, navigate to the **myplace** directory and execute:
- The following for the Control Board mock app
  ```
  mvn quarkus:dev -Dquarkus.profile=cb
  ```
- The following for the My Place app
  ```
  mvn quarkus:dev -Dquarkus.profile=myplace
  ```

## Info

The server on port 2025 uses a different http server than the quarkus http server due to myplace using unescaped json strings in the url. vertx rejects the message before it can get to a interceptor to adjust the input.
TODO: make the server a bit less of a hack.

The myplace-frontend uses the quarkus http server endpoint and send correctly formatted json calls.


## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

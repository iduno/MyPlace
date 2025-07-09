# Parent Project

This is the parent project that contains two submodules: **myplace** and **myplace_cb_demo**.
The code is not currently fully functional and work in progress.

## Project Structure

- **myplace**: This module contains the main application logic and resources.
- **myplace_cb_demo**: Not in use. to be deleted.

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
  mvn quarkus:dev -Dquarkus.profile=dev
  ```

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

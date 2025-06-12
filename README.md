# Parent Project

This is the parent project that contains two submodules: **myplace** and **myplace_cb_demo**.

## Project Structure

- **myplace**: This module contains the main application logic and resources.
- **myplace_cb_demo**: This module serves as a demonstration for the capabilities of the **myplace** module.

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

- To run the **myplace** module, navigate to the **myplace** directory and execute:
  ```
  mvn spring-boot:run
  ```

- To run the **myplace_cb_demo** module, navigate to the **myplace_cb_demo** directory and execute:
  ```
  mvn spring-boot:run
  ```

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
# Web Crawler

This project is a multithreaded web crawler implemented in Java 8, designed to extract images from the webpages of a given website. The crawler is built as a Java EE web application using Spring MVC framework. It utilizes Jsoup for HTML parsing and Thymeleaf for dynamic template rendering instead of JavaScript AJAX calls.

## Features

- Traverses the website using a breadth-first search algorithm
- Avoids revisiting already scanned pages
- User inputs a URL on a webpage and initiates the crawling process
- Displays all images found on the website in a table, excluding duplicates

## Backend Structure

The crawler consists of the following classes:

- **WebCrawler**: Represents a web crawler that visits URLs and extracts information such as links and images from web pages using a provided parser.
- **Crawl**: Represents each crawl of the web crawler and implements the Runnable interface for multithreading.
- **ThreadPoolManager**: Serves as a manager for handling threads in the WebCrawler application.
- **Utility classes**: Various utility classes are used to parse site data, along with Spring controller and configuration classes.

The backend implementation extensively employs thread-safe data structures, especially the ConcurrentHashMap.

## Frontend Structure

The frontend of the application utilizes the following technologies:

- **SCSS**: Utilized to render CSS, following the SCSS 7-1 pattern.
- **HTML**: Used for structuring the webpages.
- **Thymeleaf**: Enables dynamic template rendering to display the extracted image data back to the user.

## Installation

1. Clone the repository.
2. Build the project using your preferred Java build tool.
3. Deploy the generated web archive (WAR) file to a Java EE web server.
4. Configure the server and ensure it meets the necessary requirements.

For detailed instructions on installation and deployment, refer to the project documentation.

## Usage

1. Access the deployed web application through a web browser.
2. Enter a URL into the provided webpage.
3. Click the submit button to initiate the crawling process.
4. Wait for the crawler to complete.
5. View the extracted images displayed in a table.

## Contributing

Contributions to this project are welcome. To contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Submit a pull request, providing a detailed description of your changes.

Please ensure that your contributions adhere to the project's coding conventions and follow the established guidelines.

## License

This project is licensed under the [MIT License](LICENSE).
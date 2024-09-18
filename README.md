# Prayer Time Scraper

## Overview
The **Prayer Time Scraper** is a Java-based tool using Jsoup to extract and display prayer times from various online sources. This tool helps users get accurate prayer times for Masjid Bilal Luton and
other locations in future releases.

## Features
- **Scrapes prayer times** from specified websites.
- **Supports multiple locations**.
- **Customizable** to adapt to different website structures.

## Prerequisites
- **Java 17**
- **Gradle** (for build automation)

## Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/Dinul2694/prayer-time-scraper.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd prayer-time-scraper
    ```

3. **Build the project using Gradle**:
    ```bash
    ./gradlew build
    ```

## Usage

1. **Configure the URL and location**: Edit the `src/main/resources/config.properties` file to set the URL of the website to scrape and your location preferences.

2. **Run the scraper**: Execute the main class from the command line or through your IDE.

   ```bash
   java -cp build/libs/prayer-time-scraper-1.0.jar com.example.PrayerTimeScraper

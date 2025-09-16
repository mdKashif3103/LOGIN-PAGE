# Magnus-like Spring Boot Demo

This project implements a Magnus-like UI with Employee CRUD and a More menu containing multiple demo slides (Tabs, Menu, Autocomplete, Collapsible Content, Images upload, Slider, Tooltips, Popups, Links, CSS Properties, iFrames).

## Requirements
- Java 17+
- Maven
- MySQL running on localhost with database `magnusdb` and user `root`/`root`

Create DB:
```
CREATE DATABASE magnusdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## Run
```
mvn clean package
mvn spring-boot:run
```
Open http://localhost:8080

Images uploaded via More → Images are stored in `uploads/` directory created next to the working directory.

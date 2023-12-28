# JavaFX Desktop Photo Library Application - Project Summary
## Overview
I designed a JavaFX-based desktop photo library application, leveraging Java's robust capabilities for handling media and user interaction. This project showcases my skills in creating a functional, user-friendly application for managing digital photos, complete with a variety of features to enhance the user experience.

## Key Features

### Photo Dates
Date Representation: The application uses the last modification date of the photo file as a proxy for the date the photo was taken. This date is referred to as the "date the photo was taken" in the user interface.
### Tagging System
Flexible Tags: Photos can be tagged with a wide array of attributes like location or names of people. Tags are a combination of a name and value (e.g., "location", "New Brunswick").
User-Defined Tags: Users can define new tag types and add them to a preset list of tag options.
### Photo Storage
Stock Photos: The application includes 5 to 10 stock photos stored under a special "stock" username in a "stock" album.
User Photos: Users can import their photos from any location on their computer. The application only stores the photo's location, not the actual file.
### Login and Administration
Login System: Users log in with a username (password implementation is optional).
Admin Subsystem: An admin account can list, create, or delete users.
### Non-Admin User Subsystem
Album Display: On login, the user sees all albums with details like album name, photo count, and date range of photos.
Album Management: Users can create, delete, or rename albums.
### Album Interactions
Photo Management: Inside an album, users can add or remove photos, and caption or recaption them.
Photo Display and Tags: A separate display area shows a selected photo, its caption, date-time of capture, and tags.
Copying and Moving Photos: Photos can be copied or moved between albums. Changes to a photo reflect in all albums where it appears.
### Searching Photos
Search Functionality: Users can search for photos by date range or tag type-value pairs (e.g., "person=sesh", "location=prague").
Search Result Album: Users can create an album from search results.
### Additional Functionalities
Logout and Quit: Users can log out, saving all updates, or quit the application entirely.
Error Handling: All errors are handled within the GUI, with no reliance on the text console.
### Technical Implementation
JavaFX and FXML: The GUI is built using JavaFX and FXML (no Swing).
Serialization: Data is stored and retrieved using Java's serialization mechanism.

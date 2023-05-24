# Bookworm App
Android App to keep track of books that you are reading.

Makes use of [Google's Books API](https://developers.google.com/books/docs/v1/using) to search books and uses firestore to store any user related data and book stats.

The app was built with clean architecture in mind and the MVVM pattern.
Uses Jetpack Compose and Material 3 components for the UI layer.

## Tech Stack

- **Compose:** Modern UI designed with the compose declarative approach with Material3.
- **Retrofit:** Make network calls to Google's Books API.
- **Paging3:** Pagination of book search results and user book stats.
- **Firebase Auth:** Handle user authentication and registration.
- **Firebase Firestore:** Save the user's data and books stats.
- **Dagger-Hilt:** Handle dependency injection.

## Features

- Register to use the app
- Search for books and add them to your reading queue.
- Filter the search to get more accurate results.
- See all the books you've added based on their reading status.
- Update the reading status of a book to keep track of the books you are reding, have read and have in your queue.
- Add a rating and comments to your books
- Remove books from your list
- Supports light and dark theme

## Running the App

If you download or clone this repo, in order to make it work, you should:

- Follow the instructions given in the official documentation on [how to add Firebase to your project](https://firebase.google.com/docs/android/setup#register-app). 
- Enable **firebase authentication** with email and password as sign-in method.
- Enable **Cloud firestore** as database
    - Create a composite index containing the `userId`(ASC), `status`(ASC) and `createdAt`(DESC) fields for the **`book_stats`** collection (It should work even if the collection doesn't exist).

## Screenshots

![home](https://github.com/GustavoMuller/BookwormApp/assets/18502898/aef2f5f3-e4bd-4a71-866b-ed373846c33f)
![search](https://github.com/GustavoMuller/BookwormApp/assets/18502898/91177860-b9ef-4617-8c44-545f8a71970a)

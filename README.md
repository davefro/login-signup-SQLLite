# Book Club App

## Description

The Book Club App aims to support LTU Computer Science students by allowing them to discover, share
and discuss their favourite books. Built for Android, the app offers elegant and intuitive interface
where user can maintain a personal library, add and delete books and connect with others through book 
recommendations and reviews.

## App Design

The Book Club App is designed with a focus on a user experience (UX) and user interface (UI)
principles that promote ease of use and engagement. The app features a clean and intuitive interface
, utilising Material Design guidelines to ensure consistency and accessibility across various screens
and functions.

- Navigation: Utilises a bottom navigation bar using fragments for each section to quick access to
the app's main features: Profile where users can update or delete their profile with the option to 
logout. Library with small button to allows students to add, update, delete, rate and review books.
We can also see a light animation in which the book appears from the bottom of the screen(recyclerView).
Notifications with option to see when new book has been added (a small badge appears on the notification icon). 
Messages where users can interact with others and discuss reviews with an option to search for a book
(if it is in the library). There is also a search icon in the top tight-hand corner that helps you
find a book faster if it is in the library.

- Colour Scheme and Typography: A warm and inviting colour scheme with wave effect was adopted to encourage
reading and exploration. The Home Page uses a dark background photo of the books in the library,
where the welcome white text with the logged-in user's email can be seen in contrast to the dark
background photo. On the other screens, a soft white background with wavy lines appears so as not to
distract the user from using the application. Typography was chosen for readability on a variety of
devices, enhancing the overall user experience.

- Responsive Layouts: Designed to be fully responsive, tested on different devices, ensuring a seamless
experience

## App Functionality

The app combines social networking features with personal library management, offering a comprehensive
platform for LTU CS students.

- User Authentication: Users can sign up, log in, reset password and manage their profiles. Authentication 
ensures a personalised and secure experience with strong validations.
- Personal Library (CRUD Operations): Users can add books to their personal library, view details, 
edit entries, or remove them. This functionality adheres to CRUD (Create, Read, Update, Delete) principles,
allowing complete management of their book collections.
- Book Details and Reviews: For each book, users can view detailed information, including summaries,
ratings, and reviews from other users. Users can contribute by rating books and writing their own reviews.
- Social Interaction: Users can share book recommendations, and participate in discussions. This feature
aims to build a community of readers who can share insights and discover new books together.
- Notifications: Keeps users informed about new releases.

## Key functionalities include:

- User Authentication: Sign up, log in,reset password and manage user profiles.
- CRUD Operations: Users can create, read, update, and delete (CRUD) entries in their personal book library.
- Search Feature: Explore books by title.
- Ratings and Reviews: Rate books and write reviews.
- Social Interaction: Discuss the reviews with other users.

## Installation 

1. Clone the repository to your local machine:
   git clone https://github.com/davefro/login-signup-SQLLite.git
2. Open the project in Android Studio.
3. Run the app on an emulator or physical device.

## Challenges, Solutions, Lessons Learned

During the development of the application I encountered many problems, which is a normal process.
When one problem was solved, another one appeared. However, one of the most critical challenges was 
connecting the front-end to the back-end (database).
I created two tables in the database, one is the Userdetails table and the other is my_library. 
Initially the connection process seemed quite simple and I dealt with the login and registration pages
quite quickly. However, when I started to extend the application to add CRUD operations and display 
the added books in the recycler view, complications started to arise. The books that were stored in 
the database were not displaying correctly and I did not know why. After a thorough search I found 
the solution to my problem, which turned out to be the CustomAdapter. A CustomAdapter allows to 
design and use custom layouts for each item in the list. This is particularly useful when the standard 
layouts provided by Android do not meet requirements. In my case, displaying book details like the 
title, author, comments, and a rating likely requires a specific layout that can neatly organise this 
information, along with an image of the book cover. Additionally binding data to views enables to
efficiently bind data from my data source (in my case, an ArrayList<Book>) to the views defined in my
custom layout. This process is handled within the onBindViewHolder method, where I can set the 
properties of my views (like text, images, and ratings) based on the current book object. This dynamic 
binding is essential for displaying a list that reflects the actual contents of my dataset.
I could list many more examples, but I wanted to focus mainly on one, which I consider to be the most
significant in my case.
After much effort, the development of the Book Club application has been a valuable learning experience.
I encountered many challenges along the way, but I overcame them and improved my skills as a student.
Although this is my first mobile app, I am satisfied with the final product. While it may not be as 
advanced as other apps on the market, I focused on ensuring it meets the necessary functionality and
design requirements.

## References:

1. Android Developers (n.d.). _Documentation._ 
https://developer.android.com/docs
2. Flaticon. (2010). Flaticon, _The Largest Database of Free Vector Icons._ Flaticon.
https://www.flaticon.com/
3. GeeksforGeeks. (2021, May 13). _Android Studio Tutorial_. GeeksforGeeks. 
https://www.geeksforgeeks.org/android-studio-tutorial/
4. _The Complete Android App Developer Course || Start Developing Android Apps Today!_ (n.d.). 
Www.youtube.com. Retrieved March 19, 2024, 
from https://www.youtube.com/watch?v=NLvaOL6Cm48&list=PL6Q9UqV2Sf1gHCHOKYLDofElSvxtRRXOR&index=1
5. Unsplash. (2022). _Beautiful Free Images & Pictures._ Unsplash; Unsplash. 
https://unsplash.com/




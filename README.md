CodeChallenge App
=============================


Application based on data from service http://jsonplaceholder.typicode.com.
Avatars are generated with https://api.adorable.io/avatars/
Placeholder images are genereted with: http://via.placeholder.com

Application gets data such as:
- Posts list
- Post details
- Post comments list
- User details

REST methods are handled with Retrofit library.
Placeholders and avatars are handled by Glide library

Application is based on MVVM pattern with usage of databinding
Dependency Injection is based on Dagger2

You can find cache for loaded data based on SQLite database provided by Room ORM. This is dummy cache. It cached data, data are read from cache but I have not implemented yet working offline app based on cache.

Awesome and intuitive user interface is most important for users.
In real life needs of the users are change, project is evolve, new features appear, new SDKs brings changes and new possibilities. 
To keep up with all of this things You need good foundations. Good architecture this is it. 
In case of lack enough time I focused on architecture. 
           
And my app doesn't have awesome UI, custom animations and transitions between fragments. But is simple, tidy and friendly. 


I have written some unit and integration tests and unfortunatelly I haven't written any espresso tests yet. If I be able to make enough space in my weekly schedule, I will add them to my project. 

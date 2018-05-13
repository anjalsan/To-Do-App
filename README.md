## Task

1. Home Screen
	- Has two tabs: first one with a list of active todos and the second with a list of completed todos
	- Each active todo item can be marked as completed (using a checkbox or any similar widget).
2. Add Todo Screen
	- For creating a new active todo item.

   Notes:
	- Each todo item consists of a title (mandatory), description (optional) and a reminder time (optional).
	- If the reminder time is set, the app should display a notification at the specified time if the todo item is still active
	- Use RxJava for all asynchronous operations
	- Use Room for managing local storage
	- Architect the app for code clarity and scalability (in terms of adding new features). Document the architecture properly.
	- minSdkVersion should be 16
	- Please use Java, not Kotlin (sorry!)
	- Show your ui skills
	
## Libraries used on the sample project:
	
 - AppCompat, CardView and DesignLibrary
 - Data binding
 - RxJava, RxBinding
 - Room Persistence Library
 - Architecture Components for Lifecycle, LiveData and Lifecycle classes.
 
## Architecture

In this app, I'm following MVVM  Architecture(Model View ViewModel) with Some Android Architecture Components Like ViewModel, LiveData and Room.
Here I'm using Room for all DB related stuff. in Room, the database instance contained in a ViewModel. it contains all the data needed for the App. I wrap the list of to-do items inside LiveData, so that the Activity can observe changes and update UI accordingly.
RecyclerView Adapter of Todo item using data binding for each of its items. and there is a ViewModel Class(ItemTodoViewModel) for communicating with View and Model.
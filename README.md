# *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings and popularity within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Include other details of movie including poster, vote count, release date, original title, and popularity. Implemented for both portrait and landscape mode.
* [x] Include video trailer in the same activity as movie details for better user experience
* [x] Shorten the movie overview in MainActivity and show full description when user clicks on a movie to see details.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

(Portrait vs. Landscape)

<img src='https://i.imgur.com/4AQr3zj.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />  <img src='https://i.imgur.com/UbbgLWO.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' /> 

GIF created with [Kap](https://getkap.co/).


## Notes

- Had some issues converting the UI from portrait mode to Landscape mode for the activity that shows movie details. I had to look into ScrollView and LinearLayout in order to align my UI components. However, the documentations were clear and it was easy to implement.
- The Youtube video was supposed to play in a separate activity. However, I thought this wouldn't make a good UI so I wanted to include the YoutubeView in the activity that show movie details. 


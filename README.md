# Earthquake Feed

<img src="https://github.com/seth-wat/Earthquake-Feed/blob/polish/display-image-small.png">

## Summary
Earthquake Feed is an Android app that provides access and storage for exploring earthquake occurrences. Fluid UI/UX was accomplished using `Vector Drawables`, `Transitions Framework`, `View Animations`, and the Material Design guidelines.  

## Features
* Load recent and past earthquake occurrences from all over the world.
* Enter search parameters to refine results. 
* View the result set visually on a world map.
* View specific earthquake details.
* Save earthquakes for offline access.
* Share earthquakes.
* Home-screen widget to view the largest earthquake of the day.

## API's
* [USGS](https://earthquake.usgs.gov/fdsnws/event/1/): *Fetching earthquake data*
* [Google Maps](https://developers.google.com/maps/documentation/android-api/): *Displaying earthquakes on maps*
* [AdMob](https://developers.google.com/admob/android/quick-start): *Unintrusive banner ads*
* [Twitter](https://dev.twitter.com/twitterkit/android/compose-tweets): *Compose tweets with earthquake data*
* [Parceler](https://github.com/johncarl81/parceler): *Decrease boilerplate code `Parceable`*

## Configuration  
* Support for Android SDK's **21(Lollipop) - 27(Oreo)**
* Replace Twitter API and Google Map API keys in gradle.properties  
 `googlemapsapikey="" twitterapikey="" twittersecret=""`  
 [Twitter API Console](https://apps.twitter.com/), [Maps API Guide](https://developers.google.com/maps/documentation/android-api/start)  
* Ready to build.

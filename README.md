# PhotoGallery

## Basic info
Application for viewing photos from the site flickr.com. The main screen of the application consists of RecyclerView and SearchView for searching and displaying images. In order to see them, you need to enter the tag by which the search will take place. If the user liked the picture, he can like it, it will automatically go to the "Liked" album

# Features
* Mvvm
* Picasso
* Room
* Retrofit2

## Screenshots
<p>
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/home.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/query.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/save1.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/choose_album_name.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/save2.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/album_elements.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/picture_details.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/albums.jpg" width="256" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/change_album_name.jpg" width="256" />
</p>

In order to save a picture to an album, you must first create it. You cannot create an empty album. The Room database stores the url of the picture, its author and the album to which it belongs

To view the saved pictures, you must first go to AlbumActivity. On it, you can change the name of an existing album and go to the desired

From AlbumActivity you can go to AlbumElementsActivity. When you click on the picture, it will be displayed on the screen in a larger size and will show additional information about the user who posted it

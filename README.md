# PhotoGallery
Приложение для просмотра фотографий с сайта flickr.com. Главный экран приложения состоит из RecyclerView и SearchView для поиска и отображения картинок. Для того, чтобы их увидеть необходимо ввести tag, по которому будет происходить поиск. Если пользователю понравилась картинка, он может ее лайкнуть, она автоматически перейдет в альбом "Понравившееся"

<p>
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/home.jpg" width="320" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/query.jpg" width="320" />
</p>

Для того, чтобы сохранить картинку в альбом, его надо предварительно создать. Пустой альбом создать нельзя. В базе данных Room хранится url картинки, ее автор и альбом, к которому она принадлежит

<p>
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/save1.jpg" width="320" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/choose_album_name.jpg" width="320" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/save2.jpg" width="320" />
</p>

Для просмотра сохраненных картинок нужно сначала перейти в AlbumActivity. На ней можно изменить название уже существующего альбома и перейти к нужному

<p>
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/albums.jpg" width="320" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/change_album_name.jpg" width="320" />
</p>

Из AlbumActivity можно перейти в AlbumElementsActivity. При нажатии на картинку, она отобразится на экране в большем размере и покажет дополнительную информацию о пользователе, который ее разместил

<p>
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/album_elements.jpg" width="320" />
  <img src="https://github.com/avelycure/avelycure/blob/master/assets/photoGallery/picture_details.jpg" width="320" />
</p>

# Технологии
В качестве архитектуры проекта выбран паттерн MVVM. В стек технологий проекта входят: Retrofit2, Picasso, Room

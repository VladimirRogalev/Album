package album.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import album.model.Photo;

public class AlbumImpl implements Album {
	Photo[] photos;
	int size;

	public AlbumImpl(int capacity) {
		photos = new Photo[capacity];
	}

	@Override
	public boolean addPhoto(Photo photo) {
		if (photo == null || size == photos.length|| getPhotoFromAlbum(photo.getPhotoId(), photo.getAlbumId()) !=null) {
			return false;
		}
		photos[size++] = photo;
		return true;
	}

	@Override
	public boolean removePhoto(int photoId, int albumId) {
		for (int i = 0; i < size; i++) {
			if ((photoId == photos[i].getPhotoId() && albumId == photos[i].getAlbumId())) {
				photos[i] = photos[--size];
				photos[size] = null;

				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updatePhoto(int photoId, int albumId, String url) {
		for (int i = 0; i < size; i++) {
			if ((photoId == photos[i].getPhotoId() && albumId == photos[i].getAlbumId())) {
				photos[i].setUrl(url);

				return true;
			}
		}
		return false;
	}

	@Override
	public Photo getPhotoFromAlbum(int photoId, int albumId) {
		for (int i = 0; i < size; i++) {
			if ((photoId == photos[i].getPhotoId() && albumId == photos[i].getAlbumId())) {

				return photos[i];
			}

		}
		return null;
	}

	@Override
	public Photo[] getAllPhotoFromAlbum(int albumId) {

		return findPhotosByPredicate(c -> albumId == c.getAlbumId());
	}

	@Override
	public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
		LocalDateTime fromDate = dateFrom.atStartOfDay();
		LocalDateTime toDate = dateTo.atStartOfDay();
		
		Predicate<Photo> datePredicate = photos -> {
			LocalDateTime photodate = photos.getDate();
			return(photodate.isAfter(fromDate) && photodate.isBefore(toDate));
		};
		
		return findPhotosByPredicate(datePredicate);

	}

	@Override
	public int size() {
		return 0;
	}
	
	private Photo [] findPhotosByPredicate(Predicate<Photo> predicate) {
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (predicate.test(photos[i])) {
				count++;
			}
		}
		Photo [] res = new Photo[count];
		for (int i = 0, j= 0;  j < res.length; i++) {
			if (predicate.test(photos[i])) {
				res[j++] = photos[i];
			}
			
		}
		return res;
	}

}

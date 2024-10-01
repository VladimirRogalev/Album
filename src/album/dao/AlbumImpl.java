package album.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

import album.model.Photo;

public class AlbumImpl implements Album {
	private static final int INITIAL_CAPACITY = 10;

	Photo[] photos;
	Photo[] photosByDate;
	int size;

	private static Comparator<Photo> comparator = (o1, o2) -> {
		int res = Integer.compare(o1.getAlbumId(), o2.getAlbumId());
		return res != 0 ? res : Integer.compare(o1.getPhotoId(), o2.getPhotoId());
	};

	private static Comparator<Photo> comparatorByDate = (o1, o2) -> {

		int res = o1.getDate().compareTo(o2.getDate());
		return res;
	};

	public AlbumImpl() {
		photos = new Photo[INITIAL_CAPACITY];
		photosByDate = new Photo[INITIAL_CAPACITY];
	}

	@Override
	public boolean addPhoto(Photo photo) {
		if (photo == null || size == photos.length
				|| getPhotoFromAlbum(photo.getPhotoId(), photo.getAlbumId()) != null) {
			return false;
		}
		if (photos.length == size) {
			photos = Arrays.copyOf(photos, photos.length * 2);
			photosByDate = Arrays.copyOf(photosByDate, photosByDate.length * 2);
		}
		int index = Arrays.binarySearch(photos, 0, size, photo, comparator);
		index = index >= 0 ? index : -index - 1;
		System.arraycopy(photos, index, photos, index + 1, size - index);
		photos[index] = photo;
		index = Arrays.binarySearch(photosByDate, 0, size, photo, comparatorByDate);
		index = index >= 0 ? index : -index - 1;
		System.arraycopy(photosByDate, index, photosByDate, index + 1, size - index);
		photosByDate[index] = photo;
		size++;

		return true;
	}

	@Override
	public boolean removePhoto(int photoId, int albumId) {
		int index = searchById(photoId, albumId);
		if (index < 0) {
			return false;
		}
		System.arraycopy(photos, index + 1, photos, index, size - index - 1);
		photos = Arrays.copyOf(photos, photos.length - 1);
		System.arraycopy(photosByDate, index + 1, photosByDate, index, size - index - 1);
		photos = Arrays.copyOf(photosByDate, photosByDate.length - 1);
		size--;
		return true;

	}

	@Override
	public boolean updatePhoto(int photoId, int albumId, String url) {
		int index = searchById(photoId, albumId);
		if (index < 0) {
			return false;
		}
		photos[index].setUrl(url);
		return true;
	}

	@Override
	public Photo getPhotoFromAlbum(int photoId, int albumId) {
		int index = searchById(photoId, albumId);
		return index < 0 ? null : photos[index];
	}

	@Override
	public Photo[] getAllPhotoFromAlbum(int albumId) {
		Photo pattern = new Photo(Integer.MIN_VALUE, albumId, null, null, LocalDateTime.MIN);
		int from = -Arrays.binarySearch(photos, 0, size, pattern, comparator) - 1;
		pattern = new Photo(Integer.MAX_VALUE, albumId, null, null, LocalDateTime.MAX);
		int to = -Arrays.binarySearch(photos, 0, size, pattern, comparator) - 1;

		return Arrays.copyOfRange(photos, from, to);

	}

	@Override
	public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {

		Photo pattern = new Photo(Integer.MIN_VALUE, Integer.MIN_VALUE, null, null, dateFrom.atStartOfDay());
		int from = -Arrays.binarySearch(photosByDate, 0, size, pattern, comparatorByDate) - 1;
		pattern = new Photo(Integer.MAX_VALUE, Integer.MAX_VALUE, null, null, LocalDateTime.of(dateTo, LocalTime.MAX));
		int to = -Arrays.binarySearch(photosByDate, 0, size, pattern, comparatorByDate) - 1;
		return Arrays.copyOfRange(photosByDate, from, to);

	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void printPhotos() {
		for (int i = 0; i < size; i++) {
			System.out.println(photos[i]);

		}
		System.out.println();

	}

	private int searchById(int photoId, int albumId) {
		for (int i = 0; i < size; i++) {
			if (photos[i] != null && photoId == photos[i].getPhotoId() && albumId == photos[i].getAlbumId()) {
				return i;

			}
		}
		return -1;
	}

}

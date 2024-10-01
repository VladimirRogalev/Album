package album.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import album.dao.Album;
import album.dao.AlbumImpl;
import album.model.Photo;

class AlbumTests {
	Album album = new AlbumImpl();
	Photo[] photos = new Photo[5];
	LocalDateTime dateTime = LocalDateTime.now();
	Comparator<Photo> comparator = (o1, o2) -> {
		int res = Integer.compare(o1.getAlbumId(), o2.getAlbumId());
		return res!=0 ? res:Integer.compare(o1.getPhotoId(), o2.getPhotoId());
	};


	@BeforeEach
	void setUp() throws Exception {

		photos[0] = new Photo(1, 101, "Sunset over the Hills", "sunset_hills.jpg", dateTime.minusDays(4));
		photos[1] = new Photo(2, 202, "City Lights at Night", "city_lights.jpg", dateTime.minusDays(6));
		photos[2] = new Photo(3, 303, "Morning Dew on Leaves", "morning_dew.jpg", dateTime.minusDays(1));
		photos[3] = new Photo(4, 505, "Mountain Peak in Winter", "mountain_peak.jpg", dateTime.minusDays(4));
		photos[4] = new Photo(5, 505, "Autumn Forest Path", "autumn_forest.jpg", dateTime.minusDays(1));
		for (int i = 0; i < photos.length; i++) {

			album.addPhoto(photos[i]);
		}

	}

	@Test
	void testAddPhoto() {
		assertFalse(album.addPhoto(null));
		assertFalse(album.addPhoto(photos[2]));
		assertEquals(5, album.size());
		Photo photo = new Photo(8, 808, "Sunset in desert", "desert_sunset.jpg", LocalDateTime.of(2023, 8, 25, 20, 29));
		assertTrue(album.addPhoto(photo));
		photo = new Photo(9, 909, "Lake", "lake.jpg", LocalDateTime.of(2019, 4, 25, 20, 29));
		assertTrue(album.addPhoto(photo));

	}

	@Test
	void testRemovePhoto() {
		assertFalse(album.removePhoto(11, 1011));
		assertTrue(album.removePhoto(2, 202));
		assertEquals(4, album.size());

	}

	@Test
	void testUpdatePhoto() {
		album.updatePhoto(3, 303, "evening_dew.jpg");
		assertEquals("evening_dew.jpg", photos[2].getUrl());

	}

	@Test
	void testGetPhotoFromAlbum() {
		assertEquals(album.getPhotoFromAlbum(3, 303), photos[2]);
		assertNull(album.getPhotoFromAlbum(111, 123));
	}

	@Test
	void testGetAllPhotoFromAlbum() {
		Photo[] actual = album.getAllPhotoFromAlbum(505);
		Photo[] expected = { photos[3], photos[4] };

		assertArrayEquals(expected, actual);

	}

	@Test
	void testGetPhotoBetweenDate() {
		LocalDate localDate = LocalDate.now();
		Photo [] actual = album.getPhotoBetweenDate(localDate.minusDays(6), localDate.minusDays(3));
		Arrays.sort(actual, comparator);
		Photo[] expected = { photos[0], photos[1], photos[3]};
		assertArrayEquals(expected, actual);

	}

}

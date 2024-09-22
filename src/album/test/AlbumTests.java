package album.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import album.dao.Album;
import album.dao.AlbumImpl;
import album.model.Photo;

class AlbumTests {
	Album album;
	Photo[] photos;

	@BeforeEach
	void setUp() throws Exception {
		album = new AlbumImpl(6);
		photos = new Photo[5];
		photos[0] = new Photo(1, 101, "Sunset over the Hills", "sunset_hills.jpg",
				LocalDateTime.of(2022, 8, 15, 19, 25));
		photos[1] = new Photo(2, 202, "City Lights at Night", "city_lights.jpg", LocalDateTime.of(2024, 4, 04, 8, 14));
		photos[2] = new Photo(3, 303, "Morning Dew on Leaves", "morning_dew.jpg", LocalDateTime.of(1998, 1, 1, 12, 00));
		photos[3] = new Photo(4, 505, "Mountain Peak in Winter", "mountain_peak.jpg",
				LocalDateTime.of(2000, 12, 12, 14, 14));
		photos[4] = new Photo(5, 505, "Autumn Forest Path", "autumn_forest.jpg", LocalDateTime.of(1993, 9, 30, 23, 59));
		for (int i = 0; i < photos.length; i++) {
			album.addPhoto(photos[i]);
		}

	}

	@Test
	void testAddPhoto() {
		assertFalse(album.addPhoto(photos[0]));
		Photo photo = new Photo(8, 808, "Sunset in desert", "desert_sunset.jpg", LocalDateTime.of(2023, 8, 25, 20, 29));
		assertTrue(album.addPhoto(photo));
		photo = new Photo(9, 909, "Lake", "lake.jpg", LocalDateTime.of(2019, 4, 25, 20, 29));
		assertFalse(album.addPhoto(photo));
	}

	@Test
	void testRemovePhoto() {
		assertFalse(album.removePhoto(11, 1011));
		assertTrue(album.removePhoto(2, 202));
		assertFalse(album.removePhoto(2, 202));

	}

	@Test
	void testUpdatePhoto() {
		System.out.println("Before update: " + photos[2]);
		album.updatePhoto(3, 303, "evening_dew.jpg");
		assertEquals("evening_dew.jpg", photos[2].getUrl());
		System.out.println("After update: " + photos[2]);

	}

	@Test
	void testGetPhotoFromAlbum() {
		assertEquals(album.getPhotoFromAlbum(3, 303), photos[2]);
		assertNull(album.getPhotoFromAlbum(111, 123));
	}

	@Test
	void testGetAllPhotoFromAlbum() {
	Photo [] expected = {photos[3], photos[4]};
	assertArrayEquals(expected, album.getAllPhotoFromAlbum(505));
	}

	@Test
	void testGetPhotoBetweenDate() {
		Photo [] expected = { photos[2], photos[3], photos[4]};
		assertArrayEquals(expected, album.getPhotoBetweenDate(LocalDate.of(1993, 1, 01), LocalDate.of(2000, 12, 31)));
	}


}

package album.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Photo {

	protected int photoId;
	protected int albumId;
	protected String title;
	protected String url;
	protected LocalDateTime date;

	public Photo(int photoId, int albumId, String title, String url, LocalDateTime date) {
		this.photoId = photoId;
		this.albumId = albumId;
		this.title = title;
		this.url = url;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAlbumId() {
		return albumId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(photoId, albumId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;
		return photoId == other.photoId && photoId == other.albumId;
	}

	@Override
	public String toString() {
		return "Photo [photoId=" + photoId + ", albumId=" + albumId + ", title=" + title + ", url=" + url + ", date="
				+ date + "]";
	}

}

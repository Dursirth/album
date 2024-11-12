package test;

import dao.AlbumImpl;
import model.Photo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class AlbumImplTest {
    private AlbumImpl album;
    private Photo photo1, photo2, photo3,photo4,photo5;
    private final Comparator<Photo> comparator = (p1, p2) -> {
        int res = Integer.compare(p1.getAlbumid(), p2.getAlbumid());
        return res != 0 ? res : Integer.compare(p1.getPhotoid(), p2.getPhotoid());
    };

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        album = new AlbumImpl(5);
        photo1 = new Photo(1, 1, "first", "kkk", LocalDate.of(2021, 1, 1).atStartOfDay());
        photo2 = new Photo(2, 2, "second", "sss", LocalDate.of(2021, 3, 9).atStartOfDay());
        photo3 = new Photo(2, 3, "third", "aaa", LocalDate.of(2021, 6, 15).atStartOfDay());
        photo4 = new Photo(2, 4, "fourth", "sww", LocalDate.of(2021, 4, 27).atStartOfDay());
        photo5 = new Photo(2, 5, "fifth", "qqq", LocalDate.of(2021, 5, 12).atStartOfDay());
    }

    @Test
    void testAddPhoto() {
        assertTrue(album.addPhoto(photo1));
        assertEquals(1, album.size());
    }

    @Test
    void testRemovePhoto() {
        album.addPhoto(photo1);
        album.addPhoto(photo2);
        album.addPhoto(photo3);
        assertTrue(album.removePhoto(2, 2));
        assertEquals(2, album.size());
    }

    @Test
    void testGetPhotoFromAlbum() {
        album.addPhoto(photo1);
        assertEquals(photo1, album.getPhotoFromAlbum(photo1.getPhotoid()));
        assertNull(album.getPhotoFromAlbum(39283));
    }

    @Test
    void testGetAllPhotoFromAlbum() {
        album.addPhoto(photo1);
        album.addPhoto(photo2);
        album.addPhoto(photo3);
        album.addPhoto(photo4);
        album.addPhoto(photo5);
        Photo[] photos = album.getAllPhotoFromAlbum(2);
        Arrays.sort(photos, comparator);
        assertEquals(4, photos.length);
        Photo[] result = new Photo[]{photo2, photo3, photo4, photo5};
        assertArrayEquals(result, photos);
    }

    @Test
    void testGetPhotoBetweenDate() {
        album.addPhoto(photo1);
        album.addPhoto(photo2);
        album.addPhoto(photo3);
        Photo[] arrayLen = album.getPhotoBetweenDate(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 6, 15));
        assertEquals(1, arrayLen.length);

    }

    @Test
    void testSize() {
        assertEquals(0, album.size());
        assertTrue(album.addPhoto(photo1));
        assertEquals(1, album.size());
        assertTrue(album.addPhoto(photo2));
        assertTrue(album.addPhoto(photo3));
        assertEquals(3, album.size());

    }

    @Test
    void testUpdatePhoto() {
        album.addPhoto(photo1);
        album.addPhoto(photo2);
        assertTrue(album.updatePhoto(2,2,"222"));
        assertEquals("222",album.getPhotoFromAlbum(2).getUrl());
    }
}
package dao;

import model.Photo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

public class AlbumImpl implements Album {
    int size = 0;
    Photo[] photos;
    int capacity;

    public AlbumImpl(int capacity) {
        this.capacity = capacity;
        this.photos = new Photo[capacity];
    }

    @Override
    public boolean addPhoto(Photo photo) {
        if (size < photos.length) {
            photos[size++] = photo;
            return true;
        }
        return false;
    }

    @Override
    public boolean removePhoto(int photoid, int albumid) {
        int startIndex = -1;
        for (int i = 0; i < size; i++) {
            if (photos[i].getPhotoid() == photoid && photos[i].getAlbumid() == albumid) {
                startIndex = i;
                break;
            }
        }
        if (startIndex == -1) {
            return false;
        }
        System.arraycopy(photos, startIndex + 1, photos, startIndex, size - startIndex - 1);
        photos[--size] = null;
        return true;
    }

    private Photo[] allMethodsByPredicate(Predicate<Photo> predicate) {
        Photo[] tempArray = new Photo[size];
        int len = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(photos[i])) {
                tempArray[len++] = photos[i];
            }
        }
        return Arrays.copyOf(tempArray, len);
    }

    @Override
    public Photo getPhotoFromAlbum(int photoid) {
        Photo[] result = allMethodsByPredicate(new Predicate<Photo>() {
            @Override
            public boolean test(Photo photo) {
                return photo.getPhotoid() == photoid;
            }
        });
        return result.length > 0 ? result[0] : null;
    }

    @Override
    public Photo[] getAllPhotoFromAlbum(int albumid) {
        return allMethodsByPredicate(new Predicate<Photo>() {
            @Override
            public boolean test(Photo photo) {
                return photo.getAlbumid() == albumid;
            }
        });
    }


    @Override
    public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        Predicate<Photo> dateBetweenRange = new Predicate<Photo>() {
            @Override
            public boolean test(Photo photo) {
                return photo.getDate().isBefore(dateTo.atStartOfDay()) &&
                        photo.getDate().isAfter(dateFrom.atStartOfDay());
            }
        };

        return allMethodsByPredicate(dateBetweenRange);
    }

    @Override
    public boolean updatePhoto(int photoid, int albumid, String url) {
        for (int i = 0; i < size; i++) {
            if (photos[i].getPhotoid() == photoid && photos[i].getAlbumid() == albumid) {
                photos[i].setUrl(url);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }


}

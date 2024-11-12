package dao;

import model.Photo;

import java.time.LocalDate;

public interface Album {
    boolean addPhoto(Photo photo);
    boolean removePhoto(int photoid,int albumid);
    Photo getPhotoFromAlbum(int prhotoid);
    Photo[] getAllPhotoFromAlbum(int albumid);
    Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo);
    boolean updatePhoto(int photoid,int albumid,String url);
    int size();
}

package com.example.userservice.domain.service;

import com.example.userservice.domain.model.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
public interface ArtistService {

    List<Artist> getAll();
    Page<Artist> getAll(Pageable pageable);
    Artist getById(Long artistId);
    Artist update(Long artistId, Artist request);
    ResponseEntity<?> delete(Long artistId);
    Artist create(Artist artist);
    boolean existsartist(Long artistId);
    Artist setInstagramAccount(Long artistId,Artist request);
    Artist setFacebookAccount(Long artistId,Artist request);
    Artist setTwitterAccount(Long artistId,Artist request);
    Artist updateprofile(Long userId, Artist request);

    Artist updatepassword(Long userId, Artist request);
    void updatephoto(Long artistId, MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getprofileimage(Long userID);
}

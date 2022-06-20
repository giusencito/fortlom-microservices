package com.example.userservice.service;

import com.example.userservice.domain.model.entity.Artist;
import com.example.userservice.domain.model.entity.Rol;
import com.example.userservice.domain.model.enumeration.RolName;
import com.example.userservice.domain.persistence.ArtistRepository;
import com.example.userservice.domain.service.ArtistService;
import com.example.userservice.domain.service.RolService;
import com.example.userservice.shared.execption.ResourceNotFoundException;
import com.example.userservice.shared.execption.ResourcePerzonalized;
import com.example.userservice.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ArtistServiceImpl implements ArtistService {

    private static final String ENTITY = "Artist";

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    RolService rolService;
    @Override
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    @Override
    public Page<Artist> getAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public Artist getById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist update(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{

            post.setArtistfollowers(request.getArtistfollowers());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public ResponseEntity<?> delete(Long artistId) {
        return artistRepository.findById(artistId).map(post -> {
            artistRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }
    @Override
    public Artist create(Artist artist) {
        if(artistRepository.existsByUsername(artist.getUsername()))
            throw  new ResourcePerzonalized("ya exsite este nombre de usuario");
        if (artistRepository.existsByEmail(artist.getEmail()))
            throw  new ResourcePerzonalized("ya exsite este correo electronico");
        Set<Rol> roles = new HashSet<>();
        artist.setBann(false);
        roles.add(rolService.findByName(RolName.Role_Artist).get());
        artist.setRoles(roles);

        return artistRepository.save(artist);
    }

    @Override
    public boolean existsartist(Long artistId) {
        return artistRepository.existsById(artistId);
    }

    @Override
    public boolean ispremium(Long artistId) {
        return artistRepository.findById(artistId).map(post->{

            for (Rol rol:post.getRoles()){
                String r=rol.getName().name();

                if (r.equals("Premium_Artist")){
                    return true;
                }
            }
            return false;


        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist setInstagramAccount(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setInstagramLink(request.getInstagramLink());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist setFacebookAccount(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setFacebookLink(request.getFacebookLink());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist setTwitterAccount(Long artistId, Artist request) {
        return artistRepository.findById(artistId).map(post->{
            post.setTwitterLink(request.getTwitterLink());
            artistRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }

    @Override
    public Artist updateprofile(Long userId, Artist request) {
        return artistRepository.findById(userId).map(user ->{
            user.setRealname(request.getRealname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            artistRepository.save(user);
            return user;


        }).orElseThrow(() -> new ResourceNotFoundException("Person", userId));
    }

    @Override
    public Artist updatepassword(Long userId, Artist request) {
        return null;
    }

    @Override
    public void updatephoto(Long artistId, MultipartFile file) throws IOException {
        artistRepository.findById(artistId).map(post->{
            try {
                post.setContent(ImageUtility.compressImage(file.getBytes()));
                post.setImageprofiletype(file.getContentType());
                artistRepository.save(post);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }).orElseThrow(() -> new ResourceNotFoundException("User", artistId));
    }

    @Override
    public ResponseEntity<byte[]> getprofileimage(Long userID) {
        Optional<Artist> db=artistRepository.findById(userID);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(db.get().getImageprofiletype()))
                .body(ImageUtility.decompressImage(db.get().getContent()));
    }

    @Override
    public Artist  upgradeartist(Long artistId) {
        return artistRepository.findById(artistId).map(post->{
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.findByName(RolName.Premium_Artist).get());
            post.setRoles(roles);
            artistRepository.save(post);
            return post;

        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, artistId));
    }
}

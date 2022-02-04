/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.projectsocialmedia.service.impl;

import com.doranco.projectsocialmedia.dto.PublicationsDto;
import com.doranco.projectsocialmedia.dto.UtilisateurDto;
import com.doranco.projectsocialmedia.entity.Commentaire;
import com.doranco.projectsocialmedia.entity.Publication;
import com.doranco.projectsocialmedia.entity.PublicationEpreuve;
import com.doranco.projectsocialmedia.entity.PublicationMedia;
import com.doranco.projectsocialmedia.entity.Reagir;
import com.doranco.projectsocialmedia.entity.Utilisateur;
import com.doranco.projectsocialmedia.repository.PublicationRepository;
import com.doranco.projectsocialmedia.service.CurrentUserService;
import com.doranco.projectsocialmedia.service.FileUploadService;
import com.doranco.projectsocialmedia.service.PublicationService;
import com.doranco.projectsocialmedia.service.UtilisateurService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final FileUploadService uploadService;
    private final CurrentUserService currentUserService;
    private final UtilisateurService userService;
    private final String uploadDirectory = "src/main/resources/static/images/publications";
    private final String url = "images/publications";
    private final ModelMapper modelMapper;

    @Override
    public PublicationsDto findbyId(Long id) {
        return this.convertEntityToDto(publicationRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<Map<String, Object>> lister(int page, int size) {
        List<PublicationsDto> publications;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreation"));
        Page<Publication> pagePublications = publicationRepository.findAll(paging);
        publications = pagePublications.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (publications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("publications", publications);
        response.put("currentPage", pagePublications.getNumber());
        response.put("totalItems", pagePublications.getTotalElements());
        response.put("totalPages", pagePublications.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> savePublication(Publication publication, MultipartFile media) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        publication.setPublisher(currentUser);
        ResponseEntity<?> response;
        // if media try to save first 
        if (media != null) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(media.getOriginalFilename()));
            PublicationMedia publicationMedia = new PublicationMedia();
            BeanUtils.copyProperties(publication, publicationMedia);
            PublicationMedia savedPublication = publicationRepository.save(publicationMedia);
            response = ResponseEntity
                    .status(HttpStatus.CREATED).body(convertEntityToDto(savedPublication));
            //upload media and update media field
            try {
                String path = uploadDirectory + File.separator + savedPublication.getId();
                String url = this.url + File.separator + savedPublication.getId();
                uploadMedia(media, path, filename);
                savedPublication.setReferenceMedia(url + File.separator + filename);
                response = ResponseEntity
                        .status(HttpStatus.CREATED).body(convertEntityToDto(publicationRepository.save(publicationMedia)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response = ResponseEntity
                    .status(HttpStatus.CREATED).body(convertEntityToDto(publicationRepository.save(publication)));
        }
        return response;
    }

    @Override
    public ResponseEntity<?> saveEpreuvePublication(PublicationEpreuve publication) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        publication.setPublisher(currentUser);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(publicationRepository.save(publication));
    }

    @Override
    public void updatePublication(Long id, Publication publication, MultipartFile media
    ) {
        publicationRepository.findById(id).ifPresent(publicationAmodifier -> {

            if (media != null) {
                String filename = StringUtils.cleanPath(Objects.requireNonNull(media.getOriginalFilename()));
                PublicationMedia publicationMedia = new PublicationMedia();
                BeanUtils.copyProperties(publicationAmodifier, publicationMedia);
                //upload media and update media field
                try {
                    String path = uploadDirectory + File.separator + publicationAmodifier.getId();
                    String url = this.url + File.separator + publicationAmodifier.getId();
                    uploadMedia(media, path, filename);
                    publicationMedia.setReferenceMedia(url + File.separator + filename);
                    publicationMedia.setTexte(publication.getTexte());
                    publicationRepository.save(publicationMedia);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                publicationAmodifier.setTexte(publication.getTexte());

                publicationRepository.save(publicationAmodifier);
            }
        });
    }

    @Override
    public void deleteById(Long id
    ) {
        publicationRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByUtilisateur(Long idUtilisateur, int page, int size
    ) {
        List<PublicationsDto> publications;
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreation"));
        Page<Publication> pagePublications = publicationRepository.findAllByPublisher_id(idUtilisateur, paging);
        publications = pagePublications.getContent()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        if (publications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("publications", publications);
        response.put("currentPage", pagePublications.getNumber());
        response.put("totalItems", pagePublications.getTotalElements());
        response.put("totalPages", pagePublications.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAllByCurrentUtilisateur(int page, int size
    ) {
        Utilisateur currentUser = currentUserService.getCurrentUser();
        return this.findAllByUtilisateur(currentUser.getId(), page, size);
    }

    private PublicationsDto convertEntityToDto(Publication publication) {
        PublicationsDto publicationDto = new PublicationsDto();
        BeanUtils.copyProperties(publication, publicationDto);
        UtilisateurDto user = userService.convertEntityToDto(publication.getPublisher());
        publicationDto.setPublisher(user);
        List<Reagir> reaction = publication.getLikePublication();
        List<Reagir> likes = new ArrayList<>();
        List<Reagir> dislikes = new ArrayList<>();
        List<Commentaire> commentaires = new ArrayList<>();
        if (reaction != null) { 
            likes = reaction.stream()
                .filter(reagir -> reagir.getLike() == true)
                .collect(Collectors.toList());
            dislikes = reaction.stream()
                .filter(reagir -> reagir.getLike() == false)
                .collect(Collectors.toList());
        }
        commentaires = publication.getCommentairesPublication();
        
        int nLike = (likes != null) ? likes.size() : 0 ;
        int nDislike = (dislikes != null) ? dislikes.size() : 0 ;
        int nCommentaires = (commentaires != null) ? commentaires.size() : 0 ;

        publicationDto.setLike(nLike);
        publicationDto.setDislike(nDislike);
        publicationDto.setCommenatire(nCommentaires);

        return publicationDto;
    }

//    @Override
//    public ResponseEntity<Map<String, Object>> findAllByUtilisateurByVisibilite(Long idUtilisateur, Visibilite visibilite, int page, int size) {
//        List<PublicationDto> publications;
//        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreation"));
//        Page<Publication> pagePublications = publicationRepository.findAllByPublisher_idByVisibiliteIs(idUtilisateur, visibilite, paging);
//        publications = pagePublications.getContent()
//                .stream()
//                .map(this::convertEntityToDto)
//                .collect(Collectors.toList());
//
//        if (publications.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("publications", publications);
//        response.put("currentPage", pagePublications.getNumber());
//        response.put("totalItems", pagePublications.getTotalElements());
//        response.put("totalPages", pagePublications.getTotalPages());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    private void uploadMedia(MultipartFile media, String path, String filename) throws IOException {
        uploadService.saveFile(path, filename, media);
    }

}

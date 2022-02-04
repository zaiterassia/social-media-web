package com.doranco.projectsocialmedia.restcontroller;

import com.doranco.projectsocialmedia.dto.PublicationsDto;
import com.doranco.projectsocialmedia.entity.Publication;
import com.doranco.projectsocialmedia.entity.PublicationEpreuve;
import com.doranco.projectsocialmedia.service.CommentaireService;
import com.doranco.projectsocialmedia.service.PublicationService;
import com.doranco.projectsocialmedia.service.ReagirService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Assia
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/publication")
public class PublicationRestController {

    private final PublicationService publicationService;
    private final ReagirService reagirService;
    private final CommentaireService commentaireService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getListPublication(
            @RequestParam(required = false) Long idUtilisateur,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        if (idUtilisateur != null) {
            return publicationService.findAllByUtilisateur(idUtilisateur, page, size);
        } else {
            return publicationService.lister(page, size);
        }

    }

    @GetMapping("/mespublications")
    public ResponseEntity<Map<String, Object>> getMyPublicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return publicationService.findAllByCurrentUtilisateur(page, size);
    }

    @GetMapping("/{id}")
    public PublicationsDto getPublicationById(@PathVariable(value = "id") Long idPublication) {
        return publicationService.findbyId(idPublication);

    }

    @PostMapping
    public ResponseEntity<?> savePublication(
            @RequestPart("publication") Publication publication,
            @RequestPart(value = "media", required = false) MultipartFile media) {
        return publicationService.savePublication(publication, media);
    }

    @PostMapping("/epreuve")
    public ResponseEntity<?> saveEpreuvePublication(@RequestBody PublicationEpreuve publication) {
        return publicationService.saveEpreuvePublication(publication);
    }

    @PutMapping("/{id}")
    public void updatePublication(@PathVariable(value = "id") Long idPublication,
                @RequestPart Publication publication,
                @RequestPart(value = "media", required = false) MultipartFile media
                ) {
                    publicationService.updatePublication(idPublication, publication, media);

    }

    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable(value = "id") Long idPublication) {
        publicationService.deleteById(idPublication);
    }

    @GetMapping("/{id}/reactions")
    public ResponseEntity<Map<String, Object>> getListLike(
            @PathVariable(value = "id") Long idPublication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return reagirService.findAllByLikePublication(idPublication, page, size);

    }

    @GetMapping("/{id}/commentaires")
    public ResponseEntity<Map<String, Object>> getListCommentaires(
            @PathVariable(value = "id") Long idPublication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return commentaireService.findAllByLikePublication(idPublication, page, size);

    }

}

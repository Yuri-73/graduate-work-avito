package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.io.IOException;
import java.security.Principal;

/**
 * @author Yuri-73
 */
@RestController
@RequestMapping("ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
@Slf4j

@Tag(name = "Объявления", description = "Интерфейс для управления объявлениями о продаже")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401",
                description = "UNAUTHORIZED: пользователь не авторизован")})
public class AdsController {

    private final AdServiceImpl adService;

    @Operation(summary = "Получить список всех объявлений")
    @ApiResponse(
            responseCode = "200",
            description = "OK: возвращает список всех объявлений",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = AdsDTO.class)))
    )
    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Operation(summary = "Получить информацию об объявлении")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: возвращает информацию об объявлении",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExtendedAd.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT_FOUND: объявление не найдено"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAd(id));
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @ApiResponse(
            responseCode = "200",
            description = "OK: получен список объявлений авторизованного пользователя",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = AdsDTO.class)))
    )
    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsMe(authentication));
    }


    @Operation(summary = "Добавить объявление")
    @ApiResponse(
            responseCode = "201",
            description = "CREATED: объявление создано",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdDTO.class))
    )

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestPart(name = "properties") CreateOrUpdateAd properties,
                                       @RequestPart(name = "image") MultipartFile image,
                                       Principal principal) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(properties, image, principal.getName()));
    }


    @Operation(summary = "Изменить объявление")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: объявление изменено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN: роль пользователя не предоставляет доступ к данному api"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT_FOUND: объявление не найдено"
            )
    })
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.getAdUserName(#id) == authentication.principal.username")
    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAds(@PathVariable Integer id,
                                           @RequestBody CreateOrUpdateAd ad) {
        return ResponseEntity.ok(adService.updateAd(id, ad));
    }


    @Operation(summary = "Обновление картинки объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изображение успешно обновлено",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "FORBIDDEN: роль пользователя не предоставляет доступ к данному api"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NOT_FOUND: объявление не найдено"
                    )
            })
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.getAdUserName(#id) == authentication.principal.username")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable("id") Integer id,
                                         @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(adService.updateImage(id, image));
    }


    @Operation(summary = "Удалить объявление")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "NO_CONTENT: объявление удалено"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN: роль пользователя не предоставляет доступ к данному api"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT_FOUND: объявление не найдено"
            )
    })

    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.getAdUserName(#id) == authentication.principal.username")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable("id") Integer id) {
        adService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
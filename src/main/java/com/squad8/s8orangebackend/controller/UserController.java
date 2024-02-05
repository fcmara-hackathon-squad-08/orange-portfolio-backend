package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.UserUpdateDto;
import com.squad8.s8orangebackend.service.UserService;
import com.squad8.s8orangebackend.util.ConvertStringJsonToObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ConvertStringJsonToObject convertStringJsonToObject;

    @Operation(summary = "Get current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/me")
    public ResponseEntity<User> getUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Update current user image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated user",
                    content = { @Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = UserUpdateDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @Transactional
    @PutMapping(value = "/me", consumes = "multipart/form-data")
    public ResponseEntity<User> updateUser(@Schema(example = "{\"country\":\"string\"}")@RequestPart("body") String userDto, @RequestParam(value = "file", required = false) MultipartFile file
                                           ) throws IOException {
        User userUpdateDto = userService.fromUpdateDto(convertStringJsonToObject.deserialize(userDto, UserUpdateDto.class));
        userUpdateDto = userService.updateUserBasicInformation(userUpdateDto, file);
        return ResponseEntity.ok().body(userUpdateDto);
    }

}

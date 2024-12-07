package org.api.controller;

import jakarta.validation.Valid;
import org.api.entity.Video;
import org.api.model.error.CustomClientError;
import org.api.model.error.CustomServerError;
import org.api.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<Object> publishVideo(@RequestBody @Valid Video video) {
        try {
            return ResponseEntity.ok(videoService.publishVideo(video));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editMetadata(@PathVariable Integer id, @RequestBody @Valid Video video) {
        try {
            return ResponseEntity.ok(videoService.editMetadata(id, video));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delistVideo(@PathVariable Integer id) {
        try {
            videoService.delistVideo(id);
            return ResponseEntity.ok(Map.of("message", "Video delisted successfully"));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> loadVideo(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(videoService.loadVideo(id));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/play")
    public ResponseEntity<Object> playVideo(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(videoService.playVideo(id));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Object> listAllAvailableVideos() {
        try {
            return ResponseEntity.ok(videoService.listAllAvailableVideos());
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchVideos(@RequestParam String director) {
        try {
            return ResponseEntity.ok(videoService.searchVideos(director));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/engagement")
    public ResponseEntity<Object> getEngagementStats(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(videoService.getEngagementStats(id));
        } catch (CustomClientError e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (CustomServerError e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
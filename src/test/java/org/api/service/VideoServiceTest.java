package org.api.service;

import org.api.entity.Video;
import org.api.model.error.CustomClientError;
import org.api.model.error.CustomServerError;
import org.api.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    private Video video;

    @BeforeEach
    void setUp() {
        video = Video.builder()
                .title("The Shawshank Redemption")
                .synopsis("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
                .director("Frank Darabont")
                .actors("Tim Robbins, Morgan Freeman")
                .yearOfRelease(1994)
                .genre("Drama")
                .runningTime(142)
                .content("https://www.imdb.com/title/tt0111161/")
                .isDelisted(false)
                .impressions(0)
                .views(0)
                .build();
    }

    @Test
    void publishVideo() {
        when(videoRepository.findByTitle(video.getTitle())).thenReturn(Optional.empty());
        when(videoRepository.save(video)).thenReturn(video);

        Video result = videoService.publishVideo(video);

        assertEquals(video, result);
    }

    @Test
    void publishVideo_ThrowsCustomClientError() {
        when(videoRepository.findByTitle(video.getTitle())).thenReturn(Optional.of(List.of(video)));

        CustomClientError exception = assertThrows(CustomClientError.class, () -> videoService.publishVideo(video));

        assertEquals("Video already exists", exception.getMessage());
    }

    @Test
    void editMetadata() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);

        Video result = videoService.editMetadata(1, video);

        assertEquals(video, result);
    }
    @Test
    void editMetadata_ThrowsCustomServerError() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenThrow(new RuntimeException("Connection refused"));

        CustomServerError exception = assertThrows(CustomServerError.class, () -> videoService.editMetadata(1, video));
        assertEquals("Connection refused", exception.getMessage());
    }
    @Test
    void editMetadata_ThrowsCustomClientError() {
        when(videoRepository.findById(1)).thenReturn(Optional.empty());

        CustomClientError exception = assertThrows(CustomClientError.class, () -> videoService.editMetadata(1, video));

        assertEquals("Video not found", exception.getMessage());
    }

    @Test
    void delistVideo() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));

        assertDoesNotThrow(() -> videoService.delistVideo(1));
    }
    @Test
    void delistVideo_ThrowsCustomServerError() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenThrow(new RuntimeException("Connection refused"));

        CustomServerError exception = assertThrows(CustomServerError.class, () -> videoService.delistVideo(1));
        assertEquals("Connection refused", exception.getMessage());
    }
    @Test
    void delistVideo_ThrowsCustomClientError() {
        when(videoRepository.findById(1)).thenReturn(Optional.empty());

        CustomClientError exception = assertThrows(CustomClientError.class, () -> videoService.delistVideo(1));

        assertEquals("Video not found", exception.getMessage());
    }

    @Test
    void loadVideo() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));

        Video result = videoService.loadVideo(1);

        assertEquals(video, result);
    }

    @Test
    void loadVideo_ThrowsCustomClientError() {
        when(videoRepository.findById(1)).thenReturn(Optional.empty());

        CustomClientError exception = assertThrows(CustomClientError.class, () -> videoService.loadVideo(1));

        assertEquals("Video not found", exception.getMessage());
    }

    @Test
    void playVideo() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));

        String result = videoService.playVideo(1);

        assertEquals(video.getContent(), result);
    }
    @Test
    void playVideo_ThrowsCustomServerError() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenThrow(new RuntimeException("Connection refused"));

        CustomServerError exception = assertThrows(CustomServerError.class, () -> videoService.playVideo(1));
        assertEquals("Connection refused", exception.getMessage());
    }
    @Test
    void playVideo_ThrowsCustomClientError() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(Video.builder().isDelisted(true).build()));

        CustomClientError exception = assertThrows(CustomClientError.class, () -> videoService.playVideo(1));

        assertEquals("Video is delisted", exception.getMessage());
    }

    @Test
    void listAllAvailableVideos() {
        when(videoRepository.findByIsDelistedFalse()).thenReturn(Optional.of(List.of(video)));

        List<Video> result = videoService.listAllAvailableVideos();

        assertEquals(List.of(video), result);
    }

    @Test
    void searchVideos() {
        when(videoRepository.findByDirectorAndIsDelistedFalse(any())).thenReturn(Optional.of(List.of(video)));

        List<Video> result = videoService.searchVideos("Test");

        assertEquals(List.of(video), result);
    }

    @Test
    void getEngagementStats() {
        when(videoRepository.findById(1)).thenReturn(Optional.of(video));

        Map<String, Integer> result = videoService.getEngagementStats(1);

        assertEquals(Map.of("impressions", 0, "views", 0), result);
    }

    @Test
    void getEngagementStats_ThrowsCustomClientError() {
        when(videoRepository.findById(1)).thenReturn(Optional.empty());

        CustomClientError exception = assertThrows(CustomClientError.class, () -> videoService.getEngagementStats(1));

        assertEquals("Video not found", exception.getMessage());
    }
}
package org.api.service;

import org.api.entity.Video;
import org.api.model.error.CustomClientError;
import org.api.model.error.CustomServerError;
import org.api.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    /**
     * Publishes a new video.
     *
     * @param video The Video object to publish.
     * @return The published Video object after saving it.
     * @throws CustomClientError if a video with the same title already exists.
     * @throws CustomServerError if an error occurs while saving the video.
     */
    public Video publishVideo(Video video) {
        Optional<List<Video>> existingVideos = videoRepository.findByTitle(video.getTitle());
        if (existingVideos.isPresent()) {
            if (!existingVideos.get().isEmpty()) {
                throw new CustomClientError("Video already exists");

            }
        }
        return videoRepository.save(video);
    }

    /**
     * Updates the metadata of an existing video.
     *
     * @param id           The ID of the video to update.
     * @param updatedVideo A Video object containing the updated metadata.
     * @return The updated Video object after saving changes.
     * @throws CustomClientError if the video is not found.
     * @throws CustomServerError if an error occurs while saving the updated video.
     */
    public Video editMetadata(Integer id, Video updatedVideo) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new CustomClientError("Video not found"));
        video.setTitle(updatedVideo.getTitle());
        video.setSynopsis(updatedVideo.getSynopsis());
        video.setDirector(updatedVideo.getDirector());
        video.setActors(updatedVideo.getActors());
        video.setYearOfRelease(updatedVideo.getYearOfRelease());
        video.setGenre(updatedVideo.getGenre());
        video.setRunningTime(updatedVideo.getRunningTime());
        try {
            return videoRepository.save(video);
        } catch (Exception e) {
            throw new CustomServerError(e.getMessage());
        }
    }

    /**
     * Soft deletes a video by setting its isDelisted property to true.
     *
     * @param id The ID of the video to delist.
     * @throws CustomClientError if the video is not found.
     * @throws CustomServerError if an error occurs while saving the video.
     */
    public void delistVideo(Integer id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new CustomClientError("Video not found"));
        video.setIsDelisted(true);
        try {
            videoRepository.save(video);
        } catch (Exception e) {
            throw new CustomServerError(e.getMessage());
        }
    }

    /**
     * Loads a video by ID. Throws a CustomClientError if the video is not found.
     *
     * @param id The ID of the video to load.
     * @return The loaded Video object.
     * @throws CustomClientError if the video is not found.
     */
    public Video loadVideo(Integer id) {
        return videoRepository.findById(id).orElseThrow(() -> new CustomClientError("Video not found"));
    }

    /**
     * Loads a video by ID, increments its view count, and returns its content. Throws a CustomClientError if the
     * video is not found or is delisted. Throws a CustomServerError if an error occurs while saving the video.
     *
     * @param id The ID of the video to play.
     * @return The content of the video.
     * @throws CustomClientError if the video is not found or is delisted.
     * @throws CustomServerError if an error occurs while saving the video.
     */
    public String playVideo(Integer id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new CustomClientError("Video not found"));
        if (video.getIsDelisted()) {
            throw new CustomClientError("Video is delisted");
        }
        video.setViews(video.getViews() + 1);
        try {
            videoRepository.save(video);
            return video.getContent();
        } catch (Exception e) {
            throw new CustomServerError(e.getMessage());
        }
    }

    /**
     * Loads all videos that are not delisted. Throws a CustomClientError if no videos are found.
     *
     * @return A list of Video objects.
     * @throws CustomClientError if no videos are found.
     */
    public List<Video> listAllAvailableVideos() {
        return videoRepository.findByIsDelistedFalse().orElseThrow(() -> new CustomClientError("No videos found"));
    }

    /**
     * Searches for videos directed by the specified director that are not delisted.
     *
     * @param director The name of the director to search for.
     * @return A list of Video objects directed by the specified director.
     * @throws CustomClientError if no videos are found for the specified director.
     */
    public List<Video> searchVideos(String director) {
        return videoRepository.findByDirectorAndIsDelistedFalse(director).orElseThrow(() -> new CustomClientError("No videos found for the specified director"));
    }

    /**
     * Retrieves the engagement statistics of a video, including the number of impressions and views.
     *
     * @param id The ID of the video to retrieve the engagement statistics for.
     * @return A map containing the engagement statistics, with the keys "impressions" and "views".
     * @throws CustomClientError if the video is not found.
     */
    public Map<String, Integer> getEngagementStats(Integer id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new CustomClientError("Video not found"));
        Map<String, Integer> stats = new HashMap<>();
        stats.put("impressions", video.getImpressions());
        stats.put("views", video.getViews());
        return stats;
    }
}

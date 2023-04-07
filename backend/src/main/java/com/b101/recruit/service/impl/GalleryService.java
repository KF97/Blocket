package com.b101.recruit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.b101.recruit.domain.dto.GalleryDto;
import com.b101.recruit.domain.entity.Gallery;
import com.b101.recruit.domain.repository.GalleryRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GalleryService {

	private S3Service s3Service;
	
	private GalleryRepository galleryRepository;
	
	public Gallery savePost(GalleryDto galleryDto) {
		return galleryRepository.save(galleryDto.toEntity());
	}
	
	public List<GalleryDto> getList() {
        List<Gallery> galleryEntityList = galleryRepository.findAll();
        List<GalleryDto> galleryDtoList = new ArrayList<>();

        for (Gallery galleryEntity : galleryEntityList) {
            galleryDtoList.add(convertEntityToDto(galleryEntity));
        }

        return galleryDtoList;
    }

	private GalleryDto convertEntityToDto(Gallery gallery) {
        return GalleryDto.builder()
                .id(gallery.getId())
                .title(gallery.getTitle())
                .filePath(gallery.getFilePath())
                .imgFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + gallery.getFilePath())
                .build();
    }

	public Optional<Gallery> getGallery(Long gId) {
		return galleryRepository.findById(gId);
	}

	public void deleteGallery(Long id) {
		galleryRepository.deleteById(id);
	}

	public Gallery findByPidAndSidAndSortation(Long pid, Long sid, String sortation) {
		return galleryRepository.findByPidAndSidAndSortation(pid, sid, sortation);
	}

	public Optional<Gallery> getPropImg(Long pid, String sortation) {
		return galleryRepository.findByPidAndSortation(pid, sortation);
	}


}

package com.b101.recruit.domain.dto;

import com.b101.recruit.domain.entity.Gallery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GalleryDto {

	private Long id;
	private String title;
	private String filePath;
	private String imgFullPath;
	private Long pid;
	private Long sid;
	private String sortation;
	
	public Gallery toEntity(){
        Gallery build = Gallery.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .pid(pid)
                .sid(sid)
                .sortation(sortation)
                .build();
        return build;
    }

    @Builder
    public GalleryDto(Long id, String title, String filePath, String imgFullPath, Long pid, Long sid, String sortation) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
        this.pid = pid;
        this.sid = sid;
        this.sortation = sortation;

    }
	
}

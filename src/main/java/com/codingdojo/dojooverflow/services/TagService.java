package com.codingdojo.dojooverflow.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.dojooverflow.models.Tag;
import com.codingdojo.dojooverflow.repositories.TagRepository;

@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;
	
	public List<Tag> allTags() {
		return tagRepository.findAll();
	}
	
	public Tag createTag(Tag tag) {
		return tagRepository.save(tag);
	}
	
	public Tag findTag(Long id) {
		Optional<Tag> optionalTag = tagRepository.findById(id);
		if (optionalTag.isPresent()) {
			return optionalTag.get();
		} else {
			return null;
		}
	}
}

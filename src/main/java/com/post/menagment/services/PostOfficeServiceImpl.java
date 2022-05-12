package com.post.menagment.services;

import com.post.menagment.dto.PostOffice;
import com.post.menagment.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostOfficeServiceImpl implements PostOfficeService{

    private PostOfficeRepository postOfficeRepository;

    @Autowired
    public PostOfficeServiceImpl(PostOfficeRepository postOfficeRepository) {
        this.postOfficeRepository = postOfficeRepository;
    }

    @Override
    public boolean isPostOfficeAvailable(Long id) {
        if(postOfficeRepository.existsById(id)) {
            PostOffice postOffice = postOfficeRepository.getById(id);
            return postOffice.getIsWorking();
        }
        else return false;
    }

    @Override
    public void create(PostOffice postOffice) {
        postOfficeRepository.save(postOffice);
    }

    @Override
    public void update(PostOffice postOffice) {
        postOfficeRepository.save(postOffice);
    }
}

package com.post.menagment.services;

import com.post.menagment.model.PostOffice;
import com.post.menagment.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostOfficeServiceImpl implements PostOfficeService{

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Override
    public boolean isPostOfficeAvailable(long postOfficeId) {
        if(postOfficeRepository.existsById(postOfficeId)) {
            PostOffice postOffice = postOfficeRepository.getById(postOfficeId);
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

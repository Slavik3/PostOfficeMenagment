package com.post.menagment.services;

import com.post.menagment.dto.PostOffice;


public interface PostOfficeService {
    public boolean isPostOfficeAvailable(Long id);
    public void create(PostOffice postOffice);
    public void update(PostOffice postOffice);
}

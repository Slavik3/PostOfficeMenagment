package com.post.menagment.services;

import com.post.menagment.model.PostOffice;


public interface PostOfficeService {
    public boolean isPostOfficeAvailable(long postOfficeId);
    public void create(PostOffice postOffice);
    public void update(PostOffice postOffice);
}

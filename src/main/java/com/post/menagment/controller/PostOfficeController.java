package com.post.menagment.controller;

import com.post.menagment.dto.PostOffice;
import com.post.menagment.services.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostOfficeController {

    private PostOfficeService postOfficeService;

    @Autowired
    public PostOfficeController(PostOfficeService postOfficeService) {
        this.postOfficeService = postOfficeService;
    }

    @RequestMapping(value = "/post_office", method = RequestMethod.POST)
    public HttpStatus addNewPostOffice(@RequestBody PostOffice postOffice) {
        postOfficeService.create(postOffice);
        return HttpStatus.CREATED;
    }

    @RequestMapping(value = "/post_office", method = RequestMethod.PUT)
    public HttpStatus updatePostOffice(@RequestBody PostOffice postOffice) {
        postOfficeService.update(postOffice);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/post_office/{id}/availability", method = RequestMethod.GET)
    public boolean isPostOfficeAvailable(@PathVariable Long id) {
        return postOfficeService.isPostOfficeAvailable(id);
    }


}

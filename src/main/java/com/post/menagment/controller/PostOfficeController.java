package com.post.menagment.controller;

import com.post.menagment.dto.PostOfficeDTO;
import com.post.menagment.model.PostOffice;
import com.post.menagment.repository.PostOfficeRepository;
import com.post.menagment.services.PostOfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostOfficeController {

    private PostOfficeService postOfficeService;
    private ModelMapper modelMapper;
    private PostOfficeRepository postOfficeRepository;

    @Autowired
    public PostOfficeController(PostOfficeService postOfficeService, ModelMapper modelMapper, PostOfficeRepository postOfficeRepository) {
        this.postOfficeService = postOfficeService;
        this.modelMapper = modelMapper;
        this.postOfficeRepository = postOfficeRepository;
    }

    @RequestMapping(value = "/post-office", method = RequestMethod.POST)
    public HttpStatus addNewPostOffice(@RequestBody PostOfficeDTO postOfficeDTO) {
        PostOffice postOffice = modelMapper.map(postOfficeDTO, PostOffice.class);
        postOfficeService.create(postOffice);
        return HttpStatus.CREATED;
    }

    @RequestMapping(value = "/post-office", method = RequestMethod.PUT)
    public HttpStatus updatePostOffice(@RequestBody PostOfficeDTO postOfficeDTO) {
        PostOffice postOffice = modelMapper.map(postOfficeDTO, PostOffice.class);
        postOfficeService.update(postOffice);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/post_office/{id}/availability", method = RequestMethod.GET)
    public boolean isPostOfficeAvailable(@PathVariable Long id) {
        return postOfficeService.isPostOfficeAvailable(id);
    }


}

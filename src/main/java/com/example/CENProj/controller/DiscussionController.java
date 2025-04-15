package com.example.CENProj.controller;

import com.example.CENProj.model.Discussion;
import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.User;
import com.example.CENProj.repository.DiscussionRepository;
import com.example.CENProj.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "discussion")
@RequiredArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;

    @ModelAttribute("loggedInUser")
    public User populateTypes() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            UserDto userDto = (UserDto) authentication.getPrincipal();
            return userDto.getUser();
        } catch (Exception ignored) {}
        return null;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Discussion> discussions = this.discussionService.getAllDiscussions();
        model.addAttribute("discussions", discussions);
        return "discussion/index";
    }

    @GetMapping({"/view/{id}/", "/view/{id}"})
    public String getDiscussionById(@PathVariable(value = "id")int id, Model model) {
        Optional<Discussion> discussionOptional = this.discussionService.getDiscussionById(id);
        Discussion discussion = discussionOptional.orElse(null);
        model.addAttribute("discussion", discussion);
        return "discussion/view";
    }

    @PostMapping("/")
    public RedirectView createPost(@RequestParam(value = "title")String title, @RequestParam(value = "content") String content,
                                    RedirectAttributes redirectAttributes) {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            UserDto userDto = (UserDto) authentication.getPrincipal();
            this.discussionService.createDiscussion(title, content, userDto.getUser());
            List<Discussion> discussions = this.discussionService.getAllDiscussions();
            redirectAttributes.addFlashAttribute("discussions", discussions);
        } catch(Exception ignored) {

        }

        return new RedirectView("/discussion");
    }

    @PostMapping("/comment/")
    public RedirectView createPost(@RequestParam(value = "comment") String comment, @RequestParam(value = "id") int discussionId,
                                   RedirectAttributes redirectAttributes) {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            UserDto userDto = (UserDto) authentication.getPrincipal();
            this.discussionService.createComment(comment, discussionId, userDto.getUser());
            List<Discussion> discussions = this.discussionService.getAllDiscussions();
            redirectAttributes.addFlashAttribute("discussions", discussions);
        } catch(Exception ignored) {

        }

        return new RedirectView("/discussion/view/" + discussionId);
    }

}

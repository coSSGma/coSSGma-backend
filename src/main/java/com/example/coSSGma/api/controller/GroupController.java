package com.example.coSSGma.api.controller;

import com.example.coSSGma.api.dto.GroupBoardRequest;
import com.example.coSSGma.api.dto.GroupCreateRequest;
import com.example.coSSGma.api.service.GroupService;
import com.example.coSSGma.db.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public void createGroup(@RequestBody GroupCreateRequest request) {
        groupService.createGroup(request);
    }

    @GetMapping("/my")
    public List<Group> getMyGroups(@RequestParam Long userId) {
        return groupService.getGroupsByUser(userId);
    }

    @PostMapping("/{groupId}/join")
    public void joinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        groupService.joinGroup(groupId, userId);
    }

    @PostMapping("/{groupId}/quit")
    public void quitGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        groupService.quitGroup(groupId, userId);
    }

    @PostMapping("/{groupId}/chat")
    public void writeChat(@PathVariable Long groupId, @RequestBody GroupBoardRequest request) {
        groupService.writeChat(groupId, request);
    }
}
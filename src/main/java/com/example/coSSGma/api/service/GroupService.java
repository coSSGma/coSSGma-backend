package com.example.coSSGma.api.service;

import com.example.coSSGma.api.dto.GroupBoardRequest;
import com.example.coSSGma.api.dto.GroupCreateRequest;
import com.example.coSSGma.db.entity.Group;
import com.example.coSSGma.db.entity.GroupBoard;
import com.example.coSSGma.db.repository.GroupBoardRepository;
import com.example.coSSGma.db.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupBoardRepository groupBoardRepository;

    public void createGroup(GroupCreateRequest request) {
        Group group = Group.builder()
                .creatorUserId(request.getCreatorUserId())
                .name(request.getName())
                .subject(request.getSubject())
                .mode(request.getMode())
                .description(request.getDescription())
                .memberUserIds(String.valueOf(request.getCreatorUserId()))
                .build();
        groupRepository.save(group);
    }

    public List<Group> getGroupsByUser(Long userId) {
        return groupRepository.findAll().stream()
                .filter(g -> g.getMemberUserIdList().contains(userId))
                .toList();
    }

    public void joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
        group.addMember(userId);
        groupRepository.save(group);
    }

    public void quitGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
        group.removeMember(userId);
        groupRepository.save(group);
    }

    public void writeChat(Long groupId, GroupBoardRequest request) {
        GroupBoard chat = GroupBoard.builder()
                .groupId(groupId)
                .userId(request.getUserId())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        groupBoardRepository.save(chat);
    }
}

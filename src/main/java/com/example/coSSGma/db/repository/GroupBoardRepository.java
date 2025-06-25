package com.example.coSSGma.db.repository;

import com.example.coSSGma.db.entity.GroupBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {

    // 그룹별 채팅 목록 조회용 (선택)
    List<GroupBoard> findByGroupIdOrderByCreatedAtAsc(Long groupId);
}

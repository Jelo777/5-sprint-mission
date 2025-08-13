package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readStatus")
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @RequestMapping(path = "create", method = RequestMethod.POST)
    public ResponseEntity<ReadStatus> createReadStatus(
            @RequestBody ReadStatusCreateRequest readStatusCreateRequest) {
        ReadStatus readStatus = readStatusService.create(readStatusCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(readStatus);
    }

    @RequestMapping(path = "update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReadStatus> updateReadStatus(
            @PathVariable UUID id, @RequestBody ReadStatusUpdateRequest readStatusUpdateRequest) {
        ReadStatus readStatus = readStatusService.update(id, readStatusUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(readStatus);
    }

    @RequestMapping(path = "findAll/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ReadStatus>> findAllReadStatus(@PathVariable UUID id) {
        List<ReadStatus> readStatusList = readStatusService.findAllByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(readStatusList);
    }
}

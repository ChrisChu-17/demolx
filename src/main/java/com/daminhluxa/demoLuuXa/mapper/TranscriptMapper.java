package com.daminhluxa.demoLuuXa.mapper;
import com.daminhluxa.demoLuuXa.dto.MajorCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.MajorResponse;
import com.daminhluxa.demoLuuXa.dto.response.TranscriptResponse;
import com.daminhluxa.demoLuuXa.dto.transcript.TranscriptRequest;
import com.daminhluxa.demoLuuXa.entity.Major;
import com.daminhluxa.demoLuuXa.entity.Transcript;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TranscriptMapper {
    Transcript toTranscript(TranscriptRequest request);

    void updateTranscript(@MappingTarget Transcript transcript, TranscriptRequest request);

    TranscriptResponse toTranscriptResponse(Transcript transcript);
}

package com.addi.codetest.addicodetestdevelop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProspective {

    private String typeId;

    private String id;

    private String dateIssue;

    private String fullName;


}

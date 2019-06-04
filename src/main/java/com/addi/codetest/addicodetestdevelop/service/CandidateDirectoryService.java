package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.exception.RepublicSystemServiceException;
import com.addi.codetest.addicodetestdevelop.model.CandidateProspective;

public interface CandidateDirectoryService {

    boolean addNewCandidateProspect(CandidateProspective candidate) throws RepublicSystemServiceException;

    Integer candidateInternalRanking(CandidateProspective candidate);
}

package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.exception.RepublicSystemServiceException;
import com.addi.codetest.addicodetestdevelop.model.CandidateProspective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CandidateDirectoryServiceImpl implements CandidateDirectoryService {

    @Autowired
    private RepublicSystemService republicSystemService;

    /**
     * Temporally storage collection for prospects (this could be replaced by an storage solution
     **/
    private List<CandidateProspective> candidateProspectives = new ArrayList<>();

    @Override
    public boolean addNewCandidateProspect(CandidateProspective candidate) throws RepublicSystemServiceException {
        if (validateCandidateProspetive(candidate)) {
            candidateProspectives.add(candidate);
            return true;
        }
        return false;
    }

    @Override
    public Integer candidateInternalRanking(CandidateProspective candidate) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(100) + 1;
    }

    private boolean validateCandidateProspetive(CandidateProspective candidate) {
        if (candidate.getId() == null || candidate.getId().isEmpty()) {
            return false;
        } else if (candidate.getTypeId() == null || candidate.getTypeId().isEmpty()) {
            return false;
        } else if (candidate.getDateIssue() == null || candidate.getDateIssue().isEmpty()) {
            return false;
        } else if (candidate.getFullName() == null || candidate.getFullName().isEmpty()) {
            return false;
        }

        return true;
    }
}

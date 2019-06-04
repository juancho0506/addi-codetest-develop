package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.exception.LawCriminalHIstoryServiceException;

public interface LawCriminalRepublicService {

    Boolean isClean(final String typeId, final String id) throws LawCriminalHIstoryServiceException;
}

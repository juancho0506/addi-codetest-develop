package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.exception.RepublicSystemServiceException;

public interface RepublicSystemService {

    Boolean exists(final String typeId, final String id) throws RepublicSystemServiceException;
}

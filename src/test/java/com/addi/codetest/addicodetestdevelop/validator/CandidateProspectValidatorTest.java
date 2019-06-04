package com.addi.codetest.addicodetestdevelop.validator;

import com.addi.codetest.addicodetestdevelop.exception.CandidateValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CandidateProspectValidatorTest {

    @Before
    public void setUp() {

    }

    @Test
    public void validateIDTypePassport() throws CandidateValidationException {
        //Given
        final String type = "Passport";

        //execute
        boolean result = CandidateProspectValidator.validateIDType(type);

        //assert
        assertThat(result).isTrue();
    }

    @Test
    public void validateIDTypeCC() throws CandidateValidationException {
        //Given
        final String type = "cc";

        //execute
        boolean result = CandidateProspectValidator.validateIDType(type);

        //assert
        assertThat(result).isTrue();
    }

    @Test(expected = CandidateValidationException.class)
    public void validateIDTypeTI() throws CandidateValidationException {
        //Given
        final String type = "ti";
        //execute
        CandidateProspectValidator.validateIDType(type);

    }

    @Test
    public void validateDateSuccess() throws CandidateValidationException {
        //Given
        final String date = "1999-12-12";

        //execute
        String result = CandidateProspectValidator.validateDate(date);

        //assert
        assertThat(result).isEqualTo(date);
    }

    @Test(expected = CandidateValidationException.class)
    public void validateDateIvalid() throws CandidateValidationException {
        //Given
        final String date = "1999999ghg";

        //execute
        String result = CandidateProspectValidator.validateDate(date);

        //assert
        assertThat(result).isEqualTo(date);
    }
}

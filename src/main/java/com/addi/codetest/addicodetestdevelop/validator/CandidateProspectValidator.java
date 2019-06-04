package com.addi.codetest.addicodetestdevelop.validator;

import com.addi.codetest.addicodetestdevelop.exception.CandidateValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class CandidateProspectValidator {

    private static final String ID_TYPE_CC = "CC";
    private static final String ID_TYPE_TI = "TI";
    private static final String ID_TYPE_CE = "CE";
    private static final String ID_TYPE_PASSPORT = "PASSPORT";

    //private static final String DATE_FORMAT = "dd/MM/YYYY";

    /**
     * Validates a typeid according to standards.
     *
     * @param type
     * @return
     * @throws CandidateValidationException
     */
    public static boolean validateIDType(final String type) throws CandidateValidationException {

        switch (type.toUpperCase()) {
            case ID_TYPE_CC:
            case ID_TYPE_CE:
            case ID_TYPE_PASSPORT:
                return true;
            case ID_TYPE_TI:
                throw new CandidateValidationException("Only users over 18 years old are allowed!");
            default:
                throw new CandidateValidationException("Invalid document type!");
        }
    }

    /**
     * Validates a given date with the system format.
     *
     * @param date
     * @return
     */
    public static String validateDate(final String date) throws CandidateValidationException {
        try {
            LocalDate local = LocalDate.parse(date);
            return local.toString();
        } catch (DateTimeParseException ex) {
            throw new CandidateValidationException("Invalid date format or date value.");
        }

    }

}

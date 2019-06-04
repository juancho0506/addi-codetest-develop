package com.addi.codetest.addicodetestdevelop;

import com.addi.codetest.addicodetestdevelop.exception.CandidateValidationException;
import com.addi.codetest.addicodetestdevelop.exception.LawCriminalHIstoryServiceException;
import com.addi.codetest.addicodetestdevelop.exception.RepublicSystemServiceException;
import com.addi.codetest.addicodetestdevelop.model.CandidateProspective;
import com.addi.codetest.addicodetestdevelop.service.CandidateDirectoryService;
import com.addi.codetest.addicodetestdevelop.service.CandidateMockService;
import com.addi.codetest.addicodetestdevelop.service.LawCriminalRepublicService;
import com.addi.codetest.addicodetestdevelop.service.RepublicSystemService;
import com.addi.codetest.addicodetestdevelop.validator.CandidateProspectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class AddiCodetestDevelopApplication implements CommandLineRunner {

    @Autowired
    private CandidateDirectoryService candidateService;

    @Autowired
    private RepublicSystemService republicSystemService;

    @Autowired
    private LawCriminalRepublicService lawCriminalRepublicService;

    @Autowired
    private CandidateMockService mockService;

    public static void main(String[] args) {
        SpringApplication.run(AddiCodetestDevelopApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        //Start mock server
        mockService.launchWireMockServer();

        Scanner sc = new Scanner(System.in);
        System.out.println("*********** Addi Contact Manager Validation **************");
        System.out.println("To add new contact enter details below: ");

        System.out.println("Enter Type of ID (CC, TI, CE or Passport): ");
        String typeId = "";
        typeId = sc.next();
        System.out.println("Entered typeID: " + typeId);
        try {
            CandidateProspectValidator.validateIDType(typeId);
        } catch (CandidateValidationException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }


        System.out.println("Enter ID number: ");
        String id = "";
        sc = new Scanner(System.in);
        id = sc.next();
        System.out.println("Entered ID: " + id);

        System.out.println("Enter Date of issue (yyyy-MM-dd: ");
        String dateIssue = "";
        sc = new Scanner(System.in);
        dateIssue = sc.next();
        System.out.println("Entered Date: " + dateIssue);

        try {
            CandidateProspectValidator.validateDate(dateIssue);
        } catch (CandidateValidationException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("Enter Full Name : ");
        String fullName = "";
        sc = new Scanner(System.in);
        fullName = sc.nextLine();
        System.out.println("Entered Full Name: " + fullName);

        CandidateProspective candidate = new CandidateProspective().builder()
                .id(id)
                .typeId(typeId)
                .dateIssue(dateIssue)
                .fullName(fullName)
                .build();


        if (candidateService.addNewCandidateProspect(candidate)) {
            System.out.println("INFO: Candidate added to pre-validation process, we'll notify you if is " +
                    "successfully added to the system.");
            executeCandidateValidationAndRegistration(candidate);

        } else {
            System.out.println("ERROR: Candidate cant't be added to the pre-validation process.");
            System.exit(0);
        }
    }

    private void executeCandidateValidationAndRegistration(CandidateProspective candidate) {
        //Call the republic registration service:
        CompletableFuture<Boolean> existsRegistrationFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Checking into the Republic System for valid registration...");
                return republicSystemService.exists(candidate.getTypeId(), candidate.getId());
            } catch (RepublicSystemServiceException e) {
                System.out.println("ERROR validating the candidate on the republic system: " + e.getMessage());
                return false;
            }
        });

        //Call the law and criminology history service:
        CompletableFuture<Boolean> isCleanLawCriminalFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Checking into the Law and Criminal registration system if status is clean...");
                return lawCriminalRepublicService.isClean(candidate.getTypeId(), candidate.getId());
            } catch (LawCriminalHIstoryServiceException e) {
                System.out.println("ERROR validating the candidate on the law and criminal republic system: " + e.getMessage());
                return false;
            }
        });

        //Combine the two futures..

        CompletableFuture<Boolean> combinedResults = existsRegistrationFuture
                .thenCombine(isCleanLawCriminalFuture, (exists, isClean) -> {
                    if (exists && isClean) {
                        return true;
                    } else {
                        return false;
                    }
                });

        combinedResults.thenAccept(passTwoValidations -> {
            if (passTwoValidations) {
                System.out.println("INFO: Checking candidate on internal ranking....");
                Integer ranking  = candidateService.candidateInternalRanking(candidate);
                System.out.println("INFO: Candidate ranking on internal system: " + ranking);
                if (ranking > 60){
                    System.out.println("INFO: Success !! Candidate has passed all required validations and has been added to the contact directory.");
                }else {
                    System.out.println("INFO: Candidate has not passed all validations will not be added to directory... :(");
                }
            }
            System.exit(0);
        });
    }
}

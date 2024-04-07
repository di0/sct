package br.com.tiviati.sct.service;

import br.com.tiviati.sct.commons.Validator;
import br.com.tiviati.sct.exception.InvalidParameterException;
import br.com.tiviati.sct.exception.NotFoundException;
import br.com.tiviati.sct.model.Beneficiary;
import br.com.tiviati.sct.model.Document;
import br.com.tiviati.sct.repository.BeneficiaryRepository;
import br.com.tiviati.sct.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static br.com.tiviati.sct.commons.Constants.BENEFICIARY_NOT_EXISTS;
import static br.com.tiviati.sct.commons.Constants.DOCUMENT_NOT_EXISTS;
import static br.com.tiviati.sct.commons.Constants.INVALID_BIRTHDAY;
import static br.com.tiviati.sct.commons.Constants.INVALID_CPF;
import static br.com.tiviati.sct.commons.Constants.INVALID_PHONE_NUMBER;

@Service
@Transactional
public class SctService {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private DocumentRepository documentRepository;

    public List<Beneficiary> findAll() {
        return beneficiaryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Beneficiary findById(final Long id) {
        return beneficiaryRepository.findById(id).orElseThrow(() -> new NotFoundException(
                BENEFICIARY_NOT_EXISTS + id));
    }

    @Transactional
    public Beneficiary create(final Beneficiary beneficiary) {
        validateBeneficiaryParameters(beneficiary);

        final Beneficiary newBeneficiary =  Beneficiary.builder()
                .name(beneficiary.getName())
                .cpf(beneficiary.getCpf())
                .birthDate(beneficiary.getBirthDate())
                .contactNumber(beneficiary.getContactNumber())
                .build();

        final Beneficiary savedBeneficiary = beneficiaryRepository.save(newBeneficiary);
        beneficiary.getDocuments().forEach(document -> {
            document.setBeneficiary(savedBeneficiary);
            documentRepository.save(document);
        });
        return savedBeneficiary;
    }

    @Transactional
    public Beneficiary update(final Long id, final Beneficiary beneficiary) {
        validateBeneficiaryParameters(beneficiary);
        final Beneficiary beneficiaryExistent = this.findById(id);

        beneficiary.getDocuments().forEach((document) -> {
            Document documentFound = documentRepository.findById(document.getId())
                    .orElseThrow(() -> new NotFoundException(DOCUMENT_NOT_EXISTS));

            documentFound.setDescription(document.getDescription());
            documentFound.setDocumentType(document.getDocumentType());
            documentFound.setBeneficiary(beneficiaryExistent);
            documentRepository.save(documentFound);
        });

        beneficiaryExistent.setName(beneficiary.getName());
        beneficiaryExistent.setCpf(beneficiary.getCpf());
        beneficiaryExistent.setBirthDate(beneficiary.getBirthDate());
        beneficiaryExistent.setContactNumber(beneficiary.getContactNumber());
        return beneficiaryRepository.save(beneficiaryExistent);
    }

    public void delete(final Long id) {
        final Beneficiary beneficiary = this.findById(id);
        beneficiaryRepository.delete(beneficiary);
    }

    public Set<Document> findAllDocumentsByBeneficiaryId(final Long id) {
        return documentRepository.findByBeneficiaryId(id);
    }

    private void validateBeneficiaryParameters(final Beneficiary beneficiary) {
        if (!Validator.isValidCpf(beneficiary.getCpf())) {
            throw new InvalidParameterException(INVALID_CPF);
        }

        if (!Validator.isValidTelephoneNumberFormat(beneficiary.getContactNumber())) {
            throw new InvalidParameterException(INVALID_PHONE_NUMBER);
        }

        if (!Validator.isDateBirthOk(beneficiary.getBirthDate())) {
            throw new InvalidParameterException(INVALID_BIRTHDAY);
        }
    }
}

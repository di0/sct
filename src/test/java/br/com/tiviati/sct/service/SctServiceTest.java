package br.com.tiviati.sct.service;

import br.com.tiviati.sct.model.Beneficiary;
import br.com.tiviati.sct.repository.BeneficiaryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SctServiceTest {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Test
    public void shouldSaveBeneficiary() {
        final String fakeCpf = "133.133.133-40";
        final Beneficiary beneficiary = Beneficiary.builder()
                .name("foo")
                .cpf(fakeCpf)
                .contactNumber("11 9999 8888")
                .birthDate(new Date())
                .build();
        Long id = beneficiaryRepository.save(beneficiary).getId();
        Beneficiary beneficiaryFound = beneficiaryRepository.findById(id).orElseThrow();
        Assert.assertEquals(fakeCpf, beneficiaryFound.getCpf());
    }

    @Test
    public void shouldUpdateBeneficiary() {
        final String fakeCpf = "133.133.133-41";
        final Beneficiary beneficiary = Beneficiary.builder()
                .name("foo")
                .cpf(fakeCpf)
                .contactNumber("11 9999 8888")
                .birthDate(new Date())
                .build();

        Long id = beneficiaryRepository.save(beneficiary).getId();
        Beneficiary beneficiaryFound = beneficiaryRepository.findById(id).orElseThrow();
        Assert.assertEquals(fakeCpf, beneficiaryFound.getCpf());

        final String anotherFakeCpf = "333.444.555-40";
        beneficiaryFound.setCpf(anotherFakeCpf);

        id = beneficiaryRepository.save(beneficiaryFound).getId();

        beneficiaryFound = beneficiaryRepository.findById(id).orElseThrow();
        Assert.assertEquals(anotherFakeCpf, beneficiaryFound.getCpf());
    }

    @Test
    public void shouldDeleteBeneficiary() {
        final String fakeCpf = "133.133.133-42";
        final Beneficiary beneficiary = Beneficiary.builder()
                .name("foo")
                .cpf(fakeCpf)
                .contactNumber("11 9999 8888")
                .birthDate(new Date())
                .build();

        Long id = beneficiaryRepository.save(beneficiary).getId();
        Beneficiary beneficiaryFound = beneficiaryRepository.findById(id).orElseThrow();
        Assert.assertEquals(fakeCpf, beneficiaryFound.getCpf());

        beneficiaryRepository.delete(beneficiary);
        assertNull(beneficiaryRepository.findById(1L).orElse(null));
    }
}

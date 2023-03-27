package com.blockchain.registry.service;

import com.blockchain.registry.exception.ResourceNotFoundException;
import com.blockchain.registry.model.Contract;
import com.blockchain.registry.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public Contract getContract(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
    }

    public Contract updateContract(Long id, Contract contract) {
        Contract existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        existingContract.setTitle(contract.getTitle());
        existingContract.setDescription(contract.getDescription());
        existingContract.setStatus(contract.getStatus());

        return contractRepository.save(existingContract);
    }

    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
}

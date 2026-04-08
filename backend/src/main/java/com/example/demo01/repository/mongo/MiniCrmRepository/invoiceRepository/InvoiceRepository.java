package com.example.demo01.repository.mongo.MiniCrmRepository.invoiceRepository;

import com.example.demo01.configs.SecureRepoConfig.BaseRepository;
import com.example.demo01.domains.MiniCrm.Invoice.model.Invoice;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends BaseRepository<Invoice, String>, InvoiceCustomRepository {

}

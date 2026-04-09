package com.example.demo01.repository.mongo.MiniCrmRepository.invoiceRepository;

import com.example.demo01.configs.Mongo.SecureRepoConfig.BaseRepository;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.model.Invoice;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends BaseRepository<Invoice, String>, InvoiceCustomRepository {

}

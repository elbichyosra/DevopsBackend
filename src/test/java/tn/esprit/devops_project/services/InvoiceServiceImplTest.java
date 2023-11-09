package tn.esprit.devops_project.services;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class InvoiceServiceImplTest {

   @Autowired
   private InvoiceServiceImpl invoiceService;

   @Autowired
   private InvoiceRepository invoiceRepository;

   @Autowired
   private OperatorRepository operatorRepository;

   @Autowired
   private SupplierRepository supplierRepository;
   @Autowired
   SupplierServiceImpl supplierService;
   @Autowired
   private OperatorServiceImpl operatorService;
   @Test
   void retrieveAllInvoices() {
       Invoice invoice = new Invoice(1L, 100, 200 ,new Date(), new Date(), false, null, null);
       Invoice invoice1 = new Invoice(2L, 300, 400 ,new Date(), new Date(), false, null, null);
       invoice = invoiceRepository.save(invoice);
       invoice1 = invoiceRepository.save(invoice1);
       List<Invoice> invoices = invoiceService.retrieveAllInvoices();
       assertNotNull(invoices);
       assertFalse(invoices.isEmpty());
       assertTrue(invoices.size() >= 2);
   }

   @Test

   void cancelInvoice() {
       // Créer des données de test
       Invoice invoice = new Invoice(3L, 500, 600 ,new Date(), new Date(), false, null, null);
       invoice.setArchived(false);

       // Ajouter la facture à la base de données pour les tests
       invoice = invoiceRepository.save(invoice);

       // Appel de la méthode
       invoiceService.cancelInvoice(invoice.getIdInvoice());

       // Vérification
       assertTrue(invoice.getArchived()==true);
   }

   @Test

   void retrieveInvoice() {
       // Créer des données de test
       Invoice invoice = new Invoice(8L, 50, 200, new Date(), new Date(), false, null, null);
       invoice =invoiceService.addInvoice(invoice);

       // Appel de la méthode
       Invoice result = invoiceService.retrieveInvoice(invoice.getIdInvoice());

       // Vérification
       assertNotNull(result);
       assertEquals(invoice.getIdInvoice(), result.getIdInvoice());

   }
   @Test

   void addInvoice() {
       Invoice invoice = new Invoice(5L, 350, 600, new Date(),new Date(), false, null, null);

       Invoice invTest = invoiceService.addInvoice(invoice);

       assertNotNull(invTest);
       // Ajoutez d'autres assertions au besoin pour vérifier les propriétés de l'invoice ajouté
   }

   @Test

   void getInvoicesBySupplier() {
       // Créer des données de test
       Supplier supplier = new Supplier(1L, "code1", "label1", SupplierCategory.ORDINAIRE, null, null);
       supplier = supplierService.addSupplier(supplier);
       Invoice invoice = new Invoice(11L, 100, 200 ,new Date(), new Date(), false, null, supplier);
       Invoice invoice1 = new Invoice(12L, 300, 400 ,new Date(), new Date(), false, null, supplier);
       invoice = invoiceService.addInvoice(invoice);
       invoice1 = invoiceService.addInvoice(invoice1);

       List<Invoice>invoices=new ArrayList<Invoice>();
       invoices.add(invoice);
       invoices.add(invoice1);
       supplier.setInvoices(invoices);
       supplier = supplierRepository.save(supplier);

       // Appel de la méthode
       List<Invoice> listinvoices = invoiceService.getInvoicesBySupplier(supplier.getIdSupplier());

       // Vérification

       assertFalse(listinvoices.isEmpty());
   }
   @Test

   void assignOperatorToInvoice() {
       // Créez des données de test
       Operator operator = new Operator(2L,"yosra", "elbich", "yossra123");
       operator = operatorService.addOperator(operator);

       Invoice invoice = new Invoice(13L, 10, 100, new Date(), new Date(), false, null, null);
       invoice = invoiceService.addInvoice(invoice);

       // Appel de la méthode
       invoiceService.assignOperatorToInvoice(operator.getIdOperateur(), invoice.getIdInvoice());

       // Vérification
       Operator updatedOperator = operatorRepository.findById(operator.getIdOperateur()).orElse(null);
       Invoice updatedInvoice = invoiceRepository.findById(invoice.getIdInvoice()).orElse(null);

       assertNotNull(updatedOperator);
       assertNotNull(updatedInvoice);
       assertTrue(updatedOperator.getInvoices().contains(updatedInvoice));
   }




}


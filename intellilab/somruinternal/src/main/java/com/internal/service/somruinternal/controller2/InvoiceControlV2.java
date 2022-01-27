package com.internal.service.somruinternal.controller2;

import java.util.List;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.utils.SearchUtil;

@RestController
@RequestMapping("/invoicev2")
public class InvoiceControlV2 extends ParentControl {

	InvoiceRepositoryV2 invoiceRepo;

	SalesNumberSequenceRepository salesNumRepo;

	private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceControlV2.class);

	@Autowired
	public InvoiceControlV2(InvoiceRepositoryV2 invoiceRepo, SalesNumberSequenceRepository salesNumRepo) {
		super();
		this.invoiceRepo = invoiceRepo;
		this.salesNumRepo = salesNumRepo;

	}

	@GetMapping("/all/{page}/{active}")
	public List<InvoiceV2> getInvoicesByPage(@PathVariable(value = "page") int page,
			@PathVariable(value = "active") boolean active) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info("getInvoicesByPage");
		List<InvoiceV2> invoiceList;
		if (active)
			invoiceList = invoiceRepo.findAllActiveByPage(pageable);
		else
			invoiceList = invoiceRepo.findAllByPage(pageable);
		LOGGER.info(String.format("invoice count: %d", invoiceList.size()));
		return invoiceList;
	}

	@GetMapping("/count/{active}")
	public Long getCount(@PathVariable(value = "active") boolean active) {
		LOGGER.info("getCount");
		Long numRows;
		if (active)
			numRows = invoiceRepo.findAllActiveCount();
		else
			numRows = invoiceRepo.findAllCount();

		LOGGER.info(String.format("count = %d", numRows));
		return numRows;
	}

	@GetMapping("/nextInvoiceNumber")
	@Transactional(rollbackFor = Exception.class)
	public Long getNextInvoiceNumber() {
		LOGGER.info("getNextInvoiceNumber");
		SalesNumberSequenceV2 sequence = salesNumRepo.findNextSequenceNumber("INVOICE");
		Long nextInvoiceNumber;
		if (sequence != null) {
			LOGGER.info(String.format("current invoice number = %d", sequence.getSalesNumberSequenceValue()));
			nextInvoiceNumber = sequence.getSalesNumberSequenceValue() + 1;
			sequence.setSalesNumberSequenceValue(nextInvoiceNumber);
			salesNumRepo.save(sequence);
		} else {
			LOGGER.info("Sequene not found - starting with initial value");
			nextInvoiceNumber = SalesNumberSequenceV2.STARTING_VALUE;
			SalesNumberSequenceV2 initialSequence = new SalesNumberSequenceV2();
			initialSequence.setSalesNumberSequenceType("INVOICE");
			initialSequence.setSalesNumberSequenceValue(nextInvoiceNumber);
			salesNumRepo.save(initialSequence);
		}
		LOGGER.info(String.format("next quote number = %d", nextInvoiceNumber));
		return nextInvoiceNumber;
	}

	@GetMapping("/search/{searchKey}/{active}/{page}")
	public List<InvoiceV2> searchInvoicePageable(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active, @PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format(
				"searchInvoicePageable received request with params searchKey = %s, active = %b, page = %d", searchKey,
				active, page));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);

		List<InvoiceV2> invoiceList;
		if (active)
			invoiceList = invoiceRepo.searchActiveInvoices(search, pageable);
		else
			invoiceList = invoiceRepo.searchAllInvoices(search, pageable);
		LOGGER.info(String.format("Returned %d rows", invoiceList.size()));
		return invoiceList;
	}

	@GetMapping("/searchCount/{searchKey}/{active}")
	public Long searchInvoiceCount(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active) {
		LOGGER.info(String.format("searchInvoiceCount received request with params searchKey = %s, active = %b",
				searchKey, active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long count;
		if (active)
			count = invoiceRepo.searchCountActive(search);
		else
			count = invoiceRepo.searchCount(search);
		LOGGER.info(String.format("Returned %d rows", count));
		return count;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<InvoiceV2> get(@PathVariable(value = "id") Long invoiceId) throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with invoiceId %d", invoiceId));
		if (invoiceId == null)
			throw new EntityNotFoundException(InvoiceV2.class, "id", "");
		InvoiceV2 invoice = invoiceRepo.findByInvoiceId(invoiceId);
		if (invoice == null) {
			LOGGER.info("invoice not found");
			throw new EntityNotFoundException(InvoiceV2.class, "id", invoiceId.toString());
		}
		List<InvoiceLineItemV2> lineItems = invoice.getLineItems(); // force line items to load
		LOGGER.info(String.format("Retrieve %d line items: ", lineItems.size()));
		CompanyShippingAddressV2 shippingAddress = invoice.getShippingAddress(); // force shipping address to load
		LOGGER.info(String.format("Retrieve invoice shipping address %s: ", shippingAddress));
		AddressV2 billingAddress = invoice.getBillingAddress();
		LOGGER.info(String.format("Retrieve invoice billing address %s: ", billingAddress));
		ClientContactV2 clientContact = invoice.getClientContact();
		LOGGER.info(String.format("Retrieve client contact info %s: ", clientContact));
		// force client company info to load along with related info
		ClientCompanyV2 clientCompany = invoice.getClientCompany();
		List<ClientContactV2> contactList = clientCompany.getClientContacts();
		LOGGER.info(String.format("loaded %d contacts for company attached to invoice", contactList.size()));
		List<CompanyShippingAddressV2> shppingAddressList = clientCompany.getShippingAddresses();
		LOGGER.info(String.format("loaded %d shipping addresses for company attached to invoice",
				shppingAddressList.size()));
		AddressV2 clientBillingAddress = clientCompany.getBillingAddress();
		LOGGER.info(String.format("loaded following billing address for client on invoice: %s", clientBillingAddress));
		// force quote to load if present
		QuoteV2 linkedQuote = invoice.getQuote();
		LOGGER.info(String.format("loaded following quote on invoice: %s", linkedQuote));

		return ResponseEntity.ok().body(invoice);
	}

	/**
	 * Save the passed invoice to the database.
	 * 
	 * @param invoice the invoice to insert or update
	 * @return the invoice that was saved
	 */
	private InvoiceV2 loadInvoice(Long invoiceId) {
		InvoiceV2 invoice = invoiceRepo.findByInvoiceId(invoiceId);
		if (invoice == null) {
			LOGGER.info("invoice not found");
			throw new EntityNotFoundException(ClientCompanyV2.class, "id", invoiceId.toString());
		}
		Hibernate.initialize(invoice.getBillingAddress());
		Hibernate.initialize(invoice.getShippingAddress());
		Hibernate.initialize(invoice.getClientContact());
		Hibernate.initialize(invoice.getClientCompany());
		Hibernate.initialize(invoice.getQuote());
		Hibernate.initialize(invoice.getLineItems());
		return invoice;
	}

	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public InvoiceV2 saveInvoice(@Valid @RequestBody InvoiceV2 invoice) {
		LOGGER.info(String.format("saveInvoice received request - details %s:", invoice));

		String action;
		String auditTrailRecord;

		if (invoice.getInvoiceId() > 0) {
			InvoiceV2 previousInvoiceV2 = this.loadInvoice(invoice.getInvoiceId());
			action = "update invoice";
			auditTrailRecord = getDiffs(invoice, previousInvoiceV2);
		} else {
			action = "insert invoice";
			auditTrailRecord = invoice.toString();
		}

		InvoiceV2 savedInvoice = invoiceRepo.save(invoice);
		super.saveUserHistory(invoice.getEditedBy(), "Invoice", action, auditTrailRecord);
		LOGGER.info("Completed saving invoice");
		return savedInvoice;
	}

	private String getDiffs(InvoiceV2 lhs, InvoiceV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two invoice objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("InvoiceV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

}

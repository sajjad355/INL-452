package com.internal.service.somruinternal.controller2;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
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
@RequestMapping("/quotev2")
public class QuoteControlV2 extends ParentControl {

	QuoteRepositoryV2 quoteRepo;

	EntityManager entityManager;

	SalesNumberSequenceRepository salesNumRepo;

	private final static Logger LOGGER = LoggerFactory.getLogger(QuoteControlV2.class);

	@Autowired
	public QuoteControlV2(QuoteRepositoryV2 quoteRepo, EntityManager entityManager,
			SalesNumberSequenceRepository salesNumRepo) {
		super();
		this.quoteRepo = quoteRepo;
		this.entityManager = entityManager;
		this.salesNumRepo = salesNumRepo;
	}

	@GetMapping("/all/{page}/{active}")
	public List<QuoteV2> getQuotesByPage(@PathVariable(value = "page") int page,
			@PathVariable(value = "active") boolean active) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("getQuotesByPage called with params: page: %s, active :%b", page, active));
		List<QuoteV2> quoteList;
		if (active)
			quoteList = quoteRepo.findAllActiveByPage(pageable);
		else
			quoteList = quoteRepo.findAllByPage(pageable);
		LOGGER.info(String.format("quote count: %d", quoteList.size()));
		return quoteList;
	}

	@GetMapping("/count/{active}")
	public Long getCount(@PathVariable(value = "active") boolean active) {
		LOGGER.info("getCount");

		Long numRows;
		if (active)
			numRows = quoteRepo.findAllActiveCount();
		else
			numRows = quoteRepo.findAllCount();
		LOGGER.info(String.format("count = %d", numRows));
		return numRows;
	}

	@GetMapping("/nextQuoteNumber")
	public Long getNextQuoteNumber() {
		LOGGER.info("getNextQuoteNumber");
		SalesNumberSequenceV2 sequence = salesNumRepo.findNextSequenceNumber("QUOTE");
		Long nextQuoteNumber;
		if (sequence != null) {
			LOGGER.info(String.format("current quote number = %d", sequence.getSalesNumberSequenceValue()));
			nextQuoteNumber = sequence.getSalesNumberSequenceValue() + 1;
			sequence.setSalesNumberSequenceValue(nextQuoteNumber);
			salesNumRepo.save(sequence);
		} else {
			LOGGER.info("Sequene not found - starting with initial value");
			nextQuoteNumber = SalesNumberSequenceV2.STARTING_VALUE;
			SalesNumberSequenceV2 initialSequence = new SalesNumberSequenceV2();
			initialSequence.setSalesNumberSequenceType("QUOTE");
			initialSequence.setSalesNumberSequenceValue(nextQuoteNumber);
			salesNumRepo.save(initialSequence);
		}
		LOGGER.info(String.format("next quote number = %d", nextQuoteNumber));
		return nextQuoteNumber;
	}

	@GetMapping("/searchPageable/{searchKey}/{active}/{page}")
	public List<QuoteV2> searchQuotePageable(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active, @PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(
				String.format("searchQuotePageable received request with params searchKey = %s, active = %b, page = %d",
						searchKey, active, page));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);

		List<QuoteV2> quoteList;
		if (active)
			quoteList = quoteRepo.searchActiveQuotes(search, pageable);
		else
			quoteList = quoteRepo.searchAllQuotes(search, pageable);

		LOGGER.info(String.format("Returned %d rows", quoteList.size()));
		return quoteList;
	}

	@GetMapping("/search/{searchKey}/{active}/{complete}")
	public List<QuoteV2> searchQuote(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active, @PathVariable(value = "complete") boolean complete) {
		LOGGER.info(String.format("searchQuote received request with params searchKey = %s, active = %b, complete = %b",
				searchKey, active, complete));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuoteV2> cq = cb.createQuery(QuoteV2.class);
		Root<QuoteV2> quote = cq.from(QuoteV2.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.like(quote.get("quoteNumber"), "%" + search + "%"));
		Predicate predicateForComplete = cb.equal(quote.get("complete"), true);
		Predicate predicateForActive = cb.equal(quote.get("active"), true);
		Date now = new Date();
		Predicate predicateForExpired = cb.lessThanOrEqualTo(quote.get("expirationDate"), now);

		if (active) {
			predicates.add(predicateForActive);
		}
		if (complete) {
			predicates.add(predicateForComplete);
		} else {
			Predicate predicateForCompleteOrExpired = cb.or(predicateForComplete, predicateForExpired);
			predicates.add(predicateForCompleteOrExpired);

		}
		cq.where(predicates.toArray(new Predicate[0]));
		List<QuoteV2> quoteList = entityManager.createQuery(cq).getResultList();
		LOGGER.info(String.format("Returned %d rows", quoteList.size()));
		return quoteList;
	}

	@GetMapping("/searchExpiredAndExpiring/{expiredSinceDays}/{expiringInDays}")
	public List<QuoteV2> searchExpiredAndExpiringQuotes(@PathVariable(value = "expiredSinceDays") int expiredSinceDays,
			@PathVariable(value = "expiringInDays") int expiringInDays) {
		LOGGER.info(String.format(
				"searchExpiredAndExpiring received request with params expiredSinceDays = %d, expiringInDays = %d",
				expiredSinceDays, expiringInDays));

		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -expiredSinceDays);
		Date expiredSinceDate = cal.getTime();
		LOGGER.info(String.format("Using expiredSinceDays of %d, Calculated ExpiredSinceDate is: %s", expiredSinceDays,
				expiredSinceDate));
		cal.setTime(today);
		cal.add(Calendar.DATE, +expiringInDays);
		Date expireAfterDate = cal.getTime();
		LOGGER.info(String.format("Using expiringInDays of %d, Calculated ExpireAfterDate is: %s", expiringInDays,
				expireAfterDate));
		List<QuoteV2> quoteList = quoteRepo.searchExpiredAndExpiringQuotes(expiredSinceDate, expireAfterDate);
		LOGGER.info(String.format("Returned %d rows", quoteList.size()));
		return quoteList;
	}

	@GetMapping("/searchCount/{searchKey}/{active}")
	public Long searchQuoteCount(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active) {
		LOGGER.info(String.format("searchQuoteCount received request with params searchKey = %s, active = %b",
				searchKey, active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long count;
		if (active)
			count = quoteRepo.searchCountActive(search);
		else
			count = quoteRepo.searchCount(search);
		LOGGER.info(String.format("Returned %d rows", count));
		return count;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<QuoteV2> get(@PathVariable(value = "id") Long quoteId) throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with quoteId %d", quoteId));
		if (quoteId == null)
			throw new EntityNotFoundException(QuoteV2.class, "id", "");
		QuoteV2 quote = quoteRepo.findByQuoteId(quoteId);
		if (quote == null) {
			LOGGER.info("quote not found");
			throw new EntityNotFoundException(QuoteV2.class, "id", quoteId.toString());
		}
		// force client company info to load along with related info
		ClientCompanyV2 clientCompany = quote.getClientCompany();
		List<ClientContactV2> contactList = clientCompany.getClientContacts();
		LOGGER.info(String.format("loaded %d contacts for company attached to quote", contactList.size()));
		List<CompanyShippingAddressV2> shppingAddressList = clientCompany.getShippingAddresses();
		LOGGER.info(
				String.format("loaded %d shipping addresses for company attached to quote", shppingAddressList.size()));
		AddressV2 billingAddress = clientCompany.getBillingAddress();
		LOGGER.info(String.format("loaded following billing address for client on quote: %s", billingAddress));
		List<QuoteLineItemV2> lineItems = quote.getLineItems(); // force line items to load
		LOGGER.info(String.format("Retrieve %d line items: ", lineItems.size()));
		ClientContactV2 clientContact = quote.getClientContact(); // force client contact to load
		LOGGER.info(String.format("Retrieve client contact %s: ", clientContact));
		CompanyShippingAddressV2 shippingAddress = quote.getShippingAddress(); // force shipping address to load
		LOGGER.info(String.format("Retrieve client shipping address %s: ", shippingAddress));
		return ResponseEntity.ok().body(quote);
	}

	/**
	 * Save the passed quote to the database.
	 * 
	 * @param quote the quote to insert or update
	 * @return the quote that was saved
	 */
	private QuoteV2 loadQuote(Long quoteId) {
		QuoteV2 quote = quoteRepo.findByQuoteId(quoteId);
		if (quote == null) {
			LOGGER.info("quote not found");
			throw new EntityNotFoundException(QuoteV2.class, "id", quoteId.toString());
		}
		Hibernate.initialize(quote.getClientContact());
		Hibernate.initialize(quote.getClientCompany());
		Hibernate.initialize(quote.getShippingAddress());
		Hibernate.initialize(quote.getLineItems());
		return quote;
	}

	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public QuoteV2 saveQuote(@Valid @RequestBody QuoteV2 quote) {
		LOGGER.info(String.format("saveQuote received request - details %s:", quote));
		String action;
		String auditTrailRecord;
		if (quote.getQuoteId() > 0) {
			QuoteV2 previousQuoteDetails = this.loadQuote(quote.getQuoteId());
			action = "update Quote";
			auditTrailRecord = getDiffs(quote, previousQuoteDetails);
		} else {
			action = "insert Quote";
			auditTrailRecord = quote.toString();
		}

		QuoteV2 savedQuote = quoteRepo.save(quote);
		LOGGER.info("Completed saving quote - now user history");
		super.saveUserHistory(quote.getEditedBy(), "Quote", "saveQuote", savedQuote.toString());
		return savedQuote;
	}

	private String getDiffs(QuoteV2 lhs, QuoteV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Quote objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("QuoteV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

}

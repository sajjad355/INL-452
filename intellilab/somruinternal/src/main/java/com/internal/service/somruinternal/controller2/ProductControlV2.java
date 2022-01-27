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
@RequestMapping("/productv2")
public class ProductControlV2 extends ParentControl {

	@Autowired
	ProductRepositoryV2 productRepo;

	@Autowired
	CatalogSequenceRepositoryV2 catalogSequenceRepo;

	private final static Logger LOGGER = LoggerFactory.getLogger(ProductControlV2.class);

	@GetMapping("/all/{page}/{active}")
	public List<ProductV2> getProductsByPage(@PathVariable(value = "page") int page,
			@PathVariable(value = "active") boolean active) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("getProductsByPage with params page = %d, active = %b", page, active));
		List<ProductV2> productList;
		if (active)
			productList = productRepo.findAllActivePageable(pageable);
		else
			productList = productRepo.findAllPageable(pageable);
		LOGGER.info(String.format("Retrieved %d products", productList.size()));
		return productList;
	}

	@GetMapping("/count/{active}")
	public Long searchCount(@PathVariable(value = "active") boolean active) {
		LOGGER.info("allCount received request");
		Long count;
		if (active)
			count = productRepo.countAllActive();
		else
			count = productRepo.countAll();
		LOGGER.info(String.format("Retrieved %d products", count));
		return count;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ProductV2> get(@PathVariable(value = "id") Long productId) throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with productId %d", productId));
		if (productId == null)
			throw new EntityNotFoundException(ProductV2.class, "id", "");
		ProductV2 product = productRepo.getSingleProduct(productId);
		if (product == null) {
			LOGGER.info("product not found");
			throw new EntityNotFoundException(ProductV2.class, "id", productId.toString());
		}
		return ResponseEntity.ok().body(product);
	}

	private ProductV2 loadProduct(Long salesitemId) {
		ProductV2 product = productRepo.findBySalesItemId(salesitemId);
		if (product == null) {
			LOGGER.info("Product not found");
			throw new EntityNotFoundException(KitV2.class, "id", salesitemId.toString());
		}
		Hibernate.initialize(product.getApplications());
		return product;
	}

	/**
	 * Save the passed product to the database.
	 * 
	 * @param product the product to insert or update
	 * @return the product that was saved
	 */
	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public ProductV2 saveProduct(@Valid @RequestBody ProductV2 product) {
		LOGGER.info(String.format("saveProduct received request - details %s:", product));
		String action;
		String auditTrailRecord;

		if (product.getSalesItemId() > 0) {
			ProductV2 previousProductDetails = this.loadProduct(product.getSalesItemId());
			action = "update product";
			auditTrailRecord = getDiffs(product, previousProductDetails);
		} else {
			action = "insert product";
			auditTrailRecord = product.toString();
		}

		ProductV2 savedProduct = productRepo.save(product);
		LOGGER.info("Completed saving product");
		super.saveUserHistory(product.getEditedBy(), "Product", "saveProduct", savedProduct.toString());
		return savedProduct;
	}

	private String getDiffs(ProductV2 lhs, ProductV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Product objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("Product GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@GetMapping("/search/{searchKey}")
	public List<ProductV2> search(@PathVariable(value = "searchKey") String searchKey) {
		LOGGER.info(String.format("search received request with searchKey %s", searchKey));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		List<ProductV2> productList = productRepo.searchActive(search.toUpperCase());
		LOGGER.info(String.format("Retrieved %d products", productList.size()));
		return productList;
	}

	@GetMapping("/search/{searchKey}/{active}/{page}")
	public List<ProductV2> searchPageable(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active, @PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("searchPageable received request with searchKey %s, active %b, page %d", searchKey,
				active, page));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		List<ProductV2> productList;
		if (active) {
			productList = productRepo.searchActive(search.toUpperCase(), pageable);
		} else {
			productList = productRepo.search(search.toUpperCase(), pageable);
		}
		LOGGER.info(String.format("Retrieved %d products", productList.size()));
		return productList;
	}

	@GetMapping("/searchCount/{searchKey}/{active}")
	public Long searchCount(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active) {
		LOGGER.info(String.format("searchCount received request with searchKey %s, active %b", searchKey, active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long count;
		if (active) {
			count = productRepo.searchCountActive(search.toUpperCase());
		} else {
			count = productRepo.searchCount(search.toUpperCase());
		}
		LOGGER.info(String.format("Retrieved %d products", count));
		return count;
	}
}

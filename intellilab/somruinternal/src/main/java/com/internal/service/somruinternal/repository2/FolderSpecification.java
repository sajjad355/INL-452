package com.internal.service.somruinternal.repository2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.internal.service.somruinternal.model2.Folder;

public final class FolderSpecification {

	public static Specification<Folder> getSpecification(Long id, Boolean isActive, String studyNumber, String title,
			Long custodianId, String barcode) {
		return Specification.where(id == null ? null : idEquals(id))
				.and(isActive == null ? null : isActiveEquals(isActive))
				.and(studyNumber == null ? null : studyNumberLike(studyNumber))
				.and(title == null ? null : titleLike(title))
				.and(custodianId == null ? null : custodianIdEquals(custodianId))
				.and(barcode == null ? null : barcodeLike(barcode));
	}

	private static Specification<Folder> idEquals(Long id) {
		return new Specification<Folder>() {
			public Predicate toPredicate(Root<Folder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("dbid"), id);
			}
		};
	}

	private static Specification<Folder> isActiveEquals(Boolean isActive) {
		return new Specification<Folder>() {
			public Predicate toPredicate(Root<Folder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("isActive"), isActive);
			}
		};
	}

	private static Specification<Folder> studyNumberLike(String studyNumber) {
		return new Specification<Folder>() {
			public Predicate toPredicate(Root<Folder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("studyNumber"), "%" + studyNumber + "%");
			}
		};
	}

	private static Specification<Folder> titleLike(String title) {
		return new Specification<Folder>() {
			public Predicate toPredicate(Root<Folder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("title"), "%" + title + "%");
			}
		};
	}

	private static Specification<Folder> custodianIdEquals(Long custodianId) {
		return new Specification<Folder>() {
			public Predicate toPredicate(Root<Folder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("custodian").get("userId"), custodianId);
			}
		};
	}
	
	private static Specification<Folder> barcodeLike(String barcode) {
		return new Specification<Folder>() {
			public Predicate toPredicate(Root<Folder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("barcode"), "%" + barcode + "%");
			}
		};
	}
}

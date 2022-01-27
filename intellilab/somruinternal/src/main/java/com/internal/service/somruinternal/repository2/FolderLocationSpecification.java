package com.internal.service.somruinternal.repository2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.internal.service.somruinternal.model2.FolderLocation;

public final class FolderLocationSpecification {

	public static Specification<FolderLocation> getSpecification(Long id, Boolean isActive, String title, String barcode) {
		return Specification.where(id == null ? null : idEquals(id))
				.and(isActive == null ? null : isActiveEquals(isActive)).and(title == null ? null : titleLike(title))
				.and(barcode == null ? null : barcodeLike(barcode));
	}

	private static Specification<FolderLocation> idEquals(Long id) {
		return new Specification<FolderLocation>() {
			public Predicate toPredicate(Root<FolderLocation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("dbid"), id);
			}
		};
	}

	private static Specification<FolderLocation> isActiveEquals(Boolean isActive) {
		return new Specification<FolderLocation>() {
			public Predicate toPredicate(Root<FolderLocation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("isActive"), isActive);
			}
		};
	}

	private static Specification<FolderLocation> titleLike(String title) {
		return new Specification<FolderLocation>() {
			public Predicate toPredicate(Root<FolderLocation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("title"), "%" + title + "%");
			}
		};
	}

	private static Specification<FolderLocation> barcodeLike(String barcode) {
		return new Specification<FolderLocation>() {
			public Predicate toPredicate(Root<FolderLocation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("barcode"), "%" + barcode + "%");
			}
		};
	}
}

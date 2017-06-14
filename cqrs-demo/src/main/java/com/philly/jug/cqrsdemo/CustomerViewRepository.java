package com.philly.jug.cqrsdemo;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerViewRepository extends PagingAndSortingRepository<CustomerView, String> {
}

package com.billcat.repository;
import com.billcat.domain.AccountItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountItemRepository extends JpaRepository<AccountItem, Long> {

}

package com.billcat.service.mapper;

import com.billcat.domain.*;
import com.billcat.service.dto.AccountItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountItem} and its DTO {@link AccountItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface AccountItemMapper extends EntityMapper<AccountItemDTO, AccountItem> {

    @Mapping(source = "category.id", target = "categoryId")
    AccountItemDTO toDto(AccountItem accountItem);

    @Mapping(source = "categoryId", target = "category")
    AccountItem toEntity(AccountItemDTO accountItemDTO);

    default AccountItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountItem accountItem = new AccountItem();
        accountItem.setId(id);
        return accountItem;
    }
}

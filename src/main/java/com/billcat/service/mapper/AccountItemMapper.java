package com.billcat.service.mapper;

import com.billcat.domain.*;
import com.billcat.service.dto.AccountItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountItem} and its DTO {@link AccountItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountItemMapper extends EntityMapper<AccountItemDTO, AccountItem> {



    default AccountItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountItem accountItem = new AccountItem();
        accountItem.setId(id);
        return accountItem;
    }
}

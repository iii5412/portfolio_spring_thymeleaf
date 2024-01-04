package com.portfolio.main.application.menu.dto.factory;

import com.portfolio.main.application.menu.dto.MenuDto;

public interface MenuDtoFactory<T extends MenuDto> {
    T create(T source);
}

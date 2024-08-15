package dev.jeongyongs.firfin.convertor;

import dev.jeongyongs.firfin.domain.UserStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusCodeConvertor extends AbstractCodeConvertor<UserStatus> {

    public UserStatusCodeConvertor() {
        super(UserStatus.class);
    }
}

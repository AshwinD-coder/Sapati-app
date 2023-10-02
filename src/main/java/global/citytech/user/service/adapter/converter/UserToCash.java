package global.citytech.user.service.adapter.converter;

import global.citytech.cash.repository.Cash;
import global.citytech.user.repository.User;

public class UserToCash {
    private UserToCash(){}
    public static Cash toCash(User user){
        Cash cash = new Cash();
        cash.setAmount(0.0);
        cash.setUsername(user.getUsername());
        cash.setUserType(user.getUserType());
        return cash;
    }
}

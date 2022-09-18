package com.dikkulah.accountservice.dto;

import com.dikkulah.accountservice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private UUID id;
    private String username;
    private String hashedPassword;
    private List<AccountDto> accounts = new ArrayList<>();
    private List<ExchangeDto> exchanges = new ArrayList<>();
    private Set<Role> roles = new HashSet<>();
}

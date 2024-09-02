package com.cpatos.edo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefUnlocCode {
    private String Id;
    private String Name;
    private String Country_Code;
    private String Port_Code;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry_Code() {
        return Country_Code;
    }

    public void setCountry_Code(String country_Code) {
        Country_Code = country_Code;
    }

    public String getPort_Code() {
        return Port_Code;
    }

    public void setPort_Code(String port_Code) {
        Port_Code = port_Code;
    }
}

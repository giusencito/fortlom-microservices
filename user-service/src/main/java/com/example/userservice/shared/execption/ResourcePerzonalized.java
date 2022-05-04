package com.example.userservice.shared.execption;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourcePerzonalized extends RuntimeException{

    public ResourcePerzonalized(String mensaje) {

        super(mensaje);


    }
}

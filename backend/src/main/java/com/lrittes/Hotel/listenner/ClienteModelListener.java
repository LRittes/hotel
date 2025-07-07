package com.lrittes.Hotel.listenner; 

import com.lrittes.Hotel.Model.Cliente;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClienteModelListener extends AbstractMongoEventListener<Cliente> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Cliente> event) {

        Cliente cliente = event.getSource();
        if (cliente.getClienteId() == null) { 
            cliente.setClienteId(sequenceGenerator.generateSequence(Cliente.SEQUENCE_NAME)); 
        }
    }
}
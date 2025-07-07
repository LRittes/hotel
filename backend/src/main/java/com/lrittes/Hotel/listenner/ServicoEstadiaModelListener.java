package com.lrittes.Hotel.listenner; 

import com.lrittes.Hotel.Model.ServicoEstadia;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServicoEstadiaModelListener extends AbstractMongoEventListener<ServicoEstadia> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ServicoEstadia> event) {

        ServicoEstadia servEst = event.getSource();
        if (servEst.getSeid() == null) { 
            servEst.setSeid(sequenceGenerator.generateSequence(ServicoEstadia.SEQUENCE_NAME)); 
        }
    }
}
package com.lrittes.Hotel.listenner;

import com.lrittes.Hotel.Model.Estadia;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EstadiaModelListener extends AbstractMongoEventListener<Estadia> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Estadia> event) {

        Estadia estadia = event.getSource();
        if (estadia.getEid() == null) { 
            estadia.setEid(sequenceGenerator.generateSequence(Estadia.SEQUENCE_NAME)); 
        }
    }
}
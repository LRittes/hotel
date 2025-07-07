package com.lrittes.Hotel.listenner; // Coloque em um pacote apropriado como 'Listener'

import com.lrittes.Hotel.Model.Reserva;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservaModelListener extends AbstractMongoEventListener<Reserva> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Reserva> event) {

        Reserva reserva = event.getSource();
        if (reserva.getRid() == null) { 
            reserva.setRid(sequenceGenerator.generateSequence(Reserva.SEQUENCE_NAME)); 
        }
    }
}
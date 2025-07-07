package com.lrittes.Hotel.listenner; // Coloque em um pacote apropriado como 'Listener'

import com.lrittes.Hotel.Model.Limpeza;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LimpezaModelListener extends AbstractMongoEventListener<Limpeza> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Limpeza> event) {

        Limpeza limpeza = event.getSource();
        if (limpeza.getLid() == null) { 
            limpeza.setLid(sequenceGenerator.generateSequence(Limpeza.SEQUENCE_NAME)); 
        }
    }
}
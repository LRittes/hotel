package com.lrittes.Hotel.listenner;

import com.lrittes.Hotel.Model.TipoQuarto;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TipoQuartoModelListener extends AbstractMongoEventListener<TipoQuarto> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<TipoQuarto> event) {

        TipoQuarto tq = event.getSource();
        if (tq.getTqid() == null) { 
            tq.setTqid(sequenceGenerator.generateSequence(TipoQuarto.SEQUENCE_NAME)); 
        }
    }
}
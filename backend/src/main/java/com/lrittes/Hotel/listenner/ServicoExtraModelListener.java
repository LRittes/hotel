package com.lrittes.Hotel.listenner; 

import com.lrittes.Hotel.Model.ServicoExtra;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServicoExtraModelListener extends AbstractMongoEventListener<ServicoExtra> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ServicoExtra> event) {

        ServicoExtra servEx = event.getSource();
        if (servEx.getSeid() == null) { 
            servEx.setSeid(sequenceGenerator.generateSequence(ServicoExtra.SEQUENCE_NAME)); 
        }
    }
}
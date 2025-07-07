package com.lrittes.Hotel.listenner; // Coloque em um pacote apropriado como 'Listener'

import com.lrittes.Hotel.Model.Empregado;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmpregadoModelListener extends AbstractMongoEventListener<Empregado> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Empregado> event) {
        Empregado empregado = event.getSource();
        if (empregado.getEid() == null) {
            empregado.setEid(sequenceGenerator.generateSequence(Empregado.SEQUENCE_NAME));
        }
    }
}
package com.lrittes.Hotel.listenner; // Coloque em um pacote apropriado como 'Listener'

import com.lrittes.Hotel.Model.Hotel;
import com.lrittes.Hotel.util.SequenceGeneratorService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HotelModelListener extends AbstractMongoEventListener<Hotel> {


    @Autowired
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Hotel> event) {

        Hotel hotel = event.getSource();
        if (hotel.getHid() == null) { 
            hotel.setHid(sequenceGenerator.generateSequence(Hotel.SEQUENCE_NAME)); 
        }
    }
}
package com.example.alex.demoJones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "p")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {

    @XmlAttribute(name = "name")
    private String nameAttr;

    @XmlAttribute(name = "type")
    private String typeAttr;

    @XmlValue
    private String value;
}

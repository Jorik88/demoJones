package com.example.alex.demoJones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "parameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameters {

    @XmlElement(name = "p")
    private Parameter parameter;
}

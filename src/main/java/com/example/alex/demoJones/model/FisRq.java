package com.example.alex.demoJones.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name = "FISRq")
@XmlAccessorType(XmlAccessType.FIELD)
public class FisRq {

    @XmlElement(name = "Msg")
    private Msg msg;

    private transient List<Parameters> parameters;

    public FisRq(List<Parameters> parameters) {
        this.parameters = parameters != null ? new ArrayList<>(parameters) : Collections.EMPTY_LIST;
        this.msg = new Msg(this.parameters);
    }

    @Getter
    @ToString
    private static class Msg {

        @XmlAttribute(name = "Scheme")
        private String schemeAttr = "CRB";

        @XmlAttribute(name = "Version")
        private String versionAttr = "1.0";

        @XmlElement(name = "Request")
        private Request request;

        public Msg(List<Parameters> parameters) {
            this.request = new Request(parameters);
        }

        @ToString
        private static class Request {

            @XmlAttribute(name = "RqId")
            private String rqIdAttr = "CRB/FindSuspiciousRq";

            @XmlElement(name = "Body")
            private Body body;

            public Request(List<Parameters> parameters) {
                this.body = new Body(parameters);
            }

            @ToString
            private static class Body {

                @XmlElement(name = "req-ROWSET")
                private ReqRowset reqRowset;

                public Body(List<Parameters> parameters) {
                    this.reqRowset = new ReqRowset(parameters);
                }

                @ToString
                private static class ReqRowset {

                    @XmlElementWrapper(name = "req-ROW")
                    @XmlElement(name = "parameters")
                    private List<Parameters> parameters;

                    public ReqRowset(List<Parameters> parameters) {
                        this.parameters = parameters;
                    }
                }

            }
        }
    }
}


package ua.rd.cm.domain;


import java.util.*;

public enum Status {
    NEW("New"), IN_PROGRESS("In Progress"), APPROVED("Approved"), REJECTED("Rejected");

    Status(String name) {
        this.name = name;
    }

    private String name;
    private enum Transition {

        FROM_NEW(NEW, IN_PROGRESS, APPROVED,REJECTED),
        FROM_IN_PROGRESS(IN_PROGRESS, APPROVED, REJECTED),
        FROM_APPROVED(APPROVED),
        FROM_REJECTED(REJECTED);

        Status from;
        EnumSet<Status> to;

        Transition(Status from, Status... to) {
            this.from = from;
            this.to = EnumSet.noneOf(Status.class);
            this.to.addAll(Arrays.asList(to));
        }

        static final Map<Status, Set<Status>> transitions = new EnumMap<>(Status.class);

        static {
            for (Transition transition : Transition.values()) {
                transitions.put(transition.from, EnumSet.copyOf(transition.to));
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean canChangeTo(Status status) {
        return Transition.transitions.get(this).contains(status);
    }
    public static Status getStatusByName(String statusName){
        for(Status status:Status.values()){
            if(status.getName().equals(statusName)){
                return status;
            }
        }
        return null;
    }
}

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(exclude = "id")
//@Entity
//@Table(name = "status")
//@SequenceGenerator(name = "seqStatusGen", allocationSize = 1,
//        sequenceName = "status_seq")
//public class Status {
//
//    @Id
//    @Column(name = "status_id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqStatusGen")
//    private Long id;
//
//    @Column(name = "status_name", nullable = false, unique = true)
//    private String name;
//}

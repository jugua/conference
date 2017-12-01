package domain.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum TalkStatus {

	DRAFT("Draft"),
	SUBMITTED("Submitted"),
	PENDING("Pending"),
	UPDATE_REQUEST("Update Request"),
    ACCEPTED("Accepted"),
    NOT_ACCEPTED("Not Accepted");

    private String name;

    TalkStatus(String name) {
        this.name = name;
    }

    public static TalkStatus getStatusByName(String statusName) {
        for (TalkStatus status : TalkStatus.values()) {
            if (status.getName().equals(statusName)) {
                return status;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public boolean isStatusName(String name) {
        return this.name.equals(name);
    }

    public boolean canChangeTo(TalkStatus status) {
        return Transition.transitions.get(this).contains(status);
    }

    private enum Transition {

        FROM_NEW(DRAFT, SUBMITTED, PENDING, UPDATE_REQUEST, ACCEPTED, NOT_ACCEPTED),
        FROM_IN_PROGRESS(SUBMITTED, PENDING, UPDATE_REQUEST, ACCEPTED, NOT_ACCEPTED),
        FROM_APPROVED(ACCEPTED),
        FROM_REJECTED(NOT_ACCEPTED);

        static final Map<TalkStatus, Set<TalkStatus>> transitions = new EnumMap<>(TalkStatus.class);

        static {
            for (Transition transition : Transition.values()) {
                transitions.put(transition.from, EnumSet.copyOf(transition.to));
            }
        }

        TalkStatus from;
        EnumSet<TalkStatus> to;

        Transition(TalkStatus from, TalkStatus... to) {
            this.from = from;
            this.to = EnumSet.noneOf(TalkStatus.class);
            this.to.addAll(Arrays.asList(to));
        }
    }
}

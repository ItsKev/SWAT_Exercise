package ch.hslu.appe.fbs.data.orderstate;

public enum OrderStates {
    RECEIVED("received"),
    IN_PROGRESS("in progress"),
    CANCELLED("cancelled"),
    COMPLETED("completed"),
    WAIT_FOR_ITEM("wait for item");

    private final String state;

    OrderStates(final String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }
}

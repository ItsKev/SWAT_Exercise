package ch.hslu.appe.fbs.client.Userinterface.Shared;

import javafx.scene.control.Alert;

public class AlertMessages {
    public static final String EditOrderAndDiscardOtherOrder = "Momentan wird eine andere Bestellung bearbeitet. " +
            "Wollen Sie trotzdem fortfahren? Die bearbeitung der anderen Bestellung wird dadurch abgebrochen";

    public static final String EditOrderNoneSelected = "Bitte wählen Sie die Bestellung aus, die Sie stornieren möchten.";

    public static final String CancelOrderNoneSelected = "Bitte wählen Sie die Bestellung aus, die Sie bearbeiten möchten.";
    public static final String SureCancelOrder = "Wollen Sie diese Bestellung wirklich löschen?";
    public static final String SureEditOrder = "Wollen Sie die alte Bestellung stornieren und diese absenden?";
    public static final String OpenPayments = "Dieser Kunde besitzt unbezahlte Mahnungen. Trotzdem fortfahren?";
    public static final String NoCustomer = "Bitte weisen Sie der Bestellung einen Kunden zu, bevor Sie diese abschicken";
    public static final String NoItems = "Bitte weisen Sie der Bestellung Bestellitems zu, bevor Sie diese abschicken";
    public static final String AlreadyDelivered = "Diese Bestellung wurde bereits geliefert.";

    public static void ShowNoPermissionMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Sie haben nicht genügend Berechtigungen, um diese Aktion auszuführen.");
        alert.showAndWait();
    }
}

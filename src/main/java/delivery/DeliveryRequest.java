package delivery;
public record DeliveryRequest(DeliveryPriority priority, double maxBudget,
                              int maxDeliveryMinutes, boolean ecoPreferred, double maxEmission) {
    public DeliveryRequest {
        if (priority == null || maxBudget < 0 || maxDeliveryMinutes < 0 || maxEmission < 0)
            throw new IllegalArgumentException("Invalid request");
    }
}
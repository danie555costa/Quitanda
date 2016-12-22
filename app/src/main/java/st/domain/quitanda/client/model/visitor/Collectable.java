package st.domain.quitanda.client.model.visitor;

/**
 * Created by Daniel Costa at 9/6/16.
 * Using user computer xdata
 */
public interface Collectable {

    /**
     * Accept the
      * @param collectorVisitor
     */
    void accept(SellCollectorVisitor collectorVisitor);
}

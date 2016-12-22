package st.domain.quitanda.client.model.action;

import st.domain.quitanda.client.model.visitor.SellCollectorVisitor;

/**
 * Created by Daniel Costa at 9/6/16.
 * Using user computer xdata
 */
public interface RegSellAction {
    void register(SellCollectorVisitor collectorVisitor);
}

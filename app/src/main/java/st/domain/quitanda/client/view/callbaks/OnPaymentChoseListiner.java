package st.domain.quitanda.client.view.callbaks;

import android.app.Activity;

/**
 * Quando o pagamento for selecionado devera determinar se aceita o pagamento no esta em que pereten
 * Essa interface devera ser implementada em todas as abas dos tipos dos pagamento
 * Created by Daniel Costa at 9/7/16.
 * Using user computer xdata
 */
public interface OnPaymentChoseListiner
{
    /**
     * Verificar se o esdo do pagamento esta atualemnte valido
     * @return
     */
    boolean isValid();

    /**
     * Obter a messagem para o estada invalido
     * @return
     */
    String invalidMessage();

    /**
     * Aceitar uma activite no momenti em que o ecolhedor do pagamneto for terminado
     * @param activity
     */
    void accept(Activity activity);
}

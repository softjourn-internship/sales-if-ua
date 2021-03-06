package sales.payment.paypal.service;

import sales.payment.paypal.domain.Paypal;

import java.util.List;

/**
 * Created by volodya on 28.07.15.
 */
public interface PaypalService {

    public Paypal get(long id);

    public Paypal getByUserId(long id);

    public List<Paypal> getAll();

    public Paypal save(Paypal paypal);

    public void delete(long id);


}

package lab_2;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.SimControlException;

public class MyEvent1 extends dissimlab.simcore.BasicSimEvent<MySimObj, Object> {

    // Konstruktor zdarzenia obslugi: MyEvent1
    public MyEvent1(MySimObj entity, Object o, double period)
            throws SimControlException {
        super(entity, o, period);
    }

    // Wyzwalanie zdarzenia obslugi: MyEvent1
    @Override
    protected void stateChange() throws SimControlException {
        MySimObj mySimObj = getSimObj();
        mySimObj.dodajInteresanta();
    }

    @Override
    protected void onTermination() throws SimControlException {
    }

    @Override
    public Object getEventParams() {
        return null;
    }
}

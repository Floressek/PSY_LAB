package lab_2_testy_paczki;

import dissimlab.simcore.SimControlException;

public class MyEvent1 extends dissimlab.simcore.BasicSimEvent<MySimObj, Object> {
    public MyEvent1(MySimObj entity, Object o, double period)
            throws SimControlException {
        super(entity, o, period);
    }

    @Override
    protected void stateChange() throws SimControlException {
        MySimObj mySimObj = getSimObj();
        mySimObj.dodajInteresanta();

        double czasDoNastepnego = mySimObj.rng.exponential(mySimObj.lambda);
        new MyEvent1(mySimObj, null, czasDoNastepnego);
    }


    @Override
    protected void onTermination() throws SimControlException {
    }

    @Override
    public Object getEventParams() {
        return null;
    }
}
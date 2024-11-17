package lab_2;

import dissimlab.simcore.SimControlException;

public class MyEvent2 extends dissimlab.simcore.BasicSimEvent<MySimObj, Object>{

    public MyEvent2(MySimObj entity, Object o, double period)
            throws SimControlException {
        super(entity, o, period);
    }

    @Override
    protected void stateChange() throws SimControlException {
        MySimObj mySimObj = getSimObj();
        mySimObj.obsluzInteresanta();
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    public Object getEventParams() {
        return null;
    }
}

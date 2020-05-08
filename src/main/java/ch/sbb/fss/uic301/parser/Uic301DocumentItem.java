package ch.sbb.fss.uic301.parser;

public interface Uic301DocumentItem {
    
    public String getIdentifier();

    public String getRailUnionCompiling();
    
    public void setRailUnionCompiling(String ru);
    
    public String getRailUnionReceiving();
    
    public void setRailUnionReceiving(String ru);

    void changePeriodCounter(int i);
}

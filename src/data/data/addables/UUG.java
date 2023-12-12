package data.data.addables;


//create composite parent class for objects with id field
public abstract class UUG {
    private String id;
    private long creationTime;

     public UUG(String id){
        this.id = id;
        this.creationTime = System.currentTimeMillis();
    }


    public UUG(String id, long creationTime){
        this.id = id;
        this.creationTime = creationTime;
    }
   
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public long getTimeMade() {
        return creationTime;
    }

    public void setTimeMade(long time) {
        this.creationTime = creationTime;
    }
}

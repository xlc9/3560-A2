package data.data.addables;


//create composite parent class for objects with id field
public abstract class UUG {
    private String id;

    public UUG(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}

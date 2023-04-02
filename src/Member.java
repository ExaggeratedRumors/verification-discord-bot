public class Member{

    private final String id;
    private String key;

    public Member(String id, String key){
        this.id = id;
        this.key = key;
    }

    public String getId(){return id;}
    public String getKey(){return key;}
    public void setKey(String newKey){key = newKey;}

}
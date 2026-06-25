package ulb.model.user;

/**
 * Represents a user in the local database.
 * It is used in order to store the teams of a specific user under his/her username
 * (used in {@code GameRepository}) */
public class User {
    private Integer id = null;
    private String username = null;

    public User(Integer newId, String newUsername){
        this.id = newId;
        this.username = newUsername;
    }
    public User(){}

    public void setInformation(String username, Integer id){
        this.username = username ;
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public Integer getId(){
        return this.id;
    }
}

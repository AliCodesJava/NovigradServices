import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class Person extends User{
    private String firstName;
    private String lastName;
    private ArrayList<Address> addressList;
    private Branch mainBranch;

    public Person(String username, String password, String emailAddress, String firstName, String lastName, Branch mainBranch) {
        super(username, password, emailAddress);
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressList = new ArrayList<>();
        this.mainBranch = mainBranch;
    }
    public Address removeAddress(Address address){
        if (!addressList.contains(address)){
            throw new NoSuchElementException("This address is not present in the person's addressList");
        }
        addressList.remove(address);
        return address;
    }
    public void addAddress(Address address){
        addressList.add(address);
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Address> getAddressList() {
        return addressList;
    }

    public Branch getMainBranch() {
        return mainBranch;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMainBranch(Branch mainBranch) {
        this.mainBranch = mainBranch;
    }
}

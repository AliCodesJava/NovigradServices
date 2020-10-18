public class Address {
    private String streetNumber; //Can contain letters, so kept it as a String
    private short apartmentNumber;
    private String streetName;
    private String city;
    private String postalCode;

    public Address(String streetNumber, short apartmentNumber, String streetName, String city, String postalCode) {
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public short getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(short apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() { //TODO check for official formatting
        return "Address{" +
                "streetNumber='" + streetNumber + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}

package entities;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Zips {
	@BsonId
	private ObjectId id;
	private String city;
	private String zip;
	private Location loc;
	private int pop;
	private String state;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Zips(ObjectId id, String city, String zip, Location loc, int pop, String state) {
		this.id = id;
		this.city = city;
		this.zip = zip;
		this.loc = loc;
		this.pop = pop;
		this.state = state;
	}

	public Zips() {
	}

	@Override
	public String toString() {
		return "Zips [id=" + id + ", city=" + city + ", zip=" + zip + ", loc=" + loc + ", pop=" + pop + ", state="
				+ state + "]" + "\n" ;
	}

}

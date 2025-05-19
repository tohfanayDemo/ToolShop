package models;

public class Register {

	private String firstName;
	private String lastName;
	private String dob;
	private String street;
	private String postalCode;
	private String city;
	private String state;
	private String country;
	private String phone;
	private String email;
	private String password;
	
	public Register(RegisterBuilder registerBuilder) {
		this.firstName = registerBuilder.firstName;	
		this.lastName = registerBuilder.lastName;
		this.dob = registerBuilder.dob;
		this.street = registerBuilder.street;
		this.postalCode = registerBuilder.postalCode;
		this.city = registerBuilder.city;
		this.state = registerBuilder.state;
		this.country = registerBuilder.country;
		this.phone = registerBuilder.phone;
		this.email = registerBuilder.email;
		this.password = registerBuilder.password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDob() {
		return dob;
	}

	public String getStreet() {
		return street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	
	public static class RegisterBuilder{
		
		private String firstName;
		private String lastName;
		private String dob;
		private String street;
		private String postalCode;
		private String city;
		private String state;
		private String country;
		private String phone;
		private String email;
		private String password;


		public RegisterBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public RegisterBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public RegisterBuilder setDob(String dob) {
			this.dob = dob;
			return this;
		}
		public RegisterBuilder setStreet(String street) {
			this.street = street;
			return this;
		}
		public RegisterBuilder setPostalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}
		public RegisterBuilder setCity(String city) {
			this.city = city;
			return this;
		}
		public RegisterBuilder setState(String state) {
			this.state = state;
			return this;
		}
		public RegisterBuilder setCountry(String country) {
			this.country = country;
			return this;
		}
		public RegisterBuilder setPhone(String phone) {
			this.phone = phone;
			return this;
		}
		public RegisterBuilder setEmail(String email) {
			this.email = email;
			return this;
		}
		public RegisterBuilder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public Register build() {
			return new Register(this);
		}
	}
	
	
}

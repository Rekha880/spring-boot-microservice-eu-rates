package com.eu.rates.model;

public class Countries {


	    public String country;
	    public double standard_rate;
	    public String reduced_rate;
	    public String reduced_rate_alt;
	    public String super_reduced_rate;
	    public String parking_rate;
	    public String _comment;
	    public String iso_duplicate;
	    
	    
		public String getIso_duplicate_of() {
			return iso_duplicate;
		}
		public void setIso_duplicate_of(String iso_duplicate) {
			this.iso_duplicate = iso_duplicate;
		}
		public String get_comment() {
			return _comment;
		}
		public void set_comment(String _comment) {
			this._comment = _comment;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public double getStandard_rate() {
			return standard_rate;
		}
		
		public void setStandard_rate(double standard_rate) {
			this.standard_rate = standard_rate;
		}
		public String getReduced_rate() {
			return reduced_rate;
		}
		public void setReduced_rate(String reduced_rate) {
			this.reduced_rate = reduced_rate;
		}
		public String getReduced_rate_alt() {
			return reduced_rate_alt;
		}
		public void setReduced_rate_alt(String reduced_rate_alt) {
			this.reduced_rate_alt = reduced_rate_alt;
		}
		public String isSuper_reduced_rate() {
			return super_reduced_rate;
		}
		public void setSuper_reduced_rate(String super_reduced_rate) {
			this.super_reduced_rate = super_reduced_rate;
		}
		public String getParking_rate() {
			return parking_rate;
		}
		public void setParking_rate(String parking_rate) {
			this.parking_rate = parking_rate;
		}
	    
		@Override
		public String toString() {
			return "Countries [country=" + country + ", standard_rate=" + standard_rate + ", reduced_rate="
					+ reduced_rate + ", reduced_rate_alt=" + reduced_rate_alt + ", super_reduced_rate="
					+ super_reduced_rate + ", parking_rate=" + parking_rate + ", _comment=" + _comment
					+ ", iso_duplicate=" + iso_duplicate + "]";
		}

}

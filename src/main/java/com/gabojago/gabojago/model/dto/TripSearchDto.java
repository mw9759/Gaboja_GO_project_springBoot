package com.gabojago.gabojago.model.dto;

public class TripSearchDto {

	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String firstImage;
	private String firstImage2;
	private int readcount;
	private int sido_code;
	private int gugun_code;
	private double latitude;
	private double longitude;
	private String mlevel;
	private String overview;
	private double distance;
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getFirstImage2() {
		return firstImage2;
	}
	public void setFirstImage2(String firstImage2) {
		this.firstImage2 = firstImage2;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getSido_code() {
		return sido_code;
	}
	public void setSido_code(int sido_code) {
		this.sido_code = sido_code;
	}
	public int getGugun_code() {
		return gugun_code;
	}
	public void setGugun_code(int gugun_code) {
		this.gugun_code = gugun_code;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getMlevel() {
		return mlevel;
	}
	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}
	@Override
	public String toString() {
		return "TripSearchDto [contentId=" + contentId + ", contentTypeId=" + contentTypeId + ", title=" + title
				+ ", addr1=" + addr1 + ", addr2=" + addr2 + ", zipcode=" + zipcode + ", tel=" + tel + ", firstImage="
				+ firstImage + ", firstImage2=" + firstImage2 + ", readcount=" + readcount + ", sido_code=" + sido_code
				+ ", gugun_code=" + gugun_code + ", latitude=" + latitude + ", longitude=" + longitude + ", mlevel="
				+ mlevel + "]";
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	

	
}

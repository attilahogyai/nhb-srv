package com.nhb.domain.image;

/**
* ExifData generated by hbm2java
*/
public class ExifData implements java.io.Serializable {

	private Integer id;
	
	private String artist;
	private String dateTimeOriginal;
	private Integer imageWidth;
	private Integer imageHeight;
	
	private Double fnumber;
	private String focalLength;
	private String exposureTime;	
	private Integer iso;
	
	private String exposureProgram;
	private String exposureCompenstation;
	private String meteringMode;
	private String whiteBalance;
	
	private String model;
	private String lensInfo;
	private String lensModel;
	private String lensMake;
	private Integer yresolution;
	private Integer xresolution;
	private String resolutionUnit;
	private String orientationDesc;
	

	public ExifData() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getFnumber() {
		return this.fnumber;
	}

	public void setFnumber(Double fnumber) {
		this.fnumber = fnumber;
	}

	public Integer getIso() {
		return this.iso;
	}

	public void setIso(Integer iso) {
		this.iso = iso;
	}

	public Integer getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return this.imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getExposureTime() {
		return this.exposureTime;
	}

	public void setExposureTime(String exposureTime) {
		this.exposureTime = exposureTime;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getExposureProgram() {
		return this.exposureProgram;
	}

	public void setExposureProgram(String exposureProgram) {
		this.exposureProgram = exposureProgram;
	}

	public String getDateTimeOriginal() {
		return this.dateTimeOriginal;
	}

	public void setDateTimeOriginal(String dateTimeOriginal) {
		this.dateTimeOriginal = dateTimeOriginal;
	}

	public String getExposureCompenstation() {
		return this.exposureCompenstation;
	}

	public void setExposureCompenstation(String exposureCompenstation) {
		this.exposureCompenstation = exposureCompenstation;
	}

	public String getMeteringMode() {
		return this.meteringMode;
	}

	public void setMeteringMode(String meteringMode) {
		this.meteringMode = meteringMode;
	}

	public String getWhiteBalance() {
		return this.whiteBalance;
	}

	public void setWhiteBalance(String whiteBalance) {
		this.whiteBalance = whiteBalance;
	}

	public String getLensInfo() {
		return this.lensInfo;
	}

	public void setLensInfo(String lensInfo) {
		this.lensInfo = lensInfo;
	}

	public String getLensModel() {
		return this.lensModel;
	}

	public void setLensModel(String lensModel) {
		this.lensModel = lensModel;
	}

	public String getLensMake() {
		return this.lensMake;
	}

	public void setLensMake(String lensMake) {
		this.lensMake = lensMake;
	}

	public Integer getYresolution() {
		return this.yresolution;
	}

	public void setYresolution(Integer yresolution) {
		this.yresolution = yresolution;
	}

	public Integer getXresolution() {
		return this.xresolution;
	}

	public void setXresolution(Integer xresolution) {
		this.xresolution = xresolution;
	}

	public String getResolutionUnit() {
		return this.resolutionUnit;
	}

	public void setResolutionUnit(String resolutionUnit) {
		this.resolutionUnit = resolutionUnit;
	}

	public String getOrientationDesc() {
		return this.orientationDesc;
	}

	public void setOrientationDesc(String orientation) {
		this.orientationDesc = orientation;
	}

	public String getFocalLength() {
		return this.focalLength;
	}

	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
}

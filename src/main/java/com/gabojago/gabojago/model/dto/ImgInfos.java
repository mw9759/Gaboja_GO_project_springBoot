package com.gabojago.gabojago.model.dto;

public class ImgInfos {
	private int articleNo;
	private String imgBlob;
	
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getImgBlob() {
		return imgBlob;
	}
	public void setImgBlob(String imgBlob) {
		this.imgBlob = imgBlob;
	}
	@Override
	public String toString() {
		return "ImgInfos [articleNo=" + articleNo + ", imgBlob=" + imgBlob + "]";
	}
}

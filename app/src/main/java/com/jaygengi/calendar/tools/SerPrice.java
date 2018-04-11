package com.jaygengi.calendar.tools;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * shitilei  
 * author: JayGengi 60167
 * email:  gengy@chinaraising.com
 * time:  2017/11/14 14:54
 */
public class SerPrice implements Parcelable {

	/** 服务价格*/
	private Float serPrice;
	/**选中状态*/
	private boolean isSelect;
	/** 服务价格*/
	private String calendarTime;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public Float getSerPrice() {
		return serPrice;
	}

	public void setSerPrice(Float serPrice) {
		this.serPrice = serPrice;
	}

	public String getCalendarTime() {
		return calendarTime;
	}

	public void setCalendarTime(String calendarTime) {
		this.calendarTime = calendarTime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.serPrice);
		dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
		dest.writeString(this.calendarTime);
	}

	public SerPrice() {
	}

	protected SerPrice(Parcel in) {
		this.serPrice = (Float) in.readValue(Float.class.getClassLoader());
		this.isSelect = in.readByte() != 0;
		this.calendarTime = in.readString();
	}

	public static final Creator<SerPrice> CREATOR = new Creator<SerPrice>() {
		@Override
		public SerPrice createFromParcel(Parcel source) {
			return new SerPrice(source);
		}

		@Override
		public SerPrice[] newArray(int size) {
			return new SerPrice[size];
		}
	};
}

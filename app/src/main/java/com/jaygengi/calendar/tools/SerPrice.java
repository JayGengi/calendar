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


	/**
	 * 服务标准标识
	 */
	private Integer serId;
	/**
	 * 车辆标识
	 */
	private Integer carId;
	/**
	 * 司机标识
	 */
	private Integer driverId;
	/**
	 * 服务价格
	 */
	private Float serPrice;
	/**
	 * 开始时间
	 */
	private Date beginDt;
	private String beginDtStr;
	/**
	 * 用来存放开始时间的字符串xxx,xxx,xxx
	 */
	private String beginDtStrs;
	/**
	 * 结束时间
	 */
	private Date endDt;
	/**
	 * 结束时间字符串
	 */
	private String endDtStr;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人ID
	 */
	private Integer createUserId;
	/**
	 * 创建人类型CD
	 */
	private Integer createUserTypeCd;
	/**
	 * 创建时间
	 */
	private Date createTm;
	/**
	 * 状态
	 */
	private Integer statusCd;
	/**是否已售*/
	private Integer isSaled;
	/**选中状态*/
	private Integer isSelect;
	public SerPrice() {
		super();
	}
	public SerPrice(Float serPrice, String beginDtStr) {
		super();
		this.serPrice = serPrice;
		setBeginDtStr(beginDtStr);
	}

	public Integer getSerId() {
		return serId;
	}

	public void setSerId(Integer serId) {
		this.serId = serId;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Float getSerPrice() {
		return serPrice;
	}

	public void setSerPrice(Float serPrice) {
		this.serPrice = serPrice;
	}

	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	public String getBeginDtStr() {
		if (null != this.beginDt) {
			return new SimpleDateFormat("yyyy-MM-dd").format(this.beginDt);
		}else{
			return null;
		}
	}

	public void setBeginDtStr(String value) {
		try {
			if(null != value && !"".equals(value))
				this.beginDt= new SimpleDateFormat("yyyy-MM-dd").parse(value);
			else
				this.beginDt=null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getEndDtStr() {
		if(null != this.endDt){
			return new SimpleDateFormat("yyyy-MM-dd").format(this.endDt);
		}
		else{
			return null;
		}
	}

	public void setEndDtStr(String endDtStr) {
		try {
			if(null != endDtStr && !"".equals(endDtStr))
				this.endDt= new SimpleDateFormat("yyyy-MM-dd").parse(endDtStr);
			else
				this.endDt=null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Integer getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getCreateUserTypeCd() {
		return createUserTypeCd;
	}

	public void setCreateUserTypeCd(Integer createUserTypeCd) {
		this.createUserTypeCd = createUserTypeCd;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public Integer getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(Integer statusCd) {
		this.statusCd = statusCd;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getBeginDtStrs() {
		return beginDtStrs;
	}

	public void setBeginDtStrs(String beginDtStrs) {
		this.beginDtStrs = beginDtStrs;
	}

	public Integer getIsSaled() {
		return isSaled;
	}

	public void setIsSaled(Integer isSaled) {
		this.isSaled = isSaled;
	}

	/**
	 * 统一时间格式与日历控件时间格式比对做逻辑处理
	 *  create by gengy 60167
	 *  at  2017/1/17 21:24
	 */
	public String getBeginDtString() {
		try {
			return new SimpleDateFormat("yyyy-M-d").format(this.beginDt);
		}catch(Exception e){
			return null;
		}
	}

	public String getBeginDtStrin() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(this.beginDt);
		}catch(Exception e){
			return null;
		}
	}
	public String getBeginDtY() {
		try {
			return new SimpleDateFormat("yyyy").format(this.beginDt);
		}catch(Exception e){
			return null;
		}
	}
	public String getBeginDtS() {
		try {
			return new SimpleDateFormat("M").format(this.beginDt);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.serId);
		dest.writeValue(this.carId);
		dest.writeValue(this.driverId);
		dest.writeValue(this.serPrice);
		dest.writeLong(this.beginDt != null ? this.beginDt.getTime() : -1);
		dest.writeString(this.beginDtStr);
		dest.writeString(this.beginDtStrs);
		dest.writeLong(this.endDt != null ? this.endDt.getTime() : -1);
		dest.writeString(this.endDtStr);
		dest.writeString(this.remark);
		dest.writeValue(this.createUserId);
		dest.writeValue(this.createUserTypeCd);
		dest.writeLong(this.createTm != null ? this.createTm.getTime() : -1);
		dest.writeValue(this.statusCd);
		dest.writeValue(this.isSaled);
		dest.writeValue(this.isSelect);
	}

	protected SerPrice(Parcel in) {
		this.serId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.carId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.driverId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.serPrice = (Float) in.readValue(Float.class.getClassLoader());
		long tmpBeginDt = in.readLong();
		this.beginDt = tmpBeginDt == -1 ? null : new Date(tmpBeginDt);
		this.beginDtStr = in.readString();
		this.beginDtStrs = in.readString();
		long tmpEndDt = in.readLong();
		this.endDt = tmpEndDt == -1 ? null : new Date(tmpEndDt);
		this.endDtStr = in.readString();
		this.remark = in.readString();
		this.createUserId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.createUserTypeCd = (Integer) in.readValue(Integer.class.getClassLoader());
		long tmpCreateTm = in.readLong();
		this.createTm = tmpCreateTm == -1 ? null : new Date(tmpCreateTm);
		this.statusCd = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isSaled = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isSelect = (Integer) in.readValue(Integer.class.getClassLoader());
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

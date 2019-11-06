package com.skyedu.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 地区城市表
 */
@Entity
@Table(name = "Edu_City")
public class EduCity implements java.io.Serializable {

    // Fields


    private Integer CityId;//层次id
    private String CityCode;//层次编号
    private String CityName;//层次名称
    private String Description;//描述
    private Integer Flag;//标注

    public EduCity(Integer cityId, String cityCode, String cityName, String description, Integer flag) {
        CityId = cityId;
        CityCode = cityCode;
        CityName = cityName;
        Description = description;
        Flag = flag;
    }

    public EduCity() {
    }
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Integer getCityId() {
		return CityId;
	}

	public void setCityId(Integer cityId) {
		CityId = cityId;
	}

	public String getCityCode() {
		return CityCode;
	}

	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Integer getFlag() {
		return Flag;
	}

	public void setFlag(Integer flag) {
		Flag = flag;
	}

	@Override
	public String toString() {
		return "EduCity{" +
				"CityId=" + CityId +
				", CityCode='" + CityCode + '\'' +
				", CityName='" + CityName + '\'' +
				", Description='" + Description + '\'' +
				", Flag=" + Flag +
				'}';
	}
}
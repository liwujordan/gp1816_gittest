package com.mobile.parser.modle.dim.base;

import com.mobile.common.DateTypeEnum;
import com.mobile.util.TimeUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间维度基础类
 * */
public class DateDimension extends BaseDimension{
    private int id;
    private int year;
    private int season;
    private int month;
    private int week;
    private int day;
    private Date calendar = new Date();
    private String type;

    public DateDimension() {
    }

    public DateDimension(int year, int season, int month, int week, int day) {
        this.year = year;
        this.season = season;
        this.month = month;
        this.week = week;
        this.day = day;
    }

    public DateDimension(int year, int season, int month, int week, int day,Date calendar) {
           this(year, season, month, week, day);
           this.calendar = calendar;
    }

    public DateDimension(int year, int season, int month, int week, int day,Date calendar,String type) {
          this(year, season, month, week, day,calendar);
          this.type = type;
    }

    public DateDimension(int id,int year, int season, int month, int week, int day,Date calendar,String type) {
        this(year, season, month, week, day,calendar,type);
        this.id=id;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
          dataOutput.writeInt(this.id);
          dataOutput.writeInt(this.year);
          dataOutput.writeInt(this.season);
          dataOutput.writeInt(this.month);
          dataOutput.writeInt(this.week);
          dataOutput.writeInt(this.day);
          dataOutput.writeLong(this.calendar.getTime());   //date类型的写成long  date类型的序列化
          dataOutput.writeUTF(this.type);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
           this.id = dataInput.readInt();
           this.year = dataInput.readInt();
           this.season=dataInput.readInt();
           this.month=dataInput.readInt();
           this.week=dataInput.readInt();
           this.day=dataInput.readInt();
           this.calendar.setTime(dataInput.readLong());   //date类型的反序列化
           this.type = dataInput.readUTF();
    }

    /**
     * 获取统计值的统计周期的时间维度
     * 返回时间维度的对象
     * */
    public static DateDimension buildDate(long time, DateTypeEnum type){
        Calendar cal = Calendar.getInstance();
        cal.clear();    //清空
        //获取年  精确当年1.1号
       int year = TimeUtil.getDateInfo(time,DateTypeEnum.YEAR);
        if(type.equals(DateTypeEnum.YEAR)){
            cal.set(year,0,1);
            return new DateDimension(year,1,1,1,1,cal.getTime(),type.type);
        }
        //public final Date getTime() {
        //        return new Date(getTimeInMillis());
        //    }

        //获取季度,精确当前季度第一个月的1号
        int season = TimeUtil.getDateInfo(time,DateTypeEnum.SEASON);
        if(type.equals(DateTypeEnum.SEASON)){
            /**
             * 1  1
             * 2  4
             * 3  7
             * 4  10
             * */
            int month = season * 3 - 2;
            cal.set(year,month -1,1);
            return new DateDimension(year,season,month,1,1,cal.getTime(),type.type);
        }

        //获取月  精确当月1号
        //封装TimeUtil时候 不能再减1了
        int month = TimeUtil.getDateInfo(time,DateTypeEnum.MONTH);
        if(type.equals(DateTypeEnum.MONTH)){
            cal.set(year,month-1,1);
            return new DateDimension(year,season,month,1,1,cal.getTime(),type.type);
        }

        //获取周  精确当周第一天的
        //封装TimeUtil时候 不能再减1了
        int week = TimeUtil.getDateInfo(time,DateTypeEnum.WEEK);
        if(type.equals(DateTypeEnum.WEEK)){
            long firstDayOfWeek = TimeUtil.getFirstDayOfWeek(time);
            year = TimeUtil.getDateInfo(firstDayOfWeek,DateTypeEnum.YEAR);
            season = TimeUtil.getDateInfo(firstDayOfWeek,DateTypeEnum.SEASON);
            month = TimeUtil.getDateInfo(firstDayOfWeek,DateTypeEnum.MONTH);
            cal.set(year,month-1,1);
            return new DateDimension(year,season,month,week,1,cal.getTime(),type.type);
        }

        //获取天  精确day这天
        //封装TimeUtil时候 不能再减1了
        int day = TimeUtil.getDateInfo(time,DateTypeEnum.DAY);
        if(type.equals(DateTypeEnum.DAY)){
            cal.set(year,month-1,1);
            return new DateDimension(year,season,month,week,day,cal.getTime(),type.type);
        }

        throw new RuntimeException("该type暂时不支持获取对应的dataDimension.type:"+type.getClass());
    }

    @Override
    public int compareTo(BaseDimension o) {
        if(this == o){
            return 0;
        }
        DateDimension other =(DateDimension) o;
        int tmp = this.id-other.id;
        if(tmp != 0){
            return tmp;
        }

        tmp = this.year-other.year;
        if(tmp != 0){
            return tmp;
        }

        tmp = this.season-other.season;
        if(tmp != 0){
            return tmp;
        }

        tmp = this.month-other.month;
        if(tmp != 0){
            return tmp;
        }

        tmp = this.week-other.week;
        if(tmp != 0){
            return tmp;
        }

        tmp = this.day-other.day;
        if(tmp != 0){
            return tmp;
        }
        return this.type.compareTo(other.type);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
